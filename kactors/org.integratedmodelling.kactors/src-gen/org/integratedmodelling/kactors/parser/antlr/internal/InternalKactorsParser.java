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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_LOWERCASE_ID", "RULE_ID", "RULE_STRING", "RULE_ARGVALUE", "RULE_OBSERVABLE", "RULE_EXPR", "RULE_ANNOTATION_ID", "RULE_INT", "RULE_SEPARATOR", "RULE_CAMELCASE_ID", "RULE_UPPERCASE_ID", "RULE_EMBEDDEDTEXT", "RULE_REGEXP", "RULE_UPPERCASE_PATH", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'name'", "'import'", "','", "'worldview'", "'label'", "'description'", "'permissions'", "'author'", "'version'", "'created'", "'modified'", "'def'", "':'", "'('", "')'", "'=?'", "'='", "'urn:klab:'", "'#'", "'&'", "'{'", "'}'", "'true'", "'false'", "'inclusive'", "'exclusive'", "'to'", "'in'", "'unknown'", "'*'", "'{{'", "'}}'", "'|'", "'/'", "'.'", "'@'", "'>'", "'<'", "'!='", "'<='", "'>='", "'if'", "'else'", "'while'", "'do'", "'for'", "';'", "'->'", "'+'", "'-'", "'l'", "'e'", "'E'", "'AD'", "'CE'", "'BC'", "'^'"
    };
    public static final int T__50=50;
    public static final int RULE_EMBEDDEDTEXT=15;
    public static final int RULE_UPPERCASE_ID=14;
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
    public static final int RULE_ID=5;
    public static final int RULE_CAMELCASE_ID=13;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=11;
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
    public static final int RULE_ARGVALUE=7;
    public static final int RULE_STRING=6;
    public static final int RULE_SEPARATOR=12;
    public static final int RULE_SL_COMMENT=19;
    public static final int RULE_OBSERVABLE=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__77=77;
    public static final int T__34=34;
    public static final int T__78=78;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int RULE_REGEXP=16;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
    public static final int T__31=31;
    public static final int T__75=75;
    public static final int T__32=32;
    public static final int T__76=76;
    public static final int RULE_WS=20;
    public static final int RULE_ANY_OTHER=21;
    public static final int RULE_ANNOTATION_ID=10;
    public static final int T__48=48;
    public static final int RULE_LOWERCASE_ID=4;
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
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
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
                case 33:
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

                if ( (LA2_0==RULE_ANNOTATION_ID||LA2_0==33) ) {
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
    // InternalKactors.g:153:1: rulePreamble returns [EObject current=null] : ( () (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) ;
    public final EObject rulePreamble() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token lv_label_11_1=null;
        Token lv_label_11_2=null;
        Token lv_label_11_3=null;
        Token otherlv_12=null;
        Token lv_description_13_0=null;
        Token otherlv_14=null;
        Token lv_permissions_15_0=null;
        Token otherlv_16=null;
        Token lv_authors_17_0=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token lv_createcomment_22_0=null;
        Token otherlv_23=null;
        Token lv_modcomment_25_0=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        AntlrDatatypeRuleToken lv_imports_5_0 = null;

        AntlrDatatypeRuleToken lv_imports_7_0 = null;

        AntlrDatatypeRuleToken lv_worldview_9_0 = null;

        AntlrDatatypeRuleToken lv_version_19_0 = null;

        EObject lv_created_21_0 = null;

        EObject lv_modified_24_0 = null;



        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKactors.g:162:2: ( ( () (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) ) )
            // InternalKactors.g:163:2: ( () (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            {
            // InternalKactors.g:163:2: ( () (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) ) )
            // InternalKactors.g:164:3: () (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )? ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
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

            // InternalKactors.g:174:3: (otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==22) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalKactors.g:175:4: otherlv_1= 'name' ( (lv_name_2_0= rulePathName ) )
                    {
                    otherlv_1=(Token)match(input,22,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getPreambleAccess().getNameKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:179:4: ( (lv_name_2_0= rulePathName ) )
                    // InternalKactors.g:180:5: (lv_name_2_0= rulePathName )
                    {
                    // InternalKactors.g:180:5: (lv_name_2_0= rulePathName )
                    // InternalKactors.g:181:6: lv_name_2_0= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPreambleAccess().getNamePathNameParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_name_2_0=rulePathName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPreambleRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_2_0,
                      							"org.integratedmodelling.kactors.Kactors.PathName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalKactors.g:199:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) ) )
            // InternalKactors.g:200:4: ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) )
            {
            // InternalKactors.g:200:4: ( ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* ) )
            // InternalKactors.g:201:5: ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            // InternalKactors.g:204:5: ( ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )* )
            // InternalKactors.g:205:6: ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )*
            {
            // InternalKactors.g:205:6: ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )*
            loop9:
            do {
                int alt9=10;
                alt9 = dfa9.predict(input);
                switch (alt9) {
            	case 1 :
            	    // InternalKactors.g:206:4: ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) )
            	    {
            	    // InternalKactors.g:206:4: ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) )
            	    // InternalKactors.g:207:5: {...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKactors.g:207:105: ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) )
            	    // InternalKactors.g:208:6: ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
            	    // InternalKactors.g:211:9: ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) )
            	    // InternalKactors.g:211:10: {...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:211:19: (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* )
            	    // InternalKactors.g:211:20: otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )*
            	    {
            	    otherlv_4=(Token)match(input,23,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_4, grammarAccess.getPreambleAccess().getImportKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKactors.g:215:9: ( (lv_imports_5_0= rulePathName ) )
            	    // InternalKactors.g:216:10: (lv_imports_5_0= rulePathName )
            	    {
            	    // InternalKactors.g:216:10: (lv_imports_5_0= rulePathName )
            	    // InternalKactors.g:217:11: lv_imports_5_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_imports_5_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											add(
            	      												current,
            	      												"imports",
            	      												lv_imports_5_0,
            	      												"org.integratedmodelling.kactors.Kactors.PathName");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:234:9: (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )*
            	    loop4:
            	    do {
            	        int alt4=2;
            	        int LA4_0 = input.LA(1);

            	        if ( (LA4_0==24) ) {
            	            alt4=1;
            	        }


            	        switch (alt4) {
            	    	case 1 :
            	    	    // InternalKactors.g:235:10: otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) )
            	    	    {
            	    	    otherlv_6=(Token)match(input,24,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      										newLeafNode(otherlv_6, grammarAccess.getPreambleAccess().getCommaKeyword_2_0_2_0());
            	    	      									
            	    	    }
            	    	    // InternalKactors.g:239:10: ( (lv_imports_7_0= rulePathName ) )
            	    	    // InternalKactors.g:240:11: (lv_imports_7_0= rulePathName )
            	    	    {
            	    	    // InternalKactors.g:240:11: (lv_imports_7_0= rulePathName )
            	    	    // InternalKactors.g:241:12: lv_imports_7_0= rulePathName
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
            	    	      											
            	    	    }
            	    	    pushFollow(FOLLOW_6);
            	    	    lv_imports_7_0=rulePathName();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      												if (current==null) {
            	    	      													current = createModelElementForParent(grammarAccess.getPreambleRule());
            	    	      												}
            	    	      												add(
            	    	      													current,
            	    	      													"imports",
            	    	      													lv_imports_7_0,
            	    	      													"org.integratedmodelling.kactors.Kactors.PathName");
            	    	      												afterParserOrEnumRuleCall();
            	    	      											
            	    	    }

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop4;
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
            	    // InternalKactors.g:265:4: ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:265:4: ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) )
            	    // InternalKactors.g:266:5: {...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKactors.g:266:105: ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) )
            	    // InternalKactors.g:267:6: ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
            	    // InternalKactors.g:270:9: ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) )
            	    // InternalKactors.g:270:10: {...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:270:19: (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) )
            	    // InternalKactors.g:270:20: otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) )
            	    {
            	    otherlv_8=(Token)match(input,25,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_8, grammarAccess.getPreambleAccess().getWorldviewKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKactors.g:274:9: ( (lv_worldview_9_0= rulePathName ) )
            	    // InternalKactors.g:275:10: (lv_worldview_9_0= rulePathName )
            	    {
            	    // InternalKactors.g:275:10: (lv_worldview_9_0= rulePathName )
            	    // InternalKactors.g:276:11: lv_worldview_9_0= rulePathName
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_worldview_9_0=rulePathName();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"worldview",
            	      												lv_worldview_9_0,
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
            	    // InternalKactors.g:299:4: ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:299:4: ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) )
            	    // InternalKactors.g:300:5: {...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKactors.g:300:105: ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:301:6: ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
            	    // InternalKactors.g:304:9: ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:304:10: {...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:304:19: (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) )
            	    // InternalKactors.g:304:20: otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) )
            	    {
            	    otherlv_10=(Token)match(input,26,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_10, grammarAccess.getPreambleAccess().getLabelKeyword_2_2_0());
            	      								
            	    }
            	    // InternalKactors.g:308:9: ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) )
            	    // InternalKactors.g:309:10: ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:309:10: ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) )
            	    // InternalKactors.g:310:11: (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING )
            	    {
            	    // InternalKactors.g:310:11: (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING )
            	    int alt5=3;
            	    switch ( input.LA(1) ) {
            	    case RULE_LOWERCASE_ID:
            	        {
            	        alt5=1;
            	        }
            	        break;
            	    case RULE_ID:
            	        {
            	        alt5=2;
            	        }
            	        break;
            	    case RULE_STRING:
            	        {
            	        alt5=3;
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
            	            // InternalKactors.g:311:12: lv_label_11_1= RULE_LOWERCASE_ID
            	            {
            	            lv_label_11_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_11_1, grammarAccess.getPreambleAccess().getLabelLOWERCASE_IDTerminalRuleCall_2_2_1_0_0());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_11_1,
            	              													"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:326:12: lv_label_11_2= RULE_ID
            	            {
            	            lv_label_11_2=(Token)match(input,RULE_ID,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_11_2, grammarAccess.getPreambleAccess().getLabelIDTerminalRuleCall_2_2_1_0_1());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_11_2,
            	              													"org.eclipse.xtext.common.Terminals.ID");
            	              											
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalKactors.g:341:12: lv_label_11_3= RULE_STRING
            	            {
            	            lv_label_11_3=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              												newLeafNode(lv_label_11_3, grammarAccess.getPreambleAccess().getLabelSTRINGTerminalRuleCall_2_2_1_0_2());
            	              											
            	            }
            	            if ( state.backtracking==0 ) {

            	              												if (current==null) {
            	              													current = createModelElement(grammarAccess.getPreambleRule());
            	              												}
            	              												setWithLastConsumed(
            	              													current,
            	              													"label",
            	              													lv_label_11_3,
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
            	case 4 :
            	    // InternalKactors.g:364:4: ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:364:4: ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:365:5: {...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKactors.g:365:105: ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:366:6: ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
            	    // InternalKactors.g:369:9: ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:369:10: {...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:369:19: (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) )
            	    // InternalKactors.g:369:20: otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) )
            	    {
            	    otherlv_12=(Token)match(input,27,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_12, grammarAccess.getPreambleAccess().getDescriptionKeyword_2_3_0());
            	      								
            	    }
            	    // InternalKactors.g:373:9: ( (lv_description_13_0= RULE_STRING ) )
            	    // InternalKactors.g:374:10: (lv_description_13_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:374:10: (lv_description_13_0= RULE_STRING )
            	    // InternalKactors.g:375:11: lv_description_13_0= RULE_STRING
            	    {
            	    lv_description_13_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_description_13_0, grammarAccess.getPreambleAccess().getDescriptionSTRINGTerminalRuleCall_2_3_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"description",
            	      												lv_description_13_0,
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
            	case 5 :
            	    // InternalKactors.g:397:4: ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:397:4: ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:398:5: {...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
            	    }
            	    // InternalKactors.g:398:105: ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:399:6: ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
            	    // InternalKactors.g:402:9: ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:402:10: {...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:402:19: (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) )
            	    // InternalKactors.g:402:20: otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) )
            	    {
            	    otherlv_14=(Token)match(input,28,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_14, grammarAccess.getPreambleAccess().getPermissionsKeyword_2_4_0());
            	      								
            	    }
            	    // InternalKactors.g:406:9: ( (lv_permissions_15_0= RULE_STRING ) )
            	    // InternalKactors.g:407:10: (lv_permissions_15_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:407:10: (lv_permissions_15_0= RULE_STRING )
            	    // InternalKactors.g:408:11: lv_permissions_15_0= RULE_STRING
            	    {
            	    lv_permissions_15_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_permissions_15_0, grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_2_4_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"permissions",
            	      												lv_permissions_15_0,
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
            	    // InternalKactors.g:430:4: ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) )
            	    {
            	    // InternalKactors.g:430:4: ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) )
            	    // InternalKactors.g:431:5: {...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
            	    }
            	    // InternalKactors.g:431:105: ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ )
            	    // InternalKactors.g:432:6: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
            	    // InternalKactors.g:435:9: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+
            	    int cnt6=0;
            	    loop6:
            	    do {
            	        int alt6=2;
            	        int LA6_0 = input.LA(1);

            	        if ( (LA6_0==29) ) {
            	            int LA6_2 = input.LA(2);

            	            if ( ((synpred12_InternalKactors()&&(true))) ) {
            	                alt6=1;
            	            }


            	        }


            	        switch (alt6) {
            	    	case 1 :
            	    	    // InternalKactors.g:435:10: {...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
            	    	    {
            	    	    if ( !((true)) ) {
            	    	        if (state.backtracking>0) {state.failed=true; return current;}
            	    	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    	    }
            	    	    // InternalKactors.g:435:19: (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
            	    	    // InternalKactors.g:435:20: otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_16=(Token)match(input,29,FOLLOW_8); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      									newLeafNode(otherlv_16, grammarAccess.getPreambleAccess().getAuthorKeyword_2_5_0());
            	    	      								
            	    	    }
            	    	    // InternalKactors.g:439:9: ( (lv_authors_17_0= RULE_STRING ) )
            	    	    // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
            	    	    {
            	    	    // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
            	    	    // InternalKactors.g:441:11: lv_authors_17_0= RULE_STRING
            	    	    {
            	    	    lv_authors_17_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      											newLeafNode(lv_authors_17_0, grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_2_5_1_0());
            	    	      										
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      											if (current==null) {
            	    	      												current = createModelElement(grammarAccess.getPreambleRule());
            	    	      											}
            	    	      											addWithLastConsumed(
            	    	      												current,
            	    	      												"authors",
            	    	      												lv_authors_17_0,
            	    	      												"org.eclipse.xtext.common.Terminals.STRING");
            	    	      										
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt6 >= 1 ) break loop6;
            	    	    if (state.backtracking>0) {state.failed=true; return current;}
            	                EarlyExitException eee =
            	                    new EarlyExitException(6, input);
            	                throw eee;
            	        }
            	        cnt6++;
            	    } while (true);

            	    getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());

            	    }


            	    }


            	    }
            	    break;
            	case 7 :
            	    // InternalKactors.g:463:4: ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:463:4: ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) )
            	    // InternalKactors.g:464:5: {...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
            	    }
            	    // InternalKactors.g:464:105: ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) )
            	    // InternalKactors.g:465:6: ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
            	    // InternalKactors.g:468:9: ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) )
            	    // InternalKactors.g:468:10: {...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:468:19: (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) )
            	    // InternalKactors.g:468:20: otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) )
            	    {
            	    otherlv_18=(Token)match(input,30,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_18, grammarAccess.getPreambleAccess().getVersionKeyword_2_6_0());
            	      								
            	    }
            	    // InternalKactors.g:472:9: ( (lv_version_19_0= ruleVersionNumber ) )
            	    // InternalKactors.g:473:10: (lv_version_19_0= ruleVersionNumber )
            	    {
            	    // InternalKactors.g:473:10: (lv_version_19_0= ruleVersionNumber )
            	    // InternalKactors.g:474:11: lv_version_19_0= ruleVersionNumber
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_6_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_version_19_0=ruleVersionNumber();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"version",
            	      												lv_version_19_0,
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
            	case 8 :
            	    // InternalKactors.g:497:4: ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:497:4: ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:498:5: {...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
            	    }
            	    // InternalKactors.g:498:105: ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:499:6: ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
            	    // InternalKactors.g:502:9: ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:502:10: {...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:502:19: (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? )
            	    // InternalKactors.g:502:20: otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )?
            	    {
            	    otherlv_20=(Token)match(input,31,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_20, grammarAccess.getPreambleAccess().getCreatedKeyword_2_7_0());
            	      								
            	    }
            	    // InternalKactors.g:506:9: ( (lv_created_21_0= ruleDate ) )
            	    // InternalKactors.g:507:10: (lv_created_21_0= ruleDate )
            	    {
            	    // InternalKactors.g:507:10: (lv_created_21_0= ruleDate )
            	    // InternalKactors.g:508:11: lv_created_21_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_7_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_created_21_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"created",
            	      												lv_created_21_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:525:9: ( (lv_createcomment_22_0= RULE_STRING ) )?
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0==RULE_STRING) ) {
            	        alt7=1;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // InternalKactors.g:526:10: (lv_createcomment_22_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:526:10: (lv_createcomment_22_0= RULE_STRING )
            	            // InternalKactors.g:527:11: lv_createcomment_22_0= RULE_STRING
            	            {
            	            lv_createcomment_22_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_createcomment_22_0, grammarAccess.getPreambleAccess().getCreatecommentSTRINGTerminalRuleCall_2_7_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"createcomment",
            	              												lv_createcomment_22_0,
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
            	case 9 :
            	    // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) )
            	    {
            	    // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) )
            	    // InternalKactors.g:550:5: {...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
            	    }
            	    // InternalKactors.g:550:105: ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) )
            	    // InternalKactors.g:551:6: ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
            	    // InternalKactors.g:554:9: ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) )
            	    // InternalKactors.g:554:10: {...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:554:19: (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? )
            	    // InternalKactors.g:554:20: otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )?
            	    {
            	    otherlv_23=(Token)match(input,32,FOLLOW_9); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_23, grammarAccess.getPreambleAccess().getModifiedKeyword_2_8_0());
            	      								
            	    }
            	    // InternalKactors.g:558:9: ( (lv_modified_24_0= ruleDate ) )
            	    // InternalKactors.g:559:10: (lv_modified_24_0= ruleDate )
            	    {
            	    // InternalKactors.g:559:10: (lv_modified_24_0= ruleDate )
            	    // InternalKactors.g:560:11: lv_modified_24_0= ruleDate
            	    {
            	    if ( state.backtracking==0 ) {

            	      											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_8_1_0());
            	      										
            	    }
            	    pushFollow(FOLLOW_10);
            	    lv_modified_24_0=ruleDate();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElementForParent(grammarAccess.getPreambleRule());
            	      											}
            	      											set(
            	      												current,
            	      												"modified",
            	      												lv_modified_24_0,
            	      												"org.integratedmodelling.kactors.Kactors.Date");
            	      											afterParserOrEnumRuleCall();
            	      										
            	    }

            	    }


            	    }

            	    // InternalKactors.g:577:9: ( (lv_modcomment_25_0= RULE_STRING ) )?
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==RULE_STRING) ) {
            	        alt8=1;
            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // InternalKactors.g:578:10: (lv_modcomment_25_0= RULE_STRING )
            	            {
            	            // InternalKactors.g:578:10: (lv_modcomment_25_0= RULE_STRING )
            	            // InternalKactors.g:579:11: lv_modcomment_25_0= RULE_STRING
            	            {
            	            lv_modcomment_25_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              											newLeafNode(lv_modcomment_25_0, grammarAccess.getPreambleAccess().getModcommentSTRINGTerminalRuleCall_2_8_2_0());
            	              										
            	            }
            	            if ( state.backtracking==0 ) {

            	              											if (current==null) {
            	              												current = createModelElement(grammarAccess.getPreambleRule());
            	              											}
            	              											setWithLastConsumed(
            	              												current,
            	              												"modcomment",
            	              												lv_modcomment_25_0,
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
            	    break loop9;
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
    // InternalKactors.g:615:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:615:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:616:2: iv_ruleDefinition= ruleDefinition EOF
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
    // InternalKactors.g:622:1: ruleDefinition returns [EObject current=null] : ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) )+ ) ;
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
            // InternalKactors.g:628:2: ( ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) )+ ) )
            // InternalKactors.g:629:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) )+ )
            {
            // InternalKactors.g:629:2: ( ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) )+ )
            // InternalKactors.g:630:3: ( (lv_annotations_0_0= ruleAnnotation ) )* otherlv_1= 'def' ( (lv_name_2_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_3_0= ruleArgumentDeclaration ) )? otherlv_4= ':' ( (lv_body_5_0= ruleBody ) )+
            {
            // InternalKactors.g:630:3: ( (lv_annotations_0_0= ruleAnnotation ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RULE_ANNOTATION_ID) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalKactors.g:631:4: (lv_annotations_0_0= ruleAnnotation )
            	    {
            	    // InternalKactors.g:631:4: (lv_annotations_0_0= ruleAnnotation )
            	    // InternalKactors.g:632:5: lv_annotations_0_0= ruleAnnotation
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
            	    break loop10;
                }
            } while (true);

            otherlv_1=(Token)match(input,33,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getDefinitionAccess().getDefKeyword_1());
              		
            }
            // InternalKactors.g:653:3: ( (lv_name_2_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:654:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:654:4: (lv_name_2_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:655:5: lv_name_2_0= RULE_LOWERCASE_ID
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

            // InternalKactors.g:671:3: ( (lv_arguments_3_0= ruleArgumentDeclaration ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==35) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalKactors.g:672:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:672:4: (lv_arguments_3_0= ruleArgumentDeclaration )
                    // InternalKactors.g:673:5: lv_arguments_3_0= ruleArgumentDeclaration
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

            otherlv_4=(Token)match(input,34,FOLLOW_14); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDefinitionAccess().getColonKeyword_4());
              		
            }
            // InternalKactors.g:694:3: ( (lv_body_5_0= ruleBody ) )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_LOWERCASE_ID||LA12_0==RULE_EMBEDDEDTEXT||LA12_0==35||LA12_0==63||(LA12_0>=66 && LA12_0<=67)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalKactors.g:695:4: (lv_body_5_0= ruleBody )
            	    {
            	    // InternalKactors.g:695:4: (lv_body_5_0= ruleBody )
            	    // InternalKactors.g:696:5: lv_body_5_0= ruleBody
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getDefinitionAccess().getBodyBodyParserRuleCall_5_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_body_5_0=ruleBody();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getDefinitionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"body",
            	      						lv_body_5_0,
            	      						"org.integratedmodelling.kactors.Kactors.Body");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
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
    // $ANTLR end "ruleDefinition"


    // $ANTLR start "entryRuleArgumentDeclaration"
    // InternalKactors.g:717:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:717:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:718:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
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
    // InternalKactors.g:724:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:730:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:731:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:731:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:732:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:732:3: ()
            // InternalKactors.g:733:4: 
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

            otherlv_1=(Token)match(input,35,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:746:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_LOWERCASE_ID) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalKactors.g:747:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:747:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:748:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:748:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:749:6: lv_ids_2_0= RULE_LOWERCASE_ID
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

                    // InternalKactors.g:765:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==24) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // InternalKactors.g:766:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,24,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:770:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:771:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:771:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:772:7: lv_ids_4_0= RULE_LOWERCASE_ID
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
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleParameterList"
    // InternalKactors.g:798:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:798:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:799:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKactors.g:805:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:811:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:812:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:812:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:813:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:813:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:814:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:814:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:815:5: lv_pairs_0_0= ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_18);
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

            // InternalKactors.g:832:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==24) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalKactors.g:833:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:833:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:834:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,24,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:840:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:841:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:841:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:842:6: lv_pairs_2_0= ruleKeyValuePair
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_18);
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
            	    break loop15;
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
    // InternalKactors.g:864:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:864:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:865:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKactors.g:871:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:877:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:878:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:878:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:879:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:879:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==RULE_LOWERCASE_ID) ) {
                int LA17_1 = input.LA(2);

                if ( ((LA17_1>=37 && LA17_1<=38)) ) {
                    alt17=1;
                }
            }
            switch (alt17) {
                case 1 :
                    // InternalKactors.g:880:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:880:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:881:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:881:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:882:6: lv_name_0_0= RULE_LOWERCASE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_20); if (state.failed) return current;
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

                    // InternalKactors.g:898:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==37) ) {
                        alt16=1;
                    }
                    else if ( (LA16_0==38) ) {
                        alt16=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 0, input);

                        throw nvae;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKactors.g:899:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:899:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:900:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:900:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:901:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,37,FOLLOW_19); if (state.failed) return current;
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
                            // InternalKactors.g:914:5: otherlv_2= '='
                            {
                            otherlv_2=(Token)match(input,38,FOLLOW_19); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:920:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:921:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:921:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:922:5: lv_value_3_0= ruleValue
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
    // InternalKactors.g:943:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:943:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:944:2: iv_ruleValue= ruleValue EOF
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
    // InternalKactors.g:950:1: ruleValue returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        Token lv_argvalue_0_0=null;
        Token lv_id_2_0=null;
        Token lv_observable_6_0=null;
        Token lv_expression_7_0=null;
        EObject lv_literal_1_0 = null;

        EObject lv_urn_3_0 = null;

        EObject lv_list_4_0 = null;

        EObject lv_map_5_0 = null;

        EObject lv_table_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:956:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) ) )
            // InternalKactors.g:957:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            {
            // InternalKactors.g:957:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )
            int alt18=9;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // InternalKactors.g:958:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:958:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:959:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:959:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:960:5: lv_argvalue_0_0= RULE_ARGVALUE
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
                    // InternalKactors.g:977:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:977:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:978:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:978:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:979:5: lv_literal_1_0= ruleLiteral
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
                    // InternalKactors.g:997:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:997:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:998:4: (lv_id_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:998:4: (lv_id_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:999:5: lv_id_2_0= RULE_LOWERCASE_ID
                    {
                    lv_id_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_id_2_0, grammarAccess.getValueAccess().getIdLOWERCASE_IDTerminalRuleCall_2_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"id",
                      						lv_id_2_0,
                      						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:1016:3: ( (lv_urn_3_0= ruleUrn ) )
                    {
                    // InternalKactors.g:1016:3: ( (lv_urn_3_0= ruleUrn ) )
                    // InternalKactors.g:1017:4: (lv_urn_3_0= ruleUrn )
                    {
                    // InternalKactors.g:1017:4: (lv_urn_3_0= ruleUrn )
                    // InternalKactors.g:1018:5: lv_urn_3_0= ruleUrn
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_3_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_urn_3_0=ruleUrn();

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
                      						"org.integratedmodelling.kactors.Kactors.Urn");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:1036:3: ( (lv_list_4_0= ruleList ) )
                    {
                    // InternalKactors.g:1036:3: ( (lv_list_4_0= ruleList ) )
                    // InternalKactors.g:1037:4: (lv_list_4_0= ruleList )
                    {
                    // InternalKactors.g:1037:4: (lv_list_4_0= ruleList )
                    // InternalKactors.g:1038:5: lv_list_4_0= ruleList
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
                    // InternalKactors.g:1056:3: ( (lv_map_5_0= ruleMap ) )
                    {
                    // InternalKactors.g:1056:3: ( (lv_map_5_0= ruleMap ) )
                    // InternalKactors.g:1057:4: (lv_map_5_0= ruleMap )
                    {
                    // InternalKactors.g:1057:4: (lv_map_5_0= ruleMap )
                    // InternalKactors.g:1058:5: lv_map_5_0= ruleMap
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
                    // InternalKactors.g:1076:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:1076:3: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:1077:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:1077:4: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:1078:5: lv_observable_6_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:1095:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:1095:3: ( (lv_expression_7_0= RULE_EXPR ) )
                    // InternalKactors.g:1096:4: (lv_expression_7_0= RULE_EXPR )
                    {
                    // InternalKactors.g:1096:4: (lv_expression_7_0= RULE_EXPR )
                    // InternalKactors.g:1097:5: lv_expression_7_0= RULE_EXPR
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
                    // InternalKactors.g:1114:3: ( (lv_table_8_0= ruleLookupTable ) )
                    {
                    // InternalKactors.g:1114:3: ( (lv_table_8_0= ruleLookupTable ) )
                    // InternalKactors.g:1115:4: (lv_table_8_0= ruleLookupTable )
                    {
                    // InternalKactors.g:1115:4: (lv_table_8_0= ruleLookupTable )
                    // InternalKactors.g:1116:5: lv_table_8_0= ruleLookupTable
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


    // $ANTLR start "entryRuleUrn"
    // InternalKactors.g:1137:1: entryRuleUrn returns [EObject current=null] : iv_ruleUrn= ruleUrn EOF ;
    public final EObject entryRuleUrn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUrn = null;


        try {
            // InternalKactors.g:1137:44: (iv_ruleUrn= ruleUrn EOF )
            // InternalKactors.g:1138:2: iv_ruleUrn= ruleUrn EOF
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
    // InternalKactors.g:1144:1: ruleUrn returns [EObject current=null] : ( ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) ) ) ;
    public final EObject ruleUrn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_3=null;
        AntlrDatatypeRuleToken lv_name_0_1 = null;

        AntlrDatatypeRuleToken lv_name_0_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:1150:2: ( ( ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) ) ) )
            // InternalKactors.g:1151:2: ( ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) ) )
            {
            // InternalKactors.g:1151:2: ( ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) ) )
            // InternalKactors.g:1152:3: ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) )
            {
            // InternalKactors.g:1152:3: ( (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING ) )
            // InternalKactors.g:1153:4: (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING )
            {
            // InternalKactors.g:1153:4: (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING )
            int alt19=3;
            alt19 = dfa19.predict(input);
            switch (alt19) {
                case 1 :
                    // InternalKactors.g:1154:5: lv_name_0_1= rulePathName
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUrnAccess().getNamePathNameParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_0_1=rulePathName();

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
                      						"org.integratedmodelling.kactors.Kactors.PathName");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:1170:5: lv_name_0_2= ruleUrnId
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUrnAccess().getNameUrnIdParserRuleCall_0_1());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_0_2=ruleUrnId();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getUrnRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_0_2,
                      						"org.integratedmodelling.kactors.Kactors.UrnId");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:1186:5: lv_name_0_3= RULE_STRING
                    {
                    lv_name_0_3=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_name_0_3, grammarAccess.getUrnAccess().getNameSTRINGTerminalRuleCall_0_2());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getUrnRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"name",
                      						lv_name_0_3,
                      						"org.eclipse.xtext.common.Terminals.STRING");
                      				
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


    // $ANTLR start "entryRuleAnnotation"
    // InternalKactors.g:1206:1: entryRuleAnnotation returns [EObject current=null] : iv_ruleAnnotation= ruleAnnotation EOF ;
    public final EObject entryRuleAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotation = null;


        try {
            // InternalKactors.g:1206:51: (iv_ruleAnnotation= ruleAnnotation EOF )
            // InternalKactors.g:1207:2: iv_ruleAnnotation= ruleAnnotation EOF
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
    // InternalKactors.g:1213:1: ruleAnnotation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) ;
    public final EObject ruleAnnotation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1219:2: ( ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ) )
            // InternalKactors.g:1220:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            {
            // InternalKactors.g:1220:2: ( ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? )
            // InternalKactors.g:1221:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            {
            // InternalKactors.g:1221:3: ( (lv_name_0_0= RULE_ANNOTATION_ID ) )
            // InternalKactors.g:1222:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            {
            // InternalKactors.g:1222:4: (lv_name_0_0= RULE_ANNOTATION_ID )
            // InternalKactors.g:1223:5: lv_name_0_0= RULE_ANNOTATION_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ANNOTATION_ID,FOLLOW_21); if (state.failed) return current;
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

            // InternalKactors.g:1239:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==35) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalKactors.g:1240:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getAnnotationAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:1244:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==RULE_LOWERCASE_ID||(LA20_0>=RULE_STRING && LA20_0<=RULE_EXPR)||LA20_0==RULE_INT||LA20_0==35||LA20_0==39||LA20_0==42||(LA20_0>=44 && LA20_0<=45)||LA20_0==52||(LA20_0>=70 && LA20_0<=71)) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // InternalKactors.g:1245:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:1245:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:1246:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getAnnotationAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_23);
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

                    otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleUrnId"
    // InternalKactors.g:1272:1: entryRuleUrnId returns [String current=null] : iv_ruleUrnId= ruleUrnId EOF ;
    public final String entryRuleUrnId() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnId = null;


        try {
            // InternalKactors.g:1272:45: (iv_ruleUrnId= ruleUrnId EOF )
            // InternalKactors.g:1273:2: iv_ruleUrnId= ruleUrnId EOF
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
    // InternalKactors.g:1279:1: ruleUrnId returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) ;
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
            // InternalKactors.g:1285:2: ( ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? ) )
            // InternalKactors.g:1286:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            {
            // InternalKactors.g:1286:2: ( (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )? )
            // InternalKactors.g:1287:3: (kw= 'urn:klab:' )? this_PathName_1= rulePathName kw= ':' this_PathName_3= rulePathName kw= ':' this_PathName_5= rulePathName kw= ':' (this_Path_7= rulePath | this_INT_8= RULE_INT ) (kw= ':' this_VersionNumber_10= ruleVersionNumber )? (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            {
            // InternalKactors.g:1287:3: (kw= 'urn:klab:' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==39) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalKactors.g:1288:4: kw= 'urn:klab:'
                    {
                    kw=(Token)match(input,39,FOLLOW_4); if (state.failed) return current;
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
            kw=(Token)match(input,34,FOLLOW_4); if (state.failed) return current;
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
            kw=(Token)match(input,34,FOLLOW_4); if (state.failed) return current;
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
            kw=(Token)match(input,34,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_6());
              		
            }
            // InternalKactors.g:1339:3: (this_Path_7= rulePath | this_INT_8= RULE_INT )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_LOWERCASE_ID||LA23_0==RULE_UPPERCASE_ID) ) {
                alt23=1;
            }
            else if ( (LA23_0==RULE_INT) ) {
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
                    // InternalKactors.g:1340:4: this_Path_7= rulePath
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_7_0());
                      			
                    }
                    pushFollow(FOLLOW_25);
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
                    // InternalKactors.g:1351:4: this_INT_8= RULE_INT
                    {
                    this_INT_8=(Token)match(input,RULE_INT,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_8);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_8, grammarAccess.getUrnIdAccess().getINTTerminalRuleCall_7_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1359:3: (kw= ':' this_VersionNumber_10= ruleVersionNumber )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==34) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:1360:4: kw= ':' this_VersionNumber_10= ruleVersionNumber
                    {
                    kw=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getColonKeyword_8_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUrnIdAccess().getVersionNumberParserRuleCall_8_1());
                      			
                    }
                    pushFollow(FOLLOW_26);
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

            // InternalKactors.g:1376:3: (kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )* )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==40) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalKactors.g:1377:4: kw= '#' (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp ) (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    {
                    kw=(Token)match(input,40,FOLLOW_27); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getUrnIdAccess().getNumberSignKeyword_9_0());
                      			
                    }
                    // InternalKactors.g:1382:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )
                    int alt25=2;
                    alt25 = dfa25.predict(input);
                    switch (alt25) {
                        case 1 :
                            // InternalKactors.g:1383:5: this_Path_12= rulePath
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_1_0());
                              				
                            }
                            pushFollow(FOLLOW_28);
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
                            // InternalKactors.g:1394:5: this_UrnKvp_13= ruleUrnKvp
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_1_1());
                              				
                            }
                            pushFollow(FOLLOW_28);
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

                    // InternalKactors.g:1405:4: (kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp ) )*
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0==41) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // InternalKactors.g:1406:5: kw= '&' (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    {
                    	    kw=(Token)match(input,41,FOLLOW_27); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(kw);
                    	      					newLeafNode(kw, grammarAccess.getUrnIdAccess().getAmpersandKeyword_9_2_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:1411:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )
                    	    int alt26=2;
                    	    alt26 = dfa26.predict(input);
                    	    switch (alt26) {
                    	        case 1 :
                    	            // InternalKactors.g:1412:6: this_Path_15= rulePath
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getPathParserRuleCall_9_2_1_0());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_28);
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
                    	            // InternalKactors.g:1423:6: this_UrnKvp_16= ruleUrnKvp
                    	            {
                    	            if ( state.backtracking==0 ) {

                    	              						newCompositeNode(grammarAccess.getUrnIdAccess().getUrnKvpParserRuleCall_9_2_1_1());
                    	              					
                    	            }
                    	            pushFollow(FOLLOW_28);
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
                    	    break loop27;
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
    // InternalKactors.g:1440:1: entryRuleUrnKvp returns [String current=null] : iv_ruleUrnKvp= ruleUrnKvp EOF ;
    public final String entryRuleUrnKvp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUrnKvp = null;


        try {
            // InternalKactors.g:1440:46: (iv_ruleUrnKvp= ruleUrnKvp EOF )
            // InternalKactors.g:1441:2: iv_ruleUrnKvp= ruleUrnKvp EOF
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
    // InternalKactors.g:1447:1: ruleUrnKvp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) ;
    public final AntlrDatatypeRuleToken ruleUrnKvp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_3=null;
        AntlrDatatypeRuleToken this_PathName_0 = null;

        AntlrDatatypeRuleToken this_Path_2 = null;



        	enterRule();

        try {
            // InternalKactors.g:1453:2: ( (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) ) )
            // InternalKactors.g:1454:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            {
            // InternalKactors.g:1454:2: (this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT ) )
            // InternalKactors.g:1455:3: this_PathName_0= rulePathName kw= '=' (this_Path_2= rulePath | this_INT_3= RULE_INT )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUrnKvpAccess().getPathNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_29);
            this_PathName_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PathName_0);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            kw=(Token)match(input,38,FOLLOW_24); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getUrnKvpAccess().getEqualsSignKeyword_1());
              		
            }
            // InternalKactors.g:1470:3: (this_Path_2= rulePath | this_INT_3= RULE_INT )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_LOWERCASE_ID||LA29_0==RULE_UPPERCASE_ID) ) {
                alt29=1;
            }
            else if ( (LA29_0==RULE_INT) ) {
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
                    // InternalKactors.g:1471:4: this_Path_2= rulePath
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
                    // InternalKactors.g:1482:4: this_INT_3= RULE_INT
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
    // InternalKactors.g:1494:1: entryRuleList returns [EObject current=null] : iv_ruleList= ruleList EOF ;
    public final EObject entryRuleList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleList = null;


        try {
            // InternalKactors.g:1494:45: (iv_ruleList= ruleList EOF )
            // InternalKactors.g:1495:2: iv_ruleList= ruleList EOF
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
    // InternalKactors.g:1501:1: ruleList returns [EObject current=null] : ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) ;
    public final EObject ruleList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_contents_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1507:2: ( ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' ) )
            // InternalKactors.g:1508:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            {
            // InternalKactors.g:1508:2: ( () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')' )
            // InternalKactors.g:1509:3: () otherlv_1= '(' ( (lv_contents_2_0= ruleValue ) )* otherlv_3= ')'
            {
            // InternalKactors.g:1509:3: ()
            // InternalKactors.g:1510:4: 
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

            otherlv_1=(Token)match(input,35,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getListAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:1523:3: ( (lv_contents_2_0= ruleValue ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_LOWERCASE_ID||(LA30_0>=RULE_STRING && LA30_0<=RULE_EXPR)||LA30_0==RULE_INT||LA30_0==35||LA30_0==39||LA30_0==42||(LA30_0>=44 && LA30_0<=45)||LA30_0==52||(LA30_0>=70 && LA30_0<=71)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalKactors.g:1524:4: (lv_contents_2_0= ruleValue )
            	    {
            	    // InternalKactors.g:1524:4: (lv_contents_2_0= ruleValue )
            	    // InternalKactors.g:1525:5: lv_contents_2_0= ruleValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getListAccess().getContentsValueParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_22);
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
            	    break loop30;
                }
            } while (true);

            otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1550:1: entryRuleMap returns [EObject current=null] : iv_ruleMap= ruleMap EOF ;
    public final EObject entryRuleMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMap = null;


        try {
            // InternalKactors.g:1550:44: (iv_ruleMap= ruleMap EOF )
            // InternalKactors.g:1551:2: iv_ruleMap= ruleMap EOF
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
    // InternalKactors.g:1557:1: ruleMap returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_entries_2_0 = null;

        EObject lv_entries_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1563:2: ( ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' ) )
            // InternalKactors.g:1564:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            {
            // InternalKactors.g:1564:2: ( () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}' )
            // InternalKactors.g:1565:3: () otherlv_1= '{' ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )? otherlv_5= '}'
            {
            // InternalKactors.g:1565:3: ()
            // InternalKactors.g:1566:4: 
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

            otherlv_1=(Token)match(input,42,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:1579:3: ( ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )* )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==RULE_LOWERCASE_ID||LA32_0==RULE_STRING||LA32_0==RULE_OBSERVABLE||LA32_0==RULE_INT||LA32_0==38||(LA32_0>=44 && LA32_0<=45)||(LA32_0>=49 && LA32_0<=51)||(LA32_0>=58 && LA32_0<=62)||(LA32_0>=70 && LA32_0<=71)) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalKactors.g:1580:4: ( (lv_entries_2_0= ruleMapEntry ) ) ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    {
                    // InternalKactors.g:1580:4: ( (lv_entries_2_0= ruleMapEntry ) )
                    // InternalKactors.g:1581:5: (lv_entries_2_0= ruleMapEntry )
                    {
                    // InternalKactors.g:1581:5: (lv_entries_2_0= ruleMapEntry )
                    // InternalKactors.g:1582:6: lv_entries_2_0= ruleMapEntry
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_31);
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

                    // InternalKactors.g:1599:4: ( ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) ) )*
                    loop31:
                    do {
                        int alt31=2;
                        int LA31_0 = input.LA(1);

                        if ( (LA31_0==24) ) {
                            alt31=1;
                        }


                        switch (alt31) {
                    	case 1 :
                    	    // InternalKactors.g:1600:5: ( ( ',' )=>otherlv_3= ',' ) ( (lv_entries_4_0= ruleMapEntry ) )
                    	    {
                    	    // InternalKactors.g:1600:5: ( ( ',' )=>otherlv_3= ',' )
                    	    // InternalKactors.g:1601:6: ( ',' )=>otherlv_3= ','
                    	    {
                    	    otherlv_3=(Token)match(input,24,FOLLOW_32); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_3, grammarAccess.getMapAccess().getCommaKeyword_2_1_0_0());
                    	      					
                    	    }

                    	    }

                    	    // InternalKactors.g:1608:5: ( (lv_entries_4_0= ruleMapEntry ) )
                    	    // InternalKactors.g:1609:6: (lv_entries_4_0= ruleMapEntry )
                    	    {
                    	    // InternalKactors.g:1609:6: (lv_entries_4_0= ruleMapEntry )
                    	    // InternalKactors.g:1610:7: lv_entries_4_0= ruleMapEntry
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getMapAccess().getEntriesMapEntryParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_31);
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
                    	    break loop31;
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
    // InternalKactors.g:1637:1: entryRuleMapEntry returns [EObject current=null] : iv_ruleMapEntry= ruleMapEntry EOF ;
    public final EObject entryRuleMapEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMapEntry = null;


        try {
            // InternalKactors.g:1637:49: (iv_ruleMapEntry= ruleMapEntry EOF )
            // InternalKactors.g:1638:2: iv_ruleMapEntry= ruleMapEntry EOF
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
    // InternalKactors.g:1644:1: ruleMapEntry returns [EObject current=null] : ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleMapEntry() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_classifier_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1650:2: ( ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalKactors.g:1651:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalKactors.g:1651:2: ( ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // InternalKactors.g:1652:3: ( (lv_classifier_0_0= ruleClassifier ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalKactors.g:1652:3: ( (lv_classifier_0_0= ruleClassifier ) )
            // InternalKactors.g:1653:4: (lv_classifier_0_0= ruleClassifier )
            {
            // InternalKactors.g:1653:4: (lv_classifier_0_0= ruleClassifier )
            // InternalKactors.g:1654:5: lv_classifier_0_0= ruleClassifier
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

            otherlv_1=(Token)match(input,34,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getMapEntryAccess().getColonKeyword_1());
              		
            }
            // InternalKactors.g:1675:3: ( (lv_value_2_0= ruleValue ) )
            // InternalKactors.g:1676:4: (lv_value_2_0= ruleValue )
            {
            // InternalKactors.g:1676:4: (lv_value_2_0= ruleValue )
            // InternalKactors.g:1677:5: lv_value_2_0= ruleValue
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
    // InternalKactors.g:1698:1: entryRuleClassifier returns [EObject current=null] : iv_ruleClassifier= ruleClassifier EOF ;
    public final EObject entryRuleClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassifier = null;


        try {
            // InternalKactors.g:1698:51: (iv_ruleClassifier= ruleClassifier EOF )
            // InternalKactors.g:1699:2: iv_ruleClassifier= ruleClassifier EOF
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
    // InternalKactors.g:1705:1: ruleClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) ;
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
            // InternalKactors.g:1711:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) ) )
            // InternalKactors.g:1712:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            {
            // InternalKactors.g:1712:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )
            int alt36=10;
            alt36 = dfa36.predict(input);
            switch (alt36) {
                case 1 :
                    // InternalKactors.g:1713:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:1713:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==44) ) {
                        alt33=1;
                    }
                    else if ( (LA33_0==45) ) {
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
                            // InternalKactors.g:1714:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:1714:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:1715:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:1715:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:1716:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:1729:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:1729:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:1730:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:1730:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:1731:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:1745:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    {
                    // InternalKactors.g:1745:3: ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? )
                    // InternalKactors.g:1746:4: ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    {
                    // InternalKactors.g:1746:4: ( (lv_int0_2_0= ruleNumber ) )
                    // InternalKactors.g:1747:5: (lv_int0_2_0= ruleNumber )
                    {
                    // InternalKactors.g:1747:5: (lv_int0_2_0= ruleNumber )
                    // InternalKactors.g:1748:6: lv_int0_2_0= ruleNumber
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

                    // InternalKactors.g:1765:4: ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )?
                    int alt34=3;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==46) ) {
                        alt34=1;
                    }
                    else if ( (LA34_0==47) ) {
                        alt34=2;
                    }
                    switch (alt34) {
                        case 1 :
                            // InternalKactors.g:1766:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:1766:5: ( (lv_leftLimit_3_0= 'inclusive' ) )
                            // InternalKactors.g:1767:6: (lv_leftLimit_3_0= 'inclusive' )
                            {
                            // InternalKactors.g:1767:6: (lv_leftLimit_3_0= 'inclusive' )
                            // InternalKactors.g:1768:7: lv_leftLimit_3_0= 'inclusive'
                            {
                            lv_leftLimit_3_0=(Token)match(input,46,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:1781:5: otherlv_4= 'exclusive'
                            {
                            otherlv_4=(Token)match(input,47,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_4, grammarAccess.getClassifierAccess().getExclusiveKeyword_1_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:1786:4: ( ( 'to' )=>otherlv_5= 'to' )
                    // InternalKactors.g:1787:5: ( 'to' )=>otherlv_5= 'to'
                    {
                    otherlv_5=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_5, grammarAccess.getClassifierAccess().getToKeyword_1_2());
                      				
                    }

                    }

                    // InternalKactors.g:1793:4: ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) )
                    // InternalKactors.g:1794:5: ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber )
                    {
                    // InternalKactors.g:1798:5: (lv_int1_6_0= ruleNumber )
                    // InternalKactors.g:1799:6: lv_int1_6_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassifierAccess().getInt1NumberParserRuleCall_1_3_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

                    // InternalKactors.g:1816:4: ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )?
                    int alt35=3;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==46) ) {
                        alt35=1;
                    }
                    else if ( (LA35_0==47) ) {
                        alt35=2;
                    }
                    switch (alt35) {
                        case 1 :
                            // InternalKactors.g:1817:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:1817:5: ( (lv_rightLimit_7_0= 'inclusive' ) )
                            // InternalKactors.g:1818:6: (lv_rightLimit_7_0= 'inclusive' )
                            {
                            // InternalKactors.g:1818:6: (lv_rightLimit_7_0= 'inclusive' )
                            // InternalKactors.g:1819:7: lv_rightLimit_7_0= 'inclusive'
                            {
                            lv_rightLimit_7_0=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:1832:5: otherlv_8= 'exclusive'
                            {
                            otherlv_8=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:1839:3: ( (lv_num_9_0= ruleNumber ) )
                    {
                    // InternalKactors.g:1839:3: ( (lv_num_9_0= ruleNumber ) )
                    // InternalKactors.g:1840:4: (lv_num_9_0= ruleNumber )
                    {
                    // InternalKactors.g:1840:4: (lv_num_9_0= ruleNumber )
                    // InternalKactors.g:1841:5: lv_num_9_0= ruleNumber
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
                    // InternalKactors.g:1859:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    {
                    // InternalKactors.g:1859:3: (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) )
                    // InternalKactors.g:1860:4: otherlv_10= 'in' ( (lv_set_11_0= ruleList ) )
                    {
                    otherlv_10=(Token)match(input,49,FOLLOW_37); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getClassifierAccess().getInKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:1864:4: ( (lv_set_11_0= ruleList ) )
                    // InternalKactors.g:1865:5: (lv_set_11_0= ruleList )
                    {
                    // InternalKactors.g:1865:5: (lv_set_11_0= ruleList )
                    // InternalKactors.g:1866:6: lv_set_11_0= ruleList
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
                    // InternalKactors.g:1885:3: ( (lv_string_12_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:1885:3: ( (lv_string_12_0= RULE_STRING ) )
                    // InternalKactors.g:1886:4: (lv_string_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:1886:4: (lv_string_12_0= RULE_STRING )
                    // InternalKactors.g:1887:5: lv_string_12_0= RULE_STRING
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
                    // InternalKactors.g:1904:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:1904:3: ( (lv_observable_13_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:1905:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:1905:4: (lv_observable_13_0= RULE_OBSERVABLE )
                    // InternalKactors.g:1906:5: lv_observable_13_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:1923:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:1923:3: ( (lv_id_14_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:1924:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:1924:4: (lv_id_14_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:1925:5: lv_id_14_0= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:1942:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:1942:3: ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) )
                    // InternalKactors.g:1943:4: ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) )
                    {
                    // InternalKactors.g:1943:4: ( (lv_op_15_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:1944:5: (lv_op_15_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:1944:5: (lv_op_15_0= ruleREL_OPERATOR )
                    // InternalKactors.g:1945:6: lv_op_15_0= ruleREL_OPERATOR
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

                    // InternalKactors.g:1962:4: ( (lv_expression_16_0= ruleNumber ) )
                    // InternalKactors.g:1963:5: (lv_expression_16_0= ruleNumber )
                    {
                    // InternalKactors.g:1963:5: (lv_expression_16_0= ruleNumber )
                    // InternalKactors.g:1964:6: lv_expression_16_0= ruleNumber
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
                    // InternalKactors.g:1983:3: ( (lv_nodata_17_0= 'unknown' ) )
                    {
                    // InternalKactors.g:1983:3: ( (lv_nodata_17_0= 'unknown' ) )
                    // InternalKactors.g:1984:4: (lv_nodata_17_0= 'unknown' )
                    {
                    // InternalKactors.g:1984:4: (lv_nodata_17_0= 'unknown' )
                    // InternalKactors.g:1985:5: lv_nodata_17_0= 'unknown'
                    {
                    lv_nodata_17_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:1998:3: ( (lv_star_18_0= '*' ) )
                    {
                    // InternalKactors.g:1998:3: ( (lv_star_18_0= '*' ) )
                    // InternalKactors.g:1999:4: (lv_star_18_0= '*' )
                    {
                    // InternalKactors.g:1999:4: (lv_star_18_0= '*' )
                    // InternalKactors.g:2000:5: lv_star_18_0= '*'
                    {
                    lv_star_18_0=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:2016:1: entryRuleLookupTable returns [EObject current=null] : iv_ruleLookupTable= ruleLookupTable EOF ;
    public final EObject entryRuleLookupTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLookupTable = null;


        try {
            // InternalKactors.g:2016:52: (iv_ruleLookupTable= ruleLookupTable EOF )
            // InternalKactors.g:2017:2: iv_ruleLookupTable= ruleLookupTable EOF
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
    // InternalKactors.g:2023:1: ruleLookupTable returns [EObject current=null] : ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) ;
    public final EObject ruleLookupTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_table_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2029:2: ( ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' ) )
            // InternalKactors.g:2030:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            {
            // InternalKactors.g:2030:2: ( () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}' )
            // InternalKactors.g:2031:3: () otherlv_1= '{{' ( (lv_table_2_0= ruleTable ) )? otherlv_3= '}}'
            {
            // InternalKactors.g:2031:3: ()
            // InternalKactors.g:2032:4: 
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

            otherlv_1=(Token)match(input,52,FOLLOW_38); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getLookupTableAccess().getLeftCurlyBracketLeftCurlyBracketKeyword_1());
              		
            }
            // InternalKactors.g:2045:3: ( (lv_table_2_0= ruleTable ) )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_LOWERCASE_ID||LA37_0==RULE_STRING||(LA37_0>=RULE_OBSERVABLE && LA37_0<=RULE_EXPR)||LA37_0==RULE_INT||LA37_0==38||LA37_0==40||(LA37_0>=44 && LA37_0<=45)||(LA37_0>=49 && LA37_0<=51)||(LA37_0>=58 && LA37_0<=62)||(LA37_0>=70 && LA37_0<=71)) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalKactors.g:2046:4: (lv_table_2_0= ruleTable )
                    {
                    // InternalKactors.g:2046:4: (lv_table_2_0= ruleTable )
                    // InternalKactors.g:2047:5: lv_table_2_0= ruleTable
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLookupTableAccess().getTableTableParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_39);
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

            otherlv_3=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:2072:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalKactors.g:2072:46: (iv_ruleTable= ruleTable EOF )
            // InternalKactors.g:2073:2: iv_ruleTable= ruleTable EOF
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
    // InternalKactors.g:2079:1: ruleTable returns [EObject current=null] : ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token this_SEPARATOR_1=null;
        Token otherlv_3=null;
        EObject lv_headers_0_0 = null;

        EObject lv_rows_2_0 = null;

        EObject lv_rows_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2085:2: ( ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* ) )
            // InternalKactors.g:2086:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            {
            // InternalKactors.g:2086:2: ( ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )* )
            // InternalKactors.g:2087:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )? ( (lv_rows_2_0= ruleTableRow ) ) (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            {
            // InternalKactors.g:2087:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?
            int alt38=2;
            alt38 = dfa38.predict(input);
            switch (alt38) {
                case 1 :
                    // InternalKactors.g:2088:4: ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR
                    {
                    // InternalKactors.g:2088:4: ( (lv_headers_0_0= ruleHeaderRow ) )
                    // InternalKactors.g:2089:5: (lv_headers_0_0= ruleHeaderRow )
                    {
                    // InternalKactors.g:2089:5: (lv_headers_0_0= ruleHeaderRow )
                    // InternalKactors.g:2090:6: lv_headers_0_0= ruleHeaderRow
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableAccess().getHeadersHeaderRowParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_40);
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

                    this_SEPARATOR_1=(Token)match(input,RULE_SEPARATOR,FOLLOW_41); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SEPARATOR_1, grammarAccess.getTableAccess().getSEPARATORTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:2112:3: ( (lv_rows_2_0= ruleTableRow ) )
            // InternalKactors.g:2113:4: (lv_rows_2_0= ruleTableRow )
            {
            // InternalKactors.g:2113:4: (lv_rows_2_0= ruleTableRow )
            // InternalKactors.g:2114:5: lv_rows_2_0= ruleTableRow
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_18);
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

            // InternalKactors.g:2131:3: (otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==24) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalKactors.g:2132:4: otherlv_3= ',' ( (lv_rows_4_0= ruleTableRow ) )
            	    {
            	    otherlv_3=(Token)match(input,24,FOLLOW_41); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getCommaKeyword_2_0());
            	      			
            	    }
            	    // InternalKactors.g:2136:4: ( (lv_rows_4_0= ruleTableRow ) )
            	    // InternalKactors.g:2137:5: (lv_rows_4_0= ruleTableRow )
            	    {
            	    // InternalKactors.g:2137:5: (lv_rows_4_0= ruleTableRow )
            	    // InternalKactors.g:2138:6: lv_rows_4_0= ruleTableRow
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableAccess().getRowsTableRowParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_18);
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
            	    break loop39;
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
    // InternalKactors.g:2160:1: entryRuleHeaderRow returns [EObject current=null] : iv_ruleHeaderRow= ruleHeaderRow EOF ;
    public final EObject entryRuleHeaderRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeaderRow = null;


        try {
            // InternalKactors.g:2160:50: (iv_ruleHeaderRow= ruleHeaderRow EOF )
            // InternalKactors.g:2161:2: iv_ruleHeaderRow= ruleHeaderRow EOF
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
    // InternalKactors.g:2167:1: ruleHeaderRow returns [EObject current=null] : ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) ;
    public final EObject ruleHeaderRow() throws RecognitionException {
        EObject current = null;

        Token lv_elements_0_1=null;
        Token lv_elements_0_2=null;
        Token otherlv_1=null;
        Token lv_elements_2_1=null;
        Token lv_elements_2_2=null;


        	enterRule();

        try {
            // InternalKactors.g:2173:2: ( ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* ) )
            // InternalKactors.g:2174:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            {
            // InternalKactors.g:2174:2: ( ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )* )
            // InternalKactors.g:2175:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) ) (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            {
            // InternalKactors.g:2175:3: ( ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) ) )
            // InternalKactors.g:2176:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            {
            // InternalKactors.g:2176:4: ( (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING ) )
            // InternalKactors.g:2177:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            {
            // InternalKactors.g:2177:5: (lv_elements_0_1= RULE_LOWERCASE_ID | lv_elements_0_2= RULE_STRING )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==RULE_LOWERCASE_ID) ) {
                alt40=1;
            }
            else if ( (LA40_0==RULE_STRING) ) {
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
                    // InternalKactors.g:2178:6: lv_elements_0_1= RULE_LOWERCASE_ID
                    {
                    lv_elements_0_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_42); if (state.failed) return current;
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
                    // InternalKactors.g:2193:6: lv_elements_0_2= RULE_STRING
                    {
                    lv_elements_0_2=(Token)match(input,RULE_STRING,FOLLOW_42); if (state.failed) return current;
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

            // InternalKactors.g:2210:3: (otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) ) )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==54) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalKactors.g:2211:4: otherlv_1= '|' ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    {
            	    otherlv_1=(Token)match(input,54,FOLLOW_43); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getHeaderRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:2215:4: ( ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) ) )
            	    // InternalKactors.g:2216:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    {
            	    // InternalKactors.g:2216:5: ( (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING ) )
            	    // InternalKactors.g:2217:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    {
            	    // InternalKactors.g:2217:6: (lv_elements_2_1= RULE_LOWERCASE_ID | lv_elements_2_2= RULE_STRING )
            	    int alt41=2;
            	    int LA41_0 = input.LA(1);

            	    if ( (LA41_0==RULE_LOWERCASE_ID) ) {
            	        alt41=1;
            	    }
            	    else if ( (LA41_0==RULE_STRING) ) {
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
            	            // InternalKactors.g:2218:7: lv_elements_2_1= RULE_LOWERCASE_ID
            	            {
            	            lv_elements_2_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_42); if (state.failed) return current;
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
            	            // InternalKactors.g:2233:7: lv_elements_2_2= RULE_STRING
            	            {
            	            lv_elements_2_2=(Token)match(input,RULE_STRING,FOLLOW_42); if (state.failed) return current;
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
            	    break loop42;
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
    // InternalKactors.g:2255:1: entryRuleTableRow returns [EObject current=null] : iv_ruleTableRow= ruleTableRow EOF ;
    public final EObject entryRuleTableRow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableRow = null;


        try {
            // InternalKactors.g:2255:49: (iv_ruleTableRow= ruleTableRow EOF )
            // InternalKactors.g:2256:2: iv_ruleTableRow= ruleTableRow EOF
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
    // InternalKactors.g:2262:1: ruleTableRow returns [EObject current=null] : ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) ;
    public final EObject ruleTableRow() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_elements_0_0 = null;

        EObject lv_elements_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2268:2: ( ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* ) )
            // InternalKactors.g:2269:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            {
            // InternalKactors.g:2269:2: ( ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )* )
            // InternalKactors.g:2270:3: ( (lv_elements_0_0= ruleTableClassifier ) ) (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            {
            // InternalKactors.g:2270:3: ( (lv_elements_0_0= ruleTableClassifier ) )
            // InternalKactors.g:2271:4: (lv_elements_0_0= ruleTableClassifier )
            {
            // InternalKactors.g:2271:4: (lv_elements_0_0= ruleTableClassifier )
            // InternalKactors.g:2272:5: lv_elements_0_0= ruleTableClassifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_42);
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

            // InternalKactors.g:2289:3: (otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==54) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalKactors.g:2290:4: otherlv_1= '|' ( (lv_elements_2_0= ruleTableClassifier ) )
            	    {
            	    otherlv_1=(Token)match(input,54,FOLLOW_41); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getTableRowAccess().getVerticalLineKeyword_1_0());
            	      			
            	    }
            	    // InternalKactors.g:2294:4: ( (lv_elements_2_0= ruleTableClassifier ) )
            	    // InternalKactors.g:2295:5: (lv_elements_2_0= ruleTableClassifier )
            	    {
            	    // InternalKactors.g:2295:5: (lv_elements_2_0= ruleTableClassifier )
            	    // InternalKactors.g:2296:6: lv_elements_2_0= ruleTableClassifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getTableRowAccess().getElementsTableClassifierParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_42);
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
            	    break loop43;
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
    // InternalKactors.g:2318:1: entryRuleTableClassifier returns [EObject current=null] : iv_ruleTableClassifier= ruleTableClassifier EOF ;
    public final EObject entryRuleTableClassifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableClassifier = null;


        try {
            // InternalKactors.g:2318:56: (iv_ruleTableClassifier= ruleTableClassifier EOF )
            // InternalKactors.g:2319:2: iv_ruleTableClassifier= ruleTableClassifier EOF
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
    // InternalKactors.g:2325:1: ruleTableClassifier returns [EObject current=null] : ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) ;
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
            // InternalKactors.g:2331:2: ( ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) ) )
            // InternalKactors.g:2332:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            {
            // InternalKactors.g:2332:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )
            int alt47=13;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // InternalKactors.g:2333:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    {
                    // InternalKactors.g:2333:3: ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) )
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==44) ) {
                        alt44=1;
                    }
                    else if ( (LA44_0==45) ) {
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
                            // InternalKactors.g:2334:4: ( (lv_boolean_0_0= 'true' ) )
                            {
                            // InternalKactors.g:2334:4: ( (lv_boolean_0_0= 'true' ) )
                            // InternalKactors.g:2335:5: (lv_boolean_0_0= 'true' )
                            {
                            // InternalKactors.g:2335:5: (lv_boolean_0_0= 'true' )
                            // InternalKactors.g:2336:6: lv_boolean_0_0= 'true'
                            {
                            lv_boolean_0_0=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:2349:4: ( (lv_boolean_1_0= 'false' ) )
                            {
                            // InternalKactors.g:2349:4: ( (lv_boolean_1_0= 'false' ) )
                            // InternalKactors.g:2350:5: (lv_boolean_1_0= 'false' )
                            {
                            // InternalKactors.g:2350:5: (lv_boolean_1_0= 'false' )
                            // InternalKactors.g:2351:6: lv_boolean_1_0= 'false'
                            {
                            lv_boolean_1_0=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:2365:3: ( (lv_num_2_0= ruleNumber ) )
                    {
                    // InternalKactors.g:2365:3: ( (lv_num_2_0= ruleNumber ) )
                    // InternalKactors.g:2366:4: (lv_num_2_0= ruleNumber )
                    {
                    // InternalKactors.g:2366:4: (lv_num_2_0= ruleNumber )
                    // InternalKactors.g:2367:5: lv_num_2_0= ruleNumber
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
                    // InternalKactors.g:2385:3: ( (lv_string_3_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:2385:3: ( (lv_string_3_0= RULE_STRING ) )
                    // InternalKactors.g:2386:4: (lv_string_3_0= RULE_STRING )
                    {
                    // InternalKactors.g:2386:4: (lv_string_3_0= RULE_STRING )
                    // InternalKactors.g:2387:5: lv_string_3_0= RULE_STRING
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
                    // InternalKactors.g:2404:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:2404:3: ( (lv_observable_4_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:2405:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:2405:4: (lv_observable_4_0= RULE_OBSERVABLE )
                    // InternalKactors.g:2406:5: lv_observable_4_0= RULE_OBSERVABLE
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
                    // InternalKactors.g:2423:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:2423:3: ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) )
                    // InternalKactors.g:2424:4: ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) )
                    {
                    // InternalKactors.g:2424:4: ( (lv_op_5_0= ruleREL_OPERATOR ) )
                    // InternalKactors.g:2425:5: (lv_op_5_0= ruleREL_OPERATOR )
                    {
                    // InternalKactors.g:2425:5: (lv_op_5_0= ruleREL_OPERATOR )
                    // InternalKactors.g:2426:6: lv_op_5_0= ruleREL_OPERATOR
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

                    // InternalKactors.g:2443:4: ( (lv_expression_6_0= ruleNumber ) )
                    // InternalKactors.g:2444:5: (lv_expression_6_0= ruleNumber )
                    {
                    // InternalKactors.g:2444:5: (lv_expression_6_0= ruleNumber )
                    // InternalKactors.g:2445:6: lv_expression_6_0= ruleNumber
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
                    // InternalKactors.g:2464:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    {
                    // InternalKactors.g:2464:3: ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? )
                    // InternalKactors.g:2465:4: ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    {
                    // InternalKactors.g:2465:4: ( (lv_int0_7_0= ruleNumber ) )
                    // InternalKactors.g:2466:5: (lv_int0_7_0= ruleNumber )
                    {
                    // InternalKactors.g:2466:5: (lv_int0_7_0= ruleNumber )
                    // InternalKactors.g:2467:6: lv_int0_7_0= ruleNumber
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

                    // InternalKactors.g:2484:4: ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )?
                    int alt45=3;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==46) ) {
                        alt45=1;
                    }
                    else if ( (LA45_0==47) ) {
                        alt45=2;
                    }
                    switch (alt45) {
                        case 1 :
                            // InternalKactors.g:2485:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2485:5: ( (lv_leftLimit_8_0= 'inclusive' ) )
                            // InternalKactors.g:2486:6: (lv_leftLimit_8_0= 'inclusive' )
                            {
                            // InternalKactors.g:2486:6: (lv_leftLimit_8_0= 'inclusive' )
                            // InternalKactors.g:2487:7: lv_leftLimit_8_0= 'inclusive'
                            {
                            lv_leftLimit_8_0=(Token)match(input,46,FOLLOW_34); if (state.failed) return current;
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
                            // InternalKactors.g:2500:5: otherlv_9= 'exclusive'
                            {
                            otherlv_9=(Token)match(input,47,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_9, grammarAccess.getTableClassifierAccess().getExclusiveKeyword_5_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:2505:4: ( ( 'to' )=>otherlv_10= 'to' )
                    // InternalKactors.g:2506:5: ( 'to' )=>otherlv_10= 'to'
                    {
                    otherlv_10=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_10, grammarAccess.getTableClassifierAccess().getToKeyword_5_2());
                      				
                    }

                    }

                    // InternalKactors.g:2512:4: ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) )
                    // InternalKactors.g:2513:5: ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber )
                    {
                    // InternalKactors.g:2517:5: (lv_int1_11_0= ruleNumber )
                    // InternalKactors.g:2518:6: lv_int1_11_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTableClassifierAccess().getInt1NumberParserRuleCall_5_3_0());
                      					
                    }
                    pushFollow(FOLLOW_36);
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

                    // InternalKactors.g:2535:4: ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )?
                    int alt46=3;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==46) ) {
                        alt46=1;
                    }
                    else if ( (LA46_0==47) ) {
                        alt46=2;
                    }
                    switch (alt46) {
                        case 1 :
                            // InternalKactors.g:2536:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:2536:5: ( (lv_rightLimit_12_0= 'inclusive' ) )
                            // InternalKactors.g:2537:6: (lv_rightLimit_12_0= 'inclusive' )
                            {
                            // InternalKactors.g:2537:6: (lv_rightLimit_12_0= 'inclusive' )
                            // InternalKactors.g:2538:7: lv_rightLimit_12_0= 'inclusive'
                            {
                            lv_rightLimit_12_0=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:2551:5: otherlv_13= 'exclusive'
                            {
                            otherlv_13=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:2558:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    {
                    // InternalKactors.g:2558:3: (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) )
                    // InternalKactors.g:2559:4: otherlv_14= 'in' ( (lv_set_15_0= ruleList ) )
                    {
                    otherlv_14=(Token)match(input,49,FOLLOW_37); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_14, grammarAccess.getTableClassifierAccess().getInKeyword_6_0());
                      			
                    }
                    // InternalKactors.g:2563:4: ( (lv_set_15_0= ruleList ) )
                    // InternalKactors.g:2564:5: (lv_set_15_0= ruleList )
                    {
                    // InternalKactors.g:2564:5: (lv_set_15_0= ruleList )
                    // InternalKactors.g:2565:6: lv_set_15_0= ruleList
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
                    // InternalKactors.g:2584:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    {
                    // InternalKactors.g:2584:3: ( (lv_quantity_16_0= ruleQuantity ) )
                    // InternalKactors.g:2585:4: (lv_quantity_16_0= ruleQuantity )
                    {
                    // InternalKactors.g:2585:4: (lv_quantity_16_0= ruleQuantity )
                    // InternalKactors.g:2586:5: lv_quantity_16_0= ruleQuantity
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
                    // InternalKactors.g:2604:3: ( (lv_date_17_0= ruleDate ) )
                    {
                    // InternalKactors.g:2604:3: ( (lv_date_17_0= ruleDate ) )
                    // InternalKactors.g:2605:4: (lv_date_17_0= ruleDate )
                    {
                    // InternalKactors.g:2605:4: (lv_date_17_0= ruleDate )
                    // InternalKactors.g:2606:5: lv_date_17_0= ruleDate
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
                    // InternalKactors.g:2624:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:2624:3: ( (lv_expr_18_0= RULE_EXPR ) )
                    // InternalKactors.g:2625:4: (lv_expr_18_0= RULE_EXPR )
                    {
                    // InternalKactors.g:2625:4: (lv_expr_18_0= RULE_EXPR )
                    // InternalKactors.g:2626:5: lv_expr_18_0= RULE_EXPR
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
                    // InternalKactors.g:2643:3: ( (lv_nodata_19_0= 'unknown' ) )
                    {
                    // InternalKactors.g:2643:3: ( (lv_nodata_19_0= 'unknown' ) )
                    // InternalKactors.g:2644:4: (lv_nodata_19_0= 'unknown' )
                    {
                    // InternalKactors.g:2644:4: (lv_nodata_19_0= 'unknown' )
                    // InternalKactors.g:2645:5: lv_nodata_19_0= 'unknown'
                    {
                    lv_nodata_19_0=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:2658:3: ( (lv_star_20_0= '*' ) )
                    {
                    // InternalKactors.g:2658:3: ( (lv_star_20_0= '*' ) )
                    // InternalKactors.g:2659:4: (lv_star_20_0= '*' )
                    {
                    // InternalKactors.g:2659:4: (lv_star_20_0= '*' )
                    // InternalKactors.g:2660:5: lv_star_20_0= '*'
                    {
                    lv_star_20_0=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:2673:3: ( (lv_anything_21_0= '#' ) )
                    {
                    // InternalKactors.g:2673:3: ( (lv_anything_21_0= '#' ) )
                    // InternalKactors.g:2674:4: (lv_anything_21_0= '#' )
                    {
                    // InternalKactors.g:2674:4: (lv_anything_21_0= '#' )
                    // InternalKactors.g:2675:5: lv_anything_21_0= '#'
                    {
                    lv_anything_21_0=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:2691:1: entryRuleQuantity returns [EObject current=null] : iv_ruleQuantity= ruleQuantity EOF ;
    public final EObject entryRuleQuantity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQuantity = null;


        try {
            // InternalKactors.g:2691:49: (iv_ruleQuantity= ruleQuantity EOF )
            // InternalKactors.g:2692:2: iv_ruleQuantity= ruleQuantity EOF
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
    // InternalKactors.g:2698:1: ruleQuantity returns [EObject current=null] : ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) ;
    public final EObject ruleQuantity() throws RecognitionException {
        EObject current = null;

        Token lv_over_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_0_0 = null;

        EObject lv_unit_3_0 = null;

        EObject lv_currency_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2704:2: ( ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) ) )
            // InternalKactors.g:2705:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            {
            // InternalKactors.g:2705:2: ( ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) ) )
            // InternalKactors.g:2706:3: ( (lv_value_0_0= ruleNumber ) ) ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' ) ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            {
            // InternalKactors.g:2706:3: ( (lv_value_0_0= ruleNumber ) )
            // InternalKactors.g:2707:4: (lv_value_0_0= ruleNumber )
            {
            // InternalKactors.g:2707:4: (lv_value_0_0= ruleNumber )
            // InternalKactors.g:2708:5: lv_value_0_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getQuantityAccess().getValueNumberParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_44);
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

            // InternalKactors.g:2725:3: ( ( (lv_over_1_0= '/' ) ) | otherlv_2= '.' )
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==55) ) {
                alt48=1;
            }
            else if ( (LA48_0==56) ) {
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
                    // InternalKactors.g:2726:4: ( (lv_over_1_0= '/' ) )
                    {
                    // InternalKactors.g:2726:4: ( (lv_over_1_0= '/' ) )
                    // InternalKactors.g:2727:5: (lv_over_1_0= '/' )
                    {
                    // InternalKactors.g:2727:5: (lv_over_1_0= '/' )
                    // InternalKactors.g:2728:6: lv_over_1_0= '/'
                    {
                    lv_over_1_0=(Token)match(input,55,FOLLOW_45); if (state.failed) return current;
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
                    // InternalKactors.g:2741:4: otherlv_2= '.'
                    {
                    otherlv_2=(Token)match(input,56,FOLLOW_45); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getQuantityAccess().getFullStopKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:2746:3: ( ( (lv_unit_3_0= ruleUnit ) ) | ( (lv_currency_4_0= ruleCurrency ) ) )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==EOF||LA49_0==RULE_LOWERCASE_ID||LA49_0==RULE_CAMELCASE_ID||LA49_0==24||LA49_0==35||LA49_0==51||(LA49_0>=53 && LA49_0<=55)||LA49_0==69||LA49_0==78) ) {
                alt49=1;
            }
            else if ( (LA49_0==RULE_UPPERCASE_ID) ) {
                int LA49_2 = input.LA(2);

                if ( (LA49_2==EOF||LA49_2==24||LA49_2==51||(LA49_2>=53 && LA49_2<=55)||LA49_2==69||LA49_2==78) ) {
                    alt49=1;
                }
                else if ( (LA49_2==57) ) {
                    alt49=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 49, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // InternalKactors.g:2747:4: ( (lv_unit_3_0= ruleUnit ) )
                    {
                    // InternalKactors.g:2747:4: ( (lv_unit_3_0= ruleUnit ) )
                    // InternalKactors.g:2748:5: (lv_unit_3_0= ruleUnit )
                    {
                    // InternalKactors.g:2748:5: (lv_unit_3_0= ruleUnit )
                    // InternalKactors.g:2749:6: lv_unit_3_0= ruleUnit
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
                    // InternalKactors.g:2767:4: ( (lv_currency_4_0= ruleCurrency ) )
                    {
                    // InternalKactors.g:2767:4: ( (lv_currency_4_0= ruleCurrency ) )
                    // InternalKactors.g:2768:5: (lv_currency_4_0= ruleCurrency )
                    {
                    // InternalKactors.g:2768:5: (lv_currency_4_0= ruleCurrency )
                    // InternalKactors.g:2769:6: lv_currency_4_0= ruleCurrency
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


    // $ANTLR start "entryRuleUnitElement"
    // InternalKactors.g:2791:1: entryRuleUnitElement returns [EObject current=null] : iv_ruleUnitElement= ruleUnitElement EOF ;
    public final EObject entryRuleUnitElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitElement = null;


        try {
            // InternalKactors.g:2791:52: (iv_ruleUnitElement= ruleUnitElement EOF )
            // InternalKactors.g:2792:2: iv_ruleUnitElement= ruleUnitElement EOF
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
    // InternalKactors.g:2798:1: ruleUnitElement returns [EObject current=null] : ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) ;
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
            // InternalKactors.g:2804:2: ( ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) ) )
            // InternalKactors.g:2805:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            {
            // InternalKactors.g:2805:2: ( ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) ) | (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' ) )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==RULE_LOWERCASE_ID||(LA51_0>=RULE_CAMELCASE_ID && LA51_0<=RULE_UPPERCASE_ID)) ) {
                alt51=1;
            }
            else if ( (LA51_0==35) ) {
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
                    // InternalKactors.g:2806:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    {
                    // InternalKactors.g:2806:3: ( ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) ) )
                    // InternalKactors.g:2807:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    {
                    // InternalKactors.g:2807:4: ( (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID ) )
                    // InternalKactors.g:2808:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    {
                    // InternalKactors.g:2808:5: (lv_id_0_1= RULE_CAMELCASE_ID | lv_id_0_2= RULE_LOWERCASE_ID | lv_id_0_3= RULE_UPPERCASE_ID )
                    int alt50=3;
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
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 50, 0, input);

                        throw nvae;
                    }

                    switch (alt50) {
                        case 1 :
                            // InternalKactors.g:2809:6: lv_id_0_1= RULE_CAMELCASE_ID
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
                            // InternalKactors.g:2824:6: lv_id_0_2= RULE_LOWERCASE_ID
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
                            // InternalKactors.g:2839:6: lv_id_0_3= RULE_UPPERCASE_ID
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
                    // InternalKactors.g:2857:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    {
                    // InternalKactors.g:2857:3: (otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')' )
                    // InternalKactors.g:2858:4: otherlv_1= '(' ( (lv_unit_2_0= ruleUnit ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_46); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getUnitElementAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:2862:4: ( (lv_unit_2_0= ruleUnit ) )
                    // InternalKactors.g:2863:5: (lv_unit_2_0= ruleUnit )
                    {
                    // InternalKactors.g:2863:5: (lv_unit_2_0= ruleUnit )
                    // InternalKactors.g:2864:6: lv_unit_2_0= ruleUnit
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitElementAccess().getUnitUnitParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_23);
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

                    otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:2890:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalKactors.g:2890:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalKactors.g:2891:2: iv_ruleUnit= ruleUnit EOF
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
    // InternalKactors.g:2897:1: ruleUnit returns [EObject current=null] : ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        EObject lv_root_1_0 = null;

        Enumerator lv_connectors_2_0 = null;

        EObject lv_units_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2903:2: ( ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:2904:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:2904:2: ( () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:2905:3: () ( (lv_root_1_0= ruleUnitElement ) )? ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:2905:3: ()
            // InternalKactors.g:2906:4: 
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

            // InternalKactors.g:2915:3: ( (lv_root_1_0= ruleUnitElement ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_LOWERCASE_ID||(LA52_0>=RULE_CAMELCASE_ID && LA52_0<=RULE_UPPERCASE_ID)||LA52_0==35) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalKactors.g:2916:4: (lv_root_1_0= ruleUnitElement )
                    {
                    // InternalKactors.g:2916:4: (lv_root_1_0= ruleUnitElement )
                    // InternalKactors.g:2917:5: lv_root_1_0= ruleUnitElement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getUnitAccess().getRootUnitElementParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_47);
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

            // InternalKactors.g:2934:3: ( ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==51||LA53_0==55||LA53_0==78) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalKactors.g:2935:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) ) ( (lv_units_3_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:2935:4: ( ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) ) )
            	    // InternalKactors.g:2936:5: ( ( ( ruleUnitOp ) ) )=> ( (lv_connectors_2_0= ruleUnitOp ) )
            	    {
            	    // InternalKactors.g:2942:5: ( (lv_connectors_2_0= ruleUnitOp ) )
            	    // InternalKactors.g:2943:6: (lv_connectors_2_0= ruleUnitOp )
            	    {
            	    // InternalKactors.g:2943:6: (lv_connectors_2_0= ruleUnitOp )
            	    // InternalKactors.g:2944:7: lv_connectors_2_0= ruleUnitOp
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getUnitAccess().getConnectorsUnitOpEnumRuleCall_2_0_0_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_48);
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

            	    // InternalKactors.g:2962:4: ( (lv_units_3_0= ruleUnitElement ) )
            	    // InternalKactors.g:2963:5: (lv_units_3_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:2963:5: (lv_units_3_0= ruleUnitElement )
            	    // InternalKactors.g:2964:6: lv_units_3_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getUnitAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
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


    // $ANTLR start "entryRuleCurrency"
    // InternalKactors.g:2986:1: entryRuleCurrency returns [EObject current=null] : iv_ruleCurrency= ruleCurrency EOF ;
    public final EObject entryRuleCurrency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCurrency = null;


        try {
            // InternalKactors.g:2986:49: (iv_ruleCurrency= ruleCurrency EOF )
            // InternalKactors.g:2987:2: iv_ruleCurrency= ruleCurrency EOF
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
    // InternalKactors.g:2993:1: ruleCurrency returns [EObject current=null] : ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) ;
    public final EObject ruleCurrency() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_year_2_0=null;
        Token otherlv_3=null;
        EObject lv_units_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:2999:2: ( ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* ) )
            // InternalKactors.g:3000:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            {
            // InternalKactors.g:3000:2: ( ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )* )
            // InternalKactors.g:3001:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) ) (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) ) ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            {
            // InternalKactors.g:3001:3: ( (lv_id_0_0= RULE_UPPERCASE_ID ) )
            // InternalKactors.g:3002:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            {
            // InternalKactors.g:3002:4: (lv_id_0_0= RULE_UPPERCASE_ID )
            // InternalKactors.g:3003:5: lv_id_0_0= RULE_UPPERCASE_ID
            {
            lv_id_0_0=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_49); if (state.failed) return current;
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

            // InternalKactors.g:3019:3: (otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) ) )
            // InternalKactors.g:3020:4: otherlv_1= '@' ( (lv_year_2_0= RULE_INT ) )
            {
            otherlv_1=(Token)match(input,57,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_1, grammarAccess.getCurrencyAccess().getCommercialAtKeyword_1_0());
              			
            }
            // InternalKactors.g:3024:4: ( (lv_year_2_0= RULE_INT ) )
            // InternalKactors.g:3025:5: (lv_year_2_0= RULE_INT )
            {
            // InternalKactors.g:3025:5: (lv_year_2_0= RULE_INT )
            // InternalKactors.g:3026:6: lv_year_2_0= RULE_INT
            {
            lv_year_2_0=(Token)match(input,RULE_INT,FOLLOW_50); if (state.failed) return current;
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

            // InternalKactors.g:3043:3: ( ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==55) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalKactors.g:3044:4: ( ( '/' )=>otherlv_3= '/' ) ( (lv_units_4_0= ruleUnitElement ) )
            	    {
            	    // InternalKactors.g:3044:4: ( ( '/' )=>otherlv_3= '/' )
            	    // InternalKactors.g:3045:5: ( '/' )=>otherlv_3= '/'
            	    {
            	    otherlv_3=(Token)match(input,55,FOLLOW_48); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_3, grammarAccess.getCurrencyAccess().getSolidusKeyword_2_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:3051:4: ( (lv_units_4_0= ruleUnitElement ) )
            	    // InternalKactors.g:3052:5: (lv_units_4_0= ruleUnitElement )
            	    {
            	    // InternalKactors.g:3052:5: (lv_units_4_0= ruleUnitElement )
            	    // InternalKactors.g:3053:6: lv_units_4_0= ruleUnitElement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getCurrencyAccess().getUnitsUnitElementParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_50);
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
    // $ANTLR end "ruleCurrency"


    // $ANTLR start "entryRuleREL_OPERATOR"
    // InternalKactors.g:3075:1: entryRuleREL_OPERATOR returns [EObject current=null] : iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF ;
    public final EObject entryRuleREL_OPERATOR() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleREL_OPERATOR = null;


        try {
            // InternalKactors.g:3075:53: (iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF )
            // InternalKactors.g:3076:2: iv_ruleREL_OPERATOR= ruleREL_OPERATOR EOF
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
    // InternalKactors.g:3082:1: ruleREL_OPERATOR returns [EObject current=null] : ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) ;
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
            // InternalKactors.g:3088:2: ( ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) ) )
            // InternalKactors.g:3089:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            {
            // InternalKactors.g:3089:2: ( ( (lv_gt_0_0= '>' ) ) | ( (lv_lt_1_0= '<' ) ) | ( (lv_eq_2_0= '=' ) ) | ( (lv_ne_3_0= '!=' ) ) | ( (lv_le_4_0= '<=' ) ) | ( (lv_ge_5_0= '>=' ) ) )
            int alt55=6;
            switch ( input.LA(1) ) {
            case 58:
                {
                alt55=1;
                }
                break;
            case 59:
                {
                alt55=2;
                }
                break;
            case 38:
                {
                alt55=3;
                }
                break;
            case 60:
                {
                alt55=4;
                }
                break;
            case 61:
                {
                alt55=5;
                }
                break;
            case 62:
                {
                alt55=6;
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
                    // InternalKactors.g:3090:3: ( (lv_gt_0_0= '>' ) )
                    {
                    // InternalKactors.g:3090:3: ( (lv_gt_0_0= '>' ) )
                    // InternalKactors.g:3091:4: (lv_gt_0_0= '>' )
                    {
                    // InternalKactors.g:3091:4: (lv_gt_0_0= '>' )
                    // InternalKactors.g:3092:5: lv_gt_0_0= '>'
                    {
                    lv_gt_0_0=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3105:3: ( (lv_lt_1_0= '<' ) )
                    {
                    // InternalKactors.g:3105:3: ( (lv_lt_1_0= '<' ) )
                    // InternalKactors.g:3106:4: (lv_lt_1_0= '<' )
                    {
                    // InternalKactors.g:3106:4: (lv_lt_1_0= '<' )
                    // InternalKactors.g:3107:5: lv_lt_1_0= '<'
                    {
                    lv_lt_1_0=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3120:3: ( (lv_eq_2_0= '=' ) )
                    {
                    // InternalKactors.g:3120:3: ( (lv_eq_2_0= '=' ) )
                    // InternalKactors.g:3121:4: (lv_eq_2_0= '=' )
                    {
                    // InternalKactors.g:3121:4: (lv_eq_2_0= '=' )
                    // InternalKactors.g:3122:5: lv_eq_2_0= '='
                    {
                    lv_eq_2_0=(Token)match(input,38,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3135:3: ( (lv_ne_3_0= '!=' ) )
                    {
                    // InternalKactors.g:3135:3: ( (lv_ne_3_0= '!=' ) )
                    // InternalKactors.g:3136:4: (lv_ne_3_0= '!=' )
                    {
                    // InternalKactors.g:3136:4: (lv_ne_3_0= '!=' )
                    // InternalKactors.g:3137:5: lv_ne_3_0= '!='
                    {
                    lv_ne_3_0=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3150:3: ( (lv_le_4_0= '<=' ) )
                    {
                    // InternalKactors.g:3150:3: ( (lv_le_4_0= '<=' ) )
                    // InternalKactors.g:3151:4: (lv_le_4_0= '<=' )
                    {
                    // InternalKactors.g:3151:4: (lv_le_4_0= '<=' )
                    // InternalKactors.g:3152:5: lv_le_4_0= '<='
                    {
                    lv_le_4_0=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
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
                    // InternalKactors.g:3165:3: ( (lv_ge_5_0= '>=' ) )
                    {
                    // InternalKactors.g:3165:3: ( (lv_ge_5_0= '>=' ) )
                    // InternalKactors.g:3166:4: (lv_ge_5_0= '>=' )
                    {
                    // InternalKactors.g:3166:4: (lv_ge_5_0= '>=' )
                    // InternalKactors.g:3167:5: lv_ge_5_0= '>='
                    {
                    lv_ge_5_0=(Token)match(input,62,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleLiteral"
    // InternalKactors.g:3183:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:3183:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:3184:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKactors.g:3190:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
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
            // InternalKactors.g:3196:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:3197:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:3197:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt57=5;
            alt57 = dfa57.predict(input);
            switch (alt57) {
                case 1 :
                    // InternalKactors.g:3198:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3198:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:3199:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:3199:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:3200:5: lv_number_0_0= ruleNumber
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
                    // InternalKactors.g:3218:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:3218:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:3219:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:3219:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:3220:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:3220:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:3221:6: lv_from_1_0= ruleNumber
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

                    otherlv_2=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:3242:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:3243:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:3243:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:3244:6: lv_to_3_0= ruleNumber
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
                    // InternalKactors.g:3263:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:3263:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:3264:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:3264:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:3265:5: lv_string_4_0= RULE_STRING
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
                    // InternalKactors.g:3282:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:3282:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:3283:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:3283:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:3284:5: lv_date_5_0= ruleDate
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
                    // InternalKactors.g:3302:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:3302:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:3303:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:3303:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:3304:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:3304:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==44) ) {
                        alt56=1;
                    }
                    else if ( (LA56_0==45) ) {
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
                            // InternalKactors.g:3305:6: lv_boolean_6_1= 'true'
                            {
                            lv_boolean_6_1=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:3316:6: lv_boolean_6_2= 'false'
                            {
                            lv_boolean_6_2=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
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


    // $ANTLR start "entryRuleBody"
    // InternalKactors.g:3333:1: entryRuleBody returns [EObject current=null] : iv_ruleBody= ruleBody EOF ;
    public final EObject entryRuleBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBody = null;


        try {
            // InternalKactors.g:3333:45: (iv_ruleBody= ruleBody EOF )
            // InternalKactors.g:3334:2: iv_ruleBody= ruleBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBody=ruleBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBody; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBody"


    // $ANTLR start "ruleBody"
    // InternalKactors.g:3340:1: ruleBody returns [EObject current=null] : ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )* )? otherlv_6= ')' ) ) ;
    public final EObject ruleBody() throws RecognitionException {
        EObject current = null;

        Token lv_isgroup_3_0=null;
        Token otherlv_6=null;
        EObject lv_list_1_0 = null;

        EObject lv_list_2_0 = null;

        EObject lv_list_4_0 = null;

        EObject lv_list_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3346:2: ( ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )* )? otherlv_6= ')' ) ) )
            // InternalKactors.g:3347:2: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )* )? otherlv_6= ')' ) )
            {
            // InternalKactors.g:3347:2: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )* )? otherlv_6= ')' ) )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==RULE_LOWERCASE_ID||LA61_0==RULE_EMBEDDEDTEXT||LA61_0==63||(LA61_0>=66 && LA61_0<=67)) ) {
                alt61=1;
            }
            else if ( (LA61_0==35) ) {
                int LA61_4 = input.LA(2);

                if ( (synpred115_InternalKactors()) ) {
                    alt61=1;
                }
                else if ( (true) ) {
                    alt61=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 61, 4, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    // InternalKactors.g:3348:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
                    {
                    // InternalKactors.g:3348:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
                    // InternalKactors.g:3349:4: () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )*
                    {
                    // InternalKactors.g:3349:4: ()
                    // InternalKactors.g:3350:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					/* */
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getBodyAccess().getBodyAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalKactors.g:3359:4: ( (lv_list_1_0= ruleStatement ) )
                    // InternalKactors.g:3360:5: (lv_list_1_0= ruleStatement )
                    {
                    // InternalKactors.g:3360:5: (lv_list_1_0= ruleStatement )
                    // InternalKactors.g:3361:6: lv_list_1_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
                    lv_list_1_0=ruleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getBodyRule());
                      						}
                      						add(
                      							current,
                      							"list",
                      							lv_list_1_0,
                      							"org.integratedmodelling.kactors.Kactors.Statement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:3378:4: ( (lv_list_2_0= ruleStatement ) )*
                    loop58:
                    do {
                        int alt58=2;
                        switch ( input.LA(1) ) {
                        case RULE_LOWERCASE_ID:
                            {
                            int LA58_2 = input.LA(2);

                            if ( (synpred114_InternalKactors()) ) {
                                alt58=1;
                            }


                            }
                            break;
                        case RULE_EMBEDDEDTEXT:
                            {
                            int LA58_3 = input.LA(2);

                            if ( (synpred114_InternalKactors()) ) {
                                alt58=1;
                            }


                            }
                            break;
                        case 63:
                            {
                            int LA58_4 = input.LA(2);

                            if ( (synpred114_InternalKactors()) ) {
                                alt58=1;
                            }


                            }
                            break;
                        case 35:
                            {
                            int LA58_5 = input.LA(2);

                            if ( (synpred114_InternalKactors()) ) {
                                alt58=1;
                            }


                            }
                            break;
                        case 66:
                            {
                            int LA58_6 = input.LA(2);

                            if ( (synpred114_InternalKactors()) ) {
                                alt58=1;
                            }


                            }
                            break;
                        case 67:
                            {
                            int LA58_7 = input.LA(2);

                            if ( (synpred114_InternalKactors()) ) {
                                alt58=1;
                            }


                            }
                            break;

                        }

                        switch (alt58) {
                    	case 1 :
                    	    // InternalKactors.g:3379:5: (lv_list_2_0= ruleStatement )
                    	    {
                    	    // InternalKactors.g:3379:5: (lv_list_2_0= ruleStatement )
                    	    // InternalKactors.g:3380:6: lv_list_2_0= ruleStatement
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_15);
                    	    lv_list_2_0=ruleStatement();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getBodyRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"list",
                    	      							lv_list_2_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Statement");
                    	      						afterParserOrEnumRuleCall();
                    	      					
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
                    break;
                case 2 :
                    // InternalKactors.g:3399:3: ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )* )? otherlv_6= ')' )
                    {
                    // InternalKactors.g:3399:3: ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )* )? otherlv_6= ')' )
                    // InternalKactors.g:3400:4: ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )* )? otherlv_6= ')'
                    {
                    // InternalKactors.g:3400:4: ( (lv_isgroup_3_0= '(' ) )
                    // InternalKactors.g:3401:5: (lv_isgroup_3_0= '(' )
                    {
                    // InternalKactors.g:3401:5: (lv_isgroup_3_0= '(' )
                    // InternalKactors.g:3402:6: lv_isgroup_3_0= '('
                    {
                    lv_isgroup_3_0=(Token)match(input,35,FOLLOW_51); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_isgroup_3_0, grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getBodyRule());
                      						}
                      						setWithLastConsumed(current, "isgroup", true, "(");
                      					
                    }

                    }


                    }

                    // InternalKactors.g:3414:4: ( ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )* )?
                    int alt60=2;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==RULE_LOWERCASE_ID||LA60_0==RULE_EMBEDDEDTEXT||LA60_0==35||LA60_0==63||(LA60_0>=66 && LA60_0<=67)) ) {
                        alt60=1;
                    }
                    switch (alt60) {
                        case 1 :
                            // InternalKactors.g:3415:5: ( (lv_list_4_0= ruleStatement ) ) ( (lv_list_5_0= ruleStatement ) )*
                            {
                            // InternalKactors.g:3415:5: ( (lv_list_4_0= ruleStatement ) )
                            // InternalKactors.g:3416:6: (lv_list_4_0= ruleStatement )
                            {
                            // InternalKactors.g:3416:6: (lv_list_4_0= ruleStatement )
                            // InternalKactors.g:3417:7: lv_list_4_0= ruleStatement
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_1_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_51);
                            lv_list_4_0=ruleStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getBodyRule());
                              							}
                              							add(
                              								current,
                              								"list",
                              								lv_list_4_0,
                              								"org.integratedmodelling.kactors.Kactors.Statement");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            // InternalKactors.g:3434:5: ( (lv_list_5_0= ruleStatement ) )*
                            loop59:
                            do {
                                int alt59=2;
                                int LA59_0 = input.LA(1);

                                if ( (LA59_0==RULE_LOWERCASE_ID||LA59_0==RULE_EMBEDDEDTEXT||LA59_0==35||LA59_0==63||(LA59_0>=66 && LA59_0<=67)) ) {
                                    alt59=1;
                                }


                                switch (alt59) {
                            	case 1 :
                            	    // InternalKactors.g:3435:6: (lv_list_5_0= ruleStatement )
                            	    {
                            	    // InternalKactors.g:3435:6: (lv_list_5_0= ruleStatement )
                            	    // InternalKactors.g:3436:7: lv_list_5_0= ruleStatement
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      							newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_1_1_1_0());
                            	      						
                            	    }
                            	    pushFollow(FOLLOW_51);
                            	    lv_list_5_0=ruleStatement();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							if (current==null) {
                            	      								current = createModelElementForParent(grammarAccess.getBodyRule());
                            	      							}
                            	      							add(
                            	      								current,
                            	      								"list",
                            	      								lv_list_5_0,
                            	      								"org.integratedmodelling.kactors.Kactors.Statement");
                            	      							afterParserOrEnumRuleCall();
                            	      						
                            	    }

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop59;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getBodyAccess().getRightParenthesisKeyword_1_2());
                      			
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
    // $ANTLR end "ruleBody"


    // $ANTLR start "entryRuleStatement"
    // InternalKactors.g:3463:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:3463:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:3464:2: iv_ruleStatement= ruleStatement EOF
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
    // InternalKactors.g:3470:1: ruleStatement returns [EObject current=null] : ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ( (lv_while_7_0= ruleWhileStatement ) ) ) | ( (lv_do_8_0= ruleDoStatement ) ) | ( (lv_for_9_0= ruleForStatement ) ) ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token lv_text_1_0=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        EObject lv_call_0_0 = null;

        EObject lv_if_2_0 = null;

        EObject lv_group_4_0 = null;

        EObject lv_group_5_0 = null;

        EObject lv_while_7_0 = null;

        EObject lv_do_8_0 = null;

        EObject lv_for_9_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3476:2: ( ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ( (lv_while_7_0= ruleWhileStatement ) ) ) | ( (lv_do_8_0= ruleDoStatement ) ) | ( (lv_for_9_0= ruleForStatement ) ) ) )
            // InternalKactors.g:3477:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ( (lv_while_7_0= ruleWhileStatement ) ) ) | ( (lv_do_8_0= ruleDoStatement ) ) | ( (lv_for_9_0= ruleForStatement ) ) )
            {
            // InternalKactors.g:3477:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ( (lv_while_7_0= ruleWhileStatement ) ) ) | ( (lv_do_8_0= ruleDoStatement ) ) | ( (lv_for_9_0= ruleForStatement ) ) )
            int alt63=6;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
                {
                alt63=1;
                }
                break;
            case RULE_EMBEDDEDTEXT:
                {
                alt63=2;
                }
                break;
            case 63:
                {
                alt63=3;
                }
                break;
            case 35:
                {
                alt63=4;
                }
                break;
            case 66:
                {
                alt63=5;
                }
                break;
            case 67:
                {
                alt63=6;
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
                    // InternalKactors.g:3478:3: ( (lv_call_0_0= ruleCall ) )
                    {
                    // InternalKactors.g:3478:3: ( (lv_call_0_0= ruleCall ) )
                    // InternalKactors.g:3479:4: (lv_call_0_0= ruleCall )
                    {
                    // InternalKactors.g:3479:4: (lv_call_0_0= ruleCall )
                    // InternalKactors.g:3480:5: lv_call_0_0= ruleCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getCallCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_call_0_0=ruleCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"call",
                      						lv_call_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Call");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3498:3: ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:3498:3: ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:3499:4: (lv_text_1_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:3499:4: (lv_text_1_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:3500:5: lv_text_1_0= RULE_EMBEDDEDTEXT
                    {
                    lv_text_1_0=(Token)match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_text_1_0, grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getStatementRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"text",
                      						lv_text_1_0,
                      						"org.integratedmodelling.kactors.Kactors.EMBEDDEDTEXT");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:3517:3: ( (lv_if_2_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:3517:3: ( (lv_if_2_0= ruleIfStatement ) )
                    // InternalKactors.g:3518:4: (lv_if_2_0= ruleIfStatement )
                    {
                    // InternalKactors.g:3518:4: (lv_if_2_0= ruleIfStatement )
                    // InternalKactors.g:3519:5: lv_if_2_0= ruleIfStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getIfIfStatementParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_if_2_0=ruleIfStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"if",
                      						lv_if_2_0,
                      						"org.integratedmodelling.kactors.Kactors.IfStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:3537:3: (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ( (lv_while_7_0= ruleWhileStatement ) ) )
                    {
                    // InternalKactors.g:3537:3: (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ( (lv_while_7_0= ruleWhileStatement ) ) )
                    // InternalKactors.g:3538:4: otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ( (lv_while_7_0= ruleWhileStatement ) )
                    {
                    otherlv_3=(Token)match(input,35,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getStatementAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:3542:4: ( (lv_group_4_0= ruleStatement ) )
                    // InternalKactors.g:3543:5: (lv_group_4_0= ruleStatement )
                    {
                    // InternalKactors.g:3543:5: (lv_group_4_0= ruleStatement )
                    // InternalKactors.g:3544:6: lv_group_4_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_51);
                    lv_group_4_0=ruleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						add(
                      							current,
                      							"group",
                      							lv_group_4_0,
                      							"org.integratedmodelling.kactors.Kactors.Statement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:3561:4: ( (lv_group_5_0= ruleStatement ) )*
                    loop62:
                    do {
                        int alt62=2;
                        int LA62_0 = input.LA(1);

                        if ( (LA62_0==RULE_LOWERCASE_ID||LA62_0==RULE_EMBEDDEDTEXT||LA62_0==35||LA62_0==63||(LA62_0>=66 && LA62_0<=67)) ) {
                            alt62=1;
                        }


                        switch (alt62) {
                    	case 1 :
                    	    // InternalKactors.g:3562:5: (lv_group_5_0= ruleStatement )
                    	    {
                    	    // InternalKactors.g:3562:5: (lv_group_5_0= ruleStatement )
                    	    // InternalKactors.g:3563:6: lv_group_5_0= ruleStatement
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_51);
                    	    lv_group_5_0=ruleStatement();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getStatementRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"group",
                    	      							lv_group_5_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Statement");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop62;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,36,FOLLOW_52); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getStatementAccess().getRightParenthesisKeyword_3_3());
                      			
                    }
                    // InternalKactors.g:3584:4: ( (lv_while_7_0= ruleWhileStatement ) )
                    // InternalKactors.g:3585:5: (lv_while_7_0= ruleWhileStatement )
                    {
                    // InternalKactors.g:3585:5: (lv_while_7_0= ruleWhileStatement )
                    // InternalKactors.g:3586:6: lv_while_7_0= ruleWhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getWhileWhileStatementParserRuleCall_3_4_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_while_7_0=ruleWhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"while",
                      							lv_while_7_0,
                      							"org.integratedmodelling.kactors.Kactors.WhileStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:3605:3: ( (lv_do_8_0= ruleDoStatement ) )
                    {
                    // InternalKactors.g:3605:3: ( (lv_do_8_0= ruleDoStatement ) )
                    // InternalKactors.g:3606:4: (lv_do_8_0= ruleDoStatement )
                    {
                    // InternalKactors.g:3606:4: (lv_do_8_0= ruleDoStatement )
                    // InternalKactors.g:3607:5: lv_do_8_0= ruleDoStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getDoDoStatementParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_do_8_0=ruleDoStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"do",
                      						lv_do_8_0,
                      						"org.integratedmodelling.kactors.Kactors.DoStatement");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:3625:3: ( (lv_for_9_0= ruleForStatement ) )
                    {
                    // InternalKactors.g:3625:3: ( (lv_for_9_0= ruleForStatement ) )
                    // InternalKactors.g:3626:4: (lv_for_9_0= ruleForStatement )
                    {
                    // InternalKactors.g:3626:4: (lv_for_9_0= ruleForStatement )
                    // InternalKactors.g:3627:5: lv_for_9_0= ruleForStatement
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getStatementAccess().getForForStatementParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_for_9_0=ruleForStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getStatementRule());
                      					}
                      					set(
                      						current,
                      						"for",
                      						lv_for_9_0,
                      						"org.integratedmodelling.kactors.Kactors.ForStatement");
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


    // $ANTLR start "entryRuleIfStatement"
    // InternalKactors.g:3648:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:3648:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:3649:2: iv_ruleIfStatement= ruleIfStatement EOF
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
    // InternalKactors.g:3655:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? ) ;
    public final EObject ruleIfStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        Token otherlv_7=null;
        EObject lv_body_2_0 = null;

        EObject lv_elseIfCall_6_0 = null;

        EObject lv_elseCall_8_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3661:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? ) )
            // InternalKactors.g:3662:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? )
            {
            // InternalKactors.g:3662:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? )
            // InternalKactors.g:3663:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )?
            {
            otherlv_0=(Token)match(input,63,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:3667:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:3668:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:3668:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:3669:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_54); if (state.failed) return current;
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

            // InternalKactors.g:3685:3: ( (lv_body_2_0= ruleIfBody ) )
            // InternalKactors.g:3686:4: (lv_body_2_0= ruleIfBody )
            {
            // InternalKactors.g:3686:4: (lv_body_2_0= ruleIfBody )
            // InternalKactors.g:3687:5: lv_body_2_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIfStatementAccess().getBodyIfBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_55);
            lv_body_2_0=ruleIfBody();

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
              						"org.integratedmodelling.kactors.Kactors.IfBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalKactors.g:3704:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==64) ) {
                    int LA64_1 = input.LA(2);

                    if ( (synpred124_InternalKactors()) ) {
                        alt64=1;
                    }


                }


                switch (alt64) {
            	case 1 :
            	    // InternalKactors.g:3705:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) )
            	    {
            	    otherlv_3=(Token)match(input,64,FOLLOW_56); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,63,FOLLOW_53); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:3713:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:3714:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:3714:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:3715:6: lv_elseIfExpression_5_0= RULE_EXPR
            	    {
            	    lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_54); if (state.failed) return current;
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

            	    // InternalKactors.g:3731:4: ( (lv_elseIfCall_6_0= ruleIfBody ) )
            	    // InternalKactors.g:3732:5: (lv_elseIfCall_6_0= ruleIfBody )
            	    {
            	    // InternalKactors.g:3732:5: (lv_elseIfCall_6_0= ruleIfBody )
            	    // InternalKactors.g:3733:6: lv_elseIfCall_6_0= ruleIfBody
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfCallIfBodyParserRuleCall_3_3_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_55);
            	    lv_elseIfCall_6_0=ruleIfBody();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getIfStatementRule());
            	      						}
            	      						add(
            	      							current,
            	      							"elseIfCall",
            	      							lv_elseIfCall_6_0,
            	      							"org.integratedmodelling.kactors.Kactors.IfBody");
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

            // InternalKactors.g:3751:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==64) ) {
                int LA65_1 = input.LA(2);

                if ( (synpred125_InternalKactors()) ) {
                    alt65=1;
                }
            }
            switch (alt65) {
                case 1 :
                    // InternalKactors.g:3752:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) )
                    {
                    otherlv_7=(Token)match(input,64,FOLLOW_54); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:3756:4: ( (lv_elseCall_8_0= ruleIfBody ) )
                    // InternalKactors.g:3757:5: (lv_elseCall_8_0= ruleIfBody )
                    {
                    // InternalKactors.g:3757:5: (lv_elseCall_8_0= ruleIfBody )
                    // InternalKactors.g:3758:6: lv_elseCall_8_0= ruleIfBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseCallIfBodyParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_elseCall_8_0=ruleIfBody();

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
                      							"org.integratedmodelling.kactors.Kactors.IfBody");
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


    // $ANTLR start "entryRuleIfBody"
    // InternalKactors.g:3780:1: entryRuleIfBody returns [EObject current=null] : iv_ruleIfBody= ruleIfBody EOF ;
    public final EObject entryRuleIfBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfBody = null;


        try {
            // InternalKactors.g:3780:47: (iv_ruleIfBody= ruleIfBody EOF )
            // InternalKactors.g:3781:2: iv_ruleIfBody= ruleIfBody EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIfBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIfBody=ruleIfBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIfBody; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIfBody"


    // $ANTLR start "ruleIfBody"
    // InternalKactors.g:3787:1: ruleIfBody returns [EObject current=null] : ( ( (lv_call_0_0= ruleCall ) ) | (otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )+ otherlv_3= ')' ) ) ;
    public final EObject ruleIfBody() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_call_0_0 = null;

        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3793:2: ( ( ( (lv_call_0_0= ruleCall ) ) | (otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )+ otherlv_3= ')' ) ) )
            // InternalKactors.g:3794:2: ( ( (lv_call_0_0= ruleCall ) ) | (otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )+ otherlv_3= ')' ) )
            {
            // InternalKactors.g:3794:2: ( ( (lv_call_0_0= ruleCall ) ) | (otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )+ otherlv_3= ')' ) )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==RULE_LOWERCASE_ID) ) {
                alt67=1;
            }
            else if ( (LA67_0==35) ) {
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
                    // InternalKactors.g:3795:3: ( (lv_call_0_0= ruleCall ) )
                    {
                    // InternalKactors.g:3795:3: ( (lv_call_0_0= ruleCall ) )
                    // InternalKactors.g:3796:4: (lv_call_0_0= ruleCall )
                    {
                    // InternalKactors.g:3796:4: (lv_call_0_0= ruleCall )
                    // InternalKactors.g:3797:5: lv_call_0_0= ruleCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getIfBodyAccess().getCallCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_call_0_0=ruleCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getIfBodyRule());
                      					}
                      					set(
                      						current,
                      						"call",
                      						lv_call_0_0,
                      						"org.integratedmodelling.kactors.Kactors.Call");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:3815:3: (otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )+ otherlv_3= ')' )
                    {
                    // InternalKactors.g:3815:3: (otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )+ otherlv_3= ')' )
                    // InternalKactors.g:3816:4: otherlv_1= '(' ( (lv_body_2_0= ruleBody ) )+ otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getIfBodyAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:3820:4: ( (lv_body_2_0= ruleBody ) )+
                    int cnt66=0;
                    loop66:
                    do {
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( (LA66_0==RULE_LOWERCASE_ID||LA66_0==RULE_EMBEDDEDTEXT||LA66_0==35||LA66_0==63||(LA66_0>=66 && LA66_0<=67)) ) {
                            alt66=1;
                        }


                        switch (alt66) {
                    	case 1 :
                    	    // InternalKactors.g:3821:5: (lv_body_2_0= ruleBody )
                    	    {
                    	    // InternalKactors.g:3821:5: (lv_body_2_0= ruleBody )
                    	    // InternalKactors.g:3822:6: lv_body_2_0= ruleBody
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getIfBodyAccess().getBodyBodyParserRuleCall_1_1_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_51);
                    	    lv_body_2_0=ruleBody();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getIfBodyRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"body",
                    	      							lv_body_2_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Body");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt66 >= 1 ) break loop66;
                    	    if (state.backtracking>0) {state.failed=true; return current;}
                                EarlyExitException eee =
                                    new EarlyExitException(66, input);
                                throw eee;
                        }
                        cnt66++;
                    } while (true);

                    otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getIfBodyAccess().getRightParenthesisKeyword_1_2());
                      			
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
    // $ANTLR end "ruleIfBody"


    // $ANTLR start "entryRuleWhileStatement"
    // InternalKactors.g:3848:1: entryRuleWhileStatement returns [EObject current=null] : iv_ruleWhileStatement= ruleWhileStatement EOF ;
    public final EObject entryRuleWhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhileStatement = null;


        try {
            // InternalKactors.g:3848:55: (iv_ruleWhileStatement= ruleWhileStatement EOF )
            // InternalKactors.g:3849:2: iv_ruleWhileStatement= ruleWhileStatement EOF
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
    // InternalKactors.g:3855:1: ruleWhileStatement returns [EObject current=null] : (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) ) ;
    public final EObject ruleWhileStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_expression_1_0=null;
        EObject lv_body_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3861:2: ( (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) ) )
            // InternalKactors.g:3862:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) )
            {
            // InternalKactors.g:3862:2: (otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) )
            // InternalKactors.g:3863:3: otherlv_0= 'while' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) )
            {
            otherlv_0=(Token)match(input,65,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
              		
            }
            // InternalKactors.g:3867:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:3868:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:3868:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:3869:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_54); if (state.failed) return current;
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

            // InternalKactors.g:3885:3: ( (lv_body_2_0= ruleIfBody ) )
            // InternalKactors.g:3886:4: (lv_body_2_0= ruleIfBody )
            {
            // InternalKactors.g:3886:4: (lv_body_2_0= ruleIfBody )
            // InternalKactors.g:3887:5: lv_body_2_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getWhileStatementAccess().getBodyIfBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_2_0=ruleIfBody();

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
              						"org.integratedmodelling.kactors.Kactors.IfBody");
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
    // InternalKactors.g:3908:1: entryRuleDoStatement returns [EObject current=null] : iv_ruleDoStatement= ruleDoStatement EOF ;
    public final EObject entryRuleDoStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoStatement = null;


        try {
            // InternalKactors.g:3908:52: (iv_ruleDoStatement= ruleDoStatement EOF )
            // InternalKactors.g:3909:2: iv_ruleDoStatement= ruleDoStatement EOF
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
    // InternalKactors.g:3915:1: ruleDoStatement returns [EObject current=null] : (otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) ;
    public final EObject ruleDoStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_expression_3_0=null;
        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3921:2: ( (otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) ) )
            // InternalKactors.g:3922:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            {
            // InternalKactors.g:3922:2: (otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) ) )
            // InternalKactors.g:3923:3: otherlv_0= 'do' ( (lv_body_1_0= ruleIfBody ) ) otherlv_2= 'while' ( (lv_expression_3_0= RULE_EXPR ) )
            {
            otherlv_0=(Token)match(input,66,FOLLOW_54); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
              		
            }
            // InternalKactors.g:3927:3: ( (lv_body_1_0= ruleIfBody ) )
            // InternalKactors.g:3928:4: (lv_body_1_0= ruleIfBody )
            {
            // InternalKactors.g:3928:4: (lv_body_1_0= ruleIfBody )
            // InternalKactors.g:3929:5: lv_body_1_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDoStatementAccess().getBodyIfBodyParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_52);
            lv_body_1_0=ruleIfBody();

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
              						"org.integratedmodelling.kactors.Kactors.IfBody");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            otherlv_2=(Token)match(input,65,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
              		
            }
            // InternalKactors.g:3950:3: ( (lv_expression_3_0= RULE_EXPR ) )
            // InternalKactors.g:3951:4: (lv_expression_3_0= RULE_EXPR )
            {
            // InternalKactors.g:3951:4: (lv_expression_3_0= RULE_EXPR )
            // InternalKactors.g:3952:5: lv_expression_3_0= RULE_EXPR
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
    // InternalKactors.g:3972:1: entryRuleForStatement returns [EObject current=null] : iv_ruleForStatement= ruleForStatement EOF ;
    public final EObject entryRuleForStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForStatement = null;


        try {
            // InternalKactors.g:3972:53: (iv_ruleForStatement= ruleForStatement EOF )
            // InternalKactors.g:3973:2: iv_ruleForStatement= ruleForStatement EOF
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
    // InternalKactors.g:3979:1: ruleForStatement returns [EObject current=null] : (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) ) ) ;
    public final EObject ruleForStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_id_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;

        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:3985:2: ( (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) ) ) )
            // InternalKactors.g:3986:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) ) )
            {
            // InternalKactors.g:3986:2: (otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) ) )
            // InternalKactors.g:3987:3: otherlv_0= 'for' ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )? ( (lv_value_3_0= ruleValue ) ) ( (lv_body_4_0= ruleIfBody ) )
            {
            otherlv_0=(Token)match(input,67,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getForStatementAccess().getForKeyword_0());
              		
            }
            // InternalKactors.g:3991:3: ( ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in' )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_LOWERCASE_ID) ) {
                int LA68_1 = input.LA(2);

                if ( (LA68_1==49) ) {
                    alt68=1;
                }
            }
            switch (alt68) {
                case 1 :
                    // InternalKactors.g:3992:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) ) otherlv_2= 'in'
                    {
                    // InternalKactors.g:3992:4: ( (lv_id_1_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:3993:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:3993:5: (lv_id_1_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:3994:6: lv_id_1_0= RULE_LOWERCASE_ID
                    {
                    lv_id_1_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_57); if (state.failed) return current;
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

                    otherlv_2=(Token)match(input,49,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getInKeyword_1_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4015:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:4016:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:4016:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:4017:5: lv_value_3_0= ruleValue
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getValueValueParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_54);
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

            // InternalKactors.g:4034:3: ( (lv_body_4_0= ruleIfBody ) )
            // InternalKactors.g:4035:4: (lv_body_4_0= ruleIfBody )
            {
            // InternalKactors.g:4035:4: (lv_body_4_0= ruleIfBody )
            // InternalKactors.g:4036:5: lv_body_4_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getBodyIfBodyParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_4_0=ruleIfBody();

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
              						"org.integratedmodelling.kactors.Kactors.IfBody");
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


    // $ANTLR start "entryRuleCall"
    // InternalKactors.g:4057:1: entryRuleCall returns [EObject current=null] : iv_ruleCall= ruleCall EOF ;
    public final EObject entryRuleCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCall = null;


        try {
            // InternalKactors.g:4057:45: (iv_ruleCall= ruleCall EOF )
            // InternalKactors.g:4058:2: iv_ruleCall= ruleCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCallRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCall=ruleCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCall; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCall"


    // $ANTLR start "ruleCall"
    // InternalKactors.g:4064:1: ruleCall returns [EObject current=null] : ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? ) ;
    public final EObject ruleCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_parameters_2_0 = null;

        EObject lv_actions_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4070:2: ( ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? ) )
            // InternalKactors.g:4071:2: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? )
            {
            // InternalKactors.g:4071:2: ( ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? )
            // InternalKactors.g:4072:3: ( (lv_name_0_0= rulePathName ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )?
            {
            // InternalKactors.g:4072:3: ( (lv_name_0_0= rulePathName ) )
            // InternalKactors.g:4073:4: (lv_name_0_0= rulePathName )
            {
            // InternalKactors.g:4073:4: (lv_name_0_0= rulePathName )
            // InternalKactors.g:4074:5: lv_name_0_0= rulePathName
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getCallAccess().getNamePathNameParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_58);
            lv_name_0_0=rulePathName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getCallRule());
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

            // InternalKactors.g:4091:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt70=2;
            alt70 = dfa70.predict(input);
            switch (alt70) {
                case 1 :
                    // InternalKactors.g:4092:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_22); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getCallAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:4096:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==RULE_LOWERCASE_ID||(LA69_0>=RULE_STRING && LA69_0<=RULE_EXPR)||LA69_0==RULE_INT||LA69_0==35||LA69_0==39||LA69_0==42||(LA69_0>=44 && LA69_0<=45)||LA69_0==52||(LA69_0>=70 && LA69_0<=71)) ) {
                        alt69=1;
                    }
                    switch (alt69) {
                        case 1 :
                            // InternalKactors.g:4097:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:4097:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:4098:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_23);
                            lv_parameters_2_0=ruleParameterList();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getCallRule());
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

                    otherlv_3=(Token)match(input,36,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getCallAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:4120:3: ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )?
            int alt71=3;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==34) ) {
                alt71=1;
            }
            else if ( (LA71_0==68) ) {
                alt71=2;
            }
            switch (alt71) {
                case 1 :
                    // InternalKactors.g:4121:4: (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) )
                    {
                    // InternalKactors.g:4121:4: (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) )
                    // InternalKactors.g:4122:5: otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) )
                    {
                    otherlv_4=(Token)match(input,34,FOLLOW_60); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getCallAccess().getColonKeyword_2_0_0());
                      				
                    }
                    // InternalKactors.g:4126:5: ( (lv_actions_5_0= ruleActions ) )
                    // InternalKactors.g:4127:6: (lv_actions_5_0= ruleActions )
                    {
                    // InternalKactors.g:4127:6: (lv_actions_5_0= ruleActions )
                    // InternalKactors.g:4128:7: lv_actions_5_0= ruleActions
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getCallAccess().getActionsActionsParserRuleCall_2_0_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_actions_5_0=ruleActions();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getCallRule());
                      							}
                      							set(
                      								current,
                      								"actions",
                      								lv_actions_5_0,
                      								"org.integratedmodelling.kactors.Kactors.Actions");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:4147:4: otherlv_6= ';'
                    {
                    otherlv_6=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getCallAccess().getSemicolonKeyword_2_1());
                      			
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
    // $ANTLR end "ruleCall"


    // $ANTLR start "entryRuleActions"
    // InternalKactors.g:4156:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:4156:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:4157:2: iv_ruleActions= ruleActions EOF
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
    // InternalKactors.g:4163:1: ruleActions returns [EObject current=null] : ( ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* ) | ( (lv_body_3_0= ruleBody ) ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) ) ;
    public final EObject ruleActions() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        EObject lv_sequence_0_0 = null;

        EObject lv_sequence_2_0 = null;

        EObject lv_body_3_0 = null;

        EObject lv_match_4_0 = null;

        EObject lv_matches_6_0 = null;

        EObject lv_matches_7_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4169:2: ( ( ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* ) | ( (lv_body_3_0= ruleBody ) ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) ) )
            // InternalKactors.g:4170:2: ( ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* ) | ( (lv_body_3_0= ruleBody ) ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) )
            {
            // InternalKactors.g:4170:2: ( ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* ) | ( (lv_body_3_0= ruleBody ) ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) )
            int alt74=4;
            alt74 = dfa74.predict(input);
            switch (alt74) {
                case 1 :
                    // InternalKactors.g:4171:3: ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* )
                    {
                    // InternalKactors.g:4171:3: ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* )
                    // InternalKactors.g:4172:4: ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )*
                    {
                    // InternalKactors.g:4172:4: ( (lv_sequence_0_0= ruleStatement ) )
                    // InternalKactors.g:4173:5: (lv_sequence_0_0= ruleStatement )
                    {
                    // InternalKactors.g:4173:5: (lv_sequence_0_0= ruleStatement )
                    // InternalKactors.g:4174:6: lv_sequence_0_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getSequenceStatementParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_18);
                    lv_sequence_0_0=ruleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						add(
                      							current,
                      							"sequence",
                      							lv_sequence_0_0,
                      							"org.integratedmodelling.kactors.Kactors.Statement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4191:4: (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )*
                    loop72:
                    do {
                        int alt72=2;
                        int LA72_0 = input.LA(1);

                        if ( (LA72_0==24) ) {
                            int LA72_2 = input.LA(2);

                            if ( (synpred133_InternalKactors()) ) {
                                alt72=1;
                            }


                        }


                        switch (alt72) {
                    	case 1 :
                    	    // InternalKactors.g:4192:5: otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) )
                    	    {
                    	    otherlv_1=(Token)match(input,24,FOLLOW_14); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getActionsAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:4196:5: ( (lv_sequence_2_0= ruleStatement ) )
                    	    // InternalKactors.g:4197:6: (lv_sequence_2_0= ruleStatement )
                    	    {
                    	    // InternalKactors.g:4197:6: (lv_sequence_2_0= ruleStatement )
                    	    // InternalKactors.g:4198:7: lv_sequence_2_0= ruleStatement
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getActionsAccess().getSequenceStatementParserRuleCall_0_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_18);
                    	    lv_sequence_2_0=ruleStatement();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getActionsRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"sequence",
                    	      								lv_sequence_2_0,
                    	      								"org.integratedmodelling.kactors.Kactors.Statement");
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
                    // InternalKactors.g:4218:3: ( (lv_body_3_0= ruleBody ) )
                    {
                    // InternalKactors.g:4218:3: ( (lv_body_3_0= ruleBody ) )
                    // InternalKactors.g:4219:4: (lv_body_3_0= ruleBody )
                    {
                    // InternalKactors.g:4219:4: (lv_body_3_0= ruleBody )
                    // InternalKactors.g:4220:5: lv_body_3_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_3_0=ruleBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"body",
                      						lv_body_3_0,
                      						"org.integratedmodelling.kactors.Kactors.Body");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:4238:3: ( (lv_match_4_0= ruleMatch ) )
                    {
                    // InternalKactors.g:4238:3: ( (lv_match_4_0= ruleMatch ) )
                    // InternalKactors.g:4239:4: (lv_match_4_0= ruleMatch )
                    {
                    // InternalKactors.g:4239:4: (lv_match_4_0= ruleMatch )
                    // InternalKactors.g:4240:5: lv_match_4_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_match_4_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"match",
                      						lv_match_4_0,
                      						"org.integratedmodelling.kactors.Kactors.Match");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:4258:3: (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' )
                    {
                    // InternalKactors.g:4258:3: (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' )
                    // InternalKactors.g:4259:4: otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')'
                    {
                    otherlv_5=(Token)match(input,35,FOLLOW_61); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:4263:4: ( (lv_matches_6_0= ruleMatch ) )
                    // InternalKactors.g:4264:5: (lv_matches_6_0= ruleMatch )
                    {
                    // InternalKactors.g:4264:5: (lv_matches_6_0= ruleMatch )
                    // InternalKactors.g:4265:6: lv_matches_6_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_62);
                    lv_matches_6_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						add(
                      							current,
                      							"matches",
                      							lv_matches_6_0,
                      							"org.integratedmodelling.kactors.Kactors.Match");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4282:4: ( (lv_matches_7_0= ruleMatch ) )*
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==RULE_LOWERCASE_ID||LA73_0==RULE_STRING||(LA73_0>=RULE_OBSERVABLE && LA73_0<=RULE_EXPR)||LA73_0==RULE_INT||LA73_0==RULE_REGEXP||LA73_0==35||LA73_0==40||(LA73_0>=44 && LA73_0<=45)||(LA73_0>=49 && LA73_0<=51)||(LA73_0>=70 && LA73_0<=71)) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // InternalKactors.g:4283:5: (lv_matches_7_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:4283:5: (lv_matches_7_0= ruleMatch )
                    	    // InternalKactors.g:4284:6: lv_matches_7_0= ruleMatch
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_62);
                    	    lv_matches_7_0=ruleMatch();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActionsRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"matches",
                    	      							lv_matches_7_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Match");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop73;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getActionsAccess().getRightParenthesisKeyword_3_3());
                      			
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
    // InternalKactors.g:4310:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:4310:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:4311:2: iv_ruleMatch= ruleMatch EOF
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
    // InternalKactors.g:4317:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) | ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) ) | (otherlv_27= 'in' ( (lv_set_28_0= ruleList ) ) otherlv_29= '->' ( (lv_body_30_0= ruleBody ) ) ) | ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) ) | ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) ) | ( ( (lv_expr_37_0= RULE_EXPR ) ) otherlv_38= '->' ( (lv_body_39_0= ruleBody ) ) ) | ( ( (lv_nodata_40_0= 'unknown' ) ) otherlv_41= '->' ( (lv_body_42_0= ruleBody ) ) ) | ( ( (lv_star_43_0= '*' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleBody ) ) ) | ( ( (lv_anything_46_0= '#' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleBody ) ) ) ) ;
    public final EObject ruleMatch() throws RecognitionException {
        EObject current = null;

        Token lv_id_0_0=null;
        Token otherlv_1=null;
        Token lv_regexp_3_0=null;
        Token otherlv_4=null;
        Token lv_observable_6_0=null;
        Token otherlv_7=null;
        Token otherlv_10=null;
        Token lv_text_12_0=null;
        Token otherlv_13=null;
        Token otherlv_16=null;
        Token lv_leftLimit_19_0=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token lv_rightLimit_23_0=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_29=null;
        Token otherlv_32=null;
        Token otherlv_35=null;
        Token lv_expr_37_0=null;
        Token otherlv_38=null;
        Token lv_nodata_40_0=null;
        Token otherlv_41=null;
        Token lv_star_43_0=null;
        Token otherlv_44=null;
        Token lv_anything_46_0=null;
        Token otherlv_47=null;
        EObject lv_body_2_0 = null;

        EObject lv_body_5_0 = null;

        EObject lv_body_8_0 = null;

        EObject lv_literal_9_0 = null;

        EObject lv_body_11_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_arguments_15_0 = null;

        EObject lv_body_17_0 = null;

        EObject lv_int0_18_0 = null;

        EObject lv_int1_22_0 = null;

        EObject lv_body_26_0 = null;

        EObject lv_set_28_0 = null;

        EObject lv_body_30_0 = null;

        EObject lv_quantity_31_0 = null;

        EObject lv_body_33_0 = null;

        EObject lv_date_34_0 = null;

        EObject lv_body_36_0 = null;

        EObject lv_body_39_0 = null;

        EObject lv_body_42_0 = null;

        EObject lv_body_45_0 = null;

        EObject lv_body_48_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:4323:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) | ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) ) | (otherlv_27= 'in' ( (lv_set_28_0= ruleList ) ) otherlv_29= '->' ( (lv_body_30_0= ruleBody ) ) ) | ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) ) | ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) ) | ( ( (lv_expr_37_0= RULE_EXPR ) ) otherlv_38= '->' ( (lv_body_39_0= ruleBody ) ) ) | ( ( (lv_nodata_40_0= 'unknown' ) ) otherlv_41= '->' ( (lv_body_42_0= ruleBody ) ) ) | ( ( (lv_star_43_0= '*' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleBody ) ) ) | ( ( (lv_anything_46_0= '#' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleBody ) ) ) ) )
            // InternalKactors.g:4324:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) | ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) ) | (otherlv_27= 'in' ( (lv_set_28_0= ruleList ) ) otherlv_29= '->' ( (lv_body_30_0= ruleBody ) ) ) | ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) ) | ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) ) | ( ( (lv_expr_37_0= RULE_EXPR ) ) otherlv_38= '->' ( (lv_body_39_0= ruleBody ) ) ) | ( ( (lv_nodata_40_0= 'unknown' ) ) otherlv_41= '->' ( (lv_body_42_0= ruleBody ) ) ) | ( ( (lv_star_43_0= '*' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleBody ) ) ) | ( ( (lv_anything_46_0= '#' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleBody ) ) ) )
            {
            // InternalKactors.g:4324:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) | ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) ) | (otherlv_27= 'in' ( (lv_set_28_0= ruleList ) ) otherlv_29= '->' ( (lv_body_30_0= ruleBody ) ) ) | ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) ) | ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) ) | ( ( (lv_expr_37_0= RULE_EXPR ) ) otherlv_38= '->' ( (lv_body_39_0= ruleBody ) ) ) | ( ( (lv_nodata_40_0= 'unknown' ) ) otherlv_41= '->' ( (lv_body_42_0= ruleBody ) ) ) | ( ( (lv_star_43_0= '*' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleBody ) ) ) | ( ( (lv_anything_46_0= '#' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleBody ) ) ) )
            int alt77=14;
            alt77 = dfa77.predict(input);
            switch (alt77) {
                case 1 :
                    // InternalKactors.g:4325:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4325:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) )
                    // InternalKactors.g:4326:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) )
                    {
                    // InternalKactors.g:4326:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:4327:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:4327:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:4328:6: lv_id_0_0= RULE_LOWERCASE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_63); if (state.failed) return current;
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

                    otherlv_1=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:4348:4: ( (lv_body_2_0= ruleBody ) )
                    // InternalKactors.g:4349:5: (lv_body_2_0= ruleBody )
                    {
                    // InternalKactors.g:4349:5: (lv_body_2_0= ruleBody )
                    // InternalKactors.g:4350:6: lv_body_2_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_2_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:4369:3: ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4369:3: ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) )
                    // InternalKactors.g:4370:4: ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) )
                    {
                    // InternalKactors.g:4370:4: ( (lv_regexp_3_0= RULE_REGEXP ) )
                    // InternalKactors.g:4371:5: (lv_regexp_3_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:4371:5: (lv_regexp_3_0= RULE_REGEXP )
                    // InternalKactors.g:4372:6: lv_regexp_3_0= RULE_REGEXP
                    {
                    lv_regexp_3_0=(Token)match(input,RULE_REGEXP,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_regexp_3_0, grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_1_0_0());
                      					
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

                    otherlv_4=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:4392:4: ( (lv_body_5_0= ruleBody ) )
                    // InternalKactors.g:4393:5: (lv_body_5_0= ruleBody )
                    {
                    // InternalKactors.g:4393:5: (lv_body_5_0= ruleBody )
                    // InternalKactors.g:4394:6: lv_body_5_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_5_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:4413:3: ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4413:3: ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) )
                    // InternalKactors.g:4414:4: ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) )
                    {
                    // InternalKactors.g:4414:4: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:4415:5: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:4415:5: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:4416:6: lv_observable_6_0= RULE_OBSERVABLE
                    {
                    lv_observable_6_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_observable_6_0, grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_2_0_0());
                      					
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

                    otherlv_7=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:4436:4: ( (lv_body_8_0= ruleBody ) )
                    // InternalKactors.g:4437:5: (lv_body_8_0= ruleBody )
                    {
                    // InternalKactors.g:4437:5: (lv_body_8_0= ruleBody )
                    // InternalKactors.g:4438:6: lv_body_8_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_2_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_8_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:4457:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4457:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
                    // InternalKactors.g:4458:4: ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) )
                    {
                    // InternalKactors.g:4458:4: ( (lv_literal_9_0= ruleLiteral ) )
                    // InternalKactors.g:4459:5: (lv_literal_9_0= ruleLiteral )
                    {
                    // InternalKactors.g:4459:5: (lv_literal_9_0= ruleLiteral )
                    // InternalKactors.g:4460:6: lv_literal_9_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_literal_9_0=ruleLiteral();

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

                    otherlv_10=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:4481:4: ( (lv_body_11_0= ruleBody ) )
                    // InternalKactors.g:4482:5: (lv_body_11_0= ruleBody )
                    {
                    // InternalKactors.g:4482:5: (lv_body_11_0= ruleBody )
                    // InternalKactors.g:4483:6: lv_body_11_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_3_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_11_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:4502:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4502:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
                    // InternalKactors.g:4503:4: ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) )
                    {
                    // InternalKactors.g:4503:4: ( (lv_text_12_0= RULE_STRING ) )
                    // InternalKactors.g:4504:5: (lv_text_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:4504:5: (lv_text_12_0= RULE_STRING )
                    // InternalKactors.g:4505:6: lv_text_12_0= RULE_STRING
                    {
                    lv_text_12_0=(Token)match(input,RULE_STRING,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_text_12_0, grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_4_0_0());
                      					
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

                    otherlv_13=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:4525:4: ( (lv_body_14_0= ruleBody ) )
                    // InternalKactors.g:4526:5: (lv_body_14_0= ruleBody )
                    {
                    // InternalKactors.g:4526:5: (lv_body_14_0= ruleBody )
                    // InternalKactors.g:4527:6: lv_body_14_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_4_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_14_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalKactors.g:4546:3: ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4546:3: ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) )
                    // InternalKactors.g:4547:4: ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) )
                    {
                    // InternalKactors.g:4547:4: ( (lv_arguments_15_0= ruleArgumentDeclaration ) )
                    // InternalKactors.g:4548:5: (lv_arguments_15_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:4548:5: (lv_arguments_15_0= ruleArgumentDeclaration )
                    // InternalKactors.g:4549:6: lv_arguments_15_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_arguments_15_0=ruleArgumentDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"arguments",
                      							lv_arguments_15_0,
                      							"org.integratedmodelling.kactors.Kactors.ArgumentDeclaration");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_16=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:4570:4: ( (lv_body_17_0= ruleBody ) )
                    // InternalKactors.g:4571:5: (lv_body_17_0= ruleBody )
                    {
                    // InternalKactors.g:4571:5: (lv_body_17_0= ruleBody )
                    // InternalKactors.g:4572:6: lv_body_17_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_5_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_17_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalKactors.g:4591:3: ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4591:3: ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) )
                    // InternalKactors.g:4592:4: ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) )
                    {
                    // InternalKactors.g:4592:4: ( (lv_int0_18_0= ruleNumber ) )
                    // InternalKactors.g:4593:5: (lv_int0_18_0= ruleNumber )
                    {
                    // InternalKactors.g:4593:5: (lv_int0_18_0= ruleNumber )
                    // InternalKactors.g:4594:6: lv_int0_18_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getInt0NumberParserRuleCall_6_0_0());
                      					
                    }
                    pushFollow(FOLLOW_33);
                    lv_int0_18_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"int0",
                      							lv_int0_18_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4611:4: ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )?
                    int alt75=3;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==46) ) {
                        alt75=1;
                    }
                    else if ( (LA75_0==47) ) {
                        alt75=2;
                    }
                    switch (alt75) {
                        case 1 :
                            // InternalKactors.g:4612:5: ( (lv_leftLimit_19_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4612:5: ( (lv_leftLimit_19_0= 'inclusive' ) )
                            // InternalKactors.g:4613:6: (lv_leftLimit_19_0= 'inclusive' )
                            {
                            // InternalKactors.g:4613:6: (lv_leftLimit_19_0= 'inclusive' )
                            // InternalKactors.g:4614:7: lv_leftLimit_19_0= 'inclusive'
                            {
                            lv_leftLimit_19_0=(Token)match(input,46,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_leftLimit_19_0, grammarAccess.getMatchAccess().getLeftLimitInclusiveKeyword_6_1_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "leftLimit", lv_leftLimit_19_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4627:5: otherlv_20= 'exclusive'
                            {
                            otherlv_20=(Token)match(input,47,FOLLOW_34); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_20, grammarAccess.getMatchAccess().getExclusiveKeyword_6_1_1());
                              				
                            }

                            }
                            break;

                    }

                    // InternalKactors.g:4632:4: ( ( 'to' )=>otherlv_21= 'to' )
                    // InternalKactors.g:4633:5: ( 'to' )=>otherlv_21= 'to'
                    {
                    otherlv_21=(Token)match(input,48,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_21, grammarAccess.getMatchAccess().getToKeyword_6_2());
                      				
                    }

                    }

                    // InternalKactors.g:4639:4: ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) )
                    // InternalKactors.g:4640:5: ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber )
                    {
                    // InternalKactors.g:4644:5: (lv_int1_22_0= ruleNumber )
                    // InternalKactors.g:4645:6: lv_int1_22_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getInt1NumberParserRuleCall_6_3_0());
                      					
                    }
                    pushFollow(FOLLOW_64);
                    lv_int1_22_0=ruleNumber();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"int1",
                      							lv_int1_22_0,
                      							"org.integratedmodelling.kactors.Kactors.Number");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:4662:4: ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )?
                    int alt76=3;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==46) ) {
                        alt76=1;
                    }
                    else if ( (LA76_0==47) ) {
                        alt76=2;
                    }
                    switch (alt76) {
                        case 1 :
                            // InternalKactors.g:4663:5: ( (lv_rightLimit_23_0= 'inclusive' ) )
                            {
                            // InternalKactors.g:4663:5: ( (lv_rightLimit_23_0= 'inclusive' ) )
                            // InternalKactors.g:4664:6: (lv_rightLimit_23_0= 'inclusive' )
                            {
                            // InternalKactors.g:4664:6: (lv_rightLimit_23_0= 'inclusive' )
                            // InternalKactors.g:4665:7: lv_rightLimit_23_0= 'inclusive'
                            {
                            lv_rightLimit_23_0=(Token)match(input,46,FOLLOW_63); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							newLeafNode(lv_rightLimit_23_0, grammarAccess.getMatchAccess().getRightLimitInclusiveKeyword_6_4_0_0());
                              						
                            }
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElement(grammarAccess.getMatchRule());
                              							}
                              							setWithLastConsumed(current, "rightLimit", lv_rightLimit_23_0, "inclusive");
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalKactors.g:4678:5: otherlv_24= 'exclusive'
                            {
                            otherlv_24=(Token)match(input,47,FOLLOW_63); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_24, grammarAccess.getMatchAccess().getExclusiveKeyword_6_4_1());
                              				
                            }

                            }
                            break;

                    }

                    otherlv_25=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_25, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_6_5());
                      			
                    }
                    // InternalKactors.g:4687:4: ( (lv_body_26_0= ruleBody ) )
                    // InternalKactors.g:4688:5: (lv_body_26_0= ruleBody )
                    {
                    // InternalKactors.g:4688:5: (lv_body_26_0= ruleBody )
                    // InternalKactors.g:4689:6: lv_body_26_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_6_6_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_26_0=ruleBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"body",
                      							lv_body_26_0,
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalKactors.g:4708:3: (otherlv_27= 'in' ( (lv_set_28_0= ruleList ) ) otherlv_29= '->' ( (lv_body_30_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4708:3: (otherlv_27= 'in' ( (lv_set_28_0= ruleList ) ) otherlv_29= '->' ( (lv_body_30_0= ruleBody ) ) )
                    // InternalKactors.g:4709:4: otherlv_27= 'in' ( (lv_set_28_0= ruleList ) ) otherlv_29= '->' ( (lv_body_30_0= ruleBody ) )
                    {
                    otherlv_27=(Token)match(input,49,FOLLOW_37); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_27, grammarAccess.getMatchAccess().getInKeyword_7_0());
                      			
                    }
                    // InternalKactors.g:4713:4: ( (lv_set_28_0= ruleList ) )
                    // InternalKactors.g:4714:5: (lv_set_28_0= ruleList )
                    {
                    // InternalKactors.g:4714:5: (lv_set_28_0= ruleList )
                    // InternalKactors.g:4715:6: lv_set_28_0= ruleList
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getSetListParserRuleCall_7_1_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_set_28_0=ruleList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"set",
                      							lv_set_28_0,
                      							"org.integratedmodelling.kactors.Kactors.List");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_29=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_29, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_7_2());
                      			
                    }
                    // InternalKactors.g:4736:4: ( (lv_body_30_0= ruleBody ) )
                    // InternalKactors.g:4737:5: (lv_body_30_0= ruleBody )
                    {
                    // InternalKactors.g:4737:5: (lv_body_30_0= ruleBody )
                    // InternalKactors.g:4738:6: lv_body_30_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_7_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_30_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalKactors.g:4757:3: ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4757:3: ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) )
                    // InternalKactors.g:4758:4: ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) )
                    {
                    // InternalKactors.g:4758:4: ( (lv_quantity_31_0= ruleQuantity ) )
                    // InternalKactors.g:4759:5: (lv_quantity_31_0= ruleQuantity )
                    {
                    // InternalKactors.g:4759:5: (lv_quantity_31_0= ruleQuantity )
                    // InternalKactors.g:4760:6: lv_quantity_31_0= ruleQuantity
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_8_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_quantity_31_0=ruleQuantity();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"quantity",
                      							lv_quantity_31_0,
                      							"org.integratedmodelling.kactors.Kactors.Quantity");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_32=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_32, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_8_1());
                      			
                    }
                    // InternalKactors.g:4781:4: ( (lv_body_33_0= ruleBody ) )
                    // InternalKactors.g:4782:5: (lv_body_33_0= ruleBody )
                    {
                    // InternalKactors.g:4782:5: (lv_body_33_0= ruleBody )
                    // InternalKactors.g:4783:6: lv_body_33_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_8_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_33_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalKactors.g:4802:3: ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4802:3: ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) )
                    // InternalKactors.g:4803:4: ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) )
                    {
                    // InternalKactors.g:4803:4: ( (lv_date_34_0= ruleDate ) )
                    // InternalKactors.g:4804:5: (lv_date_34_0= ruleDate )
                    {
                    // InternalKactors.g:4804:5: (lv_date_34_0= ruleDate )
                    // InternalKactors.g:4805:6: lv_date_34_0= ruleDate
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getDateDateParserRuleCall_9_0_0());
                      					
                    }
                    pushFollow(FOLLOW_63);
                    lv_date_34_0=ruleDate();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getMatchRule());
                      						}
                      						set(
                      							current,
                      							"date",
                      							lv_date_34_0,
                      							"org.integratedmodelling.kactors.Kactors.Date");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_35=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_35, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_9_1());
                      			
                    }
                    // InternalKactors.g:4826:4: ( (lv_body_36_0= ruleBody ) )
                    // InternalKactors.g:4827:5: (lv_body_36_0= ruleBody )
                    {
                    // InternalKactors.g:4827:5: (lv_body_36_0= ruleBody )
                    // InternalKactors.g:4828:6: lv_body_36_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_9_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_36_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalKactors.g:4847:3: ( ( (lv_expr_37_0= RULE_EXPR ) ) otherlv_38= '->' ( (lv_body_39_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4847:3: ( ( (lv_expr_37_0= RULE_EXPR ) ) otherlv_38= '->' ( (lv_body_39_0= ruleBody ) ) )
                    // InternalKactors.g:4848:4: ( (lv_expr_37_0= RULE_EXPR ) ) otherlv_38= '->' ( (lv_body_39_0= ruleBody ) )
                    {
                    // InternalKactors.g:4848:4: ( (lv_expr_37_0= RULE_EXPR ) )
                    // InternalKactors.g:4849:5: (lv_expr_37_0= RULE_EXPR )
                    {
                    // InternalKactors.g:4849:5: (lv_expr_37_0= RULE_EXPR )
                    // InternalKactors.g:4850:6: lv_expr_37_0= RULE_EXPR
                    {
                    lv_expr_37_0=(Token)match(input,RULE_EXPR,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_expr_37_0, grammarAccess.getMatchAccess().getExprEXPRTerminalRuleCall_10_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"expr",
                      							lv_expr_37_0,
                      							"org.integratedmodelling.kactors.Kactors.EXPR");
                      					
                    }

                    }


                    }

                    otherlv_38=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_38, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_10_1());
                      			
                    }
                    // InternalKactors.g:4870:4: ( (lv_body_39_0= ruleBody ) )
                    // InternalKactors.g:4871:5: (lv_body_39_0= ruleBody )
                    {
                    // InternalKactors.g:4871:5: (lv_body_39_0= ruleBody )
                    // InternalKactors.g:4872:6: lv_body_39_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_10_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_39_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 12 :
                    // InternalKactors.g:4891:3: ( ( (lv_nodata_40_0= 'unknown' ) ) otherlv_41= '->' ( (lv_body_42_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4891:3: ( ( (lv_nodata_40_0= 'unknown' ) ) otherlv_41= '->' ( (lv_body_42_0= ruleBody ) ) )
                    // InternalKactors.g:4892:4: ( (lv_nodata_40_0= 'unknown' ) ) otherlv_41= '->' ( (lv_body_42_0= ruleBody ) )
                    {
                    // InternalKactors.g:4892:4: ( (lv_nodata_40_0= 'unknown' ) )
                    // InternalKactors.g:4893:5: (lv_nodata_40_0= 'unknown' )
                    {
                    // InternalKactors.g:4893:5: (lv_nodata_40_0= 'unknown' )
                    // InternalKactors.g:4894:6: lv_nodata_40_0= 'unknown'
                    {
                    lv_nodata_40_0=(Token)match(input,50,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_nodata_40_0, grammarAccess.getMatchAccess().getNodataUnknownKeyword_11_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "nodata", lv_nodata_40_0, "unknown");
                      					
                    }

                    }


                    }

                    otherlv_41=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_41, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_11_1());
                      			
                    }
                    // InternalKactors.g:4910:4: ( (lv_body_42_0= ruleBody ) )
                    // InternalKactors.g:4911:5: (lv_body_42_0= ruleBody )
                    {
                    // InternalKactors.g:4911:5: (lv_body_42_0= ruleBody )
                    // InternalKactors.g:4912:6: lv_body_42_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_11_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_42_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 13 :
                    // InternalKactors.g:4931:3: ( ( (lv_star_43_0= '*' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4931:3: ( ( (lv_star_43_0= '*' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleBody ) ) )
                    // InternalKactors.g:4932:4: ( (lv_star_43_0= '*' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleBody ) )
                    {
                    // InternalKactors.g:4932:4: ( (lv_star_43_0= '*' ) )
                    // InternalKactors.g:4933:5: (lv_star_43_0= '*' )
                    {
                    // InternalKactors.g:4933:5: (lv_star_43_0= '*' )
                    // InternalKactors.g:4934:6: lv_star_43_0= '*'
                    {
                    lv_star_43_0=(Token)match(input,51,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_star_43_0, grammarAccess.getMatchAccess().getStarAsteriskKeyword_12_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "star", true, "*");
                      					
                    }

                    }


                    }

                    otherlv_44=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_44, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_12_1());
                      			
                    }
                    // InternalKactors.g:4950:4: ( (lv_body_45_0= ruleBody ) )
                    // InternalKactors.g:4951:5: (lv_body_45_0= ruleBody )
                    {
                    // InternalKactors.g:4951:5: (lv_body_45_0= ruleBody )
                    // InternalKactors.g:4952:6: lv_body_45_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_12_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_45_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 14 :
                    // InternalKactors.g:4971:3: ( ( (lv_anything_46_0= '#' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:4971:3: ( ( (lv_anything_46_0= '#' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleBody ) ) )
                    // InternalKactors.g:4972:4: ( (lv_anything_46_0= '#' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleBody ) )
                    {
                    // InternalKactors.g:4972:4: ( (lv_anything_46_0= '#' ) )
                    // InternalKactors.g:4973:5: (lv_anything_46_0= '#' )
                    {
                    // InternalKactors.g:4973:5: (lv_anything_46_0= '#' )
                    // InternalKactors.g:4974:6: lv_anything_46_0= '#'
                    {
                    lv_anything_46_0=(Token)match(input,40,FOLLOW_63); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_anything_46_0, grammarAccess.getMatchAccess().getAnythingNumberSignKeyword_13_0_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getMatchRule());
                      						}
                      						setWithLastConsumed(current, "anything", true, "#");
                      					
                    }

                    }


                    }

                    otherlv_47=(Token)match(input,69,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_47, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_13_1());
                      			
                    }
                    // InternalKactors.g:4990:4: ( (lv_body_48_0= ruleBody ) )
                    // InternalKactors.g:4991:5: (lv_body_48_0= ruleBody )
                    {
                    // InternalKactors.g:4991:5: (lv_body_48_0= ruleBody )
                    // InternalKactors.g:4992:6: lv_body_48_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_13_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_48_0=ruleBody();

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
                      							"org.integratedmodelling.kactors.Kactors.Body");
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


    // $ANTLR start "entryRuleNumber"
    // InternalKactors.g:5014:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:5014:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:5015:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKactors.g:5021:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKactors.g:5027:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:5028:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:5028:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:5029:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:5029:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt78=3;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==70) ) {
                alt78=1;
            }
            else if ( (LA78_0==71) ) {
                alt78=2;
            }
            switch (alt78) {
                case 1 :
                    // InternalKactors.g:5030:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,70,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5035:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:5035:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:5036:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:5036:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:5037:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,71,FOLLOW_9); if (state.failed) return current;
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

            // InternalKactors.g:5050:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:5051:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:5055:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:5056:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_65); if (state.failed) return current;
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

            // InternalKactors.g:5072:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==72) && (synpred160_InternalKactors())) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalKactors.g:5073:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:5077:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:5078:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,72,FOLLOW_66); if (state.failed) return current;
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

            // InternalKactors.g:5090:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==56) ) {
                int LA80_1 = input.LA(2);

                if ( (LA80_1==RULE_INT) && (synpred161_InternalKactors())) {
                    alt80=1;
                }
            }
            switch (alt80) {
                case 1 :
                    // InternalKactors.g:5091:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5104:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:5105:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5105:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:5106:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:5106:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:5107:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,56,FOLLOW_9); if (state.failed) return current;
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

                    // InternalKactors.g:5119:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:5120:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:5120:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:5121:7: lv_decimalPart_5_0= RULE_INT
                    {
                    lv_decimalPart_5_0=(Token)match(input,RULE_INT,FOLLOW_67); if (state.failed) return current;
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

            // InternalKactors.g:5139:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==73) && (synpred165_InternalKactors())) {
                alt83=1;
            }
            else if ( (LA83_0==74) && (synpred165_InternalKactors())) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalKactors.g:5140:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:5166:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:5167:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:5167:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:5168:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:5168:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:5169:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:5169:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==73) ) {
                        alt81=1;
                    }
                    else if ( (LA81_0==74) ) {
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
                            // InternalKactors.g:5170:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,73,FOLLOW_35); if (state.failed) return current;
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
                            // InternalKactors.g:5181:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,74,FOLLOW_35); if (state.failed) return current;
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

                    // InternalKactors.g:5194:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt82=3;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==70) ) {
                        alt82=1;
                    }
                    else if ( (LA82_0==71) ) {
                        alt82=2;
                    }
                    switch (alt82) {
                        case 1 :
                            // InternalKactors.g:5195:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,70,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:5200:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:5200:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:5201:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:5201:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:5202:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,71,FOLLOW_9); if (state.failed) return current;
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

                    // InternalKactors.g:5215:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:5216:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:5216:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:5217:7: lv_exp_9_0= RULE_INT
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
    // InternalKactors.g:5239:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:5239:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:5240:2: iv_ruleDate= ruleDate EOF
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
    // InternalKactors.g:5246:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
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
            // InternalKactors.g:5252:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:5253:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:5253:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:5254:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:5254:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:5255:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:5255:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:5256:5: lv_year_0_0= RULE_INT
            {
            lv_year_0_0=(Token)match(input,RULE_INT,FOLLOW_68); if (state.failed) return current;
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

            // InternalKactors.g:5272:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt84=4;
            switch ( input.LA(1) ) {
                case 75:
                    {
                    alt84=1;
                    }
                    break;
                case 76:
                    {
                    alt84=2;
                    }
                    break;
                case 77:
                    {
                    alt84=3;
                    }
                    break;
            }

            switch (alt84) {
                case 1 :
                    // InternalKactors.g:5273:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,75,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5278:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,76,FOLLOW_69); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:5283:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:5283:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:5284:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:5284:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:5285:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,77,FOLLOW_69); if (state.failed) return current;
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

            otherlv_4=(Token)match(input,71,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:5302:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:5303:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:5303:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:5304:5: lv_month_5_0= RULE_INT
            {
            lv_month_5_0=(Token)match(input,RULE_INT,FOLLOW_69); if (state.failed) return current;
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

            otherlv_6=(Token)match(input,71,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:5324:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:5325:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:5325:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:5326:5: lv_day_7_0= RULE_INT
            {
            lv_day_7_0=(Token)match(input,RULE_INT,FOLLOW_70); if (state.failed) return current;
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

            // InternalKactors.g:5342:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==RULE_INT) ) {
                int LA87_1 = input.LA(2);

                if ( (LA87_1==34) ) {
                    alt87=1;
                }
            }
            switch (alt87) {
                case 1 :
                    // InternalKactors.g:5343:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:5343:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:5344:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:5344:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:5345:6: lv_hour_8_0= RULE_INT
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

                    otherlv_9=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getDateAccess().getColonKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:5365:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:5366:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:5366:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:5367:6: lv_min_10_0= RULE_INT
                    {
                    lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_71); if (state.failed) return current;
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

                    // InternalKactors.g:5383:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==34) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // InternalKactors.g:5384:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,34,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:5388:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:5389:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:5389:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:5390:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_72); if (state.failed) return current;
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

                            // InternalKactors.g:5406:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt85=2;
                            int LA85_0 = input.LA(1);

                            if ( (LA85_0==56) ) {
                                alt85=1;
                            }
                            switch (alt85) {
                                case 1 :
                                    // InternalKactors.g:5407:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,56,FOLLOW_9); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:5411:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:5412:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:5412:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:5413:8: lv_ms_14_0= RULE_INT
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
    // InternalKactors.g:5436:1: entryRulePathName returns [String current=null] : iv_rulePathName= rulePathName EOF ;
    public final String entryRulePathName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePathName = null;


        try {
            // InternalKactors.g:5436:48: (iv_rulePathName= rulePathName EOF )
            // InternalKactors.g:5437:2: iv_rulePathName= rulePathName EOF
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
    // InternalKactors.g:5443:1: rulePathName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) ;
    public final AntlrDatatypeRuleToken rulePathName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token kw=null;
        Token this_LOWERCASE_ID_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5449:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* ) )
            // InternalKactors.g:5450:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            {
            // InternalKactors.g:5450:2: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )* )
            // InternalKactors.g:5451:3: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            {
            this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LOWERCASE_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathNameAccess().getLOWERCASE_IDTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5458:3: (kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==56) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalKactors.g:5459:4: kw= '.' this_LOWERCASE_ID_2= RULE_LOWERCASE_ID
            	    {
            	    kw=(Token)match(input,56,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				current.merge(kw);
            	      				newLeafNode(kw, grammarAccess.getPathNameAccess().getFullStopKeyword_1_0());
            	      			
            	    }
            	    this_LOWERCASE_ID_2=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_72); if (state.failed) return current;
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
    // InternalKactors.g:5476:1: entryRulePath returns [String current=null] : iv_rulePath= rulePath EOF ;
    public final String entryRulePath() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulePath = null;


        try {
            // InternalKactors.g:5476:44: (iv_rulePath= rulePath EOF )
            // InternalKactors.g:5477:2: iv_rulePath= rulePath EOF
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
    // InternalKactors.g:5483:1: rulePath returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) ;
    public final AntlrDatatypeRuleToken rulePath() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LOWERCASE_ID_0=null;
        Token this_UPPERCASE_ID_1=null;
        Token kw=null;
        Token this_LOWERCASE_ID_4=null;
        Token this_UPPERCASE_ID_5=null;


        	enterRule();

        try {
            // InternalKactors.g:5489:2: ( ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* ) )
            // InternalKactors.g:5490:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            {
            // InternalKactors.g:5490:2: ( (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )* )
            // InternalKactors.g:5491:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID ) ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            {
            // InternalKactors.g:5491:3: (this_LOWERCASE_ID_0= RULE_LOWERCASE_ID | this_UPPERCASE_ID_1= RULE_UPPERCASE_ID )
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
                    // InternalKactors.g:5492:4: this_LOWERCASE_ID_0= RULE_LOWERCASE_ID
                    {
                    this_LOWERCASE_ID_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_LOWERCASE_ID_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOWERCASE_ID_0, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5500:4: this_UPPERCASE_ID_1= RULE_UPPERCASE_ID
                    {
                    this_UPPERCASE_ID_1=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_73); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_UPPERCASE_ID_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UPPERCASE_ID_1, grammarAccess.getPathAccess().getUPPERCASE_IDTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5508:3: ( (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID ) )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( ((LA92_0>=55 && LA92_0<=56)) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // InternalKactors.g:5509:4: (kw= '.' | kw= '/' ) (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
            	    {
            	    // InternalKactors.g:5509:4: (kw= '.' | kw= '/' )
            	    int alt90=2;
            	    int LA90_0 = input.LA(1);

            	    if ( (LA90_0==56) ) {
            	        alt90=1;
            	    }
            	    else if ( (LA90_0==55) ) {
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
            	            // InternalKactors.g:5510:5: kw= '.'
            	            {
            	            kw=(Token)match(input,56,FOLLOW_27); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getFullStopKeyword_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5516:5: kw= '/'
            	            {
            	            kw=(Token)match(input,55,FOLLOW_27); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(kw);
            	              					newLeafNode(kw, grammarAccess.getPathAccess().getSolidusKeyword_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalKactors.g:5522:4: (this_LOWERCASE_ID_4= RULE_LOWERCASE_ID | this_UPPERCASE_ID_5= RULE_UPPERCASE_ID )
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
            	            // InternalKactors.g:5523:5: this_LOWERCASE_ID_4= RULE_LOWERCASE_ID
            	            {
            	            this_LOWERCASE_ID_4=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_73); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					current.merge(this_LOWERCASE_ID_4);
            	              				
            	            }
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LOWERCASE_ID_4, grammarAccess.getPathAccess().getLOWERCASE_IDTerminalRuleCall_1_1_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalKactors.g:5531:5: this_UPPERCASE_ID_5= RULE_UPPERCASE_ID
            	            {
            	            this_UPPERCASE_ID_5=(Token)match(input,RULE_UPPERCASE_ID,FOLLOW_73); if (state.failed) return current;
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
    // InternalKactors.g:5544:1: entryRuleVersionNumber returns [String current=null] : iv_ruleVersionNumber= ruleVersionNumber EOF ;
    public final String entryRuleVersionNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionNumber = null;


        try {
            // InternalKactors.g:5544:53: (iv_ruleVersionNumber= ruleVersionNumber EOF )
            // InternalKactors.g:5545:2: iv_ruleVersionNumber= ruleVersionNumber EOF
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
    // InternalKactors.g:5551:1: ruleVersionNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) ;
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
            // InternalKactors.g:5557:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? ) )
            // InternalKactors.g:5558:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            {
            // InternalKactors.g:5558:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )? )
            // InternalKactors.g:5559:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )? (kw= '-' )? (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_74); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INT_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INT_0, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_0());
              		
            }
            // InternalKactors.g:5566:3: (kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==56) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalKactors.g:5567:4: kw= '.' this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )?
                    {
                    kw=(Token)match(input,56,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
                      			
                    }
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_74); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_INT_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INT_2, grammarAccess.getVersionNumberAccess().getINTTerminalRuleCall_1_1());
                      			
                    }
                    // InternalKactors.g:5579:4: (kw= '.' this_INT_4= RULE_INT )?
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==56) ) {
                        alt93=1;
                    }
                    switch (alt93) {
                        case 1 :
                            // InternalKactors.g:5580:5: kw= '.' this_INT_4= RULE_INT
                            {
                            kw=(Token)match(input,56,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current.merge(kw);
                              					newLeafNode(kw, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
                              				
                            }
                            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_75); if (state.failed) return current;
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

            // InternalKactors.g:5594:3: (kw= '-' )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==71) ) {
                int LA95_1 = input.LA(2);

                if ( (synpred182_InternalKactors()) ) {
                    alt95=1;
                }
            }
            switch (alt95) {
                case 1 :
                    // InternalKactors.g:5595:4: kw= '-'
                    {
                    kw=(Token)match(input,71,FOLLOW_76); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.getVersionNumberAccess().getHyphenMinusKeyword_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:5601:3: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID | this_UPPERCASE_ID_7= RULE_UPPERCASE_ID )?
            int alt96=3;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==RULE_LOWERCASE_ID) ) {
                int LA96_1 = input.LA(2);

                if ( (synpred183_InternalKactors()) ) {
                    alt96=1;
                }
            }
            else if ( (LA96_0==RULE_UPPERCASE_ID) ) {
                alt96=2;
            }
            switch (alt96) {
                case 1 :
                    // InternalKactors.g:5602:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:5610:4: this_UPPERCASE_ID_7= RULE_UPPERCASE_ID
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
    // InternalKactors.g:5622:1: ruleUnitOp returns [Enumerator current=null] : ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) ;
    public final Enumerator ruleUnitOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalKactors.g:5628:2: ( ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) ) )
            // InternalKactors.g:5629:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            {
            // InternalKactors.g:5629:2: ( (enumLiteral_0= '/' ) | (enumLiteral_1= '^' ) | (enumLiteral_2= '*' ) )
            int alt97=3;
            switch ( input.LA(1) ) {
            case 55:
                {
                alt97=1;
                }
                break;
            case 78:
                {
                alt97=2;
                }
                break;
            case 51:
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
                    // InternalKactors.g:5630:3: (enumLiteral_0= '/' )
                    {
                    // InternalKactors.g:5630:3: (enumLiteral_0= '/' )
                    // InternalKactors.g:5631:4: enumLiteral_0= '/'
                    {
                    enumLiteral_0=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_0, grammarAccess.getUnitOpAccess().getOVEREnumLiteralDeclaration_0());
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:5638:3: (enumLiteral_1= '^' )
                    {
                    // InternalKactors.g:5638:3: (enumLiteral_1= '^' )
                    // InternalKactors.g:5639:4: enumLiteral_1= '^'
                    {
                    enumLiteral_1=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                      				newLeafNode(enumLiteral_1, grammarAccess.getUnitOpAccess().getCARETEnumLiteralDeclaration_1());
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:5646:3: (enumLiteral_2= '*' )
                    {
                    // InternalKactors.g:5646:3: (enumLiteral_2= '*' )
                    // InternalKactors.g:5647:4: enumLiteral_2= '*'
                    {
                    enumLiteral_2=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
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

    // $ANTLR start synpred5_InternalKactors
    public final void synpred5_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_imports_5_0 = null;

        AntlrDatatypeRuleToken lv_imports_7_0 = null;


        // InternalKactors.g:206:4: ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) )
        // InternalKactors.g:206:4: ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) )
        {
        // InternalKactors.g:206:4: ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) )
        // InternalKactors.g:207:5: {...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred5_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKactors.g:207:105: ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) )
        // InternalKactors.g:208:6: ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
        // InternalKactors.g:211:9: ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) )
        // InternalKactors.g:211:10: {...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred5_InternalKactors", "true");
        }
        // InternalKactors.g:211:19: (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* )
        // InternalKactors.g:211:20: otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )*
        {
        otherlv_4=(Token)match(input,23,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:215:9: ( (lv_imports_5_0= rulePathName ) )
        // InternalKactors.g:216:10: (lv_imports_5_0= rulePathName )
        {
        // InternalKactors.g:216:10: (lv_imports_5_0= rulePathName )
        // InternalKactors.g:217:11: lv_imports_5_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_1_0());
          										
        }
        pushFollow(FOLLOW_18);
        lv_imports_5_0=rulePathName();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:234:9: (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )*
        loop98:
        do {
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==24) ) {
                alt98=1;
            }


            switch (alt98) {
        	case 1 :
        	    // InternalKactors.g:235:10: otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) )
        	    {
        	    otherlv_6=(Token)match(input,24,FOLLOW_4); if (state.failed) return ;
        	    // InternalKactors.g:239:10: ( (lv_imports_7_0= rulePathName ) )
        	    // InternalKactors.g:240:11: (lv_imports_7_0= rulePathName )
        	    {
        	    // InternalKactors.g:240:11: (lv_imports_7_0= rulePathName )
        	    // InternalKactors.g:241:12: lv_imports_7_0= rulePathName
        	    {
        	    if ( state.backtracking==0 ) {

        	      												newCompositeNode(grammarAccess.getPreambleAccess().getImportsPathNameParserRuleCall_2_0_2_1_0());
        	      											
        	    }
        	    pushFollow(FOLLOW_18);
        	    lv_imports_7_0=rulePathName();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }


        	    }
        	    break;

        	default :
        	    break loop98;
            }
        } while (true);


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred5_InternalKactors

    // $ANTLR start synpred6_InternalKactors
    public final void synpred6_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_worldview_9_0 = null;


        // InternalKactors.g:265:4: ( ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) )
        // InternalKactors.g:265:4: ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) )
        {
        // InternalKactors.g:265:4: ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) )
        // InternalKactors.g:266:5: {...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred6_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKactors.g:266:105: ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) )
        // InternalKactors.g:267:6: ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
        // InternalKactors.g:270:9: ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) )
        // InternalKactors.g:270:10: {...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred6_InternalKactors", "true");
        }
        // InternalKactors.g:270:19: (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) )
        // InternalKactors.g:270:20: otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) )
        {
        otherlv_8=(Token)match(input,25,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:274:9: ( (lv_worldview_9_0= rulePathName ) )
        // InternalKactors.g:275:10: (lv_worldview_9_0= rulePathName )
        {
        // InternalKactors.g:275:10: (lv_worldview_9_0= rulePathName )
        // InternalKactors.g:276:11: lv_worldview_9_0= rulePathName
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getWorldviewPathNameParserRuleCall_2_1_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_worldview_9_0=rulePathName();

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
    // $ANTLR end synpred6_InternalKactors

    // $ANTLR start synpred9_InternalKactors
    public final void synpred9_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_10=null;
        Token lv_label_11_1=null;
        Token lv_label_11_2=null;
        Token lv_label_11_3=null;

        // InternalKactors.g:299:4: ( ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) )
        // InternalKactors.g:299:4: ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) )
        {
        // InternalKactors.g:299:4: ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:300:5: {...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKactors.g:300:105: ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:301:6: ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
        // InternalKactors.g:304:9: ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) )
        // InternalKactors.g:304:10: {...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred9_InternalKactors", "true");
        }
        // InternalKactors.g:304:19: (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) )
        // InternalKactors.g:304:20: otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) )
        {
        otherlv_10=(Token)match(input,26,FOLLOW_7); if (state.failed) return ;
        // InternalKactors.g:308:9: ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) )
        // InternalKactors.g:309:10: ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) )
        {
        // InternalKactors.g:309:10: ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) )
        // InternalKactors.g:310:11: (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING )
        {
        // InternalKactors.g:310:11: (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING )
        int alt99=3;
        switch ( input.LA(1) ) {
        case RULE_LOWERCASE_ID:
            {
            alt99=1;
            }
            break;
        case RULE_ID:
            {
            alt99=2;
            }
            break;
        case RULE_STRING:
            {
            alt99=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 99, 0, input);

            throw nvae;
        }

        switch (alt99) {
            case 1 :
                // InternalKactors.g:311:12: lv_label_11_1= RULE_LOWERCASE_ID
                {
                lv_label_11_1=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:326:12: lv_label_11_2= RULE_ID
                {
                lv_label_11_2=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalKactors.g:341:12: lv_label_11_3= RULE_STRING
                {
                lv_label_11_3=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred9_InternalKactors

    // $ANTLR start synpred10_InternalKactors
    public final void synpred10_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_12=null;
        Token lv_description_13_0=null;

        // InternalKactors.g:364:4: ( ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:364:4: ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:364:4: ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:365:5: {...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKactors.g:365:105: ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:366:6: ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
        // InternalKactors.g:369:9: ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) )
        // InternalKactors.g:369:10: {...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_InternalKactors", "true");
        }
        // InternalKactors.g:369:19: (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) )
        // InternalKactors.g:369:20: otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) )
        {
        otherlv_12=(Token)match(input,27,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:373:9: ( (lv_description_13_0= RULE_STRING ) )
        // InternalKactors.g:374:10: (lv_description_13_0= RULE_STRING )
        {
        // InternalKactors.g:374:10: (lv_description_13_0= RULE_STRING )
        // InternalKactors.g:375:11: lv_description_13_0= RULE_STRING
        {
        lv_description_13_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred10_InternalKactors

    // $ANTLR start synpred11_InternalKactors
    public final void synpred11_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_14=null;
        Token lv_permissions_15_0=null;

        // InternalKactors.g:397:4: ( ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:397:4: ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:397:4: ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:398:5: {...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4)");
        }
        // InternalKactors.g:398:105: ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:399:6: ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4);
        // InternalKactors.g:402:9: ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) )
        // InternalKactors.g:402:10: {...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_InternalKactors", "true");
        }
        // InternalKactors.g:402:19: (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) )
        // InternalKactors.g:402:20: otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) )
        {
        otherlv_14=(Token)match(input,28,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:406:9: ( (lv_permissions_15_0= RULE_STRING ) )
        // InternalKactors.g:407:10: (lv_permissions_15_0= RULE_STRING )
        {
        // InternalKactors.g:407:10: (lv_permissions_15_0= RULE_STRING )
        // InternalKactors.g:408:11: lv_permissions_15_0= RULE_STRING
        {
        lv_permissions_15_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred11_InternalKactors

    // $ANTLR start synpred12_InternalKactors
    public final void synpred12_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        Token lv_authors_17_0=null;

        // InternalKactors.g:435:10: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )
        // InternalKactors.g:435:10: {...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred12_InternalKactors", "true");
        }
        // InternalKactors.g:435:19: (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
        // InternalKactors.g:435:20: otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) )
        {
        otherlv_16=(Token)match(input,29,FOLLOW_8); if (state.failed) return ;
        // InternalKactors.g:439:9: ( (lv_authors_17_0= RULE_STRING ) )
        // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
        {
        // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
        // InternalKactors.g:441:11: lv_authors_17_0= RULE_STRING
        {
        lv_authors_17_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred12_InternalKactors

    // $ANTLR start synpred13_InternalKactors
    public final void synpred13_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_16=null;
        Token lv_authors_17_0=null;

        // InternalKactors.g:430:4: ( ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) )
        // InternalKactors.g:430:4: ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) )
        {
        // InternalKactors.g:430:4: ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) )
        // InternalKactors.g:431:5: {...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5)");
        }
        // InternalKactors.g:431:105: ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ )
        // InternalKactors.g:432:6: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5);
        // InternalKactors.g:435:9: ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+
        int cnt100=0;
        loop100:
        do {
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==29) && ((true))) {
                alt100=1;
            }


            switch (alt100) {
        	case 1 :
        	    // InternalKactors.g:435:10: {...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
        	    {
        	    if ( !((true)) ) {
        	        if (state.backtracking>0) {state.failed=true; return ;}
        	        throw new FailedPredicateException(input, "synpred13_InternalKactors", "true");
        	    }
        	    // InternalKactors.g:435:19: (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) )
        	    // InternalKactors.g:435:20: otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) )
        	    {
        	    otherlv_16=(Token)match(input,29,FOLLOW_8); if (state.failed) return ;
        	    // InternalKactors.g:439:9: ( (lv_authors_17_0= RULE_STRING ) )
        	    // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
        	    {
        	    // InternalKactors.g:440:10: (lv_authors_17_0= RULE_STRING )
        	    // InternalKactors.g:441:11: lv_authors_17_0= RULE_STRING
        	    {
        	    lv_authors_17_0=(Token)match(input,RULE_STRING,FOLLOW_77); if (state.failed) return ;

        	    }


        	    }


        	    }


        	    }
        	    break;

        	default :
        	    if ( cnt100 >= 1 ) break loop100;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(100, input);
                    throw eee;
            }
            cnt100++;
        } while (true);


        }


        }


        }
    }
    // $ANTLR end synpred13_InternalKactors

    // $ANTLR start synpred14_InternalKactors
    public final void synpred14_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_18=null;
        AntlrDatatypeRuleToken lv_version_19_0 = null;


        // InternalKactors.g:463:4: ( ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) )
        // InternalKactors.g:463:4: ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) )
        {
        // InternalKactors.g:463:4: ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) )
        // InternalKactors.g:464:5: {...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6)");
        }
        // InternalKactors.g:464:105: ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) )
        // InternalKactors.g:465:6: ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6);
        // InternalKactors.g:468:9: ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) )
        // InternalKactors.g:468:10: {...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred14_InternalKactors", "true");
        }
        // InternalKactors.g:468:19: (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) )
        // InternalKactors.g:468:20: otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) )
        {
        otherlv_18=(Token)match(input,30,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:472:9: ( (lv_version_19_0= ruleVersionNumber ) )
        // InternalKactors.g:473:10: (lv_version_19_0= ruleVersionNumber )
        {
        // InternalKactors.g:473:10: (lv_version_19_0= ruleVersionNumber )
        // InternalKactors.g:474:11: lv_version_19_0= ruleVersionNumber
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getVersionVersionNumberParserRuleCall_2_6_1_0());
          										
        }
        pushFollow(FOLLOW_2);
        lv_version_19_0=ruleVersionNumber();

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
        Token otherlv_20=null;
        Token lv_createcomment_22_0=null;
        EObject lv_created_21_0 = null;


        // InternalKactors.g:497:4: ( ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:497:4: ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:497:4: ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:498:5: {...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7)");
        }
        // InternalKactors.g:498:105: ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:499:6: ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7);
        // InternalKactors.g:502:9: ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) )
        // InternalKactors.g:502:10: {...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred16_InternalKactors", "true");
        }
        // InternalKactors.g:502:19: (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? )
        // InternalKactors.g:502:20: otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )?
        {
        otherlv_20=(Token)match(input,31,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:506:9: ( (lv_created_21_0= ruleDate ) )
        // InternalKactors.g:507:10: (lv_created_21_0= ruleDate )
        {
        // InternalKactors.g:507:10: (lv_created_21_0= ruleDate )
        // InternalKactors.g:508:11: lv_created_21_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getCreatedDateParserRuleCall_2_7_1_0());
          										
        }
        pushFollow(FOLLOW_78);
        lv_created_21_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:525:9: ( (lv_createcomment_22_0= RULE_STRING ) )?
        int alt101=2;
        int LA101_0 = input.LA(1);

        if ( (LA101_0==RULE_STRING) ) {
            alt101=1;
        }
        switch (alt101) {
            case 1 :
                // InternalKactors.g:526:10: (lv_createcomment_22_0= RULE_STRING )
                {
                // InternalKactors.g:526:10: (lv_createcomment_22_0= RULE_STRING )
                // InternalKactors.g:527:11: lv_createcomment_22_0= RULE_STRING
                {
                lv_createcomment_22_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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

    // $ANTLR start synpred18_InternalKactors
    public final void synpred18_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_23=null;
        Token lv_modcomment_25_0=null;
        EObject lv_modified_24_0 = null;


        // InternalKactors.g:549:4: ( ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )
        // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) )
        {
        // InternalKactors.g:549:4: ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) )
        // InternalKactors.g:550:5: {...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8)");
        }
        // InternalKactors.g:550:105: ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) )
        // InternalKactors.g:551:6: ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8);
        // InternalKactors.g:554:9: ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) )
        // InternalKactors.g:554:10: {...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred18_InternalKactors", "true");
        }
        // InternalKactors.g:554:19: (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? )
        // InternalKactors.g:554:20: otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )?
        {
        otherlv_23=(Token)match(input,32,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:558:9: ( (lv_modified_24_0= ruleDate ) )
        // InternalKactors.g:559:10: (lv_modified_24_0= ruleDate )
        {
        // InternalKactors.g:559:10: (lv_modified_24_0= ruleDate )
        // InternalKactors.g:560:11: lv_modified_24_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          											newCompositeNode(grammarAccess.getPreambleAccess().getModifiedDateParserRuleCall_2_8_1_0());
          										
        }
        pushFollow(FOLLOW_78);
        lv_modified_24_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:577:9: ( (lv_modcomment_25_0= RULE_STRING ) )?
        int alt102=2;
        int LA102_0 = input.LA(1);

        if ( (LA102_0==RULE_STRING) ) {
            alt102=1;
        }
        switch (alt102) {
            case 1 :
                // InternalKactors.g:578:10: (lv_modcomment_25_0= RULE_STRING )
                {
                // InternalKactors.g:578:10: (lv_modcomment_25_0= RULE_STRING )
                // InternalKactors.g:579:11: lv_modcomment_25_0= RULE_STRING
                {
                lv_modcomment_25_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

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
    // $ANTLR end synpred18_InternalKactors

    // $ANTLR start synpred29_InternalKactors
    public final void synpred29_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_literal_1_0 = null;


        // InternalKactors.g:977:3: ( ( (lv_literal_1_0= ruleLiteral ) ) )
        // InternalKactors.g:977:3: ( (lv_literal_1_0= ruleLiteral ) )
        {
        // InternalKactors.g:977:3: ( (lv_literal_1_0= ruleLiteral ) )
        // InternalKactors.g:978:4: (lv_literal_1_0= ruleLiteral )
        {
        // InternalKactors.g:978:4: (lv_literal_1_0= ruleLiteral )
        // InternalKactors.g:979:5: lv_literal_1_0= ruleLiteral
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
    // $ANTLR end synpred29_InternalKactors

    // $ANTLR start synpred30_InternalKactors
    public final void synpred30_InternalKactors_fragment() throws RecognitionException {   
        Token lv_id_2_0=null;

        // InternalKactors.g:997:3: ( ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) )
        // InternalKactors.g:997:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
        {
        // InternalKactors.g:997:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
        // InternalKactors.g:998:4: (lv_id_2_0= RULE_LOWERCASE_ID )
        {
        // InternalKactors.g:998:4: (lv_id_2_0= RULE_LOWERCASE_ID )
        // InternalKactors.g:999:5: lv_id_2_0= RULE_LOWERCASE_ID
        {
        lv_id_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred30_InternalKactors

    // $ANTLR start synpred31_InternalKactors
    public final void synpred31_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_urn_3_0 = null;


        // InternalKactors.g:1016:3: ( ( (lv_urn_3_0= ruleUrn ) ) )
        // InternalKactors.g:1016:3: ( (lv_urn_3_0= ruleUrn ) )
        {
        // InternalKactors.g:1016:3: ( (lv_urn_3_0= ruleUrn ) )
        // InternalKactors.g:1017:4: (lv_urn_3_0= ruleUrn )
        {
        // InternalKactors.g:1017:4: (lv_urn_3_0= ruleUrn )
        // InternalKactors.g:1018:5: lv_urn_3_0= ruleUrn
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getValueAccess().getUrnUrnParserRuleCall_3_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_urn_3_0=ruleUrn();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred31_InternalKactors

    // $ANTLR start synpred56_InternalKactors
    public final void synpred56_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:1787:5: ( 'to' )
        // InternalKactors.g:1787:6: 'to'
        {
        match(input,48,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred56_InternalKactors

    // $ANTLR start synpred83_InternalKactors
    public final void synpred83_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:2506:5: ( 'to' )
        // InternalKactors.g:2506:6: 'to'
        {
        match(input,48,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred83_InternalKactors

    // $ANTLR start synpred109_InternalKactors
    public final void synpred109_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_number_0_0 = null;


        // InternalKactors.g:3198:3: ( ( (lv_number_0_0= ruleNumber ) ) )
        // InternalKactors.g:3198:3: ( (lv_number_0_0= ruleNumber ) )
        {
        // InternalKactors.g:3198:3: ( (lv_number_0_0= ruleNumber ) )
        // InternalKactors.g:3199:4: (lv_number_0_0= ruleNumber )
        {
        // InternalKactors.g:3199:4: (lv_number_0_0= ruleNumber )
        // InternalKactors.g:3200:5: lv_number_0_0= ruleNumber
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
    // $ANTLR end synpred109_InternalKactors

    // $ANTLR start synpred112_InternalKactors
    public final void synpred112_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_date_5_0 = null;


        // InternalKactors.g:3282:3: ( ( (lv_date_5_0= ruleDate ) ) )
        // InternalKactors.g:3282:3: ( (lv_date_5_0= ruleDate ) )
        {
        // InternalKactors.g:3282:3: ( (lv_date_5_0= ruleDate ) )
        // InternalKactors.g:3283:4: (lv_date_5_0= ruleDate )
        {
        // InternalKactors.g:3283:4: (lv_date_5_0= ruleDate )
        // InternalKactors.g:3284:5: lv_date_5_0= ruleDate
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
    // $ANTLR end synpred112_InternalKactors

    // $ANTLR start synpred114_InternalKactors
    public final void synpred114_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_list_2_0 = null;


        // InternalKactors.g:3379:5: ( (lv_list_2_0= ruleStatement ) )
        // InternalKactors.g:3379:5: (lv_list_2_0= ruleStatement )
        {
        // InternalKactors.g:3379:5: (lv_list_2_0= ruleStatement )
        // InternalKactors.g:3380:6: lv_list_2_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_list_2_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred114_InternalKactors

    // $ANTLR start synpred115_InternalKactors
    public final void synpred115_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_list_1_0 = null;

        EObject lv_list_2_0 = null;


        // InternalKactors.g:3348:3: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) )
        // InternalKactors.g:3348:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
        {
        // InternalKactors.g:3348:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
        // InternalKactors.g:3349:4: () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )*
        {
        // InternalKactors.g:3349:4: ()
        // InternalKactors.g:3350:5: 
        {
        if ( state.backtracking==0 ) {

          					/* */
          				
        }

        }

        // InternalKactors.g:3359:4: ( (lv_list_1_0= ruleStatement ) )
        // InternalKactors.g:3360:5: (lv_list_1_0= ruleStatement )
        {
        // InternalKactors.g:3360:5: (lv_list_1_0= ruleStatement )
        // InternalKactors.g:3361:6: lv_list_1_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0());
          					
        }
        pushFollow(FOLLOW_15);
        lv_list_1_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:3378:4: ( (lv_list_2_0= ruleStatement ) )*
        loop119:
        do {
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==RULE_LOWERCASE_ID||LA119_0==RULE_EMBEDDEDTEXT||LA119_0==35||LA119_0==63||(LA119_0>=66 && LA119_0<=67)) ) {
                alt119=1;
            }


            switch (alt119) {
        	case 1 :
        	    // InternalKactors.g:3379:5: (lv_list_2_0= ruleStatement )
        	    {
        	    // InternalKactors.g:3379:5: (lv_list_2_0= ruleStatement )
        	    // InternalKactors.g:3380:6: lv_list_2_0= ruleStatement
        	    {
        	    if ( state.backtracking==0 ) {

        	      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0());
        	      					
        	    }
        	    pushFollow(FOLLOW_15);
        	    lv_list_2_0=ruleStatement();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }
        	    break;

        	default :
        	    break loop119;
            }
        } while (true);


        }


        }
    }
    // $ANTLR end synpred115_InternalKactors

    // $ANTLR start synpred124_InternalKactors
    public final void synpred124_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfCall_6_0 = null;


        // InternalKactors.g:3705:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )
        // InternalKactors.g:3705:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) )
        {
        otherlv_3=(Token)match(input,64,FOLLOW_56); if (state.failed) return ;
        otherlv_4=(Token)match(input,63,FOLLOW_53); if (state.failed) return ;
        // InternalKactors.g:3713:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:3714:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:3714:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:3715:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_54); if (state.failed) return ;

        }


        }

        // InternalKactors.g:3731:4: ( (lv_elseIfCall_6_0= ruleIfBody ) )
        // InternalKactors.g:3732:5: (lv_elseIfCall_6_0= ruleIfBody )
        {
        // InternalKactors.g:3732:5: (lv_elseIfCall_6_0= ruleIfBody )
        // InternalKactors.g:3733:6: lv_elseIfCall_6_0= ruleIfBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfCallIfBodyParserRuleCall_3_3_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_elseIfCall_6_0=ruleIfBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred124_InternalKactors

    // $ANTLR start synpred125_InternalKactors
    public final void synpred125_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:3752:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )
        // InternalKactors.g:3752:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) )
        {
        otherlv_7=(Token)match(input,64,FOLLOW_54); if (state.failed) return ;
        // InternalKactors.g:3756:4: ( (lv_elseCall_8_0= ruleIfBody ) )
        // InternalKactors.g:3757:5: (lv_elseCall_8_0= ruleIfBody )
        {
        // InternalKactors.g:3757:5: (lv_elseCall_8_0= ruleIfBody )
        // InternalKactors.g:3758:6: lv_elseCall_8_0= ruleIfBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getIfStatementAccess().getElseCallIfBodyParserRuleCall_4_1_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_elseCall_8_0=ruleIfBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred125_InternalKactors

    // $ANTLR start synpred130_InternalKactors
    public final void synpred130_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:4092:4: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:4092:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,35,FOLLOW_22); if (state.failed) return ;
        // InternalKactors.g:4096:4: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt122=2;
        int LA122_0 = input.LA(1);

        if ( (LA122_0==RULE_LOWERCASE_ID||(LA122_0>=RULE_STRING && LA122_0<=RULE_EXPR)||LA122_0==RULE_INT||LA122_0==35||LA122_0==39||LA122_0==42||(LA122_0>=44 && LA122_0<=45)||LA122_0==52||(LA122_0>=70 && LA122_0<=71)) ) {
            alt122=1;
        }
        switch (alt122) {
            case 1 :
                // InternalKactors.g:4097:5: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:4097:5: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:4098:6: lv_parameters_2_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  						newCompositeNode(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0());
                  					
                }
                pushFollow(FOLLOW_23);
                lv_parameters_2_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_3=(Token)match(input,36,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred130_InternalKactors

    // $ANTLR start synpred133_InternalKactors
    public final void synpred133_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        EObject lv_sequence_2_0 = null;


        // InternalKactors.g:4192:5: (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )
        // InternalKactors.g:4192:5: otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) )
        {
        otherlv_1=(Token)match(input,24,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4196:5: ( (lv_sequence_2_0= ruleStatement ) )
        // InternalKactors.g:4197:6: (lv_sequence_2_0= ruleStatement )
        {
        // InternalKactors.g:4197:6: (lv_sequence_2_0= ruleStatement )
        // InternalKactors.g:4198:7: lv_sequence_2_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          							newCompositeNode(grammarAccess.getActionsAccess().getSequenceStatementParserRuleCall_0_1_1_0());
          						
        }
        pushFollow(FOLLOW_2);
        lv_sequence_2_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred133_InternalKactors

    // $ANTLR start synpred134_InternalKactors
    public final void synpred134_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        EObject lv_sequence_0_0 = null;

        EObject lv_sequence_2_0 = null;


        // InternalKactors.g:4171:3: ( ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* ) )
        // InternalKactors.g:4171:3: ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* )
        {
        // InternalKactors.g:4171:3: ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* )
        // InternalKactors.g:4172:4: ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )*
        {
        // InternalKactors.g:4172:4: ( (lv_sequence_0_0= ruleStatement ) )
        // InternalKactors.g:4173:5: (lv_sequence_0_0= ruleStatement )
        {
        // InternalKactors.g:4173:5: (lv_sequence_0_0= ruleStatement )
        // InternalKactors.g:4174:6: lv_sequence_0_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getActionsAccess().getSequenceStatementParserRuleCall_0_0_0());
          					
        }
        pushFollow(FOLLOW_18);
        lv_sequence_0_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:4191:4: (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )*
        loop123:
        do {
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( (LA123_0==24) ) {
                alt123=1;
            }


            switch (alt123) {
        	case 1 :
        	    // InternalKactors.g:4192:5: otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) )
        	    {
        	    otherlv_1=(Token)match(input,24,FOLLOW_14); if (state.failed) return ;
        	    // InternalKactors.g:4196:5: ( (lv_sequence_2_0= ruleStatement ) )
        	    // InternalKactors.g:4197:6: (lv_sequence_2_0= ruleStatement )
        	    {
        	    // InternalKactors.g:4197:6: (lv_sequence_2_0= ruleStatement )
        	    // InternalKactors.g:4198:7: lv_sequence_2_0= ruleStatement
        	    {
        	    if ( state.backtracking==0 ) {

        	      							newCompositeNode(grammarAccess.getActionsAccess().getSequenceStatementParserRuleCall_0_1_1_0());
        	      						
        	    }
        	    pushFollow(FOLLOW_18);
        	    lv_sequence_2_0=ruleStatement();

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
    // $ANTLR end synpred134_InternalKactors

    // $ANTLR start synpred135_InternalKactors
    public final void synpred135_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_3_0 = null;


        // InternalKactors.g:4218:3: ( ( (lv_body_3_0= ruleBody ) ) )
        // InternalKactors.g:4218:3: ( (lv_body_3_0= ruleBody ) )
        {
        // InternalKactors.g:4218:3: ( (lv_body_3_0= ruleBody ) )
        // InternalKactors.g:4219:4: (lv_body_3_0= ruleBody )
        {
        // InternalKactors.g:4219:4: (lv_body_3_0= ruleBody )
        // InternalKactors.g:4220:5: lv_body_3_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_body_3_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred135_InternalKactors

    // $ANTLR start synpred136_InternalKactors
    public final void synpred136_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_match_4_0 = null;


        // InternalKactors.g:4238:3: ( ( (lv_match_4_0= ruleMatch ) ) )
        // InternalKactors.g:4238:3: ( (lv_match_4_0= ruleMatch ) )
        {
        // InternalKactors.g:4238:3: ( (lv_match_4_0= ruleMatch ) )
        // InternalKactors.g:4239:4: (lv_match_4_0= ruleMatch )
        {
        // InternalKactors.g:4239:4: (lv_match_4_0= ruleMatch )
        // InternalKactors.g:4240:5: lv_match_4_0= ruleMatch
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_2_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_match_4_0=ruleMatch();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred136_InternalKactors

    // $ANTLR start synpred141_InternalKactors
    public final void synpred141_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_10=null;
        EObject lv_literal_9_0 = null;

        EObject lv_body_11_0 = null;


        // InternalKactors.g:4457:3: ( ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) )
        // InternalKactors.g:4457:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
        {
        // InternalKactors.g:4457:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
        // InternalKactors.g:4458:4: ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) )
        {
        // InternalKactors.g:4458:4: ( (lv_literal_9_0= ruleLiteral ) )
        // InternalKactors.g:4459:5: (lv_literal_9_0= ruleLiteral )
        {
        // InternalKactors.g:4459:5: (lv_literal_9_0= ruleLiteral )
        // InternalKactors.g:4460:6: lv_literal_9_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0());
          					
        }
        pushFollow(FOLLOW_63);
        lv_literal_9_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_10=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4481:4: ( (lv_body_11_0= ruleBody ) )
        // InternalKactors.g:4482:5: (lv_body_11_0= ruleBody )
        {
        // InternalKactors.g:4482:5: (lv_body_11_0= ruleBody )
        // InternalKactors.g:4483:6: lv_body_11_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_3_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_11_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred141_InternalKactors

    // $ANTLR start synpred142_InternalKactors
    public final void synpred142_InternalKactors_fragment() throws RecognitionException {   
        Token lv_text_12_0=null;
        Token otherlv_13=null;
        EObject lv_body_14_0 = null;


        // InternalKactors.g:4502:3: ( ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) )
        // InternalKactors.g:4502:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
        {
        // InternalKactors.g:4502:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
        // InternalKactors.g:4503:4: ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) )
        {
        // InternalKactors.g:4503:4: ( (lv_text_12_0= RULE_STRING ) )
        // InternalKactors.g:4504:5: (lv_text_12_0= RULE_STRING )
        {
        // InternalKactors.g:4504:5: (lv_text_12_0= RULE_STRING )
        // InternalKactors.g:4505:6: lv_text_12_0= RULE_STRING
        {
        lv_text_12_0=(Token)match(input,RULE_STRING,FOLLOW_63); if (state.failed) return ;

        }


        }

        otherlv_13=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4525:4: ( (lv_body_14_0= ruleBody ) )
        // InternalKactors.g:4526:5: (lv_body_14_0= ruleBody )
        {
        // InternalKactors.g:4526:5: (lv_body_14_0= ruleBody )
        // InternalKactors.g:4527:6: lv_body_14_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_4_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_14_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred142_InternalKactors

    // $ANTLR start synpred146_InternalKactors
    public final void synpred146_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:4633:5: ( 'to' )
        // InternalKactors.g:4633:6: 'to'
        {
        match(input,48,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred146_InternalKactors

    // $ANTLR start synpred150_InternalKactors
    public final void synpred150_InternalKactors_fragment() throws RecognitionException {   
        Token lv_leftLimit_19_0=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token lv_rightLimit_23_0=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        EObject lv_int0_18_0 = null;

        EObject lv_int1_22_0 = null;

        EObject lv_body_26_0 = null;


        // InternalKactors.g:4591:3: ( ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) ) )
        // InternalKactors.g:4591:3: ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) )
        {
        // InternalKactors.g:4591:3: ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) )
        // InternalKactors.g:4592:4: ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) )
        {
        // InternalKactors.g:4592:4: ( (lv_int0_18_0= ruleNumber ) )
        // InternalKactors.g:4593:5: (lv_int0_18_0= ruleNumber )
        {
        // InternalKactors.g:4593:5: (lv_int0_18_0= ruleNumber )
        // InternalKactors.g:4594:6: lv_int0_18_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getInt0NumberParserRuleCall_6_0_0());
          					
        }
        pushFollow(FOLLOW_33);
        lv_int0_18_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:4611:4: ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )?
        int alt124=3;
        int LA124_0 = input.LA(1);

        if ( (LA124_0==46) ) {
            alt124=1;
        }
        else if ( (LA124_0==47) ) {
            alt124=2;
        }
        switch (alt124) {
            case 1 :
                // InternalKactors.g:4612:5: ( (lv_leftLimit_19_0= 'inclusive' ) )
                {
                // InternalKactors.g:4612:5: ( (lv_leftLimit_19_0= 'inclusive' ) )
                // InternalKactors.g:4613:6: (lv_leftLimit_19_0= 'inclusive' )
                {
                // InternalKactors.g:4613:6: (lv_leftLimit_19_0= 'inclusive' )
                // InternalKactors.g:4614:7: lv_leftLimit_19_0= 'inclusive'
                {
                lv_leftLimit_19_0=(Token)match(input,46,FOLLOW_34); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:4627:5: otherlv_20= 'exclusive'
                {
                otherlv_20=(Token)match(input,47,FOLLOW_34); if (state.failed) return ;

                }
                break;

        }

        // InternalKactors.g:4632:4: ( ( 'to' )=>otherlv_21= 'to' )
        // InternalKactors.g:4633:5: ( 'to' )=>otherlv_21= 'to'
        {
        otherlv_21=(Token)match(input,48,FOLLOW_35); if (state.failed) return ;

        }

        // InternalKactors.g:4639:4: ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) )
        // InternalKactors.g:4640:5: ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber )
        {
        // InternalKactors.g:4644:5: (lv_int1_22_0= ruleNumber )
        // InternalKactors.g:4645:6: lv_int1_22_0= ruleNumber
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getInt1NumberParserRuleCall_6_3_0());
          					
        }
        pushFollow(FOLLOW_64);
        lv_int1_22_0=ruleNumber();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:4662:4: ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )?
        int alt125=3;
        int LA125_0 = input.LA(1);

        if ( (LA125_0==46) ) {
            alt125=1;
        }
        else if ( (LA125_0==47) ) {
            alt125=2;
        }
        switch (alt125) {
            case 1 :
                // InternalKactors.g:4663:5: ( (lv_rightLimit_23_0= 'inclusive' ) )
                {
                // InternalKactors.g:4663:5: ( (lv_rightLimit_23_0= 'inclusive' ) )
                // InternalKactors.g:4664:6: (lv_rightLimit_23_0= 'inclusive' )
                {
                // InternalKactors.g:4664:6: (lv_rightLimit_23_0= 'inclusive' )
                // InternalKactors.g:4665:7: lv_rightLimit_23_0= 'inclusive'
                {
                lv_rightLimit_23_0=(Token)match(input,46,FOLLOW_63); if (state.failed) return ;

                }


                }


                }
                break;
            case 2 :
                // InternalKactors.g:4678:5: otherlv_24= 'exclusive'
                {
                otherlv_24=(Token)match(input,47,FOLLOW_63); if (state.failed) return ;

                }
                break;

        }

        otherlv_25=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4687:4: ( (lv_body_26_0= ruleBody ) )
        // InternalKactors.g:4688:5: (lv_body_26_0= ruleBody )
        {
        // InternalKactors.g:4688:5: (lv_body_26_0= ruleBody )
        // InternalKactors.g:4689:6: lv_body_26_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_6_6_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_26_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred150_InternalKactors

    // $ANTLR start synpred152_InternalKactors
    public final void synpred152_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_32=null;
        EObject lv_quantity_31_0 = null;

        EObject lv_body_33_0 = null;


        // InternalKactors.g:4757:3: ( ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) ) )
        // InternalKactors.g:4757:3: ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) )
        {
        // InternalKactors.g:4757:3: ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) )
        // InternalKactors.g:4758:4: ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) )
        {
        // InternalKactors.g:4758:4: ( (lv_quantity_31_0= ruleQuantity ) )
        // InternalKactors.g:4759:5: (lv_quantity_31_0= ruleQuantity )
        {
        // InternalKactors.g:4759:5: (lv_quantity_31_0= ruleQuantity )
        // InternalKactors.g:4760:6: lv_quantity_31_0= ruleQuantity
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getQuantityQuantityParserRuleCall_8_0_0());
          					
        }
        pushFollow(FOLLOW_63);
        lv_quantity_31_0=ruleQuantity();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_32=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4781:4: ( (lv_body_33_0= ruleBody ) )
        // InternalKactors.g:4782:5: (lv_body_33_0= ruleBody )
        {
        // InternalKactors.g:4782:5: (lv_body_33_0= ruleBody )
        // InternalKactors.g:4783:6: lv_body_33_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_8_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_33_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred152_InternalKactors

    // $ANTLR start synpred153_InternalKactors
    public final void synpred153_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_35=null;
        EObject lv_date_34_0 = null;

        EObject lv_body_36_0 = null;


        // InternalKactors.g:4802:3: ( ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) ) )
        // InternalKactors.g:4802:3: ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) )
        {
        // InternalKactors.g:4802:3: ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) )
        // InternalKactors.g:4803:4: ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) )
        {
        // InternalKactors.g:4803:4: ( (lv_date_34_0= ruleDate ) )
        // InternalKactors.g:4804:5: (lv_date_34_0= ruleDate )
        {
        // InternalKactors.g:4804:5: (lv_date_34_0= ruleDate )
        // InternalKactors.g:4805:6: lv_date_34_0= ruleDate
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getDateDateParserRuleCall_9_0_0());
          					
        }
        pushFollow(FOLLOW_63);
        lv_date_34_0=ruleDate();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_35=(Token)match(input,69,FOLLOW_14); if (state.failed) return ;
        // InternalKactors.g:4826:4: ( (lv_body_36_0= ruleBody ) )
        // InternalKactors.g:4827:5: (lv_body_36_0= ruleBody )
        {
        // InternalKactors.g:4827:5: (lv_body_36_0= ruleBody )
        // InternalKactors.g:4828:6: lv_body_36_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_9_2_0());
          					
        }
        pushFollow(FOLLOW_2);
        lv_body_36_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred153_InternalKactors

    // $ANTLR start synpred159_InternalKactors
    public final void synpred159_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5051:4: ( ( RULE_INT ) )
        // InternalKactors.g:5051:5: ( RULE_INT )
        {
        // InternalKactors.g:5051:5: ( RULE_INT )
        // InternalKactors.g:5052:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred159_InternalKactors

    // $ANTLR start synpred160_InternalKactors
    public final void synpred160_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5073:4: ( ( 'l' ) )
        // InternalKactors.g:5073:5: ( 'l' )
        {
        // InternalKactors.g:5073:5: ( 'l' )
        // InternalKactors.g:5074:5: 'l'
        {
        match(input,72,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred160_InternalKactors

    // $ANTLR start synpred161_InternalKactors
    public final void synpred161_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5091:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5091:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5091:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:5092:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:5092:5: ( ( '.' ) )
        // InternalKactors.g:5093:6: ( '.' )
        {
        // InternalKactors.g:5093:6: ( '.' )
        // InternalKactors.g:5094:7: '.'
        {
        match(input,56,FOLLOW_9); if (state.failed) return ;

        }


        }

        // InternalKactors.g:5097:5: ( ( RULE_INT ) )
        // InternalKactors.g:5098:6: ( RULE_INT )
        {
        // InternalKactors.g:5098:6: ( RULE_INT )
        // InternalKactors.g:5099:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred161_InternalKactors

    // $ANTLR start synpred165_InternalKactors
    public final void synpred165_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:5140:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:5140:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:5140:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:5141:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:5141:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:5142:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:5142:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:5143:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=73 && input.LA(1)<=74) ) {
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

        // InternalKactors.g:5150:5: ( '+' | ( ( '-' ) ) )?
        int alt126=3;
        int LA126_0 = input.LA(1);

        if ( (LA126_0==70) ) {
            alt126=1;
        }
        else if ( (LA126_0==71) ) {
            alt126=2;
        }
        switch (alt126) {
            case 1 :
                // InternalKactors.g:5151:6: '+'
                {
                match(input,70,FOLLOW_9); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:5153:6: ( ( '-' ) )
                {
                // InternalKactors.g:5153:6: ( ( '-' ) )
                // InternalKactors.g:5154:7: ( '-' )
                {
                // InternalKactors.g:5154:7: ( '-' )
                // InternalKactors.g:5155:8: '-'
                {
                match(input,71,FOLLOW_9); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:5159:5: ( ( RULE_INT ) )
        // InternalKactors.g:5160:6: ( RULE_INT )
        {
        // InternalKactors.g:5160:6: ( RULE_INT )
        // InternalKactors.g:5161:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred165_InternalKactors

    // $ANTLR start synpred182_InternalKactors
    public final void synpred182_InternalKactors_fragment() throws RecognitionException {   
        Token kw=null;

        // InternalKactors.g:5595:4: (kw= '-' )
        // InternalKactors.g:5595:4: kw= '-'
        {
        kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred182_InternalKactors

    // $ANTLR start synpred183_InternalKactors
    public final void synpred183_InternalKactors_fragment() throws RecognitionException {   
        Token this_LOWERCASE_ID_6=null;

        // InternalKactors.g:5602:4: (this_LOWERCASE_ID_6= RULE_LOWERCASE_ID )
        // InternalKactors.g:5602:4: this_LOWERCASE_ID_6= RULE_LOWERCASE_ID
        {
        this_LOWERCASE_ID_6=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred183_InternalKactors

    // Delegated rules

    public final boolean synpred182_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred182_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred135_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred135_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred159_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred159_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred141_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred141_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred161_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred161_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred130_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred130_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred142_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred142_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred29_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred29_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred114_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred114_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred124_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred124_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred83_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred83_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred136_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred136_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred183_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred183_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred30_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred30_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred133_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred133_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred152_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred152_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred153_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred153_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred150_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred150_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred115_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred115_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred31_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred31_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred5_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred109_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred109_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred165_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred165_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred134_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred134_InternalKactors_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA19 dfa19 = new DFA19(this);
    protected DFA25 dfa25 = new DFA25(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA36 dfa36 = new DFA36(this);
    protected DFA38 dfa38 = new DFA38(this);
    protected DFA47 dfa47 = new DFA47(this);
    protected DFA57 dfa57 = new DFA57(this);
    protected DFA70 dfa70 = new DFA70(this);
    protected DFA74 dfa74 = new DFA74(this);
    protected DFA77 dfa77 = new DFA77(this);
    static final String dfa_1s = "\13\uffff";
    static final String dfa_2s = "\1\1\12\uffff";
    static final String dfa_3s = "\1\12\12\uffff";
    static final String dfa_4s = "\1\41\12\uffff";
    static final String dfa_5s = "\1\uffff\1\12\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
    static final String dfa_6s = "\1\0\12\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\14\uffff\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\1",
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

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()* loopback of 205:6: ( ({...}? => ( ({...}? => (otherlv_4= 'import' ( (lv_imports_5_0= rulePathName ) ) (otherlv_6= ',' ( (lv_imports_7_0= rulePathName ) ) )* ) ) ) ) | ({...}? => ( ({...}? => (otherlv_8= 'worldview' ( (lv_worldview_9_0= rulePathName ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= 'label' ( ( (lv_label_11_1= RULE_LOWERCASE_ID | lv_label_11_2= RULE_ID | lv_label_11_3= RULE_STRING ) ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_12= 'description' ( (lv_description_13_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_14= 'permissions' ( (lv_permissions_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= 'author' ( (lv_authors_17_0= RULE_STRING ) ) ) )+ ) ) | ({...}? => ( ({...}? => (otherlv_18= 'version' ( (lv_version_19_0= ruleVersionNumber ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_20= 'created' ( (lv_created_21_0= ruleDate ) ) ( (lv_createcomment_22_0= RULE_STRING ) )? ) ) ) ) | ({...}? => ( ({...}? => (otherlv_23= 'modified' ( (lv_modified_24_0= ruleDate ) ) ( (lv_modcomment_25_0= RULE_STRING ) )? ) ) ) ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_0 = input.LA(1);

                         
                        int index9_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA9_0==EOF||LA9_0==RULE_ANNOTATION_ID||LA9_0==33) ) {s = 1;}

                        else if ( LA9_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {s = 2;}

                        else if ( LA9_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {s = 3;}

                        else if ( LA9_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {s = 4;}

                        else if ( LA9_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {s = 5;}

                        else if ( LA9_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 4) ) {s = 6;}

                        else if ( LA9_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 5) ) {s = 7;}

                        else if ( LA9_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 6) ) {s = 8;}

                        else if ( LA9_0 == 31 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 7) ) {s = 9;}

                        else if ( LA9_0 == 32 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 8) ) {s = 10;}

                         
                        input.seek(index9_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\14\uffff";
    static final String dfa_9s = "\1\4\2\uffff\2\0\7\uffff";
    static final String dfa_10s = "\1\107\2\uffff\2\0\7\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\2\2\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\3";
    static final String dfa_12s = "\3\uffff\1\0\1\1\7\uffff}>";
    static final String[] dfa_13s = {
            "\1\4\1\uffff\1\3\1\1\1\10\1\11\1\uffff\1\2\27\uffff\1\6\3\uffff\1\5\2\uffff\1\7\1\uffff\2\2\6\uffff\1\12\21\uffff\2\2",
            "",
            "",
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

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "957:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_urn_3_0= ruleUrn ) ) | ( (lv_list_4_0= ruleList ) ) | ( (lv_map_5_0= ruleMap ) ) | ( (lv_observable_6_0= RULE_OBSERVABLE ) ) | ( (lv_expression_7_0= RULE_EXPR ) ) | ( (lv_table_8_0= ruleLookupTable ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA18_3 = input.LA(1);

                         
                        int index18_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_InternalKactors()) ) {s = 2;}

                        else if ( (synpred31_InternalKactors()) ) {s = 5;}

                         
                        input.seek(index18_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA18_4 = input.LA(1);

                         
                        int index18_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred30_InternalKactors()) ) {s = 11;}

                        else if ( (synpred31_InternalKactors()) ) {s = 5;}

                         
                        input.seek(index18_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 18, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_14s = "\7\uffff";
    static final String dfa_15s = "\1\uffff\1\5\4\uffff\1\5";
    static final String dfa_16s = "\2\4\2\uffff\1\4\1\uffff\1\4";
    static final String dfa_17s = "\1\47\1\107\2\uffff\1\4\1\uffff\1\107";
    static final String dfa_18s = "\2\uffff\1\2\1\3\1\uffff\1\1\1\uffff";
    static final String dfa_19s = "\7\uffff}>";
    static final String[] dfa_20s = {
            "\1\1\1\uffff\1\3\40\uffff\1\2",
            "\1\5\1\uffff\4\5\1\uffff\1\5\14\uffff\1\5\11\uffff\1\2\2\5\2\uffff\1\5\2\uffff\4\5\6\uffff\1\5\3\uffff\1\4\15\uffff\2\5",
            "",
            "",
            "\1\6",
            "",
            "\1\5\1\uffff\4\5\1\uffff\1\5\14\uffff\1\5\11\uffff\1\2\2\5\2\uffff\1\5\2\uffff\4\5\6\uffff\1\5\3\uffff\1\4\15\uffff\2\5"
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1153:4: (lv_name_0_1= rulePathName | lv_name_0_2= ruleUrnId | lv_name_0_3= RULE_STRING )";
        }
    }
    static final String dfa_21s = "\6\uffff";
    static final String dfa_22s = "\1\uffff\1\2\3\uffff\1\2";
    static final String dfa_23s = "\2\4\1\uffff\1\4\1\uffff\1\4";
    static final String dfa_24s = "\1\16\1\107\1\uffff\1\16\1\uffff\1\107";
    static final String dfa_25s = "\2\uffff\1\1\1\uffff\1\2\1\uffff";
    static final String dfa_26s = "\6\uffff}>";
    static final String[] dfa_27s = {
            "\1\1\11\uffff\1\2",
            "\1\2\1\uffff\4\2\1\uffff\1\2\14\uffff\1\2\12\uffff\2\2\1\uffff\1\4\1\2\1\uffff\5\2\6\uffff\1\2\2\uffff\1\2\1\3\15\uffff\2\2",
            "",
            "\1\5\11\uffff\1\2",
            "",
            "\1\2\1\uffff\4\2\1\uffff\1\2\14\uffff\1\2\12\uffff\2\2\1\uffff\1\4\1\2\1\uffff\5\2\6\uffff\1\2\2\uffff\1\2\1\3\15\uffff\2\2"
    };

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA25 extends DFA {

        public DFA25(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 25;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "1382:4: (this_Path_12= rulePath | this_UrnKvp_13= ruleUrnKvp )";
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "1411:5: (this_Path_15= rulePath | this_UrnKvp_16= ruleUrnKvp )";
        }
    }
    static final String dfa_28s = "\26\uffff";
    static final String dfa_29s = "\4\uffff\1\21\7\uffff\1\21\5\uffff\1\21\2\uffff\1\21";
    static final String dfa_30s = "\1\4\1\uffff\2\13\1\42\7\uffff\1\42\3\13\2\uffff\1\42\2\13\1\42";
    static final String dfa_31s = "\1\107\1\uffff\2\13\1\112\7\uffff\1\112\1\13\2\107\2\uffff\1\112\2\13\1\60";
    static final String dfa_32s = "\1\uffff\1\1\3\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\4\uffff\1\2\1\3\4\uffff";
    static final String dfa_33s = "\26\uffff}>";
    static final String[] dfa_34s = {
            "\1\10\1\uffff\1\6\1\uffff\1\7\2\uffff\1\4\32\uffff\1\11\5\uffff\2\1\3\uffff\1\5\1\12\1\13\6\uffff\5\11\7\uffff\1\2\1\3",
            "",
            "\1\4",
            "\1\4",
            "\1\21\13\uffff\3\20\7\uffff\1\15\17\uffff\1\14\1\16\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\21\13\uffff\3\20\7\uffff\1\15\20\uffff\1\16\1\17",
            "\1\22",
            "\1\25\72\uffff\1\23\1\24",
            "\1\25\72\uffff\1\23\1\24",
            "",
            "",
            "\1\21\13\uffff\3\20\30\uffff\1\16\1\17",
            "\1\25",
            "\1\25",
            "\1\21\13\uffff\3\20"
    };

    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final char[] dfa_31 = DFA.unpackEncodedStringToUnsignedChars(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final short[][] dfa_34 = unpackEncodedStringArray(dfa_34s);

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = dfa_28;
            this.eof = dfa_29;
            this.min = dfa_30;
            this.max = dfa_31;
            this.accept = dfa_32;
            this.special = dfa_33;
            this.transition = dfa_34;
        }
        public String getDescription() {
            return "1712:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( ( (lv_int0_2_0= ruleNumber ) ) ( ( (lv_leftLimit_3_0= 'inclusive' ) ) | otherlv_4= 'exclusive' )? ( ( 'to' )=>otherlv_5= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_6_0= ruleNumber ) ) ( ( (lv_rightLimit_7_0= 'inclusive' ) ) | otherlv_8= 'exclusive' )? ) | ( (lv_num_9_0= ruleNumber ) ) | (otherlv_10= 'in' ( (lv_set_11_0= ruleList ) ) ) | ( (lv_string_12_0= RULE_STRING ) ) | ( (lv_observable_13_0= RULE_OBSERVABLE ) ) | ( (lv_id_14_0= RULE_LOWERCASE_ID ) ) | ( ( (lv_op_15_0= ruleREL_OPERATOR ) ) ( (lv_expression_16_0= ruleNumber ) ) ) | ( (lv_nodata_17_0= 'unknown' ) ) | ( (lv_star_18_0= '*' ) ) )";
        }
    }
    static final String dfa_35s = "\2\uffff\1\3\2\uffff\1\3";
    static final String dfa_36s = "\1\4\1\uffff\1\14\1\uffff\1\4\1\14";
    static final String dfa_37s = "\1\107\1\uffff\1\66\1\uffff\1\107\1\66";
    static final String dfa_38s = "\1\uffff\1\1\1\uffff\1\2\2\uffff";
    static final String[] dfa_39s = {
            "\1\1\1\uffff\1\2\1\uffff\2\3\1\uffff\1\3\32\uffff\1\3\1\uffff\1\3\3\uffff\2\3\3\uffff\3\3\6\uffff\5\3\7\uffff\2\3",
            "",
            "\1\1\13\uffff\1\3\34\uffff\1\3\1\4",
            "",
            "\1\1\1\uffff\1\5\1\uffff\2\3\1\uffff\1\3\32\uffff\1\3\1\uffff\1\3\3\uffff\2\3\3\uffff\3\3\6\uffff\5\3\7\uffff\2\3",
            "\1\1\13\uffff\1\3\34\uffff\1\3\1\4"
    };
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final char[] dfa_36 = DFA.unpackEncodedStringToUnsignedChars(dfa_36s);
    static final char[] dfa_37 = DFA.unpackEncodedStringToUnsignedChars(dfa_37s);
    static final short[] dfa_38 = DFA.unpackEncodedString(dfa_38s);
    static final short[][] dfa_39 = unpackEncodedStringArray(dfa_39s);

    class DFA38 extends DFA {

        public DFA38(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 38;
            this.eot = dfa_21;
            this.eof = dfa_35;
            this.min = dfa_36;
            this.max = dfa_37;
            this.accept = dfa_38;
            this.special = dfa_26;
            this.transition = dfa_39;
        }
        public String getDescription() {
            return "2087:3: ( ( (lv_headers_0_0= ruleHeaderRow ) ) this_SEPARATOR_1= RULE_SEPARATOR )?";
        }
    }
    static final String dfa_40s = "\32\uffff";
    static final String dfa_41s = "\4\uffff\1\22\10\uffff\2\22\1\23\6\uffff\1\22\2\uffff\1\22";
    static final String dfa_42s = "\1\6\1\uffff\2\13\1\30\10\uffff\2\30\1\4\2\13\4\uffff\1\30\2\13\1\30";
    static final String dfa_43s = "\1\107\1\uffff\2\13\1\115\10\uffff\2\112\1\116\2\107\4\uffff\1\112\2\13\1\70";
    static final String dfa_44s = "\1\uffff\1\1\3\uffff\1\3\1\4\1\5\1\7\1\12\1\13\1\14\1\15\5\uffff\1\2\1\10\1\11\1\6\4\uffff";
    static final String dfa_45s = "\32\uffff}>";
    static final String[] dfa_46s = {
            "\1\5\1\uffff\1\6\1\11\1\uffff\1\4\32\uffff\1\7\1\uffff\1\14\3\uffff\2\1\3\uffff\1\10\1\12\1\13\6\uffff\5\7\7\uffff\1\2\1\3",
            "",
            "\1\15",
            "\1\15",
            "\1\22\25\uffff\3\25\4\uffff\2\22\1\23\1\17\16\uffff\1\24\1\16\1\20\1\21\3\24",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\22\25\uffff\3\25\4\uffff\2\22\1\23\1\17\17\uffff\1\16\1\20\1\21",
            "\1\22\25\uffff\3\25\4\uffff\2\22\1\23\1\17\20\uffff\1\20\1\21",
            "\1\23\6\uffff\1\26\1\uffff\2\23\11\uffff\1\23\12\uffff\1\23\17\uffff\1\23\1\uffff\3\23\26\uffff\1\23",
            "\1\31\72\uffff\1\27\1\30",
            "\1\31\72\uffff\1\27\1\30",
            "",
            "",
            "",
            "",
            "\1\22\25\uffff\3\25\4\uffff\2\22\2\23\20\uffff\1\20\1\21",
            "\1\31",
            "\1\31",
            "\1\22\25\uffff\3\25\4\uffff\2\22\2\23"
    };

    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final char[] dfa_42 = DFA.unpackEncodedStringToUnsignedChars(dfa_42s);
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[][] dfa_46 = unpackEncodedStringArray(dfa_46s);

    class DFA47 extends DFA {

        public DFA47(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 47;
            this.eot = dfa_40;
            this.eof = dfa_41;
            this.min = dfa_42;
            this.max = dfa_43;
            this.accept = dfa_44;
            this.special = dfa_45;
            this.transition = dfa_46;
        }
        public String getDescription() {
            return "2332:2: ( ( ( (lv_boolean_0_0= 'true' ) ) | ( (lv_boolean_1_0= 'false' ) ) ) | ( (lv_num_2_0= ruleNumber ) ) | ( (lv_string_3_0= RULE_STRING ) ) | ( (lv_observable_4_0= RULE_OBSERVABLE ) ) | ( ( (lv_op_5_0= ruleREL_OPERATOR ) ) ( (lv_expression_6_0= ruleNumber ) ) ) | ( ( (lv_int0_7_0= ruleNumber ) ) ( ( (lv_leftLimit_8_0= 'inclusive' ) ) | otherlv_9= 'exclusive' )? ( ( 'to' )=>otherlv_10= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_11_0= ruleNumber ) ) ( ( (lv_rightLimit_12_0= 'inclusive' ) ) | otherlv_13= 'exclusive' )? ) | (otherlv_14= 'in' ( (lv_set_15_0= ruleList ) ) ) | ( (lv_quantity_16_0= ruleQuantity ) ) | ( (lv_date_17_0= ruleDate ) ) | ( (lv_expr_18_0= RULE_EXPR ) ) | ( (lv_nodata_19_0= 'unknown' ) ) | ( (lv_star_20_0= '*' ) ) | ( (lv_anything_21_0= '#' ) ) )";
        }
    }
    static final String dfa_47s = "\3\uffff\1\16\2\uffff\2\16\7\uffff\1\16\2\uffff\1\16\3\uffff";
    static final String dfa_48s = "\1\6\2\13\1\4\2\uffff\2\4\3\13\2\uffff\1\13\1\uffff\1\4\2\13\2\4\1\13\1\0";
    static final String dfa_49s = "\1\107\2\13\1\115\2\uffff\2\112\1\13\2\107\2\uffff\1\13\1\uffff\1\112\2\13\1\107\1\112\1\13\1\0";
    static final String dfa_50s = "\4\uffff\1\3\1\5\5\uffff\1\2\1\4\1\uffff\1\1\7\uffff";
    static final String dfa_51s = "\25\uffff\1\0}>";
    static final String[] dfa_52s = {
            "\1\4\4\uffff\1\3\40\uffff\2\5\30\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\1\16\1\uffff\4\16\1\uffff\1\16\14\uffff\1\16\12\uffff\2\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\3\uffff\1\10\14\uffff\2\16\1\15\1\7\1\11\1\12\3\14",
            "",
            "",
            "\1\16\1\uffff\4\16\1\uffff\1\16\14\uffff\1\16\12\uffff\2\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\3\uffff\1\10\14\uffff\3\16\1\7\1\11\1\12",
            "\1\16\1\uffff\4\16\1\uffff\1\16\14\uffff\1\16\12\uffff\2\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\3\uffff\1\10\14\uffff\3\16\1\uffff\1\11\1\12",
            "\1\17",
            "\1\22\72\uffff\1\20\1\21",
            "\1\22\72\uffff\1\20\1\21",
            "",
            "",
            "\1\23",
            "",
            "\1\16\1\uffff\4\16\1\uffff\1\16\14\uffff\1\16\12\uffff\2\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\20\uffff\3\16\1\uffff\1\11\1\12",
            "\1\22",
            "\1\22",
            "\1\16\1\uffff\4\16\1\uffff\1\16\14\uffff\1\16\12\uffff\2\16\2\uffff\1\16\2\uffff\4\16\2\uffff\1\13\3\uffff\1\16\20\uffff\3\16",
            "\1\16\1\uffff\4\16\1\uffff\1\16\27\uffff\2\16\2\uffff\1\16\2\uffff\1\16\1\uffff\2\16\2\uffff\1\16\3\uffff\1\16\3\uffff\1\16\15\uffff\1\16\1\24\3\16",
            "\1\25",
            "\1\uffff"
    };
    static final short[] dfa_47 = DFA.unpackEncodedString(dfa_47s);
    static final char[] dfa_48 = DFA.unpackEncodedStringToUnsignedChars(dfa_48s);
    static final char[] dfa_49 = DFA.unpackEncodedStringToUnsignedChars(dfa_49s);
    static final short[] dfa_50 = DFA.unpackEncodedString(dfa_50s);
    static final short[] dfa_51 = DFA.unpackEncodedString(dfa_51s);
    static final short[][] dfa_52 = unpackEncodedStringArray(dfa_52s);

    class DFA57 extends DFA {

        public DFA57(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 57;
            this.eot = dfa_28;
            this.eof = dfa_47;
            this.min = dfa_48;
            this.max = dfa_49;
            this.accept = dfa_50;
            this.special = dfa_51;
            this.transition = dfa_52;
        }
        public String getDescription() {
            return "3197:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA57_21 = input.LA(1);

                         
                        int index57_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred109_InternalKactors()) ) {s = 14;}

                        else if ( (synpred112_InternalKactors()) ) {s = 12;}

                         
                        input.seek(index57_21);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 57, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_53s = "\36\uffff";
    static final String dfa_54s = "\1\2\35\uffff";
    static final String dfa_55s = "\1\4\1\0\34\uffff";
    static final String dfa_56s = "\1\107\1\0\34\uffff";
    static final String dfa_57s = "\2\uffff\1\2\32\uffff\1\1";
    static final String dfa_58s = "\1\uffff\1\0\34\uffff}>";
    static final String[] dfa_59s = {
            "\1\2\1\uffff\1\2\1\uffff\4\2\3\uffff\2\2\7\uffff\1\2\10\uffff\2\2\1\1\1\2\3\uffff\1\2\3\uffff\2\2\3\uffff\3\2\13\uffff\6\2\1\uffff\2\2",
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
            ""
    };

    static final short[] dfa_53 = DFA.unpackEncodedString(dfa_53s);
    static final short[] dfa_54 = DFA.unpackEncodedString(dfa_54s);
    static final char[] dfa_55 = DFA.unpackEncodedStringToUnsignedChars(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final short[] dfa_57 = DFA.unpackEncodedString(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[][] dfa_59 = unpackEncodedStringArray(dfa_59s);

    class DFA70 extends DFA {

        public DFA70(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 70;
            this.eot = dfa_53;
            this.eof = dfa_54;
            this.min = dfa_55;
            this.max = dfa_56;
            this.accept = dfa_57;
            this.special = dfa_58;
            this.transition = dfa_59;
        }
        public String getDescription() {
            return "4091:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA70_1 = input.LA(1);

                         
                        int index70_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred130_InternalKactors()) ) {s = 29;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index70_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 70, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_60s = "\27\uffff";
    static final String dfa_61s = "\1\4\6\0\20\uffff";
    static final String dfa_62s = "\1\107\6\0\20\uffff";
    static final String dfa_63s = "\7\uffff\1\3\14\uffff\1\1\1\2\1\4";
    static final String dfa_64s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\20\uffff}>";
    static final String[] dfa_65s = {
            "\1\1\1\uffff\1\7\1\uffff\2\7\1\uffff\1\7\3\uffff\1\2\1\7\22\uffff\1\4\4\uffff\1\7\3\uffff\2\7\3\uffff\3\7\13\uffff\1\3\2\uffff\1\5\1\6\2\uffff\2\7",
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
            ""
    };

    static final short[] dfa_60 = DFA.unpackEncodedString(dfa_60s);
    static final char[] dfa_61 = DFA.unpackEncodedStringToUnsignedChars(dfa_61s);
    static final char[] dfa_62 = DFA.unpackEncodedStringToUnsignedChars(dfa_62s);
    static final short[] dfa_63 = DFA.unpackEncodedString(dfa_63s);
    static final short[] dfa_64 = DFA.unpackEncodedString(dfa_64s);
    static final short[][] dfa_65 = unpackEncodedStringArray(dfa_65s);

    class DFA74 extends DFA {

        public DFA74(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 74;
            this.eot = dfa_60;
            this.eof = dfa_60;
            this.min = dfa_61;
            this.max = dfa_62;
            this.accept = dfa_63;
            this.special = dfa_64;
            this.transition = dfa_65;
        }
        public String getDescription() {
            return "4170:2: ( ( ( (lv_sequence_0_0= ruleStatement ) ) (otherlv_1= ',' ( (lv_sequence_2_0= ruleStatement ) ) )* ) | ( (lv_body_3_0= ruleBody ) ) | ( (lv_match_4_0= ruleMatch ) ) | (otherlv_5= '(' ( (lv_matches_6_0= ruleMatch ) ) ( (lv_matches_7_0= ruleMatch ) )* otherlv_8= ')' ) )";
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
                        if ( (synpred134_InternalKactors()) ) {s = 20;}

                        else if ( (synpred135_InternalKactors()) ) {s = 21;}

                        else if ( (synpred136_InternalKactors()) ) {s = 7;}

                         
                        input.seek(index74_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA74_2 = input.LA(1);

                         
                        int index74_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred134_InternalKactors()) ) {s = 20;}

                        else if ( (synpred135_InternalKactors()) ) {s = 21;}

                         
                        input.seek(index74_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA74_3 = input.LA(1);

                         
                        int index74_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred134_InternalKactors()) ) {s = 20;}

                        else if ( (synpred135_InternalKactors()) ) {s = 21;}

                         
                        input.seek(index74_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA74_4 = input.LA(1);

                         
                        int index74_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred134_InternalKactors()) ) {s = 20;}

                        else if ( (synpred135_InternalKactors()) ) {s = 21;}

                        else if ( (synpred136_InternalKactors()) ) {s = 7;}

                        else if ( (true) ) {s = 22;}

                         
                        input.seek(index74_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA74_5 = input.LA(1);

                         
                        int index74_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred134_InternalKactors()) ) {s = 20;}

                        else if ( (synpred135_InternalKactors()) ) {s = 21;}

                         
                        input.seek(index74_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA74_6 = input.LA(1);

                         
                        int index74_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred134_InternalKactors()) ) {s = 20;}

                        else if ( (synpred135_InternalKactors()) ) {s = 21;}

                         
                        input.seek(index74_6);
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
    static final String dfa_66s = "\24\uffff";
    static final String dfa_67s = "\1\4\3\uffff\4\0\14\uffff";
    static final String dfa_68s = "\1\107\3\uffff\4\0\14\uffff";
    static final String dfa_69s = "\1\uffff\1\1\1\2\1\3\4\uffff\1\4\1\uffff\1\6\1\10\1\13\1\14\1\15\1\16\1\7\1\11\1\12\1\5";
    static final String dfa_70s = "\4\uffff\1\0\1\1\1\2\1\3\14\uffff}>";
    static final String[] dfa_71s = {
            "\1\1\1\uffff\1\7\1\uffff\1\3\1\14\1\uffff\1\6\4\uffff\1\2\22\uffff\1\12\4\uffff\1\17\3\uffff\2\10\3\uffff\1\13\1\15\1\16\22\uffff\1\4\1\5",
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

    static final short[] dfa_66 = DFA.unpackEncodedString(dfa_66s);
    static final char[] dfa_67 = DFA.unpackEncodedStringToUnsignedChars(dfa_67s);
    static final char[] dfa_68 = DFA.unpackEncodedStringToUnsignedChars(dfa_68s);
    static final short[] dfa_69 = DFA.unpackEncodedString(dfa_69s);
    static final short[] dfa_70 = DFA.unpackEncodedString(dfa_70s);
    static final short[][] dfa_71 = unpackEncodedStringArray(dfa_71s);

    class DFA77 extends DFA {

        public DFA77(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 77;
            this.eot = dfa_66;
            this.eof = dfa_66;
            this.min = dfa_67;
            this.max = dfa_68;
            this.accept = dfa_69;
            this.special = dfa_70;
            this.transition = dfa_71;
        }
        public String getDescription() {
            return "4324:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) | ( ( (lv_int0_18_0= ruleNumber ) ) ( ( (lv_leftLimit_19_0= 'inclusive' ) ) | otherlv_20= 'exclusive' )? ( ( 'to' )=>otherlv_21= 'to' ) ( ( ( ruleNumber ) )=> (lv_int1_22_0= ruleNumber ) ) ( ( (lv_rightLimit_23_0= 'inclusive' ) ) | otherlv_24= 'exclusive' )? otherlv_25= '->' ( (lv_body_26_0= ruleBody ) ) ) | (otherlv_27= 'in' ( (lv_set_28_0= ruleList ) ) otherlv_29= '->' ( (lv_body_30_0= ruleBody ) ) ) | ( ( (lv_quantity_31_0= ruleQuantity ) ) otherlv_32= '->' ( (lv_body_33_0= ruleBody ) ) ) | ( ( (lv_date_34_0= ruleDate ) ) otherlv_35= '->' ( (lv_body_36_0= ruleBody ) ) ) | ( ( (lv_expr_37_0= RULE_EXPR ) ) otherlv_38= '->' ( (lv_body_39_0= ruleBody ) ) ) | ( ( (lv_nodata_40_0= 'unknown' ) ) otherlv_41= '->' ( (lv_body_42_0= ruleBody ) ) ) | ( ( (lv_star_43_0= '*' ) ) otherlv_44= '->' ( (lv_body_45_0= ruleBody ) ) ) | ( ( (lv_anything_46_0= '#' ) ) otherlv_47= '->' ( (lv_body_48_0= ruleBody ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA77_4 = input.LA(1);

                         
                        int index77_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred141_InternalKactors()) ) {s = 8;}

                        else if ( (synpred150_InternalKactors()) ) {s = 16;}

                        else if ( (synpred152_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index77_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA77_5 = input.LA(1);

                         
                        int index77_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred141_InternalKactors()) ) {s = 8;}

                        else if ( (synpred150_InternalKactors()) ) {s = 16;}

                        else if ( (synpred152_InternalKactors()) ) {s = 17;}

                         
                        input.seek(index77_5);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA77_6 = input.LA(1);

                         
                        int index77_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred141_InternalKactors()) ) {s = 8;}

                        else if ( (synpred150_InternalKactors()) ) {s = 16;}

                        else if ( (synpred152_InternalKactors()) ) {s = 17;}

                        else if ( (synpred153_InternalKactors()) ) {s = 18;}

                         
                        input.seek(index77_6);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA77_7 = input.LA(1);

                         
                        int index77_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred141_InternalKactors()) ) {s = 8;}

                        else if ( (synpred142_InternalKactors()) ) {s = 19;}

                         
                        input.seek(index77_7);
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
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000200000402L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000001FE800002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x00000001FF800002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000070L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000001FE800042L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000200000400L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x8000000800008010L,0x000000000000000CL});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x8000000800008012L,0x000000000000000CL});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000001000000010L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000001001000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0010348800000BD0L,0x00000000000000C0L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0010349800000BD0L,0x00000000000000C0L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000004810L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000010400000002L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000004010L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x7C0E384000000950L,0x00000000000000C0L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000080001000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x7C0E304000000950L,0x00000000000000C0L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0001C00000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000800L,0x00000000000000C0L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000C00000000002L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x7C2E314000000B50L,0x00000000000000C0L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x7C0E314000000B50L,0x00000000000000C0L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0180000000000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0088000800006010L,0x0000000000004000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0088001800006010L,0x0000000000004000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0088000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000800006010L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x8000001800008010L,0x000000000000000CL});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000800000010L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000C00000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000400000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x800E310800018B50L,0x00000000000000CCL});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x000E310800010B50L,0x00000000000000C0L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x000E311800010B50L,0x00000000000000C0L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000C00000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0100000000000002L,0x0000000000000700L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0100000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000000003880L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0180000000000002L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0100000000004012L,0x0000000000000080L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000004012L,0x0000000000000080L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000004012L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000042L});

}
