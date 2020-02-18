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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_OBSERVABLE", "RULE_LOWERCASE_ID", "RULE_ID", "RULE_STRING", "RULE_EMBEDDEDTEXT", "RULE_EXPR", "RULE_CAMELCASE_ID", "RULE_REGEXP", "RULE_INT", "RULE_SEPARATOR", "RULE_ANNOTATION_ID", "RULE_ARGVALUE", "RULE_UPPERCASE_ID", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'app'", "'user'", "'trait'", "'library'", "'behavior'", "'behaviour'", "'import'", "','", "'worldview'", "'observable'", "'label'", "'description'", "'permissions'", "'author'", "'version'", "'created'", "'modified'", "'action'", "':'", "'('", "')'", "'if'", "'else'", "'while'", "'do'", "'for'", "'in'", "'->'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'to'", "'unknown'", "'*'", "'#'", "'urn:klab:'", "'&'", "'='", "'{'", "'}'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'=?'", "'@'", "'>'", "'<'", "'!='", "'<='", "'>='", "'+'", "'-'", "'l'", "'e'", "'E'", "'AD'", "'CE'", "'BC'", "'^'"
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
    public static final int RULE_ARGVALUE=15;
    public static final int RULE_STRING=7;
    public static final int RULE_SEPARATOR=13;
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
    public static final int RULE_LOWERCASE_ID=5;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int RULE_EXPR=9;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
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
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
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
                case 39:
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

                if ( (LA2_0==RULE_ANNOTATION_ID||LA2_0==39) ) {
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
    // InternalKactors.g:153:1: rulePreamble returns [EObject current=null] : ( () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) ;
    public final EObject rulePreamble() throws RecognitionException {
        EObject current = null;

        Token lv_app_1_0=null;
        Token lv_user_2_0=null;
        Token lv_library_3_1=null;
        Token lv_library_3_2=null;
        Token lv_behavior_4_1=null;
        Token lv_behavior_4_2=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token lv_observable_14_0=null;
        Token otherlv_16=null;
        Token lv_label_17_1=null;
        Token lv_label_17_2=null;
        Token lv_label_17_3=null;
        Token otherlv_18=null;
        Token lv_description_19_0=null;
        Token otherlv_20=null;
        Token lv_permissions_21_0=null;
        Token otherlv_22=null;
        Token lv_authors_23_0=null;
        Token otherlv_24=null;
        Token otherlv_26=null;
        Token lv_createcomment_28_0=null;
        Token otherlv_29=null;
        Token lv_modcomment_31_0=null;
        AntlrDatatypeRuleToken lv_name_5_0 = null;

        AntlrDatatypeRuleToken lv_imports_8_0 = null;

        AntlrDatatypeRuleToken lv_imports_10_0 = null;

        AntlrDatatypeRuleToken lv_worldview_12_0 = null;

        EObject lv_observables_15_0 = null;

        AntlrDatatypeRuleToken lv_version_25_0 = null;

        EObject lv_created_27_0 = null;

        EObject lv_modified_30_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKactors.g:162:2: ( ( () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) )
            // InternalKactors.g:163:2: ( () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            {
            // InternalKactors.g:163:2: ( () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            // InternalKactors.g:164:3: () ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
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

            // InternalKactors.g:174:3: ( ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0>=22 && LA6_0<=27)) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalKactors.g:175:4: ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) ) ( (lv_name_5_0= rulePathName ) )
                    {
                    // InternalKactors.g:175:4: ( ( (lv_app_1_0= 'app' ) ) | ( (lv_user_2_0= 'user' ) ) | ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) ) | ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) ) )
                    int alt5=4;
                    switch ( input.LA(1) ) {
                    case 22:
                        {
                        alt5=1;
                        }
                        break;
                    case 23:
                        {
                        alt5=2;
                        }
                        break;
                    case 24:
                    case 25:
                        {
                        alt5=3;
                        }
                        break;
                    case 26:
                    case 27:
                        {
                        alt5=4;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 0, input);

                        throw nvae;
                    }

                    switch (alt5) {
                        case 1 :
                            // InternalKactors.g:176:5: ( (lv_app_1_0= 'app' ) )
                            {
                            // InternalKactors.g:176:5: ( (lv_app_1_0= 'app' ) )
                            // InternalKactors.g:177:6: (lv_app_1_0= 'app' )
                            {
                            // InternalKactors.g:177:6: (lv_app_1_0= 'app' )
                            // InternalKactors.g:178:7: lv_app_1_0= 'app'
                            {
                            lv_app_1_0=(Token)match(input,22,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_app_1_0, grammarAccess.getPreambleAccess().getAppAppKeyword_1_0_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getPreambleRule());
                              							}
                              							setWithLastConsumed(current, "app", true, "app");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:191:5: ( (lv_user_2_0= 'user' ) )
                            {
                            // InternalKactors.g:191:5: ( (lv_user_2_0= 'user' ) )
                            // InternalKactors.g:192:6: (lv_user_2_0= 'user' )
                            {
                            // InternalKactors.g:192:6: (lv_user_2_0= 'user' )
                            // InternalKactors.g:193:7: lv_user_2_0= 'user'
                            {
                            lv_user_2_0=(Token)match(input,23,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_user_2_0, grammarAccess.getPreambleAccess().getUserUserKeyword_1_0_1_0());
                              						
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
                        case 3 :
                            // InternalKactors.g:206:5: ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) )
                            {
                            // InternalKactors.g:206:5: ( ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) ) )
                            // InternalKactors.g:207:6: ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) )
                            {
                            // InternalKactors.g:207:6: ( (lv_library_3_1= 'trait' | lv_library_3_2= 'library' ) )
                            // InternalKactors.g:208:7: (lv_library_3_1= 'trait' | lv_library_3_2= 'library' )
                            {
                            // InternalKactors.g:208:7: (lv_library_3_1= 'trait' | lv_library_3_2= 'library' )
                            int alt3=2;
                            int LA3_0 = input.LA(1);

                            if ( (LA3_0==24) ) {
                                alt3=1;
                            }
                            else if ( (LA3_0==25) ) {
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
                                    // InternalKactors.g:209:8: lv_library_3_1= 'trait'
                                    {
                                    lv_library_3_1=(Token)match(input,24,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_library_3_1, grammarAccess.getPreambleAccess().getLibraryTraitKeyword_1_0_2_0_0());
                                      							
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
                                    // InternalKactors.g:220:8: lv_library_3_2= 'library'
                                    {
                                    lv_library_3_2=(Token)match(input,25,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_library_3_2, grammarAccess.getPreambleAccess().getLibraryLibraryKeyword_1_0_2_0_1());
                                      							
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
                        case 4 :
                            // InternalKactors.g:234:5: ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) )
                            {
                            // InternalKactors.g:234:5: ( ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) ) )
                            // InternalKactors.g:235:6: ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) )
                            {
                            // InternalKactors.g:235:6: ( (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' ) )
                            // InternalKactors.g:236:7: (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' )
                            {
                            // InternalKactors.g:236:7: (lv_behavior_4_1= 'behavior' | lv_behavior_4_2= 'behaviour' )
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
                                    // InternalKactors.g:237:8: lv_behavior_4_1= 'behavior'
                                    {
                                    lv_behavior_4_1=(Token)match(input,26,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_behavior_4_1, grammarAccess.getPreambleAccess().getBehaviorBehaviorKeyword_1_0_3_0_0());
                                      							
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
                                    // InternalKactors.g:248:8: lv_behavior_4_2= 'behaviour'
                                    {
                                    lv_behavior_4_2=(Token)match(input,27,FOLLOW_4); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      								newLeafNode(lv_behavior_4_2, grammarAccess.getPreambleAccess().getBehaviorBehaviourKeyword_1_0_3_0_1());
                                      							
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

                    // InternalKactors.g:262:4: ( (lv_name_5_0= rulePathName ) )
                    // InternalKactors.g:263:5: (lv_name_5_0= rulePathName )
                    {
                    // InternalKactors.g:263:5: (lv_name_5_0= rulePathName )
                    // InternalKactors.g:264:6: lv_name_5_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPreambleAccess().getNamePathNameParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_name_5_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPreambleRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_5_0,
                      							"org.integratedmodelling.kactors.Kactors.PathName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:282:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            // InternalKactors.g:283:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) )
            {
            // InternalKactors.g:283:4: ( ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* ) )
            // InternalKactors.g:284:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            // InternalKactors.g:287:5: ( ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )* )
            // InternalKactors.g:288:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*
            {
            // InternalKactors.g:288:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*
            loop13:
            do {
                int alt13=11;
                alt13 = dfa13.predict(input);
                switch (alt13) {
            	case 1 :
            	    // InternalKactors.g:289:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
            	    {
            	    // InternalKactors.g:289:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
            	    // InternalKactors.g:290:5: {...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKactors.g:290:105: ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
            	    // InternalKactors.g:291:6: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
            	    // InternalKactors.g:294:9: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
            	    // InternalKactors.g:294:10: {...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:294:19: (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
            	    // InternalKactors.g:294:20: otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
            	    {
            	    otherlv_7=(Token)match(input,28,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_7, grammarAccess.getPreambleAccess().getImportKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKactors.g:298:9: ( (lv_imports_8_0= rulePathName ) )
            	    // InternalKactors.g:299:10: (lv_imports_8_0= rulePathName )
            	    {
            	    // InternalKactors.g:299:10: (lv_imports_8_0= rulePathName )
            	    // InternalKactors.g:300:11: lv_imports_8_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_imports_8_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											add(
            	      												current,
            	      												"imports",
            	      												lv_imports_8_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:317:9: (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
            	    loop7:
            	    do {
            	        int alt7=2;
            	        int LA7_0 = input.LA(1);

            	        if ( (LA7_0==29) ) {
            	            alt7=1;
            	        }


            	        switch (alt7) {
            	    	case 1 :
            	    	    // InternalKactors.g:318:10: otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) )
            	    	    {
            	    	    otherlv_9=(Token)match(input,29,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_9, grammarAccess.getPreambleAccess().getCommaKeyword_2_0_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKactors.g:322:10: ( (lv_imports_10_0= rulePathName ) )
            	    	    // InternalKactors.g:323:11: (lv_imports_10_0= rulePathName )
            	    	    {
            	    	    // InternalKactors.g:323:11: (lv_imports_10_0= rulePathName )
            	    	    // InternalKactors.g:324:12: lv_imports_10_0= rulePathName
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
            	    	      											
            	    	    }
            	    	    pushFollow(FOLLOW_6);
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


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop7;
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
            	    // InternalKactors.g:348:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:348:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:349:5: {...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKactors.g:349:105: ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:350:6: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
            	    // InternalKactors.g:353:9: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
            	    // InternalKactors.g:353:10: {...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:353:19: (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
            	    // InternalKactors.g:353:20: otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) )
            	    {
            	    otherlv_11=(Token)match(input,30,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_11, grammarAccess.getPreambleAccess().getWorldviewKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKactors.g:357:9: ( (lv_worldview_12_0= rulePathName ) )
            	    // InternalKactors.g:358:10: (lv_worldview_12_0= rulePathName )
            	    {
            	    // InternalKactors.g:358:10: (lv_worldview_12_0= rulePathName )
            	    // InternalKactors.g:359:11: lv_worldview_12_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_worldview_12_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"worldview",
            	      												lv_worldview_12_0,
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
            	    // InternalKactors.g:382:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:382:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
            	    // InternalKactors.g:383:5: {...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKactors.g:383:105: ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
            	    // InternalKactors.g:384:6: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
            	    // InternalKactors.g:387:9: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
            	    // InternalKactors.g:387:10: {...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:387:19: (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
            	    // InternalKactors.g:387:20: otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
            	    {
            	    otherlv_13=(Token)match(input,31,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_13, grammarAccess.getPreambleAccess().getObservableKeyword_2_2_0());
            	      								
            	    }
            	    // InternalKactors.g:391:9: ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==RULE_OBSERVABLE) ) {
            	        alt8=1;
            	    }
            	    else if ( (LA8_0==41) ) {
            	        alt8=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 8, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // InternalKactors.g:392:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
            	            {
            	            // InternalKactors.g:392:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
            	            // InternalKactors.g:393:11: (lv_observable_14_0= RULE_OBSERVABLE )
            	            {
            	            // InternalKactors.g:393:11: (lv_observable_14_0= RULE_OBSERVABLE )
            	            // InternalKactors.g:394:12: lv_observable_14_0= RULE_OBSERVABLE
            	            {
            	            lv_observable_14_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_observable_14_0, grammarAccess.getPreambleAccess().getObservableOBSERVABLETerminalRuleCall_2_2_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"observable",
            	              													lv_observable_14_0,
            	              													"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
            	              											
            	            }

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:411:10: ( (lv_observables_15_0= ruleList ) )
            	            {
            	            // InternalKactors.g:411:10: ( (lv_observables_15_0= ruleList ) )
            	            // InternalKactors.g:412:11: (lv_observables_15_0= ruleList )
            	            {
            	            // InternalKactors.g:412:11: (lv_observables_15_0= ruleList )
            	            // InternalKactors.g:413:12: lv_observables_15_0= ruleList
            	            {
            	            if ( state.backtracking==0 ) {

            	              												newCompositeNode(grammarAccess.getPreambleAccess().getObservablesListParserRuleCall_2_2_1_1_0());
            	              											
            	            }
            	            pushFollow(FOLLOW_5);
            	            lv_observables_15_0=ruleList();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	              												}
            	              												set(
            	              													current,
            	              													"observables",
            	              													lv_observables_15_0,
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
            	    // InternalKactors.g:437:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:437:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
            	    // InternalKactors.g:438:5: {...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKactors.g:438:105: ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:439:6: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
            	    // InternalKactors.g:442:9: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:442:10: {...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:442:19: (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
            	    // InternalKactors.g:442:20: otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
            	    {
            	    otherlv_16=(Token)match(input,32,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_16, grammarAccess.getPreambleAccess().getLabelKeyword_2_3_0());
            	      								
            	    }
            	    // InternalKactors.g:446:9: ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
            	    // InternalKactors.g:447:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:447:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
            	    // InternalKactors.g:448:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
            	    {
            	    // InternalKactors.g:448:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
            	    int alt9=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt9=1;
            	        }
            	        break;
            	    case RULE_ID:
            	        {
            	        alt9=2;
            	        }
            	        break;
            	    case RULE_STRING:
            	        {
            	        alt9=3;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 9, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt9) {
            	        case 1 :
            	            // InternalKactors.g:449:12: lv_label_17_1= RULE_LOWERCASE_ID
            	            {
            	            lv_label_17_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_17_1, grammarAccess.getPreambleAccess().getLabelLOWERCASE_IDTerminalRuleCall_2_3_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_17_1,
            	              													"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:464:12: lv_label_17_2= RULE_ID
            	            {
            	            lv_label_17_2=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_17_2, grammarAccess.getPreambleAccess().getLabelIDTerminalRuleCall_2_3_1_0_1());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_17_2,
            	              													"org.eclipse.xtext.common.Terminals.ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKactors.g:479:12: lv_label_17_3= RULE_STRING
            	            {
            	            lv_label_17_3=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_17_3, grammarAccess.getPreambleAccess().getLabelSTRINGTerminalRuleCall_2_3_1_0_2());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_17_3,
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
            	    // InternalKactors.g:502:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:502:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:503:5: {...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
            	    }
            	    // InternalKactors.g:503:105: ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:504:6: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
            	    // InternalKactors.g:507:9: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:507:10: {...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:507:19: (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
            	    // InternalKactors.g:507:20: otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) )
            	    {
            	    otherlv_18=(Token)match(input,33,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_18, grammarAccess.getPreambleAccess().getDescriptionKeyword_2_4_0());
            	      								
            	    }
            	    // InternalKactors.g:511:9: ( (lv_description_19_0= RULE_STRING ) )
            	    // InternalKactors.g:512:10: (lv_description_19_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:512:10: (lv_description_19_0= RULE_STRING )
            	    // InternalKactors.g:513:11: lv_description_19_0= RULE_STRING
            	    {
            	    lv_description_19_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_description_19_0, grammarAccess.getPreambleAccess().getDescriptionSTRINGTerminalRuleCall_2_4_1_0());
            	      										
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

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalKactors.g:535:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:535:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:536:5: {...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
            	    }
            	    // InternalKactors.g:536:105: ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:537:6: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
            	    // InternalKactors.g:540:9: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:540:10: {...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:540:19: (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
            	    // InternalKactors.g:540:20: otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) )
            	    {
            	    otherlv_20=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_20, grammarAccess.getPreambleAccess().getPermissionsKeyword_2_5_0());
            	      								
            	    }
            	    // InternalKactors.g:544:9: ( (lv_permissions_21_0= RULE_STRING ) )
            	    // InternalKactors.g:545:10: (lv_permissions_21_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:545:10: (lv_permissions_21_0= RULE_STRING )
            	    // InternalKactors.g:546:11: lv_permissions_21_0= RULE_STRING
            	    {
            	    lv_permissions_21_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_permissions_21_0, grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_2_5_1_0());
            	      										
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

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 7 :
            	    // InternalKactors.g:568:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKactors.g:568:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKactors.g:569:5: {...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
            	    }
            	    // InternalKactors.g:569:105: ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
            	    // InternalKactors.g:570:6: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
            	    // InternalKactors.g:573:9: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
            	    int cnt10=0;
            	    loop10:
            	    do {
            	        int alt10=2;
            	        int LA10_0 = input.LA(1);

            	        if ( (LA10_0==35) ) {
            	            int LA10_2 = input.LA(2);

            	            if ( ((synpred19_InternalKactors()&&(true))) ) {
            	                alt10=1;
            	            }


            	        }


            	        switch (alt10) {
            	    	case 1 :
            	    	    // InternalKactors.g:573:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    	    }
            	    	    // InternalKactors.g:573:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
            	    	    // InternalKactors.g:573:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_22=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_22, grammarAccess.getPreambleAccess().getAuthorKeyword_2_6_0());
            	    	      								
            	    	    }
            	    	    // InternalKactors.g:577:9: ( (lv_authors_23_0= RULE_STRING ) )
            	    	    // InternalKactors.g:578:10: (lv_authors_23_0= RULE_STRING )
            	    	    {
            	    	    // InternalKactors.g:578:10: (lv_authors_23_0= RULE_STRING )
            	    	    // InternalKactors.g:579:11: lv_authors_23_0= RULE_STRING
            	    	    {
            	    	    lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											newLeafNode(lv_authors_23_0, grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_2_6_1_0());
            	    	      										
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
            	    	    if ( cnt10 >= 1 ) break loop10;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(10, input);
            	                throw eee;
            	        }
            	        cnt10++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalKactors.g:601:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:601:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKactors.g:602:5: {...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
            	    }
            	    // InternalKactors.g:602:105: ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKactors.g:603:6: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
            	    // InternalKactors.g:606:9: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
            	    // InternalKactors.g:606:10: {...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:606:19: (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
            	    // InternalKactors.g:606:20: otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) )
            	    {
            	    otherlv_24=(Token)match(input,36,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_24, grammarAccess.getPreambleAccess().getVersionKeyword_2_7_0());
            	      								
            	    }
            	    // InternalKactors.g:610:9: ( (lv_version_25_0= ruleVersionNumber ) )
            	    // InternalKactors.g:611:10: (lv_version_25_0= ruleVersionNumber )
            	    {
            	    // InternalKactors.g:611:10: (lv_version_25_0= ruleVersionNumber )
            	    // InternalKactors.g:612:11: lv_version_25_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_7_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_version_25_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"version",
            	      												lv_version_25_0,
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
            	    // InternalKactors.g:635:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:635:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:636:5: {...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
            	    }
            	    // InternalKactors.g:636:105: ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:637:6: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
            	    // InternalKactors.g:640:9: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:640:10: {...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:640:19: (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
            	    // InternalKactors.g:640:20: otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )?
            	    {
            	    otherlv_26=(Token)match(input,37,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_26, grammarAccess.getPreambleAccess().getCreatedKeyword_2_8_0());
            	      								
            	    }
            	    // InternalKactors.g:644:9: ( (lv_created_27_0= ruleDate ) )
            	    // InternalKactors.g:645:10: (lv_created_27_0= ruleDate )
            	    {
            	    // InternalKactors.g:645:10: (lv_created_27_0= ruleDate )
            	    // InternalKactors.g:646:11: lv_created_27_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_8_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_11);
            	    lv_created_27_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"created",
            	      												lv_created_27_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:663:9: ( (lv_createcomment_28_0= RULE_STRING ) )?
            	    int alt11=2;
            	    int LA11_0 = input.LA(1);

            	    if ( (LA11_0==RULE_STRING) ) {
            	        alt11=1;
            	    }
            	    switch (alt11) {
            	        case 1 :
            	            // InternalKactors.g:664:10: (lv_createcomment_28_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:664:10: (lv_createcomment_28_0= RULE_STRING )
            	            // InternalKactors.g:665:11: lv_createcomment_28_0= RULE_STRING
            	            {
            	            lv_createcomment_28_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_createcomment_28_0, grammarAccess.getPreambleAccess().getCreatecommentSTRINGTerminalRuleCall_2_8_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"createcomment",
            	              												lv_createcomment_28_0,
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
            	    // InternalKactors.g:687:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:687:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:688:5: {...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9)");
            	    }
            	    // InternalKactors.g:688:105: ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:689:6: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9);
            	    // InternalKactors.g:692:9: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:692:10: {...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:692:19: (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
            	    // InternalKactors.g:692:20: otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )?
            	    {
            	    otherlv_29=(Token)match(input,38,FOLLOW_10); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_29, grammarAccess.getPreambleAccess().getModifiedKeyword_2_9_0());
            	      								
            	    }
            	    // InternalKactors.g:696:9: ( (lv_modified_30_0= ruleDate ) )
            	    // InternalKactors.g:697:10: (lv_modified_30_0= ruleDate )
            	    {
            	    // InternalKactors.g:697:10: (lv_modified_30_0= ruleDate )
            	    // InternalKactors.g:698:11: lv_modified_30_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_9_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_11);
            	    lv_modified_30_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"modified",
            	      												lv_modified_30_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:715:9: ( (lv_modcomment_31_0= RULE_STRING ) )?
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==RULE_STRING) ) {
            	        alt12=1;
            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // InternalKactors.g:716:10: (lv_modcomment_31_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:716:10: (lv_modcomment_31_0= RULE_STRING )
            	            // InternalKactors.g:717:11: lv_modcomment_31_0= RULE_STRING
            	            {
            	            lv_modcomment_31_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_modcomment_31_0, grammarAccess.getPreambleAccess().getModcommentSTRINGTerminalRuleCall_2_9_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"modcomment",
            	              												lv_modcomment_31_0,
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
            	    break loop13;
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
    // InternalKactors.g:753:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:753:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:754:2: iv_ruleDefinition= ruleDefinition EOF
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
    // InternalKactors.g:760:1: ruleDefinition returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) ;
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
            // InternalKactors.g:766:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) ) )
            // InternalKactors.g:767:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            {
            // InternalKactors.g:767:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) ) )
            // InternalKactors.g:768:3: ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'action' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleMessageBody ) )
            {
            // InternalKactors.g:768:3: ( (lv_annotations_0_0= ruleAnnotation ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_ANNOTATION_ID) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalKactors.g:769:4: (lv_annotations_0_0= ruleAnnotation )
            	    {
            	    // InternalKactors.g:769:4: (lv_annotations_0_0= ruleAnnotation )
            	    // InternalKactors.g:770:5: lv_annotations_0_0= ruleAnnotation
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
            	    break loop14;
                }
            } while (true);

            otherlv_1=(Token)match(input,39,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getDefinitionAccess().getActionKeyword_1());
              		
            }
            // InternalKactors.g:791:3: ( (lv_name_2_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:792:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:792:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:793:5: lv_name_2_0= RULE_LOWERCASE_ID
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

            // InternalKactors.g:809:3: ( (lv_arguments_3_0= ruleArgumentDeclaration ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==41) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalKactors.g:810:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:810:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    // InternalKactors.g:811:5: lv_arguments_3_0= ruleArgumentDeclaration
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

            otherlv_4=(Token)match(input,40,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDefinitionAccess().getColonKeyword_4());
              		
            }
            // InternalKactors.g:832:3: ( (lv_body_5_0= ruleMessageBody ) )
            // InternalKactors.g:833:4: (lv_body_5_0= ruleMessageBody )
            {
            // InternalKactors.g:833:4: (lv_body_5_0= ruleMessageBody )
            // InternalKactors.g:834:5: lv_body_5_0= ruleMessageBody
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
    // InternalKactors.g:855:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:855:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:856:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
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
    // InternalKactors.g:862:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:868:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:869:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:869:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:870:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:870:3: ()
            // InternalKactors.g:871:4: 
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

            otherlv_1=(Token)match(input,41,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:884:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==RULE_LOWERCASE_ID) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalKactors.g:885:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:885:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:886:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:886:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:887:6: lv_ids_2_0= RULE_LOWERCASE_ID
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

                    // InternalKactors.g:903:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==29) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // InternalKactors.g:904:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,29,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:908:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:909:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:909:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:910:7: lv_ids_4_0= RULE_LOWERCASE_ID
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
                    	    break loop16;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:936:1: entryRuleMessageBody returns [EObject current=null] : iv_ruleMessageBody= ruleMessageBody EOF ;
    public final EObject entryRuleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageBody = null;


        try {
            // InternalKactors.g:936:52: (iv_ruleMessageBody= ruleMessageBody EOF )
            // InternalKactors.g:937:2: iv_ruleMessageBody= ruleMessageBody EOF
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
    // InternalKactors.g:943:1: ruleMessageBody returns [EObject current=null] : ( () ( (lv_lists_1_0= ruleStatementList ) )* ) ;
    public final EObject ruleMessageBody() throws RecognitionException {
        EObject current = null;

        EObject lv_lists_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:949:2: ( ( () ( (lv_lists_1_0= ruleStatementList ) )* ) )
            // InternalKactors.g:950:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            {
            // InternalKactors.g:950:2: ( () ( (lv_lists_1_0= ruleStatementList ) )* )
            // InternalKactors.g:951:3: () ( (lv_lists_1_0= ruleStatementList ) )*
            {
            // InternalKactors.g:951:3: ()
            // InternalKactors.g:952:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getMessageBodyAccess().getBodyAction_0(),
              					current);
              			
            }

            }

            // InternalKactors.g:961:3: ( (lv_lists_1_0= ruleStatementList ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=RULE_OBSERVABLE && LA18_0<=RULE_LOWERCASE_ID)||(LA18_0>=RULE_STRING && LA18_0<=RULE_EXPR)||LA18_0==RULE_INT||LA18_0==RULE_ARGVALUE||LA18_0==41||LA18_0==43||(LA18_0>=45 && LA18_0<=47)||(LA18_0>=50 && LA18_0<=51)||LA18_0==58||LA18_0==61||LA18_0==63||(LA18_0>=75 && LA18_0<=76)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalKactors.g:962:4: (lv_lists_1_0= ruleStatementList )
            	    {
            	    // InternalKactors.g:962:4: (lv_lists_1_0= ruleStatementList )
            	    // InternalKactors.g:963:5: lv_lists_1_0= ruleStatementList
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
            	    break loop18;
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
    // InternalKactors.g:984:1: entryRuleMessageCall returns [EObject current=null] : iv_ruleMessageCall= ruleMessageCall EOF ;
    public final EObject entryRuleMessageCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageCall = null;


        try {
            // InternalKactors.g:984:52: (iv_ruleMessageCall= ruleMessageCall EOF )
            // InternalKactors.g:985:2: iv_ruleMessageCall= ruleMessageCall EOF
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
    // InternalKactors.g:991:1: ruleMessageCall returns [EObject current=null] : ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) ;
    public final EObject ruleMessageCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject this_StatementGroup_4 = null;

        EObject lv_actions_6_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:997:2: ( ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? ) )
            // InternalKactors.g:998:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            {
            // InternalKactors.g:998:2: ( ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )? )
            // InternalKactors.g:999:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup ) (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            {
            // InternalKactors.g:999:3: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) | this_StatementGroup_4= ruleStatementGroup )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_LOWERCASE_ID) ) {
                alt21=1;
            }
            else if ( (LA21_0==41) ) {
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
                    // InternalKactors.g:1000:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    {
                    // InternalKactors.g:1000:4: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
                    // InternalKactors.g:1001:5: ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    {
                    // InternalKactors.g:1001:5: ( (lv_name_0_0= rulePathName ) )
                    // InternalKactors.g:1002:6: (lv_name_0_0= rulePathName )
                    {
                    // InternalKactors.g:1002:6: (lv_name_0_0= rulePathName )
                    // InternalKactors.g:1003:7: lv_name_0_0= rulePathName
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

                    // InternalKactors.g:1020:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
                    int alt20=2;
                    alt20 = dfa20.predict(input);
                    switch (alt20) {
                        case 1 :
                            // InternalKactors.g:1021:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                            {
                            otherlv_1=(Token)match(input,41,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getMessageCallAccess().getLeftParenthesisKeyword_0_0_1_0());
                              					
                            }
                            // InternalKactors.g:1025:6: ( (lv_parameters_2_0= ruleParameterList ) )?
                            int alt19=2;
                            int LA19_0 = input.LA(1);

                            if ( ((LA19_0>=RULE_OBSERVABLE && LA19_0<=RULE_LOWERCASE_ID)||LA19_0==RULE_STRING||LA19_0==RULE_EXPR||LA19_0==RULE_INT||LA19_0==RULE_ARGVALUE||LA19_0==41||(LA19_0>=50 && LA19_0<=51)||LA19_0==58||LA19_0==61||LA19_0==63||(LA19_0>=75 && LA19_0<=76)) ) {
                                alt19=1;
                            }
                            switch (alt19) {
                                case 1 :
                                    // InternalKactors.g:1026:7: (lv_parameters_2_0= ruleParameterList )
                                    {
                                    // InternalKactors.g:1026:7: (lv_parameters_2_0= ruleParameterList )
                                    // InternalKactors.g:1027:8: lv_parameters_2_0= ruleParameterList
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

                            otherlv_3=(Token)match(input,42,FOLLOW_22); if (state.failed) return current;
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
                    // InternalKactors.g:1051:4: this_StatementGroup_4= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      				/* */
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getMessageCallAccess().getStatementGroupParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_22);
                    this_StatementGroup_4=ruleStatementGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_StatementGroup_4;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1063:3: (otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==40) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalKactors.g:1064:4: otherlv_5= ':' ( (lv_actions_6_0= ruleActions ) )
                    {
                    otherlv_5=(Token)match(input,40,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getMessageCallAccess().getColonKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:1068:4: ( (lv_actions_6_0= ruleActions ) )
                    // InternalKactors.g:1069:5: (lv_actions_6_0= ruleActions )
                    {
                    // InternalKactors.g:1069:5: (lv_actions_6_0= ruleActions )
                    // InternalKactors.g:1070:6: lv_actions_6_0= ruleActions
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
    // InternalKactors.g:1092:1: entryRuleStatementGroup returns [EObject current=null] : iv_ruleStatementGroup= ruleStatementGroup EOF ;
    public final EObject entryRuleStatementGroup() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementGroup = null;


        try {
            // InternalKactors.g:1092:55: (iv_ruleStatementGroup= ruleStatementGroup EOF )
            // InternalKactors.g:1093:2: iv_ruleStatementGroup= ruleStatementGroup EOF
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
    // InternalKactors.g:1099:1: ruleStatementGroup returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ) ;
    public final EObject ruleStatementGroup() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1105:2: ( ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' ) )
            // InternalKactors.g:1106:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' )
            {
            // InternalKactors.g:1106:2: ( () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')' )
            // InternalKactors.g:1107:3: () otherlv_1= '(' ( (lv_body_2_0= ruleMessageBody ) )? otherlv_3= ')'
            {
            // InternalKactors.g:1107:3: ()
            // InternalKactors.g:1108:4: 
            {
            if ( state.backtracking==0 ) {

              				/* */
              			
            }
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getStatementGroupAccess().getGroupAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,41,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getStatementGroupAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:1121:3: ( (lv_body_2_0= ruleMessageBody ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( ((LA23_0>=RULE_OBSERVABLE && LA23_0<=RULE_LOWERCASE_ID)||(LA23_0>=RULE_STRING && LA23_0<=RULE_EXPR)||LA23_0==RULE_INT||LA23_0==RULE_ARGVALUE||LA23_0==41||LA23_0==43||(LA23_0>=45 && LA23_0<=47)||(LA23_0>=50 && LA23_0<=51)||LA23_0==58||LA23_0==61||LA23_0==63||(LA23_0>=75 && LA23_0<=76)) ) {
                alt23=1;
            }
            else if ( (LA23_0==42) ) {
                int LA23_2 = input.LA(2);

                if ( (synpred35_InternalKactors()) ) {
                    alt23=1;
                }
            }
            switch (alt23) {
                case 1 :
                    // InternalKactors.g:1122:4: (lv_body_2_0= ruleMessageBody )
                    {
                    // InternalKactors.g:1122:4: (lv_body_2_0= ruleMessageBody )
                    // InternalKactors.g:1123:5: lv_body_2_0= ruleMessageBody
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

            otherlv_3=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1148:1: entryRuleStatementList returns [EObject current=null] : iv_ruleStatementList= ruleStatementList EOF ;
    public final EObject entryRuleStatementList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementList = null;


        try {
            // InternalKactors.g:1148:54: (iv_ruleStatementList= ruleStatementList EOF )
            // InternalKactors.g:1149:2: iv_ruleStatementList= ruleStatementList EOF
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
    // InternalKactors.g:1155:1: ruleStatementList returns [EObject current=null] : ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) ;
    public final EObject ruleStatementList() throws RecognitionException {
        EObject current = null;

        EObject lv_first_0_0 = null;

        EObject lv_next_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1161:2: ( ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* ) )
            // InternalKactors.g:1162:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            {
            // InternalKactors.g:1162:2: ( ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )* )
            // InternalKactors.g:1163:3: ( (lv_first_0_0= ruleStatement ) ) ( (lv_next_1_0= ruleNextStatement ) )*
            {
            // InternalKactors.g:1163:3: ( (lv_first_0_0= ruleStatement ) )
            // InternalKactors.g:1164:4: (lv_first_0_0= ruleStatement )
            {
            // InternalKactors.g:1164:4: (lv_first_0_0= ruleStatement )
            // InternalKactors.g:1165:5: lv_first_0_0= ruleStatement
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

            // InternalKactors.g:1182:3: ( (lv_next_1_0= ruleNextStatement ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==29) ) {
                    int LA24_2 = input.LA(2);

                    if ( (synpred36_InternalKactors()) ) {
                        alt24=1;
                    }


                }


                switch (alt24) {
            	case 1 :
            	    // InternalKactors.g:1183:4: (lv_next_1_0= ruleNextStatement )
            	    {
            	    // InternalKactors.g:1183:4: (lv_next_1_0= ruleNextStatement )
            	    // InternalKactors.g:1184:5: lv_next_1_0= ruleNextStatement
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
            	    break loop24;
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
    // InternalKactors.g:1205:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:1205:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:1206:2: iv_ruleStatement= ruleStatement EOF
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
    // InternalKactors.g:1212:1: ruleStatement returns [EObject current=null] : ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token lv_text_2_0=null;
        EObject lv_verb_0_0 = null;

        EObject lv_group_1_0 = null;

        EObject lv_if_3_0 = null;

        EObject lv_while_4_0 = null;

        EObject lv_do_5_0 = null;

        EObject lv_for_6_0 = null;

        EObject lv_value_7_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1218:2: ( ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) ) )
            // InternalKactors.g:1219:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )
            {
            // InternalKactors.g:1219:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )
            int alt25=8;
            alt25 = dfa25.predict(input);
            switch (alt25) {
                case 1 :
                    // InternalKactors.g:1220:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1220:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    // InternalKactors.g:1221:4: (lv_verb_0_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1221:4: (lv_verb_0_0= ruleMessageCall )
                    // InternalKactors.g:1222:5: lv_verb_0_0= ruleMessageCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getVerbMessageCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_0_0=ruleMessageCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
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
                    // InternalKactors.g:1240:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1240:3: ( (lv_group_1_0= ruleStatementGroup ) )
                    // InternalKactors.g:1241:4: (lv_group_1_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1241:4: (lv_group_1_0= ruleStatementGroup )
                    // InternalKactors.g:1242:5: lv_group_1_0= ruleStatementGroup
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
                    // InternalKactors.g:1260:3: ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1260:3: ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1261:4: (lv_text_2_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1261:4: (lv_text_2_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1262:5: lv_text_2_0= RULE_EMBEDDEDTEXT
                    {
                    lv_text_2_0=(Token)match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_text_2_0, grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getStatementRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"text",
                      						lv_text_2_0,
                      						"org.integratedmodelling.kactors.Kactors.EMBEDDEDTEXT");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:1279:3: ( (lv_if_3_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1279:3: ( (lv_if_3_0= ruleIfStatement ) )
                    // InternalKactors.g:1280:4: (lv_if_3_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1280:4: (lv_if_3_0= ruleIfStatement )
                    // InternalKactors.g:1281:5: lv_if_3_0= ruleIfStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getIfIfStatementParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_if_3_0=ruleIfStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"if",
                      						lv_if_3_0,
                      						"org.integratedmodelling.kactors.Kactors.IfStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:1299:3: ( (lv_while_4_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1299:3: ( (lv_while_4_0= ruleWhileStatement ) )
                    // InternalKactors.g:1300:4: (lv_while_4_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1300:4: (lv_while_4_0= ruleWhileStatement )
                    // InternalKactors.g:1301:5: lv_while_4_0= ruleWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getWhileWhileStatementParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_while_4_0=ruleWhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"while",
                      						lv_while_4_0,
                      						"org.integratedmodelling.kactors.Kactors.WhileStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:1319:3: ( (lv_do_5_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1319:3: ( (lv_do_5_0= ruleDoStatement ) )
                    // InternalKactors.g:1320:4: (lv_do_5_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1320:4: (lv_do_5_0= ruleDoStatement )
                    // InternalKactors.g:1321:5: lv_do_5_0= ruleDoStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getDoDoStatementParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_do_5_0=ruleDoStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"do",
                      						lv_do_5_0,
                      						"org.integratedmodelling.kactors.Kactors.DoStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:1339:3: ( (lv_for_6_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1339:3: ( (lv_for_6_0= ruleForStatement ) )
                    // InternalKactors.g:1340:4: (lv_for_6_0= ruleForStatement )
                    {
                    // InternalKactors.g:1340:4: (lv_for_6_0= ruleForStatement )
                    // InternalKactors.g:1341:5: lv_for_6_0= ruleForStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getForForStatementParserRuleCall_6_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_for_6_0=ruleForStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"for",
                      						lv_for_6_0,
                      						"org.integratedmodelling.kactors.Kactors.ForStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:1359:3: ( (lv_value_7_0= ruleValue ) )
                    {
                    // InternalKactors.g:1359:3: ( (lv_value_7_0= ruleValue ) )
                    // InternalKactors.g:1360:4: (lv_value_7_0= ruleValue )
                    {
                    // InternalKactors.g:1360:4: (lv_value_7_0= ruleValue )
                    // InternalKactors.g:1361:5: lv_value_7_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getValueValueParserRuleCall_7_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_7_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"value",
                      						lv_value_7_0,
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
    // InternalKactors.g:1382:1: entryRuleNextStatement returns [EObject current=null] : iv_ruleNextStatement= ruleNextStatement EOF ;
    public final EObject entryRuleNextStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNextStatement = null;


        try {
            // InternalKactors.g:1382:54: (iv_ruleNextStatement= ruleNextStatement EOF )
            // InternalKactors.g:1383:2: iv_ruleNextStatement= ruleNextStatement EOF
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
    // InternalKactors.g:1389:1: ruleNextStatement returns [EObject current=null] : (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) ) ;
    public final EObject ruleNextStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_text_3_0=null;
        EObject lv_verb_1_0 = null;

        EObject lv_group_2_0 = null;

        EObject lv_if_4_0 = null;

        EObject lv_while_5_0 = null;

        EObject lv_do_6_0 = null;

        EObject lv_for_7_0 = null;

        EObject lv_value_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1395:2: ( (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) ) )
            // InternalKactors.g:1396:2: (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) )
            {
            // InternalKactors.g:1396:2: (otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) ) )
            // InternalKactors.g:1397:3: otherlv_0= ',' ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            {
            otherlv_0=(Token)match(input,29,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getNextStatementAccess().getCommaKeyword_0());
              		
            }
            // InternalKactors.g:1401:3: ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )
            int alt26=8;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // InternalKactors.g:1402:4: ( (lv_verb_1_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1402:4: ( (lv_verb_1_0= ruleMessageCall ) )
                    // InternalKactors.g:1403:5: (lv_verb_1_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1403:5: (lv_verb_1_0= ruleMessageCall )
                    // InternalKactors.g:1404:6: lv_verb_1_0= ruleMessageCall
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getVerbMessageCallParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_verb_1_0=ruleMessageCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
                      						}
                      						set(
                      							current,
                      							"verb",
                      							lv_verb_1_0,
                      							"org.integratedmodelling.kactors.Kactors.MessageCall");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:1422:4: ( (lv_group_2_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1422:4: ( (lv_group_2_0= ruleStatementGroup ) )
                    // InternalKactors.g:1423:5: (lv_group_2_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1423:5: (lv_group_2_0= ruleStatementGroup )
                    // InternalKactors.g:1424:6: lv_group_2_0= ruleStatementGroup
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getGroupStatementGroupParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_group_2_0=ruleStatementGroup();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
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
                case 3 :
                    // InternalKactors.g:1442:4: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1442:4: ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1443:5: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1443:5: (lv_text_3_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1444:6: lv_text_3_0= RULE_EMBEDDEDTEXT
                    {
                    lv_text_3_0=(Token)match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_text_3_0, grammarAccess.getNextStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getNextStatementRule());
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
                case 4 :
                    // InternalKactors.g:1461:4: ( (lv_if_4_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1461:4: ( (lv_if_4_0= ruleIfStatement ) )
                    // InternalKactors.g:1462:5: (lv_if_4_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1462:5: (lv_if_4_0= ruleIfStatement )
                    // InternalKactors.g:1463:6: lv_if_4_0= ruleIfStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getIfIfStatementParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_if_4_0=ruleIfStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
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
                case 5 :
                    // InternalKactors.g:1481:4: ( (lv_while_5_0= ruleWhileStatement ) )
                    {
                    // InternalKactors.g:1481:4: ( (lv_while_5_0= ruleWhileStatement ) )
                    // InternalKactors.g:1482:5: (lv_while_5_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:1482:5: (lv_while_5_0= ruleWhileStatement )
                    // InternalKactors.g:1483:6: lv_while_5_0= ruleWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getWhileWhileStatementParserRuleCall_1_4_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_while_5_0=ruleWhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
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
                case 6 :
                    // InternalKactors.g:1501:4: ( (lv_do_6_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:1501:4: ( (lv_do_6_0= ruleDoStatement ) )
                    // InternalKactors.g:1502:5: (lv_do_6_0= ruleDoStatement )
                    {
                    // InternalKactors.g:1502:5: (lv_do_6_0= ruleDoStatement )
                    // InternalKactors.g:1503:6: lv_do_6_0= ruleDoStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getDoDoStatementParserRuleCall_1_5_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_do_6_0=ruleDoStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
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
                case 7 :
                    // InternalKactors.g:1521:4: ( (lv_for_7_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:1521:4: ( (lv_for_7_0= ruleForStatement ) )
                    // InternalKactors.g:1522:5: (lv_for_7_0= ruleForStatement )
                    {
                    // InternalKactors.g:1522:5: (lv_for_7_0= ruleForStatement )
                    // InternalKactors.g:1523:6: lv_for_7_0= ruleForStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getForForStatementParserRuleCall_1_6_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_for_7_0=ruleForStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
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
                case 8 :
                    // InternalKactors.g:1541:4: ( (lv_value_8_0= ruleValue ) )
                    {
                    // InternalKactors.g:1541:4: ( (lv_value_8_0= ruleValue ) )
                    // InternalKactors.g:1542:5: (lv_value_8_0= ruleValue )
                    {
                    // InternalKactors.g:1542:5: (lv_value_8_0= ruleValue )
                    // InternalKactors.g:1543:6: lv_value_8_0= ruleValue
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getNextStatementAccess().getValueValueParserRuleCall_1_7_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_value_8_0=ruleValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getNextStatementRule());
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


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
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


    // $ANTLR start "entryRuleIfStatement"
    // InternalKactors.g:1565:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:1565:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:1566:2: iv_ruleIfStatement= ruleIfStatement EOF
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
    // InternalKactors.g:1572:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) ;
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
            // InternalKactors.g:1578:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? ) )
            // InternalKactors.g:1579:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            {
            // InternalKactors.g:1579:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )? )
            // InternalKactors.g:1580:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            {
            otherlv_0=(Token)match(input,43,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:1584:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1585:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1585:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1586:5: lv_expression_1_0= RULE_EXPR
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

            // InternalKactors.g:1602:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1603:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1603:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1604:5: lv_body_2_0= ruleStatementBody
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

            // InternalKactors.g:1621:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==44) ) {
                    int LA27_1 = input.LA(2);

                    if ( (synpred51_InternalKactors()) ) {
                        alt27=1;
                    }


                }


                switch (alt27) {
            	case 1 :
            	    // InternalKactors.g:1622:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    {
            	    otherlv_3=(Token)match(input,44,FOLLOW_27); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,43,FOLLOW_25); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:1630:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:1631:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:1631:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:1632:6: lv_elseIfExpression_5_0= RULE_EXPR
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

            	    // InternalKactors.g:1648:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
            	    // InternalKactors.g:1649:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    {
            	    // InternalKactors.g:1649:5: (lv_elseIfBody_6_0= ruleStatementBody )
            	    // InternalKactors.g:1650:6: lv_elseIfBody_6_0= ruleStatementBody
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
            	    break loop27;
                }
            } while (true);

            // InternalKactors.g:1668:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==44) ) {
                int LA28_1 = input.LA(2);

                if ( (synpred52_InternalKactors()) ) {
                    alt28=1;
                }
            }
            switch (alt28) {
                case 1 :
                    // InternalKactors.g:1669:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
                    {
                    otherlv_7=(Token)match(input,44,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:1673:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
                    // InternalKactors.g:1674:5: (lv_elseCall_8_0= ruleStatementBody )
                    {
                    // InternalKactors.g:1674:5: (lv_elseCall_8_0= ruleStatementBody )
                    // InternalKactors.g:1675:6: lv_elseCall_8_0= ruleStatementBody
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
    // InternalKactors.g:1697:1: entryRuleStatementBody returns [EObject current=null] : iv_ruleStatementBody= ruleStatementBody EOF ;
    public final EObject entryRuleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatementBody = null;


        try {
            // InternalKactors.g:1697:54: (iv_ruleStatementBody= ruleStatementBody EOF )
            // InternalKactors.g:1698:2: iv_ruleStatementBody= ruleStatementBody EOF
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
    // InternalKactors.g:1704:1: ruleStatementBody returns [EObject current=null] : ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) ;
    public final EObject ruleStatementBody() throws RecognitionException {
        EObject current = null;

        EObject lv_verb_0_0 = null;

        EObject lv_value_1_0 = null;

        EObject lv_group_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1710:2: ( ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) ) )
            // InternalKactors.g:1711:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            {
            // InternalKactors.g:1711:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )
            int alt29=3;
            alt29 = dfa29.predict(input);
            switch (alt29) {
                case 1 :
                    // InternalKactors.g:1712:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    {
                    // InternalKactors.g:1712:3: ( (lv_verb_0_0= ruleMessageCall ) )
                    // InternalKactors.g:1713:4: (lv_verb_0_0= ruleMessageCall )
                    {
                    // InternalKactors.g:1713:4: (lv_verb_0_0= ruleMessageCall )
                    // InternalKactors.g:1714:5: lv_verb_0_0= ruleMessageCall
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
                    // InternalKactors.g:1732:3: ( (lv_value_1_0= ruleValue ) )
                    {
                    // InternalKactors.g:1732:3: ( (lv_value_1_0= ruleValue ) )
                    // InternalKactors.g:1733:4: (lv_value_1_0= ruleValue )
                    {
                    // InternalKactors.g:1733:4: (lv_value_1_0= ruleValue )
                    // InternalKactors.g:1734:5: lv_value_1_0= ruleValue
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
                    // InternalKactors.g:1752:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    {
                    // InternalKactors.g:1752:3: ( (lv_group_2_0= ruleStatementGroup ) )
                    // InternalKactors.g:1753:4: (lv_group_2_0= ruleStatementGroup )
                    {
                    // InternalKactors.g:1753:4: (lv_group_2_0= ruleStatementGroup )
                    // InternalKactors.g:1754:5: lv_group_2_0= ruleStatementGroup
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
    // InternalKactors.g:1775:1: entryRuleWhileStatement returns [EObject current=null] : iv_ruleWhileStatement= ruleWhileStatement EOF ;
    public final EObject entryRuleWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhileStatement = null;


        try {
            // InternalKactors.g:1775:55: (iv_ruleWhileStatement= ruleWhileStatement EOF )
            // InternalKactors.g:1776:2: iv_ruleWhileStatement= ruleWhileStatement EOF
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
    // InternalKactors.g:1782:1: ruleWhileStatement returns [EObject current=null] : (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) ;
    public final EObject ruleWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1788:2: ( (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:1789:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:1789:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) ) )
            // InternalKactors.g:1790:3: otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,45,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
              		
            }
            // InternalKactors.g:1794:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1795:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1795:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1796:5: lv_expression_1_0= RULE_EXPR
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

            // InternalKactors.g:1812:3: ( (lv_body_2_0= ruleStatementBody ) )
            // InternalKactors.g:1813:4: (lv_body_2_0= ruleStatementBody )
            {
            // InternalKactors.g:1813:4: (lv_body_2_0= ruleStatementBody )
            // InternalKactors.g:1814:5: lv_body_2_0= ruleStatementBody
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
    // InternalKactors.g:1835:1: entryRuleDoStatement returns [EObject current=null] : iv_ruleDoStatement= ruleDoStatement EOF ;
    public final EObject entryRuleDoStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoStatement = null;


        try {
            // InternalKactors.g:1835:52: (iv_ruleDoStatement= ruleDoStatement EOF )
            // InternalKactors.g:1836:2: iv_ruleDoStatement= ruleDoStatement EOF
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
    // InternalKactors.g:1842:1: ruleDoStatement returns [EObject current=null] : (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) ;
    public final EObject ruleDoStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_expression_3_0=null;
        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1848:2: ( (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) )
            // InternalKactors.g:1849:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            {
            // InternalKactors.g:1849:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            // InternalKactors.g:1850:3: otherlv_0= 'do' ( (lv_body_1_0= ruleStatementBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) )
            {
            otherlv_0=(Token)match(input,46,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
              		
            }
            // InternalKactors.g:1854:3: ( (lv_body_1_0= ruleStatementBody ) )
            // InternalKactors.g:1855:4: (lv_body_1_0= ruleStatementBody )
            {
            // InternalKactors.g:1855:4: (lv_body_1_0= ruleStatementBody )
            // InternalKactors.g:1856:5: lv_body_1_0= ruleStatementBody
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

            otherlv_2=(Token)match(input,45,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
              		
            }
            // InternalKactors.g:1877:3: ( (lv_expression_3_0= RULE_EXPR ) )
            // InternalKactors.g:1878:4: (lv_expression_3_0= RULE_EXPR )
            {
            // InternalKactors.g:1878:4: (lv_expression_3_0= RULE_EXPR )
            // InternalKactors.g:1879:5: lv_expression_3_0= RULE_EXPR
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
    // InternalKactors.g:1899:1: entryRuleForStatement returns [EObject current=null] : iv_ruleForStatement= ruleForStatement EOF ;
    public final EObject entryRuleForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForStatement = null;


        try {
            // InternalKactors.g:1899:53: (iv_ruleForStatement= ruleForStatement EOF )
            // InternalKactors.g:1900:2: iv_ruleForStatement= ruleForStatement EOF
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
    // InternalKactors.g:1906:1: ruleForStatement returns [EObject current=null] : (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) ;
    public final EObject ruleForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_id_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;

        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1912:2: ( (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) ) )
            // InternalKactors.g:1913:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            {
            // InternalKactors.g:1913:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) ) )
            // InternalKactors.g:1914:3: otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleStatementBody ) )
            {
            otherlv_0=(Token)match(input,47,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getForStatementAccess().getForKeyword_0());
              		
            }
            // InternalKactors.g:1918:3: ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_LOWERCASE_ID) ) {
                int LA30_1 = input.LA(2);

                if ( (LA30_1==48) ) {
                    alt30=1;
                }
            }
            switch (alt30) {
                case 1 :
                    // InternalKactors.g:1919:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in'
                    {
                    // InternalKactors.g:1919:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:1920:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:1920:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:1921:6: lv_id_1_0= RULE_LOWERCASE_ID
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

                    otherlv_2=(Token)match(input,48,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getInKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1942:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:1943:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:1943:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:1944:5: lv_value_3_0= ruleValue
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

            // InternalKactors.g:1961:3: ( (lv_body_4_0= ruleStatementBody ) )
            // InternalKactors.g:1962:4: (lv_body_4_0= ruleStatementBody )
            {
            // InternalKactors.g:1962:4: (lv_body_4_0= ruleStatementBody )
            // InternalKactors.g:1963:5: lv_body_4_0= ruleStatementBody
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
    // InternalKactors.g:1984:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:1984:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:1985:2: iv_ruleActions= ruleActions EOF
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
    // InternalKactors.g:1991:1: ruleActions returns [EObject current=null] : ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) ;
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
            // InternalKactors.g:1997:2: ( ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) ) )
            // InternalKactors.g:1998:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            {
            // InternalKactors.g:1998:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )
            int alt32=4;
            alt32 = dfa32.predict(input);
            switch (alt32) {
                case 1 :
                    // InternalKactors.g:1999:3: ( (lv_match_0_0= ruleMatch ) )
                    {
                    // InternalKactors.g:1999:3: ( (lv_match_0_0= ruleMatch ) )
                    // InternalKactors.g:2000:4: (lv_match_0_0= ruleMatch )
                    {
                    // InternalKactors.g:2000:4: (lv_match_0_0= ruleMatch )
                    // InternalKactors.g:2001:5: lv_match_0_0= ruleMatch
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
                    // InternalKactors.g:2019:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    {
                    // InternalKactors.g:2019:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
                    // InternalKactors.g:2020:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
                    {
                    otherlv_1=(Token)match(input,41,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:2024:4: ( (lv_matches_2_0= ruleMatch ) )
                    // InternalKactors.g:2025:5: (lv_matches_2_0= ruleMatch )
                    {
                    // InternalKactors.g:2025:5: (lv_matches_2_0= ruleMatch )
                    // InternalKactors.g:2026:6: lv_matches_2_0= ruleMatch
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

                    // InternalKactors.g:2043:4: ( (lv_matches_3_0= ruleMatch ) )*
                    loop31:
                    do {
                        int alt31=2;
                        int LA31_0 = input.LA(1);

                        if ( ((LA31_0>=RULE_OBSERVABLE && LA31_0<=RULE_LOWERCASE_ID)||LA31_0==RULE_STRING||(LA31_0>=RULE_EXPR && LA31_0<=RULE_INT)||LA31_0==41||LA31_0==48||(LA31_0>=50 && LA31_0<=51)||(LA31_0>=55 && LA31_0<=57)||(LA31_0>=75 && LA31_0<=76)) ) {
                            alt31=1;
                        }


                        switch (alt31) {
                    	case 1 :
                    	    // InternalKactors.g:2044:5: (lv_matches_3_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:2044:5: (lv_matches_3_0= ruleMatch )
                    	    // InternalKactors.g:2045:6: lv_matches_3_0= ruleMatch
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
                    	    break loop31;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getActionsAccess().getRightParenthesisKeyword_1_3());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:2068:3: ( (lv_statement_5_0= ruleStatement ) )
                    {
                    // InternalKactors.g:2068:3: ( (lv_statement_5_0= ruleStatement ) )
                    // InternalKactors.g:2069:4: (lv_statement_5_0= ruleStatement )
                    {
                    // InternalKactors.g:2069:4: (lv_statement_5_0= ruleStatement )
                    // InternalKactors.g:2070:5: lv_statement_5_0= ruleStatement
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
                    // InternalKactors.g:2088:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    {
                    // InternalKactors.g:2088:3: (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' )
                    // InternalKactors.g:2089:4: otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')'
                    {
                    otherlv_6=(Token)match(input,41,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:2093:4: ( (lv_statements_7_0= ruleStatementList ) )
                    // InternalKactors.g:2094:5: (lv_statements_7_0= ruleStatementList )
                    {
                    // InternalKactors.g:2094:5: (lv_statements_7_0= ruleStatementList )
                    // InternalKactors.g:2095:6: lv_statements_7_0= ruleStatementList
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

                    otherlv_8=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleMatch"
    // InternalKactors.g:2121:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:2121:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:2122:2: iv_ruleMatch= ruleMatch EOF
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
    // InternalKactors.g:2128:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) ) ;
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
        Token lv_text_18_0=null;
        Token otherlv_19=null;
        Token otherlv_22=null;
        Token lv_leftLimit_25_0=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Token lv_rightLimit_29_0=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        Token otherlv_33=null;
        Token otherlv_35=null;
        Token otherlv_38=null;
        Token otherlv_41=null;
        Token lv_expr_43_0=null;
        Token otherlv_44=null;
        Token lv_nodata_46_0=null;
        Token otherlv_47=null;
        Token lv_star_49_0=null;
        Token otherlv_50=null;
        Token lv_anything_52_0=null;
        Token otherlv_53=null;
        EObject lv_body_2_0 = null;

        EObject lv_body_5_0 = null;

        EObject lv_body_8_0 = null;

        EObject lv_body_11_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;

        EObject lv_body_20_0 = null;

        EObject lv_arguments_21_0 = null;

        EObject lv_body_23_0 = null;

        EObject lv_int0_24_0 = null;

        EObject lv_int1_28_0 = null;

        EObject lv_body_32_0 = null;

        EObject lv_set_34_0 = null;

        EObject lv_body_36_0 = null;

        EObject lv_quantity_37_0 = null;

        EObject lv_body_39_0 = null;

        EObject lv_date_40_0 = null;

        EObject lv_body_42_0 = null;

        EObject lv_body_45_0 = null;

        EObject lv_body_48_0 = null;

        EObject lv_body_51_0 = null;

        EObject lv_body_54_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2134:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) ) )
            // InternalKactors.g:2135:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )
            {
            // InternalKactors.g:2135:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )
            int alt36=16;
            alt36 = dfa36.predict(input);
            switch (alt36) {
                case 1 :
                    // InternalKactors.g:2136:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2136:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) )
                    // InternalKactors.g:2137:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2137:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:2138:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:2138:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:2139:6: lv_id_0_0= RULE_LOWERCASE_ID
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
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      					
                    }

                    }


                    }

                    otherlv_1=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:2159:4: ( (lv_body_2_0= ruleStatementList ) )
                    // InternalKactors.g:2160:5: (lv_body_2_0= ruleStatementList )
                    {
                    // InternalKactors.g:2160:5: (lv_body_2_0= ruleStatementList )
                    // InternalKactors.g:2161:6: lv_body_2_0= ruleStatementList
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
                    // InternalKactors.g:2180:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2180:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
                    // InternalKactors.g:2181:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2181:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
                    // InternalKactors.g:2182:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    {
                    // InternalKactors.g:2182:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
                    // InternalKactors.g:2183:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    {
                    // InternalKactors.g:2183:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==50) ) {
                        alt33=1;
                    }
                    else if ( (LA33_0==51) ) {
                        alt33=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 33, 0, input);

                        throw nvae;
                    }
                    switch (alt33) {
                        case 1 :
                            // InternalKactors.g:2184:7: lv_boolean_3_1= 'true'
                            {
                            lv_boolean_3_1=(Token)match(input,50,FOLLOW_32); if (state.failed) return current;
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
                            // InternalKactors.g:2195:7: lv_boolean_3_2= 'false'
                            {
                            lv_boolean_3_2=(Token)match(input,51,FOLLOW_32); if (state.failed) return current;
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

                    otherlv_4=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:2212:4: ( (lv_body_5_0= ruleStatementList ) )
                    // InternalKactors.g:2213:5: (lv_body_5_0= ruleStatementList )
                    {
                    // InternalKactors.g:2213:5: (lv_body_5_0= ruleStatementList )
                    // InternalKactors.g:2214:6: lv_body_5_0= ruleStatementList
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
                    // InternalKactors.g:2233:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2233:3: ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) )
                    // InternalKactors.g:2234:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2234:4: ( (lv_type_6_0= RULE_CAMELCASE_ID ) )
                    // InternalKactors.g:2235:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    {
                    // InternalKactors.g:2235:5: (lv_type_6_0= RULE_CAMELCASE_ID )
                    // InternalKactors.g:2236:6: lv_type_6_0= RULE_CAMELCASE_ID
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
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.CAMELCASE_ID");
                      					
                    }

                    }


                    }

                    otherlv_7=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:2256:4: ( (lv_body_8_0= ruleStatementList ) )
                    // InternalKactors.g:2257:5: (lv_body_8_0= ruleStatementList )
                    {
                    // InternalKactors.g:2257:5: (lv_body_8_0= ruleStatementList )
                    // InternalKactors.g:2258:6: lv_body_8_0= ruleStatementList
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
                    // InternalKactors.g:2277:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2277:3: ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) )
                    // InternalKactors.g:2278:4: ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2278:4: ( (lv_regexp_9_0= RULE_REGEXP ) )
                    // InternalKactors.g:2279:5: (lv_regexp_9_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:2279:5: (lv_regexp_9_0= RULE_REGEXP )
                    // InternalKactors.g:2280:6: lv_regexp_9_0= RULE_REGEXP
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
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.REGEXP");
                      					
                    }

                    }


                    }

                    otherlv_10=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:2300:4: ( (lv_body_11_0= ruleStatementList ) )
                    // InternalKactors.g:2301:5: (lv_body_11_0= ruleStatementList )
                    {
                    // InternalKactors.g:2301:5: (lv_body_11_0= ruleStatementList )
                    // InternalKactors.g:2302:6: lv_body_11_0= ruleStatementList
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
                    // InternalKactors.g:2321:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2321:3: ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) )
                    // InternalKactors.g:2322:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2322:4: ( (lv_observable_12_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2323:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2323:5: (lv_observable_12_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2324:6: lv_observable_12_0= RULE_OBSERVABLE
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
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      					
                    }

                    }


                    }

                    otherlv_13=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:2344:4: ( (lv_body_14_0= ruleStatementList ) )
                    // InternalKactors.g:2345:5: (lv_body_14_0= ruleStatementList )
                    {
                    // InternalKactors.g:2345:5: (lv_body_14_0= ruleStatementList )
                    // InternalKactors.g:2346:6: lv_body_14_0= ruleStatementList
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
                    // InternalKactors.g:2365:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2365:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
                    // InternalKactors.g:2366:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2366:4: ( (lv_literal_15_0= ruleLiteral ) )
                    // InternalKactors.g:2367:5: (lv_literal_15_0= ruleLiteral )
                    {
                    // InternalKactors.g:2367:5: (lv_literal_15_0= ruleLiteral )
                    // InternalKactors.g:2368:6: lv_literal_15_0= ruleLiteral
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
                      							true,
                      							"org.integratedmodelling.kactors.Kactors.Literal");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_16=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:2389:4: ( (lv_body_17_0= ruleStatementList ) )
                    // InternalKactors.g:2390:5: (lv_body_17_0= ruleStatementList )
                    {
                    // InternalKactors.g:2390:5: (lv_body_17_0= ruleStatementList )
                    // InternalKactors.g:2391:6: lv_body_17_0= ruleStatementList
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
                    // InternalKactors.g:2410:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2410:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
                    // InternalKactors.g:2411:4: ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2411:4: ( (lv_text_18_0= RULE_STRING ) )
                    // InternalKactors.g:2412:5: (lv_text_18_0= RULE_STRING )
                    {
                    // InternalKactors.g:2412:5: (lv_text_18_0= RULE_STRING )
                    // InternalKactors.g:2413:6: lv_text_18_0= RULE_STRING
                    {
                    lv_text_18_0=(Token)match(input,RULE_STRING,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_text_18_0, grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_6_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"text",
                      							true,
                      							"org.eclipse.xtext.common.Terminals.STRING");
                      					
                    }

                    }


                    }

                    otherlv_19=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_19, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:2433:4: ( (lv_body_20_0= ruleStatementList ) )
                    // InternalKactors.g:2434:5: (lv_body_20_0= ruleStatementList )
                    {
                    // InternalKactors.g:2434:5: (lv_body_20_0= ruleStatementList )
                    // InternalKactors.g:2435:6: lv_body_20_0= ruleStatementList
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
                    // InternalKactors.g:2454:3: ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2454:3: ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) )
                    // InternalKactors.g:2455:4: ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2455:4: ( (lv_arguments_21_0= ruleArgumentDeclaration ) )
                    // InternalKactors.g:2456:5: (lv_arguments_21_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:2456:5: (lv_arguments_21_0= ruleArgumentDeclaration )
                    // InternalKactors.g:2457:6: lv_arguments_21_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_arguments_21_0=ruleArgumentDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"arguments",
                      							lv_arguments_21_0,
                      							"org.integratedmodelling.kactors.Kactors.ArgumentDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_22=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_22, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_7_1());
                      			
                    }
                    // InternalKactors.g:2478:4: ( (lv_body_23_0= ruleStatementList ) )
                    // InternalKactors.g:2479:5: (lv_body_23_0= ruleStatementList )
                    {
                    // InternalKactors.g:2479:5: (lv_body_23_0= ruleStatementList )
                    // InternalKactors.g:2480:6: lv_body_23_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_7_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_23_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_23_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:2499:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2499:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
                    // InternalKactors.g:2500:4: ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2500:4: ( (lv_int0_24_0= ruleNumber ) )
                    // InternalKactors.g:2501:5: (lv_int0_24_0= ruleNumber )
                    {
                    // InternalKactors.g:2501:5: (lv_int0_24_0= ruleNumber )
                    // InternalKactors.g:2502:6: lv_int0_24_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getInt0NumberParserRuleCall_8_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
                    lv_int0_24_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_24_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2519:4: ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )?
                    int alt34=3;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==52) ) {
                        alt34=1;
                    }
                    else if ( (LA34_0==53) ) {
                        alt34=2;
                    }
                    switch (alt34) {
                        case 1 :
                            // InternalKactors.g:2520:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2520:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                            // InternalKactors.g:2521:6: (lv_leftLimit_25_0= 'inclusive' )
                            {
                            // InternalKactors.g:2521:6: (lv_leftLimit_25_0= 'inclusive' )
                            // InternalKactors.g:2522:7: lv_leftLimit_25_0= 'inclusive'
                            {
                            lv_leftLimit_25_0=(Token)match(input,52,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_25_0, grammarAccess.getMatchAccess().getLeftLimitInclusiveKeyword_8_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_25_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2535:5: otherlv_26= 'exclusive'
                            {
                            otherlv_26=(Token)match(input,53,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_26, grammarAccess.getMatchAccess().getExclusiveKeyword_8_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:2540:4: ( ( 'to' )=>otherlv_27= 'to' )
                    // InternalKactors.g:2541:5: ( 'to' )=>otherlv_27= 'to'
                    {
                    otherlv_27=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_27, grammarAccess.getMatchAccess().getToKeyword_8_2());
                      				
                    }

                    }

                    // InternalKactors.g:2547:4: ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) )
                    // InternalKactors.g:2548:5: ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber )
                    {
                    // InternalKactors.g:2552:5: (lv_int1_28_0= ruleNumber )
                    // InternalKactors.g:2553:6: lv_int1_28_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getInt1NumberParserRuleCall_8_3_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
                    lv_int1_28_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_28_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:2570:4: ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )?
                    int alt35=3;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==52) ) {
                        alt35=1;
                    }
                    else if ( (LA35_0==53) ) {
                        alt35=2;
                    }
                    switch (alt35) {
                        case 1 :
                            // InternalKactors.g:2571:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2571:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                            // InternalKactors.g:2572:6: (lv_rightLimit_29_0= 'inclusive' )
                            {
                            // InternalKactors.g:2572:6: (lv_rightLimit_29_0= 'inclusive' )
                            // InternalKactors.g:2573:7: lv_rightLimit_29_0= 'inclusive'
                            {
                            lv_rightLimit_29_0=(Token)match(input,52,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_29_0, grammarAccess.getMatchAccess().getRightLimitInclusiveKeyword_8_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_29_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2586:5: otherlv_30= 'exclusive'
                            {
                            otherlv_30=(Token)match(input,53,FOLLOW_32); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_30, grammarAccess.getMatchAccess().getExclusiveKeyword_8_4_1());
                              				
                            }

                            }
                            break;

                    }

                    otherlv_31=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_31, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_8_5());
                      			
                    }
                    // InternalKactors.g:2595:4: ( (lv_body_32_0= ruleStatementList ) )
                    // InternalKactors.g:2596:5: (lv_body_32_0= ruleStatementList )
                    {
                    // InternalKactors.g:2596:5: (lv_body_32_0= ruleStatementList )
                    // InternalKactors.g:2597:6: lv_body_32_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_8_6_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_32_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_32_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:2616:3: (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2616:3: (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) )
                    // InternalKactors.g:2617:4: otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) )
                    {
                    otherlv_33=(Token)match(input,48,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_33, grammarAccess.getMatchAccess().getInKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:2621:4: ( (lv_set_34_0= ruleList ) )
                    // InternalKactors.g:2622:5: (lv_set_34_0= ruleList )
                    {
                    // InternalKactors.g:2622:5: (lv_set_34_0= ruleList )
                    // InternalKactors.g:2623:6: lv_set_34_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getSetListParserRuleCall_9_1_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_set_34_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_34_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_35=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_9_2());
                      			
                    }
                    // InternalKactors.g:2644:4: ( (lv_body_36_0= ruleStatementList ) )
                    // InternalKactors.g:2645:5: (lv_body_36_0= ruleStatementList )
                    {
                    // InternalKactors.g:2645:5: (lv_body_36_0= ruleStatementList )
                    // InternalKactors.g:2646:6: lv_body_36_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_9_3_0());
                      					
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
                case 11 :
                    // InternalKactors.g:2665:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2665:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
                    // InternalKactors.g:2666:4: ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2666:4: ( (lv_quantity_37_0= ruleQuantity ) )
                    // InternalKactors.g:2667:5: (lv_quantity_37_0= ruleQuantity )
                    {
                    // InternalKactors.g:2667:5: (lv_quantity_37_0= ruleQuantity )
                    // InternalKactors.g:2668:6: lv_quantity_37_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_10_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_quantity_37_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"quantity",
                      							lv_quantity_37_0,
                      							"org.integratedmodelling.kactors.Kactors.Quantity");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_38=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_38, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_10_1());
                      			
                    }
                    // InternalKactors.g:2689:4: ( (lv_body_39_0= ruleStatementList ) )
                    // InternalKactors.g:2690:5: (lv_body_39_0= ruleStatementList )
                    {
                    // InternalKactors.g:2690:5: (lv_body_39_0= ruleStatementList )
                    // InternalKactors.g:2691:6: lv_body_39_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_10_2_0());
                      					
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
                case 12 :
                    // InternalKactors.g:2710:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2710:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
                    // InternalKactors.g:2711:4: ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2711:4: ( (lv_date_40_0= ruleDate ) )
                    // InternalKactors.g:2712:5: (lv_date_40_0= ruleDate )
                    {
                    // InternalKactors.g:2712:5: (lv_date_40_0= ruleDate )
                    // InternalKactors.g:2713:6: lv_date_40_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getDateDateParserRuleCall_11_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_date_40_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"date",
                      							lv_date_40_0,
                      							"org.integratedmodelling.kactors.Kactors.Date");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_41=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_41, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_11_1());
                      			
                    }
                    // InternalKactors.g:2734:4: ( (lv_body_42_0= ruleStatementList ) )
                    // InternalKactors.g:2735:5: (lv_body_42_0= ruleStatementList )
                    {
                    // InternalKactors.g:2735:5: (lv_body_42_0= ruleStatementList )
                    // InternalKactors.g:2736:6: lv_body_42_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_11_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_42_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_42_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 13 :
                    // InternalKactors.g:2755:3: ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2755:3: ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) )
                    // InternalKactors.g:2756:4: ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2756:4: ( (lv_expr_43_0= RULE_EXPR ) )
                    // InternalKactors.g:2757:5: (lv_expr_43_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2757:5: (lv_expr_43_0= RULE_EXPR )
                    // InternalKactors.g:2758:6: lv_expr_43_0= RULE_EXPR
                    {
                    lv_expr_43_0=(Token)match(input,RULE_EXPR,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_expr_43_0, grammarAccess.getMatchAccess().getExprEXPRTerminalRuleCall_12_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"expr",
                      							lv_expr_43_0,
                      							"org.integratedmodelling.kactors.Kactors.EXPR");
                      					
                    }

                    }


                    }

                    otherlv_44=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_44, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_12_1());
                      			
                    }
                    // InternalKactors.g:2778:4: ( (lv_body_45_0= ruleStatementList ) )
                    // InternalKactors.g:2779:5: (lv_body_45_0= ruleStatementList )
                    {
                    // InternalKactors.g:2779:5: (lv_body_45_0= ruleStatementList )
                    // InternalKactors.g:2780:6: lv_body_45_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_12_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_45_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_45_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 14 :
                    // InternalKactors.g:2799:3: ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2799:3: ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) )
                    // InternalKactors.g:2800:4: ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2800:4: ( (lv_nodata_46_0= 'unknown' ) )
                    // InternalKactors.g:2801:5: (lv_nodata_46_0= 'unknown' )
                    {
                    // InternalKactors.g:2801:5: (lv_nodata_46_0= 'unknown' )
                    // InternalKactors.g:2802:6: lv_nodata_46_0= 'unknown'
                    {
                    lv_nodata_46_0=(Token)match(input,55,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nodata_46_0, grammarAccess.getMatchAccess().getNodataUnknownKeyword_13_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "nodata", lv_nodata_46_0, "unknown");
                      					
                    }

                    }


                    }

                    otherlv_47=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_47, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_13_1());
                      			
                    }
                    // InternalKactors.g:2818:4: ( (lv_body_48_0= ruleStatementList ) )
                    // InternalKactors.g:2819:5: (lv_body_48_0= ruleStatementList )
                    {
                    // InternalKactors.g:2819:5: (lv_body_48_0= ruleStatementList )
                    // InternalKactors.g:2820:6: lv_body_48_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_13_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_48_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_48_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 15 :
                    // InternalKactors.g:2839:3: ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2839:3: ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) )
                    // InternalKactors.g:2840:4: ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2840:4: ( (lv_star_49_0= '*' ) )
                    // InternalKactors.g:2841:5: (lv_star_49_0= '*' )
                    {
                    // InternalKactors.g:2841:5: (lv_star_49_0= '*' )
                    // InternalKactors.g:2842:6: lv_star_49_0= '*'
                    {
                    lv_star_49_0=(Token)match(input,56,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_star_49_0, grammarAccess.getMatchAccess().getStarAsteriskKeyword_14_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "star", true, "*");
                      					
                    }

                    }


                    }

                    otherlv_50=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_50, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_14_1());
                      			
                    }
                    // InternalKactors.g:2858:4: ( (lv_body_51_0= ruleStatementList ) )
                    // InternalKactors.g:2859:5: (lv_body_51_0= ruleStatementList )
                    {
                    // InternalKactors.g:2859:5: (lv_body_51_0= ruleStatementList )
                    // InternalKactors.g:2860:6: lv_body_51_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_14_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_51_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_51_0,
                      							"org.integratedmodelling.kactors.Kactors.StatementList");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 16 :
                    // InternalKactors.g:2879:3: ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) )
                    {
                    // InternalKactors.g:2879:3: ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) )
                    // InternalKactors.g:2880:4: ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) )
                    {
                    // InternalKactors.g:2880:4: ( (lv_anything_52_0= '#' ) )
                    // InternalKactors.g:2881:5: (lv_anything_52_0= '#' )
                    {
                    // InternalKactors.g:2881:5: (lv_anything_52_0= '#' )
                    // InternalKactors.g:2882:6: lv_anything_52_0= '#'
                    {
                    lv_anything_52_0=(Token)match(input,57,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_anything_52_0, grammarAccess.getMatchAccess().getAnythingNumberSignKeyword_15_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "anything", true, "#");
                      					
                    }

                    }


                    }

                    otherlv_53=(Token)match(input,49,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_53, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_15_1());
                      			
                    }
                    // InternalKactors.g:2898:4: ( (lv_body_54_0= ruleStatementList ) )
                    // InternalKactors.g:2899:5: (lv_body_54_0= ruleStatementList )
                    {
                    // InternalKactors.g:2899:5: (lv_body_54_0= ruleStatementList )
                    // InternalKactors.g:2900:6: lv_body_54_0= ruleStatementList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_15_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_54_0=ruleStatementList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_54_0,
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
    // InternalKactors.g:2922:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKactors.g:2922:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKactors.g:2923:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKactors.g:2929:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) ;
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
            // InternalKactors.g:2935:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) )
            // InternalKactors.g:2936:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            {
            // InternalKactors.g:2936:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            // InternalKactors.g:2937:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            {
            // InternalKactors.g:2937:3: (kw= 'urn:klab:' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==58) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalKactors.g:2938:4: kw= 'urn:klab:'
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
            kw=(Token)match(input,40,FOLLOW_4); if (state.failed) return current;
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
            kw=(Token)match(input,40,FOLLOW_4); if (state.failed) return current;
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
            kw=(Token)match(input,40,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            // InternalKactors.g:2989:3: (this_Path_7= rulePath | this_INT_8= RULE_INT )
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
                    // InternalKactors.g:2990:4: this_Path_7= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7_0());
                      			
                    }
                    pushFollow(FOLLOW_38);
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
                    // InternalKactors.g:3001:4: this_INT_8= RULE_INT
                    {
                    this_INT_8=(Token)match(input,RULE_INT,FOLLOW_38); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_8, grammarAccess.getUrnIdAccess().getINTTerminalRuleCall_7_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3009:3: (kw= ':' this_VersionNumber_10= ruleVersionNumber )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==40) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalKactors.g:3010:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
                    {
                    kw=(Token)match(input,40,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_39);
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

            // InternalKactors.g:3026:3: (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
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
                    // InternalKactors.g:3027:4: kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    {
                    kw=(Token)match(input,57,FOLLOW_40); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:3032:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )
                    int alt40=2;
                    alt40 = dfa40.predict(input);
                    switch (alt40) {
                        case 1 :
                            // InternalKactors.g:3033:5: this_Path_12= rulePath
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_1_0());
                              				
                            }
                            pushFollow(FOLLOW_41);
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
                            // InternalKactors.g:3044:5: this_UrnKvp_13= ruleUrnKvp
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_1_1());
                              				
                            }
                            pushFollow(FOLLOW_41);
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

                    // InternalKactors.g:3055:4: (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==59) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // InternalKactors.g:3056:5: kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    {
                    	    kw=(Token)match(input,59,FOLLOW_40); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getUrnIdAccess().getAmpersandKeyword_9_2_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:3061:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    int alt41=2;
                    	    alt41 = dfa41.predict(input);
                    	    switch (alt41) {
                    	        case 1 :
                    	            // InternalKactors.g:3062:6: this_Path_15= rulePath
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_2_1_0());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_41);
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
                    	            // InternalKactors.g:3073:6: this_UrnKvp_16= ruleUrnKvp
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_2_1_1());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_41);
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
    // InternalKactors.g:3090:1: entryRuleUrnKvp returns [String current=null] : iv_ruleUrnKvp= ruleUrnKvp EOF ;
    public final String entryRuleUrnKvp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnKvp = null;


        try {
            // InternalKactors.g:3090:46: (iv_ruleUrnKvp= ruleUrnKvp EOF )
            // InternalKactors.g:3091:2: iv_ruleUrnKvp= ruleUrnKvp EOF
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
    // InternalKactors.g:3097:1: ruleUrnKvp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleUrnKvp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;

        AntlrDatatypeRuleToken this_Path_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:3103:2: ( (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) )
            // InternalKactors.g:3104:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            {
            // InternalKactors.g:3104:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            // InternalKactors.g:3105:3: this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnKvpAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_42);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,60,FOLLOW_37); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnKvpAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalKactors.g:3120:3: (this_Path_2= rulePath | this_INT_3= RULE_INT )
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
                    // InternalKactors.g:3121:4: this_Path_2= rulePath
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
                    // InternalKactors.g:3132:4: this_INT_3= RULE_INT
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
    // InternalKactors.g:3144:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKactors.g:3144:45: (iv_ruleList= ruleList EOF )
            // InternalKactors.g:3145:2: iv_ruleList= ruleList EOF
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
    // InternalKactors.g:3151:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3157:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKactors.g:3158:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKactors.g:3158:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKactors.g:3159:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKactors.g:3159:3: ()
            // InternalKactors.g:3160:4: 
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

            otherlv_1=(Token)match(input,41,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:3173:3: ( (lv_contents_2_0= ruleValue ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( ((LA45_0>=RULE_OBSERVABLE && LA45_0<=RULE_LOWERCASE_ID)||LA45_0==RULE_STRING||LA45_0==RULE_EXPR||LA45_0==RULE_INT||LA45_0==RULE_ARGVALUE||LA45_0==41||(LA45_0>=50 && LA45_0<=51)||LA45_0==58||LA45_0==61||LA45_0==63||(LA45_0>=75 && LA45_0<=76)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalKactors.g:3174:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKactors.g:3174:4: (lv_contents_2_0= ruleValue )
            	    // InternalKactors.g:3175:5: lv_contents_2_0= ruleValue
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

            otherlv_3=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3200:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKactors.g:3200:44: (iv_ruleMap= ruleMap EOF )
            // InternalKactors.g:3201:2: iv_ruleMap= ruleMap EOF
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
    // InternalKactors.g:3207:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3213:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKactors.g:3214:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKactors.g:3214:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKactors.g:3215:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKactors.g:3215:3: ()
            // InternalKactors.g:3216:4: 
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

            otherlv_1=(Token)match(input,61,FOLLOW_43); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3229:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( ((LA47_0>=RULE_OBSERVABLE && LA47_0<=RULE_LOWERCASE_ID)||LA47_0==RULE_STRING||LA47_0==RULE_INT||LA47_0==48||(LA47_0>=50 && LA47_0<=51)||(LA47_0>=55 && LA47_0<=56)||LA47_0==60||(LA47_0>=70 && LA47_0<=76)) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalKactors.g:3230:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKactors.g:3230:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKactors.g:3231:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKactors.g:3231:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKactors.g:3232:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_44);
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

                    // InternalKactors.g:3249:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==29) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // InternalKactors.g:3250:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKactors.g:3250:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKactors.g:3251:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,29,FOLLOW_45); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKactors.g:3258:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKactors.g:3259:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKactors.g:3259:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKactors.g:3260:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_44);
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
    // InternalKactors.g:3287:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKactors.g:3287:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKactors.g:3288:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKactors.g:3294:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3300:2: ( ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:3301:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:3301:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:3302:3: ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKactors.g:3302:3: ( (lv_classifier_0_0= ruleClassifier ) )
            // InternalKactors.g:3303:4: (lv_classifier_0_0= ruleClassifier )
            {
            // InternalKactors.g:3303:4: (lv_classifier_0_0= ruleClassifier )
            // InternalKactors.g:3304:5: lv_classifier_0_0= ruleClassifier
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

            otherlv_1=(Token)match(input,40,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKactors.g:3325:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:3326:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:3326:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:3327:5: lv_value_2_0= ruleValue
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
    // InternalKactors.g:3348:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKactors.g:3348:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKactors.g:3349:2: iv_ruleClassifier= ruleClassifier EOF
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
    // InternalKactors.g:3355:1: ruleClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) ;
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
            // InternalKactors.g:3361:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) )
            // InternalKactors.g:3362:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            {
            // InternalKactors.g:3362:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            int alt51=10;
            alt51 = dfa51.predict(input);
            switch (alt51) {
                case 1 :
                    // InternalKactors.g:3363:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:3363:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==50) ) {
                        alt48=1;
                    }
                    else if ( (LA48_0==51) ) {
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
                            // InternalKactors.g:3364:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:3364:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:3365:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:3365:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:3366:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3379:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:3379:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:3380:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:3380:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:3381:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3395:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:3395:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:3396:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:3396:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:3397:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:3397:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:3398:6: lv_int0_2_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt0NumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
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

                    // InternalKactors.g:3415:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt49=3;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==52) ) {
                        alt49=1;
                    }
                    else if ( (LA49_0==53) ) {
                        alt49=2;
                    }
                    switch (alt49) {
                        case 1 :
                            // InternalKactors.g:3416:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:3416:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:3417:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:3417:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:3418:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,52,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:3431:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,53,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:3436:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:3437:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:3443:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:3444:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:3448:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:3449:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
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

                    // InternalKactors.g:3466:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt50=3;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==52) ) {
                        alt50=1;
                    }
                    else if ( (LA50_0==53) ) {
                        alt50=2;
                    }
                    switch (alt50) {
                        case 1 :
                            // InternalKactors.g:3467:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:3467:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:3468:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:3468:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:3469:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3482:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3489:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3489:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:3490:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:3490:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:3491:5: lv_num_9_0= ruleNumber
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
                    // InternalKactors.g:3509:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKactors.g:3509:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKactors.g:3510:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,48,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:3514:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKactors.g:3515:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKactors.g:3515:5: (lv_set_11_0= ruleList )
                    // InternalKactors.g:3516:6: lv_set_11_0= ruleList
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
                    // InternalKactors.g:3535:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:3535:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKactors.g:3536:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:3536:4: (lv_string_12_0= RULE_STRING )
                    // InternalKactors.g:3537:5: lv_string_12_0= RULE_STRING
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
                    // InternalKactors.g:3554:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:3554:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:3555:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:3555:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    // InternalKactors.g:3556:5: lv_observable_13_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:3573:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:3573:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:3574:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:3574:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:3575:5: lv_id_14_0= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:3592:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:3592:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    // InternalKactors.g:3593:4: ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3593:4: ( (lv_op_15_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:3594:5: (lv_op_15_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:3594:5: (lv_op_15_0= ruleREL_OPERATOR )
                    // InternalKactors.g:3595:6: lv_op_15_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getOpREL_OPERATORParserRuleCall_7_0_0());
                      					
                    }
                    pushFollow(FOLLOW_35);
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

                    // InternalKactors.g:3612:4: ( (lv_expression_16_0= ruleNumber ) )
                    // InternalKactors.g:3613:5: (lv_expression_16_0= ruleNumber )
                    {
                    // InternalKactors.g:3613:5: (lv_expression_16_0= ruleNumber )
                    // InternalKactors.g:3614:6: lv_expression_16_0= ruleNumber
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
                    // InternalKactors.g:3633:3: ( (lv_nodata_17_0= 'unknown' ) )
                    {
                    // InternalKactors.g:3633:3: ( (lv_nodata_17_0= 'unknown' ) )
                    // InternalKactors.g:3634:4: (lv_nodata_17_0= 'unknown' )
                    {
                    // InternalKactors.g:3634:4: (lv_nodata_17_0= 'unknown' )
                    // InternalKactors.g:3635:5: lv_nodata_17_0= 'unknown'
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
                    // InternalKactors.g:3648:3: ( (lv_star_18_0= '*' ) )
                    {
                    // InternalKactors.g:3648:3: ( (lv_star_18_0= '*' ) )
                    // InternalKactors.g:3649:4: (lv_star_18_0= '*' )
                    {
                    // InternalKactors.g:3649:4: (lv_star_18_0= '*' )
                    // InternalKactors.g:3650:5: lv_star_18_0= '*'
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
    // InternalKactors.g:3666:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKactors.g:3666:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKactors.g:3667:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKactors.g:3673:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3679:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKactors.g:3680:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKactors.g:3680:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKactors.g:3681:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKactors.g:3681:3: ()
            // InternalKactors.g:3682:4: 
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

            otherlv_1=(Token)match(input,63,FOLLOW_47); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:3695:3: ( (lv_table_2_0= ruleTable ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=RULE_OBSERVABLE && LA52_0<=RULE_LOWERCASE_ID)||LA52_0==RULE_STRING||LA52_0==RULE_EXPR||LA52_0==RULE_INT||LA52_0==48||(LA52_0>=50 && LA52_0<=51)||(LA52_0>=55 && LA52_0<=57)||LA52_0==60||(LA52_0>=70 && LA52_0<=76)) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalKactors.g:3696:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKactors.g:3696:4: (lv_table_2_0= ruleTable )
                    // InternalKactors.g:3697:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_48);
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

            otherlv_3=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:3722:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKactors.g:3722:46: (iv_ruleTable= ruleTable EOF )
            // InternalKactors.g:3723:2: iv_ruleTable= ruleTable EOF
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
    // InternalKactors.g:3729:1: ruleTable returns [EObject current=null] : ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token this_SEPARATOR_1=null;
        Token otherlv_3=null;
        EObject lv_headers_0_0 = null;

        EObject lv_rows_2_0 = null;

        EObject lv_rows_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3735:2: ( ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) )
            // InternalKactors.g:3736:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            {
            // InternalKactors.g:3736:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            // InternalKactors.g:3737:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            {
            // InternalKactors.g:3737:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?
            int alt53=2;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalKactors.g:3738:4: ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR
                    {
                    // InternalKactors.g:3738:4: ( (lv_headers_0_0= ruleHeaderRow ) )
                    // InternalKactors.g:3739:5: (lv_headers_0_0= ruleHeaderRow )
                    {
                    // InternalKactors.g:3739:5: (lv_headers_0_0= ruleHeaderRow )
                    // InternalKactors.g:3740:6: lv_headers_0_0= ruleHeaderRow
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableAccess().getHeadersHeaderRowParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_49);
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

                    this_SEPARATOR_1=(Token)match(input,RULE_SEPARATOR,FOLLOW_50); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SEPARATOR_1, grammarAccess.getTableAccess().getSEPARATORTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:3762:3: ( (lv_rows_2_0= ruleTableRow ) )
            // InternalKactors.g:3763:4: (lv_rows_2_0= ruleTableRow )
            {
            // InternalKactors.g:3763:4: (lv_rows_2_0= ruleTableRow )
            // InternalKactors.g:3764:5: lv_rows_2_0= ruleTableRow
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

            // InternalKactors.g:3781:3: (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==29) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalKactors.g:3782:4: otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) )
            	    {
            	    otherlv_3=(Token)match(input,29,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKactors.g:3786:4: ( (lv_rows_4_0= ruleTableRow ) )
            	    // InternalKactors.g:3787:5: (lv_rows_4_0= ruleTableRow )
            	    {
            	    // InternalKactors.g:3787:5: (lv_rows_4_0= ruleTableRow )
            	    // InternalKactors.g:3788:6: lv_rows_4_0= ruleTableRow
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
    // InternalKactors.g:3810:1: entryRuleHeaderRow returns [EObject current=null] : iv_ruleHeaderRow= ruleHeaderRow EOF ;
    public final EObject entryRuleHeaderRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeaderRow = null;


        try {
            // InternalKactors.g:3810:50: (iv_ruleHeaderRow= ruleHeaderRow EOF )
            // InternalKactors.g:3811:2: iv_ruleHeaderRow= ruleHeaderRow EOF
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
    // InternalKactors.g:3817:1: ruleHeaderRow returns [EObject current=null] : ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) ;
    public final EObject ruleHeaderRow() throws RecognitionException {
        EObject current = null;

        Token lv_elements_0_1=null;
        Token lv_elements_0_2=null;
        Token otherlv_1=null;
        Token lv_elements_2_1=null;
        Token lv_elements_2_2=null;


        	enterRule();

        try {
            // InternalKactors.g:3823:2: ( ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) )
            // InternalKactors.g:3824:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            {
            // InternalKactors.g:3824:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            // InternalKactors.g:3825:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            {
            // InternalKactors.g:3825:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) )
            // InternalKactors.g:3826:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            {
            // InternalKactors.g:3826:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            // InternalKactors.g:3827:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            {
            // InternalKactors.g:3827:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
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
                    // InternalKactors.g:3828:6: lv_elements_0_1= RULE_LOWERCASE_ID
                    {
                    lv_elements_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_51); if (state.failed) return current;
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
                    // InternalKactors.g:3843:6: lv_elements_0_2= RULE_STRING
                    {
                    lv_elements_0_2=(Token)match(input,RULE_STRING,FOLLOW_51); if (state.failed) return current;
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

            // InternalKactors.g:3860:3: (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==65) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // InternalKactors.g:3861:4: otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    {
            	    otherlv_1=(Token)match(input,65,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getHeaderRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:3865:4: ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    // InternalKactors.g:3866:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:3866:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    // InternalKactors.g:3867:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    {
            	    // InternalKactors.g:3867:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
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
            	            // InternalKactors.g:3868:7: lv_elements_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_elements_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_51); if (state.failed) return current;
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
            	            // InternalKactors.g:3883:7: lv_elements_2_2= RULE_STRING
            	            {
            	            lv_elements_2_2=(Token)match(input,RULE_STRING,FOLLOW_51); if (state.failed) return current;
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
    // InternalKactors.g:3905:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKactors.g:3905:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKactors.g:3906:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKactors.g:3912:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3918:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKactors.g:3919:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKactors.g:3919:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKactors.g:3920:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKactors.g:3920:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKactors.g:3921:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKactors.g:3921:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKactors.g:3922:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_51);
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

            // InternalKactors.g:3939:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==65) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalKactors.g:3940:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,65,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:3944:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKactors.g:3945:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKactors.g:3945:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKactors.g:3946:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_51);
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
    // InternalKactors.g:3968:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKactors.g:3968:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKactors.g:3969:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKactors.g:3975:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) ;
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
            // InternalKactors.g:3981:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) )
            // InternalKactors.g:3982:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            {
            // InternalKactors.g:3982:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            int alt62=13;
            alt62 = dfa62.predict(input);
            switch (alt62) {
                case 1 :
                    // InternalKactors.g:3983:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:3983:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==50) ) {
                        alt59=1;
                    }
                    else if ( (LA59_0==51) ) {
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
                            // InternalKactors.g:3984:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:3984:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:3985:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:3985:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:3986:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3999:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:3999:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:4000:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:4000:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:4001:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4015:3: ( (lv_num_2_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4015:3: ( (lv_num_2_0= ruleNumber ) )
                    // InternalKactors.g:4016:4: (lv_num_2_0= ruleNumber )
                    {
                    // InternalKactors.g:4016:4: (lv_num_2_0= ruleNumber )
                    // InternalKactors.g:4017:5: lv_num_2_0= ruleNumber
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
                    // InternalKactors.g:4035:3: ( (lv_string_3_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4035:3: ( (lv_string_3_0= RULE_STRING ) )
                    // InternalKactors.g:4036:4: (lv_string_3_0= RULE_STRING )
                    {
                    // InternalKactors.g:4036:4: (lv_string_3_0= RULE_STRING )
                    // InternalKactors.g:4037:5: lv_string_3_0= RULE_STRING
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
                    // InternalKactors.g:4054:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4054:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4055:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4055:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4056:5: lv_observable_4_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:4073:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4073:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    // InternalKactors.g:4074:4: ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4074:4: ( (lv_op_5_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:4075:5: (lv_op_5_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:4075:5: (lv_op_5_0= ruleREL_OPERATOR )
                    // InternalKactors.g:4076:6: lv_op_5_0= ruleREL_OPERATOR
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getOpREL_OPERATORParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_35);
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

                    // InternalKactors.g:4093:4: ( (lv_expression_6_0= ruleNumber ) )
                    // InternalKactors.g:4094:5: (lv_expression_6_0= ruleNumber )
                    {
                    // InternalKactors.g:4094:5: (lv_expression_6_0= ruleNumber )
                    // InternalKactors.g:4095:6: lv_expression_6_0= ruleNumber
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
                    // InternalKactors.g:4114:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    {
                    // InternalKactors.g:4114:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    // InternalKactors.g:4115:4: ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    {
                    // InternalKactors.g:4115:4: ( (lv_int0_7_0= ruleNumber ) )
                    // InternalKactors.g:4116:5: (lv_int0_7_0= ruleNumber )
                    {
                    // InternalKactors.g:4116:5: (lv_int0_7_0= ruleNumber )
                    // InternalKactors.g:4117:6: lv_int0_7_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt0NumberParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
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

                    // InternalKactors.g:4134:4: ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )?
                    int alt60=3;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==52) ) {
                        alt60=1;
                    }
                    else if ( (LA60_0==53) ) {
                        alt60=2;
                    }
                    switch (alt60) {
                        case 1 :
                            // InternalKactors.g:4135:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4135:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            // InternalKactors.g:4136:6: (lv_leftLimit_8_0= 'inclusive' )
                            {
                            // InternalKactors.g:4136:6: (lv_leftLimit_8_0= 'inclusive' )
                            // InternalKactors.g:4137:7: lv_leftLimit_8_0= 'inclusive'
                            {
                            lv_leftLimit_8_0=(Token)match(input,52,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:4150:5: otherlv_9= 'exclusive'
                            {
                            otherlv_9=(Token)match(input,53,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_9, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:4155:4: ( ( 'to' )=>otherlv_10= 'to' )
                    // InternalKactors.g:4156:5: ( 'to' )=>otherlv_10= 'to'
                    {
                    otherlv_10=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getToKeyword_5_2());
                      				
                    }

                    }

                    // InternalKactors.g:4162:4: ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) )
                    // InternalKactors.g:4163:5: ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber )
                    {
                    // InternalKactors.g:4167:5: (lv_int1_11_0= ruleNumber )
                    // InternalKactors.g:4168:6: lv_int1_11_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_5_3_0());
                      					
                    }
                    pushFollow(FOLLOW_46);
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

                    // InternalKactors.g:4185:4: ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    int alt61=3;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==52) ) {
                        alt61=1;
                    }
                    else if ( (LA61_0==53) ) {
                        alt61=2;
                    }
                    switch (alt61) {
                        case 1 :
                            // InternalKactors.g:4186:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4186:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            // InternalKactors.g:4187:6: (lv_rightLimit_12_0= 'inclusive' )
                            {
                            // InternalKactors.g:4187:6: (lv_rightLimit_12_0= 'inclusive' )
                            // InternalKactors.g:4188:7: lv_rightLimit_12_0= 'inclusive'
                            {
                            lv_rightLimit_12_0=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4201:5: otherlv_13= 'exclusive'
                            {
                            otherlv_13=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:4208:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    {
                    // InternalKactors.g:4208:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    // InternalKactors.g:4209:4: otherlv_14= 'in' ( (lv_set_15_0= ruleList ) )
                    {
                    otherlv_14=(Token)match(input,48,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getTableClassifierAccess().getInKeyword_6_0());
                      			
                    }
                    // InternalKactors.g:4213:4: ( (lv_set_15_0= ruleList ) )
                    // InternalKactors.g:4214:5: (lv_set_15_0= ruleList )
                    {
                    // InternalKactors.g:4214:5: (lv_set_15_0= ruleList )
                    // InternalKactors.g:4215:6: lv_set_15_0= ruleList
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
                    // InternalKactors.g:4234:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:4234:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    // InternalKactors.g:4235:4: (lv_quantity_16_0= ruleQuantity )
                    {
                    // InternalKactors.g:4235:4: (lv_quantity_16_0= ruleQuantity )
                    // InternalKactors.g:4236:5: lv_quantity_16_0= ruleQuantity
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
                    // InternalKactors.g:4254:3: ( (lv_date_17_0= ruleDate ) )
                    {
                    // InternalKactors.g:4254:3: ( (lv_date_17_0= ruleDate ) )
                    // InternalKactors.g:4255:4: (lv_date_17_0= ruleDate )
                    {
                    // InternalKactors.g:4255:4: (lv_date_17_0= ruleDate )
                    // InternalKactors.g:4256:5: lv_date_17_0= ruleDate
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
                    // InternalKactors.g:4274:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:4274:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    // InternalKactors.g:4275:4: (lv_expr_18_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4275:4: (lv_expr_18_0= RULE_EXPR )
                    // InternalKactors.g:4276:5: lv_expr_18_0= RULE_EXPR
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
                    // InternalKactors.g:4293:3: ( (lv_nodata_19_0= 'unknown' ) )
                    {
                    // InternalKactors.g:4293:3: ( (lv_nodata_19_0= 'unknown' ) )
                    // InternalKactors.g:4294:4: (lv_nodata_19_0= 'unknown' )
                    {
                    // InternalKactors.g:4294:4: (lv_nodata_19_0= 'unknown' )
                    // InternalKactors.g:4295:5: lv_nodata_19_0= 'unknown'
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
                    // InternalKactors.g:4308:3: ( (lv_star_20_0= '*' ) )
                    {
                    // InternalKactors.g:4308:3: ( (lv_star_20_0= '*' ) )
                    // InternalKactors.g:4309:4: (lv_star_20_0= '*' )
                    {
                    // InternalKactors.g:4309:4: (lv_star_20_0= '*' )
                    // InternalKactors.g:4310:5: lv_star_20_0= '*'
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
                    // InternalKactors.g:4323:3: ( (lv_anything_21_0= '#' ) )
                    {
                    // InternalKactors.g:4323:3: ( (lv_anything_21_0= '#' ) )
                    // InternalKactors.g:4324:4: (lv_anything_21_0= '#' )
                    {
                    // InternalKactors.g:4324:4: (lv_anything_21_0= '#' )
                    // InternalKactors.g:4325:5: lv_anything_21_0= '#'
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
    // InternalKactors.g:4341:1: entryRuleQuantity returns [EObject current=null] : iv_ruleQuantity= ruleQuantity EOF ;
    public final EObject entryRuleQuantity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantity = null;


        try {
            // InternalKactors.g:4341:49: (iv_ruleQuantity= ruleQuantity EOF )
            // InternalKactors.g:4342:2: iv_ruleQuantity= ruleQuantity EOF
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
    // InternalKactors.g:4348:1: ruleQuantity returns [EObject current=null] : ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) ;
    public final EObject ruleQuantity() throws RecognitionException {
        EObject current = null;

        Token lv_over_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_0_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4354:2: ( ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) )
            // InternalKactors.g:4355:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            {
            // InternalKactors.g:4355:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            // InternalKactors.g:4356:3: ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            {
            // InternalKactors.g:4356:3: ( (lv_value_0_0= ruleNumber ) )
            // InternalKactors.g:4357:4: (lv_value_0_0= ruleNumber )
            {
            // InternalKactors.g:4357:4: (lv_value_0_0= ruleNumber )
            // InternalKactors.g:4358:5: lv_value_0_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQuantityAccess().getValueNumberParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_53);
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

            // InternalKactors.g:4375:3: ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==66) ) {
                alt63=1;
            }
            else if ( (LA63_0==67) ) {
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
                    // InternalKactors.g:4376:4: ( (lv_over_1_0= '/' ) )
                    {
                    // InternalKactors.g:4376:4: ( (lv_over_1_0= '/' ) )
                    // InternalKactors.g:4377:5: (lv_over_1_0= '/' )
                    {
                    // InternalKactors.g:4377:5: (lv_over_1_0= '/' )
                    // InternalKactors.g:4378:6: lv_over_1_0= '/'
                    {
                    lv_over_1_0=(Token)match(input,66,FOLLOW_54); if (state.failed) return current;
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
                    // InternalKactors.g:4391:4: otherlv_2= '.'
                    {
                    otherlv_2=(Token)match(input,67,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQuantityAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4396:3: ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==EOF||LA64_0==RULE_LOWERCASE_ID||LA64_0==RULE_CAMELCASE_ID||LA64_0==29||LA64_0==41||LA64_0==49||LA64_0==56||(LA64_0>=64 && LA64_0<=66)||LA64_0==83) ) {
                alt64=1;
            }
            else if ( (LA64_0==RULE_UPPERCASE_ID) ) {
                int LA64_2 = input.LA(2);

                if ( (LA64_2==EOF||LA64_2==29||LA64_2==49||LA64_2==56||(LA64_2>=64 && LA64_2<=66)||LA64_2==83) ) {
                    alt64=1;
                }
                else if ( (LA64_2==69) ) {
                    alt64=2;
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
                    // InternalKactors.g:4397:4: ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKactors.g:4397:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKactors.g:4398:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKactors.g:4398:5: (lv_unit_3_0= ruleUnit )
                    // InternalKactors.g:4399:6: lv_unit_3_0= ruleUnit
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
                    // InternalKactors.g:4417:4: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKactors.g:4417:4: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKactors.g:4418:5: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKactors.g:4418:5: (lv_currency_4_0= ruleCurrency )
                    // InternalKactors.g:4419:6: lv_currency_4_0= ruleCurrency
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
    // InternalKactors.g:4441:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKactors.g:4441:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKactors.g:4442:2: iv_ruleAnnotation= ruleAnnotation EOF
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
    // InternalKactors.g:4448:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4454:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKactors.g:4455:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKactors.g:4455:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKactors.g:4456:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKactors.g:4456:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKactors.g:4457:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKactors.g:4457:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKactors.g:4458:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_55); if (state.failed) return current;
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

            // InternalKactors.g:4474:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==41) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalKactors.g:4475:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,41,FOLLOW_20); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:4479:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( ((LA65_0>=RULE_OBSERVABLE && LA65_0<=RULE_LOWERCASE_ID)||LA65_0==RULE_STRING||LA65_0==RULE_EXPR||LA65_0==RULE_INT||LA65_0==RULE_ARGVALUE||LA65_0==41||(LA65_0>=50 && LA65_0<=51)||LA65_0==58||LA65_0==61||LA65_0==63||(LA65_0>=75 && LA65_0<=76)) ) {
                        alt65=1;
                    }
                    switch (alt65) {
                        case 1 :
                            // InternalKactors.g:4480:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:4480:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:4481:6: lv_parameters_2_0= ruleParameterList
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

                    otherlv_3=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:4507:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:4507:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:4508:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKactors.g:4514:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
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
            // InternalKactors.g:4520:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:4521:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:4521:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt68=5;
            alt68 = dfa68.predict(input);
            switch (alt68) {
                case 1 :
                    // InternalKactors.g:4522:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4522:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:4523:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:4523:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:4524:5: lv_number_0_0= ruleNumber
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
                    // InternalKactors.g:4542:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:4542:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:4543:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:4543:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:4544:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:4544:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:4545:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
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

                    otherlv_2=(Token)match(input,54,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:4566:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:4567:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:4567:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:4568:6: lv_to_3_0= ruleNumber
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
                    // InternalKactors.g:4587:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:4587:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:4588:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:4588:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:4589:5: lv_string_4_0= RULE_STRING
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
                    // InternalKactors.g:4606:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:4606:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:4607:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:4607:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:4608:5: lv_date_5_0= ruleDate
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
                    // InternalKactors.g:4626:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:4626:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:4627:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:4627:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:4628:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:4628:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==50) ) {
                        alt67=1;
                    }
                    else if ( (LA67_0==51) ) {
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
                            // InternalKactors.g:4629:6: lv_boolean_6_1= 'true'
                            {
                            lv_boolean_6_1=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:4640:6: lv_boolean_6_2= 'false'
                            {
                            lv_boolean_6_2=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:4657:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:4657:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:4658:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKactors.g:4664:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4670:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:4671:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:4671:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:4672:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:4672:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:4673:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:4673:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:4674:5: lv_pairs_0_0= ruleKeyValuePair
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

            // InternalKactors.g:4691:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==29) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // InternalKactors.g:4692:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:4692:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:4693:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,29,FOLLOW_15); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:4699:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:4700:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:4700:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:4701:6: lv_pairs_2_0= ruleKeyValuePair
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
    // InternalKactors.g:4723:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:4723:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:4724:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKactors.g:4730:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4736:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:4737:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:4737:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:4738:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:4738:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==RULE_LOWERCASE_ID) ) {
                int LA71_1 = input.LA(2);

                if ( (LA71_1==60||LA71_1==68) ) {
                    alt71=1;
                }
            }
            switch (alt71) {
                case 1 :
                    // InternalKactors.g:4739:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:4739:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:4740:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:4740:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:4741:6: lv_name_0_0= RULE_LOWERCASE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_56); if (state.failed) return current;
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

                    // InternalKactors.g:4757:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==68) ) {
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
                            // InternalKactors.g:4758:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:4758:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:4759:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:4759:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:4760:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,68,FOLLOW_15); if (state.failed) return current;
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
                            // InternalKactors.g:4773:5: otherlv_2= '='
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

            // InternalKactors.g:4779:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:4780:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:4780:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:4781:5: lv_value_3_0= ruleValue
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


    // $ANTLR start "entryRuleValue"
    // InternalKactors.g:4802:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:4802:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:4803:2: iv_ruleValue= ruleValue EOF
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
    // InternalKactors.g:4809:1: ruleValue returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) ;
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



        	enterRule();

        try {
            // InternalKactors.g:4815:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) )
            // InternalKactors.g:4816:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            {
            // InternalKactors.g:4816:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            int alt72=9;
            alt72 = dfa72.predict(input);
            switch (alt72) {
                case 1 :
                    // InternalKactors.g:4817:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:4817:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:4818:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:4818:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:4819:5: lv_argvalue_0_0= RULE_ARGVALUE
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
                    // InternalKactors.g:4836:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:4836:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:4837:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:4837:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:4838:5: lv_literal_1_0= ruleLiteral
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
                    // InternalKactors.g:4856:3: ( (lv_id_2_0= rulePathName ) )
                    {
                    // InternalKactors.g:4856:3: ( (lv_id_2_0= rulePathName ) )
                    // InternalKactors.g:4857:4: (lv_id_2_0= rulePathName )
                    {
                    // InternalKactors.g:4857:4: (lv_id_2_0= rulePathName )
                    // InternalKactors.g:4858:5: lv_id_2_0= rulePathName
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
                    // InternalKactors.g:4876:3: ( (lv_urn_3_0= ruleUrnId ) )
                    {
                    // InternalKactors.g:4876:3: ( (lv_urn_3_0= ruleUrnId ) )
                    // InternalKactors.g:4877:4: (lv_urn_3_0= ruleUrnId )
                    {
                    // InternalKactors.g:4877:4: (lv_urn_3_0= ruleUrnId )
                    // InternalKactors.g:4878:5: lv_urn_3_0= ruleUrnId
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
                    // InternalKactors.g:4896:3: ( (lv_list_4_0= ruleList ) )
                    {
                    // InternalKactors.g:4896:3: ( (lv_list_4_0= ruleList ) )
                    // InternalKactors.g:4897:4: (lv_list_4_0= ruleList )
                    {
                    // InternalKactors.g:4897:4: (lv_list_4_0= ruleList )
                    // InternalKactors.g:4898:5: lv_list_4_0= ruleList
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
                    // InternalKactors.g:4916:3: ( (lv_map_5_0= ruleMap ) )
                    {
                    // InternalKactors.g:4916:3: ( (lv_map_5_0= ruleMap ) )
                    // InternalKactors.g:4917:4: (lv_map_5_0= ruleMap )
                    {
                    // InternalKactors.g:4917:4: (lv_map_5_0= ruleMap )
                    // InternalKactors.g:4918:5: lv_map_5_0= ruleMap
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
                    // InternalKactors.g:4936:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:4936:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4937:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4937:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4938:5: lv_observable_6_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:4955:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:4955:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    // InternalKactors.g:4956:4: (lv_expression_7_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4956:4: (lv_expression_7_0= RULE_EXPR )
                    // InternalKactors.g:4957:5: lv_expression_7_0= RULE_EXPR
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
                    // InternalKactors.g:4974:3: ( (lv_table_8_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:4974:3: ( (lv_table_8_0= ruleLookupTable ) )
                    // InternalKactors.g:4975:4: (lv_table_8_0= ruleLookupTable )
                    {
                    // InternalKactors.g:4975:4: (lv_table_8_0= ruleLookupTable )
                    // InternalKactors.g:4976:5: lv_table_8_0= ruleLookupTable
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

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
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


    // $ANTLR start "entryRuleUnitElement"
    // InternalKactors.g:4997:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKactors.g:4997:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKactors.g:4998:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKactors.g:5004:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
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
            // InternalKactors.g:5010:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKactors.g:5011:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKactors.g:5011:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==RULE_LOWERCASE_ID||LA74_0==RULE_CAMELCASE_ID||LA74_0==RULE_UPPERCASE_ID) ) {
                alt74=1;
            }
            else if ( (LA74_0==41) ) {
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
                    // InternalKactors.g:5012:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKactors.g:5012:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKactors.g:5013:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKactors.g:5013:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    // InternalKactors.g:5014:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKactors.g:5014:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
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
                    case RULE_UPPERCASE_ID:
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
                            // InternalKactors.g:5015:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKactors.g:5030:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                            // InternalKactors.g:5045:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                    // InternalKactors.g:5063:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:5063:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKactors.g:5064:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,41,FOLLOW_57); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:5068:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKactors.g:5069:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKactors.g:5069:5: (lv_unit_2_0= ruleUnit )
                    // InternalKactors.g:5070:6: lv_unit_2_0= ruleUnit
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

                    otherlv_3=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:5096:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKactors.g:5096:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKactors.g:5097:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKactors.g:5103:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5109:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5110:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5110:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5111:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5111:3: ()
            // InternalKactors.g:5112:4: 
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

            // InternalKactors.g:5121:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==RULE_LOWERCASE_ID||LA75_0==RULE_CAMELCASE_ID||LA75_0==RULE_UPPERCASE_ID||LA75_0==41) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalKactors.g:5122:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKactors.g:5122:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKactors.g:5123:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_58);
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

            // InternalKactors.g:5140:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==56||LA76_0==66||LA76_0==83) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // InternalKactors.g:5141:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5141:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKactors.g:5142:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKactors.g:5148:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKactors.g:5149:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKactors.g:5149:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKactors.g:5150:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_59);
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

            	    // InternalKactors.g:5168:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKactors.g:5169:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5169:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKactors.g:5170:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_58);
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
    // $ANTLR end "ruleUnit"


    // $ANTLR start "entryRuleCurrency"
    // InternalKactors.g:5192:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKactors.g:5192:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKactors.g:5193:2: iv_ruleCurrency= ruleCurrency EOF
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
    // InternalKactors.g:5199:1: ruleCurrency returns [EObject current=null] : ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token otherlv_3=null;
        EObject lv_units_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:5205:2: ( ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:5206:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:5206:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:5207:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:5207:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) )
            // InternalKactors.g:5208:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            {
            // InternalKactors.g:5208:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            // InternalKactors.g:5209:5: lv_id_0_0= RULE_UPPERCASE_ID
            {
            lv_id_0_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_60); if (state.failed) return current;
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

            // InternalKactors.g:5225:3: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
            // InternalKactors.g:5226:4: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
            {
            otherlv_1=(Token)match(input,69,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_1_0());
              			
            }
            // InternalKactors.g:5230:4: ( (lv_year_2_0= RULE_INT ) )
            // InternalKactors.g:5231:5: (lv_year_2_0= RULE_INT )
            {
            // InternalKactors.g:5231:5: (lv_year_2_0= RULE_INT )
            // InternalKactors.g:5232:6: lv_year_2_0= RULE_INT
            {
            lv_year_2_0=(Token)match(input,RULE_INT,FOLLOW_61); if (state.failed) return current;
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

            // InternalKactors.g:5249:3: ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==66) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // InternalKactors.g:5250:4: ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:5250:4: ( ( '/' )=>otherlv_3= '/' )
            	    // InternalKactors.g:5251:5: ( '/' )=>otherlv_3= '/'
            	    {
            	    otherlv_3=(Token)match(input,66,FOLLOW_59); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_3, grammarAccess.getCurrencyAccess().getSolidusKeyword_2_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:5257:4: ( (lv_units_4_0= ruleUnitElement ) )
            	    // InternalKactors.g:5258:5: (lv_units_4_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:5258:5: (lv_units_4_0= ruleUnitElement )
            	    // InternalKactors.g:5259:6: lv_units_4_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getCurrencyAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_61);
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
            	    break loop77;
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
    // InternalKactors.g:5281:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKactors.g:5281:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKactors.g:5282:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKactors.g:5288:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKactors.g:5294:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKactors.g:5295:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKactors.g:5295:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt78=6;
            switch ( input.LA(1) ) {
            case 70:
                {
                alt78=1;
                }
                break;
            case 71:
                {
                alt78=2;
                }
                break;
            case 60:
                {
                alt78=3;
                }
                break;
            case 72:
                {
                alt78=4;
                }
                break;
            case 73:
                {
                alt78=5;
                }
                break;
            case 74:
                {
                alt78=6;
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
                    // InternalKactors.g:5296:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKactors.g:5296:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKactors.g:5297:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKactors.g:5297:4: (lv_gt_0_0= '>' )
                    // InternalKactors.g:5298:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5311:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKactors.g:5311:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKactors.g:5312:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKactors.g:5312:4: (lv_lt_1_0= '<' )
                    // InternalKactors.g:5313:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5326:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKactors.g:5326:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKactors.g:5327:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKactors.g:5327:4: (lv_eq_2_0= '=' )
                    // InternalKactors.g:5328:5: lv_eq_2_0= '='
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
                    // InternalKactors.g:5341:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKactors.g:5341:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKactors.g:5342:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKactors.g:5342:4: (lv_ne_3_0= '!=' )
                    // InternalKactors.g:5343:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5356:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKactors.g:5356:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKactors.g:5357:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKactors.g:5357:4: (lv_le_4_0= '<=' )
                    // InternalKactors.g:5358:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:5371:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKactors.g:5371:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKactors.g:5372:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKactors.g:5372:4: (lv_ge_5_0= '>=' )
                    // InternalKactors.g:5373:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:5389:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:5389:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:5390:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKactors.g:5396:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKactors.g:5402:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:5403:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:5403:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:5404:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:5404:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt79=3;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==75) ) {
                alt79=1;
            }
            else if ( (LA79_0==76) ) {
                alt79=2;
            }
            switch (alt79) {
                case 1 :
                    // InternalKactors.g:5405:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,75,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5410:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:5410:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:5411:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:5411:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:5412:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,76,FOLLOW_10); if (state.failed) return current;
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

            // InternalKactors.g:5425:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:5426:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:5430:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:5431:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_62); if (state.failed) return current;
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

            // InternalKactors.g:5447:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==77) && (synpred173_InternalKactors())) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // InternalKactors.g:5448:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:5452:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:5453:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,77,FOLLOW_63); if (state.failed) return current;
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

            // InternalKactors.g:5465:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==67) ) {
                int LA81_1 = input.LA(2);

                if ( (LA81_1==RULE_INT) && (synpred174_InternalKactors())) {
                    alt81=1;
                }
            }
            switch (alt81) {
                case 1 :
                    // InternalKactors.g:5466:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5479:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:5480:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5480:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:5481:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:5481:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:5482:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,67,FOLLOW_10); if (state.failed) return current;
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

                    // InternalKactors.g:5494:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:5495:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:5495:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:5496:7: lv_decimalPart_5_0= RULE_INT
                    {
                    lv_decimalPart_5_0=(Token)match(input,RULE_INT,FOLLOW_64); if (state.failed) return current;
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

            // InternalKactors.g:5514:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==78) && (synpred178_InternalKactors())) {
                alt84=1;
            }
            else if ( (LA84_0==79) && (synpred178_InternalKactors())) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // InternalKactors.g:5515:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5541:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:5542:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5542:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:5543:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:5543:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:5544:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:5544:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==78) ) {
                        alt82=1;
                    }
                    else if ( (LA82_0==79) ) {
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
                            // InternalKactors.g:5545:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,78,FOLLOW_35); if (state.failed) return current;
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
                            // InternalKactors.g:5556:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,79,FOLLOW_35); if (state.failed) return current;
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

                    // InternalKactors.g:5569:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt83=3;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==75) ) {
                        alt83=1;
                    }
                    else if ( (LA83_0==76) ) {
                        alt83=2;
                    }
                    switch (alt83) {
                        case 1 :
                            // InternalKactors.g:5570:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,75,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5575:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:5575:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:5576:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:5576:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:5577:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,76,FOLLOW_10); if (state.failed) return current;
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

                    // InternalKactors.g:5590:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:5591:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:5591:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:5592:7: lv_exp_9_0= RULE_INT
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
    // InternalKactors.g:5614:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:5614:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:5615:2: iv_ruleDate= ruleDate EOF
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
    // InternalKactors.g:5621:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
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
            // InternalKactors.g:5627:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:5628:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:5628:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:5629:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:5629:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:5630:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:5630:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:5631:5: lv_year_0_0= RULE_INT
            {
            lv_year_0_0=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
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

            // InternalKactors.g:5647:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt85=4;
            switch ( input.LA(1) ) {
                case 80:
                    {
                    alt85=1;
                    }
                    break;
                case 81:
                    {
                    alt85=2;
                    }
                    break;
                case 82:
                    {
                    alt85=3;
                    }
                    break;
            }

            switch (alt85) {
                case 1 :
                    // InternalKactors.g:5648:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,80,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5653:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,81,FOLLOW_66); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:5658:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:5658:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:5659:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:5659:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:5660:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,82,FOLLOW_66); if (state.failed) return current;
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

            otherlv_4=(Token)match(input,76,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:5677:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:5678:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:5678:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:5679:5: lv_month_5_0= RULE_INT
            {
            lv_month_5_0=(Token)match(input,RULE_INT,FOLLOW_66); if (state.failed) return current;
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

            otherlv_6=(Token)match(input,76,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:5699:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:5700:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:5700:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:5701:5: lv_day_7_0= RULE_INT
            {
            lv_day_7_0=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
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

            // InternalKactors.g:5717:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==RULE_INT) ) {
                int LA88_1 = input.LA(2);

                if ( (LA88_1==40) ) {
                    alt88=1;
                }
            }
            switch (alt88) {
                case 1 :
                    // InternalKactors.g:5718:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:5718:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:5719:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:5719:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:5720:6: lv_hour_8_0= RULE_INT
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

                    otherlv_9=(Token)match(input,40,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getDateAccess().getColonKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:5740:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:5741:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:5741:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:5742:6: lv_min_10_0= RULE_INT
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

                    // InternalKactors.g:5758:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==40) ) {
                        alt87=1;
                    }
                    switch (alt87) {
                        case 1 :
                            // InternalKactors.g:5759:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,40,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:5763:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:5764:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:5764:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:5765:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
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

                            // InternalKactors.g:5781:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt86=2;
                            int LA86_0 = input.LA(1);

                            if ( (LA86_0==67) ) {
                                alt86=1;
                            }
                            switch (alt86) {
                                case 1 :
                                    // InternalKactors.g:5782:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,67,FOLLOW_10); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:5786:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:5787:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:5787:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:5788:8: lv_ms_14_0= RULE_INT
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
    // InternalKactors.g:5811:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKactors.g:5811:48: (iv_rulePathName= rulePathName EOF )
            // InternalKactors.g:5812:2: iv_rulePathName= rulePathName EOF
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
    // InternalKactors.g:5818:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5824:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKactors.g:5825:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKactors.g:5825:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKactors.g:5826:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5833:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==67) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalKactors.g:5834:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,67,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_68); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(this_LOWERCASE_ID_2);
            	      			
            	    }
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LOWERCASE_ID_2, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_1_1());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop89;
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
    // InternalKactors.g:5851:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKactors.g:5851:44: (iv_rulePath= rulePath EOF )
            // InternalKactors.g:5852:2: iv_rulePath= rulePath EOF
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
    // InternalKactors.g:5858:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token this_UPPERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_4=null;
        Token this_UPPERCASE_ID_5=null;


        	enterRule();

        try {
            // InternalKactors.g:5864:2: ( ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) )
            // InternalKactors.g:5865:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            {
            // InternalKactors.g:5865:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            // InternalKactors.g:5866:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            {
            // InternalKactors.g:5866:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID )
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==RULE_LOWERCASE_ID) ) {
                alt90=1;
            }
            else if ( (LA90_0==RULE_UPPERCASE_ID) ) {
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
                    // InternalKactors.g:5867:4: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5875:4: this_UPPERCASE_ID_1= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_1, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5883:3: ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( ((LA93_0>=66 && LA93_0<=67)) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // InternalKactors.g:5884:4: (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    {
            	    // InternalKactors.g:5884:4: (kw= '.' | kw= '/' )
            	    int alt91=2;
            	    int LA91_0 = input.LA(1);

            	    if ( (LA91_0==67) ) {
            	        alt91=1;
            	    }
            	    else if ( (LA91_0==66) ) {
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
            	            // InternalKactors.g:5885:5: kw= '.'
            	            {
            	            kw=(Token)match(input,67,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5891:5: kw= '/'
            	            {
            	            kw=(Token)match(input,66,FOLLOW_40); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalKactors.g:5897:4: (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    int alt92=2;
            	    int LA92_0 = input.LA(1);

            	    if ( (LA92_0==RULE_LOWERCASE_ID) ) {
            	        alt92=1;
            	    }
            	    else if ( (LA92_0==RULE_UPPERCASE_ID) ) {
            	        alt92=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 92, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt92) {
            	        case 1 :
            	            // InternalKactors.g:5898:5: this_LOWERCASE_ID_4= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_4=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_69); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_4, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5906:5: this_UPPERCASE_ID_5= RULE_UPPERCASE_ID
            	            {
            	            this_UPPERCASE_ID_5=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_69); if (state.failed) return current;
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
            	    break loop93;
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
    // InternalKactors.g:5919:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKactors.g:5919:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKactors.g:5920:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKactors.g:5926:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKactors.g:5932:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKactors.g:5933:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKactors.g:5933:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKactors.g:5934:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5941:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==67) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // InternalKactors.g:5942:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,67,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKactors.g:5954:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==67) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // InternalKactors.g:5955:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,67,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
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

            // InternalKactors.g:5969:3: (kw= '-' )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==76) ) {
                int LA96_1 = input.LA(2);

                if ( (synpred195_InternalKactors()) ) {
                    alt96=1;
                }
            }
            switch (alt96) {
                case 1 :
                    // InternalKactors.g:5970:4: kw= '-'
                    {
                    kw=(Token)match(input,76,FOLLOW_72); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5976:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt97=3;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==RULE_LOWERCASE_ID) ) {
                int LA97_1 = input.LA(2);

                if ( (synpred196_InternalKactors()) ) {
                    alt97=1;
                }
            }
            else if ( (LA97_0==RULE_UPPERCASE_ID) ) {
                alt97=2;
            }
            switch (alt97) {
                case 1 :
                    // InternalKactors.g:5977:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:5985:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKactors.g:5997:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKactors.g:6003:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKactors.g:6004:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKactors.g:6004:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt98=3;
            switch ( input.LA(1) ) {
            case 66:
                {
                alt98=1;
                }
                break;
            case 83:
                {
                alt98=2;
                }
                break;
            case 56:
                {
                alt98=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }

            switch (alt98) {
                case 1 :
                    // InternalKactors.g:6005:3: (enumLiteral_0= '/' )
                    {
                    // InternalKactors.g:6005:3: (enumLiteral_0= '/' )
                    // InternalKactors.g:6006:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:6013:3: (enumLiteral_1= '^' )
                    {
                    // InternalKactors.g:6013:3: (enumLiteral_1= '^' )
                    // InternalKactors.g:6014:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:6021:3: (enumLiteral_2= '*' )
                    {
                    // InternalKactors.g:6021:3: (enumLiteral_2= '*' )
                    // InternalKactors.g:6022:4: enumLiteral_2= '*'
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

    // $ANTLR start synpred10_InternalKactors
    public final void synpred10_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_imports_8_0 = null;

        AntlrDatatypeRuleToken lv_imports_10_0 = null;


        // InternalKactors.g:289:4: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) )
        // InternalKactors.g:289:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
        {
        // InternalKactors.g:289:4: ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) )
        // InternalKactors.g:290:5: {...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKactors.g:290:105: ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) )
        // InternalKactors.g:291:6: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
        // InternalKactors.g:294:9: ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) )
        // InternalKactors.g:294:10: {...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKactors", "true");
        }
        // InternalKactors.g:294:19: (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* )
        // InternalKactors.g:294:20: otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
        {
        otherlv_7=(Token)match(input,28,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:298:9: ( (lv_imports_8_0= rulePathName ) )
        // InternalKactors.g:299:10: (lv_imports_8_0= rulePathName )
        {
        // InternalKactors.g:299:10: (lv_imports_8_0= rulePathName )
        // InternalKactors.g:300:11: lv_imports_8_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
          										
        }
        pushFollow(FOLLOW_24);
        lv_imports_8_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:317:9: (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )*
        loop103:
        do {
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==29) ) {
                alt103=1;
            }


            switch (alt103) {
        	case 1 :
        	    // InternalKactors.g:318:10: otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) )
        	    {
        	    otherlv_9=(Token)match(input,29,FOLLOW_4); if (state.failed) return ;
        	    // InternalKactors.g:322:10: ( (lv_imports_10_0= rulePathName ) )
        	    // InternalKactors.g:323:11: (lv_imports_10_0= rulePathName )
        	    {
        	    // InternalKactors.g:323:11: (lv_imports_10_0= rulePathName )
        	    // InternalKactors.g:324:12: lv_imports_10_0= rulePathName
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_24);
        	    lv_imports_10_0=rulePathName();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop103;
            }
        } while (true);


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred10_InternalKactors

    // $ANTLR start synpred11_InternalKactors
    public final void synpred11_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_worldview_12_0 = null;


        // InternalKactors.g:348:4: ( ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:348:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:348:4: ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:349:5: {...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKactors.g:349:105: ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) )
        // InternalKactors.g:350:6: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
        // InternalKactors.g:353:9: ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) )
        // InternalKactors.g:353:10: {...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "true");
        }
        // InternalKactors.g:353:19: (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) )
        // InternalKactors.g:353:20: otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) )
        {
        otherlv_11=(Token)match(input,30,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:357:9: ( (lv_worldview_12_0= rulePathName ) )
        // InternalKactors.g:358:10: (lv_worldview_12_0= rulePathName )
        {
        // InternalKactors.g:358:10: (lv_worldview_12_0= rulePathName )
        // InternalKactors.g:359:11: lv_worldview_12_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_worldview_12_0=rulePathName();

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
    // $ANTLR end synpred11_InternalKactors

    // $ANTLR start synpred13_InternalKactors
    public final void synpred13_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_13=null;
        Token lv_observable_14_0=null;
        EObject lv_observables_15_0 = null;


        // InternalKactors.g:382:4: ( ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) )
        // InternalKactors.g:382:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
        {
        // InternalKactors.g:382:4: ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) )
        // InternalKactors.g:383:5: {...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKactors.g:383:105: ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) )
        // InternalKactors.g:384:6: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
        // InternalKactors.g:387:9: ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) )
        // InternalKactors.g:387:10: {...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "true");
        }
        // InternalKactors.g:387:19: (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) )
        // InternalKactors.g:387:20: otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
        {
        otherlv_13=(Token)match(input,31,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:391:9: ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) )
        int alt104=2;
        int LA104_0 = input.LA(1);

        if ( (LA104_0==RULE_OBSERVABLE) ) {
            alt104=1;
        }
        else if ( (LA104_0==41) ) {
            alt104=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 104, 0, input);

            throw nvae;
        }
        switch (alt104) {
            case 1 :
                // InternalKactors.g:392:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
                {
                // InternalKactors.g:392:10: ( (lv_observable_14_0= RULE_OBSERVABLE ) )
                // InternalKactors.g:393:11: (lv_observable_14_0= RULE_OBSERVABLE )
                {
                // InternalKactors.g:393:11: (lv_observable_14_0= RULE_OBSERVABLE )
                // InternalKactors.g:394:12: lv_observable_14_0= RULE_OBSERVABLE
                {
                lv_observable_14_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:411:10: ( (lv_observables_15_0= ruleList ) )
                {
                // InternalKactors.g:411:10: ( (lv_observables_15_0= ruleList ) )
                // InternalKactors.g:412:11: (lv_observables_15_0= ruleList )
                {
                // InternalKactors.g:412:11: (lv_observables_15_0= ruleList )
                // InternalKactors.g:413:12: lv_observables_15_0= ruleList
                {
                if ( state.backtracking==0 ) {

                  												newCompositeNode(grammarAccess.getPreambleAccess().getObservablesListParserRuleCall_2_2_1_1_0());
                  											
                }
                pushFollow(FOLLOW_2);
                lv_observables_15_0=ruleList();

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
    // $ANTLR end synpred13_InternalKactors

    // $ANTLR start synpred16_InternalKactors
    public final void synpred16_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        Token lv_label_17_1=null;
        Token lv_label_17_2=null;
        Token lv_label_17_3=null;

        // InternalKactors.g:437:4: ( ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) )
        // InternalKactors.g:437:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
        {
        // InternalKactors.g:437:4: ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:438:5: {...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKactors.g:438:105: ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:439:6: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
        // InternalKactors.g:442:9: ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) )
        // InternalKactors.g:442:10: {...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "true");
        }
        // InternalKactors.g:442:19: (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) )
        // InternalKactors.g:442:20: otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
        {
        otherlv_16=(Token)match(input,32,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:446:9: ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) )
        // InternalKactors.g:447:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
        {
        // InternalKactors.g:447:10: ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) )
        // InternalKactors.g:448:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
        {
        // InternalKactors.g:448:11: (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING )
        int alt105=3;
        switch ( input.LA(1) ) {
        case RULE_LOWERCASE_ID:
            {
            alt105=1;
            }
            break;
        case RULE_ID:
            {
            alt105=2;
            }
            break;
        case RULE_STRING:
            {
            alt105=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 105, 0, input);

            throw nvae;
        }

        switch (alt105) {
            case 1 :
                // InternalKactors.g:449:12: lv_label_17_1= RULE_LOWERCASE_ID
                {
                lv_label_17_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:464:12: lv_label_17_2= RULE_ID
                {
                lv_label_17_2=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKactors.g:479:12: lv_label_17_3= RULE_STRING
                {
                lv_label_17_3=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred16_InternalKactors

    // $ANTLR start synpred17_InternalKactors
    public final void synpred17_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_18=null;
        Token lv_description_19_0=null;

        // InternalKactors.g:502:4: ( ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:502:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:502:4: ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:503:5: {...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
        }
        // InternalKactors.g:503:105: ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:504:6: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
        // InternalKactors.g:507:9: ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) )
        // InternalKactors.g:507:10: {...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred17_InternalKactors", "true");
        }
        // InternalKactors.g:507:19: (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) )
        // InternalKactors.g:507:20: otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) )
        {
        otherlv_18=(Token)match(input,33,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:511:9: ( (lv_description_19_0= RULE_STRING ) )
        // InternalKactors.g:512:10: (lv_description_19_0= RULE_STRING )
        {
        // InternalKactors.g:512:10: (lv_description_19_0= RULE_STRING )
        // InternalKactors.g:513:11: lv_description_19_0= RULE_STRING
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

        // InternalKactors.g:535:4: ( ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:535:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:535:4: ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:536:5: {...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
        }
        // InternalKactors.g:536:105: ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:537:6: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
        // InternalKactors.g:540:9: ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) )
        // InternalKactors.g:540:10: {...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "true");
        }
        // InternalKactors.g:540:19: (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) )
        // InternalKactors.g:540:20: otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) )
        {
        otherlv_20=(Token)match(input,34,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:544:9: ( (lv_permissions_21_0= RULE_STRING ) )
        // InternalKactors.g:545:10: (lv_permissions_21_0= RULE_STRING )
        {
        // InternalKactors.g:545:10: (lv_permissions_21_0= RULE_STRING )
        // InternalKactors.g:546:11: lv_permissions_21_0= RULE_STRING
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

        // InternalKactors.g:573:10: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )
        // InternalKactors.g:573:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred19_InternalKactors", "true");
        }
        // InternalKactors.g:573:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        // InternalKactors.g:573:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
        {
        otherlv_22=(Token)match(input,35,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:577:9: ( (lv_authors_23_0= RULE_STRING ) )
        // InternalKactors.g:578:10: (lv_authors_23_0= RULE_STRING )
        {
        // InternalKactors.g:578:10: (lv_authors_23_0= RULE_STRING )
        // InternalKactors.g:579:11: lv_authors_23_0= RULE_STRING
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

        // InternalKactors.g:568:4: ( ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKactors.g:568:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKactors.g:568:4: ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) )
        // InternalKactors.g:569:5: {...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred20_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
        }
        // InternalKactors.g:569:105: ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ )
        // InternalKactors.g:570:6: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
        // InternalKactors.g:573:9: ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+
        int cnt106=0;
        loop106:
        do {
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==35) && ((true))) {
                alt106=1;
            }


            switch (alt106) {
        	case 1 :
        	    // InternalKactors.g:573:10: {...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred20_InternalKactors", "true");
        	    }
        	    // InternalKactors.g:573:19: (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) )
        	    // InternalKactors.g:573:20: otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) )
        	    {
        	    otherlv_22=(Token)match(input,35,FOLLOW_9); if (state.failed) return ;
        	    // InternalKactors.g:577:9: ( (lv_authors_23_0= RULE_STRING ) )
        	    // InternalKactors.g:578:10: (lv_authors_23_0= RULE_STRING )
        	    {
        	    // InternalKactors.g:578:10: (lv_authors_23_0= RULE_STRING )
        	    // InternalKactors.g:579:11: lv_authors_23_0= RULE_STRING
        	    {
        	    lv_authors_23_0=(Token)match(input,RULE_STRING,FOLLOW_73); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt106 >= 1 ) break loop106;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(106, input);
                    throw eee;
            }
            cnt106++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred20_InternalKactors

    // $ANTLR start synpred21_InternalKactors
    public final void synpred21_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_24=null;
        AntlrDatatypeRuleToken lv_version_25_0 = null;


        // InternalKactors.g:601:4: ( ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKactors.g:601:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKactors.g:601:4: ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKactors.g:602:5: {...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred21_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
        }
        // InternalKactors.g:602:105: ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) )
        // InternalKactors.g:603:6: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
        // InternalKactors.g:606:9: ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) )
        // InternalKactors.g:606:10: {...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred21_InternalKactors", "true");
        }
        // InternalKactors.g:606:19: (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) )
        // InternalKactors.g:606:20: otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) )
        {
        otherlv_24=(Token)match(input,36,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:610:9: ( (lv_version_25_0= ruleVersionNumber ) )
        // InternalKactors.g:611:10: (lv_version_25_0= ruleVersionNumber )
        {
        // InternalKactors.g:611:10: (lv_version_25_0= ruleVersionNumber )
        // InternalKactors.g:612:11: lv_version_25_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_7_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_version_25_0=ruleVersionNumber();

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

    // $ANTLR start synpred23_InternalKactors
    public final void synpred23_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_26=null;
        Token lv_createcomment_28_0=null;
        EObject lv_created_27_0 = null;


        // InternalKactors.g:635:4: ( ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:635:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:635:4: ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:636:5: {...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred23_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
        }
        // InternalKactors.g:636:105: ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:637:6: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
        // InternalKactors.g:640:9: ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) )
        // InternalKactors.g:640:10: {...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred23_InternalKactors", "true");
        }
        // InternalKactors.g:640:19: (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? )
        // InternalKactors.g:640:20: otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )?
        {
        otherlv_26=(Token)match(input,37,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:644:9: ( (lv_created_27_0= ruleDate ) )
        // InternalKactors.g:645:10: (lv_created_27_0= ruleDate )
        {
        // InternalKactors.g:645:10: (lv_created_27_0= ruleDate )
        // InternalKactors.g:646:11: lv_created_27_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_8_1_0());
          										
        }
        pushFollow(FOLLOW_74);
        lv_created_27_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:663:9: ( (lv_createcomment_28_0= RULE_STRING ) )?
        int alt107=2;
        int LA107_0 = input.LA(1);

        if ( (LA107_0==RULE_STRING) ) {
            alt107=1;
        }
        switch (alt107) {
            case 1 :
                // InternalKactors.g:664:10: (lv_createcomment_28_0= RULE_STRING )
                {
                // InternalKactors.g:664:10: (lv_createcomment_28_0= RULE_STRING )
                // InternalKactors.g:665:11: lv_createcomment_28_0= RULE_STRING
                {
                lv_createcomment_28_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred23_InternalKactors

    // $ANTLR start synpred25_InternalKactors
    public final void synpred25_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_29=null;
        Token lv_modcomment_31_0=null;
        EObject lv_modified_30_0 = null;


        // InternalKactors.g:687:4: ( ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:687:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:687:4: ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:688:5: {...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred25_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9)");
        }
        // InternalKactors.g:688:105: ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:689:6: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9);
        // InternalKactors.g:692:9: ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) )
        // InternalKactors.g:692:10: {...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred25_InternalKactors", "true");
        }
        // InternalKactors.g:692:19: (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? )
        // InternalKactors.g:692:20: otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )?
        {
        otherlv_29=(Token)match(input,38,FOLLOW_10); if (state.failed) return ;
        // InternalKactors.g:696:9: ( (lv_modified_30_0= ruleDate ) )
        // InternalKactors.g:697:10: (lv_modified_30_0= ruleDate )
        {
        // InternalKactors.g:697:10: (lv_modified_30_0= ruleDate )
        // InternalKactors.g:698:11: lv_modified_30_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_9_1_0());
          										
        }
        pushFollow(FOLLOW_74);
        lv_modified_30_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:715:9: ( (lv_modcomment_31_0= RULE_STRING ) )?
        int alt108=2;
        int LA108_0 = input.LA(1);

        if ( (LA108_0==RULE_STRING) ) {
            alt108=1;
        }
        switch (alt108) {
            case 1 :
                // InternalKactors.g:716:10: (lv_modcomment_31_0= RULE_STRING )
                {
                // InternalKactors.g:716:10: (lv_modcomment_31_0= RULE_STRING )
                // InternalKactors.g:717:11: lv_modcomment_31_0= RULE_STRING
                {
                lv_modcomment_31_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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

    // $ANTLR start synpred32_InternalKactors
    public final void synpred32_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:1021:6: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:1021:6: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,41,FOLLOW_20); if (state.failed) return ;
        // InternalKactors.g:1025:6: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt110=2;
        int LA110_0 = input.LA(1);

        if ( ((LA110_0>=RULE_OBSERVABLE && LA110_0<=RULE_LOWERCASE_ID)||LA110_0==RULE_STRING||LA110_0==RULE_EXPR||LA110_0==RULE_INT||LA110_0==RULE_ARGVALUE||LA110_0==41||(LA110_0>=50 && LA110_0<=51)||LA110_0==58||LA110_0==61||LA110_0==63||(LA110_0>=75 && LA110_0<=76)) ) {
            alt110=1;
        }
        switch (alt110) {
            case 1 :
                // InternalKactors.g:1026:7: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:1026:7: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:1027:8: lv_parameters_2_0= ruleParameterList
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

        otherlv_3=(Token)match(input,42,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred32_InternalKactors

    // $ANTLR start synpred35_InternalKactors
    public final void synpred35_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_2_0 = null;


        // InternalKactors.g:1122:4: ( (lv_body_2_0= ruleMessageBody ) )
        // InternalKactors.g:1122:4: (lv_body_2_0= ruleMessageBody )
        {
        // InternalKactors.g:1122:4: (lv_body_2_0= ruleMessageBody )
        // InternalKactors.g:1123:5: lv_body_2_0= ruleMessageBody
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
    // $ANTLR end synpred35_InternalKactors

    // $ANTLR start synpred36_InternalKactors
    public final void synpred36_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_next_1_0 = null;


        // InternalKactors.g:1183:4: ( (lv_next_1_0= ruleNextStatement ) )
        // InternalKactors.g:1183:4: (lv_next_1_0= ruleNextStatement )
        {
        // InternalKactors.g:1183:4: (lv_next_1_0= ruleNextStatement )
        // InternalKactors.g:1184:5: lv_next_1_0= ruleNextStatement
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
    // $ANTLR end synpred36_InternalKactors

    // $ANTLR start synpred37_InternalKactors
    public final void synpred37_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:1220:3: ( ( (lv_verb_0_0= ruleMessageCall ) ) )
        // InternalKactors.g:1220:3: ( (lv_verb_0_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1220:3: ( (lv_verb_0_0= ruleMessageCall ) )
        // InternalKactors.g:1221:4: (lv_verb_0_0= ruleMessageCall )
        {
        // InternalKactors.g:1221:4: (lv_verb_0_0= ruleMessageCall )
        // InternalKactors.g:1222:5: lv_verb_0_0= ruleMessageCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getStatementAccess().getVerbMessageCallParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_verb_0_0=ruleMessageCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred37_InternalKactors

    // $ANTLR start synpred38_InternalKactors
    public final void synpred38_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_1_0 = null;


        // InternalKactors.g:1240:3: ( ( (lv_group_1_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1240:3: ( (lv_group_1_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1240:3: ( (lv_group_1_0= ruleStatementGroup ) )
        // InternalKactors.g:1241:4: (lv_group_1_0= ruleStatementGroup )
        {
        // InternalKactors.g:1241:4: (lv_group_1_0= ruleStatementGroup )
        // InternalKactors.g:1242:5: lv_group_1_0= ruleStatementGroup
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
    // $ANTLR end synpred38_InternalKactors

    // $ANTLR start synpred44_InternalKactors
    public final void synpred44_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_1_0 = null;


        // InternalKactors.g:1402:4: ( ( (lv_verb_1_0= ruleMessageCall ) ) )
        // InternalKactors.g:1402:4: ( (lv_verb_1_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1402:4: ( (lv_verb_1_0= ruleMessageCall ) )
        // InternalKactors.g:1403:5: (lv_verb_1_0= ruleMessageCall )
        {
        // InternalKactors.g:1403:5: (lv_verb_1_0= ruleMessageCall )
        // InternalKactors.g:1404:6: lv_verb_1_0= ruleMessageCall
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getNextStatementAccess().getVerbMessageCallParserRuleCall_1_0_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_verb_1_0=ruleMessageCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred44_InternalKactors

    // $ANTLR start synpred45_InternalKactors
    public final void synpred45_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_group_2_0 = null;


        // InternalKactors.g:1422:4: ( ( (lv_group_2_0= ruleStatementGroup ) ) )
        // InternalKactors.g:1422:4: ( (lv_group_2_0= ruleStatementGroup ) )
        {
        // InternalKactors.g:1422:4: ( (lv_group_2_0= ruleStatementGroup ) )
        // InternalKactors.g:1423:5: (lv_group_2_0= ruleStatementGroup )
        {
        // InternalKactors.g:1423:5: (lv_group_2_0= ruleStatementGroup )
        // InternalKactors.g:1424:6: lv_group_2_0= ruleStatementGroup
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getNextStatementAccess().getGroupStatementGroupParserRuleCall_1_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_group_2_0=ruleStatementGroup();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred45_InternalKactors

    // $ANTLR start synpred51_InternalKactors
    public final void synpred51_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfBody_6_0 = null;


        // InternalKactors.g:1622:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) ) )
        // InternalKactors.g:1622:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        {
        otherlv_3=(Token)match(input,44,FOLLOW_27); if (state.failed) return ;
        otherlv_4=(Token)match(input,43,FOLLOW_25); if (state.failed) return ;
        // InternalKactors.g:1630:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:1631:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:1631:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:1632:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_15); if (state.failed) return ;

        }


        }

        // InternalKactors.g:1648:4: ( (lv_elseIfBody_6_0= ruleStatementBody ) )
        // InternalKactors.g:1649:5: (lv_elseIfBody_6_0= ruleStatementBody )
        {
        // InternalKactors.g:1649:5: (lv_elseIfBody_6_0= ruleStatementBody )
        // InternalKactors.g:1650:6: lv_elseIfBody_6_0= ruleStatementBody
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
    // $ANTLR end synpred51_InternalKactors

    // $ANTLR start synpred52_InternalKactors
    public final void synpred52_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:1669:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) ) )
        // InternalKactors.g:1669:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleStatementBody ) )
        {
        otherlv_7=(Token)match(input,44,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:1673:4: ( (lv_elseCall_8_0= ruleStatementBody ) )
        // InternalKactors.g:1674:5: (lv_elseCall_8_0= ruleStatementBody )
        {
        // InternalKactors.g:1674:5: (lv_elseCall_8_0= ruleStatementBody )
        // InternalKactors.g:1675:6: lv_elseCall_8_0= ruleStatementBody
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
    // $ANTLR end synpred52_InternalKactors

    // $ANTLR start synpred53_InternalKactors
    public final void synpred53_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_verb_0_0 = null;


        // InternalKactors.g:1712:3: ( ( (lv_verb_0_0= ruleMessageCall ) ) )
        // InternalKactors.g:1712:3: ( (lv_verb_0_0= ruleMessageCall ) )
        {
        // InternalKactors.g:1712:3: ( (lv_verb_0_0= ruleMessageCall ) )
        // InternalKactors.g:1713:4: (lv_verb_0_0= ruleMessageCall )
        {
        // InternalKactors.g:1713:4: (lv_verb_0_0= ruleMessageCall )
        // InternalKactors.g:1714:5: lv_verb_0_0= ruleMessageCall
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
    // $ANTLR end synpred53_InternalKactors

    // $ANTLR start synpred54_InternalKactors
    public final void synpred54_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_value_1_0 = null;


        // InternalKactors.g:1732:3: ( ( (lv_value_1_0= ruleValue ) ) )
        // InternalKactors.g:1732:3: ( (lv_value_1_0= ruleValue ) )
        {
        // InternalKactors.g:1732:3: ( (lv_value_1_0= ruleValue ) )
        // InternalKactors.g:1733:4: (lv_value_1_0= ruleValue )
        {
        // InternalKactors.g:1733:4: (lv_value_1_0= ruleValue )
        // InternalKactors.g:1734:5: lv_value_1_0= ruleValue
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
    // $ANTLR end synpred54_InternalKactors

    // $ANTLR start synpred56_InternalKactors
    public final void synpred56_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_match_0_0 = null;


        // InternalKactors.g:1999:3: ( ( (lv_match_0_0= ruleMatch ) ) )
        // InternalKactors.g:1999:3: ( (lv_match_0_0= ruleMatch ) )
        {
        // InternalKactors.g:1999:3: ( (lv_match_0_0= ruleMatch ) )
        // InternalKactors.g:2000:4: (lv_match_0_0= ruleMatch )
        {
        // InternalKactors.g:2000:4: (lv_match_0_0= ruleMatch )
        // InternalKactors.g:2001:5: lv_match_0_0= ruleMatch
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
    // $ANTLR end synpred56_InternalKactors

    // $ANTLR start synpred58_InternalKactors
    public final void synpred58_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_matches_2_0 = null;

        EObject lv_matches_3_0 = null;


        // InternalKactors.g:2019:3: ( (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) )
        // InternalKactors.g:2019:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        {
        // InternalKactors.g:2019:3: (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' )
        // InternalKactors.g:2020:4: otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')'
        {
        otherlv_1=(Token)match(input,41,FOLLOW_30); if (state.failed) return ;
        // InternalKactors.g:2024:4: ( (lv_matches_2_0= ruleMatch ) )
        // InternalKactors.g:2025:5: (lv_matches_2_0= ruleMatch )
        {
        // InternalKactors.g:2025:5: (lv_matches_2_0= ruleMatch )
        // InternalKactors.g:2026:6: lv_matches_2_0= ruleMatch
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

        // InternalKactors.g:2043:4: ( (lv_matches_3_0= ruleMatch ) )*
        loop113:
        do {
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( ((LA113_0>=RULE_OBSERVABLE && LA113_0<=RULE_LOWERCASE_ID)||LA113_0==RULE_STRING||(LA113_0>=RULE_EXPR && LA113_0<=RULE_INT)||LA113_0==41||LA113_0==48||(LA113_0>=50 && LA113_0<=51)||(LA113_0>=55 && LA113_0<=57)||(LA113_0>=75 && LA113_0<=76)) ) {
                alt113=1;
            }


            switch (alt113) {
        	case 1 :
        	    // InternalKactors.g:2044:5: (lv_matches_3_0= ruleMatch )
        	    {
        	    // InternalKactors.g:2044:5: (lv_matches_3_0= ruleMatch )
        	    // InternalKactors.g:2045:6: lv_matches_3_0= ruleMatch
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
        	    break loop113;
            }
        } while (true);

        otherlv_4=(Token)match(input,42,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred58_InternalKactors

    // $ANTLR start synpred59_InternalKactors
    public final void synpred59_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_statement_5_0 = null;


        // InternalKactors.g:2068:3: ( ( (lv_statement_5_0= ruleStatement ) ) )
        // InternalKactors.g:2068:3: ( (lv_statement_5_0= ruleStatement ) )
        {
        // InternalKactors.g:2068:3: ( (lv_statement_5_0= ruleStatement ) )
        // InternalKactors.g:2069:4: (lv_statement_5_0= ruleStatement )
        {
        // InternalKactors.g:2069:4: (lv_statement_5_0= ruleStatement )
        // InternalKactors.g:2070:5: lv_statement_5_0= ruleStatement
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
    // $ANTLR end synpred59_InternalKactors

    // $ANTLR start synpred62_InternalKactors
    public final void synpred62_InternalKactors_fragment() throws RecognitionException {   
        Token lv_boolean_3_1=null;
        Token lv_boolean_3_2=null;
        Token otherlv_4=null;
        EObject lv_body_5_0 = null;


        // InternalKactors.g:2180:3: ( ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2180:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2180:3: ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) )
        // InternalKactors.g:2181:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) )
        {
        // InternalKactors.g:2181:4: ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) )
        // InternalKactors.g:2182:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        {
        // InternalKactors.g:2182:5: ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) )
        // InternalKactors.g:2183:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        {
        // InternalKactors.g:2183:6: (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' )
        int alt114=2;
        int LA114_0 = input.LA(1);

        if ( (LA114_0==50) ) {
            alt114=1;
        }
        else if ( (LA114_0==51) ) {
            alt114=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 114, 0, input);

            throw nvae;
        }
        switch (alt114) {
            case 1 :
                // InternalKactors.g:2184:7: lv_boolean_3_1= 'true'
                {
                lv_boolean_3_1=(Token)match(input,50,FOLLOW_32); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:2195:7: lv_boolean_3_2= 'false'
                {
                lv_boolean_3_2=(Token)match(input,51,FOLLOW_32); if (state.failed) return ;

                }
                break;

        }


        }


        }

        otherlv_4=(Token)match(input,49,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2212:4: ( (lv_body_5_0= ruleStatementList ) )
        // InternalKactors.g:2213:5: (lv_body_5_0= ruleStatementList )
        {
        // InternalKactors.g:2213:5: (lv_body_5_0= ruleStatementList )
        // InternalKactors.g:2214:6: lv_body_5_0= ruleStatementList
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
    // $ANTLR end synpred62_InternalKactors

    // $ANTLR start synpred66_InternalKactors
    public final void synpred66_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        EObject lv_literal_15_0 = null;

        EObject lv_body_17_0 = null;


        // InternalKactors.g:2365:3: ( ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2365:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2365:3: ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) )
        // InternalKactors.g:2366:4: ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) )
        {
        // InternalKactors.g:2366:4: ( (lv_literal_15_0= ruleLiteral ) )
        // InternalKactors.g:2367:5: (lv_literal_15_0= ruleLiteral )
        {
        // InternalKactors.g:2367:5: (lv_literal_15_0= ruleLiteral )
        // InternalKactors.g:2368:6: lv_literal_15_0= ruleLiteral
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

        otherlv_16=(Token)match(input,49,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2389:4: ( (lv_body_17_0= ruleStatementList ) )
        // InternalKactors.g:2390:5: (lv_body_17_0= ruleStatementList )
        {
        // InternalKactors.g:2390:5: (lv_body_17_0= ruleStatementList )
        // InternalKactors.g:2391:6: lv_body_17_0= ruleStatementList
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
    // $ANTLR end synpred66_InternalKactors

    // $ANTLR start synpred67_InternalKactors
    public final void synpred67_InternalKactors_fragment() throws RecognitionException {   
        Token lv_text_18_0=null;
        Token otherlv_19=null;
        EObject lv_body_20_0 = null;


        // InternalKactors.g:2410:3: ( ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2410:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2410:3: ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) )
        // InternalKactors.g:2411:4: ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) )
        {
        // InternalKactors.g:2411:4: ( (lv_text_18_0= RULE_STRING ) )
        // InternalKactors.g:2412:5: (lv_text_18_0= RULE_STRING )
        {
        // InternalKactors.g:2412:5: (lv_text_18_0= RULE_STRING )
        // InternalKactors.g:2413:6: lv_text_18_0= RULE_STRING
        {
        lv_text_18_0=(Token)match(input,RULE_STRING,FOLLOW_32); if (state.failed) return ;

        }


        }

        otherlv_19=(Token)match(input,49,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2433:4: ( (lv_body_20_0= ruleStatementList ) )
        // InternalKactors.g:2434:5: (lv_body_20_0= ruleStatementList )
        {
        // InternalKactors.g:2434:5: (lv_body_20_0= ruleStatementList )
        // InternalKactors.g:2435:6: lv_body_20_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_6_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_20_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred67_InternalKactors

    // $ANTLR start synpred71_InternalKactors
    public final void synpred71_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:2541:5: ( 'to' )
        // InternalKactors.g:2541:6: 'to'
        {
        match(input,54,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred71_InternalKactors

    // $ANTLR start synpred75_InternalKactors
    public final void synpred75_InternalKactors_fragment() throws RecognitionException {   
        Token lv_leftLimit_25_0=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Token lv_rightLimit_29_0=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        EObject lv_int0_24_0 = null;

        EObject lv_int1_28_0 = null;

        EObject lv_body_32_0 = null;


        // InternalKactors.g:2499:3: ( ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2499:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2499:3: ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) )
        // InternalKactors.g:2500:4: ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) )
        {
        // InternalKactors.g:2500:4: ( (lv_int0_24_0= ruleNumber ) )
        // InternalKactors.g:2501:5: (lv_int0_24_0= ruleNumber )
        {
        // InternalKactors.g:2501:5: (lv_int0_24_0= ruleNumber )
        // InternalKactors.g:2502:6: lv_int0_24_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getInt0NumberParserRuleCall_8_0_0());
          					
        }
        pushFollow(FOLLOW_33);
        lv_int0_24_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:2519:4: ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )?
        int alt115=3;
        int LA115_0 = input.LA(1);

        if ( (LA115_0==52) ) {
            alt115=1;
        }
        else if ( (LA115_0==53) ) {
            alt115=2;
        }
        switch (alt115) {
            case 1 :
                // InternalKactors.g:2520:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                {
                // InternalKactors.g:2520:5: ( (lv_leftLimit_25_0= 'inclusive' ) )
                // InternalKactors.g:2521:6: (lv_leftLimit_25_0= 'inclusive' )
                {
                // InternalKactors.g:2521:6: (lv_leftLimit_25_0= 'inclusive' )
                // InternalKactors.g:2522:7: lv_leftLimit_25_0= 'inclusive'
                {
                lv_leftLimit_25_0=(Token)match(input,52,FOLLOW_34); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:2535:5: otherlv_26= 'exclusive'
                {
                otherlv_26=(Token)match(input,53,FOLLOW_34); if (state.failed) return ;

                }
                break;

        }

        // InternalKactors.g:2540:4: ( ( 'to' )=>otherlv_27= 'to' )
        // InternalKactors.g:2541:5: ( 'to' )=>otherlv_27= 'to'
        {
        otherlv_27=(Token)match(input,54,FOLLOW_35); if (state.failed) return ;

        }

        // InternalKactors.g:2547:4: ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) )
        // InternalKactors.g:2548:5: ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber )
        {
        // InternalKactors.g:2552:5: (lv_int1_28_0= ruleNumber )
        // InternalKactors.g:2553:6: lv_int1_28_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getInt1NumberParserRuleCall_8_3_0());
          					
        }
        pushFollow(FOLLOW_36);
        lv_int1_28_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:2570:4: ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )?
        int alt116=3;
        int LA116_0 = input.LA(1);

        if ( (LA116_0==52) ) {
            alt116=1;
        }
        else if ( (LA116_0==53) ) {
            alt116=2;
        }
        switch (alt116) {
            case 1 :
                // InternalKactors.g:2571:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                {
                // InternalKactors.g:2571:5: ( (lv_rightLimit_29_0= 'inclusive' ) )
                // InternalKactors.g:2572:6: (lv_rightLimit_29_0= 'inclusive' )
                {
                // InternalKactors.g:2572:6: (lv_rightLimit_29_0= 'inclusive' )
                // InternalKactors.g:2573:7: lv_rightLimit_29_0= 'inclusive'
                {
                lv_rightLimit_29_0=(Token)match(input,52,FOLLOW_32); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:2586:5: otherlv_30= 'exclusive'
                {
                otherlv_30=(Token)match(input,53,FOLLOW_32); if (state.failed) return ;

                }
                break;

        }

        otherlv_31=(Token)match(input,49,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2595:4: ( (lv_body_32_0= ruleStatementList ) )
        // InternalKactors.g:2596:5: (lv_body_32_0= ruleStatementList )
        {
        // InternalKactors.g:2596:5: (lv_body_32_0= ruleStatementList )
        // InternalKactors.g:2597:6: lv_body_32_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_8_6_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_32_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred75_InternalKactors

    // $ANTLR start synpred77_InternalKactors
    public final void synpred77_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_38=null;
        EObject lv_quantity_37_0 = null;

        EObject lv_body_39_0 = null;


        // InternalKactors.g:2665:3: ( ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2665:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2665:3: ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) )
        // InternalKactors.g:2666:4: ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) )
        {
        // InternalKactors.g:2666:4: ( (lv_quantity_37_0= ruleQuantity ) )
        // InternalKactors.g:2667:5: (lv_quantity_37_0= ruleQuantity )
        {
        // InternalKactors.g:2667:5: (lv_quantity_37_0= ruleQuantity )
        // InternalKactors.g:2668:6: lv_quantity_37_0= ruleQuantity
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_10_0_0());
          					
        }
        pushFollow(FOLLOW_32);
        lv_quantity_37_0=ruleQuantity();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_38=(Token)match(input,49,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2689:4: ( (lv_body_39_0= ruleStatementList ) )
        // InternalKactors.g:2690:5: (lv_body_39_0= ruleStatementList )
        {
        // InternalKactors.g:2690:5: (lv_body_39_0= ruleStatementList )
        // InternalKactors.g:2691:6: lv_body_39_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_10_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_39_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred77_InternalKactors

    // $ANTLR start synpred78_InternalKactors
    public final void synpred78_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_41=null;
        EObject lv_date_40_0 = null;

        EObject lv_body_42_0 = null;


        // InternalKactors.g:2710:3: ( ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) )
        // InternalKactors.g:2710:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
        {
        // InternalKactors.g:2710:3: ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) )
        // InternalKactors.g:2711:4: ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) )
        {
        // InternalKactors.g:2711:4: ( (lv_date_40_0= ruleDate ) )
        // InternalKactors.g:2712:5: (lv_date_40_0= ruleDate )
        {
        // InternalKactors.g:2712:5: (lv_date_40_0= ruleDate )
        // InternalKactors.g:2713:6: lv_date_40_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getDateDateParserRuleCall_11_0_0());
          					
        }
        pushFollow(FOLLOW_32);
        lv_date_40_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_41=(Token)match(input,49,FOLLOW_15); if (state.failed) return ;
        // InternalKactors.g:2734:4: ( (lv_body_42_0= ruleStatementList ) )
        // InternalKactors.g:2735:5: (lv_body_42_0= ruleStatementList )
        {
        // InternalKactors.g:2735:5: (lv_body_42_0= ruleStatementList )
        // InternalKactors.g:2736:6: lv_body_42_0= ruleStatementList
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyStatementListParserRuleCall_11_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_42_0=ruleStatementList();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred78_InternalKactors

    // $ANTLR start synpred98_InternalKactors
    public final void synpred98_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:3437:5: ( 'to' )
        // InternalKactors.g:3437:6: 'to'
        {
        match(input,54,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred98_InternalKactors

    // $ANTLR start synpred125_InternalKactors
    public final void synpred125_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4156:5: ( 'to' )
        // InternalKactors.g:4156:6: 'to'
        {
        match(input,54,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred125_InternalKactors

    // $ANTLR start synpred140_InternalKactors
    public final void synpred140_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_number_0_0 = null;


        // InternalKactors.g:4522:3: ( ( (lv_number_0_0= ruleNumber ) ) )
        // InternalKactors.g:4522:3: ( (lv_number_0_0= ruleNumber ) )
        {
        // InternalKactors.g:4522:3: ( (lv_number_0_0= ruleNumber ) )
        // InternalKactors.g:4523:4: (lv_number_0_0= ruleNumber )
        {
        // InternalKactors.g:4523:4: (lv_number_0_0= ruleNumber )
        // InternalKactors.g:4524:5: lv_number_0_0= ruleNumber
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
    // $ANTLR end synpred140_InternalKactors

    // $ANTLR start synpred143_InternalKactors
    public final void synpred143_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_date_5_0 = null;


        // InternalKactors.g:4606:3: ( ( (lv_date_5_0= ruleDate ) ) )
        // InternalKactors.g:4606:3: ( (lv_date_5_0= ruleDate ) )
        {
        // InternalKactors.g:4606:3: ( (lv_date_5_0= ruleDate ) )
        // InternalKactors.g:4607:4: (lv_date_5_0= ruleDate )
        {
        // InternalKactors.g:4607:4: (lv_date_5_0= ruleDate )
        // InternalKactors.g:4608:5: lv_date_5_0= ruleDate
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
    // $ANTLR end synpred143_InternalKactors

    // $ANTLR start synpred172_InternalKactors
    public final void synpred172_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5426:4: ( ( RULE_INT ) )
        // InternalKactors.g:5426:5: ( RULE_INT )
        {
        // InternalKactors.g:5426:5: ( RULE_INT )
        // InternalKactors.g:5427:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred172_InternalKactors

    // $ANTLR start synpred173_InternalKactors
    public final void synpred173_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5448:4: ( ( 'l' ) )
        // InternalKactors.g:5448:5: ( 'l' )
        {
        // InternalKactors.g:5448:5: ( 'l' )
        // InternalKactors.g:5449:5: 'l'
        {
        match(input,77,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred173_InternalKactors

    // $ANTLR start synpred174_InternalKactors
    public final void synpred174_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5466:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5466:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5466:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:5467:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:5467:5: ( ( '.' ) )
        // InternalKactors.g:5468:6: ( '.' )
        {
        // InternalKactors.g:5468:6: ( '.' )
        // InternalKactors.g:5469:7: '.'
        {
        match(input,67,FOLLOW_10); if (state.failed) return ;

        }


        }

        // InternalKactors.g:5472:5: ( ( RULE_INT ) )
        // InternalKactors.g:5473:6: ( RULE_INT )
        {
        // InternalKactors.g:5473:6: ( RULE_INT )
        // InternalKactors.g:5474:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred174_InternalKactors

    // $ANTLR start synpred178_InternalKactors
    public final void synpred178_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5515:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5515:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5515:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:5516:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:5516:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:5517:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:5517:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:5518:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=78 && input.LA(1)<=79) ) {
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

        // InternalKactors.g:5525:5: ( '+' | ( ( '-' ) ) )?
        int alt132=3;
        int LA132_0 = input.LA(1);

        if ( (LA132_0==75) ) {
            alt132=1;
        }
        else if ( (LA132_0==76) ) {
            alt132=2;
        }
        switch (alt132) {
            case 1 :
                // InternalKactors.g:5526:6: '+'
                {
                match(input,75,FOLLOW_10); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:5528:6: ( ( '-' ) )
                {
                // InternalKactors.g:5528:6: ( ( '-' ) )
                // InternalKactors.g:5529:7: ( '-' )
                {
                // InternalKactors.g:5529:7: ( '-' )
                // InternalKactors.g:5530:8: '-'
                {
                match(input,76,FOLLOW_10); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:5534:5: ( ( RULE_INT ) )
        // InternalKactors.g:5535:6: ( RULE_INT )
        {
        // InternalKactors.g:5535:6: ( RULE_INT )
        // InternalKactors.g:5536:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred178_InternalKactors

    // $ANTLR start synpred195_InternalKactors
    public final void synpred195_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKactors.g:5970:4: (kw= '-' )
        // InternalKactors.g:5970:4: kw= '-'
        {
        kw=(Token)match(input,76,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred195_InternalKactors

    // $ANTLR start synpred196_InternalKactors
    public final void synpred196_InternalKactors_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKactors.g:5977:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKactors.g:5977:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred196_InternalKactors

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
    public final boolean synpred10_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred172_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred172_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred178_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred178_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred53_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred53_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred54_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred54_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred11_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred32_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred32_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred98_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred98_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred140_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred140_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred174_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred174_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred45_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred45_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred195_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred195_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred77_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred77_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred125_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred125_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred196_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred196_InternalKactors_fragment(); // can never throw exception
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


    protected DFA13 dfa13 = new DFA13(this);
    protected DFA20 dfa20 = new DFA20(this);
    protected DFA25 dfa25 = new DFA25(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA29 dfa29 = new DFA29(this);
    protected DFA32 dfa32 = new DFA32(this);
    protected DFA36 dfa36 = new DFA36(this);
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA51 dfa51 = new DFA51(this);
    protected DFA53 dfa53 = new DFA53(this);
    protected DFA62 dfa62 = new DFA62(this);
    protected DFA68 dfa68 = new DFA68(this);
    protected DFA72 dfa72 = new DFA72(this);
    static final String dfa_1s = "\14\uffff";
    static final String dfa_2s = "\1\1\13\uffff";
    static final String dfa_3s = "\1\16\13\uffff";
    static final String dfa_4s = "\1\47\13\uffff";
    static final String dfa_5s = "\1\uffff\1\13\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12";
    static final String dfa_6s = "\1\0\13\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\15\uffff\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\1",
            "",
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

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 288:6: ( ({...}? => ( ({...}? => (otherlv_7= 'import' ( (lv_imports_8_0= rulePathName ) ) (otherlv_9= ',' ( (lv_imports_10_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_11= 'worldview' ( (lv_worldview_12_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= 'observable' ( ( (lv_observable_14_0= RULE_OBSERVABLE ) ) | ( (lv_observables_15_0= ruleList ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'label' ( ( (lv_label_17_1= RULE_LOWERCASE_ID | lv_label_17_2= RULE_ID | lv_label_17_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_18= 'description' ( (lv_description_19_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'permissions' ( (lv_permissions_21_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= 'author' ( (lv_authors_23_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_24= 'version' ( (lv_version_25_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_26= 'created' ( (lv_created_27_0= ruleDate ) ) ( (lv_createcomment_28_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_29= 'modified' ( (lv_modified_30_0= ruleDate ) ) ( (lv_modcomment_31_0= RULE_STRING ) )? ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA13_0 = input.LA(1);

                         
                        int index13_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA13_0==EOF||LA13_0==RULE_ANNOTATION_ID||LA13_0==39) ) {s = 1;}

                        else if ( LA13_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {s = 2;}

                        else if ( LA13_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {s = 3;}

                        else if ( LA13_0 == 31 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {s = 4;}

                        else if ( LA13_0 == 32 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {s = 5;}

                        else if ( LA13_0 == 33 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {s = 6;}

                        else if ( LA13_0 == 34 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {s = 7;}

                        else if ( LA13_0 == 35 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {s = 8;}

                        else if ( LA13_0 == 36 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {s = 9;}

                        else if ( LA13_0 == 37 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {s = 10;}

                        else if ( LA13_0 == 38 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 9) ) {s = 11;}

                         
                        input.seek(index13_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 13, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\42\uffff";
    static final String dfa_9s = "\1\2\41\uffff";
    static final String dfa_10s = "\1\4\1\0\40\uffff";
    static final String dfa_11s = "\1\114\1\0\40\uffff";
    static final String dfa_12s = "\2\uffff\1\2\36\uffff\1\1";
    static final String dfa_13s = "\1\uffff\1\0\40\uffff}>";
    static final String[] dfa_14s = {
            "\2\2\1\uffff\6\2\1\uffff\2\2\15\uffff\1\2\11\uffff\2\2\1\1\7\2\1\uffff\2\2\3\uffff\4\2\2\uffff\1\2\1\uffff\1\2\13\uffff\2\2",
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
            ""
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "1020:5: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA20_1 = input.LA(1);

                         
                        int index20_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred32_InternalKactors()) ) {s = 33;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index20_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 20, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\26\uffff";
    static final String dfa_16s = "\1\4\2\0\23\uffff";
    static final String dfa_17s = "\1\114\2\0\23\uffff";
    static final String dfa_18s = "\3\uffff\1\3\1\4\1\5\1\6\1\7\1\10\13\uffff\1\1\1\2";
    static final String dfa_19s = "\1\uffff\1\0\1\1\23\uffff}>";
    static final String[] dfa_20s = {
            "\1\10\1\1\1\uffff\1\10\1\3\1\10\2\uffff\1\10\2\uffff\1\10\31\uffff\1\2\1\uffff\1\4\1\uffff\1\5\1\6\1\7\2\uffff\2\10\6\uffff\1\10\2\uffff\1\10\1\uffff\1\10\13\uffff\2\10",
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

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1219:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_group_1_0= ruleStatementGroup ) ) | ( (lv_text_2_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_3_0= ruleIfStatement ) ) | ( (lv_while_4_0= ruleWhileStatement ) ) | ( (lv_do_5_0= ruleDoStatement ) ) | ( (lv_for_6_0= ruleForStatement ) ) | ( (lv_value_7_0= ruleValue ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA25_1 = input.LA(1);

                         
                        int index25_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalKactors()) ) {s = 20;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index25_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA25_2 = input.LA(1);

                         
                        int index25_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalKactors()) ) {s = 20;}

                        else if ( (synpred38_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index25_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 25, _s, input);
            error(nvae);
            throw nvae;
        }
    }

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
            return "1401:3: ( ( (lv_verb_1_0= ruleMessageCall ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) | ( (lv_text_3_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_4_0= ruleIfStatement ) ) | ( (lv_while_5_0= ruleWhileStatement ) ) | ( (lv_do_6_0= ruleDoStatement ) ) | ( (lv_for_7_0= ruleForStatement ) ) | ( (lv_value_8_0= ruleValue ) ) )";
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
                        if ( (synpred44_InternalKactors()) ) {s = 20;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index26_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_2 = input.LA(1);

                         
                        int index26_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred44_InternalKactors()) ) {s = 20;}

                        else if ( (synpred45_InternalKactors()) ) {s = 21;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index26_2);
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
    static final String dfa_21s = "\21\uffff";
    static final String dfa_22s = "\1\4\2\0\16\uffff";
    static final String dfa_23s = "\1\114\2\0\16\uffff";
    static final String dfa_24s = "\3\uffff\1\2\13\uffff\1\1\1\3";
    static final String dfa_25s = "\1\uffff\1\0\1\1\16\uffff}>";
    static final String[] dfa_26s = {
            "\1\3\1\1\1\uffff\1\3\1\uffff\1\3\2\uffff\1\3\2\uffff\1\3\31\uffff\1\2\10\uffff\2\3\6\uffff\1\3\2\uffff\1\3\1\uffff\1\3\13\uffff\2\3",
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

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[][] dfa_26 = unpackEncodedStringArray(dfa_26s);

    class DFA29 extends DFA {

        public DFA29(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 29;
            this.eot = dfa_21;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "1711:2: ( ( (lv_verb_0_0= ruleMessageCall ) ) | ( (lv_value_1_0= ruleValue ) ) | ( (lv_group_2_0= ruleStatementGroup ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA29_1 = input.LA(1);

                         
                        int index29_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred53_InternalKactors()) ) {s = 15;}

                        else if ( (synpred54_InternalKactors()) ) {s = 3;}

                         
                        input.seek(index29_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA29_2 = input.LA(1);

                         
                        int index29_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred53_InternalKactors()) ) {s = 15;}

                        else if ( (synpred54_InternalKactors()) ) {s = 3;}

                        else if ( (true) ) {s = 16;}

                         
                        input.seek(index29_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 29, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_27s = "\34\uffff";
    static final String dfa_28s = "\1\4\3\0\2\uffff\6\0\1\uffff\1\0\16\uffff";
    static final String dfa_29s = "\1\114\3\0\2\uffff\6\0\1\uffff\1\0\16\uffff";
    static final String dfa_30s = "\4\uffff\1\1\14\uffff\1\3\10\uffff\1\2\1\4";
    static final String dfa_31s = "\1\uffff\1\0\1\1\1\2\2\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\11\16\uffff}>";
    static final String[] dfa_32s = {
            "\1\6\1\1\1\uffff\1\12\1\21\1\15\2\4\1\11\2\uffff\1\21\31\uffff\1\13\1\uffff\1\21\1\uffff\3\21\1\4\1\uffff\1\2\1\3\3\uffff\3\4\1\21\2\uffff\1\21\1\uffff\1\21\13\uffff\1\7\1\10",
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
            ""
    };

    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final char[] dfa_28 = DFA.unpackEncodedStringToUnsignedChars(dfa_28s);
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[][] dfa_32 = unpackEncodedStringArray(dfa_32s);

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = dfa_27;
            this.eof = dfa_27;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_31;
            this.transition = dfa_32;
        }
        public String getDescription() {
            return "1998:2: ( ( (lv_match_0_0= ruleMatch ) ) | (otherlv_1= '(' ( (lv_matches_2_0= ruleMatch ) ) ( (lv_matches_3_0= ruleMatch ) )* otherlv_4= ')' ) | ( (lv_statement_5_0= ruleStatement ) ) | (otherlv_6= '(' ( (lv_statements_7_0= ruleStatementList ) ) otherlv_8= ')' ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA32_1 = input.LA(1);

                         
                        int index32_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA32_2 = input.LA(1);

                         
                        int index32_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA32_3 = input.LA(1);

                         
                        int index32_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA32_6 = input.LA(1);

                         
                        int index32_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA32_7 = input.LA(1);

                         
                        int index32_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA32_8 = input.LA(1);

                         
                        int index32_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA32_9 = input.LA(1);

                         
                        int index32_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA32_10 = input.LA(1);

                         
                        int index32_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA32_11 = input.LA(1);

                         
                        int index32_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred58_InternalKactors()) ) {s = 26;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index32_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA32_13 = input.LA(1);

                         
                        int index32_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred56_InternalKactors()) ) {s = 4;}

                        else if ( (synpred59_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index32_13);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 32, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_33s = "\27\uffff";
    static final String dfa_34s = "\1\4\1\uffff\2\0\3\uffff\4\0\14\uffff";
    static final String dfa_35s = "\1\114\1\uffff\2\0\3\uffff\4\0\14\uffff";
    static final String dfa_36s = "\1\uffff\1\1\2\uffff\1\3\1\4\1\5\4\uffff\1\10\1\12\1\15\1\16\1\17\1\20\1\2\1\6\1\11\1\13\1\14\1\7";
    static final String dfa_37s = "\2\uffff\1\0\1\1\3\uffff\1\2\1\3\1\4\1\5\14\uffff}>";
    static final String[] dfa_38s = {
            "\1\6\1\1\1\uffff\1\12\1\uffff\1\15\1\4\1\5\1\11\34\uffff\1\13\6\uffff\1\14\1\uffff\1\2\1\3\3\uffff\1\16\1\17\1\20\21\uffff\1\7\1\10",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
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
            ""
    };

    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final char[] dfa_34 = DFA.unpackEncodedStringToUnsignedChars(dfa_34s);
    static final char[] dfa_35 = DFA.unpackEncodedStringToUnsignedChars(dfa_35s);
    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final short[][] dfa_38 = unpackEncodedStringArray(dfa_38s);

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = dfa_33;
            this.eof = dfa_33;
            this.min = dfa_34;
            this.max = dfa_35;
            this.accept = dfa_36;
            this.special = dfa_37;
            this.transition = dfa_38;
        }
        public String getDescription() {
            return "2135:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleStatementList ) ) ) | ( ( ( (lv_boolean_3_1= 'true' | lv_boolean_3_2= 'false' ) ) ) otherlv_4= '->' ( (lv_body_5_0= ruleStatementList ) ) ) | ( ( (lv_type_6_0= RULE_CAMELCASE_ID ) ) otherlv_7= '->' ( (lv_body_8_0= ruleStatementList ) ) ) | ( ( (lv_regexp_9_0= RULE_REGEXP ) ) otherlv_10= '->' ( (lv_body_11_0= ruleStatementList ) ) ) | ( ( (lv_observable_12_0= RULE_OBSERVABLE ) ) otherlv_13= '->' ( (lv_body_14_0= ruleStatementList ) ) ) | ( ( (lv_literal_15_0= ruleLiteral ) ) otherlv_16= '->' ( (lv_body_17_0= ruleStatementList ) ) ) | ( ( (lv_text_18_0= RULE_STRING ) ) otherlv_19= '->' ( (lv_body_20_0= ruleStatementList ) ) ) | ( ( (lv_arguments_21_0= ruleArgumentDeclaration ) ) otherlv_22= '->' ( (lv_body_23_0= ruleStatementList ) ) ) | ( ( (lv_int0_24_0= ruleNumber ) ) ( ( (lv_leftLimit_25_0= 'inclusive' ) ) | otherlv_26= 'exclusive' )? ( ( 'to' )=>otherlv_27= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_28_0= ruleNumber ) ) ( ( (lv_rightLimit_29_0= 'inclusive' ) ) | otherlv_30= 'exclusive' )? otherlv_31= '->' ( (lv_body_32_0= ruleStatementList ) ) ) | (otherlv_33= 'in' ( (lv_set_34_0= ruleList ) ) otherlv_35= '->' ( (lv_body_36_0= ruleStatementList ) ) ) | ( ( (lv_quantity_37_0= ruleQuantity ) ) otherlv_38= '->' ( (lv_body_39_0= ruleStatementList ) ) ) | ( ( (lv_date_40_0= ruleDate ) ) otherlv_41= '->' ( (lv_body_42_0= ruleStatementList ) ) ) | ( ( (lv_expr_43_0= RULE_EXPR ) ) otherlv_44= '->' ( (lv_body_45_0= ruleStatementList ) ) ) | ( ( (lv_nodata_46_0= 'unknown' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleStatementList ) ) ) | ( ( (lv_star_49_0= '*' ) ) otherlv_50= '->' ( (lv_body_51_0= ruleStatementList ) ) ) | ( ( (lv_anything_52_0= '#' ) ) otherlv_53= '->' ( (lv_body_54_0= ruleStatementList ) ) ) )";
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
                        if ( (synpred62_InternalKactors()) ) {s = 17;}

                        else if ( (synpred66_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index36_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA36_3 = input.LA(1);

                         
                        int index36_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred62_InternalKactors()) ) {s = 17;}

                        else if ( (synpred66_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index36_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA36_7 = input.LA(1);

                         
                        int index36_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred66_InternalKactors()) ) {s = 18;}

                        else if ( (synpred75_InternalKactors()) ) {s = 19;}

                        else if ( (synpred77_InternalKactors()) ) {s = 20;}

                         
                        input.seek(index36_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA36_8 = input.LA(1);

                         
                        int index36_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred66_InternalKactors()) ) {s = 18;}

                        else if ( (synpred75_InternalKactors()) ) {s = 19;}

                        else if ( (synpred77_InternalKactors()) ) {s = 20;}

                         
                        input.seek(index36_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA36_9 = input.LA(1);

                         
                        int index36_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred66_InternalKactors()) ) {s = 18;}

                        else if ( (synpred75_InternalKactors()) ) {s = 19;}

                        else if ( (synpred77_InternalKactors()) ) {s = 20;}

                        else if ( (synpred78_InternalKactors()) ) {s = 21;}

                         
                        input.seek(index36_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA36_10 = input.LA(1);

                         
                        int index36_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred66_InternalKactors()) ) {s = 18;}

                        else if ( (synpred67_InternalKactors()) ) {s = 22;}

                         
                        input.seek(index36_10);
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
    static final String dfa_39s = "\6\uffff";
    static final String dfa_40s = "\1\uffff\1\2\3\uffff\1\2";
    static final String dfa_41s = "\1\5\1\4\1\uffff\1\5\1\uffff\1\4";
    static final String dfa_42s = "\1\20\1\114\1\uffff\1\20\1\uffff\1\114";
    static final String dfa_43s = "\2\uffff\1\1\1\uffff\1\2\1\uffff";
    static final String dfa_44s = "\6\uffff}>";
    static final String[] dfa_45s = {
            "\1\1\12\uffff\1\2",
            "\2\2\1\uffff\6\2\1\uffff\2\2\15\uffff\1\2\11\uffff\1\2\1\uffff\10\2\1\uffff\2\2\3\uffff\5\2\1\4\3\2\2\uffff\1\2\1\3\7\uffff\2\2",
            "",
            "\1\5\12\uffff\1\2",
            "",
            "\2\2\1\uffff\6\2\1\uffff\2\2\15\uffff\1\2\11\uffff\1\2\1\uffff\10\2\1\uffff\2\2\3\uffff\5\2\1\4\3\2\2\uffff\1\2\1\3\7\uffff\2\2"
    };

    static final short[] dfa_39 = DFA.unpackEncodedString(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final char[] dfa_41 = DFA.unpackEncodedStringToUnsignedChars(dfa_41s);
    static final char[] dfa_42 = DFA.unpackEncodedStringToUnsignedChars(dfa_42s);
    static final short[] dfa_43 = DFA.unpackEncodedString(dfa_43s);
    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final short[][] dfa_45 = unpackEncodedStringArray(dfa_45s);

    class DFA40 extends DFA {

        public DFA40(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 40;
            this.eot = dfa_39;
            this.eof = dfa_40;
            this.min = dfa_41;
            this.max = dfa_42;
            this.accept = dfa_43;
            this.special = dfa_44;
            this.transition = dfa_45;
        }
        public String getDescription() {
            return "3032:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )";
        }
    }

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = dfa_39;
            this.eof = dfa_40;
            this.min = dfa_41;
            this.max = dfa_42;
            this.accept = dfa_43;
            this.special = dfa_44;
            this.transition = dfa_45;
        }
        public String getDescription() {
            return "3061:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )";
        }
    }
    static final String dfa_46s = "\4\uffff\1\20\7\uffff\1\20\5\uffff\1\20\2\uffff\1\20";
    static final String dfa_47s = "\1\4\1\uffff\2\14\1\50\7\uffff\1\50\3\14\2\uffff\1\50\2\14\1\50";
    static final String dfa_48s = "\1\114\1\uffff\2\14\1\117\7\uffff\1\117\1\14\2\114\2\uffff\1\117\2\14\1\66";
    static final String dfa_49s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\4\uffff\1\3\1\2\4\uffff";
    static final String dfa_50s = "\26\uffff}>";
    static final String[] dfa_51s = {
            "\1\7\1\10\1\uffff\1\6\4\uffff\1\4\43\uffff\1\5\1\uffff\2\1\3\uffff\1\12\1\13\3\uffff\1\11\11\uffff\5\11\1\2\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\20\13\uffff\3\21\14\uffff\1\15\11\uffff\1\14\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\20\13\uffff\3\21\14\uffff\1\15\12\uffff\1\16\1\17",
            "\1\22",
            "\1\25\76\uffff\1\23\1\24",
            "\1\25\76\uffff\1\23\1\24",
            "",
            "",
            "\1\20\13\uffff\3\21\27\uffff\1\16\1\17",
            "\1\25",
            "\1\25",
            "\1\20\13\uffff\3\21"
    };
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final char[] dfa_47 = DFA.unpackEncodedStringToUnsignedChars(dfa_47s);
    static final char[] dfa_48 = DFA.unpackEncodedStringToUnsignedChars(dfa_48s);
    static final short[] dfa_49 = DFA.unpackEncodedString(dfa_49s);
    static final short[] dfa_50 = DFA.unpackEncodedString(dfa_50s);
    static final short[][] dfa_51 = unpackEncodedStringArray(dfa_51s);

    class DFA51 extends DFA {

        public DFA51(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 51;
            this.eot = dfa_15;
            this.eof = dfa_46;
            this.min = dfa_47;
            this.max = dfa_48;
            this.accept = dfa_49;
            this.special = dfa_50;
            this.transition = dfa_51;
        }
        public String getDescription() {
            return "3362:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )";
        }
    }
    static final String dfa_52s = "\2\uffff\1\3\2\uffff\1\3";
    static final String dfa_53s = "\1\4\1\uffff\1\15\1\uffff\1\4\1\15";
    static final String dfa_54s = "\1\114\1\uffff\1\101\1\uffff\1\114\1\101";
    static final String dfa_55s = "\1\uffff\1\1\1\uffff\1\2\2\uffff";
    static final String[] dfa_56s = {
            "\1\3\1\1\1\uffff\1\2\1\uffff\1\3\2\uffff\1\3\43\uffff\1\3\1\uffff\2\3\3\uffff\3\3\2\uffff\1\3\11\uffff\7\3",
            "",
            "\1\1\17\uffff\1\3\42\uffff\1\3\1\4",
            "",
            "\1\3\1\1\1\uffff\1\5\1\uffff\1\3\2\uffff\1\3\43\uffff\1\3\1\uffff\2\3\3\uffff\3\3\2\uffff\1\3\11\uffff\7\3",
            "\1\1\17\uffff\1\3\42\uffff\1\3\1\4"
    };
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final char[] dfa_53 = DFA.unpackEncodedStringToUnsignedChars(dfa_53s);
    static final char[] dfa_54 = DFA.unpackEncodedStringToUnsignedChars(dfa_54s);
    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final short[][] dfa_56 = unpackEncodedStringArray(dfa_56s);

    class DFA53 extends DFA {

        public DFA53(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 53;
            this.eot = dfa_39;
            this.eof = dfa_52;
            this.min = dfa_53;
            this.max = dfa_54;
            this.accept = dfa_55;
            this.special = dfa_44;
            this.transition = dfa_56;
        }
        public String getDescription() {
            return "3737:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?";
        }
    }
    static final String dfa_57s = "\32\uffff";
    static final String dfa_58s = "\4\uffff\1\24\10\uffff\1\24\1\uffff\1\24\1\25\5\uffff\1\24\2\uffff\1\24";
    static final String dfa_59s = "\1\4\1\uffff\2\14\1\35\10\uffff\1\35\1\uffff\1\35\1\5\2\14\3\uffff\1\35\2\14\1\35";
    static final String dfa_60s = "\1\114\1\uffff\2\14\1\122\10\uffff\1\117\1\uffff\1\117\1\123\2\114\3\uffff\1\117\2\14\1\103";
    static final String dfa_61s = "\1\uffff\1\1\3\uffff\1\3\1\4\1\5\1\7\1\12\1\13\1\14\1\15\1\uffff\1\11\4\uffff\1\6\1\2\1\10\4\uffff";
    static final String dfa_62s = "\32\uffff}>";
    static final String[] dfa_63s = {
            "\1\6\2\uffff\1\5\1\uffff\1\11\2\uffff\1\4\43\uffff\1\10\1\uffff\2\1\3\uffff\1\12\1\13\1\14\2\uffff\1\7\11\uffff\5\7\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\1\24\26\uffff\3\23\11\uffff\2\24\1\25\1\20\10\uffff\1\16\1\17\1\21\1\22\3\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\24\26\uffff\3\23\11\uffff\2\24\1\25\1\20\11\uffff\1\17\1\21\1\22",
            "",
            "\1\24\26\uffff\3\23\11\uffff\2\24\1\25\1\20\12\uffff\1\21\1\22",
            "\1\25\4\uffff\1\25\1\uffff\1\26\3\uffff\1\25\14\uffff\1\25\13\uffff\1\25\16\uffff\1\25\7\uffff\3\25\20\uffff\1\25",
            "\1\31\76\uffff\1\27\1\30",
            "\1\31\76\uffff\1\27\1\30",
            "",
            "",
            "",
            "\1\24\26\uffff\3\23\11\uffff\2\24\2\25\12\uffff\1\21\1\22",
            "\1\31",
            "\1\31",
            "\1\24\26\uffff\3\23\11\uffff\2\24\2\25"
    };

    static final short[] dfa_57 = DFA.unpackEncodedString(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final char[] dfa_59 = DFA.unpackEncodedStringToUnsignedChars(dfa_59s);
    static final char[] dfa_60 = DFA.unpackEncodedStringToUnsignedChars(dfa_60s);
    static final short[] dfa_61 = DFA.unpackEncodedString(dfa_61s);
    static final short[] dfa_62 = DFA.unpackEncodedString(dfa_62s);
    static final short[][] dfa_63 = unpackEncodedStringArray(dfa_63s);

    class DFA62 extends DFA {

        public DFA62(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 62;
            this.eot = dfa_57;
            this.eof = dfa_58;
            this.min = dfa_59;
            this.max = dfa_60;
            this.accept = dfa_61;
            this.special = dfa_62;
            this.transition = dfa_63;
        }
        public String getDescription() {
            return "3982:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )";
        }
    }
    static final String dfa_64s = "\3\uffff\1\13\2\uffff\2\13\7\uffff\1\13\2\uffff\2\13\2\uffff";
    static final String dfa_65s = "\1\7\2\14\1\4\2\uffff\2\4\3\14\1\uffff\1\14\2\uffff\1\4\2\14\2\4\1\14\1\0";
    static final String dfa_66s = "\1\114\2\14\1\122\2\uffff\2\117\1\14\2\114\1\uffff\1\14\2\uffff\1\117\2\14\1\114\1\117\1\14\1\0";
    static final String dfa_67s = "\4\uffff\1\3\1\5\5\uffff\1\1\1\uffff\1\2\1\4\7\uffff";
    static final String dfa_68s = "\25\uffff\1\0}>";
    static final String[] dfa_69s = {
            "\1\4\4\uffff\1\3\45\uffff\2\5\27\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\2\13\1\uffff\6\13\1\uffff\2\13\15\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\1\13\1\14\1\7\1\11\1\12\3\16",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\15\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\2\13\1\7\1\11\1\12",
            "\2\13\1\uffff\6\13\1\uffff\2\13\15\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\3\uffff\1\10\7\uffff\2\13\1\uffff\1\11\1\12",
            "\1\17",
            "\1\22\76\uffff\1\20\1\21",
            "\1\22\76\uffff\1\20\1\21",
            "",
            "\1\23",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\15\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\13\uffff\2\13\1\uffff\1\11\1\12",
            "\1\22",
            "\1\22",
            "\2\13\1\uffff\6\13\1\uffff\2\13\15\uffff\1\13\11\uffff\1\13\1\uffff\13\13\2\uffff\1\15\4\13\2\uffff\3\13\13\uffff\2\13",
            "\2\13\1\uffff\6\13\1\uffff\2\13\15\uffff\1\13\11\uffff\1\13\1\uffff\22\13\2\uffff\1\13\1\uffff\1\13\2\uffff\2\13\7\uffff\1\13\1\24\3\13",
            "\1\25",
            "\1\uffff"
    };
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final char[] dfa_65 = DFA.unpackEncodedStringToUnsignedChars(dfa_65s);
    static final char[] dfa_66 = DFA.unpackEncodedStringToUnsignedChars(dfa_66s);
    static final short[] dfa_67 = DFA.unpackEncodedString(dfa_67s);
    static final short[] dfa_68 = DFA.unpackEncodedString(dfa_68s);
    static final short[][] dfa_69 = unpackEncodedStringArray(dfa_69s);

    class DFA68 extends DFA {

        public DFA68(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 68;
            this.eot = dfa_15;
            this.eof = dfa_64;
            this.min = dfa_65;
            this.max = dfa_66;
            this.accept = dfa_67;
            this.special = dfa_68;
            this.transition = dfa_69;
        }
        public String getDescription() {
            return "4521:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
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
                        if ( (synpred140_InternalKactors()) ) {s = 11;}

                        else if ( (synpred143_InternalKactors()) ) {s = 14;}

                         
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
    static final String dfa_70s = "\15\uffff";
    static final String dfa_71s = "\3\uffff\1\13\10\uffff\1\13";
    static final String dfa_72s = "\1\4\2\uffff\1\4\6\uffff\1\5\1\uffff\1\4";
    static final String dfa_73s = "\1\114\2\uffff\1\114\6\uffff\1\5\1\uffff\1\114";
    static final String dfa_74s = "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\3\1\uffff";
    static final String dfa_75s = "\15\uffff}>";
    static final String[] dfa_76s = {
            "\1\7\1\3\1\uffff\1\2\1\uffff\1\10\2\uffff\1\2\2\uffff\1\1\31\uffff\1\5\10\uffff\2\2\6\uffff\1\4\2\uffff\1\6\1\uffff\1\11\13\uffff\2\2",
            "",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\15\uffff\1\13\11\uffff\1\13\1\4\10\13\1\uffff\2\13\3\uffff\4\13\2\uffff\3\13\3\uffff\1\12\7\uffff\2\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14",
            "",
            "\2\13\1\uffff\6\13\1\uffff\2\13\15\uffff\1\13\11\uffff\1\13\1\4\10\13\1\uffff\2\13\3\uffff\4\13\2\uffff\3\13\3\uffff\1\12\7\uffff\2\13"
    };

    static final short[] dfa_70 = DFA.unpackEncodedString(dfa_70s);
    static final short[] dfa_71 = DFA.unpackEncodedString(dfa_71s);
    static final char[] dfa_72 = DFA.unpackEncodedStringToUnsignedChars(dfa_72s);
    static final char[] dfa_73 = DFA.unpackEncodedStringToUnsignedChars(dfa_73s);
    static final short[] dfa_74 = DFA.unpackEncodedString(dfa_74s);
    static final short[] dfa_75 = DFA.unpackEncodedString(dfa_75s);
    static final short[][] dfa_76 = unpackEncodedStringArray(dfa_76s);

    class DFA72 extends DFA {

        public DFA72(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 72;
            this.eot = dfa_70;
            this.eof = dfa_71;
            this.min = dfa_72;
            this.max = dfa_73;
            this.accept = dfa_74;
            this.special = dfa_75;
            this.transition = dfa_76;
        }
        public String getDescription() {
            return "4816:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= rulePathName ) ) | ( (lv_urn_3_0= ruleUrnId ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000008000004002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000007FD0000002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000007FF0000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000020000000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000007FD0000082L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000008000004000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000030000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0xA40CEA00000093B0L,0x0000000000001800L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000040000000020L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000040020000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0xA40CEA00000093B2L,0x0000000000001800L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000030000000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0xA40CEE00000093B0L,0x0000000000001800L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0xA78DEA0000009FB0L,0x0000000000001800L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x038D020000001EB0L,0x0000000000001800L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x038D060000001EB0L,0x0000000000001800L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0070000000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000001000L,0x0000000000001800L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0032000000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000011020L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0200010000000002L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000010020L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x518D0000000010B0L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x4000000020000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x118D0000000010B0L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0030000000000002L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x138D0000000012B0L,0x0000000000001FC1L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x138D0000000012B0L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x000000000000000CL});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0100020000010420L,0x0000000000080004L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x1000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0100060000010420L,0x0000000000080004L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0100000000000002L,0x0000000000080004L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000020000010420L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000002L,0x000000000000E008L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C008L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000000071000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000002L,0x000000000000000CL});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000010022L,0x0000000000001008L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000010022L,0x0000000000001000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000010022L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000082L});

}
