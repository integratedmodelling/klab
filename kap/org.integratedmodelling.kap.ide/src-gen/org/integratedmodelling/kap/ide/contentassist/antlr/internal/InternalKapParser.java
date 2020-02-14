package org.integratedmodelling.kap.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.integratedmodelling.kap.services.KapGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalKapParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_LOWERCASE_ID", "RULE_STRING", "RULE_ARGVALUE", "RULE_OBSERVABLE", "RULE_EXPR", "RULE_EMBEDDEDTEXT", "RULE_REGEXP", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'='", "'true'", "'false'", "';'", "'+'", "'e'", "'E'", "'AD'", "'CE'", "'name'", "'worldview'", "'permissions'", "'author'", "'version'", "'def'", "':'", "'('", "')'", "','", "'to'", "'if'", "'else'", "'->'", "'-'", "'.'", "'=?'", "'l'", "'BC'"
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


        public InternalKapParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalKapParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalKapParser.tokenNames; }
    public String getGrammarFileName() { return "InternalKap.g"; }


    	private KapGrammarAccess grammarAccess;

    	public void setGrammarAccess(KapGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleModel"
    // InternalKap.g:54:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // InternalKap.g:55:1: ( ruleModel EOF )
            // InternalKap.g:56:1: ruleModel EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleModel();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalKap.g:63:1: ruleModel : ( ( rule__Model__Group__0 ) ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:67:2: ( ( ( rule__Model__Group__0 ) ) )
            // InternalKap.g:68:2: ( ( rule__Model__Group__0 ) )
            {
            // InternalKap.g:68:2: ( ( rule__Model__Group__0 ) )
            // InternalKap.g:69:3: ( rule__Model__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getGroup()); 
            }
            // InternalKap.g:70:3: ( rule__Model__Group__0 )
            // InternalKap.g:70:4: rule__Model__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Model__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRulePreamble"
    // InternalKap.g:79:1: entryRulePreamble : rulePreamble EOF ;
    public final void entryRulePreamble() throws RecognitionException {
        try {
            // InternalKap.g:80:1: ( rulePreamble EOF )
            // InternalKap.g:81:1: rulePreamble EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleRule()); 
            }
            pushFollow(FOLLOW_1);
            rulePreamble();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePreamble"


    // $ANTLR start "rulePreamble"
    // InternalKap.g:88:1: rulePreamble : ( ( rule__Preamble__Group__0 ) ) ;
    public final void rulePreamble() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:92:2: ( ( ( rule__Preamble__Group__0 ) ) )
            // InternalKap.g:93:2: ( ( rule__Preamble__Group__0 ) )
            {
            // InternalKap.g:93:2: ( ( rule__Preamble__Group__0 ) )
            // InternalKap.g:94:3: ( rule__Preamble__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getGroup()); 
            }
            // InternalKap.g:95:3: ( rule__Preamble__Group__0 )
            // InternalKap.g:95:4: rule__Preamble__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePreamble"


    // $ANTLR start "entryRuleDefinition"
    // InternalKap.g:104:1: entryRuleDefinition : ruleDefinition EOF ;
    public final void entryRuleDefinition() throws RecognitionException {
        try {
            // InternalKap.g:105:1: ( ruleDefinition EOF )
            // InternalKap.g:106:1: ruleDefinition EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleDefinition();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDefinition"


    // $ANTLR start "ruleDefinition"
    // InternalKap.g:113:1: ruleDefinition : ( ( rule__Definition__Group__0 ) ) ;
    public final void ruleDefinition() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:117:2: ( ( ( rule__Definition__Group__0 ) ) )
            // InternalKap.g:118:2: ( ( rule__Definition__Group__0 ) )
            {
            // InternalKap.g:118:2: ( ( rule__Definition__Group__0 ) )
            // InternalKap.g:119:3: ( rule__Definition__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getGroup()); 
            }
            // InternalKap.g:120:3: ( rule__Definition__Group__0 )
            // InternalKap.g:120:4: rule__Definition__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Definition__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDefinition"


    // $ANTLR start "entryRuleArgumentDeclaration"
    // InternalKap.g:129:1: entryRuleArgumentDeclaration : ruleArgumentDeclaration EOF ;
    public final void entryRuleArgumentDeclaration() throws RecognitionException {
        try {
            // InternalKap.g:130:1: ( ruleArgumentDeclaration EOF )
            // InternalKap.g:131:1: ruleArgumentDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleArgumentDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleArgumentDeclaration"


    // $ANTLR start "ruleArgumentDeclaration"
    // InternalKap.g:138:1: ruleArgumentDeclaration : ( ( rule__ArgumentDeclaration__Group__0 ) ) ;
    public final void ruleArgumentDeclaration() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:142:2: ( ( ( rule__ArgumentDeclaration__Group__0 ) ) )
            // InternalKap.g:143:2: ( ( rule__ArgumentDeclaration__Group__0 ) )
            {
            // InternalKap.g:143:2: ( ( rule__ArgumentDeclaration__Group__0 ) )
            // InternalKap.g:144:3: ( rule__ArgumentDeclaration__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getGroup()); 
            }
            // InternalKap.g:145:3: ( rule__ArgumentDeclaration__Group__0 )
            // InternalKap.g:145:4: rule__ArgumentDeclaration__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleArgumentDeclaration"


    // $ANTLR start "entryRuleParameterList"
    // InternalKap.g:154:1: entryRuleParameterList : ruleParameterList EOF ;
    public final void entryRuleParameterList() throws RecognitionException {
        try {
            // InternalKap.g:155:1: ( ruleParameterList EOF )
            // InternalKap.g:156:1: ruleParameterList EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleParameterList();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleParameterList"


    // $ANTLR start "ruleParameterList"
    // InternalKap.g:163:1: ruleParameterList : ( ( rule__ParameterList__Group__0 ) ) ;
    public final void ruleParameterList() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:167:2: ( ( ( rule__ParameterList__Group__0 ) ) )
            // InternalKap.g:168:2: ( ( rule__ParameterList__Group__0 ) )
            {
            // InternalKap.g:168:2: ( ( rule__ParameterList__Group__0 ) )
            // InternalKap.g:169:3: ( rule__ParameterList__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getGroup()); 
            }
            // InternalKap.g:170:3: ( rule__ParameterList__Group__0 )
            // InternalKap.g:170:4: rule__ParameterList__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleParameterList"


    // $ANTLR start "entryRuleKeyValuePair"
    // InternalKap.g:179:1: entryRuleKeyValuePair : ruleKeyValuePair EOF ;
    public final void entryRuleKeyValuePair() throws RecognitionException {
        try {
            // InternalKap.g:180:1: ( ruleKeyValuePair EOF )
            // InternalKap.g:181:1: ruleKeyValuePair EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleKeyValuePair"


    // $ANTLR start "ruleKeyValuePair"
    // InternalKap.g:188:1: ruleKeyValuePair : ( ( rule__KeyValuePair__Group__0 ) ) ;
    public final void ruleKeyValuePair() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:192:2: ( ( ( rule__KeyValuePair__Group__0 ) ) )
            // InternalKap.g:193:2: ( ( rule__KeyValuePair__Group__0 ) )
            {
            // InternalKap.g:193:2: ( ( rule__KeyValuePair__Group__0 ) )
            // InternalKap.g:194:3: ( rule__KeyValuePair__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getGroup()); 
            }
            // InternalKap.g:195:3: ( rule__KeyValuePair__Group__0 )
            // InternalKap.g:195:4: rule__KeyValuePair__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleKeyValuePair"


    // $ANTLR start "entryRuleValue"
    // InternalKap.g:204:1: entryRuleValue : ruleValue EOF ;
    public final void entryRuleValue() throws RecognitionException {
        try {
            // InternalKap.g:205:1: ( ruleValue EOF )
            // InternalKap.g:206:1: ruleValue EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // InternalKap.g:213:1: ruleValue : ( ( rule__Value__Alternatives ) ) ;
    public final void ruleValue() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:217:2: ( ( ( rule__Value__Alternatives ) ) )
            // InternalKap.g:218:2: ( ( rule__Value__Alternatives ) )
            {
            // InternalKap.g:218:2: ( ( rule__Value__Alternatives ) )
            // InternalKap.g:219:3: ( rule__Value__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getAlternatives()); 
            }
            // InternalKap.g:220:3: ( rule__Value__Alternatives )
            // InternalKap.g:220:4: rule__Value__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Value__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleLiteral"
    // InternalKap.g:229:1: entryRuleLiteral : ruleLiteral EOF ;
    public final void entryRuleLiteral() throws RecognitionException {
        try {
            // InternalKap.g:230:1: ( ruleLiteral EOF )
            // InternalKap.g:231:1: ruleLiteral EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleLiteral();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLiteral"


    // $ANTLR start "ruleLiteral"
    // InternalKap.g:238:1: ruleLiteral : ( ( rule__Literal__Alternatives ) ) ;
    public final void ruleLiteral() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:242:2: ( ( ( rule__Literal__Alternatives ) ) )
            // InternalKap.g:243:2: ( ( rule__Literal__Alternatives ) )
            {
            // InternalKap.g:243:2: ( ( rule__Literal__Alternatives ) )
            // InternalKap.g:244:3: ( rule__Literal__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getAlternatives()); 
            }
            // InternalKap.g:245:3: ( rule__Literal__Alternatives )
            // InternalKap.g:245:4: rule__Literal__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Literal__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLiteral"


    // $ANTLR start "entryRuleBody"
    // InternalKap.g:254:1: entryRuleBody : ruleBody EOF ;
    public final void entryRuleBody() throws RecognitionException {
        try {
            // InternalKap.g:255:1: ( ruleBody EOF )
            // InternalKap.g:256:1: ruleBody EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBody"


    // $ANTLR start "ruleBody"
    // InternalKap.g:263:1: ruleBody : ( ( rule__Body__Alternatives ) ) ;
    public final void ruleBody() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:267:2: ( ( ( rule__Body__Alternatives ) ) )
            // InternalKap.g:268:2: ( ( rule__Body__Alternatives ) )
            {
            // InternalKap.g:268:2: ( ( rule__Body__Alternatives ) )
            // InternalKap.g:269:3: ( rule__Body__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getAlternatives()); 
            }
            // InternalKap.g:270:3: ( rule__Body__Alternatives )
            // InternalKap.g:270:4: rule__Body__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Body__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBody"


    // $ANTLR start "entryRuleStatement"
    // InternalKap.g:279:1: entryRuleStatement : ruleStatement EOF ;
    public final void entryRuleStatement() throws RecognitionException {
        try {
            // InternalKap.g:280:1: ( ruleStatement EOF )
            // InternalKap.g:281:1: ruleStatement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStatement"


    // $ANTLR start "ruleStatement"
    // InternalKap.g:288:1: ruleStatement : ( ( rule__Statement__Alternatives ) ) ;
    public final void ruleStatement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:292:2: ( ( ( rule__Statement__Alternatives ) ) )
            // InternalKap.g:293:2: ( ( rule__Statement__Alternatives ) )
            {
            // InternalKap.g:293:2: ( ( rule__Statement__Alternatives ) )
            // InternalKap.g:294:3: ( rule__Statement__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getAlternatives()); 
            }
            // InternalKap.g:295:3: ( rule__Statement__Alternatives )
            // InternalKap.g:295:4: rule__Statement__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Statement__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStatement"


    // $ANTLR start "entryRuleIfStatement"
    // InternalKap.g:304:1: entryRuleIfStatement : ruleIfStatement EOF ;
    public final void entryRuleIfStatement() throws RecognitionException {
        try {
            // InternalKap.g:305:1: ( ruleIfStatement EOF )
            // InternalKap.g:306:1: ruleIfStatement EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleIfStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleIfStatement"


    // $ANTLR start "ruleIfStatement"
    // InternalKap.g:313:1: ruleIfStatement : ( ( rule__IfStatement__Group__0 ) ) ;
    public final void ruleIfStatement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:317:2: ( ( ( rule__IfStatement__Group__0 ) ) )
            // InternalKap.g:318:2: ( ( rule__IfStatement__Group__0 ) )
            {
            // InternalKap.g:318:2: ( ( rule__IfStatement__Group__0 ) )
            // InternalKap.g:319:3: ( rule__IfStatement__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getGroup()); 
            }
            // InternalKap.g:320:3: ( rule__IfStatement__Group__0 )
            // InternalKap.g:320:4: rule__IfStatement__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIfStatement"


    // $ANTLR start "entryRuleIfBody"
    // InternalKap.g:329:1: entryRuleIfBody : ruleIfBody EOF ;
    public final void entryRuleIfBody() throws RecognitionException {
        try {
            // InternalKap.g:330:1: ( ruleIfBody EOF )
            // InternalKap.g:331:1: ruleIfBody EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfBodyRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleIfBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfBodyRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleIfBody"


    // $ANTLR start "ruleIfBody"
    // InternalKap.g:338:1: ruleIfBody : ( ( rule__IfBody__Alternatives ) ) ;
    public final void ruleIfBody() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:342:2: ( ( ( rule__IfBody__Alternatives ) ) )
            // InternalKap.g:343:2: ( ( rule__IfBody__Alternatives ) )
            {
            // InternalKap.g:343:2: ( ( rule__IfBody__Alternatives ) )
            // InternalKap.g:344:3: ( rule__IfBody__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfBodyAccess().getAlternatives()); 
            }
            // InternalKap.g:345:3: ( rule__IfBody__Alternatives )
            // InternalKap.g:345:4: rule__IfBody__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__IfBody__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfBodyAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIfBody"


    // $ANTLR start "entryRuleCall"
    // InternalKap.g:354:1: entryRuleCall : ruleCall EOF ;
    public final void entryRuleCall() throws RecognitionException {
        try {
            // InternalKap.g:355:1: ( ruleCall EOF )
            // InternalKap.g:356:1: ruleCall EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCall"


    // $ANTLR start "ruleCall"
    // InternalKap.g:363:1: ruleCall : ( ( rule__Call__Group__0 ) ) ;
    public final void ruleCall() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:367:2: ( ( ( rule__Call__Group__0 ) ) )
            // InternalKap.g:368:2: ( ( rule__Call__Group__0 ) )
            {
            // InternalKap.g:368:2: ( ( rule__Call__Group__0 ) )
            // InternalKap.g:369:3: ( rule__Call__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getGroup()); 
            }
            // InternalKap.g:370:3: ( rule__Call__Group__0 )
            // InternalKap.g:370:4: rule__Call__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Call__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCall"


    // $ANTLR start "entryRuleActions"
    // InternalKap.g:379:1: entryRuleActions : ruleActions EOF ;
    public final void entryRuleActions() throws RecognitionException {
        try {
            // InternalKap.g:380:1: ( ruleActions EOF )
            // InternalKap.g:381:1: ruleActions EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleActions();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleActions"


    // $ANTLR start "ruleActions"
    // InternalKap.g:388:1: ruleActions : ( ( rule__Actions__Alternatives ) ) ;
    public final void ruleActions() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:392:2: ( ( ( rule__Actions__Alternatives ) ) )
            // InternalKap.g:393:2: ( ( rule__Actions__Alternatives ) )
            {
            // InternalKap.g:393:2: ( ( rule__Actions__Alternatives ) )
            // InternalKap.g:394:3: ( rule__Actions__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getAlternatives()); 
            }
            // InternalKap.g:395:3: ( rule__Actions__Alternatives )
            // InternalKap.g:395:4: rule__Actions__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Actions__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleActions"


    // $ANTLR start "entryRuleMatch"
    // InternalKap.g:404:1: entryRuleMatch : ruleMatch EOF ;
    public final void entryRuleMatch() throws RecognitionException {
        try {
            // InternalKap.g:405:1: ( ruleMatch EOF )
            // InternalKap.g:406:1: ruleMatch EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleMatch();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMatch"


    // $ANTLR start "ruleMatch"
    // InternalKap.g:413:1: ruleMatch : ( ( rule__Match__Alternatives ) ) ;
    public final void ruleMatch() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:417:2: ( ( ( rule__Match__Alternatives ) ) )
            // InternalKap.g:418:2: ( ( rule__Match__Alternatives ) )
            {
            // InternalKap.g:418:2: ( ( rule__Match__Alternatives ) )
            // InternalKap.g:419:3: ( rule__Match__Alternatives )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getAlternatives()); 
            }
            // InternalKap.g:420:3: ( rule__Match__Alternatives )
            // InternalKap.g:420:4: rule__Match__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Match__Alternatives();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getAlternatives()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMatch"


    // $ANTLR start "entryRuleNumber"
    // InternalKap.g:429:1: entryRuleNumber : ruleNumber EOF ;
    public final void entryRuleNumber() throws RecognitionException {
        try {
            // InternalKap.g:430:1: ( ruleNumber EOF )
            // InternalKap.g:431:1: ruleNumber EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNumber"


    // $ANTLR start "ruleNumber"
    // InternalKap.g:438:1: ruleNumber : ( ( rule__Number__Group__0 ) ) ;
    public final void ruleNumber() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:442:2: ( ( ( rule__Number__Group__0 ) ) )
            // InternalKap.g:443:2: ( ( rule__Number__Group__0 ) )
            {
            // InternalKap.g:443:2: ( ( rule__Number__Group__0 ) )
            // InternalKap.g:444:3: ( rule__Number__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup()); 
            }
            // InternalKap.g:445:3: ( rule__Number__Group__0 )
            // InternalKap.g:445:4: rule__Number__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNumber"


    // $ANTLR start "entryRuleDate"
    // InternalKap.g:454:1: entryRuleDate : ruleDate EOF ;
    public final void entryRuleDate() throws RecognitionException {
        try {
            // InternalKap.g:455:1: ( ruleDate EOF )
            // InternalKap.g:456:1: ruleDate EOF
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateRule()); 
            }
            pushFollow(FOLLOW_1);
            ruleDate();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateRule()); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDate"


    // $ANTLR start "ruleDate"
    // InternalKap.g:463:1: ruleDate : ( ( rule__Date__Group__0 ) ) ;
    public final void ruleDate() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:467:2: ( ( ( rule__Date__Group__0 ) ) )
            // InternalKap.g:468:2: ( ( rule__Date__Group__0 ) )
            {
            // InternalKap.g:468:2: ( ( rule__Date__Group__0 ) )
            // InternalKap.g:469:3: ( rule__Date__Group__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getGroup()); 
            }
            // InternalKap.g:470:3: ( rule__Date__Group__0 )
            // InternalKap.g:470:4: rule__Date__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getGroup()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDate"


    // $ANTLR start "rule__KeyValuePair__Alternatives_0_1"
    // InternalKap.g:478:1: rule__KeyValuePair__Alternatives_0_1 : ( ( ( rule__KeyValuePair__InteractiveAssignment_0_1_0 ) ) | ( '=' ) );
    public final void rule__KeyValuePair__Alternatives_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:482:1: ( ( ( rule__KeyValuePair__InteractiveAssignment_0_1_0 ) ) | ( '=' ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==42) ) {
                alt1=1;
            }
            else if ( (LA1_0==17) ) {
                alt1=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalKap.g:483:2: ( ( rule__KeyValuePair__InteractiveAssignment_0_1_0 ) )
                    {
                    // InternalKap.g:483:2: ( ( rule__KeyValuePair__InteractiveAssignment_0_1_0 ) )
                    // InternalKap.g:484:3: ( rule__KeyValuePair__InteractiveAssignment_0_1_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getKeyValuePairAccess().getInteractiveAssignment_0_1_0()); 
                    }
                    // InternalKap.g:485:3: ( rule__KeyValuePair__InteractiveAssignment_0_1_0 )
                    // InternalKap.g:485:4: rule__KeyValuePair__InteractiveAssignment_0_1_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__KeyValuePair__InteractiveAssignment_0_1_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getKeyValuePairAccess().getInteractiveAssignment_0_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:489:2: ( '=' )
                    {
                    // InternalKap.g:489:2: ( '=' )
                    // InternalKap.g:490:3: '='
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1()); 
                    }
                    match(input,17,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getKeyValuePairAccess().getEqualsSignKeyword_0_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Alternatives_0_1"


    // $ANTLR start "rule__Value__Alternatives"
    // InternalKap.g:499:1: rule__Value__Alternatives : ( ( ( rule__Value__ArgvalueAssignment_0 ) ) | ( ( rule__Value__LiteralAssignment_1 ) ) | ( ( rule__Value__IdAssignment_2 ) ) | ( ( rule__Value__ObservableAssignment_3 ) ) | ( ( rule__Value__ExpressionAssignment_4 ) ) );
    public final void rule__Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:503:1: ( ( ( rule__Value__ArgvalueAssignment_0 ) ) | ( ( rule__Value__LiteralAssignment_1 ) ) | ( ( rule__Value__IdAssignment_2 ) ) | ( ( rule__Value__ObservableAssignment_3 ) ) | ( ( rule__Value__ExpressionAssignment_4 ) ) )
            int alt2=5;
            switch ( input.LA(1) ) {
            case RULE_ARGVALUE:
                {
                alt2=1;
                }
                break;
            case RULE_STRING:
            case RULE_INT:
            case 18:
            case 19:
            case 21:
            case 40:
                {
                alt2=2;
                }
                break;
            case RULE_LOWERCASE_ID:
                {
                alt2=3;
                }
                break;
            case RULE_OBSERVABLE:
                {
                alt2=4;
                }
                break;
            case RULE_EXPR:
                {
                alt2=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalKap.g:504:2: ( ( rule__Value__ArgvalueAssignment_0 ) )
                    {
                    // InternalKap.g:504:2: ( ( rule__Value__ArgvalueAssignment_0 ) )
                    // InternalKap.g:505:3: ( rule__Value__ArgvalueAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getArgvalueAssignment_0()); 
                    }
                    // InternalKap.g:506:3: ( rule__Value__ArgvalueAssignment_0 )
                    // InternalKap.g:506:4: rule__Value__ArgvalueAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__ArgvalueAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getArgvalueAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:510:2: ( ( rule__Value__LiteralAssignment_1 ) )
                    {
                    // InternalKap.g:510:2: ( ( rule__Value__LiteralAssignment_1 ) )
                    // InternalKap.g:511:3: ( rule__Value__LiteralAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getLiteralAssignment_1()); 
                    }
                    // InternalKap.g:512:3: ( rule__Value__LiteralAssignment_1 )
                    // InternalKap.g:512:4: rule__Value__LiteralAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__LiteralAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getLiteralAssignment_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:516:2: ( ( rule__Value__IdAssignment_2 ) )
                    {
                    // InternalKap.g:516:2: ( ( rule__Value__IdAssignment_2 ) )
                    // InternalKap.g:517:3: ( rule__Value__IdAssignment_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getIdAssignment_2()); 
                    }
                    // InternalKap.g:518:3: ( rule__Value__IdAssignment_2 )
                    // InternalKap.g:518:4: rule__Value__IdAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__IdAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getIdAssignment_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:522:2: ( ( rule__Value__ObservableAssignment_3 ) )
                    {
                    // InternalKap.g:522:2: ( ( rule__Value__ObservableAssignment_3 ) )
                    // InternalKap.g:523:3: ( rule__Value__ObservableAssignment_3 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getObservableAssignment_3()); 
                    }
                    // InternalKap.g:524:3: ( rule__Value__ObservableAssignment_3 )
                    // InternalKap.g:524:4: rule__Value__ObservableAssignment_3
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__ObservableAssignment_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getObservableAssignment_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalKap.g:528:2: ( ( rule__Value__ExpressionAssignment_4 ) )
                    {
                    // InternalKap.g:528:2: ( ( rule__Value__ExpressionAssignment_4 ) )
                    // InternalKap.g:529:3: ( rule__Value__ExpressionAssignment_4 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getValueAccess().getExpressionAssignment_4()); 
                    }
                    // InternalKap.g:530:3: ( rule__Value__ExpressionAssignment_4 )
                    // InternalKap.g:530:4: rule__Value__ExpressionAssignment_4
                    {
                    pushFollow(FOLLOW_2);
                    rule__Value__ExpressionAssignment_4();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getValueAccess().getExpressionAssignment_4()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__Alternatives"


    // $ANTLR start "rule__Literal__Alternatives"
    // InternalKap.g:538:1: rule__Literal__Alternatives : ( ( ( rule__Literal__NumberAssignment_0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__StringAssignment_2 ) ) | ( ( rule__Literal__DateAssignment_3 ) ) | ( ( rule__Literal__BooleanAssignment_4 ) ) );
    public final void rule__Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:542:1: ( ( ( rule__Literal__NumberAssignment_0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__StringAssignment_2 ) ) | ( ( rule__Literal__DateAssignment_3 ) ) | ( ( rule__Literal__BooleanAssignment_4 ) ) )
            int alt3=5;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // InternalKap.g:543:2: ( ( rule__Literal__NumberAssignment_0 ) )
                    {
                    // InternalKap.g:543:2: ( ( rule__Literal__NumberAssignment_0 ) )
                    // InternalKap.g:544:3: ( rule__Literal__NumberAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getNumberAssignment_0()); 
                    }
                    // InternalKap.g:545:3: ( rule__Literal__NumberAssignment_0 )
                    // InternalKap.g:545:4: rule__Literal__NumberAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__NumberAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getNumberAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:549:2: ( ( rule__Literal__Group_1__0 ) )
                    {
                    // InternalKap.g:549:2: ( ( rule__Literal__Group_1__0 ) )
                    // InternalKap.g:550:3: ( rule__Literal__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getGroup_1()); 
                    }
                    // InternalKap.g:551:3: ( rule__Literal__Group_1__0 )
                    // InternalKap.g:551:4: rule__Literal__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:555:2: ( ( rule__Literal__StringAssignment_2 ) )
                    {
                    // InternalKap.g:555:2: ( ( rule__Literal__StringAssignment_2 ) )
                    // InternalKap.g:556:3: ( rule__Literal__StringAssignment_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getStringAssignment_2()); 
                    }
                    // InternalKap.g:557:3: ( rule__Literal__StringAssignment_2 )
                    // InternalKap.g:557:4: rule__Literal__StringAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__StringAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getStringAssignment_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:561:2: ( ( rule__Literal__DateAssignment_3 ) )
                    {
                    // InternalKap.g:561:2: ( ( rule__Literal__DateAssignment_3 ) )
                    // InternalKap.g:562:3: ( rule__Literal__DateAssignment_3 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getDateAssignment_3()); 
                    }
                    // InternalKap.g:563:3: ( rule__Literal__DateAssignment_3 )
                    // InternalKap.g:563:4: rule__Literal__DateAssignment_3
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__DateAssignment_3();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getDateAssignment_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalKap.g:567:2: ( ( rule__Literal__BooleanAssignment_4 ) )
                    {
                    // InternalKap.g:567:2: ( ( rule__Literal__BooleanAssignment_4 ) )
                    // InternalKap.g:568:3: ( rule__Literal__BooleanAssignment_4 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getBooleanAssignment_4()); 
                    }
                    // InternalKap.g:569:3: ( rule__Literal__BooleanAssignment_4 )
                    // InternalKap.g:569:4: rule__Literal__BooleanAssignment_4
                    {
                    pushFollow(FOLLOW_2);
                    rule__Literal__BooleanAssignment_4();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getBooleanAssignment_4()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Alternatives"


    // $ANTLR start "rule__Literal__BooleanAlternatives_4_0"
    // InternalKap.g:577:1: rule__Literal__BooleanAlternatives_4_0 : ( ( 'true' ) | ( 'false' ) );
    public final void rule__Literal__BooleanAlternatives_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:581:1: ( ( 'true' ) | ( 'false' ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==18) ) {
                alt4=1;
            }
            else if ( (LA4_0==19) ) {
                alt4=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalKap.g:582:2: ( 'true' )
                    {
                    // InternalKap.g:582:2: ( 'true' )
                    // InternalKap.g:583:3: 'true'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getBooleanTrueKeyword_4_0_0()); 
                    }
                    match(input,18,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getBooleanTrueKeyword_4_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:588:2: ( 'false' )
                    {
                    // InternalKap.g:588:2: ( 'false' )
                    // InternalKap.g:589:3: 'false'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getLiteralAccess().getBooleanFalseKeyword_4_0_1()); 
                    }
                    match(input,19,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getLiteralAccess().getBooleanFalseKeyword_4_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__BooleanAlternatives_4_0"


    // $ANTLR start "rule__Body__Alternatives"
    // InternalKap.g:598:1: rule__Body__Alternatives : ( ( ( rule__Body__Group_0__0 ) ) | ( ( rule__Body__Group_1__0 ) ) );
    public final void rule__Body__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:602:1: ( ( ( rule__Body__Group_0__0 ) ) | ( ( rule__Body__Group_1__0 ) ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_LOWERCASE_ID||LA5_0==RULE_EMBEDDEDTEXT||LA5_0==37) ) {
                alt5=1;
            }
            else if ( (LA5_0==33) ) {
                int LA5_4 = input.LA(2);

                if ( (synpred11_InternalKap()) ) {
                    alt5=1;
                }
                else if ( (true) ) {
                    alt5=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 4, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalKap.g:603:2: ( ( rule__Body__Group_0__0 ) )
                    {
                    // InternalKap.g:603:2: ( ( rule__Body__Group_0__0 ) )
                    // InternalKap.g:604:3: ( rule__Body__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBodyAccess().getGroup_0()); 
                    }
                    // InternalKap.g:605:3: ( rule__Body__Group_0__0 )
                    // InternalKap.g:605:4: rule__Body__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Body__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBodyAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:609:2: ( ( rule__Body__Group_1__0 ) )
                    {
                    // InternalKap.g:609:2: ( ( rule__Body__Group_1__0 ) )
                    // InternalKap.g:610:3: ( rule__Body__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getBodyAccess().getGroup_1()); 
                    }
                    // InternalKap.g:611:3: ( rule__Body__Group_1__0 )
                    // InternalKap.g:611:4: rule__Body__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Body__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getBodyAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Alternatives"


    // $ANTLR start "rule__Statement__Alternatives"
    // InternalKap.g:619:1: rule__Statement__Alternatives : ( ( ( rule__Statement__CallAssignment_0 ) ) | ( ( rule__Statement__TextAssignment_1 ) ) | ( ( rule__Statement__IfAssignment_2 ) ) | ( ( rule__Statement__Group_3__0 ) ) );
    public final void rule__Statement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:623:1: ( ( ( rule__Statement__CallAssignment_0 ) ) | ( ( rule__Statement__TextAssignment_1 ) ) | ( ( rule__Statement__IfAssignment_2 ) ) | ( ( rule__Statement__Group_3__0 ) ) )
            int alt6=4;
            switch ( input.LA(1) ) {
            case RULE_LOWERCASE_ID:
                {
                alt6=1;
                }
                break;
            case RULE_EMBEDDEDTEXT:
                {
                alt6=2;
                }
                break;
            case 37:
                {
                alt6=3;
                }
                break;
            case 33:
                {
                alt6=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalKap.g:624:2: ( ( rule__Statement__CallAssignment_0 ) )
                    {
                    // InternalKap.g:624:2: ( ( rule__Statement__CallAssignment_0 ) )
                    // InternalKap.g:625:3: ( rule__Statement__CallAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStatementAccess().getCallAssignment_0()); 
                    }
                    // InternalKap.g:626:3: ( rule__Statement__CallAssignment_0 )
                    // InternalKap.g:626:4: rule__Statement__CallAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Statement__CallAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStatementAccess().getCallAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:630:2: ( ( rule__Statement__TextAssignment_1 ) )
                    {
                    // InternalKap.g:630:2: ( ( rule__Statement__TextAssignment_1 ) )
                    // InternalKap.g:631:3: ( rule__Statement__TextAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStatementAccess().getTextAssignment_1()); 
                    }
                    // InternalKap.g:632:3: ( rule__Statement__TextAssignment_1 )
                    // InternalKap.g:632:4: rule__Statement__TextAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Statement__TextAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStatementAccess().getTextAssignment_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:636:2: ( ( rule__Statement__IfAssignment_2 ) )
                    {
                    // InternalKap.g:636:2: ( ( rule__Statement__IfAssignment_2 ) )
                    // InternalKap.g:637:3: ( rule__Statement__IfAssignment_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStatementAccess().getIfAssignment_2()); 
                    }
                    // InternalKap.g:638:3: ( rule__Statement__IfAssignment_2 )
                    // InternalKap.g:638:4: rule__Statement__IfAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Statement__IfAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStatementAccess().getIfAssignment_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:642:2: ( ( rule__Statement__Group_3__0 ) )
                    {
                    // InternalKap.g:642:2: ( ( rule__Statement__Group_3__0 ) )
                    // InternalKap.g:643:3: ( rule__Statement__Group_3__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getStatementAccess().getGroup_3()); 
                    }
                    // InternalKap.g:644:3: ( rule__Statement__Group_3__0 )
                    // InternalKap.g:644:4: rule__Statement__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Statement__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getStatementAccess().getGroup_3()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Alternatives"


    // $ANTLR start "rule__IfBody__Alternatives"
    // InternalKap.g:652:1: rule__IfBody__Alternatives : ( ( ( rule__IfBody__CallAssignment_0 ) ) | ( ( rule__IfBody__BodyAssignment_1 ) ) );
    public final void rule__IfBody__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:656:1: ( ( ( rule__IfBody__CallAssignment_0 ) ) | ( ( rule__IfBody__BodyAssignment_1 ) ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_LOWERCASE_ID) ) {
                int LA7_1 = input.LA(2);

                if ( (synpred15_InternalKap()) ) {
                    alt7=1;
                }
                else if ( (true) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA7_0==RULE_EMBEDDEDTEXT||LA7_0==33||LA7_0==37) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalKap.g:657:2: ( ( rule__IfBody__CallAssignment_0 ) )
                    {
                    // InternalKap.g:657:2: ( ( rule__IfBody__CallAssignment_0 ) )
                    // InternalKap.g:658:3: ( rule__IfBody__CallAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getIfBodyAccess().getCallAssignment_0()); 
                    }
                    // InternalKap.g:659:3: ( rule__IfBody__CallAssignment_0 )
                    // InternalKap.g:659:4: rule__IfBody__CallAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__IfBody__CallAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getIfBodyAccess().getCallAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:663:2: ( ( rule__IfBody__BodyAssignment_1 ) )
                    {
                    // InternalKap.g:663:2: ( ( rule__IfBody__BodyAssignment_1 ) )
                    // InternalKap.g:664:3: ( rule__IfBody__BodyAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getIfBodyAccess().getBodyAssignment_1()); 
                    }
                    // InternalKap.g:665:3: ( rule__IfBody__BodyAssignment_1 )
                    // InternalKap.g:665:4: rule__IfBody__BodyAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__IfBody__BodyAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getIfBodyAccess().getBodyAssignment_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfBody__Alternatives"


    // $ANTLR start "rule__Call__Alternatives_2"
    // InternalKap.g:673:1: rule__Call__Alternatives_2 : ( ( ( rule__Call__Group_2_0__0 ) ) | ( ';' ) );
    public final void rule__Call__Alternatives_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:677:1: ( ( ( rule__Call__Group_2_0__0 ) ) | ( ';' ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==32) ) {
                alt8=1;
            }
            else if ( (LA8_0==20) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalKap.g:678:2: ( ( rule__Call__Group_2_0__0 ) )
                    {
                    // InternalKap.g:678:2: ( ( rule__Call__Group_2_0__0 ) )
                    // InternalKap.g:679:3: ( rule__Call__Group_2_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCallAccess().getGroup_2_0()); 
                    }
                    // InternalKap.g:680:3: ( rule__Call__Group_2_0__0 )
                    // InternalKap.g:680:4: rule__Call__Group_2_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Call__Group_2_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCallAccess().getGroup_2_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:684:2: ( ';' )
                    {
                    // InternalKap.g:684:2: ( ';' )
                    // InternalKap.g:685:3: ';'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getCallAccess().getSemicolonKeyword_2_1()); 
                    }
                    match(input,20,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getCallAccess().getSemicolonKeyword_2_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Alternatives_2"


    // $ANTLR start "rule__Actions__Alternatives"
    // InternalKap.g:694:1: rule__Actions__Alternatives : ( ( ( rule__Actions__CallAssignment_0 ) ) | ( ( rule__Actions__BodyAssignment_1 ) ) | ( ( rule__Actions__MatchAssignment_2 ) ) | ( ( rule__Actions__Group_3__0 ) ) );
    public final void rule__Actions__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:698:1: ( ( ( rule__Actions__CallAssignment_0 ) ) | ( ( rule__Actions__BodyAssignment_1 ) ) | ( ( rule__Actions__MatchAssignment_2 ) ) | ( ( rule__Actions__Group_3__0 ) ) )
            int alt9=4;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // InternalKap.g:699:2: ( ( rule__Actions__CallAssignment_0 ) )
                    {
                    // InternalKap.g:699:2: ( ( rule__Actions__CallAssignment_0 ) )
                    // InternalKap.g:700:3: ( rule__Actions__CallAssignment_0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getCallAssignment_0()); 
                    }
                    // InternalKap.g:701:3: ( rule__Actions__CallAssignment_0 )
                    // InternalKap.g:701:4: rule__Actions__CallAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Actions__CallAssignment_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getCallAssignment_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:705:2: ( ( rule__Actions__BodyAssignment_1 ) )
                    {
                    // InternalKap.g:705:2: ( ( rule__Actions__BodyAssignment_1 ) )
                    // InternalKap.g:706:3: ( rule__Actions__BodyAssignment_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getBodyAssignment_1()); 
                    }
                    // InternalKap.g:707:3: ( rule__Actions__BodyAssignment_1 )
                    // InternalKap.g:707:4: rule__Actions__BodyAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Actions__BodyAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getBodyAssignment_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:711:2: ( ( rule__Actions__MatchAssignment_2 ) )
                    {
                    // InternalKap.g:711:2: ( ( rule__Actions__MatchAssignment_2 ) )
                    // InternalKap.g:712:3: ( rule__Actions__MatchAssignment_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getMatchAssignment_2()); 
                    }
                    // InternalKap.g:713:3: ( rule__Actions__MatchAssignment_2 )
                    // InternalKap.g:713:4: rule__Actions__MatchAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Actions__MatchAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getMatchAssignment_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:717:2: ( ( rule__Actions__Group_3__0 ) )
                    {
                    // InternalKap.g:717:2: ( ( rule__Actions__Group_3__0 ) )
                    // InternalKap.g:718:3: ( rule__Actions__Group_3__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getActionsAccess().getGroup_3()); 
                    }
                    // InternalKap.g:719:3: ( rule__Actions__Group_3__0 )
                    // InternalKap.g:719:4: rule__Actions__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Actions__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getActionsAccess().getGroup_3()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Alternatives"


    // $ANTLR start "rule__Match__Alternatives"
    // InternalKap.g:727:1: rule__Match__Alternatives : ( ( ( rule__Match__Group_0__0 ) ) | ( ( rule__Match__Group_1__0 ) ) | ( ( rule__Match__Group_2__0 ) ) | ( ( rule__Match__Group_3__0 ) ) | ( ( rule__Match__Group_4__0 ) ) | ( ( rule__Match__Group_5__0 ) ) );
    public final void rule__Match__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:731:1: ( ( ( rule__Match__Group_0__0 ) ) | ( ( rule__Match__Group_1__0 ) ) | ( ( rule__Match__Group_2__0 ) ) | ( ( rule__Match__Group_3__0 ) ) | ( ( rule__Match__Group_4__0 ) ) | ( ( rule__Match__Group_5__0 ) ) )
            int alt10=6;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // InternalKap.g:732:2: ( ( rule__Match__Group_0__0 ) )
                    {
                    // InternalKap.g:732:2: ( ( rule__Match__Group_0__0 ) )
                    // InternalKap.g:733:3: ( rule__Match__Group_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_0()); 
                    }
                    // InternalKap.g:734:3: ( rule__Match__Group_0__0 )
                    // InternalKap.g:734:4: rule__Match__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:738:2: ( ( rule__Match__Group_1__0 ) )
                    {
                    // InternalKap.g:738:2: ( ( rule__Match__Group_1__0 ) )
                    // InternalKap.g:739:3: ( rule__Match__Group_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_1()); 
                    }
                    // InternalKap.g:740:3: ( rule__Match__Group_1__0 )
                    // InternalKap.g:740:4: rule__Match__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:744:2: ( ( rule__Match__Group_2__0 ) )
                    {
                    // InternalKap.g:744:2: ( ( rule__Match__Group_2__0 ) )
                    // InternalKap.g:745:3: ( rule__Match__Group_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_2()); 
                    }
                    // InternalKap.g:746:3: ( rule__Match__Group_2__0 )
                    // InternalKap.g:746:4: rule__Match__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_2()); 
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:750:2: ( ( rule__Match__Group_3__0 ) )
                    {
                    // InternalKap.g:750:2: ( ( rule__Match__Group_3__0 ) )
                    // InternalKap.g:751:3: ( rule__Match__Group_3__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_3()); 
                    }
                    // InternalKap.g:752:3: ( rule__Match__Group_3__0 )
                    // InternalKap.g:752:4: rule__Match__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_3()); 
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalKap.g:756:2: ( ( rule__Match__Group_4__0 ) )
                    {
                    // InternalKap.g:756:2: ( ( rule__Match__Group_4__0 ) )
                    // InternalKap.g:757:3: ( rule__Match__Group_4__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_4()); 
                    }
                    // InternalKap.g:758:3: ( rule__Match__Group_4__0 )
                    // InternalKap.g:758:4: rule__Match__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_4__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_4()); 
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalKap.g:762:2: ( ( rule__Match__Group_5__0 ) )
                    {
                    // InternalKap.g:762:2: ( ( rule__Match__Group_5__0 ) )
                    // InternalKap.g:763:3: ( rule__Match__Group_5__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getMatchAccess().getGroup_5()); 
                    }
                    // InternalKap.g:764:3: ( rule__Match__Group_5__0 )
                    // InternalKap.g:764:4: rule__Match__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Match__Group_5__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getMatchAccess().getGroup_5()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Alternatives"


    // $ANTLR start "rule__Number__Alternatives_0"
    // InternalKap.g:772:1: rule__Number__Alternatives_0 : ( ( '+' ) | ( ( rule__Number__NegativeAssignment_0_1 ) ) );
    public final void rule__Number__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:776:1: ( ( '+' ) | ( ( rule__Number__NegativeAssignment_0_1 ) ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==21) ) {
                alt11=1;
            }
            else if ( (LA11_0==40) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalKap.g:777:2: ( '+' )
                    {
                    // InternalKap.g:777:2: ( '+' )
                    // InternalKap.g:778:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getPlusSignKeyword_0_0()); 
                    }
                    match(input,21,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getPlusSignKeyword_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:783:2: ( ( rule__Number__NegativeAssignment_0_1 ) )
                    {
                    // InternalKap.g:783:2: ( ( rule__Number__NegativeAssignment_0_1 ) )
                    // InternalKap.g:784:3: ( rule__Number__NegativeAssignment_0_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getNegativeAssignment_0_1()); 
                    }
                    // InternalKap.g:785:3: ( rule__Number__NegativeAssignment_0_1 )
                    // InternalKap.g:785:4: rule__Number__NegativeAssignment_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__NegativeAssignment_0_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getNegativeAssignment_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Alternatives_0"


    // $ANTLR start "rule__Number__ExponentialAlternatives_4_0_0_0"
    // InternalKap.g:793:1: rule__Number__ExponentialAlternatives_4_0_0_0 : ( ( 'e' ) | ( 'E' ) );
    public final void rule__Number__ExponentialAlternatives_4_0_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:797:1: ( ( 'e' ) | ( 'E' ) )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==22) ) {
                alt12=1;
            }
            else if ( (LA12_0==23) ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // InternalKap.g:798:2: ( 'e' )
                    {
                    // InternalKap.g:798:2: ( 'e' )
                    // InternalKap.g:799:3: 'e'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_0()); 
                    }
                    match(input,22,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:804:2: ( 'E' )
                    {
                    // InternalKap.g:804:2: ( 'E' )
                    // InternalKap.g:805:3: 'E'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_1()); 
                    }
                    match(input,23,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getExponentialEKeyword_4_0_0_0_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__ExponentialAlternatives_4_0_0_0"


    // $ANTLR start "rule__Number__Alternatives_4_0_1"
    // InternalKap.g:814:1: rule__Number__Alternatives_4_0_1 : ( ( '+' ) | ( ( rule__Number__ExpNegativeAssignment_4_0_1_1 ) ) );
    public final void rule__Number__Alternatives_4_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:818:1: ( ( '+' ) | ( ( rule__Number__ExpNegativeAssignment_4_0_1_1 ) ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==21) ) {
                alt13=1;
            }
            else if ( (LA13_0==40) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalKap.g:819:2: ( '+' )
                    {
                    // InternalKap.g:819:2: ( '+' )
                    // InternalKap.g:820:3: '+'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0()); 
                    }
                    match(input,21,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getPlusSignKeyword_4_0_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:825:2: ( ( rule__Number__ExpNegativeAssignment_4_0_1_1 ) )
                    {
                    // InternalKap.g:825:2: ( ( rule__Number__ExpNegativeAssignment_4_0_1_1 ) )
                    // InternalKap.g:826:3: ( rule__Number__ExpNegativeAssignment_4_0_1_1 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getNumberAccess().getExpNegativeAssignment_4_0_1_1()); 
                    }
                    // InternalKap.g:827:3: ( rule__Number__ExpNegativeAssignment_4_0_1_1 )
                    // InternalKap.g:827:4: rule__Number__ExpNegativeAssignment_4_0_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__ExpNegativeAssignment_4_0_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getNumberAccess().getExpNegativeAssignment_4_0_1_1()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Alternatives_4_0_1"


    // $ANTLR start "rule__Date__Alternatives_1"
    // InternalKap.g:835:1: rule__Date__Alternatives_1 : ( ( 'AD' ) | ( 'CE' ) | ( ( rule__Date__BcAssignment_1_2 ) ) );
    public final void rule__Date__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:839:1: ( ( 'AD' ) | ( 'CE' ) | ( ( rule__Date__BcAssignment_1_2 ) ) )
            int alt14=3;
            switch ( input.LA(1) ) {
            case 24:
                {
                alt14=1;
                }
                break;
            case 25:
                {
                alt14=2;
                }
                break;
            case 44:
                {
                alt14=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // InternalKap.g:840:2: ( 'AD' )
                    {
                    // InternalKap.g:840:2: ( 'AD' )
                    // InternalKap.g:841:3: 'AD'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDateAccess().getADKeyword_1_0()); 
                    }
                    match(input,24,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDateAccess().getADKeyword_1_0()); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:846:2: ( 'CE' )
                    {
                    // InternalKap.g:846:2: ( 'CE' )
                    // InternalKap.g:847:3: 'CE'
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDateAccess().getCEKeyword_1_1()); 
                    }
                    match(input,25,FOLLOW_2); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDateAccess().getCEKeyword_1_1()); 
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:852:2: ( ( rule__Date__BcAssignment_1_2 ) )
                    {
                    // InternalKap.g:852:2: ( ( rule__Date__BcAssignment_1_2 ) )
                    // InternalKap.g:853:3: ( rule__Date__BcAssignment_1_2 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getDateAccess().getBcAssignment_1_2()); 
                    }
                    // InternalKap.g:854:3: ( rule__Date__BcAssignment_1_2 )
                    // InternalKap.g:854:4: rule__Date__BcAssignment_1_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__BcAssignment_1_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getDateAccess().getBcAssignment_1_2()); 
                    }

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Alternatives_1"


    // $ANTLR start "rule__Model__Group__0"
    // InternalKap.g:862:1: rule__Model__Group__0 : rule__Model__Group__0__Impl rule__Model__Group__1 ;
    public final void rule__Model__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:866:1: ( rule__Model__Group__0__Impl rule__Model__Group__1 )
            // InternalKap.g:867:2: rule__Model__Group__0__Impl rule__Model__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Model__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Model__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0"


    // $ANTLR start "rule__Model__Group__0__Impl"
    // InternalKap.g:874:1: rule__Model__Group__0__Impl : ( () ) ;
    public final void rule__Model__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:878:1: ( ( () ) )
            // InternalKap.g:879:1: ( () )
            {
            // InternalKap.g:879:1: ( () )
            // InternalKap.g:880:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getModelAction_0()); 
            }
            // InternalKap.g:881:2: ()
            // InternalKap.g:881:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getModelAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0__Impl"


    // $ANTLR start "rule__Model__Group__1"
    // InternalKap.g:889:1: rule__Model__Group__1 : rule__Model__Group__1__Impl rule__Model__Group__2 ;
    public final void rule__Model__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:893:1: ( rule__Model__Group__1__Impl rule__Model__Group__2 )
            // InternalKap.g:894:2: rule__Model__Group__1__Impl rule__Model__Group__2
            {
            pushFollow(FOLLOW_3);
            rule__Model__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Model__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__1"


    // $ANTLR start "rule__Model__Group__1__Impl"
    // InternalKap.g:901:1: rule__Model__Group__1__Impl : ( ( rule__Model__PreambleAssignment_1 )? ) ;
    public final void rule__Model__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:905:1: ( ( ( rule__Model__PreambleAssignment_1 )? ) )
            // InternalKap.g:906:1: ( ( rule__Model__PreambleAssignment_1 )? )
            {
            // InternalKap.g:906:1: ( ( rule__Model__PreambleAssignment_1 )? )
            // InternalKap.g:907:2: ( rule__Model__PreambleAssignment_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getPreambleAssignment_1()); 
            }
            // InternalKap.g:908:2: ( rule__Model__PreambleAssignment_1 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==26) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalKap.g:908:3: rule__Model__PreambleAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Model__PreambleAssignment_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getPreambleAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__1__Impl"


    // $ANTLR start "rule__Model__Group__2"
    // InternalKap.g:916:1: rule__Model__Group__2 : rule__Model__Group__2__Impl ;
    public final void rule__Model__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:920:1: ( rule__Model__Group__2__Impl )
            // InternalKap.g:921:2: rule__Model__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Model__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__2"


    // $ANTLR start "rule__Model__Group__2__Impl"
    // InternalKap.g:927:1: rule__Model__Group__2__Impl : ( ( rule__Model__DefinitionsAssignment_2 )* ) ;
    public final void rule__Model__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:931:1: ( ( ( rule__Model__DefinitionsAssignment_2 )* ) )
            // InternalKap.g:932:1: ( ( rule__Model__DefinitionsAssignment_2 )* )
            {
            // InternalKap.g:932:1: ( ( rule__Model__DefinitionsAssignment_2 )* )
            // InternalKap.g:933:2: ( rule__Model__DefinitionsAssignment_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getDefinitionsAssignment_2()); 
            }
            // InternalKap.g:934:2: ( rule__Model__DefinitionsAssignment_2 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==31) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalKap.g:934:3: rule__Model__DefinitionsAssignment_2
            	    {
            	    pushFollow(FOLLOW_4);
            	    rule__Model__DefinitionsAssignment_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getDefinitionsAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__2__Impl"


    // $ANTLR start "rule__Preamble__Group__0"
    // InternalKap.g:943:1: rule__Preamble__Group__0 : rule__Preamble__Group__0__Impl rule__Preamble__Group__1 ;
    public final void rule__Preamble__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:947:1: ( rule__Preamble__Group__0__Impl rule__Preamble__Group__1 )
            // InternalKap.g:948:2: rule__Preamble__Group__0__Impl rule__Preamble__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__Preamble__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Preamble__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__0"


    // $ANTLR start "rule__Preamble__Group__0__Impl"
    // InternalKap.g:955:1: rule__Preamble__Group__0__Impl : ( 'name' ) ;
    public final void rule__Preamble__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:959:1: ( ( 'name' ) )
            // InternalKap.g:960:1: ( 'name' )
            {
            // InternalKap.g:960:1: ( 'name' )
            // InternalKap.g:961:2: 'name'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getNameKeyword_0()); 
            }
            match(input,26,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getNameKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__0__Impl"


    // $ANTLR start "rule__Preamble__Group__1"
    // InternalKap.g:970:1: rule__Preamble__Group__1 : rule__Preamble__Group__1__Impl rule__Preamble__Group__2 ;
    public final void rule__Preamble__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:974:1: ( rule__Preamble__Group__1__Impl rule__Preamble__Group__2 )
            // InternalKap.g:975:2: rule__Preamble__Group__1__Impl rule__Preamble__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__Preamble__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Preamble__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__1"


    // $ANTLR start "rule__Preamble__Group__1__Impl"
    // InternalKap.g:982:1: rule__Preamble__Group__1__Impl : ( ( rule__Preamble__NameAssignment_1 ) ) ;
    public final void rule__Preamble__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:986:1: ( ( ( rule__Preamble__NameAssignment_1 ) ) )
            // InternalKap.g:987:1: ( ( rule__Preamble__NameAssignment_1 ) )
            {
            // InternalKap.g:987:1: ( ( rule__Preamble__NameAssignment_1 ) )
            // InternalKap.g:988:2: ( rule__Preamble__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getNameAssignment_1()); 
            }
            // InternalKap.g:989:2: ( rule__Preamble__NameAssignment_1 )
            // InternalKap.g:989:3: rule__Preamble__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getNameAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__1__Impl"


    // $ANTLR start "rule__Preamble__Group__2"
    // InternalKap.g:997:1: rule__Preamble__Group__2 : rule__Preamble__Group__2__Impl ;
    public final void rule__Preamble__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1001:1: ( rule__Preamble__Group__2__Impl )
            // InternalKap.g:1002:2: rule__Preamble__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__2"


    // $ANTLR start "rule__Preamble__Group__2__Impl"
    // InternalKap.g:1008:1: rule__Preamble__Group__2__Impl : ( ( rule__Preamble__UnorderedGroup_2 ) ) ;
    public final void rule__Preamble__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1012:1: ( ( ( rule__Preamble__UnorderedGroup_2 ) ) )
            // InternalKap.g:1013:1: ( ( rule__Preamble__UnorderedGroup_2 ) )
            {
            // InternalKap.g:1013:1: ( ( rule__Preamble__UnorderedGroup_2 ) )
            // InternalKap.g:1014:2: ( rule__Preamble__UnorderedGroup_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getUnorderedGroup_2()); 
            }
            // InternalKap.g:1015:2: ( rule__Preamble__UnorderedGroup_2 )
            // InternalKap.g:1015:3: rule__Preamble__UnorderedGroup_2
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__UnorderedGroup_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getUnorderedGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group__2__Impl"


    // $ANTLR start "rule__Preamble__Group_2_0__0"
    // InternalKap.g:1024:1: rule__Preamble__Group_2_0__0 : rule__Preamble__Group_2_0__0__Impl rule__Preamble__Group_2_0__1 ;
    public final void rule__Preamble__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1028:1: ( rule__Preamble__Group_2_0__0__Impl rule__Preamble__Group_2_0__1 )
            // InternalKap.g:1029:2: rule__Preamble__Group_2_0__0__Impl rule__Preamble__Group_2_0__1
            {
            pushFollow(FOLLOW_5);
            rule__Preamble__Group_2_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Preamble__Group_2_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_0__0"


    // $ANTLR start "rule__Preamble__Group_2_0__0__Impl"
    // InternalKap.g:1036:1: rule__Preamble__Group_2_0__0__Impl : ( 'worldview' ) ;
    public final void rule__Preamble__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1040:1: ( ( 'worldview' ) )
            // InternalKap.g:1041:1: ( 'worldview' )
            {
            // InternalKap.g:1041:1: ( 'worldview' )
            // InternalKap.g:1042:2: 'worldview'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getWorldviewKeyword_2_0_0()); 
            }
            match(input,27,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getWorldviewKeyword_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_0__0__Impl"


    // $ANTLR start "rule__Preamble__Group_2_0__1"
    // InternalKap.g:1051:1: rule__Preamble__Group_2_0__1 : rule__Preamble__Group_2_0__1__Impl ;
    public final void rule__Preamble__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1055:1: ( rule__Preamble__Group_2_0__1__Impl )
            // InternalKap.g:1056:2: rule__Preamble__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__Group_2_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_0__1"


    // $ANTLR start "rule__Preamble__Group_2_0__1__Impl"
    // InternalKap.g:1062:1: rule__Preamble__Group_2_0__1__Impl : ( ( rule__Preamble__WorldviewAssignment_2_0_1 ) ) ;
    public final void rule__Preamble__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1066:1: ( ( ( rule__Preamble__WorldviewAssignment_2_0_1 ) ) )
            // InternalKap.g:1067:1: ( ( rule__Preamble__WorldviewAssignment_2_0_1 ) )
            {
            // InternalKap.g:1067:1: ( ( rule__Preamble__WorldviewAssignment_2_0_1 ) )
            // InternalKap.g:1068:2: ( rule__Preamble__WorldviewAssignment_2_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getWorldviewAssignment_2_0_1()); 
            }
            // InternalKap.g:1069:2: ( rule__Preamble__WorldviewAssignment_2_0_1 )
            // InternalKap.g:1069:3: rule__Preamble__WorldviewAssignment_2_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__WorldviewAssignment_2_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getWorldviewAssignment_2_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_0__1__Impl"


    // $ANTLR start "rule__Preamble__Group_2_1__0"
    // InternalKap.g:1078:1: rule__Preamble__Group_2_1__0 : rule__Preamble__Group_2_1__0__Impl rule__Preamble__Group_2_1__1 ;
    public final void rule__Preamble__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1082:1: ( rule__Preamble__Group_2_1__0__Impl rule__Preamble__Group_2_1__1 )
            // InternalKap.g:1083:2: rule__Preamble__Group_2_1__0__Impl rule__Preamble__Group_2_1__1
            {
            pushFollow(FOLLOW_7);
            rule__Preamble__Group_2_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Preamble__Group_2_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_1__0"


    // $ANTLR start "rule__Preamble__Group_2_1__0__Impl"
    // InternalKap.g:1090:1: rule__Preamble__Group_2_1__0__Impl : ( 'permissions' ) ;
    public final void rule__Preamble__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1094:1: ( ( 'permissions' ) )
            // InternalKap.g:1095:1: ( 'permissions' )
            {
            // InternalKap.g:1095:1: ( 'permissions' )
            // InternalKap.g:1096:2: 'permissions'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getPermissionsKeyword_2_1_0()); 
            }
            match(input,28,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getPermissionsKeyword_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_1__0__Impl"


    // $ANTLR start "rule__Preamble__Group_2_1__1"
    // InternalKap.g:1105:1: rule__Preamble__Group_2_1__1 : rule__Preamble__Group_2_1__1__Impl ;
    public final void rule__Preamble__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1109:1: ( rule__Preamble__Group_2_1__1__Impl )
            // InternalKap.g:1110:2: rule__Preamble__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__Group_2_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_1__1"


    // $ANTLR start "rule__Preamble__Group_2_1__1__Impl"
    // InternalKap.g:1116:1: rule__Preamble__Group_2_1__1__Impl : ( ( rule__Preamble__PermissionsAssignment_2_1_1 ) ) ;
    public final void rule__Preamble__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1120:1: ( ( ( rule__Preamble__PermissionsAssignment_2_1_1 ) ) )
            // InternalKap.g:1121:1: ( ( rule__Preamble__PermissionsAssignment_2_1_1 ) )
            {
            // InternalKap.g:1121:1: ( ( rule__Preamble__PermissionsAssignment_2_1_1 ) )
            // InternalKap.g:1122:2: ( rule__Preamble__PermissionsAssignment_2_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getPermissionsAssignment_2_1_1()); 
            }
            // InternalKap.g:1123:2: ( rule__Preamble__PermissionsAssignment_2_1_1 )
            // InternalKap.g:1123:3: rule__Preamble__PermissionsAssignment_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__PermissionsAssignment_2_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getPermissionsAssignment_2_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_1__1__Impl"


    // $ANTLR start "rule__Preamble__Group_2_2__0"
    // InternalKap.g:1132:1: rule__Preamble__Group_2_2__0 : rule__Preamble__Group_2_2__0__Impl rule__Preamble__Group_2_2__1 ;
    public final void rule__Preamble__Group_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1136:1: ( rule__Preamble__Group_2_2__0__Impl rule__Preamble__Group_2_2__1 )
            // InternalKap.g:1137:2: rule__Preamble__Group_2_2__0__Impl rule__Preamble__Group_2_2__1
            {
            pushFollow(FOLLOW_7);
            rule__Preamble__Group_2_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Preamble__Group_2_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_2__0"


    // $ANTLR start "rule__Preamble__Group_2_2__0__Impl"
    // InternalKap.g:1144:1: rule__Preamble__Group_2_2__0__Impl : ( 'author' ) ;
    public final void rule__Preamble__Group_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1148:1: ( ( 'author' ) )
            // InternalKap.g:1149:1: ( 'author' )
            {
            // InternalKap.g:1149:1: ( 'author' )
            // InternalKap.g:1150:2: 'author'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getAuthorKeyword_2_2_0()); 
            }
            match(input,29,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getAuthorKeyword_2_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_2__0__Impl"


    // $ANTLR start "rule__Preamble__Group_2_2__1"
    // InternalKap.g:1159:1: rule__Preamble__Group_2_2__1 : rule__Preamble__Group_2_2__1__Impl ;
    public final void rule__Preamble__Group_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1163:1: ( rule__Preamble__Group_2_2__1__Impl )
            // InternalKap.g:1164:2: rule__Preamble__Group_2_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__Group_2_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_2__1"


    // $ANTLR start "rule__Preamble__Group_2_2__1__Impl"
    // InternalKap.g:1170:1: rule__Preamble__Group_2_2__1__Impl : ( ( rule__Preamble__AuthorsAssignment_2_2_1 ) ) ;
    public final void rule__Preamble__Group_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1174:1: ( ( ( rule__Preamble__AuthorsAssignment_2_2_1 ) ) )
            // InternalKap.g:1175:1: ( ( rule__Preamble__AuthorsAssignment_2_2_1 ) )
            {
            // InternalKap.g:1175:1: ( ( rule__Preamble__AuthorsAssignment_2_2_1 ) )
            // InternalKap.g:1176:2: ( rule__Preamble__AuthorsAssignment_2_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getAuthorsAssignment_2_2_1()); 
            }
            // InternalKap.g:1177:2: ( rule__Preamble__AuthorsAssignment_2_2_1 )
            // InternalKap.g:1177:3: rule__Preamble__AuthorsAssignment_2_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__AuthorsAssignment_2_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getAuthorsAssignment_2_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_2__1__Impl"


    // $ANTLR start "rule__Preamble__Group_2_3__0"
    // InternalKap.g:1186:1: rule__Preamble__Group_2_3__0 : rule__Preamble__Group_2_3__0__Impl rule__Preamble__Group_2_3__1 ;
    public final void rule__Preamble__Group_2_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1190:1: ( rule__Preamble__Group_2_3__0__Impl rule__Preamble__Group_2_3__1 )
            // InternalKap.g:1191:2: rule__Preamble__Group_2_3__0__Impl rule__Preamble__Group_2_3__1
            {
            pushFollow(FOLLOW_5);
            rule__Preamble__Group_2_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Preamble__Group_2_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_3__0"


    // $ANTLR start "rule__Preamble__Group_2_3__0__Impl"
    // InternalKap.g:1198:1: rule__Preamble__Group_2_3__0__Impl : ( 'version' ) ;
    public final void rule__Preamble__Group_2_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1202:1: ( ( 'version' ) )
            // InternalKap.g:1203:1: ( 'version' )
            {
            // InternalKap.g:1203:1: ( 'version' )
            // InternalKap.g:1204:2: 'version'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getVersionKeyword_2_3_0()); 
            }
            match(input,30,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getVersionKeyword_2_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_3__0__Impl"


    // $ANTLR start "rule__Preamble__Group_2_3__1"
    // InternalKap.g:1213:1: rule__Preamble__Group_2_3__1 : rule__Preamble__Group_2_3__1__Impl ;
    public final void rule__Preamble__Group_2_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1217:1: ( rule__Preamble__Group_2_3__1__Impl )
            // InternalKap.g:1218:2: rule__Preamble__Group_2_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__Group_2_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_3__1"


    // $ANTLR start "rule__Preamble__Group_2_3__1__Impl"
    // InternalKap.g:1224:1: rule__Preamble__Group_2_3__1__Impl : ( ( rule__Preamble__VersionAssignment_2_3_1 ) ) ;
    public final void rule__Preamble__Group_2_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1228:1: ( ( ( rule__Preamble__VersionAssignment_2_3_1 ) ) )
            // InternalKap.g:1229:1: ( ( rule__Preamble__VersionAssignment_2_3_1 ) )
            {
            // InternalKap.g:1229:1: ( ( rule__Preamble__VersionAssignment_2_3_1 ) )
            // InternalKap.g:1230:2: ( rule__Preamble__VersionAssignment_2_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getVersionAssignment_2_3_1()); 
            }
            // InternalKap.g:1231:2: ( rule__Preamble__VersionAssignment_2_3_1 )
            // InternalKap.g:1231:3: rule__Preamble__VersionAssignment_2_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__VersionAssignment_2_3_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getVersionAssignment_2_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__Group_2_3__1__Impl"


    // $ANTLR start "rule__Definition__Group__0"
    // InternalKap.g:1240:1: rule__Definition__Group__0 : rule__Definition__Group__0__Impl rule__Definition__Group__1 ;
    public final void rule__Definition__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1244:1: ( rule__Definition__Group__0__Impl rule__Definition__Group__1 )
            // InternalKap.g:1245:2: rule__Definition__Group__0__Impl rule__Definition__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__Definition__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Definition__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__0"


    // $ANTLR start "rule__Definition__Group__0__Impl"
    // InternalKap.g:1252:1: rule__Definition__Group__0__Impl : ( 'def' ) ;
    public final void rule__Definition__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1256:1: ( ( 'def' ) )
            // InternalKap.g:1257:1: ( 'def' )
            {
            // InternalKap.g:1257:1: ( 'def' )
            // InternalKap.g:1258:2: 'def'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getDefKeyword_0()); 
            }
            match(input,31,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getDefKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__0__Impl"


    // $ANTLR start "rule__Definition__Group__1"
    // InternalKap.g:1267:1: rule__Definition__Group__1 : rule__Definition__Group__1__Impl rule__Definition__Group__2 ;
    public final void rule__Definition__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1271:1: ( rule__Definition__Group__1__Impl rule__Definition__Group__2 )
            // InternalKap.g:1272:2: rule__Definition__Group__1__Impl rule__Definition__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__Definition__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Definition__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__1"


    // $ANTLR start "rule__Definition__Group__1__Impl"
    // InternalKap.g:1279:1: rule__Definition__Group__1__Impl : ( ( rule__Definition__NameAssignment_1 ) ) ;
    public final void rule__Definition__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1283:1: ( ( ( rule__Definition__NameAssignment_1 ) ) )
            // InternalKap.g:1284:1: ( ( rule__Definition__NameAssignment_1 ) )
            {
            // InternalKap.g:1284:1: ( ( rule__Definition__NameAssignment_1 ) )
            // InternalKap.g:1285:2: ( rule__Definition__NameAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getNameAssignment_1()); 
            }
            // InternalKap.g:1286:2: ( rule__Definition__NameAssignment_1 )
            // InternalKap.g:1286:3: rule__Definition__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Definition__NameAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getNameAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__1__Impl"


    // $ANTLR start "rule__Definition__Group__2"
    // InternalKap.g:1294:1: rule__Definition__Group__2 : rule__Definition__Group__2__Impl rule__Definition__Group__3 ;
    public final void rule__Definition__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1298:1: ( rule__Definition__Group__2__Impl rule__Definition__Group__3 )
            // InternalKap.g:1299:2: rule__Definition__Group__2__Impl rule__Definition__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__Definition__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Definition__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__2"


    // $ANTLR start "rule__Definition__Group__2__Impl"
    // InternalKap.g:1306:1: rule__Definition__Group__2__Impl : ( ( rule__Definition__ArgumentsAssignment_2 )? ) ;
    public final void rule__Definition__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1310:1: ( ( ( rule__Definition__ArgumentsAssignment_2 )? ) )
            // InternalKap.g:1311:1: ( ( rule__Definition__ArgumentsAssignment_2 )? )
            {
            // InternalKap.g:1311:1: ( ( rule__Definition__ArgumentsAssignment_2 )? )
            // InternalKap.g:1312:2: ( rule__Definition__ArgumentsAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getArgumentsAssignment_2()); 
            }
            // InternalKap.g:1313:2: ( rule__Definition__ArgumentsAssignment_2 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==33) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalKap.g:1313:3: rule__Definition__ArgumentsAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Definition__ArgumentsAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getArgumentsAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__2__Impl"


    // $ANTLR start "rule__Definition__Group__3"
    // InternalKap.g:1321:1: rule__Definition__Group__3 : rule__Definition__Group__3__Impl rule__Definition__Group__4 ;
    public final void rule__Definition__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1325:1: ( rule__Definition__Group__3__Impl rule__Definition__Group__4 )
            // InternalKap.g:1326:2: rule__Definition__Group__3__Impl rule__Definition__Group__4
            {
            pushFollow(FOLLOW_9);
            rule__Definition__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Definition__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__3"


    // $ANTLR start "rule__Definition__Group__3__Impl"
    // InternalKap.g:1333:1: rule__Definition__Group__3__Impl : ( ':' ) ;
    public final void rule__Definition__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1337:1: ( ( ':' ) )
            // InternalKap.g:1338:1: ( ':' )
            {
            // InternalKap.g:1338:1: ( ':' )
            // InternalKap.g:1339:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getColonKeyword_3()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getColonKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__3__Impl"


    // $ANTLR start "rule__Definition__Group__4"
    // InternalKap.g:1348:1: rule__Definition__Group__4 : rule__Definition__Group__4__Impl ;
    public final void rule__Definition__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1352:1: ( rule__Definition__Group__4__Impl )
            // InternalKap.g:1353:2: rule__Definition__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Definition__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__4"


    // $ANTLR start "rule__Definition__Group__4__Impl"
    // InternalKap.g:1359:1: rule__Definition__Group__4__Impl : ( ( rule__Definition__BodyAssignment_4 ) ) ;
    public final void rule__Definition__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1363:1: ( ( ( rule__Definition__BodyAssignment_4 ) ) )
            // InternalKap.g:1364:1: ( ( rule__Definition__BodyAssignment_4 ) )
            {
            // InternalKap.g:1364:1: ( ( rule__Definition__BodyAssignment_4 ) )
            // InternalKap.g:1365:2: ( rule__Definition__BodyAssignment_4 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getBodyAssignment_4()); 
            }
            // InternalKap.g:1366:2: ( rule__Definition__BodyAssignment_4 )
            // InternalKap.g:1366:3: rule__Definition__BodyAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__Definition__BodyAssignment_4();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getBodyAssignment_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__Group__4__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group__0"
    // InternalKap.g:1375:1: rule__ArgumentDeclaration__Group__0 : rule__ArgumentDeclaration__Group__0__Impl rule__ArgumentDeclaration__Group__1 ;
    public final void rule__ArgumentDeclaration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1379:1: ( rule__ArgumentDeclaration__Group__0__Impl rule__ArgumentDeclaration__Group__1 )
            // InternalKap.g:1380:2: rule__ArgumentDeclaration__Group__0__Impl rule__ArgumentDeclaration__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__ArgumentDeclaration__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__0"


    // $ANTLR start "rule__ArgumentDeclaration__Group__0__Impl"
    // InternalKap.g:1387:1: rule__ArgumentDeclaration__Group__0__Impl : ( () ) ;
    public final void rule__ArgumentDeclaration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1391:1: ( ( () ) )
            // InternalKap.g:1392:1: ( () )
            {
            // InternalKap.g:1392:1: ( () )
            // InternalKap.g:1393:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getArgumentDeclarationAction_0()); 
            }
            // InternalKap.g:1394:2: ()
            // InternalKap.g:1394:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getArgumentDeclarationAction_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__0__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group__1"
    // InternalKap.g:1402:1: rule__ArgumentDeclaration__Group__1 : rule__ArgumentDeclaration__Group__1__Impl rule__ArgumentDeclaration__Group__2 ;
    public final void rule__ArgumentDeclaration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1406:1: ( rule__ArgumentDeclaration__Group__1__Impl rule__ArgumentDeclaration__Group__2 )
            // InternalKap.g:1407:2: rule__ArgumentDeclaration__Group__1__Impl rule__ArgumentDeclaration__Group__2
            {
            pushFollow(FOLLOW_11);
            rule__ArgumentDeclaration__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__1"


    // $ANTLR start "rule__ArgumentDeclaration__Group__1__Impl"
    // InternalKap.g:1414:1: rule__ArgumentDeclaration__Group__1__Impl : ( '(' ) ;
    public final void rule__ArgumentDeclaration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1418:1: ( ( '(' ) )
            // InternalKap.g:1419:1: ( '(' )
            {
            // InternalKap.g:1419:1: ( '(' )
            // InternalKap.g:1420:2: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getLeftParenthesisKeyword_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__1__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group__2"
    // InternalKap.g:1429:1: rule__ArgumentDeclaration__Group__2 : rule__ArgumentDeclaration__Group__2__Impl rule__ArgumentDeclaration__Group__3 ;
    public final void rule__ArgumentDeclaration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1433:1: ( rule__ArgumentDeclaration__Group__2__Impl rule__ArgumentDeclaration__Group__3 )
            // InternalKap.g:1434:2: rule__ArgumentDeclaration__Group__2__Impl rule__ArgumentDeclaration__Group__3
            {
            pushFollow(FOLLOW_11);
            rule__ArgumentDeclaration__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__2"


    // $ANTLR start "rule__ArgumentDeclaration__Group__2__Impl"
    // InternalKap.g:1441:1: rule__ArgumentDeclaration__Group__2__Impl : ( ( rule__ArgumentDeclaration__Group_2__0 )? ) ;
    public final void rule__ArgumentDeclaration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1445:1: ( ( ( rule__ArgumentDeclaration__Group_2__0 )? ) )
            // InternalKap.g:1446:1: ( ( rule__ArgumentDeclaration__Group_2__0 )? )
            {
            // InternalKap.g:1446:1: ( ( rule__ArgumentDeclaration__Group_2__0 )? )
            // InternalKap.g:1447:2: ( rule__ArgumentDeclaration__Group_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getGroup_2()); 
            }
            // InternalKap.g:1448:2: ( rule__ArgumentDeclaration__Group_2__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_LOWERCASE_ID) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalKap.g:1448:3: rule__ArgumentDeclaration__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ArgumentDeclaration__Group_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getGroup_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__2__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group__3"
    // InternalKap.g:1456:1: rule__ArgumentDeclaration__Group__3 : rule__ArgumentDeclaration__Group__3__Impl ;
    public final void rule__ArgumentDeclaration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1460:1: ( rule__ArgumentDeclaration__Group__3__Impl )
            // InternalKap.g:1461:2: rule__ArgumentDeclaration__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__3"


    // $ANTLR start "rule__ArgumentDeclaration__Group__3__Impl"
    // InternalKap.g:1467:1: rule__ArgumentDeclaration__Group__3__Impl : ( ')' ) ;
    public final void rule__ArgumentDeclaration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1471:1: ( ( ')' ) )
            // InternalKap.g:1472:1: ( ')' )
            {
            // InternalKap.g:1472:1: ( ')' )
            // InternalKap.g:1473:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getRightParenthesisKeyword_3()); 
            }
            match(input,34,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getRightParenthesisKeyword_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group__3__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2__0"
    // InternalKap.g:1483:1: rule__ArgumentDeclaration__Group_2__0 : rule__ArgumentDeclaration__Group_2__0__Impl rule__ArgumentDeclaration__Group_2__1 ;
    public final void rule__ArgumentDeclaration__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1487:1: ( rule__ArgumentDeclaration__Group_2__0__Impl rule__ArgumentDeclaration__Group_2__1 )
            // InternalKap.g:1488:2: rule__ArgumentDeclaration__Group_2__0__Impl rule__ArgumentDeclaration__Group_2__1
            {
            pushFollow(FOLLOW_12);
            rule__ArgumentDeclaration__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2__0"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2__0__Impl"
    // InternalKap.g:1495:1: rule__ArgumentDeclaration__Group_2__0__Impl : ( ( rule__ArgumentDeclaration__IdsAssignment_2_0 ) ) ;
    public final void rule__ArgumentDeclaration__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1499:1: ( ( ( rule__ArgumentDeclaration__IdsAssignment_2_0 ) ) )
            // InternalKap.g:1500:1: ( ( rule__ArgumentDeclaration__IdsAssignment_2_0 ) )
            {
            // InternalKap.g:1500:1: ( ( rule__ArgumentDeclaration__IdsAssignment_2_0 ) )
            // InternalKap.g:1501:2: ( rule__ArgumentDeclaration__IdsAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getIdsAssignment_2_0()); 
            }
            // InternalKap.g:1502:2: ( rule__ArgumentDeclaration__IdsAssignment_2_0 )
            // InternalKap.g:1502:3: rule__ArgumentDeclaration__IdsAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__IdsAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getIdsAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2__0__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2__1"
    // InternalKap.g:1510:1: rule__ArgumentDeclaration__Group_2__1 : rule__ArgumentDeclaration__Group_2__1__Impl ;
    public final void rule__ArgumentDeclaration__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1514:1: ( rule__ArgumentDeclaration__Group_2__1__Impl )
            // InternalKap.g:1515:2: rule__ArgumentDeclaration__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2__1"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2__1__Impl"
    // InternalKap.g:1521:1: rule__ArgumentDeclaration__Group_2__1__Impl : ( ( rule__ArgumentDeclaration__Group_2_1__0 )* ) ;
    public final void rule__ArgumentDeclaration__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1525:1: ( ( ( rule__ArgumentDeclaration__Group_2_1__0 )* ) )
            // InternalKap.g:1526:1: ( ( rule__ArgumentDeclaration__Group_2_1__0 )* )
            {
            // InternalKap.g:1526:1: ( ( rule__ArgumentDeclaration__Group_2_1__0 )* )
            // InternalKap.g:1527:2: ( rule__ArgumentDeclaration__Group_2_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getGroup_2_1()); 
            }
            // InternalKap.g:1528:2: ( rule__ArgumentDeclaration__Group_2_1__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==35) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalKap.g:1528:3: rule__ArgumentDeclaration__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_13);
            	    rule__ArgumentDeclaration__Group_2_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getGroup_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2__1__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2_1__0"
    // InternalKap.g:1537:1: rule__ArgumentDeclaration__Group_2_1__0 : rule__ArgumentDeclaration__Group_2_1__0__Impl rule__ArgumentDeclaration__Group_2_1__1 ;
    public final void rule__ArgumentDeclaration__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1541:1: ( rule__ArgumentDeclaration__Group_2_1__0__Impl rule__ArgumentDeclaration__Group_2_1__1 )
            // InternalKap.g:1542:2: rule__ArgumentDeclaration__Group_2_1__0__Impl rule__ArgumentDeclaration__Group_2_1__1
            {
            pushFollow(FOLLOW_5);
            rule__ArgumentDeclaration__Group_2_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group_2_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2_1__0"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2_1__0__Impl"
    // InternalKap.g:1549:1: rule__ArgumentDeclaration__Group_2_1__0__Impl : ( ',' ) ;
    public final void rule__ArgumentDeclaration__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1553:1: ( ( ',' ) )
            // InternalKap.g:1554:1: ( ',' )
            {
            // InternalKap.g:1554:1: ( ',' )
            // InternalKap.g:1555:2: ','
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0()); 
            }
            match(input,35,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getCommaKeyword_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2_1__0__Impl"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2_1__1"
    // InternalKap.g:1564:1: rule__ArgumentDeclaration__Group_2_1__1 : rule__ArgumentDeclaration__Group_2_1__1__Impl ;
    public final void rule__ArgumentDeclaration__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1568:1: ( rule__ArgumentDeclaration__Group_2_1__1__Impl )
            // InternalKap.g:1569:2: rule__ArgumentDeclaration__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__Group_2_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2_1__1"


    // $ANTLR start "rule__ArgumentDeclaration__Group_2_1__1__Impl"
    // InternalKap.g:1575:1: rule__ArgumentDeclaration__Group_2_1__1__Impl : ( ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 ) ) ;
    public final void rule__ArgumentDeclaration__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1579:1: ( ( ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 ) ) )
            // InternalKap.g:1580:1: ( ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 ) )
            {
            // InternalKap.g:1580:1: ( ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 ) )
            // InternalKap.g:1581:2: ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getIdsAssignment_2_1_1()); 
            }
            // InternalKap.g:1582:2: ( rule__ArgumentDeclaration__IdsAssignment_2_1_1 )
            // InternalKap.g:1582:3: rule__ArgumentDeclaration__IdsAssignment_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ArgumentDeclaration__IdsAssignment_2_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getIdsAssignment_2_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__Group_2_1__1__Impl"


    // $ANTLR start "rule__ParameterList__Group__0"
    // InternalKap.g:1591:1: rule__ParameterList__Group__0 : rule__ParameterList__Group__0__Impl rule__ParameterList__Group__1 ;
    public final void rule__ParameterList__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1595:1: ( rule__ParameterList__Group__0__Impl rule__ParameterList__Group__1 )
            // InternalKap.g:1596:2: rule__ParameterList__Group__0__Impl rule__ParameterList__Group__1
            {
            pushFollow(FOLLOW_12);
            rule__ParameterList__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group__0"


    // $ANTLR start "rule__ParameterList__Group__0__Impl"
    // InternalKap.g:1603:1: rule__ParameterList__Group__0__Impl : ( ( rule__ParameterList__PairsAssignment_0 ) ) ;
    public final void rule__ParameterList__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1607:1: ( ( ( rule__ParameterList__PairsAssignment_0 ) ) )
            // InternalKap.g:1608:1: ( ( rule__ParameterList__PairsAssignment_0 ) )
            {
            // InternalKap.g:1608:1: ( ( rule__ParameterList__PairsAssignment_0 ) )
            // InternalKap.g:1609:2: ( rule__ParameterList__PairsAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getPairsAssignment_0()); 
            }
            // InternalKap.g:1610:2: ( rule__ParameterList__PairsAssignment_0 )
            // InternalKap.g:1610:3: rule__ParameterList__PairsAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__PairsAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getPairsAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group__0__Impl"


    // $ANTLR start "rule__ParameterList__Group__1"
    // InternalKap.g:1618:1: rule__ParameterList__Group__1 : rule__ParameterList__Group__1__Impl ;
    public final void rule__ParameterList__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1622:1: ( rule__ParameterList__Group__1__Impl )
            // InternalKap.g:1623:2: rule__ParameterList__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group__1"


    // $ANTLR start "rule__ParameterList__Group__1__Impl"
    // InternalKap.g:1629:1: rule__ParameterList__Group__1__Impl : ( ( rule__ParameterList__Group_1__0 )* ) ;
    public final void rule__ParameterList__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1633:1: ( ( ( rule__ParameterList__Group_1__0 )* ) )
            // InternalKap.g:1634:1: ( ( rule__ParameterList__Group_1__0 )* )
            {
            // InternalKap.g:1634:1: ( ( rule__ParameterList__Group_1__0 )* )
            // InternalKap.g:1635:2: ( rule__ParameterList__Group_1__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getGroup_1()); 
            }
            // InternalKap.g:1636:2: ( rule__ParameterList__Group_1__0 )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==35) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalKap.g:1636:3: rule__ParameterList__Group_1__0
            	    {
            	    pushFollow(FOLLOW_13);
            	    rule__ParameterList__Group_1__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group__1__Impl"


    // $ANTLR start "rule__ParameterList__Group_1__0"
    // InternalKap.g:1645:1: rule__ParameterList__Group_1__0 : rule__ParameterList__Group_1__0__Impl rule__ParameterList__Group_1__1 ;
    public final void rule__ParameterList__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1649:1: ( rule__ParameterList__Group_1__0__Impl rule__ParameterList__Group_1__1 )
            // InternalKap.g:1650:2: rule__ParameterList__Group_1__0__Impl rule__ParameterList__Group_1__1
            {
            pushFollow(FOLLOW_14);
            rule__ParameterList__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group_1__0"


    // $ANTLR start "rule__ParameterList__Group_1__0__Impl"
    // InternalKap.g:1657:1: rule__ParameterList__Group_1__0__Impl : ( ( ',' ) ) ;
    public final void rule__ParameterList__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1661:1: ( ( ( ',' ) ) )
            // InternalKap.g:1662:1: ( ( ',' ) )
            {
            // InternalKap.g:1662:1: ( ( ',' ) )
            // InternalKap.g:1663:2: ( ',' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getCommaKeyword_1_0()); 
            }
            // InternalKap.g:1664:2: ( ',' )
            // InternalKap.g:1664:3: ','
            {
            match(input,35,FOLLOW_2); if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getCommaKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group_1__0__Impl"


    // $ANTLR start "rule__ParameterList__Group_1__1"
    // InternalKap.g:1672:1: rule__ParameterList__Group_1__1 : rule__ParameterList__Group_1__1__Impl ;
    public final void rule__ParameterList__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1676:1: ( rule__ParameterList__Group_1__1__Impl )
            // InternalKap.g:1677:2: rule__ParameterList__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group_1__1"


    // $ANTLR start "rule__ParameterList__Group_1__1__Impl"
    // InternalKap.g:1683:1: rule__ParameterList__Group_1__1__Impl : ( ( rule__ParameterList__PairsAssignment_1_1 ) ) ;
    public final void rule__ParameterList__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1687:1: ( ( ( rule__ParameterList__PairsAssignment_1_1 ) ) )
            // InternalKap.g:1688:1: ( ( rule__ParameterList__PairsAssignment_1_1 ) )
            {
            // InternalKap.g:1688:1: ( ( rule__ParameterList__PairsAssignment_1_1 ) )
            // InternalKap.g:1689:2: ( rule__ParameterList__PairsAssignment_1_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getPairsAssignment_1_1()); 
            }
            // InternalKap.g:1690:2: ( rule__ParameterList__PairsAssignment_1_1 )
            // InternalKap.g:1690:3: rule__ParameterList__PairsAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ParameterList__PairsAssignment_1_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getPairsAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__Group_1__1__Impl"


    // $ANTLR start "rule__KeyValuePair__Group__0"
    // InternalKap.g:1699:1: rule__KeyValuePair__Group__0 : rule__KeyValuePair__Group__0__Impl rule__KeyValuePair__Group__1 ;
    public final void rule__KeyValuePair__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1703:1: ( rule__KeyValuePair__Group__0__Impl rule__KeyValuePair__Group__1 )
            // InternalKap.g:1704:2: rule__KeyValuePair__Group__0__Impl rule__KeyValuePair__Group__1
            {
            pushFollow(FOLLOW_14);
            rule__KeyValuePair__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group__0"


    // $ANTLR start "rule__KeyValuePair__Group__0__Impl"
    // InternalKap.g:1711:1: rule__KeyValuePair__Group__0__Impl : ( ( rule__KeyValuePair__Group_0__0 )? ) ;
    public final void rule__KeyValuePair__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1715:1: ( ( ( rule__KeyValuePair__Group_0__0 )? ) )
            // InternalKap.g:1716:1: ( ( rule__KeyValuePair__Group_0__0 )? )
            {
            // InternalKap.g:1716:1: ( ( rule__KeyValuePair__Group_0__0 )? )
            // InternalKap.g:1717:2: ( rule__KeyValuePair__Group_0__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getGroup_0()); 
            }
            // InternalKap.g:1718:2: ( rule__KeyValuePair__Group_0__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_LOWERCASE_ID) ) {
                int LA21_1 = input.LA(2);

                if ( (LA21_1==17||LA21_1==42) ) {
                    alt21=1;
                }
            }
            switch (alt21) {
                case 1 :
                    // InternalKap.g:1718:3: rule__KeyValuePair__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__KeyValuePair__Group_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getGroup_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group__0__Impl"


    // $ANTLR start "rule__KeyValuePair__Group__1"
    // InternalKap.g:1726:1: rule__KeyValuePair__Group__1 : rule__KeyValuePair__Group__1__Impl ;
    public final void rule__KeyValuePair__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1730:1: ( rule__KeyValuePair__Group__1__Impl )
            // InternalKap.g:1731:2: rule__KeyValuePair__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group__1"


    // $ANTLR start "rule__KeyValuePair__Group__1__Impl"
    // InternalKap.g:1737:1: rule__KeyValuePair__Group__1__Impl : ( ( rule__KeyValuePair__ValueAssignment_1 ) ) ;
    public final void rule__KeyValuePair__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1741:1: ( ( ( rule__KeyValuePair__ValueAssignment_1 ) ) )
            // InternalKap.g:1742:1: ( ( rule__KeyValuePair__ValueAssignment_1 ) )
            {
            // InternalKap.g:1742:1: ( ( rule__KeyValuePair__ValueAssignment_1 ) )
            // InternalKap.g:1743:2: ( rule__KeyValuePair__ValueAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getValueAssignment_1()); 
            }
            // InternalKap.g:1744:2: ( rule__KeyValuePair__ValueAssignment_1 )
            // InternalKap.g:1744:3: rule__KeyValuePair__ValueAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__ValueAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getValueAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group__1__Impl"


    // $ANTLR start "rule__KeyValuePair__Group_0__0"
    // InternalKap.g:1753:1: rule__KeyValuePair__Group_0__0 : rule__KeyValuePair__Group_0__0__Impl rule__KeyValuePair__Group_0__1 ;
    public final void rule__KeyValuePair__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1757:1: ( rule__KeyValuePair__Group_0__0__Impl rule__KeyValuePair__Group_0__1 )
            // InternalKap.g:1758:2: rule__KeyValuePair__Group_0__0__Impl rule__KeyValuePair__Group_0__1
            {
            pushFollow(FOLLOW_15);
            rule__KeyValuePair__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group_0__0"


    // $ANTLR start "rule__KeyValuePair__Group_0__0__Impl"
    // InternalKap.g:1765:1: rule__KeyValuePair__Group_0__0__Impl : ( ( rule__KeyValuePair__NameAssignment_0_0 ) ) ;
    public final void rule__KeyValuePair__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1769:1: ( ( ( rule__KeyValuePair__NameAssignment_0_0 ) ) )
            // InternalKap.g:1770:1: ( ( rule__KeyValuePair__NameAssignment_0_0 ) )
            {
            // InternalKap.g:1770:1: ( ( rule__KeyValuePair__NameAssignment_0_0 ) )
            // InternalKap.g:1771:2: ( rule__KeyValuePair__NameAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getNameAssignment_0_0()); 
            }
            // InternalKap.g:1772:2: ( rule__KeyValuePair__NameAssignment_0_0 )
            // InternalKap.g:1772:3: rule__KeyValuePair__NameAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__NameAssignment_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getNameAssignment_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group_0__0__Impl"


    // $ANTLR start "rule__KeyValuePair__Group_0__1"
    // InternalKap.g:1780:1: rule__KeyValuePair__Group_0__1 : rule__KeyValuePair__Group_0__1__Impl ;
    public final void rule__KeyValuePair__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1784:1: ( rule__KeyValuePair__Group_0__1__Impl )
            // InternalKap.g:1785:2: rule__KeyValuePair__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group_0__1"


    // $ANTLR start "rule__KeyValuePair__Group_0__1__Impl"
    // InternalKap.g:1791:1: rule__KeyValuePair__Group_0__1__Impl : ( ( rule__KeyValuePair__Alternatives_0_1 ) ) ;
    public final void rule__KeyValuePair__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1795:1: ( ( ( rule__KeyValuePair__Alternatives_0_1 ) ) )
            // InternalKap.g:1796:1: ( ( rule__KeyValuePair__Alternatives_0_1 ) )
            {
            // InternalKap.g:1796:1: ( ( rule__KeyValuePair__Alternatives_0_1 ) )
            // InternalKap.g:1797:2: ( rule__KeyValuePair__Alternatives_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getAlternatives_0_1()); 
            }
            // InternalKap.g:1798:2: ( rule__KeyValuePair__Alternatives_0_1 )
            // InternalKap.g:1798:3: rule__KeyValuePair__Alternatives_0_1
            {
            pushFollow(FOLLOW_2);
            rule__KeyValuePair__Alternatives_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getAlternatives_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__Group_0__1__Impl"


    // $ANTLR start "rule__Literal__Group_1__0"
    // InternalKap.g:1807:1: rule__Literal__Group_1__0 : rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1 ;
    public final void rule__Literal__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1811:1: ( rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1 )
            // InternalKap.g:1812:2: rule__Literal__Group_1__0__Impl rule__Literal__Group_1__1
            {
            pushFollow(FOLLOW_16);
            rule__Literal__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Literal__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__0"


    // $ANTLR start "rule__Literal__Group_1__0__Impl"
    // InternalKap.g:1819:1: rule__Literal__Group_1__0__Impl : ( ( rule__Literal__FromAssignment_1_0 ) ) ;
    public final void rule__Literal__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1823:1: ( ( ( rule__Literal__FromAssignment_1_0 ) ) )
            // InternalKap.g:1824:1: ( ( rule__Literal__FromAssignment_1_0 ) )
            {
            // InternalKap.g:1824:1: ( ( rule__Literal__FromAssignment_1_0 ) )
            // InternalKap.g:1825:2: ( rule__Literal__FromAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getFromAssignment_1_0()); 
            }
            // InternalKap.g:1826:2: ( rule__Literal__FromAssignment_1_0 )
            // InternalKap.g:1826:3: rule__Literal__FromAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Literal__FromAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getFromAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__0__Impl"


    // $ANTLR start "rule__Literal__Group_1__1"
    // InternalKap.g:1834:1: rule__Literal__Group_1__1 : rule__Literal__Group_1__1__Impl rule__Literal__Group_1__2 ;
    public final void rule__Literal__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1838:1: ( rule__Literal__Group_1__1__Impl rule__Literal__Group_1__2 )
            // InternalKap.g:1839:2: rule__Literal__Group_1__1__Impl rule__Literal__Group_1__2
            {
            pushFollow(FOLLOW_17);
            rule__Literal__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Literal__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__1"


    // $ANTLR start "rule__Literal__Group_1__1__Impl"
    // InternalKap.g:1846:1: rule__Literal__Group_1__1__Impl : ( 'to' ) ;
    public final void rule__Literal__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1850:1: ( ( 'to' ) )
            // InternalKap.g:1851:1: ( 'to' )
            {
            // InternalKap.g:1851:1: ( 'to' )
            // InternalKap.g:1852:2: 'to'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getToKeyword_1_1()); 
            }
            match(input,36,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getToKeyword_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__1__Impl"


    // $ANTLR start "rule__Literal__Group_1__2"
    // InternalKap.g:1861:1: rule__Literal__Group_1__2 : rule__Literal__Group_1__2__Impl ;
    public final void rule__Literal__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1865:1: ( rule__Literal__Group_1__2__Impl )
            // InternalKap.g:1866:2: rule__Literal__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Literal__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__2"


    // $ANTLR start "rule__Literal__Group_1__2__Impl"
    // InternalKap.g:1872:1: rule__Literal__Group_1__2__Impl : ( ( rule__Literal__ToAssignment_1_2 ) ) ;
    public final void rule__Literal__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1876:1: ( ( ( rule__Literal__ToAssignment_1_2 ) ) )
            // InternalKap.g:1877:1: ( ( rule__Literal__ToAssignment_1_2 ) )
            {
            // InternalKap.g:1877:1: ( ( rule__Literal__ToAssignment_1_2 ) )
            // InternalKap.g:1878:2: ( rule__Literal__ToAssignment_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getToAssignment_1_2()); 
            }
            // InternalKap.g:1879:2: ( rule__Literal__ToAssignment_1_2 )
            // InternalKap.g:1879:3: rule__Literal__ToAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Literal__ToAssignment_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getToAssignment_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__Group_1__2__Impl"


    // $ANTLR start "rule__Body__Group_0__0"
    // InternalKap.g:1888:1: rule__Body__Group_0__0 : rule__Body__Group_0__0__Impl rule__Body__Group_0__1 ;
    public final void rule__Body__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1892:1: ( rule__Body__Group_0__0__Impl rule__Body__Group_0__1 )
            // InternalKap.g:1893:2: rule__Body__Group_0__0__Impl rule__Body__Group_0__1
            {
            pushFollow(FOLLOW_9);
            rule__Body__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__0"


    // $ANTLR start "rule__Body__Group_0__0__Impl"
    // InternalKap.g:1900:1: rule__Body__Group_0__0__Impl : ( () ) ;
    public final void rule__Body__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1904:1: ( ( () ) )
            // InternalKap.g:1905:1: ( () )
            {
            // InternalKap.g:1905:1: ( () )
            // InternalKap.g:1906:2: ()
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getBodyAction_0_0()); 
            }
            // InternalKap.g:1907:2: ()
            // InternalKap.g:1907:3: 
            {
            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getBodyAction_0_0()); 
            }

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__0__Impl"


    // $ANTLR start "rule__Body__Group_0__1"
    // InternalKap.g:1915:1: rule__Body__Group_0__1 : rule__Body__Group_0__1__Impl rule__Body__Group_0__2 ;
    public final void rule__Body__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1919:1: ( rule__Body__Group_0__1__Impl rule__Body__Group_0__2 )
            // InternalKap.g:1920:2: rule__Body__Group_0__1__Impl rule__Body__Group_0__2
            {
            pushFollow(FOLLOW_9);
            rule__Body__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__1"


    // $ANTLR start "rule__Body__Group_0__1__Impl"
    // InternalKap.g:1927:1: rule__Body__Group_0__1__Impl : ( ( rule__Body__ListAssignment_0_1 ) ) ;
    public final void rule__Body__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1931:1: ( ( ( rule__Body__ListAssignment_0_1 ) ) )
            // InternalKap.g:1932:1: ( ( rule__Body__ListAssignment_0_1 ) )
            {
            // InternalKap.g:1932:1: ( ( rule__Body__ListAssignment_0_1 ) )
            // InternalKap.g:1933:2: ( rule__Body__ListAssignment_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getListAssignment_0_1()); 
            }
            // InternalKap.g:1934:2: ( rule__Body__ListAssignment_0_1 )
            // InternalKap.g:1934:3: rule__Body__ListAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Body__ListAssignment_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getListAssignment_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__1__Impl"


    // $ANTLR start "rule__Body__Group_0__2"
    // InternalKap.g:1942:1: rule__Body__Group_0__2 : rule__Body__Group_0__2__Impl ;
    public final void rule__Body__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1946:1: ( rule__Body__Group_0__2__Impl )
            // InternalKap.g:1947:2: rule__Body__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Body__Group_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__2"


    // $ANTLR start "rule__Body__Group_0__2__Impl"
    // InternalKap.g:1953:1: rule__Body__Group_0__2__Impl : ( ( rule__Body__ListAssignment_0_2 )* ) ;
    public final void rule__Body__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1957:1: ( ( ( rule__Body__ListAssignment_0_2 )* ) )
            // InternalKap.g:1958:1: ( ( rule__Body__ListAssignment_0_2 )* )
            {
            // InternalKap.g:1958:1: ( ( rule__Body__ListAssignment_0_2 )* )
            // InternalKap.g:1959:2: ( rule__Body__ListAssignment_0_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getListAssignment_0_2()); 
            }
            // InternalKap.g:1960:2: ( rule__Body__ListAssignment_0_2 )*
            loop22:
            do {
                int alt22=2;
                alt22 = dfa22.predict(input);
                switch (alt22) {
            	case 1 :
            	    // InternalKap.g:1960:3: rule__Body__ListAssignment_0_2
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__Body__ListAssignment_0_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getListAssignment_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_0__2__Impl"


    // $ANTLR start "rule__Body__Group_1__0"
    // InternalKap.g:1969:1: rule__Body__Group_1__0 : rule__Body__Group_1__0__Impl rule__Body__Group_1__1 ;
    public final void rule__Body__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1973:1: ( rule__Body__Group_1__0__Impl rule__Body__Group_1__1 )
            // InternalKap.g:1974:2: rule__Body__Group_1__0__Impl rule__Body__Group_1__1
            {
            pushFollow(FOLLOW_19);
            rule__Body__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__0"


    // $ANTLR start "rule__Body__Group_1__0__Impl"
    // InternalKap.g:1981:1: rule__Body__Group_1__0__Impl : ( ( rule__Body__IsgroupAssignment_1_0 ) ) ;
    public final void rule__Body__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:1985:1: ( ( ( rule__Body__IsgroupAssignment_1_0 ) ) )
            // InternalKap.g:1986:1: ( ( rule__Body__IsgroupAssignment_1_0 ) )
            {
            // InternalKap.g:1986:1: ( ( rule__Body__IsgroupAssignment_1_0 ) )
            // InternalKap.g:1987:2: ( rule__Body__IsgroupAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getIsgroupAssignment_1_0()); 
            }
            // InternalKap.g:1988:2: ( rule__Body__IsgroupAssignment_1_0 )
            // InternalKap.g:1988:3: rule__Body__IsgroupAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Body__IsgroupAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getIsgroupAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__0__Impl"


    // $ANTLR start "rule__Body__Group_1__1"
    // InternalKap.g:1996:1: rule__Body__Group_1__1 : rule__Body__Group_1__1__Impl rule__Body__Group_1__2 ;
    public final void rule__Body__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2000:1: ( rule__Body__Group_1__1__Impl rule__Body__Group_1__2 )
            // InternalKap.g:2001:2: rule__Body__Group_1__1__Impl rule__Body__Group_1__2
            {
            pushFollow(FOLLOW_19);
            rule__Body__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__1"


    // $ANTLR start "rule__Body__Group_1__1__Impl"
    // InternalKap.g:2008:1: rule__Body__Group_1__1__Impl : ( ( rule__Body__Group_1_1__0 )? ) ;
    public final void rule__Body__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2012:1: ( ( ( rule__Body__Group_1_1__0 )? ) )
            // InternalKap.g:2013:1: ( ( rule__Body__Group_1_1__0 )? )
            {
            // InternalKap.g:2013:1: ( ( rule__Body__Group_1_1__0 )? )
            // InternalKap.g:2014:2: ( rule__Body__Group_1_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroup_1_1()); 
            }
            // InternalKap.g:2015:2: ( rule__Body__Group_1_1__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_LOWERCASE_ID||LA23_0==RULE_EMBEDDEDTEXT||LA23_0==33||LA23_0==37) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalKap.g:2015:3: rule__Body__Group_1_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Body__Group_1_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroup_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__1__Impl"


    // $ANTLR start "rule__Body__Group_1__2"
    // InternalKap.g:2023:1: rule__Body__Group_1__2 : rule__Body__Group_1__2__Impl ;
    public final void rule__Body__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2027:1: ( rule__Body__Group_1__2__Impl )
            // InternalKap.g:2028:2: rule__Body__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Body__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__2"


    // $ANTLR start "rule__Body__Group_1__2__Impl"
    // InternalKap.g:2034:1: rule__Body__Group_1__2__Impl : ( ')' ) ;
    public final void rule__Body__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2038:1: ( ( ')' ) )
            // InternalKap.g:2039:1: ( ')' )
            {
            // InternalKap.g:2039:1: ( ')' )
            // InternalKap.g:2040:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getRightParenthesisKeyword_1_2()); 
            }
            match(input,34,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getRightParenthesisKeyword_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1__2__Impl"


    // $ANTLR start "rule__Body__Group_1_1__0"
    // InternalKap.g:2050:1: rule__Body__Group_1_1__0 : rule__Body__Group_1_1__0__Impl rule__Body__Group_1_1__1 ;
    public final void rule__Body__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2054:1: ( rule__Body__Group_1_1__0__Impl rule__Body__Group_1_1__1 )
            // InternalKap.g:2055:2: rule__Body__Group_1_1__0__Impl rule__Body__Group_1_1__1
            {
            pushFollow(FOLLOW_9);
            rule__Body__Group_1_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Body__Group_1_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1__0"


    // $ANTLR start "rule__Body__Group_1_1__0__Impl"
    // InternalKap.g:2062:1: rule__Body__Group_1_1__0__Impl : ( ( rule__Body__GroupAssignment_1_1_0 ) ) ;
    public final void rule__Body__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2066:1: ( ( ( rule__Body__GroupAssignment_1_1_0 ) ) )
            // InternalKap.g:2067:1: ( ( rule__Body__GroupAssignment_1_1_0 ) )
            {
            // InternalKap.g:2067:1: ( ( rule__Body__GroupAssignment_1_1_0 ) )
            // InternalKap.g:2068:2: ( rule__Body__GroupAssignment_1_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroupAssignment_1_1_0()); 
            }
            // InternalKap.g:2069:2: ( rule__Body__GroupAssignment_1_1_0 )
            // InternalKap.g:2069:3: rule__Body__GroupAssignment_1_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Body__GroupAssignment_1_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroupAssignment_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1__0__Impl"


    // $ANTLR start "rule__Body__Group_1_1__1"
    // InternalKap.g:2077:1: rule__Body__Group_1_1__1 : rule__Body__Group_1_1__1__Impl ;
    public final void rule__Body__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2081:1: ( rule__Body__Group_1_1__1__Impl )
            // InternalKap.g:2082:2: rule__Body__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Body__Group_1_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1__1"


    // $ANTLR start "rule__Body__Group_1_1__1__Impl"
    // InternalKap.g:2088:1: rule__Body__Group_1_1__1__Impl : ( ( rule__Body__GroupAssignment_1_1_1 )* ) ;
    public final void rule__Body__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2092:1: ( ( ( rule__Body__GroupAssignment_1_1_1 )* ) )
            // InternalKap.g:2093:1: ( ( rule__Body__GroupAssignment_1_1_1 )* )
            {
            // InternalKap.g:2093:1: ( ( rule__Body__GroupAssignment_1_1_1 )* )
            // InternalKap.g:2094:2: ( rule__Body__GroupAssignment_1_1_1 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroupAssignment_1_1_1()); 
            }
            // InternalKap.g:2095:2: ( rule__Body__GroupAssignment_1_1_1 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==RULE_LOWERCASE_ID||LA24_0==RULE_EMBEDDEDTEXT||LA24_0==33||LA24_0==37) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalKap.g:2095:3: rule__Body__GroupAssignment_1_1_1
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__Body__GroupAssignment_1_1_1();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroupAssignment_1_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__Group_1_1__1__Impl"


    // $ANTLR start "rule__Statement__Group_3__0"
    // InternalKap.g:2104:1: rule__Statement__Group_3__0 : rule__Statement__Group_3__0__Impl rule__Statement__Group_3__1 ;
    public final void rule__Statement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2108:1: ( rule__Statement__Group_3__0__Impl rule__Statement__Group_3__1 )
            // InternalKap.g:2109:2: rule__Statement__Group_3__0__Impl rule__Statement__Group_3__1
            {
            pushFollow(FOLLOW_9);
            rule__Statement__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Statement__Group_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_3__0"


    // $ANTLR start "rule__Statement__Group_3__0__Impl"
    // InternalKap.g:2116:1: rule__Statement__Group_3__0__Impl : ( '(' ) ;
    public final void rule__Statement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2120:1: ( ( '(' ) )
            // InternalKap.g:2121:1: ( '(' )
            {
            // InternalKap.g:2121:1: ( '(' )
            // InternalKap.g:2122:2: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getLeftParenthesisKeyword_3_0()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getLeftParenthesisKeyword_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_3__0__Impl"


    // $ANTLR start "rule__Statement__Group_3__1"
    // InternalKap.g:2131:1: rule__Statement__Group_3__1 : rule__Statement__Group_3__1__Impl rule__Statement__Group_3__2 ;
    public final void rule__Statement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2135:1: ( rule__Statement__Group_3__1__Impl rule__Statement__Group_3__2 )
            // InternalKap.g:2136:2: rule__Statement__Group_3__1__Impl rule__Statement__Group_3__2
            {
            pushFollow(FOLLOW_19);
            rule__Statement__Group_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Statement__Group_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_3__1"


    // $ANTLR start "rule__Statement__Group_3__1__Impl"
    // InternalKap.g:2143:1: rule__Statement__Group_3__1__Impl : ( ( rule__Statement__GroupAssignment_3_1 ) ) ;
    public final void rule__Statement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2147:1: ( ( ( rule__Statement__GroupAssignment_3_1 ) ) )
            // InternalKap.g:2148:1: ( ( rule__Statement__GroupAssignment_3_1 ) )
            {
            // InternalKap.g:2148:1: ( ( rule__Statement__GroupAssignment_3_1 ) )
            // InternalKap.g:2149:2: ( rule__Statement__GroupAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getGroupAssignment_3_1()); 
            }
            // InternalKap.g:2150:2: ( rule__Statement__GroupAssignment_3_1 )
            // InternalKap.g:2150:3: rule__Statement__GroupAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Statement__GroupAssignment_3_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getGroupAssignment_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_3__1__Impl"


    // $ANTLR start "rule__Statement__Group_3__2"
    // InternalKap.g:2158:1: rule__Statement__Group_3__2 : rule__Statement__Group_3__2__Impl rule__Statement__Group_3__3 ;
    public final void rule__Statement__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2162:1: ( rule__Statement__Group_3__2__Impl rule__Statement__Group_3__3 )
            // InternalKap.g:2163:2: rule__Statement__Group_3__2__Impl rule__Statement__Group_3__3
            {
            pushFollow(FOLLOW_19);
            rule__Statement__Group_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Statement__Group_3__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_3__2"


    // $ANTLR start "rule__Statement__Group_3__2__Impl"
    // InternalKap.g:2170:1: rule__Statement__Group_3__2__Impl : ( ( rule__Statement__GroupAssignment_3_2 )* ) ;
    public final void rule__Statement__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2174:1: ( ( ( rule__Statement__GroupAssignment_3_2 )* ) )
            // InternalKap.g:2175:1: ( ( rule__Statement__GroupAssignment_3_2 )* )
            {
            // InternalKap.g:2175:1: ( ( rule__Statement__GroupAssignment_3_2 )* )
            // InternalKap.g:2176:2: ( rule__Statement__GroupAssignment_3_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getGroupAssignment_3_2()); 
            }
            // InternalKap.g:2177:2: ( rule__Statement__GroupAssignment_3_2 )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==RULE_LOWERCASE_ID||LA25_0==RULE_EMBEDDEDTEXT||LA25_0==33||LA25_0==37) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalKap.g:2177:3: rule__Statement__GroupAssignment_3_2
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__Statement__GroupAssignment_3_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getGroupAssignment_3_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_3__2__Impl"


    // $ANTLR start "rule__Statement__Group_3__3"
    // InternalKap.g:2185:1: rule__Statement__Group_3__3 : rule__Statement__Group_3__3__Impl ;
    public final void rule__Statement__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2189:1: ( rule__Statement__Group_3__3__Impl )
            // InternalKap.g:2190:2: rule__Statement__Group_3__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Statement__Group_3__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_3__3"


    // $ANTLR start "rule__Statement__Group_3__3__Impl"
    // InternalKap.g:2196:1: rule__Statement__Group_3__3__Impl : ( ')' ) ;
    public final void rule__Statement__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2200:1: ( ( ')' ) )
            // InternalKap.g:2201:1: ( ')' )
            {
            // InternalKap.g:2201:1: ( ')' )
            // InternalKap.g:2202:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getRightParenthesisKeyword_3_3()); 
            }
            match(input,34,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getRightParenthesisKeyword_3_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__Group_3__3__Impl"


    // $ANTLR start "rule__IfStatement__Group__0"
    // InternalKap.g:2212:1: rule__IfStatement__Group__0 : rule__IfStatement__Group__0__Impl rule__IfStatement__Group__1 ;
    public final void rule__IfStatement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2216:1: ( rule__IfStatement__Group__0__Impl rule__IfStatement__Group__1 )
            // InternalKap.g:2217:2: rule__IfStatement__Group__0__Impl rule__IfStatement__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__IfStatement__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__0"


    // $ANTLR start "rule__IfStatement__Group__0__Impl"
    // InternalKap.g:2224:1: rule__IfStatement__Group__0__Impl : ( 'if' ) ;
    public final void rule__IfStatement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2228:1: ( ( 'if' ) )
            // InternalKap.g:2229:1: ( 'if' )
            {
            // InternalKap.g:2229:1: ( 'if' )
            // InternalKap.g:2230:2: 'if'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getIfKeyword_0()); 
            }
            match(input,37,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getIfKeyword_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__0__Impl"


    // $ANTLR start "rule__IfStatement__Group__1"
    // InternalKap.g:2239:1: rule__IfStatement__Group__1 : rule__IfStatement__Group__1__Impl rule__IfStatement__Group__2 ;
    public final void rule__IfStatement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2243:1: ( rule__IfStatement__Group__1__Impl rule__IfStatement__Group__2 )
            // InternalKap.g:2244:2: rule__IfStatement__Group__1__Impl rule__IfStatement__Group__2
            {
            pushFollow(FOLLOW_9);
            rule__IfStatement__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__1"


    // $ANTLR start "rule__IfStatement__Group__1__Impl"
    // InternalKap.g:2251:1: rule__IfStatement__Group__1__Impl : ( ( rule__IfStatement__ExpressionAssignment_1 ) ) ;
    public final void rule__IfStatement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2255:1: ( ( ( rule__IfStatement__ExpressionAssignment_1 ) ) )
            // InternalKap.g:2256:1: ( ( rule__IfStatement__ExpressionAssignment_1 ) )
            {
            // InternalKap.g:2256:1: ( ( rule__IfStatement__ExpressionAssignment_1 ) )
            // InternalKap.g:2257:2: ( rule__IfStatement__ExpressionAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getExpressionAssignment_1()); 
            }
            // InternalKap.g:2258:2: ( rule__IfStatement__ExpressionAssignment_1 )
            // InternalKap.g:2258:3: rule__IfStatement__ExpressionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__ExpressionAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getExpressionAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__1__Impl"


    // $ANTLR start "rule__IfStatement__Group__2"
    // InternalKap.g:2266:1: rule__IfStatement__Group__2 : rule__IfStatement__Group__2__Impl rule__IfStatement__Group__3 ;
    public final void rule__IfStatement__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2270:1: ( rule__IfStatement__Group__2__Impl rule__IfStatement__Group__3 )
            // InternalKap.g:2271:2: rule__IfStatement__Group__2__Impl rule__IfStatement__Group__3
            {
            pushFollow(FOLLOW_21);
            rule__IfStatement__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__2"


    // $ANTLR start "rule__IfStatement__Group__2__Impl"
    // InternalKap.g:2278:1: rule__IfStatement__Group__2__Impl : ( ( rule__IfStatement__BodyAssignment_2 ) ) ;
    public final void rule__IfStatement__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2282:1: ( ( ( rule__IfStatement__BodyAssignment_2 ) ) )
            // InternalKap.g:2283:1: ( ( rule__IfStatement__BodyAssignment_2 ) )
            {
            // InternalKap.g:2283:1: ( ( rule__IfStatement__BodyAssignment_2 ) )
            // InternalKap.g:2284:2: ( rule__IfStatement__BodyAssignment_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getBodyAssignment_2()); 
            }
            // InternalKap.g:2285:2: ( rule__IfStatement__BodyAssignment_2 )
            // InternalKap.g:2285:3: rule__IfStatement__BodyAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__BodyAssignment_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getBodyAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__2__Impl"


    // $ANTLR start "rule__IfStatement__Group__3"
    // InternalKap.g:2293:1: rule__IfStatement__Group__3 : rule__IfStatement__Group__3__Impl rule__IfStatement__Group__4 ;
    public final void rule__IfStatement__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2297:1: ( rule__IfStatement__Group__3__Impl rule__IfStatement__Group__4 )
            // InternalKap.g:2298:2: rule__IfStatement__Group__3__Impl rule__IfStatement__Group__4
            {
            pushFollow(FOLLOW_21);
            rule__IfStatement__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__3"


    // $ANTLR start "rule__IfStatement__Group__3__Impl"
    // InternalKap.g:2305:1: rule__IfStatement__Group__3__Impl : ( ( rule__IfStatement__Group_3__0 )* ) ;
    public final void rule__IfStatement__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2309:1: ( ( ( rule__IfStatement__Group_3__0 )* ) )
            // InternalKap.g:2310:1: ( ( rule__IfStatement__Group_3__0 )* )
            {
            // InternalKap.g:2310:1: ( ( rule__IfStatement__Group_3__0 )* )
            // InternalKap.g:2311:2: ( rule__IfStatement__Group_3__0 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getGroup_3()); 
            }
            // InternalKap.g:2312:2: ( rule__IfStatement__Group_3__0 )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==38) ) {
                    int LA26_1 = input.LA(2);

                    if ( (synpred41_InternalKap()) ) {
                        alt26=1;
                    }


                }


                switch (alt26) {
            	case 1 :
            	    // InternalKap.g:2312:3: rule__IfStatement__Group_3__0
            	    {
            	    pushFollow(FOLLOW_22);
            	    rule__IfStatement__Group_3__0();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getGroup_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__3__Impl"


    // $ANTLR start "rule__IfStatement__Group__4"
    // InternalKap.g:2320:1: rule__IfStatement__Group__4 : rule__IfStatement__Group__4__Impl ;
    public final void rule__IfStatement__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2324:1: ( rule__IfStatement__Group__4__Impl )
            // InternalKap.g:2325:2: rule__IfStatement__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__4"


    // $ANTLR start "rule__IfStatement__Group__4__Impl"
    // InternalKap.g:2331:1: rule__IfStatement__Group__4__Impl : ( ( rule__IfStatement__Group_4__0 )? ) ;
    public final void rule__IfStatement__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2335:1: ( ( ( rule__IfStatement__Group_4__0 )? ) )
            // InternalKap.g:2336:1: ( ( rule__IfStatement__Group_4__0 )? )
            {
            // InternalKap.g:2336:1: ( ( rule__IfStatement__Group_4__0 )? )
            // InternalKap.g:2337:2: ( rule__IfStatement__Group_4__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getGroup_4()); 
            }
            // InternalKap.g:2338:2: ( rule__IfStatement__Group_4__0 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==38) ) {
                int LA27_1 = input.LA(2);

                if ( (synpred42_InternalKap()) ) {
                    alt27=1;
                }
            }
            switch (alt27) {
                case 1 :
                    // InternalKap.g:2338:3: rule__IfStatement__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__IfStatement__Group_4__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getGroup_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group__4__Impl"


    // $ANTLR start "rule__IfStatement__Group_3__0"
    // InternalKap.g:2347:1: rule__IfStatement__Group_3__0 : rule__IfStatement__Group_3__0__Impl rule__IfStatement__Group_3__1 ;
    public final void rule__IfStatement__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2351:1: ( rule__IfStatement__Group_3__0__Impl rule__IfStatement__Group_3__1 )
            // InternalKap.g:2352:2: rule__IfStatement__Group_3__0__Impl rule__IfStatement__Group_3__1
            {
            pushFollow(FOLLOW_23);
            rule__IfStatement__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_3__0"


    // $ANTLR start "rule__IfStatement__Group_3__0__Impl"
    // InternalKap.g:2359:1: rule__IfStatement__Group_3__0__Impl : ( 'else' ) ;
    public final void rule__IfStatement__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2363:1: ( ( 'else' ) )
            // InternalKap.g:2364:1: ( 'else' )
            {
            // InternalKap.g:2364:1: ( 'else' )
            // InternalKap.g:2365:2: 'else'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getElseKeyword_3_0()); 
            }
            match(input,38,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getElseKeyword_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_3__0__Impl"


    // $ANTLR start "rule__IfStatement__Group_3__1"
    // InternalKap.g:2374:1: rule__IfStatement__Group_3__1 : rule__IfStatement__Group_3__1__Impl rule__IfStatement__Group_3__2 ;
    public final void rule__IfStatement__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2378:1: ( rule__IfStatement__Group_3__1__Impl rule__IfStatement__Group_3__2 )
            // InternalKap.g:2379:2: rule__IfStatement__Group_3__1__Impl rule__IfStatement__Group_3__2
            {
            pushFollow(FOLLOW_20);
            rule__IfStatement__Group_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_3__1"


    // $ANTLR start "rule__IfStatement__Group_3__1__Impl"
    // InternalKap.g:2386:1: rule__IfStatement__Group_3__1__Impl : ( 'if' ) ;
    public final void rule__IfStatement__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2390:1: ( ( 'if' ) )
            // InternalKap.g:2391:1: ( 'if' )
            {
            // InternalKap.g:2391:1: ( 'if' )
            // InternalKap.g:2392:2: 'if'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getIfKeyword_3_1()); 
            }
            match(input,37,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getIfKeyword_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_3__1__Impl"


    // $ANTLR start "rule__IfStatement__Group_3__2"
    // InternalKap.g:2401:1: rule__IfStatement__Group_3__2 : rule__IfStatement__Group_3__2__Impl rule__IfStatement__Group_3__3 ;
    public final void rule__IfStatement__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2405:1: ( rule__IfStatement__Group_3__2__Impl rule__IfStatement__Group_3__3 )
            // InternalKap.g:2406:2: rule__IfStatement__Group_3__2__Impl rule__IfStatement__Group_3__3
            {
            pushFollow(FOLLOW_9);
            rule__IfStatement__Group_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group_3__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_3__2"


    // $ANTLR start "rule__IfStatement__Group_3__2__Impl"
    // InternalKap.g:2413:1: rule__IfStatement__Group_3__2__Impl : ( ( rule__IfStatement__ElseIfExpressionAssignment_3_2 ) ) ;
    public final void rule__IfStatement__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2417:1: ( ( ( rule__IfStatement__ElseIfExpressionAssignment_3_2 ) ) )
            // InternalKap.g:2418:1: ( ( rule__IfStatement__ElseIfExpressionAssignment_3_2 ) )
            {
            // InternalKap.g:2418:1: ( ( rule__IfStatement__ElseIfExpressionAssignment_3_2 ) )
            // InternalKap.g:2419:2: ( rule__IfStatement__ElseIfExpressionAssignment_3_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getElseIfExpressionAssignment_3_2()); 
            }
            // InternalKap.g:2420:2: ( rule__IfStatement__ElseIfExpressionAssignment_3_2 )
            // InternalKap.g:2420:3: rule__IfStatement__ElseIfExpressionAssignment_3_2
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__ElseIfExpressionAssignment_3_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getElseIfExpressionAssignment_3_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_3__2__Impl"


    // $ANTLR start "rule__IfStatement__Group_3__3"
    // InternalKap.g:2428:1: rule__IfStatement__Group_3__3 : rule__IfStatement__Group_3__3__Impl ;
    public final void rule__IfStatement__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2432:1: ( rule__IfStatement__Group_3__3__Impl )
            // InternalKap.g:2433:2: rule__IfStatement__Group_3__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group_3__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_3__3"


    // $ANTLR start "rule__IfStatement__Group_3__3__Impl"
    // InternalKap.g:2439:1: rule__IfStatement__Group_3__3__Impl : ( ( rule__IfStatement__ElseIfCallAssignment_3_3 ) ) ;
    public final void rule__IfStatement__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2443:1: ( ( ( rule__IfStatement__ElseIfCallAssignment_3_3 ) ) )
            // InternalKap.g:2444:1: ( ( rule__IfStatement__ElseIfCallAssignment_3_3 ) )
            {
            // InternalKap.g:2444:1: ( ( rule__IfStatement__ElseIfCallAssignment_3_3 ) )
            // InternalKap.g:2445:2: ( rule__IfStatement__ElseIfCallAssignment_3_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getElseIfCallAssignment_3_3()); 
            }
            // InternalKap.g:2446:2: ( rule__IfStatement__ElseIfCallAssignment_3_3 )
            // InternalKap.g:2446:3: rule__IfStatement__ElseIfCallAssignment_3_3
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__ElseIfCallAssignment_3_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getElseIfCallAssignment_3_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_3__3__Impl"


    // $ANTLR start "rule__IfStatement__Group_4__0"
    // InternalKap.g:2455:1: rule__IfStatement__Group_4__0 : rule__IfStatement__Group_4__0__Impl rule__IfStatement__Group_4__1 ;
    public final void rule__IfStatement__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2459:1: ( rule__IfStatement__Group_4__0__Impl rule__IfStatement__Group_4__1 )
            // InternalKap.g:2460:2: rule__IfStatement__Group_4__0__Impl rule__IfStatement__Group_4__1
            {
            pushFollow(FOLLOW_9);
            rule__IfStatement__Group_4__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group_4__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_4__0"


    // $ANTLR start "rule__IfStatement__Group_4__0__Impl"
    // InternalKap.g:2467:1: rule__IfStatement__Group_4__0__Impl : ( 'else' ) ;
    public final void rule__IfStatement__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2471:1: ( ( 'else' ) )
            // InternalKap.g:2472:1: ( 'else' )
            {
            // InternalKap.g:2472:1: ( 'else' )
            // InternalKap.g:2473:2: 'else'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getElseKeyword_4_0()); 
            }
            match(input,38,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getElseKeyword_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_4__0__Impl"


    // $ANTLR start "rule__IfStatement__Group_4__1"
    // InternalKap.g:2482:1: rule__IfStatement__Group_4__1 : rule__IfStatement__Group_4__1__Impl ;
    public final void rule__IfStatement__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2486:1: ( rule__IfStatement__Group_4__1__Impl )
            // InternalKap.g:2487:2: rule__IfStatement__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__Group_4__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_4__1"


    // $ANTLR start "rule__IfStatement__Group_4__1__Impl"
    // InternalKap.g:2493:1: rule__IfStatement__Group_4__1__Impl : ( ( rule__IfStatement__ElseCallAssignment_4_1 ) ) ;
    public final void rule__IfStatement__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2497:1: ( ( ( rule__IfStatement__ElseCallAssignment_4_1 ) ) )
            // InternalKap.g:2498:1: ( ( rule__IfStatement__ElseCallAssignment_4_1 ) )
            {
            // InternalKap.g:2498:1: ( ( rule__IfStatement__ElseCallAssignment_4_1 ) )
            // InternalKap.g:2499:2: ( rule__IfStatement__ElseCallAssignment_4_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getElseCallAssignment_4_1()); 
            }
            // InternalKap.g:2500:2: ( rule__IfStatement__ElseCallAssignment_4_1 )
            // InternalKap.g:2500:3: rule__IfStatement__ElseCallAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__IfStatement__ElseCallAssignment_4_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getElseCallAssignment_4_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__Group_4__1__Impl"


    // $ANTLR start "rule__Call__Group__0"
    // InternalKap.g:2509:1: rule__Call__Group__0 : rule__Call__Group__0__Impl rule__Call__Group__1 ;
    public final void rule__Call__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2513:1: ( rule__Call__Group__0__Impl rule__Call__Group__1 )
            // InternalKap.g:2514:2: rule__Call__Group__0__Impl rule__Call__Group__1
            {
            pushFollow(FOLLOW_24);
            rule__Call__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__0"


    // $ANTLR start "rule__Call__Group__0__Impl"
    // InternalKap.g:2521:1: rule__Call__Group__0__Impl : ( ( rule__Call__NameAssignment_0 ) ) ;
    public final void rule__Call__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2525:1: ( ( ( rule__Call__NameAssignment_0 ) ) )
            // InternalKap.g:2526:1: ( ( rule__Call__NameAssignment_0 ) )
            {
            // InternalKap.g:2526:1: ( ( rule__Call__NameAssignment_0 ) )
            // InternalKap.g:2527:2: ( rule__Call__NameAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getNameAssignment_0()); 
            }
            // InternalKap.g:2528:2: ( rule__Call__NameAssignment_0 )
            // InternalKap.g:2528:3: rule__Call__NameAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Call__NameAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getNameAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__0__Impl"


    // $ANTLR start "rule__Call__Group__1"
    // InternalKap.g:2536:1: rule__Call__Group__1 : rule__Call__Group__1__Impl rule__Call__Group__2 ;
    public final void rule__Call__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2540:1: ( rule__Call__Group__1__Impl rule__Call__Group__2 )
            // InternalKap.g:2541:2: rule__Call__Group__1__Impl rule__Call__Group__2
            {
            pushFollow(FOLLOW_24);
            rule__Call__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__1"


    // $ANTLR start "rule__Call__Group__1__Impl"
    // InternalKap.g:2548:1: rule__Call__Group__1__Impl : ( ( rule__Call__Group_1__0 )? ) ;
    public final void rule__Call__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2552:1: ( ( ( rule__Call__Group_1__0 )? ) )
            // InternalKap.g:2553:1: ( ( rule__Call__Group_1__0 )? )
            {
            // InternalKap.g:2553:1: ( ( rule__Call__Group_1__0 )? )
            // InternalKap.g:2554:2: ( rule__Call__Group_1__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getGroup_1()); 
            }
            // InternalKap.g:2555:2: ( rule__Call__Group_1__0 )?
            int alt28=2;
            alt28 = dfa28.predict(input);
            switch (alt28) {
                case 1 :
                    // InternalKap.g:2555:3: rule__Call__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Call__Group_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getGroup_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__1__Impl"


    // $ANTLR start "rule__Call__Group__2"
    // InternalKap.g:2563:1: rule__Call__Group__2 : rule__Call__Group__2__Impl ;
    public final void rule__Call__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2567:1: ( rule__Call__Group__2__Impl )
            // InternalKap.g:2568:2: rule__Call__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Call__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__2"


    // $ANTLR start "rule__Call__Group__2__Impl"
    // InternalKap.g:2574:1: rule__Call__Group__2__Impl : ( ( rule__Call__Alternatives_2 )? ) ;
    public final void rule__Call__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2578:1: ( ( ( rule__Call__Alternatives_2 )? ) )
            // InternalKap.g:2579:1: ( ( rule__Call__Alternatives_2 )? )
            {
            // InternalKap.g:2579:1: ( ( rule__Call__Alternatives_2 )? )
            // InternalKap.g:2580:2: ( rule__Call__Alternatives_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getAlternatives_2()); 
            }
            // InternalKap.g:2581:2: ( rule__Call__Alternatives_2 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==20||LA29_0==32) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalKap.g:2581:3: rule__Call__Alternatives_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Call__Alternatives_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getAlternatives_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group__2__Impl"


    // $ANTLR start "rule__Call__Group_1__0"
    // InternalKap.g:2590:1: rule__Call__Group_1__0 : rule__Call__Group_1__0__Impl rule__Call__Group_1__1 ;
    public final void rule__Call__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2594:1: ( rule__Call__Group_1__0__Impl rule__Call__Group_1__1 )
            // InternalKap.g:2595:2: rule__Call__Group_1__0__Impl rule__Call__Group_1__1
            {
            pushFollow(FOLLOW_25);
            rule__Call__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__0"


    // $ANTLR start "rule__Call__Group_1__0__Impl"
    // InternalKap.g:2602:1: rule__Call__Group_1__0__Impl : ( '(' ) ;
    public final void rule__Call__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2606:1: ( ( '(' ) )
            // InternalKap.g:2607:1: ( '(' )
            {
            // InternalKap.g:2607:1: ( '(' )
            // InternalKap.g:2608:2: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getLeftParenthesisKeyword_1_0()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getLeftParenthesisKeyword_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__0__Impl"


    // $ANTLR start "rule__Call__Group_1__1"
    // InternalKap.g:2617:1: rule__Call__Group_1__1 : rule__Call__Group_1__1__Impl rule__Call__Group_1__2 ;
    public final void rule__Call__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2621:1: ( rule__Call__Group_1__1__Impl rule__Call__Group_1__2 )
            // InternalKap.g:2622:2: rule__Call__Group_1__1__Impl rule__Call__Group_1__2
            {
            pushFollow(FOLLOW_25);
            rule__Call__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__1"


    // $ANTLR start "rule__Call__Group_1__1__Impl"
    // InternalKap.g:2629:1: rule__Call__Group_1__1__Impl : ( ( rule__Call__ParametersAssignment_1_1 )? ) ;
    public final void rule__Call__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2633:1: ( ( ( rule__Call__ParametersAssignment_1_1 )? ) )
            // InternalKap.g:2634:1: ( ( rule__Call__ParametersAssignment_1_1 )? )
            {
            // InternalKap.g:2634:1: ( ( rule__Call__ParametersAssignment_1_1 )? )
            // InternalKap.g:2635:2: ( rule__Call__ParametersAssignment_1_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getParametersAssignment_1_1()); 
            }
            // InternalKap.g:2636:2: ( rule__Call__ParametersAssignment_1_1 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( ((LA30_0>=RULE_LOWERCASE_ID && LA30_0<=RULE_EXPR)||LA30_0==RULE_INT||(LA30_0>=18 && LA30_0<=19)||LA30_0==21||LA30_0==40) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalKap.g:2636:3: rule__Call__ParametersAssignment_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Call__ParametersAssignment_1_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getParametersAssignment_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__1__Impl"


    // $ANTLR start "rule__Call__Group_1__2"
    // InternalKap.g:2644:1: rule__Call__Group_1__2 : rule__Call__Group_1__2__Impl ;
    public final void rule__Call__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2648:1: ( rule__Call__Group_1__2__Impl )
            // InternalKap.g:2649:2: rule__Call__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Call__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__2"


    // $ANTLR start "rule__Call__Group_1__2__Impl"
    // InternalKap.g:2655:1: rule__Call__Group_1__2__Impl : ( ')' ) ;
    public final void rule__Call__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2659:1: ( ( ')' ) )
            // InternalKap.g:2660:1: ( ')' )
            {
            // InternalKap.g:2660:1: ( ')' )
            // InternalKap.g:2661:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getRightParenthesisKeyword_1_2()); 
            }
            match(input,34,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getRightParenthesisKeyword_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_1__2__Impl"


    // $ANTLR start "rule__Call__Group_2_0__0"
    // InternalKap.g:2671:1: rule__Call__Group_2_0__0 : rule__Call__Group_2_0__0__Impl rule__Call__Group_2_0__1 ;
    public final void rule__Call__Group_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2675:1: ( rule__Call__Group_2_0__0__Impl rule__Call__Group_2_0__1 )
            // InternalKap.g:2676:2: rule__Call__Group_2_0__0__Impl rule__Call__Group_2_0__1
            {
            pushFollow(FOLLOW_26);
            rule__Call__Group_2_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Call__Group_2_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_2_0__0"


    // $ANTLR start "rule__Call__Group_2_0__0__Impl"
    // InternalKap.g:2683:1: rule__Call__Group_2_0__0__Impl : ( ':' ) ;
    public final void rule__Call__Group_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2687:1: ( ( ':' ) )
            // InternalKap.g:2688:1: ( ':' )
            {
            // InternalKap.g:2688:1: ( ':' )
            // InternalKap.g:2689:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getColonKeyword_2_0_0()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getColonKeyword_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_2_0__0__Impl"


    // $ANTLR start "rule__Call__Group_2_0__1"
    // InternalKap.g:2698:1: rule__Call__Group_2_0__1 : rule__Call__Group_2_0__1__Impl ;
    public final void rule__Call__Group_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2702:1: ( rule__Call__Group_2_0__1__Impl )
            // InternalKap.g:2703:2: rule__Call__Group_2_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Call__Group_2_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_2_0__1"


    // $ANTLR start "rule__Call__Group_2_0__1__Impl"
    // InternalKap.g:2709:1: rule__Call__Group_2_0__1__Impl : ( ( rule__Call__ActionsAssignment_2_0_1 ) ) ;
    public final void rule__Call__Group_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2713:1: ( ( ( rule__Call__ActionsAssignment_2_0_1 ) ) )
            // InternalKap.g:2714:1: ( ( rule__Call__ActionsAssignment_2_0_1 ) )
            {
            // InternalKap.g:2714:1: ( ( rule__Call__ActionsAssignment_2_0_1 ) )
            // InternalKap.g:2715:2: ( rule__Call__ActionsAssignment_2_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getActionsAssignment_2_0_1()); 
            }
            // InternalKap.g:2716:2: ( rule__Call__ActionsAssignment_2_0_1 )
            // InternalKap.g:2716:3: rule__Call__ActionsAssignment_2_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Call__ActionsAssignment_2_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getActionsAssignment_2_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__Group_2_0__1__Impl"


    // $ANTLR start "rule__Actions__Group_3__0"
    // InternalKap.g:2725:1: rule__Actions__Group_3__0 : rule__Actions__Group_3__0__Impl rule__Actions__Group_3__1 ;
    public final void rule__Actions__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2729:1: ( rule__Actions__Group_3__0__Impl rule__Actions__Group_3__1 )
            // InternalKap.g:2730:2: rule__Actions__Group_3__0__Impl rule__Actions__Group_3__1
            {
            pushFollow(FOLLOW_27);
            rule__Actions__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Actions__Group_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_3__0"


    // $ANTLR start "rule__Actions__Group_3__0__Impl"
    // InternalKap.g:2737:1: rule__Actions__Group_3__0__Impl : ( '(' ) ;
    public final void rule__Actions__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2741:1: ( ( '(' ) )
            // InternalKap.g:2742:1: ( '(' )
            {
            // InternalKap.g:2742:1: ( '(' )
            // InternalKap.g:2743:2: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getLeftParenthesisKeyword_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_3__0__Impl"


    // $ANTLR start "rule__Actions__Group_3__1"
    // InternalKap.g:2752:1: rule__Actions__Group_3__1 : rule__Actions__Group_3__1__Impl rule__Actions__Group_3__2 ;
    public final void rule__Actions__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2756:1: ( rule__Actions__Group_3__1__Impl rule__Actions__Group_3__2 )
            // InternalKap.g:2757:2: rule__Actions__Group_3__1__Impl rule__Actions__Group_3__2
            {
            pushFollow(FOLLOW_28);
            rule__Actions__Group_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Actions__Group_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_3__1"


    // $ANTLR start "rule__Actions__Group_3__1__Impl"
    // InternalKap.g:2764:1: rule__Actions__Group_3__1__Impl : ( ( rule__Actions__MatchesAssignment_3_1 ) ) ;
    public final void rule__Actions__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2768:1: ( ( ( rule__Actions__MatchesAssignment_3_1 ) ) )
            // InternalKap.g:2769:1: ( ( rule__Actions__MatchesAssignment_3_1 ) )
            {
            // InternalKap.g:2769:1: ( ( rule__Actions__MatchesAssignment_3_1 ) )
            // InternalKap.g:2770:2: ( rule__Actions__MatchesAssignment_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchesAssignment_3_1()); 
            }
            // InternalKap.g:2771:2: ( rule__Actions__MatchesAssignment_3_1 )
            // InternalKap.g:2771:3: rule__Actions__MatchesAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Actions__MatchesAssignment_3_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchesAssignment_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_3__1__Impl"


    // $ANTLR start "rule__Actions__Group_3__2"
    // InternalKap.g:2779:1: rule__Actions__Group_3__2 : rule__Actions__Group_3__2__Impl rule__Actions__Group_3__3 ;
    public final void rule__Actions__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2783:1: ( rule__Actions__Group_3__2__Impl rule__Actions__Group_3__3 )
            // InternalKap.g:2784:2: rule__Actions__Group_3__2__Impl rule__Actions__Group_3__3
            {
            pushFollow(FOLLOW_28);
            rule__Actions__Group_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Actions__Group_3__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_3__2"


    // $ANTLR start "rule__Actions__Group_3__2__Impl"
    // InternalKap.g:2791:1: rule__Actions__Group_3__2__Impl : ( ( rule__Actions__MatchesAssignment_3_2 )* ) ;
    public final void rule__Actions__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2795:1: ( ( ( rule__Actions__MatchesAssignment_3_2 )* ) )
            // InternalKap.g:2796:1: ( ( rule__Actions__MatchesAssignment_3_2 )* )
            {
            // InternalKap.g:2796:1: ( ( rule__Actions__MatchesAssignment_3_2 )* )
            // InternalKap.g:2797:2: ( rule__Actions__MatchesAssignment_3_2 )*
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchesAssignment_3_2()); 
            }
            // InternalKap.g:2798:2: ( rule__Actions__MatchesAssignment_3_2 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( ((LA31_0>=RULE_LOWERCASE_ID && LA31_0<=RULE_STRING)||LA31_0==RULE_OBSERVABLE||(LA31_0>=RULE_REGEXP && LA31_0<=RULE_INT)||(LA31_0>=18 && LA31_0<=19)||LA31_0==21||LA31_0==33||LA31_0==40) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalKap.g:2798:3: rule__Actions__MatchesAssignment_3_2
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__Actions__MatchesAssignment_3_2();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchesAssignment_3_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_3__2__Impl"


    // $ANTLR start "rule__Actions__Group_3__3"
    // InternalKap.g:2806:1: rule__Actions__Group_3__3 : rule__Actions__Group_3__3__Impl ;
    public final void rule__Actions__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2810:1: ( rule__Actions__Group_3__3__Impl )
            // InternalKap.g:2811:2: rule__Actions__Group_3__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Actions__Group_3__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_3__3"


    // $ANTLR start "rule__Actions__Group_3__3__Impl"
    // InternalKap.g:2817:1: rule__Actions__Group_3__3__Impl : ( ')' ) ;
    public final void rule__Actions__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2821:1: ( ( ')' ) )
            // InternalKap.g:2822:1: ( ')' )
            {
            // InternalKap.g:2822:1: ( ')' )
            // InternalKap.g:2823:2: ')'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getRightParenthesisKeyword_3_3()); 
            }
            match(input,34,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getRightParenthesisKeyword_3_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__Group_3__3__Impl"


    // $ANTLR start "rule__Match__Group_0__0"
    // InternalKap.g:2833:1: rule__Match__Group_0__0 : rule__Match__Group_0__0__Impl rule__Match__Group_0__1 ;
    public final void rule__Match__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2837:1: ( rule__Match__Group_0__0__Impl rule__Match__Group_0__1 )
            // InternalKap.g:2838:2: rule__Match__Group_0__0__Impl rule__Match__Group_0__1
            {
            pushFollow(FOLLOW_30);
            rule__Match__Group_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__0"


    // $ANTLR start "rule__Match__Group_0__0__Impl"
    // InternalKap.g:2845:1: rule__Match__Group_0__0__Impl : ( ( rule__Match__IdAssignment_0_0 ) ) ;
    public final void rule__Match__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2849:1: ( ( ( rule__Match__IdAssignment_0_0 ) ) )
            // InternalKap.g:2850:1: ( ( rule__Match__IdAssignment_0_0 ) )
            {
            // InternalKap.g:2850:1: ( ( rule__Match__IdAssignment_0_0 ) )
            // InternalKap.g:2851:2: ( rule__Match__IdAssignment_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getIdAssignment_0_0()); 
            }
            // InternalKap.g:2852:2: ( rule__Match__IdAssignment_0_0 )
            // InternalKap.g:2852:3: rule__Match__IdAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__IdAssignment_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getIdAssignment_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__0__Impl"


    // $ANTLR start "rule__Match__Group_0__1"
    // InternalKap.g:2860:1: rule__Match__Group_0__1 : rule__Match__Group_0__1__Impl rule__Match__Group_0__2 ;
    public final void rule__Match__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2864:1: ( rule__Match__Group_0__1__Impl rule__Match__Group_0__2 )
            // InternalKap.g:2865:2: rule__Match__Group_0__1__Impl rule__Match__Group_0__2
            {
            pushFollow(FOLLOW_9);
            rule__Match__Group_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__1"


    // $ANTLR start "rule__Match__Group_0__1__Impl"
    // InternalKap.g:2872:1: rule__Match__Group_0__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2876:1: ( ( '->' ) )
            // InternalKap.g:2877:1: ( '->' )
            {
            // InternalKap.g:2877:1: ( '->' )
            // InternalKap.g:2878:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__1__Impl"


    // $ANTLR start "rule__Match__Group_0__2"
    // InternalKap.g:2887:1: rule__Match__Group_0__2 : rule__Match__Group_0__2__Impl ;
    public final void rule__Match__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2891:1: ( rule__Match__Group_0__2__Impl )
            // InternalKap.g:2892:2: rule__Match__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__2"


    // $ANTLR start "rule__Match__Group_0__2__Impl"
    // InternalKap.g:2898:1: rule__Match__Group_0__2__Impl : ( ( rule__Match__BodyAssignment_0_2 ) ) ;
    public final void rule__Match__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2902:1: ( ( ( rule__Match__BodyAssignment_0_2 ) ) )
            // InternalKap.g:2903:1: ( ( rule__Match__BodyAssignment_0_2 ) )
            {
            // InternalKap.g:2903:1: ( ( rule__Match__BodyAssignment_0_2 ) )
            // InternalKap.g:2904:2: ( rule__Match__BodyAssignment_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_0_2()); 
            }
            // InternalKap.g:2905:2: ( rule__Match__BodyAssignment_0_2 )
            // InternalKap.g:2905:3: rule__Match__BodyAssignment_0_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_0__2__Impl"


    // $ANTLR start "rule__Match__Group_1__0"
    // InternalKap.g:2914:1: rule__Match__Group_1__0 : rule__Match__Group_1__0__Impl rule__Match__Group_1__1 ;
    public final void rule__Match__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2918:1: ( rule__Match__Group_1__0__Impl rule__Match__Group_1__1 )
            // InternalKap.g:2919:2: rule__Match__Group_1__0__Impl rule__Match__Group_1__1
            {
            pushFollow(FOLLOW_30);
            rule__Match__Group_1__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_1__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__0"


    // $ANTLR start "rule__Match__Group_1__0__Impl"
    // InternalKap.g:2926:1: rule__Match__Group_1__0__Impl : ( ( rule__Match__RegexpAssignment_1_0 ) ) ;
    public final void rule__Match__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2930:1: ( ( ( rule__Match__RegexpAssignment_1_0 ) ) )
            // InternalKap.g:2931:1: ( ( rule__Match__RegexpAssignment_1_0 ) )
            {
            // InternalKap.g:2931:1: ( ( rule__Match__RegexpAssignment_1_0 ) )
            // InternalKap.g:2932:2: ( rule__Match__RegexpAssignment_1_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getRegexpAssignment_1_0()); 
            }
            // InternalKap.g:2933:2: ( rule__Match__RegexpAssignment_1_0 )
            // InternalKap.g:2933:3: rule__Match__RegexpAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__RegexpAssignment_1_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getRegexpAssignment_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__0__Impl"


    // $ANTLR start "rule__Match__Group_1__1"
    // InternalKap.g:2941:1: rule__Match__Group_1__1 : rule__Match__Group_1__1__Impl rule__Match__Group_1__2 ;
    public final void rule__Match__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2945:1: ( rule__Match__Group_1__1__Impl rule__Match__Group_1__2 )
            // InternalKap.g:2946:2: rule__Match__Group_1__1__Impl rule__Match__Group_1__2
            {
            pushFollow(FOLLOW_9);
            rule__Match__Group_1__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_1__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__1"


    // $ANTLR start "rule__Match__Group_1__1__Impl"
    // InternalKap.g:2953:1: rule__Match__Group_1__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2957:1: ( ( '->' ) )
            // InternalKap.g:2958:1: ( '->' )
            {
            // InternalKap.g:2958:1: ( '->' )
            // InternalKap.g:2959:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_1_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__1__Impl"


    // $ANTLR start "rule__Match__Group_1__2"
    // InternalKap.g:2968:1: rule__Match__Group_1__2 : rule__Match__Group_1__2__Impl ;
    public final void rule__Match__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2972:1: ( rule__Match__Group_1__2__Impl )
            // InternalKap.g:2973:2: rule__Match__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_1__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__2"


    // $ANTLR start "rule__Match__Group_1__2__Impl"
    // InternalKap.g:2979:1: rule__Match__Group_1__2__Impl : ( ( rule__Match__BodyAssignment_1_2 ) ) ;
    public final void rule__Match__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2983:1: ( ( ( rule__Match__BodyAssignment_1_2 ) ) )
            // InternalKap.g:2984:1: ( ( rule__Match__BodyAssignment_1_2 ) )
            {
            // InternalKap.g:2984:1: ( ( rule__Match__BodyAssignment_1_2 ) )
            // InternalKap.g:2985:2: ( rule__Match__BodyAssignment_1_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_1_2()); 
            }
            // InternalKap.g:2986:2: ( rule__Match__BodyAssignment_1_2 )
            // InternalKap.g:2986:3: rule__Match__BodyAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_1_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_1_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_1__2__Impl"


    // $ANTLR start "rule__Match__Group_2__0"
    // InternalKap.g:2995:1: rule__Match__Group_2__0 : rule__Match__Group_2__0__Impl rule__Match__Group_2__1 ;
    public final void rule__Match__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:2999:1: ( rule__Match__Group_2__0__Impl rule__Match__Group_2__1 )
            // InternalKap.g:3000:2: rule__Match__Group_2__0__Impl rule__Match__Group_2__1
            {
            pushFollow(FOLLOW_30);
            rule__Match__Group_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__0"


    // $ANTLR start "rule__Match__Group_2__0__Impl"
    // InternalKap.g:3007:1: rule__Match__Group_2__0__Impl : ( ( rule__Match__ObservableAssignment_2_0 ) ) ;
    public final void rule__Match__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3011:1: ( ( ( rule__Match__ObservableAssignment_2_0 ) ) )
            // InternalKap.g:3012:1: ( ( rule__Match__ObservableAssignment_2_0 ) )
            {
            // InternalKap.g:3012:1: ( ( rule__Match__ObservableAssignment_2_0 ) )
            // InternalKap.g:3013:2: ( rule__Match__ObservableAssignment_2_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getObservableAssignment_2_0()); 
            }
            // InternalKap.g:3014:2: ( rule__Match__ObservableAssignment_2_0 )
            // InternalKap.g:3014:3: rule__Match__ObservableAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__ObservableAssignment_2_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getObservableAssignment_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__0__Impl"


    // $ANTLR start "rule__Match__Group_2__1"
    // InternalKap.g:3022:1: rule__Match__Group_2__1 : rule__Match__Group_2__1__Impl rule__Match__Group_2__2 ;
    public final void rule__Match__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3026:1: ( rule__Match__Group_2__1__Impl rule__Match__Group_2__2 )
            // InternalKap.g:3027:2: rule__Match__Group_2__1__Impl rule__Match__Group_2__2
            {
            pushFollow(FOLLOW_9);
            rule__Match__Group_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_2__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__1"


    // $ANTLR start "rule__Match__Group_2__1__Impl"
    // InternalKap.g:3034:1: rule__Match__Group_2__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3038:1: ( ( '->' ) )
            // InternalKap.g:3039:1: ( '->' )
            {
            // InternalKap.g:3039:1: ( '->' )
            // InternalKap.g:3040:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__1__Impl"


    // $ANTLR start "rule__Match__Group_2__2"
    // InternalKap.g:3049:1: rule__Match__Group_2__2 : rule__Match__Group_2__2__Impl ;
    public final void rule__Match__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3053:1: ( rule__Match__Group_2__2__Impl )
            // InternalKap.g:3054:2: rule__Match__Group_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_2__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__2"


    // $ANTLR start "rule__Match__Group_2__2__Impl"
    // InternalKap.g:3060:1: rule__Match__Group_2__2__Impl : ( ( rule__Match__BodyAssignment_2_2 ) ) ;
    public final void rule__Match__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3064:1: ( ( ( rule__Match__BodyAssignment_2_2 ) ) )
            // InternalKap.g:3065:1: ( ( rule__Match__BodyAssignment_2_2 ) )
            {
            // InternalKap.g:3065:1: ( ( rule__Match__BodyAssignment_2_2 ) )
            // InternalKap.g:3066:2: ( rule__Match__BodyAssignment_2_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_2_2()); 
            }
            // InternalKap.g:3067:2: ( rule__Match__BodyAssignment_2_2 )
            // InternalKap.g:3067:3: rule__Match__BodyAssignment_2_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_2_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_2_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_2__2__Impl"


    // $ANTLR start "rule__Match__Group_3__0"
    // InternalKap.g:3076:1: rule__Match__Group_3__0 : rule__Match__Group_3__0__Impl rule__Match__Group_3__1 ;
    public final void rule__Match__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3080:1: ( rule__Match__Group_3__0__Impl rule__Match__Group_3__1 )
            // InternalKap.g:3081:2: rule__Match__Group_3__0__Impl rule__Match__Group_3__1
            {
            pushFollow(FOLLOW_30);
            rule__Match__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__0"


    // $ANTLR start "rule__Match__Group_3__0__Impl"
    // InternalKap.g:3088:1: rule__Match__Group_3__0__Impl : ( ( rule__Match__LiteralAssignment_3_0 ) ) ;
    public final void rule__Match__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3092:1: ( ( ( rule__Match__LiteralAssignment_3_0 ) ) )
            // InternalKap.g:3093:1: ( ( rule__Match__LiteralAssignment_3_0 ) )
            {
            // InternalKap.g:3093:1: ( ( rule__Match__LiteralAssignment_3_0 ) )
            // InternalKap.g:3094:2: ( rule__Match__LiteralAssignment_3_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getLiteralAssignment_3_0()); 
            }
            // InternalKap.g:3095:2: ( rule__Match__LiteralAssignment_3_0 )
            // InternalKap.g:3095:3: rule__Match__LiteralAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__LiteralAssignment_3_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getLiteralAssignment_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__0__Impl"


    // $ANTLR start "rule__Match__Group_3__1"
    // InternalKap.g:3103:1: rule__Match__Group_3__1 : rule__Match__Group_3__1__Impl rule__Match__Group_3__2 ;
    public final void rule__Match__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3107:1: ( rule__Match__Group_3__1__Impl rule__Match__Group_3__2 )
            // InternalKap.g:3108:2: rule__Match__Group_3__1__Impl rule__Match__Group_3__2
            {
            pushFollow(FOLLOW_9);
            rule__Match__Group_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__1"


    // $ANTLR start "rule__Match__Group_3__1__Impl"
    // InternalKap.g:3115:1: rule__Match__Group_3__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3119:1: ( ( '->' ) )
            // InternalKap.g:3120:1: ( '->' )
            {
            // InternalKap.g:3120:1: ( '->' )
            // InternalKap.g:3121:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__1__Impl"


    // $ANTLR start "rule__Match__Group_3__2"
    // InternalKap.g:3130:1: rule__Match__Group_3__2 : rule__Match__Group_3__2__Impl ;
    public final void rule__Match__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3134:1: ( rule__Match__Group_3__2__Impl )
            // InternalKap.g:3135:2: rule__Match__Group_3__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__2"


    // $ANTLR start "rule__Match__Group_3__2__Impl"
    // InternalKap.g:3141:1: rule__Match__Group_3__2__Impl : ( ( rule__Match__BodyAssignment_3_2 ) ) ;
    public final void rule__Match__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3145:1: ( ( ( rule__Match__BodyAssignment_3_2 ) ) )
            // InternalKap.g:3146:1: ( ( rule__Match__BodyAssignment_3_2 ) )
            {
            // InternalKap.g:3146:1: ( ( rule__Match__BodyAssignment_3_2 ) )
            // InternalKap.g:3147:2: ( rule__Match__BodyAssignment_3_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_3_2()); 
            }
            // InternalKap.g:3148:2: ( rule__Match__BodyAssignment_3_2 )
            // InternalKap.g:3148:3: rule__Match__BodyAssignment_3_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_3_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_3_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_3__2__Impl"


    // $ANTLR start "rule__Match__Group_4__0"
    // InternalKap.g:3157:1: rule__Match__Group_4__0 : rule__Match__Group_4__0__Impl rule__Match__Group_4__1 ;
    public final void rule__Match__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3161:1: ( rule__Match__Group_4__0__Impl rule__Match__Group_4__1 )
            // InternalKap.g:3162:2: rule__Match__Group_4__0__Impl rule__Match__Group_4__1
            {
            pushFollow(FOLLOW_30);
            rule__Match__Group_4__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_4__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__0"


    // $ANTLR start "rule__Match__Group_4__0__Impl"
    // InternalKap.g:3169:1: rule__Match__Group_4__0__Impl : ( ( rule__Match__TextAssignment_4_0 ) ) ;
    public final void rule__Match__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3173:1: ( ( ( rule__Match__TextAssignment_4_0 ) ) )
            // InternalKap.g:3174:1: ( ( rule__Match__TextAssignment_4_0 ) )
            {
            // InternalKap.g:3174:1: ( ( rule__Match__TextAssignment_4_0 ) )
            // InternalKap.g:3175:2: ( rule__Match__TextAssignment_4_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getTextAssignment_4_0()); 
            }
            // InternalKap.g:3176:2: ( rule__Match__TextAssignment_4_0 )
            // InternalKap.g:3176:3: rule__Match__TextAssignment_4_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__TextAssignment_4_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getTextAssignment_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__0__Impl"


    // $ANTLR start "rule__Match__Group_4__1"
    // InternalKap.g:3184:1: rule__Match__Group_4__1 : rule__Match__Group_4__1__Impl rule__Match__Group_4__2 ;
    public final void rule__Match__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3188:1: ( rule__Match__Group_4__1__Impl rule__Match__Group_4__2 )
            // InternalKap.g:3189:2: rule__Match__Group_4__1__Impl rule__Match__Group_4__2
            {
            pushFollow(FOLLOW_9);
            rule__Match__Group_4__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_4__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__1"


    // $ANTLR start "rule__Match__Group_4__1__Impl"
    // InternalKap.g:3196:1: rule__Match__Group_4__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3200:1: ( ( '->' ) )
            // InternalKap.g:3201:1: ( '->' )
            {
            // InternalKap.g:3201:1: ( '->' )
            // InternalKap.g:3202:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_4_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__1__Impl"


    // $ANTLR start "rule__Match__Group_4__2"
    // InternalKap.g:3211:1: rule__Match__Group_4__2 : rule__Match__Group_4__2__Impl ;
    public final void rule__Match__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3215:1: ( rule__Match__Group_4__2__Impl )
            // InternalKap.g:3216:2: rule__Match__Group_4__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_4__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__2"


    // $ANTLR start "rule__Match__Group_4__2__Impl"
    // InternalKap.g:3222:1: rule__Match__Group_4__2__Impl : ( ( rule__Match__BodyAssignment_4_2 ) ) ;
    public final void rule__Match__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3226:1: ( ( ( rule__Match__BodyAssignment_4_2 ) ) )
            // InternalKap.g:3227:1: ( ( rule__Match__BodyAssignment_4_2 ) )
            {
            // InternalKap.g:3227:1: ( ( rule__Match__BodyAssignment_4_2 ) )
            // InternalKap.g:3228:2: ( rule__Match__BodyAssignment_4_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_4_2()); 
            }
            // InternalKap.g:3229:2: ( rule__Match__BodyAssignment_4_2 )
            // InternalKap.g:3229:3: rule__Match__BodyAssignment_4_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_4_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_4_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_4__2__Impl"


    // $ANTLR start "rule__Match__Group_5__0"
    // InternalKap.g:3238:1: rule__Match__Group_5__0 : rule__Match__Group_5__0__Impl rule__Match__Group_5__1 ;
    public final void rule__Match__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3242:1: ( rule__Match__Group_5__0__Impl rule__Match__Group_5__1 )
            // InternalKap.g:3243:2: rule__Match__Group_5__0__Impl rule__Match__Group_5__1
            {
            pushFollow(FOLLOW_30);
            rule__Match__Group_5__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_5__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__0"


    // $ANTLR start "rule__Match__Group_5__0__Impl"
    // InternalKap.g:3250:1: rule__Match__Group_5__0__Impl : ( ( rule__Match__ArgumentsAssignment_5_0 ) ) ;
    public final void rule__Match__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3254:1: ( ( ( rule__Match__ArgumentsAssignment_5_0 ) ) )
            // InternalKap.g:3255:1: ( ( rule__Match__ArgumentsAssignment_5_0 ) )
            {
            // InternalKap.g:3255:1: ( ( rule__Match__ArgumentsAssignment_5_0 ) )
            // InternalKap.g:3256:2: ( rule__Match__ArgumentsAssignment_5_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getArgumentsAssignment_5_0()); 
            }
            // InternalKap.g:3257:2: ( rule__Match__ArgumentsAssignment_5_0 )
            // InternalKap.g:3257:3: rule__Match__ArgumentsAssignment_5_0
            {
            pushFollow(FOLLOW_2);
            rule__Match__ArgumentsAssignment_5_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getArgumentsAssignment_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__0__Impl"


    // $ANTLR start "rule__Match__Group_5__1"
    // InternalKap.g:3265:1: rule__Match__Group_5__1 : rule__Match__Group_5__1__Impl rule__Match__Group_5__2 ;
    public final void rule__Match__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3269:1: ( rule__Match__Group_5__1__Impl rule__Match__Group_5__2 )
            // InternalKap.g:3270:2: rule__Match__Group_5__1__Impl rule__Match__Group_5__2
            {
            pushFollow(FOLLOW_9);
            rule__Match__Group_5__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Match__Group_5__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__1"


    // $ANTLR start "rule__Match__Group_5__1__Impl"
    // InternalKap.g:3277:1: rule__Match__Group_5__1__Impl : ( '->' ) ;
    public final void rule__Match__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3281:1: ( ( '->' ) )
            // InternalKap.g:3282:1: ( '->' )
            {
            // InternalKap.g:3282:1: ( '->' )
            // InternalKap.g:3283:2: '->'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1()); 
            }
            match(input,39,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getHyphenMinusGreaterThanSignKeyword_5_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__1__Impl"


    // $ANTLR start "rule__Match__Group_5__2"
    // InternalKap.g:3292:1: rule__Match__Group_5__2 : rule__Match__Group_5__2__Impl ;
    public final void rule__Match__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3296:1: ( rule__Match__Group_5__2__Impl )
            // InternalKap.g:3297:2: rule__Match__Group_5__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Match__Group_5__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__2"


    // $ANTLR start "rule__Match__Group_5__2__Impl"
    // InternalKap.g:3303:1: rule__Match__Group_5__2__Impl : ( ( rule__Match__BodyAssignment_5_2 ) ) ;
    public final void rule__Match__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3307:1: ( ( ( rule__Match__BodyAssignment_5_2 ) ) )
            // InternalKap.g:3308:1: ( ( rule__Match__BodyAssignment_5_2 ) )
            {
            // InternalKap.g:3308:1: ( ( rule__Match__BodyAssignment_5_2 ) )
            // InternalKap.g:3309:2: ( rule__Match__BodyAssignment_5_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyAssignment_5_2()); 
            }
            // InternalKap.g:3310:2: ( rule__Match__BodyAssignment_5_2 )
            // InternalKap.g:3310:3: rule__Match__BodyAssignment_5_2
            {
            pushFollow(FOLLOW_2);
            rule__Match__BodyAssignment_5_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyAssignment_5_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__Group_5__2__Impl"


    // $ANTLR start "rule__Number__Group__0"
    // InternalKap.g:3319:1: rule__Number__Group__0 : rule__Number__Group__0__Impl rule__Number__Group__1 ;
    public final void rule__Number__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3323:1: ( rule__Number__Group__0__Impl rule__Number__Group__1 )
            // InternalKap.g:3324:2: rule__Number__Group__0__Impl rule__Number__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__Number__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__0"


    // $ANTLR start "rule__Number__Group__0__Impl"
    // InternalKap.g:3331:1: rule__Number__Group__0__Impl : ( ( rule__Number__Alternatives_0 )? ) ;
    public final void rule__Number__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3335:1: ( ( ( rule__Number__Alternatives_0 )? ) )
            // InternalKap.g:3336:1: ( ( rule__Number__Alternatives_0 )? )
            {
            // InternalKap.g:3336:1: ( ( rule__Number__Alternatives_0 )? )
            // InternalKap.g:3337:2: ( rule__Number__Alternatives_0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getAlternatives_0()); 
            }
            // InternalKap.g:3338:2: ( rule__Number__Alternatives_0 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==21||LA32_0==40) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalKap.g:3338:3: rule__Number__Alternatives_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__Alternatives_0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getAlternatives_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__0__Impl"


    // $ANTLR start "rule__Number__Group__1"
    // InternalKap.g:3346:1: rule__Number__Group__1 : rule__Number__Group__1__Impl rule__Number__Group__2 ;
    public final void rule__Number__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3350:1: ( rule__Number__Group__1__Impl rule__Number__Group__2 )
            // InternalKap.g:3351:2: rule__Number__Group__1__Impl rule__Number__Group__2
            {
            pushFollow(FOLLOW_31);
            rule__Number__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__1"


    // $ANTLR start "rule__Number__Group__1__Impl"
    // InternalKap.g:3358:1: rule__Number__Group__1__Impl : ( ( rule__Number__RealAssignment_1 ) ) ;
    public final void rule__Number__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3362:1: ( ( ( rule__Number__RealAssignment_1 ) ) )
            // InternalKap.g:3363:1: ( ( rule__Number__RealAssignment_1 ) )
            {
            // InternalKap.g:3363:1: ( ( rule__Number__RealAssignment_1 ) )
            // InternalKap.g:3364:2: ( rule__Number__RealAssignment_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getRealAssignment_1()); 
            }
            // InternalKap.g:3365:2: ( rule__Number__RealAssignment_1 )
            // InternalKap.g:3365:3: rule__Number__RealAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Number__RealAssignment_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getRealAssignment_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__1__Impl"


    // $ANTLR start "rule__Number__Group__2"
    // InternalKap.g:3373:1: rule__Number__Group__2 : rule__Number__Group__2__Impl rule__Number__Group__3 ;
    public final void rule__Number__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3377:1: ( rule__Number__Group__2__Impl rule__Number__Group__3 )
            // InternalKap.g:3378:2: rule__Number__Group__2__Impl rule__Number__Group__3
            {
            pushFollow(FOLLOW_31);
            rule__Number__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__2"


    // $ANTLR start "rule__Number__Group__2__Impl"
    // InternalKap.g:3385:1: rule__Number__Group__2__Impl : ( ( rule__Number__LongAssignment_2 )? ) ;
    public final void rule__Number__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3389:1: ( ( ( rule__Number__LongAssignment_2 )? ) )
            // InternalKap.g:3390:1: ( ( rule__Number__LongAssignment_2 )? )
            {
            // InternalKap.g:3390:1: ( ( rule__Number__LongAssignment_2 )? )
            // InternalKap.g:3391:2: ( rule__Number__LongAssignment_2 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getLongAssignment_2()); 
            }
            // InternalKap.g:3392:2: ( rule__Number__LongAssignment_2 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==43) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalKap.g:3392:3: rule__Number__LongAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__LongAssignment_2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getLongAssignment_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__2__Impl"


    // $ANTLR start "rule__Number__Group__3"
    // InternalKap.g:3400:1: rule__Number__Group__3 : rule__Number__Group__3__Impl rule__Number__Group__4 ;
    public final void rule__Number__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3404:1: ( rule__Number__Group__3__Impl rule__Number__Group__4 )
            // InternalKap.g:3405:2: rule__Number__Group__3__Impl rule__Number__Group__4
            {
            pushFollow(FOLLOW_31);
            rule__Number__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__3"


    // $ANTLR start "rule__Number__Group__3__Impl"
    // InternalKap.g:3412:1: rule__Number__Group__3__Impl : ( ( rule__Number__Group_3__0 )? ) ;
    public final void rule__Number__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3416:1: ( ( ( rule__Number__Group_3__0 )? ) )
            // InternalKap.g:3417:1: ( ( rule__Number__Group_3__0 )? )
            {
            // InternalKap.g:3417:1: ( ( rule__Number__Group_3__0 )? )
            // InternalKap.g:3418:2: ( rule__Number__Group_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup_3()); 
            }
            // InternalKap.g:3419:2: ( rule__Number__Group_3__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==41) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalKap.g:3419:3: rule__Number__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__Group_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__3__Impl"


    // $ANTLR start "rule__Number__Group__4"
    // InternalKap.g:3427:1: rule__Number__Group__4 : rule__Number__Group__4__Impl ;
    public final void rule__Number__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3431:1: ( rule__Number__Group__4__Impl )
            // InternalKap.g:3432:2: rule__Number__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__4"


    // $ANTLR start "rule__Number__Group__4__Impl"
    // InternalKap.g:3438:1: rule__Number__Group__4__Impl : ( ( rule__Number__Group_4__0 )? ) ;
    public final void rule__Number__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3442:1: ( ( ( rule__Number__Group_4__0 )? ) )
            // InternalKap.g:3443:1: ( ( rule__Number__Group_4__0 )? )
            {
            // InternalKap.g:3443:1: ( ( rule__Number__Group_4__0 )? )
            // InternalKap.g:3444:2: ( rule__Number__Group_4__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup_4()); 
            }
            // InternalKap.g:3445:2: ( rule__Number__Group_4__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( ((LA35_0>=22 && LA35_0<=23)) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalKap.g:3445:3: rule__Number__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__Group_4__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group__4__Impl"


    // $ANTLR start "rule__Number__Group_3__0"
    // InternalKap.g:3454:1: rule__Number__Group_3__0 : rule__Number__Group_3__0__Impl ;
    public final void rule__Number__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3458:1: ( rule__Number__Group_3__0__Impl )
            // InternalKap.g:3459:2: rule__Number__Group_3__0__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3__0"


    // $ANTLR start "rule__Number__Group_3__0__Impl"
    // InternalKap.g:3465:1: rule__Number__Group_3__0__Impl : ( ( rule__Number__Group_3_0__0 ) ) ;
    public final void rule__Number__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3469:1: ( ( ( rule__Number__Group_3_0__0 ) ) )
            // InternalKap.g:3470:1: ( ( rule__Number__Group_3_0__0 ) )
            {
            // InternalKap.g:3470:1: ( ( rule__Number__Group_3_0__0 ) )
            // InternalKap.g:3471:2: ( rule__Number__Group_3_0__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup_3_0()); 
            }
            // InternalKap.g:3472:2: ( rule__Number__Group_3_0__0 )
            // InternalKap.g:3472:3: rule__Number__Group_3_0__0
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_3_0__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3__0__Impl"


    // $ANTLR start "rule__Number__Group_3_0__0"
    // InternalKap.g:3481:1: rule__Number__Group_3_0__0 : rule__Number__Group_3_0__0__Impl rule__Number__Group_3_0__1 ;
    public final void rule__Number__Group_3_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3485:1: ( rule__Number__Group_3_0__0__Impl rule__Number__Group_3_0__1 )
            // InternalKap.g:3486:2: rule__Number__Group_3_0__0__Impl rule__Number__Group_3_0__1
            {
            pushFollow(FOLLOW_32);
            rule__Number__Group_3_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group_3_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3_0__0"


    // $ANTLR start "rule__Number__Group_3_0__0__Impl"
    // InternalKap.g:3493:1: rule__Number__Group_3_0__0__Impl : ( ( rule__Number__DecimalAssignment_3_0_0 ) ) ;
    public final void rule__Number__Group_3_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3497:1: ( ( ( rule__Number__DecimalAssignment_3_0_0 ) ) )
            // InternalKap.g:3498:1: ( ( rule__Number__DecimalAssignment_3_0_0 ) )
            {
            // InternalKap.g:3498:1: ( ( rule__Number__DecimalAssignment_3_0_0 ) )
            // InternalKap.g:3499:2: ( rule__Number__DecimalAssignment_3_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalAssignment_3_0_0()); 
            }
            // InternalKap.g:3500:2: ( rule__Number__DecimalAssignment_3_0_0 )
            // InternalKap.g:3500:3: rule__Number__DecimalAssignment_3_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Number__DecimalAssignment_3_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalAssignment_3_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3_0__0__Impl"


    // $ANTLR start "rule__Number__Group_3_0__1"
    // InternalKap.g:3508:1: rule__Number__Group_3_0__1 : rule__Number__Group_3_0__1__Impl ;
    public final void rule__Number__Group_3_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3512:1: ( rule__Number__Group_3_0__1__Impl )
            // InternalKap.g:3513:2: rule__Number__Group_3_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_3_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3_0__1"


    // $ANTLR start "rule__Number__Group_3_0__1__Impl"
    // InternalKap.g:3519:1: rule__Number__Group_3_0__1__Impl : ( ( rule__Number__DecimalPartAssignment_3_0_1 ) ) ;
    public final void rule__Number__Group_3_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3523:1: ( ( ( rule__Number__DecimalPartAssignment_3_0_1 ) ) )
            // InternalKap.g:3524:1: ( ( rule__Number__DecimalPartAssignment_3_0_1 ) )
            {
            // InternalKap.g:3524:1: ( ( rule__Number__DecimalPartAssignment_3_0_1 ) )
            // InternalKap.g:3525:2: ( rule__Number__DecimalPartAssignment_3_0_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalPartAssignment_3_0_1()); 
            }
            // InternalKap.g:3526:2: ( rule__Number__DecimalPartAssignment_3_0_1 )
            // InternalKap.g:3526:3: rule__Number__DecimalPartAssignment_3_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Number__DecimalPartAssignment_3_0_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalPartAssignment_3_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_3_0__1__Impl"


    // $ANTLR start "rule__Number__Group_4__0"
    // InternalKap.g:3535:1: rule__Number__Group_4__0 : rule__Number__Group_4__0__Impl ;
    public final void rule__Number__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3539:1: ( rule__Number__Group_4__0__Impl )
            // InternalKap.g:3540:2: rule__Number__Group_4__0__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_4__0__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4__0"


    // $ANTLR start "rule__Number__Group_4__0__Impl"
    // InternalKap.g:3546:1: rule__Number__Group_4__0__Impl : ( ( rule__Number__Group_4_0__0 ) ) ;
    public final void rule__Number__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3550:1: ( ( ( rule__Number__Group_4_0__0 ) ) )
            // InternalKap.g:3551:1: ( ( rule__Number__Group_4_0__0 ) )
            {
            // InternalKap.g:3551:1: ( ( rule__Number__Group_4_0__0 ) )
            // InternalKap.g:3552:2: ( rule__Number__Group_4_0__0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getGroup_4_0()); 
            }
            // InternalKap.g:3553:2: ( rule__Number__Group_4_0__0 )
            // InternalKap.g:3553:3: rule__Number__Group_4_0__0
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_4_0__0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getGroup_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4__0__Impl"


    // $ANTLR start "rule__Number__Group_4_0__0"
    // InternalKap.g:3562:1: rule__Number__Group_4_0__0 : rule__Number__Group_4_0__0__Impl rule__Number__Group_4_0__1 ;
    public final void rule__Number__Group_4_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3566:1: ( rule__Number__Group_4_0__0__Impl rule__Number__Group_4_0__1 )
            // InternalKap.g:3567:2: rule__Number__Group_4_0__0__Impl rule__Number__Group_4_0__1
            {
            pushFollow(FOLLOW_17);
            rule__Number__Group_4_0__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group_4_0__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__0"


    // $ANTLR start "rule__Number__Group_4_0__0__Impl"
    // InternalKap.g:3574:1: rule__Number__Group_4_0__0__Impl : ( ( rule__Number__ExponentialAssignment_4_0_0 ) ) ;
    public final void rule__Number__Group_4_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3578:1: ( ( ( rule__Number__ExponentialAssignment_4_0_0 ) ) )
            // InternalKap.g:3579:1: ( ( rule__Number__ExponentialAssignment_4_0_0 ) )
            {
            // InternalKap.g:3579:1: ( ( rule__Number__ExponentialAssignment_4_0_0 ) )
            // InternalKap.g:3580:2: ( rule__Number__ExponentialAssignment_4_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExponentialAssignment_4_0_0()); 
            }
            // InternalKap.g:3581:2: ( rule__Number__ExponentialAssignment_4_0_0 )
            // InternalKap.g:3581:3: rule__Number__ExponentialAssignment_4_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Number__ExponentialAssignment_4_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExponentialAssignment_4_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__0__Impl"


    // $ANTLR start "rule__Number__Group_4_0__1"
    // InternalKap.g:3589:1: rule__Number__Group_4_0__1 : rule__Number__Group_4_0__1__Impl rule__Number__Group_4_0__2 ;
    public final void rule__Number__Group_4_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3593:1: ( rule__Number__Group_4_0__1__Impl rule__Number__Group_4_0__2 )
            // InternalKap.g:3594:2: rule__Number__Group_4_0__1__Impl rule__Number__Group_4_0__2
            {
            pushFollow(FOLLOW_17);
            rule__Number__Group_4_0__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Number__Group_4_0__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__1"


    // $ANTLR start "rule__Number__Group_4_0__1__Impl"
    // InternalKap.g:3601:1: rule__Number__Group_4_0__1__Impl : ( ( rule__Number__Alternatives_4_0_1 )? ) ;
    public final void rule__Number__Group_4_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3605:1: ( ( ( rule__Number__Alternatives_4_0_1 )? ) )
            // InternalKap.g:3606:1: ( ( rule__Number__Alternatives_4_0_1 )? )
            {
            // InternalKap.g:3606:1: ( ( rule__Number__Alternatives_4_0_1 )? )
            // InternalKap.g:3607:2: ( rule__Number__Alternatives_4_0_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getAlternatives_4_0_1()); 
            }
            // InternalKap.g:3608:2: ( rule__Number__Alternatives_4_0_1 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==21||LA36_0==40) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalKap.g:3608:3: rule__Number__Alternatives_4_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Number__Alternatives_4_0_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getAlternatives_4_0_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__1__Impl"


    // $ANTLR start "rule__Number__Group_4_0__2"
    // InternalKap.g:3616:1: rule__Number__Group_4_0__2 : rule__Number__Group_4_0__2__Impl ;
    public final void rule__Number__Group_4_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3620:1: ( rule__Number__Group_4_0__2__Impl )
            // InternalKap.g:3621:2: rule__Number__Group_4_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Number__Group_4_0__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__2"


    // $ANTLR start "rule__Number__Group_4_0__2__Impl"
    // InternalKap.g:3627:1: rule__Number__Group_4_0__2__Impl : ( ( rule__Number__ExpAssignment_4_0_2 ) ) ;
    public final void rule__Number__Group_4_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3631:1: ( ( ( rule__Number__ExpAssignment_4_0_2 ) ) )
            // InternalKap.g:3632:1: ( ( rule__Number__ExpAssignment_4_0_2 ) )
            {
            // InternalKap.g:3632:1: ( ( rule__Number__ExpAssignment_4_0_2 ) )
            // InternalKap.g:3633:2: ( rule__Number__ExpAssignment_4_0_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExpAssignment_4_0_2()); 
            }
            // InternalKap.g:3634:2: ( rule__Number__ExpAssignment_4_0_2 )
            // InternalKap.g:3634:3: rule__Number__ExpAssignment_4_0_2
            {
            pushFollow(FOLLOW_2);
            rule__Number__ExpAssignment_4_0_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExpAssignment_4_0_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__Group_4_0__2__Impl"


    // $ANTLR start "rule__Date__Group__0"
    // InternalKap.g:3643:1: rule__Date__Group__0 : rule__Date__Group__0__Impl rule__Date__Group__1 ;
    public final void rule__Date__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3647:1: ( rule__Date__Group__0__Impl rule__Date__Group__1 )
            // InternalKap.g:3648:2: rule__Date__Group__0__Impl rule__Date__Group__1
            {
            pushFollow(FOLLOW_33);
            rule__Date__Group__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__0"


    // $ANTLR start "rule__Date__Group__0__Impl"
    // InternalKap.g:3655:1: rule__Date__Group__0__Impl : ( ( rule__Date__YearAssignment_0 ) ) ;
    public final void rule__Date__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3659:1: ( ( ( rule__Date__YearAssignment_0 ) ) )
            // InternalKap.g:3660:1: ( ( rule__Date__YearAssignment_0 ) )
            {
            // InternalKap.g:3660:1: ( ( rule__Date__YearAssignment_0 ) )
            // InternalKap.g:3661:2: ( rule__Date__YearAssignment_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getYearAssignment_0()); 
            }
            // InternalKap.g:3662:2: ( rule__Date__YearAssignment_0 )
            // InternalKap.g:3662:3: rule__Date__YearAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Date__YearAssignment_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getYearAssignment_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__0__Impl"


    // $ANTLR start "rule__Date__Group__1"
    // InternalKap.g:3670:1: rule__Date__Group__1 : rule__Date__Group__1__Impl rule__Date__Group__2 ;
    public final void rule__Date__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3674:1: ( rule__Date__Group__1__Impl rule__Date__Group__2 )
            // InternalKap.g:3675:2: rule__Date__Group__1__Impl rule__Date__Group__2
            {
            pushFollow(FOLLOW_33);
            rule__Date__Group__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__1"


    // $ANTLR start "rule__Date__Group__1__Impl"
    // InternalKap.g:3682:1: rule__Date__Group__1__Impl : ( ( rule__Date__Alternatives_1 )? ) ;
    public final void rule__Date__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3686:1: ( ( ( rule__Date__Alternatives_1 )? ) )
            // InternalKap.g:3687:1: ( ( rule__Date__Alternatives_1 )? )
            {
            // InternalKap.g:3687:1: ( ( rule__Date__Alternatives_1 )? )
            // InternalKap.g:3688:2: ( rule__Date__Alternatives_1 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getAlternatives_1()); 
            }
            // InternalKap.g:3689:2: ( rule__Date__Alternatives_1 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( ((LA37_0>=24 && LA37_0<=25)||LA37_0==44) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalKap.g:3689:3: rule__Date__Alternatives_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__Alternatives_1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getAlternatives_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__1__Impl"


    // $ANTLR start "rule__Date__Group__2"
    // InternalKap.g:3697:1: rule__Date__Group__2 : rule__Date__Group__2__Impl rule__Date__Group__3 ;
    public final void rule__Date__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3701:1: ( rule__Date__Group__2__Impl rule__Date__Group__3 )
            // InternalKap.g:3702:2: rule__Date__Group__2__Impl rule__Date__Group__3
            {
            pushFollow(FOLLOW_32);
            rule__Date__Group__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__2"


    // $ANTLR start "rule__Date__Group__2__Impl"
    // InternalKap.g:3709:1: rule__Date__Group__2__Impl : ( '-' ) ;
    public final void rule__Date__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3713:1: ( ( '-' ) )
            // InternalKap.g:3714:1: ( '-' )
            {
            // InternalKap.g:3714:1: ( '-' )
            // InternalKap.g:3715:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getHyphenMinusKeyword_2()); 
            }
            match(input,40,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getHyphenMinusKeyword_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__2__Impl"


    // $ANTLR start "rule__Date__Group__3"
    // InternalKap.g:3724:1: rule__Date__Group__3 : rule__Date__Group__3__Impl rule__Date__Group__4 ;
    public final void rule__Date__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3728:1: ( rule__Date__Group__3__Impl rule__Date__Group__4 )
            // InternalKap.g:3729:2: rule__Date__Group__3__Impl rule__Date__Group__4
            {
            pushFollow(FOLLOW_34);
            rule__Date__Group__3__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__4();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__3"


    // $ANTLR start "rule__Date__Group__3__Impl"
    // InternalKap.g:3736:1: rule__Date__Group__3__Impl : ( ( rule__Date__MonthAssignment_3 ) ) ;
    public final void rule__Date__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3740:1: ( ( ( rule__Date__MonthAssignment_3 ) ) )
            // InternalKap.g:3741:1: ( ( rule__Date__MonthAssignment_3 ) )
            {
            // InternalKap.g:3741:1: ( ( rule__Date__MonthAssignment_3 ) )
            // InternalKap.g:3742:2: ( rule__Date__MonthAssignment_3 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMonthAssignment_3()); 
            }
            // InternalKap.g:3743:2: ( rule__Date__MonthAssignment_3 )
            // InternalKap.g:3743:3: rule__Date__MonthAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Date__MonthAssignment_3();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMonthAssignment_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__3__Impl"


    // $ANTLR start "rule__Date__Group__4"
    // InternalKap.g:3751:1: rule__Date__Group__4 : rule__Date__Group__4__Impl rule__Date__Group__5 ;
    public final void rule__Date__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3755:1: ( rule__Date__Group__4__Impl rule__Date__Group__5 )
            // InternalKap.g:3756:2: rule__Date__Group__4__Impl rule__Date__Group__5
            {
            pushFollow(FOLLOW_32);
            rule__Date__Group__4__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__5();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__4"


    // $ANTLR start "rule__Date__Group__4__Impl"
    // InternalKap.g:3763:1: rule__Date__Group__4__Impl : ( '-' ) ;
    public final void rule__Date__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3767:1: ( ( '-' ) )
            // InternalKap.g:3768:1: ( '-' )
            {
            // InternalKap.g:3768:1: ( '-' )
            // InternalKap.g:3769:2: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getHyphenMinusKeyword_4()); 
            }
            match(input,40,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getHyphenMinusKeyword_4()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__4__Impl"


    // $ANTLR start "rule__Date__Group__5"
    // InternalKap.g:3778:1: rule__Date__Group__5 : rule__Date__Group__5__Impl rule__Date__Group__6 ;
    public final void rule__Date__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3782:1: ( rule__Date__Group__5__Impl rule__Date__Group__6 )
            // InternalKap.g:3783:2: rule__Date__Group__5__Impl rule__Date__Group__6
            {
            pushFollow(FOLLOW_32);
            rule__Date__Group__5__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group__6();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__5"


    // $ANTLR start "rule__Date__Group__5__Impl"
    // InternalKap.g:3790:1: rule__Date__Group__5__Impl : ( ( rule__Date__DayAssignment_5 ) ) ;
    public final void rule__Date__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3794:1: ( ( ( rule__Date__DayAssignment_5 ) ) )
            // InternalKap.g:3795:1: ( ( rule__Date__DayAssignment_5 ) )
            {
            // InternalKap.g:3795:1: ( ( rule__Date__DayAssignment_5 ) )
            // InternalKap.g:3796:2: ( rule__Date__DayAssignment_5 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getDayAssignment_5()); 
            }
            // InternalKap.g:3797:2: ( rule__Date__DayAssignment_5 )
            // InternalKap.g:3797:3: rule__Date__DayAssignment_5
            {
            pushFollow(FOLLOW_2);
            rule__Date__DayAssignment_5();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getDayAssignment_5()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__5__Impl"


    // $ANTLR start "rule__Date__Group__6"
    // InternalKap.g:3805:1: rule__Date__Group__6 : rule__Date__Group__6__Impl ;
    public final void rule__Date__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3809:1: ( rule__Date__Group__6__Impl )
            // InternalKap.g:3810:2: rule__Date__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group__6__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__6"


    // $ANTLR start "rule__Date__Group__6__Impl"
    // InternalKap.g:3816:1: rule__Date__Group__6__Impl : ( ( rule__Date__Group_6__0 )? ) ;
    public final void rule__Date__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3820:1: ( ( ( rule__Date__Group_6__0 )? ) )
            // InternalKap.g:3821:1: ( ( rule__Date__Group_6__0 )? )
            {
            // InternalKap.g:3821:1: ( ( rule__Date__Group_6__0 )? )
            // InternalKap.g:3822:2: ( rule__Date__Group_6__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getGroup_6()); 
            }
            // InternalKap.g:3823:2: ( rule__Date__Group_6__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==RULE_INT) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalKap.g:3823:3: rule__Date__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__Group_6__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getGroup_6()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group__6__Impl"


    // $ANTLR start "rule__Date__Group_6__0"
    // InternalKap.g:3832:1: rule__Date__Group_6__0 : rule__Date__Group_6__0__Impl rule__Date__Group_6__1 ;
    public final void rule__Date__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3836:1: ( rule__Date__Group_6__0__Impl rule__Date__Group_6__1 )
            // InternalKap.g:3837:2: rule__Date__Group_6__0__Impl rule__Date__Group_6__1
            {
            pushFollow(FOLLOW_35);
            rule__Date__Group_6__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__0"


    // $ANTLR start "rule__Date__Group_6__0__Impl"
    // InternalKap.g:3844:1: rule__Date__Group_6__0__Impl : ( ( rule__Date__HourAssignment_6_0 ) ) ;
    public final void rule__Date__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3848:1: ( ( ( rule__Date__HourAssignment_6_0 ) ) )
            // InternalKap.g:3849:1: ( ( rule__Date__HourAssignment_6_0 ) )
            {
            // InternalKap.g:3849:1: ( ( rule__Date__HourAssignment_6_0 ) )
            // InternalKap.g:3850:2: ( rule__Date__HourAssignment_6_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getHourAssignment_6_0()); 
            }
            // InternalKap.g:3851:2: ( rule__Date__HourAssignment_6_0 )
            // InternalKap.g:3851:3: rule__Date__HourAssignment_6_0
            {
            pushFollow(FOLLOW_2);
            rule__Date__HourAssignment_6_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getHourAssignment_6_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__0__Impl"


    // $ANTLR start "rule__Date__Group_6__1"
    // InternalKap.g:3859:1: rule__Date__Group_6__1 : rule__Date__Group_6__1__Impl rule__Date__Group_6__2 ;
    public final void rule__Date__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3863:1: ( rule__Date__Group_6__1__Impl rule__Date__Group_6__2 )
            // InternalKap.g:3864:2: rule__Date__Group_6__1__Impl rule__Date__Group_6__2
            {
            pushFollow(FOLLOW_32);
            rule__Date__Group_6__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__1"


    // $ANTLR start "rule__Date__Group_6__1__Impl"
    // InternalKap.g:3871:1: rule__Date__Group_6__1__Impl : ( ':' ) ;
    public final void rule__Date__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3875:1: ( ( ':' ) )
            // InternalKap.g:3876:1: ( ':' )
            {
            // InternalKap.g:3876:1: ( ':' )
            // InternalKap.g:3877:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getColonKeyword_6_1()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getColonKeyword_6_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__1__Impl"


    // $ANTLR start "rule__Date__Group_6__2"
    // InternalKap.g:3886:1: rule__Date__Group_6__2 : rule__Date__Group_6__2__Impl rule__Date__Group_6__3 ;
    public final void rule__Date__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3890:1: ( rule__Date__Group_6__2__Impl rule__Date__Group_6__3 )
            // InternalKap.g:3891:2: rule__Date__Group_6__2__Impl rule__Date__Group_6__3
            {
            pushFollow(FOLLOW_35);
            rule__Date__Group_6__2__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6__3();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__2"


    // $ANTLR start "rule__Date__Group_6__2__Impl"
    // InternalKap.g:3898:1: rule__Date__Group_6__2__Impl : ( ( rule__Date__MinAssignment_6_2 ) ) ;
    public final void rule__Date__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3902:1: ( ( ( rule__Date__MinAssignment_6_2 ) ) )
            // InternalKap.g:3903:1: ( ( rule__Date__MinAssignment_6_2 ) )
            {
            // InternalKap.g:3903:1: ( ( rule__Date__MinAssignment_6_2 ) )
            // InternalKap.g:3904:2: ( rule__Date__MinAssignment_6_2 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMinAssignment_6_2()); 
            }
            // InternalKap.g:3905:2: ( rule__Date__MinAssignment_6_2 )
            // InternalKap.g:3905:3: rule__Date__MinAssignment_6_2
            {
            pushFollow(FOLLOW_2);
            rule__Date__MinAssignment_6_2();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMinAssignment_6_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__2__Impl"


    // $ANTLR start "rule__Date__Group_6__3"
    // InternalKap.g:3913:1: rule__Date__Group_6__3 : rule__Date__Group_6__3__Impl ;
    public final void rule__Date__Group_6__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3917:1: ( rule__Date__Group_6__3__Impl )
            // InternalKap.g:3918:2: rule__Date__Group_6__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group_6__3__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__3"


    // $ANTLR start "rule__Date__Group_6__3__Impl"
    // InternalKap.g:3924:1: rule__Date__Group_6__3__Impl : ( ( rule__Date__Group_6_3__0 )? ) ;
    public final void rule__Date__Group_6__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3928:1: ( ( ( rule__Date__Group_6_3__0 )? ) )
            // InternalKap.g:3929:1: ( ( rule__Date__Group_6_3__0 )? )
            {
            // InternalKap.g:3929:1: ( ( rule__Date__Group_6_3__0 )? )
            // InternalKap.g:3930:2: ( rule__Date__Group_6_3__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getGroup_6_3()); 
            }
            // InternalKap.g:3931:2: ( rule__Date__Group_6_3__0 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==32) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalKap.g:3931:3: rule__Date__Group_6_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__Group_6_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getGroup_6_3()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6__3__Impl"


    // $ANTLR start "rule__Date__Group_6_3__0"
    // InternalKap.g:3940:1: rule__Date__Group_6_3__0 : rule__Date__Group_6_3__0__Impl rule__Date__Group_6_3__1 ;
    public final void rule__Date__Group_6_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3944:1: ( rule__Date__Group_6_3__0__Impl rule__Date__Group_6_3__1 )
            // InternalKap.g:3945:2: rule__Date__Group_6_3__0__Impl rule__Date__Group_6_3__1
            {
            pushFollow(FOLLOW_32);
            rule__Date__Group_6_3__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__0"


    // $ANTLR start "rule__Date__Group_6_3__0__Impl"
    // InternalKap.g:3952:1: rule__Date__Group_6_3__0__Impl : ( ':' ) ;
    public final void rule__Date__Group_6_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3956:1: ( ( ':' ) )
            // InternalKap.g:3957:1: ( ':' )
            {
            // InternalKap.g:3957:1: ( ':' )
            // InternalKap.g:3958:2: ':'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getColonKeyword_6_3_0()); 
            }
            match(input,32,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getColonKeyword_6_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__0__Impl"


    // $ANTLR start "rule__Date__Group_6_3__1"
    // InternalKap.g:3967:1: rule__Date__Group_6_3__1 : rule__Date__Group_6_3__1__Impl rule__Date__Group_6_3__2 ;
    public final void rule__Date__Group_6_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3971:1: ( rule__Date__Group_6_3__1__Impl rule__Date__Group_6_3__2 )
            // InternalKap.g:3972:2: rule__Date__Group_6_3__1__Impl rule__Date__Group_6_3__2
            {
            pushFollow(FOLLOW_36);
            rule__Date__Group_6_3__1__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3__2();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__1"


    // $ANTLR start "rule__Date__Group_6_3__1__Impl"
    // InternalKap.g:3979:1: rule__Date__Group_6_3__1__Impl : ( ( rule__Date__SecAssignment_6_3_1 ) ) ;
    public final void rule__Date__Group_6_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3983:1: ( ( ( rule__Date__SecAssignment_6_3_1 ) ) )
            // InternalKap.g:3984:1: ( ( rule__Date__SecAssignment_6_3_1 ) )
            {
            // InternalKap.g:3984:1: ( ( rule__Date__SecAssignment_6_3_1 ) )
            // InternalKap.g:3985:2: ( rule__Date__SecAssignment_6_3_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getSecAssignment_6_3_1()); 
            }
            // InternalKap.g:3986:2: ( rule__Date__SecAssignment_6_3_1 )
            // InternalKap.g:3986:3: rule__Date__SecAssignment_6_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Date__SecAssignment_6_3_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getSecAssignment_6_3_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__1__Impl"


    // $ANTLR start "rule__Date__Group_6_3__2"
    // InternalKap.g:3994:1: rule__Date__Group_6_3__2 : rule__Date__Group_6_3__2__Impl ;
    public final void rule__Date__Group_6_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:3998:1: ( rule__Date__Group_6_3__2__Impl )
            // InternalKap.g:3999:2: rule__Date__Group_6_3__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3__2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__2"


    // $ANTLR start "rule__Date__Group_6_3__2__Impl"
    // InternalKap.g:4005:1: rule__Date__Group_6_3__2__Impl : ( ( rule__Date__Group_6_3_2__0 )? ) ;
    public final void rule__Date__Group_6_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4009:1: ( ( ( rule__Date__Group_6_3_2__0 )? ) )
            // InternalKap.g:4010:1: ( ( rule__Date__Group_6_3_2__0 )? )
            {
            // InternalKap.g:4010:1: ( ( rule__Date__Group_6_3_2__0 )? )
            // InternalKap.g:4011:2: ( rule__Date__Group_6_3_2__0 )?
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getGroup_6_3_2()); 
            }
            // InternalKap.g:4012:2: ( rule__Date__Group_6_3_2__0 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==41) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalKap.g:4012:3: rule__Date__Group_6_3_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Date__Group_6_3_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getGroup_6_3_2()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3__2__Impl"


    // $ANTLR start "rule__Date__Group_6_3_2__0"
    // InternalKap.g:4021:1: rule__Date__Group_6_3_2__0 : rule__Date__Group_6_3_2__0__Impl rule__Date__Group_6_3_2__1 ;
    public final void rule__Date__Group_6_3_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4025:1: ( rule__Date__Group_6_3_2__0__Impl rule__Date__Group_6_3_2__1 )
            // InternalKap.g:4026:2: rule__Date__Group_6_3_2__0__Impl rule__Date__Group_6_3_2__1
            {
            pushFollow(FOLLOW_32);
            rule__Date__Group_6_3_2__0__Impl();

            state._fsp--;
            if (state.failed) return ;
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3_2__1();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3_2__0"


    // $ANTLR start "rule__Date__Group_6_3_2__0__Impl"
    // InternalKap.g:4033:1: rule__Date__Group_6_3_2__0__Impl : ( '.' ) ;
    public final void rule__Date__Group_6_3_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4037:1: ( ( '.' ) )
            // InternalKap.g:4038:1: ( '.' )
            {
            // InternalKap.g:4038:1: ( '.' )
            // InternalKap.g:4039:2: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0()); 
            }
            match(input,41,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getFullStopKeyword_6_3_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3_2__0__Impl"


    // $ANTLR start "rule__Date__Group_6_3_2__1"
    // InternalKap.g:4048:1: rule__Date__Group_6_3_2__1 : rule__Date__Group_6_3_2__1__Impl ;
    public final void rule__Date__Group_6_3_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4052:1: ( rule__Date__Group_6_3_2__1__Impl )
            // InternalKap.g:4053:2: rule__Date__Group_6_3_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Date__Group_6_3_2__1__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3_2__1"


    // $ANTLR start "rule__Date__Group_6_3_2__1__Impl"
    // InternalKap.g:4059:1: rule__Date__Group_6_3_2__1__Impl : ( ( rule__Date__MsAssignment_6_3_2_1 ) ) ;
    public final void rule__Date__Group_6_3_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4063:1: ( ( ( rule__Date__MsAssignment_6_3_2_1 ) ) )
            // InternalKap.g:4064:1: ( ( rule__Date__MsAssignment_6_3_2_1 ) )
            {
            // InternalKap.g:4064:1: ( ( rule__Date__MsAssignment_6_3_2_1 ) )
            // InternalKap.g:4065:2: ( rule__Date__MsAssignment_6_3_2_1 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMsAssignment_6_3_2_1()); 
            }
            // InternalKap.g:4066:2: ( rule__Date__MsAssignment_6_3_2_1 )
            // InternalKap.g:4066:3: rule__Date__MsAssignment_6_3_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Date__MsAssignment_6_3_2_1();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMsAssignment_6_3_2_1()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__Group_6_3_2__1__Impl"


    // $ANTLR start "rule__Preamble__UnorderedGroup_2"
    // InternalKap.g:4075:1: rule__Preamble__UnorderedGroup_2 : ( rule__Preamble__UnorderedGroup_2__0 )? ;
    public final void rule__Preamble__UnorderedGroup_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        		getUnorderedGroupHelper().enter(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
        	
        try {
            // InternalKap.g:4080:1: ( ( rule__Preamble__UnorderedGroup_2__0 )? )
            // InternalKap.g:4081:2: ( rule__Preamble__UnorderedGroup_2__0 )?
            {
            // InternalKap.g:4081:2: ( rule__Preamble__UnorderedGroup_2__0 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( LA41_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
                alt41=1;
            }
            else if ( LA41_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
                alt41=1;
            }
            else if ( LA41_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
                alt41=1;
            }
            else if ( LA41_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalKap.g:0:0: rule__Preamble__UnorderedGroup_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Preamble__UnorderedGroup_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	getUnorderedGroupHelper().leave(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__UnorderedGroup_2"


    // $ANTLR start "rule__Preamble__UnorderedGroup_2__Impl"
    // InternalKap.g:4089:1: rule__Preamble__UnorderedGroup_2__Impl : ( ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_3__0 ) ) ) ) ) ;
    public final void rule__Preamble__UnorderedGroup_2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        		boolean selected = false;
        	
        try {
            // InternalKap.g:4094:1: ( ( ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_3__0 ) ) ) ) ) )
            // InternalKap.g:4095:3: ( ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_3__0 ) ) ) ) )
            {
            // InternalKap.g:4095:3: ( ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) ) | ({...}? => ( ( ( rule__Preamble__Group_2_3__0 ) ) ) ) )
            int alt42=4;
            int LA42_0 = input.LA(1);

            if ( LA42_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
                alt42=1;
            }
            else if ( LA42_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
                alt42=2;
            }
            else if ( LA42_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
                alt42=3;
            }
            else if ( LA42_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
                alt42=4;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // InternalKap.g:4096:3: ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) )
                    {
                    // InternalKap.g:4096:3: ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) )
                    // InternalKap.g:4097:4: {...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__Preamble__UnorderedGroup_2__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
                    }
                    // InternalKap.g:4097:104: ( ( ( rule__Preamble__Group_2_0__0 ) ) )
                    // InternalKap.g:4098:5: ( ( rule__Preamble__Group_2_0__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
                    selected = true;
                    // InternalKap.g:4104:5: ( ( rule__Preamble__Group_2_0__0 ) )
                    // InternalKap.g:4105:6: ( rule__Preamble__Group_2_0__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPreambleAccess().getGroup_2_0()); 
                    }
                    // InternalKap.g:4106:6: ( rule__Preamble__Group_2_0__0 )
                    // InternalKap.g:4106:7: rule__Preamble__Group_2_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Preamble__Group_2_0__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPreambleAccess().getGroup_2_0()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalKap.g:4111:3: ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) )
                    {
                    // InternalKap.g:4111:3: ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) )
                    // InternalKap.g:4112:4: {...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__Preamble__UnorderedGroup_2__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
                    }
                    // InternalKap.g:4112:104: ( ( ( rule__Preamble__Group_2_1__0 ) ) )
                    // InternalKap.g:4113:5: ( ( rule__Preamble__Group_2_1__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
                    selected = true;
                    // InternalKap.g:4119:5: ( ( rule__Preamble__Group_2_1__0 ) )
                    // InternalKap.g:4120:6: ( rule__Preamble__Group_2_1__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPreambleAccess().getGroup_2_1()); 
                    }
                    // InternalKap.g:4121:6: ( rule__Preamble__Group_2_1__0 )
                    // InternalKap.g:4121:7: rule__Preamble__Group_2_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Preamble__Group_2_1__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPreambleAccess().getGroup_2_1()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalKap.g:4126:3: ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) )
                    {
                    // InternalKap.g:4126:3: ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) )
                    // InternalKap.g:4127:4: {...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__Preamble__UnorderedGroup_2__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
                    }
                    // InternalKap.g:4127:104: ( ( ( rule__Preamble__Group_2_2__0 ) ) )
                    // InternalKap.g:4128:5: ( ( rule__Preamble__Group_2_2__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
                    selected = true;
                    // InternalKap.g:4134:5: ( ( rule__Preamble__Group_2_2__0 ) )
                    // InternalKap.g:4135:6: ( rule__Preamble__Group_2_2__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPreambleAccess().getGroup_2_2()); 
                    }
                    // InternalKap.g:4136:6: ( rule__Preamble__Group_2_2__0 )
                    // InternalKap.g:4136:7: rule__Preamble__Group_2_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Preamble__Group_2_2__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPreambleAccess().getGroup_2_2()); 
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalKap.g:4141:3: ({...}? => ( ( ( rule__Preamble__Group_2_3__0 ) ) ) )
                    {
                    // InternalKap.g:4141:3: ({...}? => ( ( ( rule__Preamble__Group_2_3__0 ) ) ) )
                    // InternalKap.g:4142:4: {...}? => ( ( ( rule__Preamble__Group_2_3__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        throw new FailedPredicateException(input, "rule__Preamble__UnorderedGroup_2__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3)");
                    }
                    // InternalKap.g:4142:104: ( ( ( rule__Preamble__Group_2_3__0 ) ) )
                    // InternalKap.g:4143:5: ( ( rule__Preamble__Group_2_3__0 ) )
                    {
                    getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3);
                    selected = true;
                    // InternalKap.g:4149:5: ( ( rule__Preamble__Group_2_3__0 ) )
                    // InternalKap.g:4150:6: ( rule__Preamble__Group_2_3__0 )
                    {
                    if ( state.backtracking==0 ) {
                       before(grammarAccess.getPreambleAccess().getGroup_2_3()); 
                    }
                    // InternalKap.g:4151:6: ( rule__Preamble__Group_2_3__0 )
                    // InternalKap.g:4151:7: rule__Preamble__Group_2_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Preamble__Group_2_3__0();

                    state._fsp--;
                    if (state.failed) return ;

                    }

                    if ( state.backtracking==0 ) {
                       after(grammarAccess.getPreambleAccess().getGroup_2_3()); 
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	if (selected)
            		getUnorderedGroupHelper().returnFromSelection(grammarAccess.getPreambleAccess().getUnorderedGroup_2());
            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__UnorderedGroup_2__Impl"


    // $ANTLR start "rule__Preamble__UnorderedGroup_2__0"
    // InternalKap.g:4164:1: rule__Preamble__UnorderedGroup_2__0 : rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__1 )? ;
    public final void rule__Preamble__UnorderedGroup_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4168:1: ( rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__1 )? )
            // InternalKap.g:4169:2: rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__1 )?
            {
            pushFollow(FOLLOW_37);
            rule__Preamble__UnorderedGroup_2__Impl();

            state._fsp--;
            if (state.failed) return ;
            // InternalKap.g:4170:2: ( rule__Preamble__UnorderedGroup_2__1 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( LA43_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
                alt43=1;
            }
            else if ( LA43_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
                alt43=1;
            }
            else if ( LA43_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
                alt43=1;
            }
            else if ( LA43_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalKap.g:0:0: rule__Preamble__UnorderedGroup_2__1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Preamble__UnorderedGroup_2__1();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__UnorderedGroup_2__0"


    // $ANTLR start "rule__Preamble__UnorderedGroup_2__1"
    // InternalKap.g:4176:1: rule__Preamble__UnorderedGroup_2__1 : rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__2 )? ;
    public final void rule__Preamble__UnorderedGroup_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4180:1: ( rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__2 )? )
            // InternalKap.g:4181:2: rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__2 )?
            {
            pushFollow(FOLLOW_37);
            rule__Preamble__UnorderedGroup_2__Impl();

            state._fsp--;
            if (state.failed) return ;
            // InternalKap.g:4182:2: ( rule__Preamble__UnorderedGroup_2__2 )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( LA44_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
                alt44=1;
            }
            else if ( LA44_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
                alt44=1;
            }
            else if ( LA44_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
                alt44=1;
            }
            else if ( LA44_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalKap.g:0:0: rule__Preamble__UnorderedGroup_2__2
                    {
                    pushFollow(FOLLOW_2);
                    rule__Preamble__UnorderedGroup_2__2();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__UnorderedGroup_2__1"


    // $ANTLR start "rule__Preamble__UnorderedGroup_2__2"
    // InternalKap.g:4188:1: rule__Preamble__UnorderedGroup_2__2 : rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__3 )? ;
    public final void rule__Preamble__UnorderedGroup_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4192:1: ( rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__3 )? )
            // InternalKap.g:4193:2: rule__Preamble__UnorderedGroup_2__Impl ( rule__Preamble__UnorderedGroup_2__3 )?
            {
            pushFollow(FOLLOW_37);
            rule__Preamble__UnorderedGroup_2__Impl();

            state._fsp--;
            if (state.failed) return ;
            // InternalKap.g:4194:2: ( rule__Preamble__UnorderedGroup_2__3 )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( LA45_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
                alt45=1;
            }
            else if ( LA45_0 == 28 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
                alt45=1;
            }
            else if ( LA45_0 == 29 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
                alt45=1;
            }
            else if ( LA45_0 == 30 && getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 3) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalKap.g:0:0: rule__Preamble__UnorderedGroup_2__3
                    {
                    pushFollow(FOLLOW_2);
                    rule__Preamble__UnorderedGroup_2__3();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__UnorderedGroup_2__2"


    // $ANTLR start "rule__Preamble__UnorderedGroup_2__3"
    // InternalKap.g:4200:1: rule__Preamble__UnorderedGroup_2__3 : rule__Preamble__UnorderedGroup_2__Impl ;
    public final void rule__Preamble__UnorderedGroup_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4204:1: ( rule__Preamble__UnorderedGroup_2__Impl )
            // InternalKap.g:4205:2: rule__Preamble__UnorderedGroup_2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Preamble__UnorderedGroup_2__Impl();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__UnorderedGroup_2__3"


    // $ANTLR start "rule__Model__PreambleAssignment_1"
    // InternalKap.g:4212:1: rule__Model__PreambleAssignment_1 : ( rulePreamble ) ;
    public final void rule__Model__PreambleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4216:1: ( ( rulePreamble ) )
            // InternalKap.g:4217:2: ( rulePreamble )
            {
            // InternalKap.g:4217:2: ( rulePreamble )
            // InternalKap.g:4218:3: rulePreamble
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getPreamblePreambleParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            rulePreamble();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getPreamblePreambleParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__PreambleAssignment_1"


    // $ANTLR start "rule__Model__DefinitionsAssignment_2"
    // InternalKap.g:4227:1: rule__Model__DefinitionsAssignment_2 : ( ruleDefinition ) ;
    public final void rule__Model__DefinitionsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4231:1: ( ( ruleDefinition ) )
            // InternalKap.g:4232:2: ( ruleDefinition )
            {
            // InternalKap.g:4232:2: ( ruleDefinition )
            // InternalKap.g:4233:3: ruleDefinition
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getModelAccess().getDefinitionsDefinitionParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDefinition();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getModelAccess().getDefinitionsDefinitionParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__DefinitionsAssignment_2"


    // $ANTLR start "rule__Preamble__NameAssignment_1"
    // InternalKap.g:4242:1: rule__Preamble__NameAssignment_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Preamble__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4246:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4247:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4247:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4248:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__NameAssignment_1"


    // $ANTLR start "rule__Preamble__WorldviewAssignment_2_0_1"
    // InternalKap.g:4257:1: rule__Preamble__WorldviewAssignment_2_0_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Preamble__WorldviewAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4261:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4262:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4262:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4263:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getWorldviewLOWERCASE_IDTerminalRuleCall_2_0_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getWorldviewLOWERCASE_IDTerminalRuleCall_2_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__WorldviewAssignment_2_0_1"


    // $ANTLR start "rule__Preamble__PermissionsAssignment_2_1_1"
    // InternalKap.g:4272:1: rule__Preamble__PermissionsAssignment_2_1_1 : ( RULE_STRING ) ;
    public final void rule__Preamble__PermissionsAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4276:1: ( ( RULE_STRING ) )
            // InternalKap.g:4277:2: ( RULE_STRING )
            {
            // InternalKap.g:4277:2: ( RULE_STRING )
            // InternalKap.g:4278:3: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_2_1_1_0()); 
            }
            match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getPermissionsSTRINGTerminalRuleCall_2_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__PermissionsAssignment_2_1_1"


    // $ANTLR start "rule__Preamble__AuthorsAssignment_2_2_1"
    // InternalKap.g:4287:1: rule__Preamble__AuthorsAssignment_2_2_1 : ( RULE_STRING ) ;
    public final void rule__Preamble__AuthorsAssignment_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4291:1: ( ( RULE_STRING ) )
            // InternalKap.g:4292:2: ( RULE_STRING )
            {
            // InternalKap.g:4292:2: ( RULE_STRING )
            // InternalKap.g:4293:3: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_2_2_1_0()); 
            }
            match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getAuthorsSTRINGTerminalRuleCall_2_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__AuthorsAssignment_2_2_1"


    // $ANTLR start "rule__Preamble__VersionAssignment_2_3_1"
    // InternalKap.g:4302:1: rule__Preamble__VersionAssignment_2_3_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Preamble__VersionAssignment_2_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4306:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4307:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4307:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4308:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getPreambleAccess().getVersionLOWERCASE_IDTerminalRuleCall_2_3_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getPreambleAccess().getVersionLOWERCASE_IDTerminalRuleCall_2_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Preamble__VersionAssignment_2_3_1"


    // $ANTLR start "rule__Definition__NameAssignment_1"
    // InternalKap.g:4317:1: rule__Definition__NameAssignment_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Definition__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4321:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4322:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4322:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4323:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getNameLOWERCASE_IDTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__NameAssignment_1"


    // $ANTLR start "rule__Definition__ArgumentsAssignment_2"
    // InternalKap.g:4332:1: rule__Definition__ArgumentsAssignment_2 : ( ruleArgumentDeclaration ) ;
    public final void rule__Definition__ArgumentsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4336:1: ( ( ruleArgumentDeclaration ) )
            // InternalKap.g:4337:2: ( ruleArgumentDeclaration )
            {
            // InternalKap.g:4337:2: ( ruleArgumentDeclaration )
            // InternalKap.g:4338:3: ruleArgumentDeclaration
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleArgumentDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getArgumentsArgumentDeclarationParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__ArgumentsAssignment_2"


    // $ANTLR start "rule__Definition__BodyAssignment_4"
    // InternalKap.g:4347:1: rule__Definition__BodyAssignment_4 : ( ruleBody ) ;
    public final void rule__Definition__BodyAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4351:1: ( ( ruleBody ) )
            // InternalKap.g:4352:2: ( ruleBody )
            {
            // InternalKap.g:4352:2: ( ruleBody )
            // InternalKap.g:4353:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDefinitionAccess().getBodyBodyParserRuleCall_4_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDefinitionAccess().getBodyBodyParserRuleCall_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Definition__BodyAssignment_4"


    // $ANTLR start "rule__ArgumentDeclaration__IdsAssignment_2_0"
    // InternalKap.g:4362:1: rule__ArgumentDeclaration__IdsAssignment_2_0 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__ArgumentDeclaration__IdsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4366:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4367:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4367:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4368:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_0_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__IdsAssignment_2_0"


    // $ANTLR start "rule__ArgumentDeclaration__IdsAssignment_2_1_1"
    // InternalKap.g:4377:1: rule__ArgumentDeclaration__IdsAssignment_2_1_1 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__ArgumentDeclaration__IdsAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4381:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4382:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4382:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4383:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_1_1_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getArgumentDeclarationAccess().getIdsLOWERCASE_IDTerminalRuleCall_2_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ArgumentDeclaration__IdsAssignment_2_1_1"


    // $ANTLR start "rule__ParameterList__PairsAssignment_0"
    // InternalKap.g:4392:1: rule__ParameterList__PairsAssignment_0 : ( ruleKeyValuePair ) ;
    public final void rule__ParameterList__PairsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4396:1: ( ( ruleKeyValuePair ) )
            // InternalKap.g:4397:2: ( ruleKeyValuePair )
            {
            // InternalKap.g:4397:2: ( ruleKeyValuePair )
            // InternalKap.g:4398:3: ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__PairsAssignment_0"


    // $ANTLR start "rule__ParameterList__PairsAssignment_1_1"
    // InternalKap.g:4407:1: rule__ParameterList__PairsAssignment_1_1 : ( ruleKeyValuePair ) ;
    public final void rule__ParameterList__PairsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4411:1: ( ( ruleKeyValuePair ) )
            // InternalKap.g:4412:2: ( ruleKeyValuePair )
            {
            // InternalKap.g:4412:2: ( ruleKeyValuePair )
            // InternalKap.g:4413:3: ruleKeyValuePair
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleKeyValuePair();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getParameterListAccess().getPairsKeyValuePairParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ParameterList__PairsAssignment_1_1"


    // $ANTLR start "rule__KeyValuePair__NameAssignment_0_0"
    // InternalKap.g:4422:1: rule__KeyValuePair__NameAssignment_0_0 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__KeyValuePair__NameAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4426:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4427:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4427:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4428:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__NameAssignment_0_0"


    // $ANTLR start "rule__KeyValuePair__InteractiveAssignment_0_1_0"
    // InternalKap.g:4437:1: rule__KeyValuePair__InteractiveAssignment_0_1_0 : ( ( '=?' ) ) ;
    public final void rule__KeyValuePair__InteractiveAssignment_0_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4441:1: ( ( ( '=?' ) ) )
            // InternalKap.g:4442:2: ( ( '=?' ) )
            {
            // InternalKap.g:4442:2: ( ( '=?' ) )
            // InternalKap.g:4443:3: ( '=?' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0()); 
            }
            // InternalKap.g:4444:3: ( '=?' )
            // InternalKap.g:4445:4: '=?'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0()); 
            }
            match(input,42,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getInteractiveEqualsSignQuestionMarkKeyword_0_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__InteractiveAssignment_0_1_0"


    // $ANTLR start "rule__KeyValuePair__ValueAssignment_1"
    // InternalKap.g:4456:1: rule__KeyValuePair__ValueAssignment_1 : ( ruleValue ) ;
    public final void rule__KeyValuePair__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4460:1: ( ( ruleValue ) )
            // InternalKap.g:4461:2: ( ruleValue )
            {
            // InternalKap.g:4461:2: ( ruleValue )
            // InternalKap.g:4462:3: ruleValue
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getKeyValuePairAccess().getValueValueParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleValue();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getKeyValuePairAccess().getValueValueParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__KeyValuePair__ValueAssignment_1"


    // $ANTLR start "rule__Value__ArgvalueAssignment_0"
    // InternalKap.g:4471:1: rule__Value__ArgvalueAssignment_0 : ( RULE_ARGVALUE ) ;
    public final void rule__Value__ArgvalueAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4475:1: ( ( RULE_ARGVALUE ) )
            // InternalKap.g:4476:2: ( RULE_ARGVALUE )
            {
            // InternalKap.g:4476:2: ( RULE_ARGVALUE )
            // InternalKap.g:4477:3: RULE_ARGVALUE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getArgvalueARGVALUETerminalRuleCall_0_0()); 
            }
            match(input,RULE_ARGVALUE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getArgvalueARGVALUETerminalRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__ArgvalueAssignment_0"


    // $ANTLR start "rule__Value__LiteralAssignment_1"
    // InternalKap.g:4486:1: rule__Value__LiteralAssignment_1 : ( ruleLiteral ) ;
    public final void rule__Value__LiteralAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4490:1: ( ( ruleLiteral ) )
            // InternalKap.g:4491:2: ( ruleLiteral )
            {
            // InternalKap.g:4491:2: ( ruleLiteral )
            // InternalKap.g:4492:3: ruleLiteral
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLiteral();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getLiteralLiteralParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__LiteralAssignment_1"


    // $ANTLR start "rule__Value__IdAssignment_2"
    // InternalKap.g:4501:1: rule__Value__IdAssignment_2 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Value__IdAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4505:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4506:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4506:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4507:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getIdLOWERCASE_IDTerminalRuleCall_2_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getIdLOWERCASE_IDTerminalRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__IdAssignment_2"


    // $ANTLR start "rule__Value__ObservableAssignment_3"
    // InternalKap.g:4516:1: rule__Value__ObservableAssignment_3 : ( RULE_OBSERVABLE ) ;
    public final void rule__Value__ObservableAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4520:1: ( ( RULE_OBSERVABLE ) )
            // InternalKap.g:4521:2: ( RULE_OBSERVABLE )
            {
            // InternalKap.g:4521:2: ( RULE_OBSERVABLE )
            // InternalKap.g:4522:3: RULE_OBSERVABLE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getObservableOBSERVABLETerminalRuleCall_3_0()); 
            }
            match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getObservableOBSERVABLETerminalRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__ObservableAssignment_3"


    // $ANTLR start "rule__Value__ExpressionAssignment_4"
    // InternalKap.g:4531:1: rule__Value__ExpressionAssignment_4 : ( RULE_EXPR ) ;
    public final void rule__Value__ExpressionAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4535:1: ( ( RULE_EXPR ) )
            // InternalKap.g:4536:2: ( RULE_EXPR )
            {
            // InternalKap.g:4536:2: ( RULE_EXPR )
            // InternalKap.g:4537:3: RULE_EXPR
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_4_0()); 
            }
            match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getValueAccess().getExpressionEXPRTerminalRuleCall_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Value__ExpressionAssignment_4"


    // $ANTLR start "rule__Literal__NumberAssignment_0"
    // InternalKap.g:4546:1: rule__Literal__NumberAssignment_0 : ( ruleNumber ) ;
    public final void rule__Literal__NumberAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4550:1: ( ( ruleNumber ) )
            // InternalKap.g:4551:2: ( ruleNumber )
            {
            // InternalKap.g:4551:2: ( ruleNumber )
            // InternalKap.g:4552:3: ruleNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getNumberNumberParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getNumberNumberParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__NumberAssignment_0"


    // $ANTLR start "rule__Literal__FromAssignment_1_0"
    // InternalKap.g:4561:1: rule__Literal__FromAssignment_1_0 : ( ruleNumber ) ;
    public final void rule__Literal__FromAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4565:1: ( ( ruleNumber ) )
            // InternalKap.g:4566:2: ( ruleNumber )
            {
            // InternalKap.g:4566:2: ( ruleNumber )
            // InternalKap.g:4567:3: ruleNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getFromNumberParserRuleCall_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__FromAssignment_1_0"


    // $ANTLR start "rule__Literal__ToAssignment_1_2"
    // InternalKap.g:4576:1: rule__Literal__ToAssignment_1_2 : ( ruleNumber ) ;
    public final void rule__Literal__ToAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4580:1: ( ( ruleNumber ) )
            // InternalKap.g:4581:2: ( ruleNumber )
            {
            // InternalKap.g:4581:2: ( ruleNumber )
            // InternalKap.g:4582:3: ruleNumber
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getToNumberParserRuleCall_1_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleNumber();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getToNumberParserRuleCall_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__ToAssignment_1_2"


    // $ANTLR start "rule__Literal__StringAssignment_2"
    // InternalKap.g:4591:1: rule__Literal__StringAssignment_2 : ( RULE_STRING ) ;
    public final void rule__Literal__StringAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4595:1: ( ( RULE_STRING ) )
            // InternalKap.g:4596:2: ( RULE_STRING )
            {
            // InternalKap.g:4596:2: ( RULE_STRING )
            // InternalKap.g:4597:3: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getStringSTRINGTerminalRuleCall_2_0()); 
            }
            match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getStringSTRINGTerminalRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__StringAssignment_2"


    // $ANTLR start "rule__Literal__DateAssignment_3"
    // InternalKap.g:4606:1: rule__Literal__DateAssignment_3 : ( ruleDate ) ;
    public final void rule__Literal__DateAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4610:1: ( ( ruleDate ) )
            // InternalKap.g:4611:2: ( ruleDate )
            {
            // InternalKap.g:4611:2: ( ruleDate )
            // InternalKap.g:4612:3: ruleDate
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getDateDateParserRuleCall_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleDate();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getDateDateParserRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__DateAssignment_3"


    // $ANTLR start "rule__Literal__BooleanAssignment_4"
    // InternalKap.g:4621:1: rule__Literal__BooleanAssignment_4 : ( ( rule__Literal__BooleanAlternatives_4_0 ) ) ;
    public final void rule__Literal__BooleanAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4625:1: ( ( ( rule__Literal__BooleanAlternatives_4_0 ) ) )
            // InternalKap.g:4626:2: ( ( rule__Literal__BooleanAlternatives_4_0 ) )
            {
            // InternalKap.g:4626:2: ( ( rule__Literal__BooleanAlternatives_4_0 ) )
            // InternalKap.g:4627:3: ( rule__Literal__BooleanAlternatives_4_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getLiteralAccess().getBooleanAlternatives_4_0()); 
            }
            // InternalKap.g:4628:3: ( rule__Literal__BooleanAlternatives_4_0 )
            // InternalKap.g:4628:4: rule__Literal__BooleanAlternatives_4_0
            {
            pushFollow(FOLLOW_2);
            rule__Literal__BooleanAlternatives_4_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getLiteralAccess().getBooleanAlternatives_4_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Literal__BooleanAssignment_4"


    // $ANTLR start "rule__Body__ListAssignment_0_1"
    // InternalKap.g:4636:1: rule__Body__ListAssignment_0_1 : ( ruleStatement ) ;
    public final void rule__Body__ListAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4640:1: ( ( ruleStatement ) )
            // InternalKap.g:4641:2: ( ruleStatement )
            {
            // InternalKap.g:4641:2: ( ruleStatement )
            // InternalKap.g:4642:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__ListAssignment_0_1"


    // $ANTLR start "rule__Body__ListAssignment_0_2"
    // InternalKap.g:4651:1: rule__Body__ListAssignment_0_2 : ( ruleStatement ) ;
    public final void rule__Body__ListAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4655:1: ( ( ruleStatement ) )
            // InternalKap.g:4656:2: ( ruleStatement )
            {
            // InternalKap.g:4656:2: ( ruleStatement )
            // InternalKap.g:4657:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getListStatementParserRuleCall_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__ListAssignment_0_2"


    // $ANTLR start "rule__Body__IsgroupAssignment_1_0"
    // InternalKap.g:4666:1: rule__Body__IsgroupAssignment_1_0 : ( ( '(' ) ) ;
    public final void rule__Body__IsgroupAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4670:1: ( ( ( '(' ) ) )
            // InternalKap.g:4671:2: ( ( '(' ) )
            {
            // InternalKap.g:4671:2: ( ( '(' ) )
            // InternalKap.g:4672:3: ( '(' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0()); 
            }
            // InternalKap.g:4673:3: ( '(' )
            // InternalKap.g:4674:4: '('
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0()); 
            }
            match(input,33,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getIsgroupLeftParenthesisKeyword_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__IsgroupAssignment_1_0"


    // $ANTLR start "rule__Body__GroupAssignment_1_1_0"
    // InternalKap.g:4685:1: rule__Body__GroupAssignment_1_1_0 : ( ruleStatement ) ;
    public final void rule__Body__GroupAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4689:1: ( ( ruleStatement ) )
            // InternalKap.g:4690:2: ( ruleStatement )
            {
            // InternalKap.g:4690:2: ( ruleStatement )
            // InternalKap.g:4691:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__GroupAssignment_1_1_0"


    // $ANTLR start "rule__Body__GroupAssignment_1_1_1"
    // InternalKap.g:4700:1: rule__Body__GroupAssignment_1_1_1 : ( ruleStatement ) ;
    public final void rule__Body__GroupAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4704:1: ( ( ruleStatement ) )
            // InternalKap.g:4705:2: ( ruleStatement )
            {
            // InternalKap.g:4705:2: ( ruleStatement )
            // InternalKap.g:4706:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getBodyAccess().getGroupStatementParserRuleCall_1_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Body__GroupAssignment_1_1_1"


    // $ANTLR start "rule__Statement__CallAssignment_0"
    // InternalKap.g:4715:1: rule__Statement__CallAssignment_0 : ( ruleCall ) ;
    public final void rule__Statement__CallAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4719:1: ( ( ruleCall ) )
            // InternalKap.g:4720:2: ( ruleCall )
            {
            // InternalKap.g:4720:2: ( ruleCall )
            // InternalKap.g:4721:3: ruleCall
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getCallCallParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getCallCallParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__CallAssignment_0"


    // $ANTLR start "rule__Statement__TextAssignment_1"
    // InternalKap.g:4730:1: rule__Statement__TextAssignment_1 : ( RULE_EMBEDDEDTEXT ) ;
    public final void rule__Statement__TextAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4734:1: ( ( RULE_EMBEDDEDTEXT ) )
            // InternalKap.g:4735:2: ( RULE_EMBEDDEDTEXT )
            {
            // InternalKap.g:4735:2: ( RULE_EMBEDDEDTEXT )
            // InternalKap.g:4736:3: RULE_EMBEDDEDTEXT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_0()); 
            }
            match(input,RULE_EMBEDDEDTEXT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getTextEMBEDDEDTEXTTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__TextAssignment_1"


    // $ANTLR start "rule__Statement__IfAssignment_2"
    // InternalKap.g:4745:1: rule__Statement__IfAssignment_2 : ( ruleIfStatement ) ;
    public final void rule__Statement__IfAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4749:1: ( ( ruleIfStatement ) )
            // InternalKap.g:4750:2: ( ruleIfStatement )
            {
            // InternalKap.g:4750:2: ( ruleIfStatement )
            // InternalKap.g:4751:3: ruleIfStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getIfIfStatementParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleIfStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getIfIfStatementParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__IfAssignment_2"


    // $ANTLR start "rule__Statement__GroupAssignment_3_1"
    // InternalKap.g:4760:1: rule__Statement__GroupAssignment_3_1 : ( ruleStatement ) ;
    public final void rule__Statement__GroupAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4764:1: ( ( ruleStatement ) )
            // InternalKap.g:4765:2: ( ruleStatement )
            {
            // InternalKap.g:4765:2: ( ruleStatement )
            // InternalKap.g:4766:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__GroupAssignment_3_1"


    // $ANTLR start "rule__Statement__GroupAssignment_3_2"
    // InternalKap.g:4775:1: rule__Statement__GroupAssignment_3_2 : ( ruleStatement ) ;
    public final void rule__Statement__GroupAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4779:1: ( ( ruleStatement ) )
            // InternalKap.g:4780:2: ( ruleStatement )
            {
            // InternalKap.g:4780:2: ( ruleStatement )
            // InternalKap.g:4781:3: ruleStatement
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleStatement();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getStatementAccess().getGroupStatementParserRuleCall_3_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Statement__GroupAssignment_3_2"


    // $ANTLR start "rule__IfStatement__ExpressionAssignment_1"
    // InternalKap.g:4790:1: rule__IfStatement__ExpressionAssignment_1 : ( RULE_EXPR ) ;
    public final void rule__IfStatement__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4794:1: ( ( RULE_EXPR ) )
            // InternalKap.g:4795:2: ( RULE_EXPR )
            {
            // InternalKap.g:4795:2: ( RULE_EXPR )
            // InternalKap.g:4796:3: RULE_EXPR
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getExpressionEXPRTerminalRuleCall_1_0()); 
            }
            match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getExpressionEXPRTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__ExpressionAssignment_1"


    // $ANTLR start "rule__IfStatement__BodyAssignment_2"
    // InternalKap.g:4805:1: rule__IfStatement__BodyAssignment_2 : ( ruleIfBody ) ;
    public final void rule__IfStatement__BodyAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4809:1: ( ( ruleIfBody ) )
            // InternalKap.g:4810:2: ( ruleIfBody )
            {
            // InternalKap.g:4810:2: ( ruleIfBody )
            // InternalKap.g:4811:3: ruleIfBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getBodyIfBodyParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleIfBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getBodyIfBodyParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__BodyAssignment_2"


    // $ANTLR start "rule__IfStatement__ElseIfExpressionAssignment_3_2"
    // InternalKap.g:4820:1: rule__IfStatement__ElseIfExpressionAssignment_3_2 : ( RULE_EXPR ) ;
    public final void rule__IfStatement__ElseIfExpressionAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4824:1: ( ( RULE_EXPR ) )
            // InternalKap.g:4825:2: ( RULE_EXPR )
            {
            // InternalKap.g:4825:2: ( RULE_EXPR )
            // InternalKap.g:4826:3: RULE_EXPR
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getElseIfExpressionEXPRTerminalRuleCall_3_2_0()); 
            }
            match(input,RULE_EXPR,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getElseIfExpressionEXPRTerminalRuleCall_3_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__ElseIfExpressionAssignment_3_2"


    // $ANTLR start "rule__IfStatement__ElseIfCallAssignment_3_3"
    // InternalKap.g:4835:1: rule__IfStatement__ElseIfCallAssignment_3_3 : ( ruleIfBody ) ;
    public final void rule__IfStatement__ElseIfCallAssignment_3_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4839:1: ( ( ruleIfBody ) )
            // InternalKap.g:4840:2: ( ruleIfBody )
            {
            // InternalKap.g:4840:2: ( ruleIfBody )
            // InternalKap.g:4841:3: ruleIfBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getElseIfCallIfBodyParserRuleCall_3_3_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleIfBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getElseIfCallIfBodyParserRuleCall_3_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__ElseIfCallAssignment_3_3"


    // $ANTLR start "rule__IfStatement__ElseCallAssignment_4_1"
    // InternalKap.g:4850:1: rule__IfStatement__ElseCallAssignment_4_1 : ( ruleIfBody ) ;
    public final void rule__IfStatement__ElseCallAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4854:1: ( ( ruleIfBody ) )
            // InternalKap.g:4855:2: ( ruleIfBody )
            {
            // InternalKap.g:4855:2: ( ruleIfBody )
            // InternalKap.g:4856:3: ruleIfBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfStatementAccess().getElseCallIfBodyParserRuleCall_4_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleIfBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfStatementAccess().getElseCallIfBodyParserRuleCall_4_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfStatement__ElseCallAssignment_4_1"


    // $ANTLR start "rule__IfBody__CallAssignment_0"
    // InternalKap.g:4865:1: rule__IfBody__CallAssignment_0 : ( ruleCall ) ;
    public final void rule__IfBody__CallAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4869:1: ( ( ruleCall ) )
            // InternalKap.g:4870:2: ( ruleCall )
            {
            // InternalKap.g:4870:2: ( ruleCall )
            // InternalKap.g:4871:3: ruleCall
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfBodyAccess().getCallCallParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfBodyAccess().getCallCallParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfBody__CallAssignment_0"


    // $ANTLR start "rule__IfBody__BodyAssignment_1"
    // InternalKap.g:4880:1: rule__IfBody__BodyAssignment_1 : ( ruleBody ) ;
    public final void rule__IfBody__BodyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4884:1: ( ( ruleBody ) )
            // InternalKap.g:4885:2: ( ruleBody )
            {
            // InternalKap.g:4885:2: ( ruleBody )
            // InternalKap.g:4886:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getIfBodyAccess().getBodyBodyParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getIfBodyAccess().getBodyBodyParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IfBody__BodyAssignment_1"


    // $ANTLR start "rule__Call__NameAssignment_0"
    // InternalKap.g:4895:1: rule__Call__NameAssignment_0 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Call__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4899:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:4900:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:4900:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:4901:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getNameLOWERCASE_IDTerminalRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__NameAssignment_0"


    // $ANTLR start "rule__Call__ParametersAssignment_1_1"
    // InternalKap.g:4910:1: rule__Call__ParametersAssignment_1_1 : ( ruleParameterList ) ;
    public final void rule__Call__ParametersAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4914:1: ( ( ruleParameterList ) )
            // InternalKap.g:4915:2: ( ruleParameterList )
            {
            // InternalKap.g:4915:2: ( ruleParameterList )
            // InternalKap.g:4916:3: ruleParameterList
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleParameterList();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getParametersParameterListParserRuleCall_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__ParametersAssignment_1_1"


    // $ANTLR start "rule__Call__ActionsAssignment_2_0_1"
    // InternalKap.g:4925:1: rule__Call__ActionsAssignment_2_0_1 : ( ruleActions ) ;
    public final void rule__Call__ActionsAssignment_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4929:1: ( ( ruleActions ) )
            // InternalKap.g:4930:2: ( ruleActions )
            {
            // InternalKap.g:4930:2: ( ruleActions )
            // InternalKap.g:4931:3: ruleActions
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getCallAccess().getActionsActionsParserRuleCall_2_0_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleActions();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getCallAccess().getActionsActionsParserRuleCall_2_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Call__ActionsAssignment_2_0_1"


    // $ANTLR start "rule__Actions__CallAssignment_0"
    // InternalKap.g:4940:1: rule__Actions__CallAssignment_0 : ( ruleCall ) ;
    public final void rule__Actions__CallAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4944:1: ( ( ruleCall ) )
            // InternalKap.g:4945:2: ( ruleCall )
            {
            // InternalKap.g:4945:2: ( ruleCall )
            // InternalKap.g:4946:3: ruleCall
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getCallCallParserRuleCall_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleCall();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getCallCallParserRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__CallAssignment_0"


    // $ANTLR start "rule__Actions__BodyAssignment_1"
    // InternalKap.g:4955:1: rule__Actions__BodyAssignment_1 : ( ruleBody ) ;
    public final void rule__Actions__BodyAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4959:1: ( ( ruleBody ) )
            // InternalKap.g:4960:2: ( ruleBody )
            {
            // InternalKap.g:4960:2: ( ruleBody )
            // InternalKap.g:4961:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getBodyBodyParserRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__BodyAssignment_1"


    // $ANTLR start "rule__Actions__MatchAssignment_2"
    // InternalKap.g:4970:1: rule__Actions__MatchAssignment_2 : ( ruleMatch ) ;
    public final void rule__Actions__MatchAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4974:1: ( ( ruleMatch ) )
            // InternalKap.g:4975:2: ( ruleMatch )
            {
            // InternalKap.g:4975:2: ( ruleMatch )
            // InternalKap.g:4976:3: ruleMatch
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMatch();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchMatchParserRuleCall_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__MatchAssignment_2"


    // $ANTLR start "rule__Actions__MatchesAssignment_3_1"
    // InternalKap.g:4985:1: rule__Actions__MatchesAssignment_3_1 : ( ruleMatch ) ;
    public final void rule__Actions__MatchesAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:4989:1: ( ( ruleMatch ) )
            // InternalKap.g:4990:2: ( ruleMatch )
            {
            // InternalKap.g:4990:2: ( ruleMatch )
            // InternalKap.g:4991:3: ruleMatch
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_1_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMatch();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__MatchesAssignment_3_1"


    // $ANTLR start "rule__Actions__MatchesAssignment_3_2"
    // InternalKap.g:5000:1: rule__Actions__MatchesAssignment_3_2 : ( ruleMatch ) ;
    public final void rule__Actions__MatchesAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5004:1: ( ( ruleMatch ) )
            // InternalKap.g:5005:2: ( ruleMatch )
            {
            // InternalKap.g:5005:2: ( ruleMatch )
            // InternalKap.g:5006:3: ruleMatch
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleMatch();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getActionsAccess().getMatchesMatchParserRuleCall_3_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Actions__MatchesAssignment_3_2"


    // $ANTLR start "rule__Match__IdAssignment_0_0"
    // InternalKap.g:5015:1: rule__Match__IdAssignment_0_0 : ( RULE_LOWERCASE_ID ) ;
    public final void rule__Match__IdAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5019:1: ( ( RULE_LOWERCASE_ID ) )
            // InternalKap.g:5020:2: ( RULE_LOWERCASE_ID )
            {
            // InternalKap.g:5020:2: ( RULE_LOWERCASE_ID )
            // InternalKap.g:5021:3: RULE_LOWERCASE_ID
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getIdLOWERCASE_IDTerminalRuleCall_0_0_0()); 
            }
            match(input,RULE_LOWERCASE_ID,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getIdLOWERCASE_IDTerminalRuleCall_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__IdAssignment_0_0"


    // $ANTLR start "rule__Match__BodyAssignment_0_2"
    // InternalKap.g:5030:1: rule__Match__BodyAssignment_0_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5034:1: ( ( ruleBody ) )
            // InternalKap.g:5035:2: ( ruleBody )
            {
            // InternalKap.g:5035:2: ( ruleBody )
            // InternalKap.g:5036:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_0_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_0_2"


    // $ANTLR start "rule__Match__RegexpAssignment_1_0"
    // InternalKap.g:5045:1: rule__Match__RegexpAssignment_1_0 : ( RULE_REGEXP ) ;
    public final void rule__Match__RegexpAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5049:1: ( ( RULE_REGEXP ) )
            // InternalKap.g:5050:2: ( RULE_REGEXP )
            {
            // InternalKap.g:5050:2: ( RULE_REGEXP )
            // InternalKap.g:5051:3: RULE_REGEXP
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_1_0_0()); 
            }
            match(input,RULE_REGEXP,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getRegexpREGEXPTerminalRuleCall_1_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__RegexpAssignment_1_0"


    // $ANTLR start "rule__Match__BodyAssignment_1_2"
    // InternalKap.g:5060:1: rule__Match__BodyAssignment_1_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5064:1: ( ( ruleBody ) )
            // InternalKap.g:5065:2: ( ruleBody )
            {
            // InternalKap.g:5065:2: ( ruleBody )
            // InternalKap.g:5066:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_1_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_1_2"


    // $ANTLR start "rule__Match__ObservableAssignment_2_0"
    // InternalKap.g:5075:1: rule__Match__ObservableAssignment_2_0 : ( RULE_OBSERVABLE ) ;
    public final void rule__Match__ObservableAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5079:1: ( ( RULE_OBSERVABLE ) )
            // InternalKap.g:5080:2: ( RULE_OBSERVABLE )
            {
            // InternalKap.g:5080:2: ( RULE_OBSERVABLE )
            // InternalKap.g:5081:3: RULE_OBSERVABLE
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_2_0_0()); 
            }
            match(input,RULE_OBSERVABLE,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getObservableOBSERVABLETerminalRuleCall_2_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__ObservableAssignment_2_0"


    // $ANTLR start "rule__Match__BodyAssignment_2_2"
    // InternalKap.g:5090:1: rule__Match__BodyAssignment_2_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5094:1: ( ( ruleBody ) )
            // InternalKap.g:5095:2: ( ruleBody )
            {
            // InternalKap.g:5095:2: ( ruleBody )
            // InternalKap.g:5096:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_2_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_2_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_2_2"


    // $ANTLR start "rule__Match__LiteralAssignment_3_0"
    // InternalKap.g:5105:1: rule__Match__LiteralAssignment_3_0 : ( ruleLiteral ) ;
    public final void rule__Match__LiteralAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5109:1: ( ( ruleLiteral ) )
            // InternalKap.g:5110:2: ( ruleLiteral )
            {
            // InternalKap.g:5110:2: ( ruleLiteral )
            // InternalKap.g:5111:3: ruleLiteral
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleLiteral();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getLiteralLiteralParserRuleCall_3_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__LiteralAssignment_3_0"


    // $ANTLR start "rule__Match__BodyAssignment_3_2"
    // InternalKap.g:5120:1: rule__Match__BodyAssignment_3_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5124:1: ( ( ruleBody ) )
            // InternalKap.g:5125:2: ( ruleBody )
            {
            // InternalKap.g:5125:2: ( ruleBody )
            // InternalKap.g:5126:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_3_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_3_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_3_2"


    // $ANTLR start "rule__Match__TextAssignment_4_0"
    // InternalKap.g:5135:1: rule__Match__TextAssignment_4_0 : ( RULE_STRING ) ;
    public final void rule__Match__TextAssignment_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5139:1: ( ( RULE_STRING ) )
            // InternalKap.g:5140:2: ( RULE_STRING )
            {
            // InternalKap.g:5140:2: ( RULE_STRING )
            // InternalKap.g:5141:3: RULE_STRING
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_4_0_0()); 
            }
            match(input,RULE_STRING,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getTextSTRINGTerminalRuleCall_4_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__TextAssignment_4_0"


    // $ANTLR start "rule__Match__BodyAssignment_4_2"
    // InternalKap.g:5150:1: rule__Match__BodyAssignment_4_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5154:1: ( ( ruleBody ) )
            // InternalKap.g:5155:2: ( ruleBody )
            {
            // InternalKap.g:5155:2: ( ruleBody )
            // InternalKap.g:5156:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_4_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_4_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_4_2"


    // $ANTLR start "rule__Match__ArgumentsAssignment_5_0"
    // InternalKap.g:5165:1: rule__Match__ArgumentsAssignment_5_0 : ( ruleArgumentDeclaration ) ;
    public final void rule__Match__ArgumentsAssignment_5_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5169:1: ( ( ruleArgumentDeclaration ) )
            // InternalKap.g:5170:2: ( ruleArgumentDeclaration )
            {
            // InternalKap.g:5170:2: ( ruleArgumentDeclaration )
            // InternalKap.g:5171:3: ruleArgumentDeclaration
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_5_0_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleArgumentDeclaration();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getArgumentsArgumentDeclarationParserRuleCall_5_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__ArgumentsAssignment_5_0"


    // $ANTLR start "rule__Match__BodyAssignment_5_2"
    // InternalKap.g:5180:1: rule__Match__BodyAssignment_5_2 : ( ruleBody ) ;
    public final void rule__Match__BodyAssignment_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5184:1: ( ( ruleBody ) )
            // InternalKap.g:5185:2: ( ruleBody )
            {
            // InternalKap.g:5185:2: ( ruleBody )
            // InternalKap.g:5186:3: ruleBody
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_5_2_0()); 
            }
            pushFollow(FOLLOW_2);
            ruleBody();

            state._fsp--;
            if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getMatchAccess().getBodyBodyParserRuleCall_5_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Match__BodyAssignment_5_2"


    // $ANTLR start "rule__Number__NegativeAssignment_0_1"
    // InternalKap.g:5195:1: rule__Number__NegativeAssignment_0_1 : ( ( '-' ) ) ;
    public final void rule__Number__NegativeAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5199:1: ( ( ( '-' ) ) )
            // InternalKap.g:5200:2: ( ( '-' ) )
            {
            // InternalKap.g:5200:2: ( ( '-' ) )
            // InternalKap.g:5201:3: ( '-' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0()); 
            }
            // InternalKap.g:5202:3: ( '-' )
            // InternalKap.g:5203:4: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0()); 
            }
            match(input,40,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getNegativeHyphenMinusKeyword_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__NegativeAssignment_0_1"


    // $ANTLR start "rule__Number__RealAssignment_1"
    // InternalKap.g:5214:1: rule__Number__RealAssignment_1 : ( RULE_INT ) ;
    public final void rule__Number__RealAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5218:1: ( ( RULE_INT ) )
            // InternalKap.g:5219:2: ( RULE_INT )
            {
            // InternalKap.g:5219:2: ( RULE_INT )
            // InternalKap.g:5220:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getRealINTTerminalRuleCall_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getRealINTTerminalRuleCall_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__RealAssignment_1"


    // $ANTLR start "rule__Number__LongAssignment_2"
    // InternalKap.g:5229:1: rule__Number__LongAssignment_2 : ( ( 'l' ) ) ;
    public final void rule__Number__LongAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5233:1: ( ( ( 'l' ) ) )
            // InternalKap.g:5234:2: ( ( 'l' ) )
            {
            // InternalKap.g:5234:2: ( ( 'l' ) )
            // InternalKap.g:5235:3: ( 'l' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getLongLKeyword_2_0()); 
            }
            // InternalKap.g:5236:3: ( 'l' )
            // InternalKap.g:5237:4: 'l'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getLongLKeyword_2_0()); 
            }
            match(input,43,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getLongLKeyword_2_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getLongLKeyword_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__LongAssignment_2"


    // $ANTLR start "rule__Number__DecimalAssignment_3_0_0"
    // InternalKap.g:5248:1: rule__Number__DecimalAssignment_3_0_0 : ( ( '.' ) ) ;
    public final void rule__Number__DecimalAssignment_3_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5252:1: ( ( ( '.' ) ) )
            // InternalKap.g:5253:2: ( ( '.' ) )
            {
            // InternalKap.g:5253:2: ( ( '.' ) )
            // InternalKap.g:5254:3: ( '.' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0()); 
            }
            // InternalKap.g:5255:3: ( '.' )
            // InternalKap.g:5256:4: '.'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0()); 
            }
            match(input,41,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalFullStopKeyword_3_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__DecimalAssignment_3_0_0"


    // $ANTLR start "rule__Number__DecimalPartAssignment_3_0_1"
    // InternalKap.g:5267:1: rule__Number__DecimalPartAssignment_3_0_1 : ( RULE_INT ) ;
    public final void rule__Number__DecimalPartAssignment_3_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5271:1: ( ( RULE_INT ) )
            // InternalKap.g:5272:2: ( RULE_INT )
            {
            // InternalKap.g:5272:2: ( RULE_INT )
            // InternalKap.g:5273:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getDecimalPartINTTerminalRuleCall_3_0_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getDecimalPartINTTerminalRuleCall_3_0_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__DecimalPartAssignment_3_0_1"


    // $ANTLR start "rule__Number__ExponentialAssignment_4_0_0"
    // InternalKap.g:5282:1: rule__Number__ExponentialAssignment_4_0_0 : ( ( rule__Number__ExponentialAlternatives_4_0_0_0 ) ) ;
    public final void rule__Number__ExponentialAssignment_4_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5286:1: ( ( ( rule__Number__ExponentialAlternatives_4_0_0_0 ) ) )
            // InternalKap.g:5287:2: ( ( rule__Number__ExponentialAlternatives_4_0_0_0 ) )
            {
            // InternalKap.g:5287:2: ( ( rule__Number__ExponentialAlternatives_4_0_0_0 ) )
            // InternalKap.g:5288:3: ( rule__Number__ExponentialAlternatives_4_0_0_0 )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExponentialAlternatives_4_0_0_0()); 
            }
            // InternalKap.g:5289:3: ( rule__Number__ExponentialAlternatives_4_0_0_0 )
            // InternalKap.g:5289:4: rule__Number__ExponentialAlternatives_4_0_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Number__ExponentialAlternatives_4_0_0_0();

            state._fsp--;
            if (state.failed) return ;

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExponentialAlternatives_4_0_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__ExponentialAssignment_4_0_0"


    // $ANTLR start "rule__Number__ExpNegativeAssignment_4_0_1_1"
    // InternalKap.g:5297:1: rule__Number__ExpNegativeAssignment_4_0_1_1 : ( ( '-' ) ) ;
    public final void rule__Number__ExpNegativeAssignment_4_0_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5301:1: ( ( ( '-' ) ) )
            // InternalKap.g:5302:2: ( ( '-' ) )
            {
            // InternalKap.g:5302:2: ( ( '-' ) )
            // InternalKap.g:5303:3: ( '-' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0()); 
            }
            // InternalKap.g:5304:3: ( '-' )
            // InternalKap.g:5305:4: '-'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0()); 
            }
            match(input,40,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExpNegativeHyphenMinusKeyword_4_0_1_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__ExpNegativeAssignment_4_0_1_1"


    // $ANTLR start "rule__Number__ExpAssignment_4_0_2"
    // InternalKap.g:5316:1: rule__Number__ExpAssignment_4_0_2 : ( RULE_INT ) ;
    public final void rule__Number__ExpAssignment_4_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5320:1: ( ( RULE_INT ) )
            // InternalKap.g:5321:2: ( RULE_INT )
            {
            // InternalKap.g:5321:2: ( RULE_INT )
            // InternalKap.g:5322:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getNumberAccess().getExpINTTerminalRuleCall_4_0_2_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getNumberAccess().getExpINTTerminalRuleCall_4_0_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Number__ExpAssignment_4_0_2"


    // $ANTLR start "rule__Date__YearAssignment_0"
    // InternalKap.g:5331:1: rule__Date__YearAssignment_0 : ( RULE_INT ) ;
    public final void rule__Date__YearAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5335:1: ( ( RULE_INT ) )
            // InternalKap.g:5336:2: ( RULE_INT )
            {
            // InternalKap.g:5336:2: ( RULE_INT )
            // InternalKap.g:5337:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getYearINTTerminalRuleCall_0_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getYearINTTerminalRuleCall_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__YearAssignment_0"


    // $ANTLR start "rule__Date__BcAssignment_1_2"
    // InternalKap.g:5346:1: rule__Date__BcAssignment_1_2 : ( ( 'BC' ) ) ;
    public final void rule__Date__BcAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5350:1: ( ( ( 'BC' ) ) )
            // InternalKap.g:5351:2: ( ( 'BC' ) )
            {
            // InternalKap.g:5351:2: ( ( 'BC' ) )
            // InternalKap.g:5352:3: ( 'BC' )
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getBcBCKeyword_1_2_0()); 
            }
            // InternalKap.g:5353:3: ( 'BC' )
            // InternalKap.g:5354:4: 'BC'
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getBcBCKeyword_1_2_0()); 
            }
            match(input,44,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getBcBCKeyword_1_2_0()); 
            }

            }

            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getBcBCKeyword_1_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__BcAssignment_1_2"


    // $ANTLR start "rule__Date__MonthAssignment_3"
    // InternalKap.g:5365:1: rule__Date__MonthAssignment_3 : ( RULE_INT ) ;
    public final void rule__Date__MonthAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5369:1: ( ( RULE_INT ) )
            // InternalKap.g:5370:2: ( RULE_INT )
            {
            // InternalKap.g:5370:2: ( RULE_INT )
            // InternalKap.g:5371:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMonthINTTerminalRuleCall_3_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMonthINTTerminalRuleCall_3_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__MonthAssignment_3"


    // $ANTLR start "rule__Date__DayAssignment_5"
    // InternalKap.g:5380:1: rule__Date__DayAssignment_5 : ( RULE_INT ) ;
    public final void rule__Date__DayAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5384:1: ( ( RULE_INT ) )
            // InternalKap.g:5385:2: ( RULE_INT )
            {
            // InternalKap.g:5385:2: ( RULE_INT )
            // InternalKap.g:5386:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getDayINTTerminalRuleCall_5_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getDayINTTerminalRuleCall_5_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__DayAssignment_5"


    // $ANTLR start "rule__Date__HourAssignment_6_0"
    // InternalKap.g:5395:1: rule__Date__HourAssignment_6_0 : ( RULE_INT ) ;
    public final void rule__Date__HourAssignment_6_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5399:1: ( ( RULE_INT ) )
            // InternalKap.g:5400:2: ( RULE_INT )
            {
            // InternalKap.g:5400:2: ( RULE_INT )
            // InternalKap.g:5401:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getHourINTTerminalRuleCall_6_0_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getHourINTTerminalRuleCall_6_0_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__HourAssignment_6_0"


    // $ANTLR start "rule__Date__MinAssignment_6_2"
    // InternalKap.g:5410:1: rule__Date__MinAssignment_6_2 : ( RULE_INT ) ;
    public final void rule__Date__MinAssignment_6_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5414:1: ( ( RULE_INT ) )
            // InternalKap.g:5415:2: ( RULE_INT )
            {
            // InternalKap.g:5415:2: ( RULE_INT )
            // InternalKap.g:5416:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMinINTTerminalRuleCall_6_2_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMinINTTerminalRuleCall_6_2_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__MinAssignment_6_2"


    // $ANTLR start "rule__Date__SecAssignment_6_3_1"
    // InternalKap.g:5425:1: rule__Date__SecAssignment_6_3_1 : ( RULE_INT ) ;
    public final void rule__Date__SecAssignment_6_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5429:1: ( ( RULE_INT ) )
            // InternalKap.g:5430:2: ( RULE_INT )
            {
            // InternalKap.g:5430:2: ( RULE_INT )
            // InternalKap.g:5431:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getSecINTTerminalRuleCall_6_3_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getSecINTTerminalRuleCall_6_3_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__SecAssignment_6_3_1"


    // $ANTLR start "rule__Date__MsAssignment_6_3_2_1"
    // InternalKap.g:5440:1: rule__Date__MsAssignment_6_3_2_1 : ( RULE_INT ) ;
    public final void rule__Date__MsAssignment_6_3_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalKap.g:5444:1: ( ( RULE_INT ) )
            // InternalKap.g:5445:2: ( RULE_INT )
            {
            // InternalKap.g:5445:2: ( RULE_INT )
            // InternalKap.g:5446:3: RULE_INT
            {
            if ( state.backtracking==0 ) {
               before(grammarAccess.getDateAccess().getMsINTTerminalRuleCall_6_3_2_1_0()); 
            }
            match(input,RULE_INT,FOLLOW_2); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               after(grammarAccess.getDateAccess().getMsINTTerminalRuleCall_6_3_2_1_0()); 
            }

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Date__MsAssignment_6_3_2_1"

    // $ANTLR start synpred11_InternalKap
    public final void synpred11_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:603:2: ( ( ( rule__Body__Group_0__0 ) ) )
        // InternalKap.g:603:2: ( ( rule__Body__Group_0__0 ) )
        {
        // InternalKap.g:603:2: ( ( rule__Body__Group_0__0 ) )
        // InternalKap.g:604:3: ( rule__Body__Group_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getBodyAccess().getGroup_0()); 
        }
        // InternalKap.g:605:3: ( rule__Body__Group_0__0 )
        // InternalKap.g:605:4: rule__Body__Group_0__0
        {
        pushFollow(FOLLOW_2);
        rule__Body__Group_0__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred11_InternalKap

    // $ANTLR start synpred15_InternalKap
    public final void synpred15_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:657:2: ( ( ( rule__IfBody__CallAssignment_0 ) ) )
        // InternalKap.g:657:2: ( ( rule__IfBody__CallAssignment_0 ) )
        {
        // InternalKap.g:657:2: ( ( rule__IfBody__CallAssignment_0 ) )
        // InternalKap.g:658:3: ( rule__IfBody__CallAssignment_0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getIfBodyAccess().getCallAssignment_0()); 
        }
        // InternalKap.g:659:3: ( rule__IfBody__CallAssignment_0 )
        // InternalKap.g:659:4: rule__IfBody__CallAssignment_0
        {
        pushFollow(FOLLOW_2);
        rule__IfBody__CallAssignment_0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred15_InternalKap

    // $ANTLR start synpred17_InternalKap
    public final void synpred17_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:699:2: ( ( ( rule__Actions__CallAssignment_0 ) ) )
        // InternalKap.g:699:2: ( ( rule__Actions__CallAssignment_0 ) )
        {
        // InternalKap.g:699:2: ( ( rule__Actions__CallAssignment_0 ) )
        // InternalKap.g:700:3: ( rule__Actions__CallAssignment_0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getActionsAccess().getCallAssignment_0()); 
        }
        // InternalKap.g:701:3: ( rule__Actions__CallAssignment_0 )
        // InternalKap.g:701:4: rule__Actions__CallAssignment_0
        {
        pushFollow(FOLLOW_2);
        rule__Actions__CallAssignment_0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred17_InternalKap

    // $ANTLR start synpred18_InternalKap
    public final void synpred18_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:705:2: ( ( ( rule__Actions__BodyAssignment_1 ) ) )
        // InternalKap.g:705:2: ( ( rule__Actions__BodyAssignment_1 ) )
        {
        // InternalKap.g:705:2: ( ( rule__Actions__BodyAssignment_1 ) )
        // InternalKap.g:706:3: ( rule__Actions__BodyAssignment_1 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getActionsAccess().getBodyAssignment_1()); 
        }
        // InternalKap.g:707:3: ( rule__Actions__BodyAssignment_1 )
        // InternalKap.g:707:4: rule__Actions__BodyAssignment_1
        {
        pushFollow(FOLLOW_2);
        rule__Actions__BodyAssignment_1();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred18_InternalKap

    // $ANTLR start synpred23_InternalKap
    public final void synpred23_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:750:2: ( ( ( rule__Match__Group_3__0 ) ) )
        // InternalKap.g:750:2: ( ( rule__Match__Group_3__0 ) )
        {
        // InternalKap.g:750:2: ( ( rule__Match__Group_3__0 ) )
        // InternalKap.g:751:3: ( rule__Match__Group_3__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getMatchAccess().getGroup_3()); 
        }
        // InternalKap.g:752:3: ( rule__Match__Group_3__0 )
        // InternalKap.g:752:4: rule__Match__Group_3__0
        {
        pushFollow(FOLLOW_2);
        rule__Match__Group_3__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred23_InternalKap

    // $ANTLR start synpred24_InternalKap
    public final void synpred24_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:756:2: ( ( ( rule__Match__Group_4__0 ) ) )
        // InternalKap.g:756:2: ( ( rule__Match__Group_4__0 ) )
        {
        // InternalKap.g:756:2: ( ( rule__Match__Group_4__0 ) )
        // InternalKap.g:757:3: ( rule__Match__Group_4__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getMatchAccess().getGroup_4()); 
        }
        // InternalKap.g:758:3: ( rule__Match__Group_4__0 )
        // InternalKap.g:758:4: rule__Match__Group_4__0
        {
        pushFollow(FOLLOW_2);
        rule__Match__Group_4__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred24_InternalKap

    // $ANTLR start synpred37_InternalKap
    public final void synpred37_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:1960:3: ( rule__Body__ListAssignment_0_2 )
        // InternalKap.g:1960:3: rule__Body__ListAssignment_0_2
        {
        pushFollow(FOLLOW_2);
        rule__Body__ListAssignment_0_2();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred37_InternalKap

    // $ANTLR start synpred41_InternalKap
    public final void synpred41_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:2312:3: ( rule__IfStatement__Group_3__0 )
        // InternalKap.g:2312:3: rule__IfStatement__Group_3__0
        {
        pushFollow(FOLLOW_2);
        rule__IfStatement__Group_3__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred41_InternalKap

    // $ANTLR start synpred42_InternalKap
    public final void synpred42_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:2338:3: ( rule__IfStatement__Group_4__0 )
        // InternalKap.g:2338:3: rule__IfStatement__Group_4__0
        {
        pushFollow(FOLLOW_2);
        rule__IfStatement__Group_4__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred42_InternalKap

    // $ANTLR start synpred43_InternalKap
    public final void synpred43_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:2555:3: ( rule__Call__Group_1__0 )
        // InternalKap.g:2555:3: rule__Call__Group_1__0
        {
        pushFollow(FOLLOW_2);
        rule__Call__Group_1__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred43_InternalKap

    // $ANTLR start synpred56_InternalKap
    public final void synpred56_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:4081:2: ( rule__Preamble__UnorderedGroup_2__0 )
        // InternalKap.g:4081:2: rule__Preamble__UnorderedGroup_2__0
        {
        pushFollow(FOLLOW_2);
        rule__Preamble__UnorderedGroup_2__0();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred56_InternalKap

    // $ANTLR start synpred57_InternalKap
    public final void synpred57_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:4096:3: ( ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) ) )
        // InternalKap.g:4096:3: ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) )
        {
        // InternalKap.g:4096:3: ({...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) ) )
        // InternalKap.g:4097:4: {...}? => ( ( ( rule__Preamble__Group_2_0__0 ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred57_InternalKap", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0)");
        }
        // InternalKap.g:4097:104: ( ( ( rule__Preamble__Group_2_0__0 ) ) )
        // InternalKap.g:4098:5: ( ( rule__Preamble__Group_2_0__0 ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 0);
        // InternalKap.g:4104:5: ( ( rule__Preamble__Group_2_0__0 ) )
        // InternalKap.g:4105:6: ( rule__Preamble__Group_2_0__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getPreambleAccess().getGroup_2_0()); 
        }
        // InternalKap.g:4106:6: ( rule__Preamble__Group_2_0__0 )
        // InternalKap.g:4106:7: rule__Preamble__Group_2_0__0
        {
        pushFollow(FOLLOW_2);
        rule__Preamble__Group_2_0__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred57_InternalKap

    // $ANTLR start synpred58_InternalKap
    public final void synpred58_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:4111:3: ( ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) ) )
        // InternalKap.g:4111:3: ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) )
        {
        // InternalKap.g:4111:3: ({...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) ) )
        // InternalKap.g:4112:4: {...}? => ( ( ( rule__Preamble__Group_2_1__0 ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred58_InternalKap", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1)");
        }
        // InternalKap.g:4112:104: ( ( ( rule__Preamble__Group_2_1__0 ) ) )
        // InternalKap.g:4113:5: ( ( rule__Preamble__Group_2_1__0 ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 1);
        // InternalKap.g:4119:5: ( ( rule__Preamble__Group_2_1__0 ) )
        // InternalKap.g:4120:6: ( rule__Preamble__Group_2_1__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getPreambleAccess().getGroup_2_1()); 
        }
        // InternalKap.g:4121:6: ( rule__Preamble__Group_2_1__0 )
        // InternalKap.g:4121:7: rule__Preamble__Group_2_1__0
        {
        pushFollow(FOLLOW_2);
        rule__Preamble__Group_2_1__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred58_InternalKap

    // $ANTLR start synpred59_InternalKap
    public final void synpred59_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:4126:3: ( ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) ) )
        // InternalKap.g:4126:3: ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) )
        {
        // InternalKap.g:4126:3: ({...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) ) )
        // InternalKap.g:4127:4: {...}? => ( ( ( rule__Preamble__Group_2_2__0 ) ) )
        {
        if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred59_InternalKap", "getUnorderedGroupHelper().canSelect(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2)");
        }
        // InternalKap.g:4127:104: ( ( ( rule__Preamble__Group_2_2__0 ) ) )
        // InternalKap.g:4128:5: ( ( rule__Preamble__Group_2_2__0 ) )
        {
        getUnorderedGroupHelper().select(grammarAccess.getPreambleAccess().getUnorderedGroup_2(), 2);
        // InternalKap.g:4134:5: ( ( rule__Preamble__Group_2_2__0 ) )
        // InternalKap.g:4135:6: ( rule__Preamble__Group_2_2__0 )
        {
        if ( state.backtracking==0 ) {
           before(grammarAccess.getPreambleAccess().getGroup_2_2()); 
        }
        // InternalKap.g:4136:6: ( rule__Preamble__Group_2_2__0 )
        // InternalKap.g:4136:7: rule__Preamble__Group_2_2__0
        {
        pushFollow(FOLLOW_2);
        rule__Preamble__Group_2_2__0();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }


        }
    }
    // $ANTLR end synpred59_InternalKap

    // $ANTLR start synpred60_InternalKap
    public final void synpred60_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:4170:2: ( rule__Preamble__UnorderedGroup_2__1 )
        // InternalKap.g:4170:2: rule__Preamble__UnorderedGroup_2__1
        {
        pushFollow(FOLLOW_2);
        rule__Preamble__UnorderedGroup_2__1();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred60_InternalKap

    // $ANTLR start synpred61_InternalKap
    public final void synpred61_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:4182:2: ( rule__Preamble__UnorderedGroup_2__2 )
        // InternalKap.g:4182:2: rule__Preamble__UnorderedGroup_2__2
        {
        pushFollow(FOLLOW_2);
        rule__Preamble__UnorderedGroup_2__2();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred61_InternalKap

    // $ANTLR start synpred62_InternalKap
    public final void synpred62_InternalKap_fragment() throws RecognitionException {   
        // InternalKap.g:4194:2: ( rule__Preamble__UnorderedGroup_2__3 )
        // InternalKap.g:4194:2: rule__Preamble__UnorderedGroup_2__3
        {
        pushFollow(FOLLOW_2);
        rule__Preamble__UnorderedGroup_2__3();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred62_InternalKap

    // Delegated rules

    public final boolean synpred17_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred59_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred59_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred37_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred37_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred58_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred58_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred56_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred56_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred57_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred57_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred60_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred60_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred62_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred62_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred41_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred41_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred61_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred61_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred42_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred42_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred43_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred43_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_InternalKap() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_InternalKap_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA22 dfa22 = new DFA22(this);
    protected DFA28 dfa28 = new DFA28(this);
    static final String dfa_1s = "\22\uffff";
    static final String dfa_2s = "\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\5\uffff\1\14\2\uffff\1\14";
    static final String dfa_3s = "\1\5\2\13\1\26\2\uffff\1\26\1\uffff\1\26\3\13\2\uffff\1\26\2\13\1\42";
    static final String dfa_4s = "\1\50\2\13\1\54\2\uffff\1\53\1\uffff\1\51\1\13\2\50\2\uffff\1\47\2\13\1\47";
    static final String dfa_5s = "\4\uffff\1\3\1\5\1\uffff\1\4\4\uffff\1\1\1\2\4\uffff";
    static final String dfa_6s = "\22\uffff}>";
    static final String[] dfa_7s = {
            "\1\4\5\uffff\1\3\6\uffff\2\5\1\uffff\1\1\22\uffff\1\2",
            "\1\6",
            "\1\6",
            "\1\12\1\13\2\7\10\uffff\2\14\1\15\2\uffff\1\14\1\7\1\11\1\uffff\1\10\1\7",
            "",
            "",
            "\1\12\1\13\12\uffff\2\14\1\15\2\uffff\1\14\1\uffff\1\11\1\uffff\1\10",
            "",
            "\1\12\1\13\12\uffff\2\14\1\15\2\uffff\1\14\1\uffff\1\11",
            "\1\16",
            "\1\21\11\uffff\1\17\22\uffff\1\20",
            "\1\21\11\uffff\1\17\22\uffff\1\20",
            "",
            "",
            "\1\12\1\13\12\uffff\2\14\1\15\2\uffff\1\14",
            "\1\21",
            "\1\21",
            "\2\14\1\15\2\uffff\1\14"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "538:1: rule__Literal__Alternatives : ( ( ( rule__Literal__NumberAssignment_0 ) ) | ( ( rule__Literal__Group_1__0 ) ) | ( ( rule__Literal__StringAssignment_2 ) ) | ( ( rule__Literal__DateAssignment_3 ) ) | ( ( rule__Literal__BooleanAssignment_4 ) ) );";
        }
    }
    static final String dfa_8s = "\15\uffff";
    static final String dfa_9s = "\7\uffff\1\2\2\uffff\1\2\2\uffff";
    static final String dfa_10s = "\1\4\1\47\1\uffff\1\4\2\uffff\3\4\1\uffff\3\4";
    static final String dfa_11s = "\1\50\1\47\1\uffff\1\50\2\uffff\1\47\1\50\1\45\1\uffff\1\50\1\45\1\47";
    static final String dfa_12s = "\2\uffff\1\2\1\uffff\1\3\1\1\3\uffff\1\4\3\uffff";
    static final String dfa_13s = "\1\uffff\1\0\13\uffff}>";
    static final String[] dfa_14s = {
            "\1\1\1\4\1\uffff\1\4\1\uffff\1\2\2\4\6\uffff\2\4\1\uffff\1\4\13\uffff\1\3\3\uffff\1\2\2\uffff\1\4",
            "\1\4",
            "",
            "\1\6\1\11\1\uffff\1\11\1\uffff\1\2\2\11\6\uffff\2\11\1\uffff\1\11\13\uffff\1\10\1\7\2\uffff\1\2\2\uffff\1\11",
            "",
            "",
            "\1\2\4\uffff\1\2\12\uffff\1\2\13\uffff\2\2\1\12\1\4\1\uffff\1\2\1\uffff\1\11",
            "\2\2\1\uffff\1\2\1\uffff\3\2\6\uffff\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\2\2\2\uffff\2\2\1\4\1\2",
            "\1\13\4\uffff\1\2\27\uffff\1\2\1\11\2\uffff\1\2",
            "",
            "\2\2\1\uffff\1\2\1\uffff\3\2\6\uffff\2\2\1\uffff\1\2\11\uffff\1\2\1\uffff\2\2\2\uffff\2\2\1\4\1\2",
            "\1\2\4\uffff\1\2\12\uffff\1\2\13\uffff\2\2\1\14\1\11\1\uffff\1\2",
            "\1\2\4\uffff\1\2\27\uffff\2\2\2\uffff\1\2\1\uffff\1\11"
    };

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final char[] dfa_11 = DFA.unpackEncodedStringToUnsignedChars(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[][] dfa_14 = unpackEncodedStringArray(dfa_14s);

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = dfa_8;
            this.eof = dfa_9;
            this.min = dfa_10;
            this.max = dfa_11;
            this.accept = dfa_12;
            this.special = dfa_13;
            this.transition = dfa_14;
        }
        public String getDescription() {
            return "694:1: rule__Actions__Alternatives : ( ( ( rule__Actions__CallAssignment_0 ) ) | ( ( rule__Actions__BodyAssignment_1 ) ) | ( ( rule__Actions__MatchAssignment_2 ) ) | ( ( rule__Actions__Group_3__0 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_1 = input.LA(1);

                         
                        int index9_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA9_1==39) ) {s = 4;}

                        else if ( (synpred17_InternalKap()) ) {s = 5;}

                        else if ( (synpred18_InternalKap()) ) {s = 2;}

                         
                        input.seek(index9_1);
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
    static final String dfa_15s = "\14\uffff";
    static final String dfa_16s = "\1\4\6\uffff\1\0\4\uffff";
    static final String dfa_17s = "\1\50\6\uffff\1\0\4\uffff";
    static final String dfa_18s = "\1\uffff\1\1\1\2\1\3\1\4\5\uffff\1\6\1\5";
    static final String dfa_19s = "\7\uffff\1\0\4\uffff}>";
    static final String[] dfa_20s = {
            "\1\1\1\7\1\uffff\1\3\2\uffff\1\2\1\4\6\uffff\2\4\1\uffff\1\4\13\uffff\1\12\6\uffff\1\4",
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

    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = dfa_15;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "727:1: rule__Match__Alternatives : ( ( ( rule__Match__Group_0__0 ) ) | ( ( rule__Match__Group_1__0 ) ) | ( ( rule__Match__Group_2__0 ) ) | ( ( rule__Match__Group_3__0 ) ) | ( ( rule__Match__Group_4__0 ) ) | ( ( rule__Match__Group_5__0 ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_7 = input.LA(1);

                         
                        int index10_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_InternalKap()) ) {s = 4;}

                        else if ( (synpred24_InternalKap()) ) {s = 11;}

                         
                        input.seek(index10_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_21s = "\1\1\21\uffff";
    static final String dfa_22s = "\1\4\3\uffff\4\0\12\uffff";
    static final String dfa_23s = "\1\50\3\uffff\4\0\12\uffff";
    static final String dfa_24s = "\1\uffff\1\2\17\uffff\1\1";
    static final String dfa_25s = "\4\uffff\1\0\1\1\1\2\1\3\12\uffff}>";
    static final String[] dfa_26s = {
            "\1\4\1\1\1\uffff\1\1\1\uffff\1\5\2\1\6\uffff\2\1\1\uffff\1\1\11\uffff\1\1\1\uffff\1\7\1\1\2\uffff\1\6\1\1\1\uffff\1\1",
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
            ""
    };
    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[][] dfa_26 = unpackEncodedStringArray(dfa_26s);

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = dfa_1;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "()* loopback of 1960:2: ( rule__Body__ListAssignment_0_2 )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA22_4 = input.LA(1);

                         
                        int index22_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalKap()) ) {s = 17;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index22_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA22_5 = input.LA(1);

                         
                        int index22_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalKap()) ) {s = 17;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index22_5);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA22_6 = input.LA(1);

                         
                        int index22_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalKap()) ) {s = 17;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index22_6);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA22_7 = input.LA(1);

                         
                        int index22_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred37_InternalKap()) ) {s = 17;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index22_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 22, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_27s = "\11\uffff";
    static final String dfa_28s = "\1\2\4\uffff\1\4\3\uffff";
    static final String dfa_29s = "\2\4\1\uffff\1\4\1\uffff\2\4\1\0\1\21";
    static final String dfa_30s = "\2\50\1\uffff\1\52\1\uffff\2\50\1\0\1\52";
    static final String dfa_31s = "\2\uffff\1\2\1\uffff\1\1\4\uffff";
    static final String dfa_32s = "\7\uffff\1\0\1\uffff}>";
    static final String[] dfa_33s = {
            "\2\2\1\uffff\1\2\1\uffff\3\2\6\uffff\4\2\11\uffff\2\2\1\1\1\2\2\uffff\2\2\1\uffff\1\2",
            "\1\3\4\4\1\2\1\uffff\1\4\6\uffff\2\4\1\uffff\1\4\13\uffff\1\2\1\5\2\uffff\1\2\2\uffff\1\4",
            "",
            "\1\2\4\uffff\1\2\7\uffff\1\4\2\uffff\1\2\13\uffff\2\2\1\7\1\6\1\uffff\1\2\4\uffff\1\4",
            "",
            "\2\4\1\uffff\1\4\1\uffff\3\4\6\uffff\4\4\11\uffff\4\4\2\uffff\2\4\1\2\1\4",
            "\1\10\4\4\2\uffff\1\4\6\uffff\2\4\1\uffff\1\4\22\uffff\1\4",
            "\1\uffff",
            "\1\4\20\uffff\1\5\1\6\6\uffff\1\4"
    };

    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final char[] dfa_30 = DFA.unpackEncodedStringToUnsignedChars(dfa_30s);
    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final short[][] dfa_33 = unpackEncodedStringArray(dfa_33s);

    class DFA28 extends DFA {

        public DFA28(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 28;
            this.eot = dfa_27;
            this.eof = dfa_28;
            this.min = dfa_29;
            this.max = dfa_30;
            this.accept = dfa_31;
            this.special = dfa_32;
            this.transition = dfa_33;
        }
        public String getDescription() {
            return "2555:2: ( rule__Call__Group_1__0 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA28_7 = input.LA(1);

                         
                        int index28_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred43_InternalKap()) ) {s = 4;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index28_7);
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
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000084000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000078000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000300000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000002200000210L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000400000010L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x00000100002C09F0L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000040000020000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000010000200800L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000002200000212L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000002600000210L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000300100000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x00000104002C09F0L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000122002C0EB0L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000102002C0CB0L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00000106002C0CB0L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x00000102002C0CB2L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x00000A0000C00000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000110003000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000078000002L});

}
