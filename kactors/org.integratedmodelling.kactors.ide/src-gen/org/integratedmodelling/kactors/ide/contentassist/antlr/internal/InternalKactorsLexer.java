package org.integratedmodelling.kactors.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalKactorsLexer extends Lexer {
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

    public InternalKactorsLexer() {;} 
    public InternalKactorsLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalKactorsLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalKactors.g"; }

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:11:7: ( '=' )
            // InternalKactors.g:11:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:12:7: ( 'true' )
            // InternalKactors.g:12:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:13:7: ( 'false' )
            // InternalKactors.g:13:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14:7: ( ';' )
            // InternalKactors.g:14:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:15:7: ( '+' )
            // InternalKactors.g:15:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:16:7: ( 'e' )
            // InternalKactors.g:16:9: 'e'
            {
            match('e'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:17:7: ( 'E' )
            // InternalKactors.g:17:9: 'E'
            {
            match('E'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:18:7: ( 'AD' )
            // InternalKactors.g:18:9: 'AD'
            {
            match("AD"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:19:7: ( 'CE' )
            // InternalKactors.g:19:9: 'CE'
            {
            match("CE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:20:7: ( 'name' )
            // InternalKactors.g:20:9: 'name'
            {
            match("name"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:21:7: ( 'worldview' )
            // InternalKactors.g:21:9: 'worldview'
            {
            match("worldview"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:22:7: ( 'permissions' )
            // InternalKactors.g:22:9: 'permissions'
            {
            match("permissions"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:23:7: ( 'author' )
            // InternalKactors.g:23:9: 'author'
            {
            match("author"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:24:7: ( 'version' )
            // InternalKactors.g:24:9: 'version'
            {
            match("version"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:25:7: ( 'def' )
            // InternalKactors.g:25:9: 'def'
            {
            match("def"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:26:7: ( ':' )
            // InternalKactors.g:26:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:27:7: ( '(' )
            // InternalKactors.g:27:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:28:7: ( ')' )
            // InternalKactors.g:28:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:29:7: ( ',' )
            // InternalKactors.g:29:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:30:7: ( 'to' )
            // InternalKactors.g:30:9: 'to'
            {
            match("to"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:31:7: ( 'if' )
            // InternalKactors.g:31:9: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:32:7: ( 'else' )
            // InternalKactors.g:32:9: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:33:7: ( '->' )
            // InternalKactors.g:33:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:34:7: ( '-' )
            // InternalKactors.g:34:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:35:7: ( '.' )
            // InternalKactors.g:35:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:36:7: ( '=?' )
            // InternalKactors.g:36:9: '=?'
            {
            match("=?"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:37:7: ( 'l' )
            // InternalKactors.g:37:9: 'l'
            {
            match('l'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:38:7: ( 'BC' )
            // InternalKactors.g:38:9: 'BC'
            {
            match("BC"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "RULE_LOWERCASE_ID"
    public final void mRULE_LOWERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5455:19: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )* )
            // InternalKactors.g:5455:21: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )*
            {
            matchRange('a','z'); 
            // InternalKactors.g:5455:30: ( 'a' .. 'z' | '0' .. '9' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_LOWERCASE_ID"

    // $ANTLR start "RULE_ARGVALUE"
    public final void mRULE_ARGVALUE() throws RecognitionException {
        try {
            int _type = RULE_ARGVALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5457:15: ( '$' ( '0' .. '9' )* )
            // InternalKactors.g:5457:17: '$' ( '0' .. '9' )*
            {
            match('$'); 
            // InternalKactors.g:5457:21: ( '0' .. '9' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKactors.g:5457:22: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ARGVALUE"

    // $ANTLR start "RULE_EXPR"
    public final void mRULE_EXPR() throws RecognitionException {
        try {
            int _type = RULE_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5459:11: ( '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']' )
            // InternalKactors.g:5459:13: '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']'
            {
            match('['); 
            // InternalKactors.g:5459:17: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )*
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\\') ) {
                    alt3=1;
                }
                else if ( ((LA3_0>='\u0000' && LA3_0<='[')||(LA3_0>='^' && LA3_0<='\uFFFF')) ) {
                    alt3=2;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalKactors.g:5459:18: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( (input.LA(1)>='\\' && input.LA(1)<=']')||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:5459:58: ~ ( ( '\\\\' | ']' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='[')||(input.LA(1)>='^' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_EXPR"

    // $ANTLR start "RULE_EMBEDDEDTEXT"
    public final void mRULE_EMBEDDEDTEXT() throws RecognitionException {
        try {
            int _type = RULE_EMBEDDEDTEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5461:19: ( '%%%' ( ( ' ' )* '-' )* '\\r' ( options {greedy=false; } : . )* '%%%' ( ( ' ' )* '-' )* '\\r' )
            // InternalKactors.g:5461:21: '%%%' ( ( ' ' )* '-' )* '\\r' ( options {greedy=false; } : . )* '%%%' ( ( ' ' )* '-' )* '\\r'
            {
            match("%%%"); 

            // InternalKactors.g:5461:27: ( ( ' ' )* '-' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==' '||LA5_0=='-') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalKactors.g:5461:28: ( ' ' )* '-'
            	    {
            	    // InternalKactors.g:5461:28: ( ' ' )*
            	    loop4:
            	    do {
            	        int alt4=2;
            	        int LA4_0 = input.LA(1);

            	        if ( (LA4_0==' ') ) {
            	            alt4=1;
            	        }


            	        switch (alt4) {
            	    	case 1 :
            	    	    // InternalKactors.g:5461:28: ' '
            	    	    {
            	    	    match(' '); 

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop4;
            	        }
            	    } while (true);

            	    match('-'); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match('\r'); 
            // InternalKactors.g:5461:44: ( options {greedy=false; } : . )*
            loop6:
            do {
                int alt6=2;
                alt6 = dfa6.predict(input);
                switch (alt6) {
            	case 1 :
            	    // InternalKactors.g:5461:72: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            match("%%%"); 

            // InternalKactors.g:5461:82: ( ( ' ' )* '-' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==' '||LA8_0=='-') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalKactors.g:5461:83: ( ' ' )* '-'
            	    {
            	    // InternalKactors.g:5461:83: ( ' ' )*
            	    loop7:
            	    do {
            	        int alt7=2;
            	        int LA7_0 = input.LA(1);

            	        if ( (LA7_0==' ') ) {
            	            alt7=1;
            	        }


            	        switch (alt7) {
            	    	case 1 :
            	    	    // InternalKactors.g:5461:83: ' '
            	    	    {
            	    	    match(' '); 

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop7;
            	        }
            	    } while (true);

            	    match('-'); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match('\r'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_EMBEDDEDTEXT"

    // $ANTLR start "RULE_REGEXP"
    public final void mRULE_REGEXP() throws RecognitionException {
        try {
            int _type = RULE_REGEXP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5463:13: ( '%' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )* '%' )
            // InternalKactors.g:5463:15: '%' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )* '%'
            {
            match('%'); 
            // InternalKactors.g:5463:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )*
            loop9:
            do {
                int alt9=3;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='\\') ) {
                    alt9=1;
                }
                else if ( ((LA9_0>='\u0000' && LA9_0<='$')||(LA9_0>='&' && LA9_0<='[')||(LA9_0>=']' && LA9_0<='\uFFFF')) ) {
                    alt9=2;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalKactors.g:5463:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='%'||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:5463:60: ~ ( ( '\\\\' | '%' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='$')||(input.LA(1)>='&' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_REGEXP"

    // $ANTLR start "RULE_OBSERVABLE"
    public final void mRULE_OBSERVABLE() throws RecognitionException {
        try {
            int _type = RULE_OBSERVABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5465:17: ( '{' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )* '}' )
            // InternalKactors.g:5465:19: '{' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )* '}'
            {
            match('{'); 
            // InternalKactors.g:5465:23: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )*
            loop10:
            do {
                int alt10=3;
                int LA10_0 = input.LA(1);

                if ( (LA10_0=='\\') ) {
                    alt10=1;
                }
                else if ( ((LA10_0>='\u0000' && LA10_0<='[')||(LA10_0>=']' && LA10_0<='|')||(LA10_0>='~' && LA10_0<='\uFFFF')) ) {
                    alt10=2;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalKactors.g:5465:24: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalKactors.g:5465:60: ~ ( ( '\\\\' | '}' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='|')||(input.LA(1)>='~' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_OBSERVABLE"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5467:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalKactors.g:5467:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalKactors.g:5467:11: ( '^' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='^') ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalKactors.g:5467:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKactors.g:5467:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='0' && LA12_0<='9')||(LA12_0>='A' && LA12_0<='Z')||LA12_0=='_'||(LA12_0>='a' && LA12_0<='z')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5469:10: ( ( '0' .. '9' )+ )
            // InternalKactors.g:5469:12: ( '0' .. '9' )+
            {
            // InternalKactors.g:5469:12: ( '0' .. '9' )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='0' && LA13_0<='9')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalKactors.g:5469:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5471:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalKactors.g:5471:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalKactors.g:5471:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='\"') ) {
                alt16=1;
            }
            else if ( (LA16_0=='\'') ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalKactors.g:5471:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalKactors.g:5471:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop14:
                    do {
                        int alt14=3;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0=='\\') ) {
                            alt14=1;
                        }
                        else if ( ((LA14_0>='\u0000' && LA14_0<='!')||(LA14_0>='#' && LA14_0<='[')||(LA14_0>=']' && LA14_0<='\uFFFF')) ) {
                            alt14=2;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalKactors.g:5471:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKactors.g:5471:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalKactors.g:5471:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalKactors.g:5471:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop15:
                    do {
                        int alt15=3;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0=='\\') ) {
                            alt15=1;
                        }
                        else if ( ((LA15_0>='\u0000' && LA15_0<='&')||(LA15_0>='(' && LA15_0<='[')||(LA15_0>=']' && LA15_0<='\uFFFF')) ) {
                            alt15=2;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // InternalKactors.g:5471:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKactors.g:5471:61: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5473:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalKactors.g:5473:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalKactors.g:5473:24: ( options {greedy=false; } : . )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0=='*') ) {
                    int LA17_1 = input.LA(2);

                    if ( (LA17_1=='/') ) {
                        alt17=2;
                    }
                    else if ( ((LA17_1>='\u0000' && LA17_1<='.')||(LA17_1>='0' && LA17_1<='\uFFFF')) ) {
                        alt17=1;
                    }


                }
                else if ( ((LA17_0>='\u0000' && LA17_0<=')')||(LA17_0>='+' && LA17_0<='\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalKactors.g:5473:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5475:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKactors.g:5475:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalKactors.g:5475:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='\u0000' && LA18_0<='\t')||(LA18_0>='\u000B' && LA18_0<='\f')||(LA18_0>='\u000E' && LA18_0<='\uFFFF')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalKactors.g:5475:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            // InternalKactors.g:5475:40: ( ( '\\r' )? '\\n' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='\n'||LA20_0=='\r') ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalKactors.g:5475:41: ( '\\r' )? '\\n'
                    {
                    // InternalKactors.g:5475:41: ( '\\r' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='\r') ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalKactors.g:5475:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5477:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKactors.g:5477:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKactors.g:5477:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt21=0;
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>='\t' && LA21_0<='\n')||LA21_0=='\r'||LA21_0==' ') ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt21 >= 1 ) break loop21;
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:5479:16: ( . )
            // InternalKactors.g:5479:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalKactors.g:1:8: ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | RULE_LOWERCASE_ID | RULE_ARGVALUE | RULE_EXPR | RULE_EMBEDDEDTEXT | RULE_REGEXP | RULE_OBSERVABLE | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt22=41;
        alt22 = dfa22.predict(input);
        switch (alt22) {
            case 1 :
                // InternalKactors.g:1:10: T__17
                {
                mT__17(); 

                }
                break;
            case 2 :
                // InternalKactors.g:1:16: T__18
                {
                mT__18(); 

                }
                break;
            case 3 :
                // InternalKactors.g:1:22: T__19
                {
                mT__19(); 

                }
                break;
            case 4 :
                // InternalKactors.g:1:28: T__20
                {
                mT__20(); 

                }
                break;
            case 5 :
                // InternalKactors.g:1:34: T__21
                {
                mT__21(); 

                }
                break;
            case 6 :
                // InternalKactors.g:1:40: T__22
                {
                mT__22(); 

                }
                break;
            case 7 :
                // InternalKactors.g:1:46: T__23
                {
                mT__23(); 

                }
                break;
            case 8 :
                // InternalKactors.g:1:52: T__24
                {
                mT__24(); 

                }
                break;
            case 9 :
                // InternalKactors.g:1:58: T__25
                {
                mT__25(); 

                }
                break;
            case 10 :
                // InternalKactors.g:1:64: T__26
                {
                mT__26(); 

                }
                break;
            case 11 :
                // InternalKactors.g:1:70: T__27
                {
                mT__27(); 

                }
                break;
            case 12 :
                // InternalKactors.g:1:76: T__28
                {
                mT__28(); 

                }
                break;
            case 13 :
                // InternalKactors.g:1:82: T__29
                {
                mT__29(); 

                }
                break;
            case 14 :
                // InternalKactors.g:1:88: T__30
                {
                mT__30(); 

                }
                break;
            case 15 :
                // InternalKactors.g:1:94: T__31
                {
                mT__31(); 

                }
                break;
            case 16 :
                // InternalKactors.g:1:100: T__32
                {
                mT__32(); 

                }
                break;
            case 17 :
                // InternalKactors.g:1:106: T__33
                {
                mT__33(); 

                }
                break;
            case 18 :
                // InternalKactors.g:1:112: T__34
                {
                mT__34(); 

                }
                break;
            case 19 :
                // InternalKactors.g:1:118: T__35
                {
                mT__35(); 

                }
                break;
            case 20 :
                // InternalKactors.g:1:124: T__36
                {
                mT__36(); 

                }
                break;
            case 21 :
                // InternalKactors.g:1:130: T__37
                {
                mT__37(); 

                }
                break;
            case 22 :
                // InternalKactors.g:1:136: T__38
                {
                mT__38(); 

                }
                break;
            case 23 :
                // InternalKactors.g:1:142: T__39
                {
                mT__39(); 

                }
                break;
            case 24 :
                // InternalKactors.g:1:148: T__40
                {
                mT__40(); 

                }
                break;
            case 25 :
                // InternalKactors.g:1:154: T__41
                {
                mT__41(); 

                }
                break;
            case 26 :
                // InternalKactors.g:1:160: T__42
                {
                mT__42(); 

                }
                break;
            case 27 :
                // InternalKactors.g:1:166: T__43
                {
                mT__43(); 

                }
                break;
            case 28 :
                // InternalKactors.g:1:172: T__44
                {
                mT__44(); 

                }
                break;
            case 29 :
                // InternalKactors.g:1:178: RULE_LOWERCASE_ID
                {
                mRULE_LOWERCASE_ID(); 

                }
                break;
            case 30 :
                // InternalKactors.g:1:196: RULE_ARGVALUE
                {
                mRULE_ARGVALUE(); 

                }
                break;
            case 31 :
                // InternalKactors.g:1:210: RULE_EXPR
                {
                mRULE_EXPR(); 

                }
                break;
            case 32 :
                // InternalKactors.g:1:220: RULE_EMBEDDEDTEXT
                {
                mRULE_EMBEDDEDTEXT(); 

                }
                break;
            case 33 :
                // InternalKactors.g:1:238: RULE_REGEXP
                {
                mRULE_REGEXP(); 

                }
                break;
            case 34 :
                // InternalKactors.g:1:250: RULE_OBSERVABLE
                {
                mRULE_OBSERVABLE(); 

                }
                break;
            case 35 :
                // InternalKactors.g:1:266: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 36 :
                // InternalKactors.g:1:274: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 37 :
                // InternalKactors.g:1:283: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 38 :
                // InternalKactors.g:1:295: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 39 :
                // InternalKactors.g:1:311: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 40 :
                // InternalKactors.g:1:327: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 41 :
                // InternalKactors.g:1:335: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA22 dfa22 = new DFA22(this);
    static final String DFA6_eotS =
        "\10\uffff";
    static final String DFA6_eofS =
        "\10\uffff";
    static final String DFA6_minS =
        "\2\0\1\uffff\4\0\1\uffff";
    static final String DFA6_maxS =
        "\2\uffff\1\uffff\4\uffff\1\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\1\4\uffff\1\2";
    static final String DFA6_specialS =
        "\1\0\1\5\1\uffff\1\3\1\4\1\1\1\2\1\uffff}>";
    static final String[] DFA6_transitionS = {
            "\45\2\1\1\uffda\2",
            "\45\2\1\3\uffda\2",
            "",
            "\45\2\1\4\uffda\2",
            "\15\2\1\7\22\2\1\5\14\2\1\6\uffd2\2",
            "\40\2\1\5\14\2\1\6\uffd2\2",
            "\15\2\1\7\22\2\1\5\14\2\1\6\uffd2\2",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "()* loopback of 5461:44: ( options {greedy=false; } : . )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_0 = input.LA(1);

                        s = -1;
                        if ( (LA6_0=='%') ) {s = 1;}

                        else if ( ((LA6_0>='\u0000' && LA6_0<='$')||(LA6_0>='&' && LA6_0<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA6_5 = input.LA(1);

                        s = -1;
                        if ( (LA6_5=='-') ) {s = 6;}

                        else if ( (LA6_5==' ') ) {s = 5;}

                        else if ( ((LA6_5>='\u0000' && LA6_5<='\u001F')||(LA6_5>='!' && LA6_5<=',')||(LA6_5>='.' && LA6_5<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA6_6 = input.LA(1);

                        s = -1;
                        if ( ((LA6_6>='\u0000' && LA6_6<='\f')||(LA6_6>='\u000E' && LA6_6<='\u001F')||(LA6_6>='!' && LA6_6<=',')||(LA6_6>='.' && LA6_6<='\uFFFF')) ) {s = 2;}

                        else if ( (LA6_6=='\r') ) {s = 7;}

                        else if ( (LA6_6==' ') ) {s = 5;}

                        else if ( (LA6_6=='-') ) {s = 6;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA6_3 = input.LA(1);

                        s = -1;
                        if ( (LA6_3=='%') ) {s = 4;}

                        else if ( ((LA6_3>='\u0000' && LA6_3<='$')||(LA6_3>='&' && LA6_3<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA6_4 = input.LA(1);

                        s = -1;
                        if ( ((LA6_4>='\u0000' && LA6_4<='\f')||(LA6_4>='\u000E' && LA6_4<='\u001F')||(LA6_4>='!' && LA6_4<=',')||(LA6_4>='.' && LA6_4<='\uFFFF')) ) {s = 2;}

                        else if ( (LA6_4==' ') ) {s = 5;}

                        else if ( (LA6_4=='-') ) {s = 6;}

                        else if ( (LA6_4=='\r') ) {s = 7;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA6_1 = input.LA(1);

                        s = -1;
                        if ( (LA6_1=='%') ) {s = 3;}

                        else if ( ((LA6_1>='\u0000' && LA6_1<='$')||(LA6_1>='&' && LA6_1<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 6, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA22_eotS =
        "\1\uffff\1\47\2\52\2\uffff\1\61\1\62\2\54\6\52\4\uffff\1\52\1\101\1\uffff\1\103\1\54\1\52\1\uffff\4\45\2\uffff\3\45\4\uffff\1\52\1\120\1\uffff\1\52\1\uffff\1\52\2\uffff\1\52\2\uffff\1\123\1\124\6\52\4\uffff\1\133\4\uffff\1\134\2\uffff\1\110\7\uffff\1\52\1\uffff\2\52\2\uffff\5\52\1\146\3\uffff\1\147\1\52\1\151\1\152\4\52\2\uffff\1\157\2\uffff\4\52\1\uffff\2\52\1\166\3\52\1\uffff\1\172\2\52\1\uffff\1\175\1\52\1\uffff\1\52\1\u0080\1\uffff";
    static final String DFA22_eofS =
        "\u0081\uffff";
    static final String DFA22_minS =
        "\1\0\1\77\2\60\2\uffff\2\60\1\104\1\105\6\60\4\uffff\1\60\1\76\1\uffff\1\60\1\103\1\60\1\uffff\3\0\1\101\2\uffff\2\0\1\52\4\uffff\2\60\1\uffff\1\60\1\uffff\1\60\2\uffff\1\60\2\uffff\10\60\4\uffff\1\60\4\uffff\1\60\2\uffff\1\45\7\uffff\1\60\1\uffff\2\60\2\uffff\6\60\3\uffff\10\60\2\uffff\1\60\2\uffff\4\60\1\uffff\6\60\1\uffff\3\60\1\uffff\2\60\1\uffff\2\60\1\uffff";
    static final String DFA22_maxS =
        "\1\uffff\1\77\2\172\2\uffff\2\172\1\104\1\105\6\172\4\uffff\1\172\1\76\1\uffff\1\172\1\103\1\172\1\uffff\3\uffff\1\172\2\uffff\2\uffff\1\57\4\uffff\2\172\1\uffff\1\172\1\uffff\1\172\2\uffff\1\172\2\uffff\10\172\4\uffff\1\172\4\uffff\1\172\2\uffff\1\45\7\uffff\1\172\1\uffff\2\172\2\uffff\6\172\3\uffff\10\172\2\uffff\1\172\2\uffff\4\172\1\uffff\6\172\1\uffff\3\172\1\uffff\2\172\1\uffff\2\172\1\uffff";
    static final String DFA22_acceptS =
        "\4\uffff\1\4\1\5\12\uffff\1\20\1\21\1\22\1\23\2\uffff\1\31\3\uffff\1\36\4\uffff\1\43\1\44\3\uffff\1\50\1\51\1\32\1\1\2\uffff\1\35\1\uffff\1\43\1\uffff\1\4\1\5\1\uffff\1\6\1\7\10\uffff\1\20\1\21\1\22\1\23\1\uffff\1\27\1\30\1\31\1\33\1\uffff\1\36\1\37\1\uffff\1\41\1\42\1\44\1\45\1\46\1\47\1\50\1\uffff\1\24\2\uffff\1\10\1\11\6\uffff\1\25\1\34\1\40\10\uffff\1\17\1\2\1\uffff\1\26\1\12\4\uffff\1\3\6\uffff\1\15\3\uffff\1\16\2\uffff\1\13\2\uffff\1\14";
    static final String DFA22_specialS =
        "\1\5\32\uffff\1\0\1\2\1\3\3\uffff\1\1\1\4\136\uffff}>";
    static final String[] DFA22_transitionS = {
            "\11\45\2\44\2\45\1\44\22\45\1\44\1\45\1\41\1\45\1\32\1\34\1\45\1\42\1\21\1\22\1\45\1\5\1\23\1\25\1\26\1\43\12\40\1\20\1\4\1\45\1\1\3\45\1\10\1\30\1\11\1\37\1\7\25\37\1\33\2\45\1\36\1\37\1\45\1\15\2\31\1\17\1\6\1\3\2\31\1\24\2\31\1\27\1\31\1\12\1\31\1\14\3\31\1\2\1\31\1\16\1\13\3\31\1\35\uff84\45",
            "\1\46",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\16\53\1\51\2\53\1\50\10\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\1\55\31\53",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\13\53\1\60\16\53",
            "\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
            "\1\63",
            "\1\64",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\1\65\31\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\16\53\1\66\13\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\4\53\1\67\25\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\24\53\1\70\5\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\4\53\1\71\25\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\4\53\1\72\25\53",
            "",
            "",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\5\53\1\77\24\53",
            "\1\100",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "\1\104",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "",
            "\0\106",
            "\45\110\1\107\uffda\110",
            "\0\111",
            "\32\54\4\uffff\1\54\1\uffff\32\54",
            "",
            "",
            "\0\113",
            "\0\113",
            "\1\114\4\uffff\1\115",
            "",
            "",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\24\53\1\117\5\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\13\53\1\121\16\53",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\22\53\1\122\7\53",
            "",
            "",
            "\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
            "\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\14\53\1\125\15\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\21\53\1\126\10\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\21\53\1\127\10\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\23\53\1\130\6\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\21\53\1\131\10\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\5\53\1\132\24\53",
            "",
            "",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "",
            "",
            "\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
            "",
            "",
            "\1\135",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\4\53\1\136\25\53",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\22\53\1\137\7\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\4\53\1\140\25\53",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\4\53\1\141\25\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\13\53\1\142\16\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\14\53\1\143\15\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\7\53\1\144\22\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\22\53\1\145\7\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\4\53\1\150\25\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\3\53\1\153\26\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\10\53\1\154\21\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\16\53\1\155\13\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\10\53\1\156\21\53",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\25\53\1\160\4\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\22\53\1\161\7\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\21\53\1\162\10\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\16\53\1\163\13\53",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\10\53\1\164\21\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\22\53\1\165\7\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\15\53\1\167\14\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\4\53\1\170\25\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\10\53\1\171\21\53",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\26\53\1\173\3\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\16\53\1\174\13\53",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\15\53\1\176\14\53",
            "",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\22\53\1\177\7\53",
            "\12\53\7\uffff\32\54\4\uffff\1\53\1\uffff\32\53",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | RULE_LOWERCASE_ID | RULE_ARGVALUE | RULE_EXPR | RULE_EMBEDDEDTEXT | RULE_REGEXP | RULE_OBSERVABLE | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA22_27 = input.LA(1);

                        s = -1;
                        if ( ((LA22_27>='\u0000' && LA22_27<='\uFFFF')) ) {s = 70;}

                        else s = 37;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA22_33 = input.LA(1);

                        s = -1;
                        if ( ((LA22_33>='\u0000' && LA22_33<='\uFFFF')) ) {s = 75;}

                        else s = 37;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA22_28 = input.LA(1);

                        s = -1;
                        if ( (LA22_28=='%') ) {s = 71;}

                        else if ( ((LA22_28>='\u0000' && LA22_28<='$')||(LA22_28>='&' && LA22_28<='\uFFFF')) ) {s = 72;}

                        else s = 37;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA22_29 = input.LA(1);

                        s = -1;
                        if ( ((LA22_29>='\u0000' && LA22_29<='\uFFFF')) ) {s = 73;}

                        else s = 37;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA22_34 = input.LA(1);

                        s = -1;
                        if ( ((LA22_34>='\u0000' && LA22_34<='\uFFFF')) ) {s = 75;}

                        else s = 37;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA22_0 = input.LA(1);

                        s = -1;
                        if ( (LA22_0=='=') ) {s = 1;}

                        else if ( (LA22_0=='t') ) {s = 2;}

                        else if ( (LA22_0=='f') ) {s = 3;}

                        else if ( (LA22_0==';') ) {s = 4;}

                        else if ( (LA22_0=='+') ) {s = 5;}

                        else if ( (LA22_0=='e') ) {s = 6;}

                        else if ( (LA22_0=='E') ) {s = 7;}

                        else if ( (LA22_0=='A') ) {s = 8;}

                        else if ( (LA22_0=='C') ) {s = 9;}

                        else if ( (LA22_0=='n') ) {s = 10;}

                        else if ( (LA22_0=='w') ) {s = 11;}

                        else if ( (LA22_0=='p') ) {s = 12;}

                        else if ( (LA22_0=='a') ) {s = 13;}

                        else if ( (LA22_0=='v') ) {s = 14;}

                        else if ( (LA22_0=='d') ) {s = 15;}

                        else if ( (LA22_0==':') ) {s = 16;}

                        else if ( (LA22_0=='(') ) {s = 17;}

                        else if ( (LA22_0==')') ) {s = 18;}

                        else if ( (LA22_0==',') ) {s = 19;}

                        else if ( (LA22_0=='i') ) {s = 20;}

                        else if ( (LA22_0=='-') ) {s = 21;}

                        else if ( (LA22_0=='.') ) {s = 22;}

                        else if ( (LA22_0=='l') ) {s = 23;}

                        else if ( (LA22_0=='B') ) {s = 24;}

                        else if ( ((LA22_0>='b' && LA22_0<='c')||(LA22_0>='g' && LA22_0<='h')||(LA22_0>='j' && LA22_0<='k')||LA22_0=='m'||LA22_0=='o'||(LA22_0>='q' && LA22_0<='s')||LA22_0=='u'||(LA22_0>='x' && LA22_0<='z')) ) {s = 25;}

                        else if ( (LA22_0=='$') ) {s = 26;}

                        else if ( (LA22_0=='[') ) {s = 27;}

                        else if ( (LA22_0=='%') ) {s = 28;}

                        else if ( (LA22_0=='{') ) {s = 29;}

                        else if ( (LA22_0=='^') ) {s = 30;}

                        else if ( (LA22_0=='D'||(LA22_0>='F' && LA22_0<='Z')||LA22_0=='_') ) {s = 31;}

                        else if ( ((LA22_0>='0' && LA22_0<='9')) ) {s = 32;}

                        else if ( (LA22_0=='\"') ) {s = 33;}

                        else if ( (LA22_0=='\'') ) {s = 34;}

                        else if ( (LA22_0=='/') ) {s = 35;}

                        else if ( ((LA22_0>='\t' && LA22_0<='\n')||LA22_0=='\r'||LA22_0==' ') ) {s = 36;}

                        else if ( ((LA22_0>='\u0000' && LA22_0<='\b')||(LA22_0>='\u000B' && LA22_0<='\f')||(LA22_0>='\u000E' && LA22_0<='\u001F')||LA22_0=='!'||LA22_0=='#'||LA22_0=='&'||LA22_0=='*'||LA22_0=='<'||(LA22_0>='>' && LA22_0<='@')||(LA22_0>='\\' && LA22_0<=']')||LA22_0=='`'||(LA22_0>='|' && LA22_0<='\uFFFF')) ) {s = 37;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 22, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}