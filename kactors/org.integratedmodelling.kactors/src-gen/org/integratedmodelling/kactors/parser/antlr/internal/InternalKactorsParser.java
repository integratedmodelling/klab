package org.integratedmodelling.kactors.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_LOWERCASE_ID", "RULE_STRING", "RULE_ARGVALUE", "RULE_OBSERVABLE", "RULE_EXPR", "RULE_EMBEDDEDTEXT", "RULE_REGEXP", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'name'", "'worldview'", "'permissions'", "'author'", "'version'", "'def'", "':'", "'('", "','", "')'", "'=?'", "'='", "'to'", "'true'", "'false'", "'if'", "'else'", "';'", "'->'", "'+'", "'-'", "'l'", "'.'", "'e'", "'E'", "'AD'", "'CE'", "'BC'"
    };
    public static final int T__19=19;
    public static final int RULE_EMBEDDEDTEXT=9;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int RULE_ID=12;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=11;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=13;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int RULE_ARGVALUE=6;
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=14;
    public static final int RULE_OBSERVABLE=7;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_REGEXP=10;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=15;
    public static final int RULE_ANY_OTHER=16;
    public static final int RULE_LOWERCASE_ID=4;
    public static final int T__44=44;
    public static final int RULE_EXPR=8;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
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
    // InternalKactors.g:71:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalKactors.g:71:46: (iv_ruleModel= ruleModel EOF )
            // InternalKactors.g:72:2: iv_ruleModel= ruleModel EOF
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
    // InternalKactors.g:78:1: ruleModel returns [EObject current=null] : ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_preamble_1_0 = null;

        EObject lv_definitions_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:84:2: ( ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* ) )
            // InternalKactors.g:85:2: ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* )
            {
            // InternalKactors.g:85:2: ( () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )* )
            // InternalKactors.g:86:3: () ( (lv_preamble_1_0= rulePreamble ) )? ( (lv_definitions_2_0= ruleDefinition ) )*
            {
            // InternalKactors.g:86:3: ()
            // InternalKactors.g:87:4: 
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

            // InternalKactors.g:96:3: ( (lv_preamble_1_0= rulePreamble ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==17) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalKactors.g:97:4: (lv_preamble_1_0= rulePreamble )
                    {
                    // InternalKactors.g:97:4: (lv_preamble_1_0= rulePreamble )
                    // InternalKactors.g:98:5: lv_preamble_1_0= rulePreamble
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

            // InternalKactors.g:115:3: ( (lv_definitions_2_0= ruleDefinition ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==22) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKactors.g:116:4: (lv_definitions_2_0= ruleDefinition )
            	    {
            	    // InternalKactors.g:116:4: (lv_definitions_2_0= ruleDefinition )
            	    // InternalKactors.g:117:5: lv_definitions_2_0= ruleDefinition
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
    // InternalKactors.g:138:1: entryRulePreamble returns [EObject current=null] : iv_rulePreamble= rulePreamble EOF ;
    public final EObject entryRulePreamble() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePreamble = null;



        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKactors.g:142:2: (iv_rulePreamble= rulePreamble EOF )
            // InternalKactors.g:143:2: iv_rulePreamble= rulePreamble EOF
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
    // InternalKactors.g:152:1: rulePreamble returns [EObject current=null] : (otherlv_0= 'name' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* ) ) ) ) ;
    public final EObject rulePreamble() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        Token lv_worldview_4_0=null;
        Token otherlv_5=null;
        Token lv_permissions_6_0=null;
        Token otherlv_7=null;
        Token lv_authors_8_0=null;
        Token otherlv_9=null;
        Token lv_version_10_0=null;


        	enterRule();
        	UnorderedGroupState myUnorderedGroupState = getUnorderedGroupHelper().snapShot(
        	grammarAccess.getPreambleAccess().getUnorderedGroup_2()
        	);

        try {
            // InternalKactors.g:161:2: ( (otherlv_0= 'name' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* ) ) ) ) )
            // InternalKactors.g:162:2: (otherlv_0= 'name' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* ) ) ) )
            {
            // InternalKactors.g:162:2: (otherlv_0= 'name' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* ) ) ) )
            // InternalKactors.g:163:3: otherlv_0= 'name' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* ) ) )
            {
            otherlv_0=(Token)match(input,17,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getPreambleAccess().getNameKeyword_0());
              		
            }
            // InternalKactors.g:167:3: ( (lv_name_1_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:168:4: (lv_name_1_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:168:4: (lv_name_1_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:169:5: lv_name_1_0= RULE_LOWERCASE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getPreambleAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getPreambleRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:185:3: ( ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* ) ) )
            // InternalKactors.g:186:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* ) )
            {
            // InternalKactors.g:186:4: ( ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* ) )
            // InternalKactors.g:187:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* )
            {
            getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            // InternalKactors.g:190:5: ( ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )* )
            // InternalKactors.g:191:6: ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )*
            {
            // InternalKactors.g:191:6: ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )*
            loop3:
            do {
                int alt3=5;
                int LA3_0 = input.LA(1);

                if ( LA3_0 == 18 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
                    alt3=1;
                }
                else if ( LA3_0 == 19 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
                    alt3=2;
                }
                else if ( LA3_0 == 20 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
                    alt3=3;
                }
                else if ( LA3_0 == 21 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
                    alt3=4;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalKactors.g:192:4: ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:192:4: ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) )
            	    // InternalKactors.g:193:5: {...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
            	    }
            	    // InternalKactors.g:193:105: ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) )
            	    // InternalKactors.g:194:6: ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
            	    // InternalKactors.g:197:9: ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) )
            	    // InternalKactors.g:197:10: {...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:197:19: (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) )
            	    // InternalKactors.g:197:20: otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) )
            	    {
            	    otherlv_3=(Token)match(input,18,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_3, grammarAccess.getPreambleAccess().getWorldviewKeyword_2_0_0());
            	      								
            	    }
            	    // InternalKactors.g:201:9: ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) )
            	    // InternalKactors.g:202:10: (lv_worldview_4_0= RULE_LOWERCASE_ID )
            	    {
            	    // InternalKactors.g:202:10: (lv_worldview_4_0= RULE_LOWERCASE_ID )
            	    // InternalKactors.g:203:11: lv_worldview_4_0= RULE_LOWERCASE_ID
            	    {
            	    lv_worldview_4_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_worldview_4_0, grammarAccess.getPreambleAccess().getWorldviewLOWERCASE_IDTerminalRuleCall_2_0_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"worldview",
            	      												lv_worldview_4_0,
            	      												"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	      										
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
            	case 2 :
            	    // InternalKactors.g:225:4: ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:225:4: ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:226:5: {...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
            	    }
            	    // InternalKactors.g:226:105: ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:227:6: ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
            	    // InternalKactors.g:230:9: ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:230:10: {...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:230:19: (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) )
            	    // InternalKactors.g:230:20: otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) )
            	    {
            	    otherlv_5=(Token)match(input,19,FOLLOW_6); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_5, grammarAccess.getPreambleAccess().getPermissionsKeyword_2_1_0());
            	      								
            	    }
            	    // InternalKactors.g:234:9: ( (lv_permissions_6_0= RULE_STRING ) )
            	    // InternalKactors.g:235:10: (lv_permissions_6_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:235:10: (lv_permissions_6_0= RULE_STRING )
            	    // InternalKactors.g:236:11: lv_permissions_6_0= RULE_STRING
            	    {
            	    lv_permissions_6_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_permissions_6_0, grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_2_1_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"permissions",
            	      												lv_permissions_6_0,
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
            	case 3 :
            	    // InternalKactors.g:258:4: ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:258:4: ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) )
            	    // InternalKactors.g:259:5: {...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
            	    }
            	    // InternalKactors.g:259:105: ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) )
            	    // InternalKactors.g:260:6: ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
            	    // InternalKactors.g:263:9: ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) )
            	    // InternalKactors.g:263:10: {...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:263:19: (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) )
            	    // InternalKactors.g:263:20: otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) )
            	    {
            	    otherlv_7=(Token)match(input,20,FOLLOW_6); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_7, grammarAccess.getPreambleAccess().getAuthorKeyword_2_2_0());
            	      								
            	    }
            	    // InternalKactors.g:267:9: ( (lv_authors_8_0= RULE_STRING ) )
            	    // InternalKactors.g:268:10: (lv_authors_8_0= RULE_STRING )
            	    {
            	    // InternalKactors.g:268:10: (lv_authors_8_0= RULE_STRING )
            	    // InternalKactors.g:269:11: lv_authors_8_0= RULE_STRING
            	    {
            	    lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_authors_8_0, grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_2_2_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"authors",
            	      												lv_authors_8_0,
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
            	case 4 :
            	    // InternalKactors.g:291:4: ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) )
            	    {
            	    // InternalKactors.g:291:4: ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) )
            	    // InternalKactors.g:292:5: {...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
            	    }
            	    // InternalKactors.g:292:105: ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) )
            	    // InternalKactors.g:293:6: ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) )
            	    {
            	    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
            	    // InternalKactors.g:296:9: ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) )
            	    // InternalKactors.g:296:10: {...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) )
            	    {
            	    if ( !((true)) ) {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        throw new FailedPredicateException(input, "rulePreamble", "true");
            	    }
            	    // InternalKactors.g:296:19: (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) )
            	    // InternalKactors.g:296:20: otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) )
            	    {
            	    otherlv_9=(Token)match(input,21,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      									newLeafNode(otherlv_9, grammarAccess.getPreambleAccess().getVersionKeyword_2_3_0());
            	      								
            	    }
            	    // InternalKactors.g:300:9: ( (lv_version_10_0= RULE_LOWERCASE_ID ) )
            	    // InternalKactors.g:301:10: (lv_version_10_0= RULE_LOWERCASE_ID )
            	    {
            	    // InternalKactors.g:301:10: (lv_version_10_0= RULE_LOWERCASE_ID )
            	    // InternalKactors.g:302:11: lv_version_10_0= RULE_LOWERCASE_ID
            	    {
            	    lv_version_10_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      											newLeafNode(lv_version_10_0, grammarAccess.getPreambleAccess().getVersionLOWERCASE_IDTerminalRuleCall_2_3_1_0());
            	      										
            	    }
            	    if ( state.backtracking==0 ) {

            	      											if (current==null) {
            	      												current = createModelElement(grammarAccess.getPreambleRule());
            	      											}
            	      											setWithLastConsumed(
            	      												current,
            	      												"version",
            	      												lv_version_10_0,
            	      												"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
            	      										
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

            	default :
            	    break loop3;
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
    // InternalKactors.g:338:1: entryRuleDefinition returns [EObject current=null] : iv_ruleDefinition= ruleDefinition EOF ;
    public final EObject entryRuleDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinition = null;


        try {
            // InternalKactors.g:338:51: (iv_ruleDefinition= ruleDefinition EOF )
            // InternalKactors.g:339:2: iv_ruleDefinition= ruleDefinition EOF
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
    // InternalKactors.g:345:1: ruleDefinition returns [EObject current=null] : (otherlv_0= 'def' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_2_0= ruleArgumentDeclaration ) )? otherlv_3= ':' ( (lv_body_4_0= ruleBody ) ) ) ;
    public final EObject ruleDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_3=null;
        EObject lv_arguments_2_0 = null;

        EObject lv_body_4_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:351:2: ( (otherlv_0= 'def' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_2_0= ruleArgumentDeclaration ) )? otherlv_3= ':' ( (lv_body_4_0= ruleBody ) ) ) )
            // InternalKactors.g:352:2: (otherlv_0= 'def' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_2_0= ruleArgumentDeclaration ) )? otherlv_3= ':' ( (lv_body_4_0= ruleBody ) ) )
            {
            // InternalKactors.g:352:2: (otherlv_0= 'def' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_2_0= ruleArgumentDeclaration ) )? otherlv_3= ':' ( (lv_body_4_0= ruleBody ) ) )
            // InternalKactors.g:353:3: otherlv_0= 'def' ( (lv_name_1_0= RULE_LOWERCASE_ID ) ) ( (lv_arguments_2_0= ruleArgumentDeclaration ) )? otherlv_3= ':' ( (lv_body_4_0= ruleBody ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getDefinitionAccess().getDefKeyword_0());
              		
            }
            // InternalKactors.g:357:3: ( (lv_name_1_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:358:4: (lv_name_1_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:358:4: (lv_name_1_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:359:5: lv_name_1_0= RULE_LOWERCASE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:375:3: ( (lv_arguments_2_0= ruleArgumentDeclaration ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==24) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalKactors.g:376:4: (lv_arguments_2_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:376:4: (lv_arguments_2_0= ruleArgumentDeclaration )
                    // InternalKactors.g:377:5: lv_arguments_2_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_8);
                    lv_arguments_2_0=ruleArgumentDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getDefinitionRule());
                      					}
                      					set(
                      						current,
                      						"arguments",
                      						lv_arguments_2_0,
                      						"org.integratedmodelling.kactors.Kactors.ArgumentDeclaration");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,23,FOLLOW_9); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getDefinitionAccess().getColonKeyword_3());
              		
            }
            // InternalKactors.g:398:3: ( (lv_body_4_0= ruleBody ) )
            // InternalKactors.g:399:4: (lv_body_4_0= ruleBody )
            {
            // InternalKactors.g:399:4: (lv_body_4_0= ruleBody )
            // InternalKactors.g:400:5: lv_body_4_0= ruleBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDefinitionAccess().getBodyBodyParserRuleCall_4_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_body_4_0=ruleBody();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDefinitionRule());
              					}
              					set(
              						current,
              						"body",
              						lv_body_4_0,
              						"org.integratedmodelling.kactors.Kactors.Body");
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
    // InternalKactors.g:421:1: entryRuleArgumentDeclaration returns [EObject current=null] : iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF ;
    public final EObject entryRuleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDeclaration = null;


        try {
            // InternalKactors.g:421:60: (iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF )
            // InternalKactors.g:422:2: iv_ruleArgumentDeclaration= ruleArgumentDeclaration EOF
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
    // InternalKactors.g:428:1: ruleArgumentDeclaration returns [EObject current=null] : ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleArgumentDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_ids_2_0=null;
        Token otherlv_3=null;
        Token lv_ids_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalKactors.g:434:2: ( ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' ) )
            // InternalKactors.g:435:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            {
            // InternalKactors.g:435:2: ( () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')' )
            // InternalKactors.g:436:3: () otherlv_1= '(' ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )? otherlv_5= ')'
            {
            // InternalKactors.g:436:3: ()
            // InternalKactors.g:437:4: 
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

            otherlv_1=(Token)match(input,24,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1());
              		
            }
            // InternalKactors.g:450:3: ( ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_LOWERCASE_ID) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalKactors.g:451:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) ) (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    {
                    // InternalKactors.g:451:4: ( (lv_ids_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:452:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:452:5: (lv_ids_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:453:6: lv_ids_2_0= RULE_LOWERCASE_ID
                    {
                    lv_ids_2_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_11); if (state.failed) return current;
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

                    // InternalKactors.g:469:4: (otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==25) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalKactors.g:470:5: otherlv_3= ',' ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    {
                    	    otherlv_3=(Token)match(input,25,FOLLOW_4); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_3, grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0());
                    	      				
                    	    }
                    	    // InternalKactors.g:474:5: ( (lv_ids_4_0= RULE_LOWERCASE_ID ) )
                    	    // InternalKactors.g:475:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    {
                    	    // InternalKactors.g:475:6: (lv_ids_4_0= RULE_LOWERCASE_ID )
                    	    // InternalKactors.g:476:7: lv_ids_4_0= RULE_LOWERCASE_ID
                    	    {
                    	    lv_ids_4_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_11); if (state.failed) return current;
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
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,26,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:502:1: entryRuleParameterList returns [EObject current=null] : iv_ruleParameterList= ruleParameterList EOF ;
    public final EObject entryRuleParameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterList = null;


        try {
            // InternalKactors.g:502:54: (iv_ruleParameterList= ruleParameterList EOF )
            // InternalKactors.g:503:2: iv_ruleParameterList= ruleParameterList EOF
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
    // InternalKactors.g:509:1: ruleParameterList returns [EObject current=null] : ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) ;
    public final EObject ruleParameterList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_pairs_0_0 = null;

        EObject lv_pairs_2_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:515:2: ( ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* ) )
            // InternalKactors.g:516:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            {
            // InternalKactors.g:516:2: ( ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )* )
            // InternalKactors.g:517:3: ( (lv_pairs_0_0= ruleKeyValuePair ) ) ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            {
            // InternalKactors.g:517:3: ( (lv_pairs_0_0= ruleKeyValuePair ) )
            // InternalKactors.g:518:4: (lv_pairs_0_0= ruleKeyValuePair )
            {
            // InternalKactors.g:518:4: (lv_pairs_0_0= ruleKeyValuePair )
            // InternalKactors.g:519:5: lv_pairs_0_0= ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
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

            // InternalKactors.g:536:3: ( ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==25) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKactors.g:537:4: ( ( ',' )=>otherlv_1= ',' ) ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    {
            	    // InternalKactors.g:537:4: ( ( ',' )=>otherlv_1= ',' )
            	    // InternalKactors.g:538:5: ( ',' )=>otherlv_1= ','
            	    {
            	    otherlv_1=(Token)match(input,25,FOLLOW_13); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(otherlv_1, grammarAccess.getParameterListAccess().getCommaKeyword_1_0());
            	      				
            	    }

            	    }

            	    // InternalKactors.g:544:4: ( (lv_pairs_2_0= ruleKeyValuePair ) )
            	    // InternalKactors.g:545:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    {
            	    // InternalKactors.g:545:5: (lv_pairs_2_0= ruleKeyValuePair )
            	    // InternalKactors.g:546:6: lv_pairs_2_0= ruleKeyValuePair
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_12);
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
        }
        return current;
    }
    // $ANTLR end "ruleParameterList"


    // $ANTLR start "entryRuleKeyValuePair"
    // InternalKactors.g:568:1: entryRuleKeyValuePair returns [EObject current=null] : iv_ruleKeyValuePair= ruleKeyValuePair EOF ;
    public final EObject entryRuleKeyValuePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyValuePair = null;


        try {
            // InternalKactors.g:568:53: (iv_ruleKeyValuePair= ruleKeyValuePair EOF )
            // InternalKactors.g:569:2: iv_ruleKeyValuePair= ruleKeyValuePair EOF
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
    // InternalKactors.g:575:1: ruleKeyValuePair returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) ;
    public final EObject ruleKeyValuePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token lv_interactive_1_0=null;
        Token otherlv_2=null;
        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:581:2: ( ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) ) )
            // InternalKactors.g:582:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            {
            // InternalKactors.g:582:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) ) )
            // InternalKactors.g:583:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )? ( (lv_value_3_0= ruleValue ) )
            {
            // InternalKactors.g:583:3: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_LOWERCASE_ID) ) {
                int LA9_1 = input.LA(2);

                if ( ((LA9_1>=27 && LA9_1<=28)) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // InternalKactors.g:584:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    {
                    // InternalKactors.g:584:4: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:585:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:585:5: (lv_name_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:586:6: lv_name_0_0= RULE_LOWERCASE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_14); if (state.failed) return current;
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

                    // InternalKactors.g:602:4: ( ( (lv_interactive_1_0= '=?' ) ) | otherlv_2= '=' )
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==27) ) {
                        alt8=1;
                    }
                    else if ( (LA8_0==28) ) {
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
                            // InternalKactors.g:603:5: ( (lv_interactive_1_0= '=?' ) )
                            {
                            // InternalKactors.g:603:5: ( (lv_interactive_1_0= '=?' ) )
                            // InternalKactors.g:604:6: (lv_interactive_1_0= '=?' )
                            {
                            // InternalKactors.g:604:6: (lv_interactive_1_0= '=?' )
                            // InternalKactors.g:605:7: lv_interactive_1_0= '=?'
                            {
                            lv_interactive_1_0=(Token)match(input,27,FOLLOW_13); if (state.failed) return current;
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
                            // InternalKactors.g:618:5: otherlv_2= '='
                            {
                            otherlv_2=(Token)match(input,28,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_2, grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1());
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalKactors.g:624:3: ( (lv_value_3_0= ruleValue ) )
            // InternalKactors.g:625:4: (lv_value_3_0= ruleValue )
            {
            // InternalKactors.g:625:4: (lv_value_3_0= ruleValue )
            // InternalKactors.g:626:5: lv_value_3_0= ruleValue
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
    // InternalKactors.g:647:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalKactors.g:647:46: (iv_ruleValue= ruleValue EOF )
            // InternalKactors.g:648:2: iv_ruleValue= ruleValue EOF
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
    // InternalKactors.g:654:1: ruleValue returns [EObject current=null] : ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_observable_3_0= RULE_OBSERVABLE ) ) | ( (lv_expression_4_0= RULE_EXPR ) ) ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        Token lv_argvalue_0_0=null;
        Token lv_id_2_0=null;
        Token lv_observable_3_0=null;
        Token lv_expression_4_0=null;
        EObject lv_literal_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:660:2: ( ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_observable_3_0= RULE_OBSERVABLE ) ) | ( (lv_expression_4_0= RULE_EXPR ) ) ) )
            // InternalKactors.g:661:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_observable_3_0= RULE_OBSERVABLE ) ) | ( (lv_expression_4_0= RULE_EXPR ) ) )
            {
            // InternalKactors.g:661:2: ( ( (lv_argvalue_0_0= RULE_ARGVALUE ) ) | ( (lv_literal_1_0= ruleLiteral ) ) | ( (lv_id_2_0= RULE_LOWERCASE_ID ) ) | ( (lv_observable_3_0= RULE_OBSERVABLE ) ) | ( (lv_expression_4_0= RULE_EXPR ) ) )
            int alt10=5;
            switch ( input.LA(1) ) {
            case RULE_ARGVALUE:
                {
                alt10=1;
                }
                break;
            case RULE_STRING:
            case RULE_INT:
            case 30:
            case 31:
            case 36:
            case 37:
                {
                alt10=2;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt10=3;
                }
                break;
            case RULE_OBSERVABLE:
                {
                alt10=4;
                }
                break;
            case RULE_EXPR:
                {
                alt10=5;
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
                    // InternalKactors.g:662:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    {
                    // InternalKactors.g:662:3: ( (lv_argvalue_0_0= RULE_ARGVALUE ) )
                    // InternalKactors.g:663:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    {
                    // InternalKactors.g:663:4: (lv_argvalue_0_0= RULE_ARGVALUE )
                    // InternalKactors.g:664:5: lv_argvalue_0_0= RULE_ARGVALUE
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
                    // InternalKactors.g:681:3: ( (lv_literal_1_0= ruleLiteral ) )
                    {
                    // InternalKactors.g:681:3: ( (lv_literal_1_0= ruleLiteral ) )
                    // InternalKactors.g:682:4: (lv_literal_1_0= ruleLiteral )
                    {
                    // InternalKactors.g:682:4: (lv_literal_1_0= ruleLiteral )
                    // InternalKactors.g:683:5: lv_literal_1_0= ruleLiteral
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
                    // InternalKactors.g:701:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
                    {
                    // InternalKactors.g:701:3: ( (lv_id_2_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:702:4: (lv_id_2_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:702:4: (lv_id_2_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:703:5: lv_id_2_0= RULE_LOWERCASE_ID
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
                    // InternalKactors.g:720:3: ( (lv_observable_3_0= RULE_OBSERVABLE ) )
                    {
                    // InternalKactors.g:720:3: ( (lv_observable_3_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:721:4: (lv_observable_3_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:721:4: (lv_observable_3_0= RULE_OBSERVABLE )
                    // InternalKactors.g:722:5: lv_observable_3_0= RULE_OBSERVABLE
                    {
                    lv_observable_3_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_observable_3_0, grammarAccess.getValueAccess().getObservableOBSERVABLETerminalRuleCall_3_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"observable",
                      						lv_observable_3_0,
                      						"org.integratedmodelling.kactors.Kactors.OBSERVABLE");
                      				
                    }

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalKactors.g:739:3: ( (lv_expression_4_0= RULE_EXPR ) )
                    {
                    // InternalKactors.g:739:3: ( (lv_expression_4_0= RULE_EXPR ) )
                    // InternalKactors.g:740:4: (lv_expression_4_0= RULE_EXPR )
                    {
                    // InternalKactors.g:740:4: (lv_expression_4_0= RULE_EXPR )
                    // InternalKactors.g:741:5: lv_expression_4_0= RULE_EXPR
                    {
                    lv_expression_4_0=(Token)match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(lv_expression_4_0, grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_4_0());
                      				
                    }
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElement(grammarAccess.getValueRule());
                      					}
                      					setWithLastConsumed(
                      						current,
                      						"expression",
                      						lv_expression_4_0,
                      						"org.integratedmodelling.kactors.Kactors.EXPR");
                      				
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


    // $ANTLR start "entryRuleLiteral"
    // InternalKactors.g:761:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalKactors.g:761:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalKactors.g:762:2: iv_ruleLiteral= ruleLiteral EOF
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
    // InternalKactors.g:768:1: ruleLiteral returns [EObject current=null] : ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) ;
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
            // InternalKactors.g:774:2: ( ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) ) )
            // InternalKactors.g:775:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            {
            // InternalKactors.g:775:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )
            int alt12=5;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // InternalKactors.g:776:3: ( (lv_number_0_0= ruleNumber ) )
                    {
                    // InternalKactors.g:776:3: ( (lv_number_0_0= ruleNumber ) )
                    // InternalKactors.g:777:4: (lv_number_0_0= ruleNumber )
                    {
                    // InternalKactors.g:777:4: (lv_number_0_0= ruleNumber )
                    // InternalKactors.g:778:5: lv_number_0_0= ruleNumber
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
                    // InternalKactors.g:796:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    {
                    // InternalKactors.g:796:3: ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) )
                    // InternalKactors.g:797:4: ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) )
                    {
                    // InternalKactors.g:797:4: ( (lv_from_1_0= ruleNumber ) )
                    // InternalKactors.g:798:5: (lv_from_1_0= ruleNumber )
                    {
                    // InternalKactors.g:798:5: (lv_from_1_0= ruleNumber )
                    // InternalKactors.g:799:6: lv_from_1_0= ruleNumber
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
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

                    otherlv_2=(Token)match(input,29,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getLiteralAccess().getToKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:820:4: ( (lv_to_3_0= ruleNumber ) )
                    // InternalKactors.g:821:5: (lv_to_3_0= ruleNumber )
                    {
                    // InternalKactors.g:821:5: (lv_to_3_0= ruleNumber )
                    // InternalKactors.g:822:6: lv_to_3_0= ruleNumber
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
                    // InternalKactors.g:841:3: ( (lv_string_4_0= RULE_STRING ) )
                    {
                    // InternalKactors.g:841:3: ( (lv_string_4_0= RULE_STRING ) )
                    // InternalKactors.g:842:4: (lv_string_4_0= RULE_STRING )
                    {
                    // InternalKactors.g:842:4: (lv_string_4_0= RULE_STRING )
                    // InternalKactors.g:843:5: lv_string_4_0= RULE_STRING
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
                    // InternalKactors.g:860:3: ( (lv_date_5_0= ruleDate ) )
                    {
                    // InternalKactors.g:860:3: ( (lv_date_5_0= ruleDate ) )
                    // InternalKactors.g:861:4: (lv_date_5_0= ruleDate )
                    {
                    // InternalKactors.g:861:4: (lv_date_5_0= ruleDate )
                    // InternalKactors.g:862:5: lv_date_5_0= ruleDate
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
                    // InternalKactors.g:880:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    {
                    // InternalKactors.g:880:3: ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) )
                    // InternalKactors.g:881:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    {
                    // InternalKactors.g:881:4: ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) )
                    // InternalKactors.g:882:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    {
                    // InternalKactors.g:882:5: (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' )
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==30) ) {
                        alt11=1;
                    }
                    else if ( (LA11_0==31) ) {
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
                            // InternalKactors.g:883:6: lv_boolean_6_1= 'true'
                            {
                            lv_boolean_6_1=(Token)match(input,30,FOLLOW_2); if (state.failed) return current;
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
                            // InternalKactors.g:894:6: lv_boolean_6_2= 'false'
                            {
                            lv_boolean_6_2=(Token)match(input,31,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:911:1: entryRuleBody returns [EObject current=null] : iv_ruleBody= ruleBody EOF ;
    public final EObject entryRuleBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBody = null;


        try {
            // InternalKactors.g:911:45: (iv_ruleBody= ruleBody EOF )
            // InternalKactors.g:912:2: iv_ruleBody= ruleBody EOF
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
    // InternalKactors.g:918:1: ruleBody returns [EObject current=null] : ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' ) ) ;
    public final EObject ruleBody() throws RecognitionException {
        EObject current = null;

        Token lv_isgroup_3_0=null;
        Token otherlv_6=null;
        EObject lv_list_1_0 = null;

        EObject lv_list_2_0 = null;

        EObject lv_group_4_0 = null;

        EObject lv_group_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:924:2: ( ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' ) ) )
            // InternalKactors.g:925:2: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' ) )
            {
            // InternalKactors.g:925:2: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) | ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_LOWERCASE_ID||LA16_0==RULE_EMBEDDEDTEXT||LA16_0==32) ) {
                alt16=1;
            }
            else if ( (LA16_0==24) ) {
                int LA16_4 = input.LA(2);

                if ( (synpred24_InternalKactors()) ) {
                    alt16=1;
                }
                else if ( (true) ) {
                    alt16=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 4, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalKactors.g:926:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
                    {
                    // InternalKactors.g:926:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
                    // InternalKactors.g:927:4: () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )*
                    {
                    // InternalKactors.g:927:4: ()
                    // InternalKactors.g:928:5: 
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

                    // InternalKactors.g:937:4: ( (lv_list_1_0= ruleStatement ) )
                    // InternalKactors.g:938:5: (lv_list_1_0= ruleStatement )
                    {
                    // InternalKactors.g:938:5: (lv_list_1_0= ruleStatement )
                    // InternalKactors.g:939:6: lv_list_1_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0());
                      					
                    }
                    pushFollow(FOLLOW_17);
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

                    // InternalKactors.g:956:4: ( (lv_list_2_0= ruleStatement ) )*
                    loop13:
                    do {
                        int alt13=2;
                        switch ( input.LA(1) ) {
                        case RULE_LOWERCASE_ID:
                            {
                            int LA13_2 = input.LA(2);

                            if ( (synpred23_InternalKactors()) ) {
                                alt13=1;
                            }


                            }
                            break;
                        case RULE_EMBEDDEDTEXT:
                            {
                            int LA13_3 = input.LA(2);

                            if ( (synpred23_InternalKactors()) ) {
                                alt13=1;
                            }


                            }
                            break;
                        case 32:
                            {
                            int LA13_4 = input.LA(2);

                            if ( (synpred23_InternalKactors()) ) {
                                alt13=1;
                            }


                            }
                            break;
                        case 24:
                            {
                            int LA13_5 = input.LA(2);

                            if ( (synpred23_InternalKactors()) ) {
                                alt13=1;
                            }


                            }
                            break;

                        }

                        switch (alt13) {
                    	case 1 :
                    	    // InternalKactors.g:957:5: (lv_list_2_0= ruleStatement )
                    	    {
                    	    // InternalKactors.g:957:5: (lv_list_2_0= ruleStatement )
                    	    // InternalKactors.g:958:6: lv_list_2_0= ruleStatement
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_17);
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
                    	    break loop13;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalKactors.g:977:3: ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' )
                    {
                    // InternalKactors.g:977:3: ( ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')' )
                    // InternalKactors.g:978:4: ( (lv_isgroup_3_0= '(' ) ) ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )? otherlv_6= ')'
                    {
                    // InternalKactors.g:978:4: ( (lv_isgroup_3_0= '(' ) )
                    // InternalKactors.g:979:5: (lv_isgroup_3_0= '(' )
                    {
                    // InternalKactors.g:979:5: (lv_isgroup_3_0= '(' )
                    // InternalKactors.g:980:6: lv_isgroup_3_0= '('
                    {
                    lv_isgroup_3_0=(Token)match(input,24,FOLLOW_18); if (state.failed) return current;
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

                    // InternalKactors.g:992:4: ( ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==RULE_LOWERCASE_ID||LA15_0==RULE_EMBEDDEDTEXT||LA15_0==24||LA15_0==32) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalKactors.g:993:5: ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )*
                            {
                            // InternalKactors.g:993:5: ( (lv_group_4_0= ruleStatement ) )
                            // InternalKactors.g:994:6: (lv_group_4_0= ruleStatement )
                            {
                            // InternalKactors.g:994:6: (lv_group_4_0= ruleStatement )
                            // InternalKactors.g:995:7: lv_group_4_0= ruleStatement
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_18);
                            lv_group_4_0=ruleStatement();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getBodyRule());
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

                            // InternalKactors.g:1012:5: ( (lv_group_5_0= ruleStatement ) )*
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( (LA14_0==RULE_LOWERCASE_ID||LA14_0==RULE_EMBEDDEDTEXT||LA14_0==24||LA14_0==32) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // InternalKactors.g:1013:6: (lv_group_5_0= ruleStatement )
                            	    {
                            	    // InternalKactors.g:1013:6: (lv_group_5_0= ruleStatement )
                            	    // InternalKactors.g:1014:7: lv_group_5_0= ruleStatement
                            	    {
                            	    if ( state.backtracking==0 ) {

                            	      							newCompositeNode(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_1_0());
                            	      						
                            	    }
                            	    pushFollow(FOLLOW_18);
                            	    lv_group_5_0=ruleStatement();

                            	    state._fsp--;
                            	    if (state.failed) return current;
                            	    if ( state.backtracking==0 ) {

                            	      							if (current==null) {
                            	      								current = createModelElementForParent(grammarAccess.getBodyRule());
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
                            	    break loop14;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,26,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1041:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalKactors.g:1041:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalKactors.g:1042:2: iv_ruleStatement= ruleStatement EOF
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
    // InternalKactors.g:1048:1: ruleStatement returns [EObject current=null] : ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ) ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token lv_text_1_0=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        EObject lv_call_0_0 = null;

        EObject lv_if_2_0 = null;

        EObject lv_group_4_0 = null;

        EObject lv_group_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1054:2: ( ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ) ) )
            // InternalKactors.g:1055:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ) )
            {
            // InternalKactors.g:1055:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) ) | ( (lv_if_2_0= ruleIfStatement ) ) | (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' ) )
            int alt18=4;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
                {
                alt18=1;
                }
                break;
            case RULE_EMBEDDEDTEXT:
                {
                alt18=2;
                }
                break;
            case 32:
                {
                alt18=3;
                }
                break;
            case 24:
                {
                alt18=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalKactors.g:1056:3: ( (lv_call_0_0= ruleCall ) )
                    {
                    // InternalKactors.g:1056:3: ( (lv_call_0_0= ruleCall ) )
                    // InternalKactors.g:1057:4: (lv_call_0_0= ruleCall )
                    {
                    // InternalKactors.g:1057:4: (lv_call_0_0= ruleCall )
                    // InternalKactors.g:1058:5: lv_call_0_0= ruleCall
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
                    // InternalKactors.g:1076:3: ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) )
                    {
                    // InternalKactors.g:1076:3: ( (lv_text_1_0= RULE_EMBEDDEDTEXT ) )
                    // InternalKactors.g:1077:4: (lv_text_1_0= RULE_EMBEDDEDTEXT )
                    {
                    // InternalKactors.g:1077:4: (lv_text_1_0= RULE_EMBEDDEDTEXT )
                    // InternalKactors.g:1078:5: lv_text_1_0= RULE_EMBEDDEDTEXT
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
                    // InternalKactors.g:1095:3: ( (lv_if_2_0= ruleIfStatement ) )
                    {
                    // InternalKactors.g:1095:3: ( (lv_if_2_0= ruleIfStatement ) )
                    // InternalKactors.g:1096:4: (lv_if_2_0= ruleIfStatement )
                    {
                    // InternalKactors.g:1096:4: (lv_if_2_0= ruleIfStatement )
                    // InternalKactors.g:1097:5: lv_if_2_0= ruleIfStatement
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
                    // InternalKactors.g:1115:3: (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' )
                    {
                    // InternalKactors.g:1115:3: (otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')' )
                    // InternalKactors.g:1116:4: otherlv_3= '(' ( (lv_group_4_0= ruleStatement ) ) ( (lv_group_5_0= ruleStatement ) )* otherlv_6= ')'
                    {
                    otherlv_3=(Token)match(input,24,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getStatementAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:1120:4: ( (lv_group_4_0= ruleStatement ) )
                    // InternalKactors.g:1121:5: (lv_group_4_0= ruleStatement )
                    {
                    // InternalKactors.g:1121:5: (lv_group_4_0= ruleStatement )
                    // InternalKactors.g:1122:6: lv_group_4_0= ruleStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_18);
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

                    // InternalKactors.g:1139:4: ( (lv_group_5_0= ruleStatement ) )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==RULE_LOWERCASE_ID||LA17_0==RULE_EMBEDDEDTEXT||LA17_0==24||LA17_0==32) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // InternalKactors.g:1140:5: (lv_group_5_0= ruleStatement )
                    	    {
                    	    // InternalKactors.g:1140:5: (lv_group_5_0= ruleStatement )
                    	    // InternalKactors.g:1141:6: lv_group_5_0= ruleStatement
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_18);
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
                    	    break loop17;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,26,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getStatementAccess().getRightParenthesisKeyword_3_3());
                      			
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
    // InternalKactors.g:1167:1: entryRuleIfStatement returns [EObject current=null] : iv_ruleIfStatement= ruleIfStatement EOF ;
    public final EObject entryRuleIfStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfStatement = null;


        try {
            // InternalKactors.g:1167:52: (iv_ruleIfStatement= ruleIfStatement EOF )
            // InternalKactors.g:1168:2: iv_ruleIfStatement= ruleIfStatement EOF
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
    // InternalKactors.g:1174:1: ruleIfStatement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? ) ;
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
            // InternalKactors.g:1180:2: ( (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? ) )
            // InternalKactors.g:1181:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? )
            {
            // InternalKactors.g:1181:2: (otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )? )
            // InternalKactors.g:1182:3: otherlv_0= 'if' ( (lv_expression_1_0= RULE_EXPR ) ) ( (lv_body_2_0= ruleIfBody ) ) (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )* (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )?
            {
            otherlv_0=(Token)match(input,32,FOLLOW_19); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
              		
            }
            // InternalKactors.g:1186:3: ( (lv_expression_1_0= RULE_EXPR ) )
            // InternalKactors.g:1187:4: (lv_expression_1_0= RULE_EXPR )
            {
            // InternalKactors.g:1187:4: (lv_expression_1_0= RULE_EXPR )
            // InternalKactors.g:1188:5: lv_expression_1_0= RULE_EXPR
            {
            lv_expression_1_0=(Token)match(input,RULE_EXPR,FOLLOW_9); if (state.failed) return current;
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

            // InternalKactors.g:1204:3: ( (lv_body_2_0= ruleIfBody ) )
            // InternalKactors.g:1205:4: (lv_body_2_0= ruleIfBody )
            {
            // InternalKactors.g:1205:4: (lv_body_2_0= ruleIfBody )
            // InternalKactors.g:1206:5: lv_body_2_0= ruleIfBody
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIfStatementAccess().getBodyIfBodyParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_20);
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

            // InternalKactors.g:1223:3: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==33) ) {
                    int LA19_1 = input.LA(2);

                    if ( (synpred31_InternalKactors()) ) {
                        alt19=1;
                    }


                }


                switch (alt19) {
            	case 1 :
            	    // InternalKactors.g:1224:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) )
            	    {
            	    otherlv_3=(Token)match(input,33,FOLLOW_21); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getElseKeyword_3_0());
            	      			
            	    }
            	    otherlv_4=(Token)match(input,32,FOLLOW_19); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_4, grammarAccess.getIfStatementAccess().getIfKeyword_3_1());
            	      			
            	    }
            	    // InternalKactors.g:1232:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
            	    // InternalKactors.g:1233:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    {
            	    // InternalKactors.g:1233:5: (lv_elseIfExpression_5_0= RULE_EXPR )
            	    // InternalKactors.g:1234:6: lv_elseIfExpression_5_0= RULE_EXPR
            	    {
            	    lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_9); if (state.failed) return current;
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

            	    // InternalKactors.g:1250:4: ( (lv_elseIfCall_6_0= ruleIfBody ) )
            	    // InternalKactors.g:1251:5: (lv_elseIfCall_6_0= ruleIfBody )
            	    {
            	    // InternalKactors.g:1251:5: (lv_elseIfCall_6_0= ruleIfBody )
            	    // InternalKactors.g:1252:6: lv_elseIfCall_6_0= ruleIfBody
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getIfStatementAccess().getElseIfCallIfBodyParserRuleCall_3_3_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_20);
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
            	    break loop19;
                }
            } while (true);

            // InternalKactors.g:1270:3: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==33) ) {
                int LA20_1 = input.LA(2);

                if ( (synpred32_InternalKactors()) ) {
                    alt20=1;
                }
            }
            switch (alt20) {
                case 1 :
                    // InternalKactors.g:1271:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) )
                    {
                    otherlv_7=(Token)match(input,33,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getIfStatementAccess().getElseKeyword_4_0());
                      			
                    }
                    // InternalKactors.g:1275:4: ( (lv_elseCall_8_0= ruleIfBody ) )
                    // InternalKactors.g:1276:5: (lv_elseCall_8_0= ruleIfBody )
                    {
                    // InternalKactors.g:1276:5: (lv_elseCall_8_0= ruleIfBody )
                    // InternalKactors.g:1277:6: lv_elseCall_8_0= ruleIfBody
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
    // InternalKactors.g:1299:1: entryRuleIfBody returns [EObject current=null] : iv_ruleIfBody= ruleIfBody EOF ;
    public final EObject entryRuleIfBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfBody = null;


        try {
            // InternalKactors.g:1299:47: (iv_ruleIfBody= ruleIfBody EOF )
            // InternalKactors.g:1300:2: iv_ruleIfBody= ruleIfBody EOF
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
    // InternalKactors.g:1306:1: ruleIfBody returns [EObject current=null] : ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) ) ;
    public final EObject ruleIfBody() throws RecognitionException {
        EObject current = null;

        EObject lv_call_0_0 = null;

        EObject lv_body_1_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1312:2: ( ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) ) )
            // InternalKactors.g:1313:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) )
            {
            // InternalKactors.g:1313:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_LOWERCASE_ID) ) {
                int LA21_1 = input.LA(2);

                if ( (synpred33_InternalKactors()) ) {
                    alt21=1;
                }
                else if ( (true) ) {
                    alt21=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 21, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA21_0==RULE_EMBEDDEDTEXT||LA21_0==24||LA21_0==32) ) {
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
                    // InternalKactors.g:1314:3: ( (lv_call_0_0= ruleCall ) )
                    {
                    // InternalKactors.g:1314:3: ( (lv_call_0_0= ruleCall ) )
                    // InternalKactors.g:1315:4: (lv_call_0_0= ruleCall )
                    {
                    // InternalKactors.g:1315:4: (lv_call_0_0= ruleCall )
                    // InternalKactors.g:1316:5: lv_call_0_0= ruleCall
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
                    // InternalKactors.g:1334:3: ( (lv_body_1_0= ruleBody ) )
                    {
                    // InternalKactors.g:1334:3: ( (lv_body_1_0= ruleBody ) )
                    // InternalKactors.g:1335:4: (lv_body_1_0= ruleBody )
                    {
                    // InternalKactors.g:1335:4: (lv_body_1_0= ruleBody )
                    // InternalKactors.g:1336:5: lv_body_1_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getIfBodyAccess().getBodyBodyParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_1_0=ruleBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getIfBodyRule());
                      					}
                      					set(
                      						current,
                      						"body",
                      						lv_body_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Body");
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
    // $ANTLR end "ruleIfBody"


    // $ANTLR start "entryRuleCall"
    // InternalKactors.g:1357:1: entryRuleCall returns [EObject current=null] : iv_ruleCall= ruleCall EOF ;
    public final EObject entryRuleCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCall = null;


        try {
            // InternalKactors.g:1357:45: (iv_ruleCall= ruleCall EOF )
            // InternalKactors.g:1358:2: iv_ruleCall= ruleCall EOF
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
    // InternalKactors.g:1364:1: ruleCall returns [EObject current=null] : ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? ) ;
    public final EObject ruleCall() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_parameters_2_0 = null;

        EObject lv_actions_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1370:2: ( ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? ) )
            // InternalKactors.g:1371:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? )
            {
            // InternalKactors.g:1371:2: ( ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )? )
            // InternalKactors.g:1372:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) ) (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )? ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )?
            {
            // InternalKactors.g:1372:3: ( (lv_name_0_0= RULE_LOWERCASE_ID ) )
            // InternalKactors.g:1373:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            {
            // InternalKactors.g:1373:4: (lv_name_0_0= RULE_LOWERCASE_ID )
            // InternalKactors.g:1374:5: lv_name_0_0= RULE_LOWERCASE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_0_0, grammarAccess.getCallAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getCallRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_0_0,
              						"org.integratedmodelling.kactors.Kactors.LOWERCASE_ID");
              				
            }

            }


            }

            // InternalKactors.g:1390:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?
            int alt23=2;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // InternalKactors.g:1391:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,24,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getCallAccess().getLeftParenthesisKeyword_1_0());
                      			
                    }
                    // InternalKactors.g:1395:4: ( (lv_parameters_2_0= ruleParameterList ) )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( ((LA22_0>=RULE_LOWERCASE_ID && LA22_0<=RULE_EXPR)||LA22_0==RULE_INT||(LA22_0>=30 && LA22_0<=31)||(LA22_0>=36 && LA22_0<=37)) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalKactors.g:1396:5: (lv_parameters_2_0= ruleParameterList )
                            {
                            // InternalKactors.g:1396:5: (lv_parameters_2_0= ruleParameterList )
                            // InternalKactors.g:1397:6: lv_parameters_2_0= ruleParameterList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0());
                              					
                            }
                            pushFollow(FOLLOW_24);
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

                    otherlv_3=(Token)match(input,26,FOLLOW_25); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getCallAccess().getRightParenthesisKeyword_1_2());
                      			
                    }

                    }
                    break;

            }

            // InternalKactors.g:1419:3: ( (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) ) | otherlv_6= ';' )?
            int alt24=3;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==23) ) {
                alt24=1;
            }
            else if ( (LA24_0==34) ) {
                alt24=2;
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:1420:4: (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) )
                    {
                    // InternalKactors.g:1420:4: (otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) ) )
                    // InternalKactors.g:1421:5: otherlv_4= ':' ( (lv_actions_5_0= ruleActions ) )
                    {
                    otherlv_4=(Token)match(input,23,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getCallAccess().getColonKeyword_2_0_0());
                      				
                    }
                    // InternalKactors.g:1425:5: ( (lv_actions_5_0= ruleActions ) )
                    // InternalKactors.g:1426:6: (lv_actions_5_0= ruleActions )
                    {
                    // InternalKactors.g:1426:6: (lv_actions_5_0= ruleActions )
                    // InternalKactors.g:1427:7: lv_actions_5_0= ruleActions
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
                    // InternalKactors.g:1446:4: otherlv_6= ';'
                    {
                    otherlv_6=(Token)match(input,34,FOLLOW_2); if (state.failed) return current;
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
    // InternalKactors.g:1455:1: entryRuleActions returns [EObject current=null] : iv_ruleActions= ruleActions EOF ;
    public final EObject entryRuleActions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActions = null;


        try {
            // InternalKactors.g:1455:48: (iv_ruleActions= ruleActions EOF )
            // InternalKactors.g:1456:2: iv_ruleActions= ruleActions EOF
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
    // InternalKactors.g:1462:1: ruleActions returns [EObject current=null] : ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) ) ;
    public final EObject ruleActions() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_6=null;
        EObject lv_call_0_0 = null;

        EObject lv_body_1_0 = null;

        EObject lv_match_2_0 = null;

        EObject lv_matches_4_0 = null;

        EObject lv_matches_5_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1468:2: ( ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) ) )
            // InternalKactors.g:1469:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) )
            {
            // InternalKactors.g:1469:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) )
            int alt26=4;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // InternalKactors.g:1470:3: ( (lv_call_0_0= ruleCall ) )
                    {
                    // InternalKactors.g:1470:3: ( (lv_call_0_0= ruleCall ) )
                    // InternalKactors.g:1471:4: (lv_call_0_0= ruleCall )
                    {
                    // InternalKactors.g:1471:4: (lv_call_0_0= ruleCall )
                    // InternalKactors.g:1472:5: lv_call_0_0= ruleCall
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getCallCallParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_call_0_0=ruleCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
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
                    // InternalKactors.g:1490:3: ( (lv_body_1_0= ruleBody ) )
                    {
                    // InternalKactors.g:1490:3: ( (lv_body_1_0= ruleBody ) )
                    // InternalKactors.g:1491:4: (lv_body_1_0= ruleBody )
                    {
                    // InternalKactors.g:1491:4: (lv_body_1_0= ruleBody )
                    // InternalKactors.g:1492:5: lv_body_1_0= ruleBody
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_body_1_0=ruleBody();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"body",
                      						lv_body_1_0,
                      						"org.integratedmodelling.kactors.Kactors.Body");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKactors.g:1510:3: ( (lv_match_2_0= ruleMatch ) )
                    {
                    // InternalKactors.g:1510:3: ( (lv_match_2_0= ruleMatch ) )
                    // InternalKactors.g:1511:4: (lv_match_2_0= ruleMatch )
                    {
                    // InternalKactors.g:1511:4: (lv_match_2_0= ruleMatch )
                    // InternalKactors.g:1512:5: lv_match_2_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_match_2_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getActionsRule());
                      					}
                      					set(
                      						current,
                      						"match",
                      						lv_match_2_0,
                      						"org.integratedmodelling.kactors.Kactors.Match");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKactors.g:1530:3: (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' )
                    {
                    // InternalKactors.g:1530:3: (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' )
                    // InternalKactors.g:1531:4: otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')'
                    {
                    otherlv_3=(Token)match(input,24,FOLLOW_27); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalKactors.g:1535:4: ( (lv_matches_4_0= ruleMatch ) )
                    // InternalKactors.g:1536:5: (lv_matches_4_0= ruleMatch )
                    {
                    // InternalKactors.g:1536:5: (lv_matches_4_0= ruleMatch )
                    // InternalKactors.g:1537:6: lv_matches_4_0= ruleMatch
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_28);
                    lv_matches_4_0=ruleMatch();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionsRule());
                      						}
                      						add(
                      							current,
                      							"matches",
                      							lv_matches_4_0,
                      							"org.integratedmodelling.kactors.Kactors.Match");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalKactors.g:1554:4: ( (lv_matches_5_0= ruleMatch ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( ((LA25_0>=RULE_LOWERCASE_ID && LA25_0<=RULE_STRING)||LA25_0==RULE_OBSERVABLE||(LA25_0>=RULE_REGEXP && LA25_0<=RULE_INT)||LA25_0==24||(LA25_0>=30 && LA25_0<=31)||(LA25_0>=36 && LA25_0<=37)) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // InternalKactors.g:1555:5: (lv_matches_5_0= ruleMatch )
                    	    {
                    	    // InternalKactors.g:1555:5: (lv_matches_5_0= ruleMatch )
                    	    // InternalKactors.g:1556:6: lv_matches_5_0= ruleMatch
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_2_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_28);
                    	    lv_matches_5_0=ruleMatch();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getActionsRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"matches",
                    	      							lv_matches_5_0,
                    	      							"org.integratedmodelling.kactors.Kactors.Match");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,26,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getActionsAccess().getRightParenthesisKeyword_3_3());
                      			
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
    // InternalKactors.g:1582:1: entryRuleMatch returns [EObject current=null] : iv_ruleMatch= ruleMatch EOF ;
    public final EObject entryRuleMatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMatch = null;


        try {
            // InternalKactors.g:1582:46: (iv_ruleMatch= ruleMatch EOF )
            // InternalKactors.g:1583:2: iv_ruleMatch= ruleMatch EOF
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
    // InternalKactors.g:1589:1: ruleMatch returns [EObject current=null] : ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) ) ;
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
        EObject lv_body_2_0 = null;

        EObject lv_body_5_0 = null;

        EObject lv_body_8_0 = null;

        EObject lv_literal_9_0 = null;

        EObject lv_body_11_0 = null;

        EObject lv_body_14_0 = null;

        EObject lv_arguments_15_0 = null;

        EObject lv_body_17_0 = null;



        	enterRule();

        try {
            // InternalKactors.g:1595:2: ( ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) ) )
            // InternalKactors.g:1596:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) )
            {
            // InternalKactors.g:1596:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) )
            int alt27=6;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalKactors.g:1597:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:1597:3: ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) )
                    // InternalKactors.g:1598:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) )
                    {
                    // InternalKactors.g:1598:4: ( (lv_id_0_0= RULE_LOWERCASE_ID ) )
                    // InternalKactors.g:1599:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    {
                    // InternalKactors.g:1599:5: (lv_id_0_0= RULE_LOWERCASE_ID )
                    // InternalKactors.g:1600:6: lv_id_0_0= RULE_LOWERCASE_ID
                    {
                    lv_id_0_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_29); if (state.failed) return current;
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

                    otherlv_1=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1());
                      			
                    }
                    // InternalKactors.g:1620:4: ( (lv_body_2_0= ruleBody ) )
                    // InternalKactors.g:1621:5: (lv_body_2_0= ruleBody )
                    {
                    // InternalKactors.g:1621:5: (lv_body_2_0= ruleBody )
                    // InternalKactors.g:1622:6: lv_body_2_0= ruleBody
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
                    // InternalKactors.g:1641:3: ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:1641:3: ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) )
                    // InternalKactors.g:1642:4: ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) )
                    {
                    // InternalKactors.g:1642:4: ( (lv_regexp_3_0= RULE_REGEXP ) )
                    // InternalKactors.g:1643:5: (lv_regexp_3_0= RULE_REGEXP )
                    {
                    // InternalKactors.g:1643:5: (lv_regexp_3_0= RULE_REGEXP )
                    // InternalKactors.g:1644:6: lv_regexp_3_0= RULE_REGEXP
                    {
                    lv_regexp_3_0=(Token)match(input,RULE_REGEXP,FOLLOW_29); if (state.failed) return current;
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

                    otherlv_4=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1());
                      			
                    }
                    // InternalKactors.g:1664:4: ( (lv_body_5_0= ruleBody ) )
                    // InternalKactors.g:1665:5: (lv_body_5_0= ruleBody )
                    {
                    // InternalKactors.g:1665:5: (lv_body_5_0= ruleBody )
                    // InternalKactors.g:1666:6: lv_body_5_0= ruleBody
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
                    // InternalKactors.g:1685:3: ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:1685:3: ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) )
                    // InternalKactors.g:1686:4: ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) )
                    {
                    // InternalKactors.g:1686:4: ( (lv_observable_6_0= RULE_OBSERVABLE ) )
                    // InternalKactors.g:1687:5: (lv_observable_6_0= RULE_OBSERVABLE )
                    {
                    // InternalKactors.g:1687:5: (lv_observable_6_0= RULE_OBSERVABLE )
                    // InternalKactors.g:1688:6: lv_observable_6_0= RULE_OBSERVABLE
                    {
                    lv_observable_6_0=(Token)match(input,RULE_OBSERVABLE,FOLLOW_29); if (state.failed) return current;
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

                    otherlv_7=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1());
                      			
                    }
                    // InternalKactors.g:1708:4: ( (lv_body_8_0= ruleBody ) )
                    // InternalKactors.g:1709:5: (lv_body_8_0= ruleBody )
                    {
                    // InternalKactors.g:1709:5: (lv_body_8_0= ruleBody )
                    // InternalKactors.g:1710:6: lv_body_8_0= ruleBody
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
                    // InternalKactors.g:1729:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:1729:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
                    // InternalKactors.g:1730:4: ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) )
                    {
                    // InternalKactors.g:1730:4: ( (lv_literal_9_0= ruleLiteral ) )
                    // InternalKactors.g:1731:5: (lv_literal_9_0= ruleLiteral )
                    {
                    // InternalKactors.g:1731:5: (lv_literal_9_0= ruleLiteral )
                    // InternalKactors.g:1732:6: lv_literal_9_0= ruleLiteral
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
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

                    otherlv_10=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_10, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1());
                      			
                    }
                    // InternalKactors.g:1753:4: ( (lv_body_11_0= ruleBody ) )
                    // InternalKactors.g:1754:5: (lv_body_11_0= ruleBody )
                    {
                    // InternalKactors.g:1754:5: (lv_body_11_0= ruleBody )
                    // InternalKactors.g:1755:6: lv_body_11_0= ruleBody
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
                    // InternalKactors.g:1774:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:1774:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
                    // InternalKactors.g:1775:4: ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) )
                    {
                    // InternalKactors.g:1775:4: ( (lv_text_12_0= RULE_STRING ) )
                    // InternalKactors.g:1776:5: (lv_text_12_0= RULE_STRING )
                    {
                    // InternalKactors.g:1776:5: (lv_text_12_0= RULE_STRING )
                    // InternalKactors.g:1777:6: lv_text_12_0= RULE_STRING
                    {
                    lv_text_12_0=(Token)match(input,RULE_STRING,FOLLOW_29); if (state.failed) return current;
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

                    otherlv_13=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_13, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1());
                      			
                    }
                    // InternalKactors.g:1797:4: ( (lv_body_14_0= ruleBody ) )
                    // InternalKactors.g:1798:5: (lv_body_14_0= ruleBody )
                    {
                    // InternalKactors.g:1798:5: (lv_body_14_0= ruleBody )
                    // InternalKactors.g:1799:6: lv_body_14_0= ruleBody
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
                    // InternalKactors.g:1818:3: ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) )
                    {
                    // InternalKactors.g:1818:3: ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) )
                    // InternalKactors.g:1819:4: ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) )
                    {
                    // InternalKactors.g:1819:4: ( (lv_arguments_15_0= ruleArgumentDeclaration ) )
                    // InternalKactors.g:1820:5: (lv_arguments_15_0= ruleArgumentDeclaration )
                    {
                    // InternalKactors.g:1820:5: (lv_arguments_15_0= ruleArgumentDeclaration )
                    // InternalKactors.g:1821:6: lv_arguments_15_0= ruleArgumentDeclaration
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_29);
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

                    otherlv_16=(Token)match(input,35,FOLLOW_9); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1());
                      			
                    }
                    // InternalKactors.g:1842:4: ( (lv_body_17_0= ruleBody ) )
                    // InternalKactors.g:1843:5: (lv_body_17_0= ruleBody )
                    {
                    // InternalKactors.g:1843:5: (lv_body_17_0= ruleBody )
                    // InternalKactors.g:1844:6: lv_body_17_0= ruleBody
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

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
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
    // InternalKactors.g:1866:1: entryRuleNumber returns [EObject current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final EObject entryRuleNumber() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumber = null;


        try {
            // InternalKactors.g:1866:47: (iv_ruleNumber= ruleNumber EOF )
            // InternalKactors.g:1867:2: iv_ruleNumber= ruleNumber EOF
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
    // InternalKactors.g:1873:1: ruleNumber returns [EObject current=null] : ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) ;
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
            // InternalKactors.g:1879:2: ( ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? ) )
            // InternalKactors.g:1880:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            {
            // InternalKactors.g:1880:2: ( (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )? )
            // InternalKactors.g:1881:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )? ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) ) ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )? ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )? ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            {
            // InternalKactors.g:1881:3: (otherlv_0= '+' | ( (lv_negative_1_0= '-' ) ) )?
            int alt28=3;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==36) ) {
                alt28=1;
            }
            else if ( (LA28_0==37) ) {
                alt28=2;
            }
            switch (alt28) {
                case 1 :
                    // InternalKactors.g:1882:4: otherlv_0= '+'
                    {
                    otherlv_0=(Token)match(input,36,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_0, grammarAccess.getNumberAccess().getPlusSignKeyword_0_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:1887:4: ( (lv_negative_1_0= '-' ) )
                    {
                    // InternalKactors.g:1887:4: ( (lv_negative_1_0= '-' ) )
                    // InternalKactors.g:1888:5: (lv_negative_1_0= '-' )
                    {
                    // InternalKactors.g:1888:5: (lv_negative_1_0= '-' )
                    // InternalKactors.g:1889:6: lv_negative_1_0= '-'
                    {
                    lv_negative_1_0=(Token)match(input,37,FOLLOW_30); if (state.failed) return current;
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

            // InternalKactors.g:1902:3: ( ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT ) )
            // InternalKactors.g:1903:4: ( ( RULE_INT ) )=> (lv_real_2_0= RULE_INT )
            {
            // InternalKactors.g:1907:4: (lv_real_2_0= RULE_INT )
            // InternalKactors.g:1908:5: lv_real_2_0= RULE_INT
            {
            lv_real_2_0=(Token)match(input,RULE_INT,FOLLOW_31); if (state.failed) return current;
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

            // InternalKactors.g:1924:3: ( ( ( 'l' ) )=> (lv_long_3_0= 'l' ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==38) && (synpred50_InternalKactors())) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalKactors.g:1925:4: ( ( 'l' ) )=> (lv_long_3_0= 'l' )
                    {
                    // InternalKactors.g:1929:4: (lv_long_3_0= 'l' )
                    // InternalKactors.g:1930:5: lv_long_3_0= 'l'
                    {
                    lv_long_3_0=(Token)match(input,38,FOLLOW_32); if (state.failed) return current;
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

            // InternalKactors.g:1942:3: ( ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==39) && (synpred51_InternalKactors())) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalKactors.g:1943:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )=> ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:1956:4: ( ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) ) )
                    // InternalKactors.g:1957:5: ( (lv_decimal_4_0= '.' ) ) ( (lv_decimalPart_5_0= RULE_INT ) )
                    {
                    // InternalKactors.g:1957:5: ( (lv_decimal_4_0= '.' ) )
                    // InternalKactors.g:1958:6: (lv_decimal_4_0= '.' )
                    {
                    // InternalKactors.g:1958:6: (lv_decimal_4_0= '.' )
                    // InternalKactors.g:1959:7: lv_decimal_4_0= '.'
                    {
                    lv_decimal_4_0=(Token)match(input,39,FOLLOW_30); if (state.failed) return current;
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

                    // InternalKactors.g:1971:5: ( (lv_decimalPart_5_0= RULE_INT ) )
                    // InternalKactors.g:1972:6: (lv_decimalPart_5_0= RULE_INT )
                    {
                    // InternalKactors.g:1972:6: (lv_decimalPart_5_0= RULE_INT )
                    // InternalKactors.g:1973:7: lv_decimalPart_5_0= RULE_INT
                    {
                    lv_decimalPart_5_0=(Token)match(input,RULE_INT,FOLLOW_33); if (state.failed) return current;
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

            // InternalKactors.g:1991:3: ( ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) ) )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==40) && (synpred55_InternalKactors())) {
                alt33=1;
            }
            else if ( (LA33_0==41) && (synpred55_InternalKactors())) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalKactors.g:1992:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )=> ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    {
                    // InternalKactors.g:2018:4: ( ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) ) )
                    // InternalKactors.g:2019:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) ) (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )? ( (lv_exp_9_0= RULE_INT ) )
                    {
                    // InternalKactors.g:2019:5: ( ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) ) )
                    // InternalKactors.g:2020:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    {
                    // InternalKactors.g:2020:6: ( (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' ) )
                    // InternalKactors.g:2021:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    {
                    // InternalKactors.g:2021:7: (lv_exponential_6_1= 'e' | lv_exponential_6_2= 'E' )
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==40) ) {
                        alt31=1;
                    }
                    else if ( (LA31_0==41) ) {
                        alt31=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 0, input);

                        throw nvae;
                    }
                    switch (alt31) {
                        case 1 :
                            // InternalKactors.g:2022:8: lv_exponential_6_1= 'e'
                            {
                            lv_exponential_6_1=(Token)match(input,40,FOLLOW_16); if (state.failed) return current;
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
                            // InternalKactors.g:2033:8: lv_exponential_6_2= 'E'
                            {
                            lv_exponential_6_2=(Token)match(input,41,FOLLOW_16); if (state.failed) return current;
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

                    // InternalKactors.g:2046:5: (otherlv_7= '+' | ( (lv_expNegative_8_0= '-' ) ) )?
                    int alt32=3;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==36) ) {
                        alt32=1;
                    }
                    else if ( (LA32_0==37) ) {
                        alt32=2;
                    }
                    switch (alt32) {
                        case 1 :
                            // InternalKactors.g:2047:6: otherlv_7= '+'
                            {
                            otherlv_7=(Token)match(input,36,FOLLOW_30); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_7, grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0());
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalKactors.g:2052:6: ( (lv_expNegative_8_0= '-' ) )
                            {
                            // InternalKactors.g:2052:6: ( (lv_expNegative_8_0= '-' ) )
                            // InternalKactors.g:2053:7: (lv_expNegative_8_0= '-' )
                            {
                            // InternalKactors.g:2053:7: (lv_expNegative_8_0= '-' )
                            // InternalKactors.g:2054:8: lv_expNegative_8_0= '-'
                            {
                            lv_expNegative_8_0=(Token)match(input,37,FOLLOW_30); if (state.failed) return current;
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

                    // InternalKactors.g:2067:5: ( (lv_exp_9_0= RULE_INT ) )
                    // InternalKactors.g:2068:6: (lv_exp_9_0= RULE_INT )
                    {
                    // InternalKactors.g:2068:6: (lv_exp_9_0= RULE_INT )
                    // InternalKactors.g:2069:7: lv_exp_9_0= RULE_INT
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
    // InternalKactors.g:2091:1: entryRuleDate returns [EObject current=null] : iv_ruleDate= ruleDate EOF ;
    public final EObject entryRuleDate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDate = null;


        try {
            // InternalKactors.g:2091:45: (iv_ruleDate= ruleDate EOF )
            // InternalKactors.g:2092:2: iv_ruleDate= ruleDate EOF
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
    // InternalKactors.g:2098:1: ruleDate returns [EObject current=null] : ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) ;
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
            // InternalKactors.g:2104:2: ( ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? ) )
            // InternalKactors.g:2105:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            {
            // InternalKactors.g:2105:2: ( ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )? )
            // InternalKactors.g:2106:3: ( (lv_year_0_0= RULE_INT ) ) (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )? otherlv_4= '-' ( (lv_month_5_0= RULE_INT ) ) otherlv_6= '-' ( (lv_day_7_0= RULE_INT ) ) ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            {
            // InternalKactors.g:2106:3: ( (lv_year_0_0= RULE_INT ) )
            // InternalKactors.g:2107:4: (lv_year_0_0= RULE_INT )
            {
            // InternalKactors.g:2107:4: (lv_year_0_0= RULE_INT )
            // InternalKactors.g:2108:5: lv_year_0_0= RULE_INT
            {
            lv_year_0_0=(Token)match(input,RULE_INT,FOLLOW_34); if (state.failed) return current;
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

            // InternalKactors.g:2124:3: (otherlv_1= 'AD' | otherlv_2= 'CE' | ( (lv_bc_3_0= 'BC' ) ) )?
            int alt34=4;
            switch ( input.LA(1) ) {
                case 42:
                    {
                    alt34=1;
                    }
                    break;
                case 43:
                    {
                    alt34=2;
                    }
                    break;
                case 44:
                    {
                    alt34=3;
                    }
                    break;
            }

            switch (alt34) {
                case 1 :
                    // InternalKactors.g:2125:4: otherlv_1= 'AD'
                    {
                    otherlv_1=(Token)match(input,42,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_1, grammarAccess.getDateAccess().getADKeyword_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalKactors.g:2130:4: otherlv_2= 'CE'
                    {
                    otherlv_2=(Token)match(input,43,FOLLOW_35); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getDateAccess().getCEKeyword_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalKactors.g:2135:4: ( (lv_bc_3_0= 'BC' ) )
                    {
                    // InternalKactors.g:2135:4: ( (lv_bc_3_0= 'BC' ) )
                    // InternalKactors.g:2136:5: (lv_bc_3_0= 'BC' )
                    {
                    // InternalKactors.g:2136:5: (lv_bc_3_0= 'BC' )
                    // InternalKactors.g:2137:6: lv_bc_3_0= 'BC'
                    {
                    lv_bc_3_0=(Token)match(input,44,FOLLOW_35); if (state.failed) return current;
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

            otherlv_4=(Token)match(input,37,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getDateAccess().getHyphenMinusKeyword_2());
              		
            }
            // InternalKactors.g:2154:3: ( (lv_month_5_0= RULE_INT ) )
            // InternalKactors.g:2155:4: (lv_month_5_0= RULE_INT )
            {
            // InternalKactors.g:2155:4: (lv_month_5_0= RULE_INT )
            // InternalKactors.g:2156:5: lv_month_5_0= RULE_INT
            {
            lv_month_5_0=(Token)match(input,RULE_INT,FOLLOW_35); if (state.failed) return current;
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

            otherlv_6=(Token)match(input,37,FOLLOW_30); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_6, grammarAccess.getDateAccess().getHyphenMinusKeyword_4());
              		
            }
            // InternalKactors.g:2176:3: ( (lv_day_7_0= RULE_INT ) )
            // InternalKactors.g:2177:4: (lv_day_7_0= RULE_INT )
            {
            // InternalKactors.g:2177:4: (lv_day_7_0= RULE_INT )
            // InternalKactors.g:2178:5: lv_day_7_0= RULE_INT
            {
            lv_day_7_0=(Token)match(input,RULE_INT,FOLLOW_36); if (state.failed) return current;
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

            // InternalKactors.g:2194:3: ( ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )? )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_INT) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalKactors.g:2195:4: ( (lv_hour_8_0= RULE_INT ) ) otherlv_9= ':' ( (lv_min_10_0= RULE_INT ) ) (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    {
                    // InternalKactors.g:2195:4: ( (lv_hour_8_0= RULE_INT ) )
                    // InternalKactors.g:2196:5: (lv_hour_8_0= RULE_INT )
                    {
                    // InternalKactors.g:2196:5: (lv_hour_8_0= RULE_INT )
                    // InternalKactors.g:2197:6: lv_hour_8_0= RULE_INT
                    {
                    lv_hour_8_0=(Token)match(input,RULE_INT,FOLLOW_8); if (state.failed) return current;
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

                    otherlv_9=(Token)match(input,23,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getDateAccess().getColonKeyword_6_1());
                      			
                    }
                    // InternalKactors.g:2217:4: ( (lv_min_10_0= RULE_INT ) )
                    // InternalKactors.g:2218:5: (lv_min_10_0= RULE_INT )
                    {
                    // InternalKactors.g:2218:5: (lv_min_10_0= RULE_INT )
                    // InternalKactors.g:2219:6: lv_min_10_0= RULE_INT
                    {
                    lv_min_10_0=(Token)match(input,RULE_INT,FOLLOW_37); if (state.failed) return current;
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

                    // InternalKactors.g:2235:4: (otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )? )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==23) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // InternalKactors.g:2236:5: otherlv_11= ':' ( (lv_sec_12_0= RULE_INT ) ) (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            {
                            otherlv_11=(Token)match(input,23,FOLLOW_30); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_11, grammarAccess.getDateAccess().getColonKeyword_6_3_0());
                              				
                            }
                            // InternalKactors.g:2240:5: ( (lv_sec_12_0= RULE_INT ) )
                            // InternalKactors.g:2241:6: (lv_sec_12_0= RULE_INT )
                            {
                            // InternalKactors.g:2241:6: (lv_sec_12_0= RULE_INT )
                            // InternalKactors.g:2242:7: lv_sec_12_0= RULE_INT
                            {
                            lv_sec_12_0=(Token)match(input,RULE_INT,FOLLOW_38); if (state.failed) return current;
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

                            // InternalKactors.g:2258:5: (otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) ) )?
                            int alt35=2;
                            int LA35_0 = input.LA(1);

                            if ( (LA35_0==39) ) {
                                alt35=1;
                            }
                            switch (alt35) {
                                case 1 :
                                    // InternalKactors.g:2259:6: otherlv_13= '.' ( (lv_ms_14_0= RULE_INT ) )
                                    {
                                    otherlv_13=(Token)match(input,39,FOLLOW_30); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						newLeafNode(otherlv_13, grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0());
                                      					
                                    }
                                    // InternalKactors.g:2263:6: ( (lv_ms_14_0= RULE_INT ) )
                                    // InternalKactors.g:2264:7: (lv_ms_14_0= RULE_INT )
                                    {
                                    // InternalKactors.g:2264:7: (lv_ms_14_0= RULE_INT )
                                    // InternalKactors.g:2265:8: lv_ms_14_0= RULE_INT
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

    // $ANTLR start synpred3_InternalKactors
    public final void synpred3_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token lv_worldview_4_0=null;

        // InternalKactors.g:192:4: ( ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )
        // InternalKactors.g:192:4: ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) )
        {
        // InternalKactors.g:192:4: ({...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) ) )
        // InternalKactors.g:193:5: {...}? => ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred3_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKactors.g:193:105: ( ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) ) )
        // InternalKactors.g:194:6: ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
        // InternalKactors.g:197:9: ({...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) ) )
        // InternalKactors.g:197:10: {...}? => (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred3_InternalKactors", "true");
        }
        // InternalKactors.g:197:19: (otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) ) )
        // InternalKactors.g:197:20: otherlv_3= 'worldview' ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) )
        {
        otherlv_3=(Token)match(input,18,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:201:9: ( (lv_worldview_4_0= RULE_LOWERCASE_ID ) )
        // InternalKactors.g:202:10: (lv_worldview_4_0= RULE_LOWERCASE_ID )
        {
        // InternalKactors.g:202:10: (lv_worldview_4_0= RULE_LOWERCASE_ID )
        // InternalKactors.g:203:11: lv_worldview_4_0= RULE_LOWERCASE_ID
        {
        lv_worldview_4_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred3_InternalKactors

    // $ANTLR start synpred4_InternalKactors
    public final void synpred4_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_5=null;
        Token lv_permissions_6_0=null;

        // InternalKactors.g:225:4: ( ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:225:4: ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:225:4: ({...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:226:5: {...}? => ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred4_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKactors.g:226:105: ( ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:227:6: ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
        // InternalKactors.g:230:9: ({...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) ) )
        // InternalKactors.g:230:10: {...}? => (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred4_InternalKactors", "true");
        }
        // InternalKactors.g:230:19: (otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) ) )
        // InternalKactors.g:230:20: otherlv_5= 'permissions' ( (lv_permissions_6_0= RULE_STRING ) )
        {
        otherlv_5=(Token)match(input,19,FOLLOW_6); if (state.failed) return ;
        // InternalKactors.g:234:9: ( (lv_permissions_6_0= RULE_STRING ) )
        // InternalKactors.g:235:10: (lv_permissions_6_0= RULE_STRING )
        {
        // InternalKactors.g:235:10: (lv_permissions_6_0= RULE_STRING )
        // InternalKactors.g:236:11: lv_permissions_6_0= RULE_STRING
        {
        lv_permissions_6_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred4_InternalKactors

    // $ANTLR start synpred5_InternalKactors
    public final void synpred5_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        Token lv_authors_8_0=null;

        // InternalKactors.g:258:4: ( ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) ) )
        // InternalKactors.g:258:4: ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) )
        {
        // InternalKactors.g:258:4: ({...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) ) )
        // InternalKactors.g:259:5: {...}? => ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred5_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKactors.g:259:105: ( ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) ) )
        // InternalKactors.g:260:6: ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
        // InternalKactors.g:263:9: ({...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) ) )
        // InternalKactors.g:263:10: {...}? => (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred5_InternalKactors", "true");
        }
        // InternalKactors.g:263:19: (otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) ) )
        // InternalKactors.g:263:20: otherlv_7= 'author' ( (lv_authors_8_0= RULE_STRING ) )
        {
        otherlv_7=(Token)match(input,20,FOLLOW_6); if (state.failed) return ;
        // InternalKactors.g:267:9: ( (lv_authors_8_0= RULE_STRING ) )
        // InternalKactors.g:268:10: (lv_authors_8_0= RULE_STRING )
        {
        // InternalKactors.g:268:10: (lv_authors_8_0= RULE_STRING )
        // InternalKactors.g:269:11: lv_authors_8_0= RULE_STRING
        {
        lv_authors_8_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred5_InternalKactors

    // $ANTLR start synpred6_InternalKactors
    public final void synpred6_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_9=null;
        Token lv_version_10_0=null;

        // InternalKactors.g:291:4: ( ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) ) )
        // InternalKactors.g:291:4: ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) )
        {
        // InternalKactors.g:291:4: ({...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) ) )
        // InternalKactors.g:292:5: {...}? => ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred6_InternalKactors", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
        }
        // InternalKactors.g:292:105: ( ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) ) )
        // InternalKactors.g:293:6: ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
        // InternalKactors.g:296:9: ({...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) ) )
        // InternalKactors.g:296:10: {...}? => (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) )
        {
        if ( !((true)) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred6_InternalKactors", "true");
        }
        // InternalKactors.g:296:19: (otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) ) )
        // InternalKactors.g:296:20: otherlv_9= 'version' ( (lv_version_10_0= RULE_LOWERCASE_ID ) )
        {
        otherlv_9=(Token)match(input,21,FOLLOW_4); if (state.failed) return ;
        // InternalKactors.g:300:9: ( (lv_version_10_0= RULE_LOWERCASE_ID ) )
        // InternalKactors.g:301:10: (lv_version_10_0= RULE_LOWERCASE_ID )
        {
        // InternalKactors.g:301:10: (lv_version_10_0= RULE_LOWERCASE_ID )
        // InternalKactors.g:302:11: lv_version_10_0= RULE_LOWERCASE_ID
        {
        lv_version_10_0=(Token)match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred6_InternalKactors

    // $ANTLR start synpred23_InternalKactors
    public final void synpred23_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_list_2_0 = null;


        // InternalKactors.g:957:5: ( (lv_list_2_0= ruleStatement ) )
        // InternalKactors.g:957:5: (lv_list_2_0= ruleStatement )
        {
        // InternalKactors.g:957:5: (lv_list_2_0= ruleStatement )
        // InternalKactors.g:958:6: lv_list_2_0= ruleStatement
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
    // $ANTLR end synpred23_InternalKactors

    // $ANTLR start synpred24_InternalKactors
    public final void synpred24_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_list_1_0 = null;

        EObject lv_list_2_0 = null;


        // InternalKactors.g:926:3: ( ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* ) )
        // InternalKactors.g:926:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
        {
        // InternalKactors.g:926:3: ( () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )* )
        // InternalKactors.g:927:4: () ( (lv_list_1_0= ruleStatement ) ) ( (lv_list_2_0= ruleStatement ) )*
        {
        // InternalKactors.g:927:4: ()
        // InternalKactors.g:928:5: 
        {
        if ( state.backtracking==0 ) {

          					/* */
          				
        }

        }

        // InternalKactors.g:937:4: ( (lv_list_1_0= ruleStatement ) )
        // InternalKactors.g:938:5: (lv_list_1_0= ruleStatement )
        {
        // InternalKactors.g:938:5: (lv_list_1_0= ruleStatement )
        // InternalKactors.g:939:6: lv_list_1_0= ruleStatement
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0());
          					
        }
        pushFollow(FOLLOW_17);
        lv_list_1_0=ruleStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // InternalKactors.g:956:4: ( (lv_list_2_0= ruleStatement ) )*
        loop40:
        do {
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==RULE_LOWERCASE_ID||LA40_0==RULE_EMBEDDEDTEXT||LA40_0==24||LA40_0==32) ) {
                alt40=1;
            }


            switch (alt40) {
        	case 1 :
        	    // InternalKactors.g:957:5: (lv_list_2_0= ruleStatement )
        	    {
        	    // InternalKactors.g:957:5: (lv_list_2_0= ruleStatement )
        	    // InternalKactors.g:958:6: lv_list_2_0= ruleStatement
        	    {
        	    if ( state.backtracking==0 ) {

        	      						newCompositeNode(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0());
        	      					
        	    }
        	    pushFollow(FOLLOW_17);
        	    lv_list_2_0=ruleStatement();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }


        	    }
        	    break;

        	default :
        	    break loop40;
            }
        } while (true);


        }


        }
    }
    // $ANTLR end synpred24_InternalKactors

    // $ANTLR start synpred31_InternalKactors
    public final void synpred31_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_elseIfExpression_5_0=null;
        EObject lv_elseIfCall_6_0 = null;


        // InternalKactors.g:1224:4: (otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) ) )
        // InternalKactors.g:1224:4: otherlv_3= 'else' otherlv_4= 'if' ( (lv_elseIfExpression_5_0= RULE_EXPR ) ) ( (lv_elseIfCall_6_0= ruleIfBody ) )
        {
        otherlv_3=(Token)match(input,33,FOLLOW_21); if (state.failed) return ;
        otherlv_4=(Token)match(input,32,FOLLOW_19); if (state.failed) return ;
        // InternalKactors.g:1232:4: ( (lv_elseIfExpression_5_0= RULE_EXPR ) )
        // InternalKactors.g:1233:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        {
        // InternalKactors.g:1233:5: (lv_elseIfExpression_5_0= RULE_EXPR )
        // InternalKactors.g:1234:6: lv_elseIfExpression_5_0= RULE_EXPR
        {
        lv_elseIfExpression_5_0=(Token)match(input,RULE_EXPR,FOLLOW_9); if (state.failed) return ;

        }


        }

        // InternalKactors.g:1250:4: ( (lv_elseIfCall_6_0= ruleIfBody ) )
        // InternalKactors.g:1251:5: (lv_elseIfCall_6_0= ruleIfBody )
        {
        // InternalKactors.g:1251:5: (lv_elseIfCall_6_0= ruleIfBody )
        // InternalKactors.g:1252:6: lv_elseIfCall_6_0= ruleIfBody
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
    // $ANTLR end synpred31_InternalKactors

    // $ANTLR start synpred32_InternalKactors
    public final void synpred32_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_7=null;
        EObject lv_elseCall_8_0 = null;


        // InternalKactors.g:1271:4: (otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) ) )
        // InternalKactors.g:1271:4: otherlv_7= 'else' ( (lv_elseCall_8_0= ruleIfBody ) )
        {
        otherlv_7=(Token)match(input,33,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:1275:4: ( (lv_elseCall_8_0= ruleIfBody ) )
        // InternalKactors.g:1276:5: (lv_elseCall_8_0= ruleIfBody )
        {
        // InternalKactors.g:1276:5: (lv_elseCall_8_0= ruleIfBody )
        // InternalKactors.g:1277:6: lv_elseCall_8_0= ruleIfBody
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
    // $ANTLR end synpred32_InternalKactors

    // $ANTLR start synpred33_InternalKactors
    public final void synpred33_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_call_0_0 = null;


        // InternalKactors.g:1314:3: ( ( (lv_call_0_0= ruleCall ) ) )
        // InternalKactors.g:1314:3: ( (lv_call_0_0= ruleCall ) )
        {
        // InternalKactors.g:1314:3: ( (lv_call_0_0= ruleCall ) )
        // InternalKactors.g:1315:4: (lv_call_0_0= ruleCall )
        {
        // InternalKactors.g:1315:4: (lv_call_0_0= ruleCall )
        // InternalKactors.g:1316:5: lv_call_0_0= ruleCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getIfBodyAccess().getCallCallParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_call_0_0=ruleCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred33_InternalKactors

    // $ANTLR start synpred35_InternalKactors
    public final void synpred35_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_parameters_2_0 = null;


        // InternalKactors.g:1391:4: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )
        // InternalKactors.g:1391:4: otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')'
        {
        otherlv_1=(Token)match(input,24,FOLLOW_23); if (state.failed) return ;
        // InternalKactors.g:1395:4: ( (lv_parameters_2_0= ruleParameterList ) )?
        int alt42=2;
        int LA42_0 = input.LA(1);

        if ( ((LA42_0>=RULE_LOWERCASE_ID && LA42_0<=RULE_EXPR)||LA42_0==RULE_INT||(LA42_0>=30 && LA42_0<=31)||(LA42_0>=36 && LA42_0<=37)) ) {
            alt42=1;
        }
        switch (alt42) {
            case 1 :
                // InternalKactors.g:1396:5: (lv_parameters_2_0= ruleParameterList )
                {
                // InternalKactors.g:1396:5: (lv_parameters_2_0= ruleParameterList )
                // InternalKactors.g:1397:6: lv_parameters_2_0= ruleParameterList
                {
                if ( state.backtracking==0 ) {

                  						newCompositeNode(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0());
                  					
                }
                pushFollow(FOLLOW_24);
                lv_parameters_2_0=ruleParameterList();

                state._fsp--;
                if (state.failed) return ;

                }


                }
                break;

        }

        otherlv_3=(Token)match(input,26,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred35_InternalKactors

    // $ANTLR start synpred38_InternalKactors
    public final void synpred38_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_call_0_0 = null;


        // InternalKactors.g:1470:3: ( ( (lv_call_0_0= ruleCall ) ) )
        // InternalKactors.g:1470:3: ( (lv_call_0_0= ruleCall ) )
        {
        // InternalKactors.g:1470:3: ( (lv_call_0_0= ruleCall ) )
        // InternalKactors.g:1471:4: (lv_call_0_0= ruleCall )
        {
        // InternalKactors.g:1471:4: (lv_call_0_0= ruleCall )
        // InternalKactors.g:1472:5: lv_call_0_0= ruleCall
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getCallCallParserRuleCall_0_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_call_0_0=ruleCall();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred38_InternalKactors

    // $ANTLR start synpred39_InternalKactors
    public final void synpred39_InternalKactors_fragment() throws RecognitionException {   
        EObject lv_body_1_0 = null;


        // InternalKactors.g:1490:3: ( ( (lv_body_1_0= ruleBody ) ) )
        // InternalKactors.g:1490:3: ( (lv_body_1_0= ruleBody ) )
        {
        // InternalKactors.g:1490:3: ( (lv_body_1_0= ruleBody ) )
        // InternalKactors.g:1491:4: (lv_body_1_0= ruleBody )
        {
        // InternalKactors.g:1491:4: (lv_body_1_0= ruleBody )
        // InternalKactors.g:1492:5: lv_body_1_0= ruleBody
        {
        if ( state.backtracking==0 ) {

          					newCompositeNode(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_1_0());
          				
        }
        pushFollow(FOLLOW_2);
        lv_body_1_0=ruleBody();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred39_InternalKactors

    // $ANTLR start synpred45_InternalKactors
    public final void synpred45_InternalKactors_fragment() throws RecognitionException {   
        Token otherlv_10=null;
        EObject lv_literal_9_0 = null;

        EObject lv_body_11_0 = null;


        // InternalKactors.g:1729:3: ( ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) )
        // InternalKactors.g:1729:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
        {
        // InternalKactors.g:1729:3: ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) )
        // InternalKactors.g:1730:4: ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) )
        {
        // InternalKactors.g:1730:4: ( (lv_literal_9_0= ruleLiteral ) )
        // InternalKactors.g:1731:5: (lv_literal_9_0= ruleLiteral )
        {
        // InternalKactors.g:1731:5: (lv_literal_9_0= ruleLiteral )
        // InternalKactors.g:1732:6: lv_literal_9_0= ruleLiteral
        {
        if ( state.backtracking==0 ) {

          						newCompositeNode(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0());
          					
        }
        pushFollow(FOLLOW_29);
        lv_literal_9_0=ruleLiteral();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        otherlv_10=(Token)match(input,35,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:1753:4: ( (lv_body_11_0= ruleBody ) )
        // InternalKactors.g:1754:5: (lv_body_11_0= ruleBody )
        {
        // InternalKactors.g:1754:5: (lv_body_11_0= ruleBody )
        // InternalKactors.g:1755:6: lv_body_11_0= ruleBody
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
    // $ANTLR end synpred45_InternalKactors

    // $ANTLR start synpred46_InternalKactors
    public final void synpred46_InternalKactors_fragment() throws RecognitionException {   
        Token lv_text_12_0=null;
        Token otherlv_13=null;
        EObject lv_body_14_0 = null;


        // InternalKactors.g:1774:3: ( ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) )
        // InternalKactors.g:1774:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
        {
        // InternalKactors.g:1774:3: ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) )
        // InternalKactors.g:1775:4: ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) )
        {
        // InternalKactors.g:1775:4: ( (lv_text_12_0= RULE_STRING ) )
        // InternalKactors.g:1776:5: (lv_text_12_0= RULE_STRING )
        {
        // InternalKactors.g:1776:5: (lv_text_12_0= RULE_STRING )
        // InternalKactors.g:1777:6: lv_text_12_0= RULE_STRING
        {
        lv_text_12_0=(Token)match(input,RULE_STRING,FOLLOW_29); if (state.failed) return ;

        }


        }

        otherlv_13=(Token)match(input,35,FOLLOW_9); if (state.failed) return ;
        // InternalKactors.g:1797:4: ( (lv_body_14_0= ruleBody ) )
        // InternalKactors.g:1798:5: (lv_body_14_0= ruleBody )
        {
        // InternalKactors.g:1798:5: (lv_body_14_0= ruleBody )
        // InternalKactors.g:1799:6: lv_body_14_0= ruleBody
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
    // $ANTLR end synpred46_InternalKactors

    // $ANTLR start synpred49_InternalKactors
    public final void synpred49_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:1903:4: ( ( RULE_INT ) )
        // InternalKactors.g:1903:5: ( RULE_INT )
        {
        // InternalKactors.g:1903:5: ( RULE_INT )
        // InternalKactors.g:1904:5: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred49_InternalKactors

    // $ANTLR start synpred50_InternalKactors
    public final void synpred50_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:1925:4: ( ( 'l' ) )
        // InternalKactors.g:1925:5: ( 'l' )
        {
        // InternalKactors.g:1925:5: ( 'l' )
        // InternalKactors.g:1926:5: 'l'
        {
        match(input,38,FOLLOW_2); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred50_InternalKactors

    // $ANTLR start synpred51_InternalKactors
    public final void synpred51_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:1943:4: ( ( ( ( '.' ) ) ( ( RULE_INT ) ) ) )
        // InternalKactors.g:1943:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:1943:5: ( ( ( '.' ) ) ( ( RULE_INT ) ) )
        // InternalKactors.g:1944:5: ( ( '.' ) ) ( ( RULE_INT ) )
        {
        // InternalKactors.g:1944:5: ( ( '.' ) )
        // InternalKactors.g:1945:6: ( '.' )
        {
        // InternalKactors.g:1945:6: ( '.' )
        // InternalKactors.g:1946:7: '.'
        {
        match(input,39,FOLLOW_30); if (state.failed) return ;

        }


        }

        // InternalKactors.g:1949:5: ( ( RULE_INT ) )
        // InternalKactors.g:1950:6: ( RULE_INT )
        {
        // InternalKactors.g:1950:6: ( RULE_INT )
        // InternalKactors.g:1951:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred51_InternalKactors

    // $ANTLR start synpred55_InternalKactors
    public final void synpred55_InternalKactors_fragment() throws RecognitionException {   
        // InternalKactors.g:1992:4: ( ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) ) )
        // InternalKactors.g:1992:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        {
        // InternalKactors.g:1992:5: ( ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) ) )
        // InternalKactors.g:1993:5: ( ( ( 'e' | 'E' ) ) ) ( '+' | ( ( '-' ) ) )? ( ( RULE_INT ) )
        {
        // InternalKactors.g:1993:5: ( ( ( 'e' | 'E' ) ) )
        // InternalKactors.g:1994:6: ( ( 'e' | 'E' ) )
        {
        // InternalKactors.g:1994:6: ( ( 'e' | 'E' ) )
        // InternalKactors.g:1995:7: ( 'e' | 'E' )
        {
        if ( (input.LA(1)>=40 && input.LA(1)<=41) ) {
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

        // InternalKactors.g:2002:5: ( '+' | ( ( '-' ) ) )?
        int alt43=3;
        int LA43_0 = input.LA(1);

        if ( (LA43_0==36) ) {
            alt43=1;
        }
        else if ( (LA43_0==37) ) {
            alt43=2;
        }
        switch (alt43) {
            case 1 :
                // InternalKactors.g:2003:6: '+'
                {
                match(input,36,FOLLOW_30); if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalKactors.g:2005:6: ( ( '-' ) )
                {
                // InternalKactors.g:2005:6: ( ( '-' ) )
                // InternalKactors.g:2006:7: ( '-' )
                {
                // InternalKactors.g:2006:7: ( '-' )
                // InternalKactors.g:2007:8: '-'
                {
                match(input,37,FOLLOW_30); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // InternalKactors.g:2011:5: ( ( RULE_INT ) )
        // InternalKactors.g:2012:6: ( RULE_INT )
        {
        // InternalKactors.g:2012:6: ( RULE_INT )
        // InternalKactors.g:2013:7: RULE_INT
        {
        match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred55_InternalKactors

    // Delegated rules

    public final boolean synpred4_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred50_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred50_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred3_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalKactors_fragment(); // can never throw exception
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
    public final boolean synpred46_InternalKactors() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred46_InternalKactors_fragment(); // can never throw exception
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


    protected DFA12 dfa12 = new DFA12(this);
    protected DFA23 dfa23 = new DFA23(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA27 dfa27 = new DFA27(this);
    static final String dfa_1s = "\22\uffff";
    static final String dfa_2s = "\3\uffff\1\15\2\uffff\2\15\6\uffff\1\15\2\uffff\1\15";
    static final String dfa_3s = "\1\5\2\13\1\31\2\uffff\2\31\3\13\3\uffff\1\31\2\13\1\31";
    static final String dfa_4s = "\1\45\2\13\1\54\2\uffff\2\51\1\13\2\45\3\uffff\1\51\2\13\1\43";
    static final String dfa_5s = "\4\uffff\1\3\1\5\5\uffff\1\2\1\4\1\1\4\uffff";
    static final String dfa_6s = "\22\uffff}>";
    static final String[] dfa_7s = {
            "\1\4\5\uffff\1\3\22\uffff\2\5\4\uffff\1\1\1\2",
            "\1\6",
            "\1\6",
            "\2\15\2\uffff\1\13\5\uffff\1\15\1\uffff\1\14\1\7\1\10\1\11\1\12\3\14",
            "",
            "",
            "\2\15\2\uffff\1\13\5\uffff\1\15\2\uffff\1\7\1\10\1\11\1\12",
            "\2\15\2\uffff\1\13\5\uffff\1\15\3\uffff\1\10\1\11\1\12",
            "\1\16",
            "\1\21\30\uffff\1\17\1\20",
            "\1\21\30\uffff\1\17\1\20",
            "",
            "",
            "",
            "\2\15\2\uffff\1\13\5\uffff\1\15\4\uffff\1\11\1\12",
            "\1\21",
            "\1\21",
            "\2\15\2\uffff\1\13\5\uffff\1\15"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "775:2: ( ( (lv_number_0_0= ruleNumber ) ) | ( ( (lv_from_1_0= ruleNumber ) ) otherlv_2= 'to' ( (lv_to_3_0= ruleNumber ) ) ) | ( (lv_string_4_0= RULE_STRING ) ) | ( (lv_date_5_0= ruleDate ) ) | ( ( (lv_boolean_6_1= 'true' | lv_boolean_6_2= 'false' ) ) ) )";
        }
    }
    static final String dfa_8s = "\11\uffff";
    static final String dfa_9s = "\1\2\4\uffff\1\4\3\uffff";
    static final String dfa_10s = "\2\4\1\uffff\1\4\1\uffff\1\4\1\0\1\4\1\31";
    static final String dfa_11s = "\2\45\1\uffff\1\42\1\uffff\1\45\1\0\1\45\1\34";
    static final String dfa_12s = "\2\uffff\1\2\1\uffff\1\1\4\uffff";
    static final String dfa_13s = "\6\uffff\1\0\2\uffff}>";
    static final String[] dfa_14s = {
            "\2\2\1\uffff\1\2\1\uffff\3\2\12\uffff\2\2\1\1\1\uffff\1\2\3\uffff\5\2\1\uffff\2\2",
            "\1\3\4\4\1\2\1\uffff\1\4\14\uffff\1\2\1\uffff\1\5\3\uffff\2\4\1\2\3\uffff\2\4",
            "",
            "\1\2\4\uffff\1\2\15\uffff\2\2\1\7\1\6\2\4\3\uffff\1\2\1\uffff\1\2",
            "",
            "\2\4\1\uffff\1\4\1\uffff\3\4\12\uffff\3\4\1\uffff\1\4\3\uffff\5\4\1\2\2\4",
            "\1\uffff",
            "\1\10\4\4\2\uffff\1\4\22\uffff\2\4\4\uffff\2\4",
            "\1\7\1\5\2\4"
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "1390:3: (otherlv_1= '(' ( (lv_parameters_2_0= ruleParameterList ) )? otherlv_3= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA23_6 = input.LA(1);

                         
                        int index23_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred35_InternalKactors()) ) {s = 4;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index23_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 23, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_15s = "\15\uffff";
    static final String dfa_16s = "\10\uffff\1\2\1\uffff\1\2\2\uffff";
    static final String dfa_17s = "\1\4\1\43\1\uffff\1\4\2\uffff\3\4\1\uffff\3\4";
    static final String dfa_18s = "\1\45\1\43\1\uffff\1\45\2\uffff\1\43\1\40\1\45\1\uffff\1\45\1\42\1\43";
    static final String dfa_19s = "\2\uffff\1\2\1\uffff\1\3\1\1\3\uffff\1\4\3\uffff";
    static final String dfa_20s = "\1\uffff\1\0\13\uffff}>";
    static final String[] dfa_21s = {
            "\1\1\1\4\1\uffff\1\4\1\uffff\1\2\2\4\14\uffff\1\3\5\uffff\2\4\1\2\3\uffff\2\4",
            "\1\4",
            "",
            "\1\6\1\11\1\uffff\1\11\1\uffff\1\2\2\11\14\uffff\1\7\1\uffff\1\10\3\uffff\2\11\1\2\3\uffff\2\11",
            "",
            "",
            "\1\2\4\uffff\1\2\15\uffff\2\2\1\4\1\12\5\uffff\1\2\1\uffff\1\2\1\11",
            "\1\13\4\uffff\1\2\16\uffff\1\2\1\uffff\1\11\5\uffff\1\2",
            "\2\2\1\uffff\1\2\1\uffff\3\2\12\uffff\1\2\1\uffff\1\2\1\uffff\1\2\3\uffff\4\2\1\uffff\1\4\2\2",
            "",
            "\2\2\1\uffff\1\2\1\uffff\3\2\12\uffff\1\2\1\uffff\1\2\1\uffff\1\2\3\uffff\4\2\1\uffff\1\4\2\2",
            "\1\2\4\uffff\1\2\15\uffff\2\2\1\11\1\14\5\uffff\1\2\1\uffff\1\2",
            "\1\2\4\uffff\1\2\16\uffff\1\2\1\uffff\1\2\5\uffff\1\2\2\uffff\1\11"
    };

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final char[] dfa_18 = DFA.unpackEncodedStringToUnsignedChars(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final short[][] dfa_21 = unpackEncodedStringArray(dfa_21s);

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = dfa_15;
            this.eof = dfa_16;
            this.min = dfa_17;
            this.max = dfa_18;
            this.accept = dfa_19;
            this.special = dfa_20;
            this.transition = dfa_21;
        }
        public String getDescription() {
            return "1469:2: ( ( (lv_call_0_0= ruleCall ) ) | ( (lv_body_1_0= ruleBody ) ) | ( (lv_match_2_0= ruleMatch ) ) | (otherlv_3= '(' ( (lv_matches_4_0= ruleMatch ) ) ( (lv_matches_5_0= ruleMatch ) )* otherlv_6= ')' ) )";
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
                        if ( (LA26_1==35) ) {s = 4;}

                        else if ( (synpred38_InternalKactors()) ) {s = 5;}

                        else if ( (synpred39_InternalKactors()) ) {s = 2;}

                         
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
    static final String dfa_22s = "\14\uffff";
    static final String dfa_23s = "\1\4\6\uffff\1\0\4\uffff";
    static final String dfa_24s = "\1\45\6\uffff\1\0\4\uffff";
    static final String dfa_25s = "\1\uffff\1\1\1\2\1\3\1\4\5\uffff\1\6\1\5";
    static final String dfa_26s = "\7\uffff\1\0\4\uffff}>";
    static final String[] dfa_27s = {
            "\1\1\1\7\1\uffff\1\3\2\uffff\1\2\1\4\14\uffff\1\12\5\uffff\2\4\4\uffff\2\4",
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
            ""
    };

    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_22;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "1596:2: ( ( ( (lv_id_0_0= RULE_LOWERCASE_ID ) ) otherlv_1= '->' ( (lv_body_2_0= ruleBody ) ) ) | ( ( (lv_regexp_3_0= RULE_REGEXP ) ) otherlv_4= '->' ( (lv_body_5_0= ruleBody ) ) ) | ( ( (lv_observable_6_0= RULE_OBSERVABLE ) ) otherlv_7= '->' ( (lv_body_8_0= ruleBody ) ) ) | ( ( (lv_literal_9_0= ruleLiteral ) ) otherlv_10= '->' ( (lv_body_11_0= ruleBody ) ) ) | ( ( (lv_text_12_0= RULE_STRING ) ) otherlv_13= '->' ( (lv_body_14_0= ruleBody ) ) ) | ( ( (lv_arguments_15_0= ruleArgumentDeclaration ) ) otherlv_16= '->' ( (lv_body_17_0= ruleBody ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_7 = input.LA(1);

                         
                        int index27_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred45_InternalKactors()) ) {s = 4;}

                        else if ( (synpred46_InternalKactors()) ) {s = 11;}

                         
                        input.seek(index27_7);
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
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000000003C0002L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000001800000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000101000210L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000004000010L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000006000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000030C00009F0L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000018000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000003000000800L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000101000212L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000105000210L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000401800002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000030C40009F0L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000400800002L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000031C1000EB0L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000030C1000CB0L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00000030C5000CB0L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x000003C000000002L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000038000000002L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000030000000002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x00001C2000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000008000000002L});

}
