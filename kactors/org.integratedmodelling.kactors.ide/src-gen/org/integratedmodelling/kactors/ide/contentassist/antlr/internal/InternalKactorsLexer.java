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
    public static final int T__50=50;
    public static final int RULE_EMBEDDEDTEXT=12;
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
    public static final int RULE_ID=5;
    public static final int RULE_CAMELCASE_ID=8;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=7;
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
    public static final int RULE_ARGVALUE=16;
    public static final int RULE_STRING=6;
    public static final int RULE_SEPARATOR=10;
    public static final int RULE_SL_COMMENT=19;
    public static final int RULE_OBSERVABLE=11;
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
    public static final int RULE_REGEXP=14;
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
    public static final int RULE_WS=20;
    public static final int RULE_ANY_OTHER=21;
    public static final int RULE_ANNOTATION_ID=15;
    public static final int T__48=48;
    public static final int RULE_LOWERCASE_ID=4;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int RULE_EXPR=13;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int RULE_UPPERCASE_PATH=17;
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

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:11:7: ( ';' )
            // InternalKactors.g:11:9: ';'
            {
            match(';'); 

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
            // InternalKactors.g:12:7: ( 'exclusive' )
            // InternalKactors.g:12:9: 'exclusive'
            {
            match("exclusive"); 


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
            // InternalKactors.g:13:7: ( '.' )
            // InternalKactors.g:13:9: '.'
            {
            match('.'); 

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
            // InternalKactors.g:14:7: ( 'true' )
            // InternalKactors.g:14:9: 'true'
            {
            match("true"); 


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
            // InternalKactors.g:15:7: ( 'false' )
            // InternalKactors.g:15:9: 'false'
            {
            match("false"); 


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
            // InternalKactors.g:16:7: ( '=' )
            // InternalKactors.g:16:9: '='
            {
            match('='); 

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
            // InternalKactors.g:17:7: ( '+' )
            // InternalKactors.g:17:9: '+'
            {
            match('+'); 

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
            // InternalKactors.g:18:7: ( 'e' )
            // InternalKactors.g:18:9: 'e'
            {
            match('e'); 

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
            // InternalKactors.g:19:7: ( 'E' )
            // InternalKactors.g:19:9: 'E'
            {
            match('E'); 

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
            // InternalKactors.g:20:7: ( 'AD' )
            // InternalKactors.g:20:9: 'AD'
            {
            match("AD"); 


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
            // InternalKactors.g:21:7: ( 'CE' )
            // InternalKactors.g:21:9: 'CE'
            {
            match("CE"); 


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
            // InternalKactors.g:22:7: ( '/' )
            // InternalKactors.g:22:9: '/'
            {
            match('/'); 

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
            // InternalKactors.g:23:7: ( '^' )
            // InternalKactors.g:23:9: '^'
            {
            match('^'); 

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
            // InternalKactors.g:24:7: ( '*' )
            // InternalKactors.g:24:9: '*'
            {
            match('*'); 

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
            // InternalKactors.g:25:7: ( 'import' )
            // InternalKactors.g:25:9: 'import'
            {
            match("import"); 


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
            // InternalKactors.g:26:7: ( ',' )
            // InternalKactors.g:26:9: ','
            {
            match(','); 

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
            // InternalKactors.g:27:7: ( 'worldview' )
            // InternalKactors.g:27:9: 'worldview'
            {
            match("worldview"); 


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
            // InternalKactors.g:28:7: ( 'observable' )
            // InternalKactors.g:28:9: 'observable'
            {
            match("observable"); 


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
            // InternalKactors.g:29:7: ( 'label' )
            // InternalKactors.g:29:9: 'label'
            {
            match("label"); 


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
            // InternalKactors.g:30:7: ( 'description' )
            // InternalKactors.g:30:9: 'description'
            {
            match("description"); 


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
            // InternalKactors.g:31:7: ( 'permissions' )
            // InternalKactors.g:31:9: 'permissions'
            {
            match("permissions"); 


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
            // InternalKactors.g:32:7: ( 'author' )
            // InternalKactors.g:32:9: 'author'
            {
            match("author"); 


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
            // InternalKactors.g:33:7: ( 'version' )
            // InternalKactors.g:33:9: 'version'
            {
            match("version"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:34:7: ( 'created' )
            // InternalKactors.g:34:9: 'created'
            {
            match("created"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:35:7: ( 'modified' )
            // InternalKactors.g:35:9: 'modified'
            {
            match("modified"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:36:7: ( 'message' )
            // InternalKactors.g:36:9: 'message'
            {
            match("message"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:37:7: ( ':' )
            // InternalKactors.g:37:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:38:7: ( '(' )
            // InternalKactors.g:38:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:39:7: ( ')' )
            // InternalKactors.g:39:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:40:7: ( 'if' )
            // InternalKactors.g:40:9: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:41:7: ( 'else' )
            // InternalKactors.g:41:9: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:42:7: ( 'while' )
            // InternalKactors.g:42:9: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:43:7: ( 'do' )
            // InternalKactors.g:43:9: 'do'
            {
            match("do"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:44:7: ( 'for' )
            // InternalKactors.g:44:9: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:45:7: ( 'in' )
            // InternalKactors.g:45:9: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:46:7: ( '->' )
            // InternalKactors.g:46:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:47:7: ( 'to' )
            // InternalKactors.g:47:9: 'to'
            {
            match("to"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:48:7: ( 'urn:klab:' )
            // InternalKactors.g:48:9: 'urn:klab:'
            {
            match("urn:klab:"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:49:7: ( '#' )
            // InternalKactors.g:49:9: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:50:7: ( '&' )
            // InternalKactors.g:50:9: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:51:7: ( '{' )
            // InternalKactors.g:51:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:52:7: ( '}' )
            // InternalKactors.g:52:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:53:7: ( '{{' )
            // InternalKactors.g:53:9: '{{'
            {
            match("{{"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:54:7: ( '}}' )
            // InternalKactors.g:54:9: '}}'
            {
            match("}}"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:55:7: ( '|' )
            // InternalKactors.g:55:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:56:7: ( '@' )
            // InternalKactors.g:56:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:57:7: ( '-' )
            // InternalKactors.g:57:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:58:7: ( 'app' )
            // InternalKactors.g:58:9: 'app'
            {
            match("app"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:59:7: ( 'user' )
            // InternalKactors.g:59:9: 'user'
            {
            match("user"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:60:7: ( 'lib' )
            // InternalKactors.g:60:9: 'lib'
            {
            match("lib"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:61:7: ( 'actor' )
            // InternalKactors.g:61:9: 'actor'
            {
            match("actor"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:62:7: ( 'inclusive' )
            // InternalKactors.g:62:9: 'inclusive'
            {
            match("inclusive"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:63:7: ( 'unknown' )
            // InternalKactors.g:63:9: 'unknown'
            {
            match("unknown"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:64:7: ( '=?' )
            // InternalKactors.g:64:9: '=?'
            {
            match("=?"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:65:7: ( '>' )
            // InternalKactors.g:65:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:66:7: ( '<' )
            // InternalKactors.g:66:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:67:7: ( '!=' )
            // InternalKactors.g:67:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:68:7: ( '<=' )
            // InternalKactors.g:68:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:69:7: ( '>=' )
            // InternalKactors.g:69:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:70:7: ( 'l' )
            // InternalKactors.g:70:9: 'l'
            {
            match('l'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:71:7: ( 'BC' )
            // InternalKactors.g:71:9: 'BC'
            {
            match("BC"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "RULE_UPPERCASE_ID"
    public final void mRULE_UPPERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_UPPERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14091:19: ( 'A' .. 'Z' ( 'A' .. 'Z' | '_' )* )
            // InternalKactors.g:14091:21: 'A' .. 'Z' ( 'A' .. 'Z' | '_' )*
            {
            matchRange('A','Z'); 
            // InternalKactors.g:14091:30: ( 'A' .. 'Z' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='A' && LA1_0<='Z')||LA1_0=='_') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_' ) {
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
    // $ANTLR end "RULE_UPPERCASE_ID"

    // $ANTLR start "RULE_UPPERCASE_PATH"
    public final void mRULE_UPPERCASE_PATH() throws RecognitionException {
        try {
            int _type = RULE_UPPERCASE_PATH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14093:21: ( RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )* )
            // InternalKactors.g:14093:23: RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )*
            {
            mRULE_UPPERCASE_ID(); 
            // InternalKactors.g:14093:41: ( '.' RULE_UPPERCASE_ID )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='.') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKactors.g:14093:42: '.' RULE_UPPERCASE_ID
            	    {
            	    match('.'); 
            	    mRULE_UPPERCASE_ID(); 

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
    // $ANTLR end "RULE_UPPERCASE_PATH"

    // $ANTLR start "RULE_CAMELCASE_ID"
    public final void mRULE_CAMELCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_CAMELCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14095:19: ( 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )* )
            // InternalKactors.g:14095:21: 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )*
            {
            matchRange('A','Z'); 
            // InternalKactors.g:14095:30: ( 'A' .. 'z' | '0' .. '9' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalKactors.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='z') ) {
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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_CAMELCASE_ID"

    // $ANTLR start "RULE_LOWERCASE_ID"
    public final void mRULE_LOWERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14097:19: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )* )
            // InternalKactors.g:14097:21: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )*
            {
            matchRange('a','z'); 
            // InternalKactors.g:14097:30: ( 'a' .. 'z' | '0' .. '9' | '_' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
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
            	    break loop4;
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
            // InternalKactors.g:14099:15: ( '$' ( '0' .. '9' )* )
            // InternalKactors.g:14099:17: '$' ( '0' .. '9' )*
            {
            match('$'); 
            // InternalKactors.g:14099:21: ( '0' .. '9' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalKactors.g:14099:22: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    break loop5;
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
            // InternalKactors.g:14101:11: ( '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']' )
            // InternalKactors.g:14101:13: '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']'
            {
            match('['); 
            // InternalKactors.g:14101:17: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )*
            loop6:
            do {
                int alt6=3;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\\') ) {
                    alt6=1;
                }
                else if ( ((LA6_0>='\u0000' && LA6_0<='[')||(LA6_0>='^' && LA6_0<='\uFFFF')) ) {
                    alt6=2;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalKactors.g:14101:18: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' )
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
            	    // InternalKactors.g:14101:58: ~ ( ( '\\\\' | ']' ) )
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
            	    break loop6;
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
            // InternalKactors.g:14103:19: ( '%%%' ( ( ' ' )* '-' )* '\\r' ( options {greedy=false; } : . )* '%%%' ( ( ' ' )* '-' )* )
            // InternalKactors.g:14103:21: '%%%' ( ( ' ' )* '-' )* '\\r' ( options {greedy=false; } : . )* '%%%' ( ( ' ' )* '-' )*
            {
            match("%%%"); 

            // InternalKactors.g:14103:27: ( ( ' ' )* '-' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==' '||LA8_0=='-') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalKactors.g:14103:28: ( ' ' )* '-'
            	    {
            	    // InternalKactors.g:14103:28: ( ' ' )*
            	    loop7:
            	    do {
            	        int alt7=2;
            	        int LA7_0 = input.LA(1);

            	        if ( (LA7_0==' ') ) {
            	            alt7=1;
            	        }


            	        switch (alt7) {
            	    	case 1 :
            	    	    // InternalKactors.g:14103:28: ' '
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
            // InternalKactors.g:14103:44: ( options {greedy=false; } : . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='%') ) {
                    int LA9_1 = input.LA(2);

                    if ( (LA9_1=='%') ) {
                        int LA9_3 = input.LA(3);

                        if ( (LA9_3=='%') ) {
                            alt9=2;
                        }
                        else if ( ((LA9_3>='\u0000' && LA9_3<='$')||(LA9_3>='&' && LA9_3<='\uFFFF')) ) {
                            alt9=1;
                        }


                    }
                    else if ( ((LA9_1>='\u0000' && LA9_1<='$')||(LA9_1>='&' && LA9_1<='\uFFFF')) ) {
                        alt9=1;
                    }


                }
                else if ( ((LA9_0>='\u0000' && LA9_0<='$')||(LA9_0>='&' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalKactors.g:14103:72: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match("%%%"); 

            // InternalKactors.g:14103:82: ( ( ' ' )* '-' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==' '||LA11_0=='-') ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalKactors.g:14103:83: ( ' ' )* '-'
            	    {
            	    // InternalKactors.g:14103:83: ( ' ' )*
            	    loop10:
            	    do {
            	        int alt10=2;
            	        int LA10_0 = input.LA(1);

            	        if ( (LA10_0==' ') ) {
            	            alt10=1;
            	        }


            	        switch (alt10) {
            	    	case 1 :
            	    	    // InternalKactors.g:14103:83: ' '
            	    	    {
            	    	    match(' '); 

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop10;
            	        }
            	    } while (true);

            	    match('-'); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


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
            // InternalKactors.g:14105:13: ( '%' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )* '%' )
            // InternalKactors.g:14105:15: '%' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )* '%'
            {
            match('%'); 
            // InternalKactors.g:14105:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' ) | ~ ( ( '\\\\' | '%' ) ) )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='\\') ) {
                    alt12=1;
                }
                else if ( ((LA12_0>='\u0000' && LA12_0<='$')||(LA12_0>='&' && LA12_0<='[')||(LA12_0>=']' && LA12_0<='\uFFFF')) ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalKactors.g:14105:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '%' | '\\\\' )
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
            	    // InternalKactors.g:14105:60: ~ ( ( '\\\\' | '%' ) )
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
            	    break loop12;
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
            // InternalKactors.g:14107:17: ( '{' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )* '}' )
            // InternalKactors.g:14107:19: '{' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )* '}'
            {
            match('{'); 
            // InternalKactors.g:14107:23: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' ) | ~ ( ( '\\\\' | '}' ) ) )*
            loop13:
            do {
                int alt13=3;
                int LA13_0 = input.LA(1);

                if ( (LA13_0=='\\') ) {
                    alt13=1;
                }
                else if ( ((LA13_0>='\u0000' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='|')||(LA13_0>='~' && LA13_0<='\uFFFF')) ) {
                    alt13=2;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalKactors.g:14107:24: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\\\\' )
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
            	    // InternalKactors.g:14107:60: ~ ( ( '\\\\' | '}' ) )
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
            	    break loop13;
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

    // $ANTLR start "RULE_SEPARATOR"
    public final void mRULE_SEPARATOR() throws RecognitionException {
        try {
            int _type = RULE_SEPARATOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14109:16: ( '---' ( '-' )* )
            // InternalKactors.g:14109:18: '---' ( '-' )*
            {
            match("---"); 

            // InternalKactors.g:14109:24: ( '-' )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0=='-') ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalKactors.g:14109:24: '-'
            	    {
            	    match('-'); 

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SEPARATOR"

    // $ANTLR start "RULE_ANNOTATION_ID"
    public final void mRULE_ANNOTATION_ID() throws RecognitionException {
        try {
            int _type = RULE_ANNOTATION_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14111:20: ( '@' RULE_LOWERCASE_ID )
            // InternalKactors.g:14111:22: '@' RULE_LOWERCASE_ID
            {
            match('@'); 
            mRULE_LOWERCASE_ID(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANNOTATION_ID"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKactors.g:14113:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalKactors.g:14113:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalKactors.g:14113:11: ( '^' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='^') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalKactors.g:14113:11: '^'
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

            // InternalKactors.g:14113:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='Z')||LA16_0=='_'||(LA16_0>='a' && LA16_0<='z')) ) {
                    alt16=1;
                }


                switch (alt16) {
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
            	    break loop16;
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
            // InternalKactors.g:14115:10: ( ( '0' .. '9' )+ )
            // InternalKactors.g:14115:12: ( '0' .. '9' )+
            {
            // InternalKactors.g:14115:12: ( '0' .. '9' )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalKactors.g:14115:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
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
            // InternalKactors.g:14117:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalKactors.g:14117:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalKactors.g:14117:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='\"') ) {
                alt20=1;
            }
            else if ( (LA20_0=='\'') ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // InternalKactors.g:14117:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalKactors.g:14117:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop18:
                    do {
                        int alt18=3;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0=='\\') ) {
                            alt18=1;
                        }
                        else if ( ((LA18_0>='\u0000' && LA18_0<='!')||(LA18_0>='#' && LA18_0<='[')||(LA18_0>=']' && LA18_0<='\uFFFF')) ) {
                            alt18=2;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // InternalKactors.g:14117:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKactors.g:14117:28: ~ ( ( '\\\\' | '\"' ) )
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
                    	    break loop18;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalKactors.g:14117:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalKactors.g:14117:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop19:
                    do {
                        int alt19=3;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0=='\\') ) {
                            alt19=1;
                        }
                        else if ( ((LA19_0>='\u0000' && LA19_0<='&')||(LA19_0>='(' && LA19_0<='[')||(LA19_0>=']' && LA19_0<='\uFFFF')) ) {
                            alt19=2;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // InternalKactors.g:14117:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKactors.g:14117:61: ~ ( ( '\\\\' | '\\'' ) )
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
                    	    break loop19;
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
            // InternalKactors.g:14119:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalKactors.g:14119:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalKactors.g:14119:24: ( options {greedy=false; } : . )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0=='*') ) {
                    int LA21_1 = input.LA(2);

                    if ( (LA21_1=='/') ) {
                        alt21=2;
                    }
                    else if ( ((LA21_1>='\u0000' && LA21_1<='.')||(LA21_1>='0' && LA21_1<='\uFFFF')) ) {
                        alt21=1;
                    }


                }
                else if ( ((LA21_0>='\u0000' && LA21_0<=')')||(LA21_0>='+' && LA21_0<='\uFFFF')) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalKactors.g:14119:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop21;
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
            // InternalKactors.g:14121:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKactors.g:14121:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalKactors.g:14121:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='\u0000' && LA22_0<='\t')||(LA22_0>='\u000B' && LA22_0<='\f')||(LA22_0>='\u000E' && LA22_0<='\uFFFF')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalKactors.g:14121:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop22;
                }
            } while (true);

            // InternalKactors.g:14121:40: ( ( '\\r' )? '\\n' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0=='\n'||LA24_0=='\r') ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalKactors.g:14121:41: ( '\\r' )? '\\n'
                    {
                    // InternalKactors.g:14121:41: ( '\\r' )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0=='\r') ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // InternalKactors.g:14121:41: '\\r'
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
            // InternalKactors.g:14123:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKactors.g:14123:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKactors.g:14123:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>='\t' && LA25_0<='\n')||LA25_0=='\r'||LA25_0==' ') ) {
                    alt25=1;
                }


                switch (alt25) {
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
            	    if ( cnt25 >= 1 ) break loop25;
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
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
            // InternalKactors.g:14125:16: ( . )
            // InternalKactors.g:14125:18: .
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
        // InternalKactors.g:1:8: ( T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_LOWERCASE_ID | RULE_ARGVALUE | RULE_EXPR | RULE_EMBEDDEDTEXT | RULE_REGEXP | RULE_OBSERVABLE | RULE_SEPARATOR | RULE_ANNOTATION_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt26=79;
        alt26 = dfa26.predict(input);
        switch (alt26) {
            case 1 :
                // InternalKactors.g:1:10: T__22
                {
                mT__22(); 

                }
                break;
            case 2 :
                // InternalKactors.g:1:16: T__23
                {
                mT__23(); 

                }
                break;
            case 3 :
                // InternalKactors.g:1:22: T__24
                {
                mT__24(); 

                }
                break;
            case 4 :
                // InternalKactors.g:1:28: T__25
                {
                mT__25(); 

                }
                break;
            case 5 :
                // InternalKactors.g:1:34: T__26
                {
                mT__26(); 

                }
                break;
            case 6 :
                // InternalKactors.g:1:40: T__27
                {
                mT__27(); 

                }
                break;
            case 7 :
                // InternalKactors.g:1:46: T__28
                {
                mT__28(); 

                }
                break;
            case 8 :
                // InternalKactors.g:1:52: T__29
                {
                mT__29(); 

                }
                break;
            case 9 :
                // InternalKactors.g:1:58: T__30
                {
                mT__30(); 

                }
                break;
            case 10 :
                // InternalKactors.g:1:64: T__31
                {
                mT__31(); 

                }
                break;
            case 11 :
                // InternalKactors.g:1:70: T__32
                {
                mT__32(); 

                }
                break;
            case 12 :
                // InternalKactors.g:1:76: T__33
                {
                mT__33(); 

                }
                break;
            case 13 :
                // InternalKactors.g:1:82: T__34
                {
                mT__34(); 

                }
                break;
            case 14 :
                // InternalKactors.g:1:88: T__35
                {
                mT__35(); 

                }
                break;
            case 15 :
                // InternalKactors.g:1:94: T__36
                {
                mT__36(); 

                }
                break;
            case 16 :
                // InternalKactors.g:1:100: T__37
                {
                mT__37(); 

                }
                break;
            case 17 :
                // InternalKactors.g:1:106: T__38
                {
                mT__38(); 

                }
                break;
            case 18 :
                // InternalKactors.g:1:112: T__39
                {
                mT__39(); 

                }
                break;
            case 19 :
                // InternalKactors.g:1:118: T__40
                {
                mT__40(); 

                }
                break;
            case 20 :
                // InternalKactors.g:1:124: T__41
                {
                mT__41(); 

                }
                break;
            case 21 :
                // InternalKactors.g:1:130: T__42
                {
                mT__42(); 

                }
                break;
            case 22 :
                // InternalKactors.g:1:136: T__43
                {
                mT__43(); 

                }
                break;
            case 23 :
                // InternalKactors.g:1:142: T__44
                {
                mT__44(); 

                }
                break;
            case 24 :
                // InternalKactors.g:1:148: T__45
                {
                mT__45(); 

                }
                break;
            case 25 :
                // InternalKactors.g:1:154: T__46
                {
                mT__46(); 

                }
                break;
            case 26 :
                // InternalKactors.g:1:160: T__47
                {
                mT__47(); 

                }
                break;
            case 27 :
                // InternalKactors.g:1:166: T__48
                {
                mT__48(); 

                }
                break;
            case 28 :
                // InternalKactors.g:1:172: T__49
                {
                mT__49(); 

                }
                break;
            case 29 :
                // InternalKactors.g:1:178: T__50
                {
                mT__50(); 

                }
                break;
            case 30 :
                // InternalKactors.g:1:184: T__51
                {
                mT__51(); 

                }
                break;
            case 31 :
                // InternalKactors.g:1:190: T__52
                {
                mT__52(); 

                }
                break;
            case 32 :
                // InternalKactors.g:1:196: T__53
                {
                mT__53(); 

                }
                break;
            case 33 :
                // InternalKactors.g:1:202: T__54
                {
                mT__54(); 

                }
                break;
            case 34 :
                // InternalKactors.g:1:208: T__55
                {
                mT__55(); 

                }
                break;
            case 35 :
                // InternalKactors.g:1:214: T__56
                {
                mT__56(); 

                }
                break;
            case 36 :
                // InternalKactors.g:1:220: T__57
                {
                mT__57(); 

                }
                break;
            case 37 :
                // InternalKactors.g:1:226: T__58
                {
                mT__58(); 

                }
                break;
            case 38 :
                // InternalKactors.g:1:232: T__59
                {
                mT__59(); 

                }
                break;
            case 39 :
                // InternalKactors.g:1:238: T__60
                {
                mT__60(); 

                }
                break;
            case 40 :
                // InternalKactors.g:1:244: T__61
                {
                mT__61(); 

                }
                break;
            case 41 :
                // InternalKactors.g:1:250: T__62
                {
                mT__62(); 

                }
                break;
            case 42 :
                // InternalKactors.g:1:256: T__63
                {
                mT__63(); 

                }
                break;
            case 43 :
                // InternalKactors.g:1:262: T__64
                {
                mT__64(); 

                }
                break;
            case 44 :
                // InternalKactors.g:1:268: T__65
                {
                mT__65(); 

                }
                break;
            case 45 :
                // InternalKactors.g:1:274: T__66
                {
                mT__66(); 

                }
                break;
            case 46 :
                // InternalKactors.g:1:280: T__67
                {
                mT__67(); 

                }
                break;
            case 47 :
                // InternalKactors.g:1:286: T__68
                {
                mT__68(); 

                }
                break;
            case 48 :
                // InternalKactors.g:1:292: T__69
                {
                mT__69(); 

                }
                break;
            case 49 :
                // InternalKactors.g:1:298: T__70
                {
                mT__70(); 

                }
                break;
            case 50 :
                // InternalKactors.g:1:304: T__71
                {
                mT__71(); 

                }
                break;
            case 51 :
                // InternalKactors.g:1:310: T__72
                {
                mT__72(); 

                }
                break;
            case 52 :
                // InternalKactors.g:1:316: T__73
                {
                mT__73(); 

                }
                break;
            case 53 :
                // InternalKactors.g:1:322: T__74
                {
                mT__74(); 

                }
                break;
            case 54 :
                // InternalKactors.g:1:328: T__75
                {
                mT__75(); 

                }
                break;
            case 55 :
                // InternalKactors.g:1:334: T__76
                {
                mT__76(); 

                }
                break;
            case 56 :
                // InternalKactors.g:1:340: T__77
                {
                mT__77(); 

                }
                break;
            case 57 :
                // InternalKactors.g:1:346: T__78
                {
                mT__78(); 

                }
                break;
            case 58 :
                // InternalKactors.g:1:352: T__79
                {
                mT__79(); 

                }
                break;
            case 59 :
                // InternalKactors.g:1:358: T__80
                {
                mT__80(); 

                }
                break;
            case 60 :
                // InternalKactors.g:1:364: T__81
                {
                mT__81(); 

                }
                break;
            case 61 :
                // InternalKactors.g:1:370: T__82
                {
                mT__82(); 

                }
                break;
            case 62 :
                // InternalKactors.g:1:376: RULE_UPPERCASE_ID
                {
                mRULE_UPPERCASE_ID(); 

                }
                break;
            case 63 :
                // InternalKactors.g:1:394: RULE_UPPERCASE_PATH
                {
                mRULE_UPPERCASE_PATH(); 

                }
                break;
            case 64 :
                // InternalKactors.g:1:414: RULE_CAMELCASE_ID
                {
                mRULE_CAMELCASE_ID(); 

                }
                break;
            case 65 :
                // InternalKactors.g:1:432: RULE_LOWERCASE_ID
                {
                mRULE_LOWERCASE_ID(); 

                }
                break;
            case 66 :
                // InternalKactors.g:1:450: RULE_ARGVALUE
                {
                mRULE_ARGVALUE(); 

                }
                break;
            case 67 :
                // InternalKactors.g:1:464: RULE_EXPR
                {
                mRULE_EXPR(); 

                }
                break;
            case 68 :
                // InternalKactors.g:1:474: RULE_EMBEDDEDTEXT
                {
                mRULE_EMBEDDEDTEXT(); 

                }
                break;
            case 69 :
                // InternalKactors.g:1:492: RULE_REGEXP
                {
                mRULE_REGEXP(); 

                }
                break;
            case 70 :
                // InternalKactors.g:1:504: RULE_OBSERVABLE
                {
                mRULE_OBSERVABLE(); 

                }
                break;
            case 71 :
                // InternalKactors.g:1:520: RULE_SEPARATOR
                {
                mRULE_SEPARATOR(); 

                }
                break;
            case 72 :
                // InternalKactors.g:1:535: RULE_ANNOTATION_ID
                {
                mRULE_ANNOTATION_ID(); 

                }
                break;
            case 73 :
                // InternalKactors.g:1:554: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 74 :
                // InternalKactors.g:1:562: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 75 :
                // InternalKactors.g:1:571: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 76 :
                // InternalKactors.g:1:583: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 77 :
                // InternalKactors.g:1:599: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 78 :
                // InternalKactors.g:1:615: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 79 :
                // InternalKactors.g:1:623: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA26 dfa26 = new DFA26(this);
    static final String DFA26_eotS =
        "\2\uffff\1\66\1\uffff\2\74\1\100\1\uffff\1\102\2\110\1\114\1\115\1\uffff\1\74\1\uffff\2\74\1\130\6\74\3\uffff\1\150\1\74\2\uffff\1\157\1\162\1\uffff\1\165\1\167\1\171\1\62\2\110\1\74\1\uffff\2\62\2\uffff\2\62\3\uffff\2\74\1\uffff\1\74\2\uffff\1\74\1\u0086\1\uffff\2\74\4\uffff\1\110\1\uffff\1\106\1\uffff\1\u0089\1\uffff\1\u008a\5\uffff\1\74\1\u008c\1\u008e\1\uffff\5\74\1\uffff\1\74\1\u0095\10\74\6\uffff\3\74\2\uffff\1\u00a1\14\uffff\1\u00a2\2\uffff\1\177\4\uffff\3\74\1\uffff\1\74\1\u00a8\2\uffff\1\74\1\uffff\1\74\1\uffff\4\74\1\u00af\1\74\1\uffff\2\74\1\u00b3\10\74\3\uffff\1\74\1\u00bd\1\u00be\1\74\1\uffff\6\74\1\uffff\3\74\1\uffff\5\74\1\uffff\1\u00ce\2\74\2\uffff\1\u00d1\3\74\1\u00d5\1\74\1\u00d7\3\74\1\u00db\4\74\1\uffff\2\74\1\uffff\1\u00e2\2\74\1\uffff\1\74\1\uffff\2\74\1\u00e8\1\uffff\6\74\1\uffff\5\74\1\uffff\1\u00f4\1\u00f5\1\74\1\u00f7\1\u00f8\6\74\2\uffff\1\u00ff\2\uffff\1\u0100\1\u0101\1\u0102\3\74\4\uffff\1\u0106\2\74\1\uffff\1\u0109\1\u010a\2\uffff";
    static final String DFA26_eofS =
        "\u010b\uffff";
    static final String DFA26_minS =
        "\1\0\1\uffff\1\60\1\uffff\2\60\1\77\1\uffff\3\56\1\52\1\101\1\uffff\1\60\1\uffff\11\60\3\uffff\1\55\1\60\2\uffff\1\0\1\175\1\uffff\1\141\3\75\2\56\1\60\1\uffff\2\0\2\uffff\2\0\3\uffff\2\60\1\uffff\1\60\2\uffff\2\60\1\uffff\2\60\4\uffff\1\56\1\uffff\1\60\1\uffff\1\56\1\uffff\1\56\5\uffff\3\60\1\uffff\5\60\1\uffff\12\60\6\uffff\3\60\2\uffff\1\0\14\uffff\1\56\2\uffff\1\45\4\uffff\3\60\1\uffff\2\60\2\uffff\1\60\1\uffff\1\60\1\uffff\6\60\1\uffff\13\60\3\uffff\4\60\1\uffff\6\60\1\uffff\3\60\1\uffff\5\60\1\uffff\3\60\2\uffff\17\60\1\uffff\2\60\1\uffff\3\60\1\uffff\1\60\1\uffff\3\60\1\uffff\6\60\1\uffff\5\60\1\uffff\13\60\2\uffff\1\60\2\uffff\6\60\4\uffff\3\60\1\uffff\2\60\2\uffff";
    static final String DFA26_maxS =
        "\1\uffff\1\uffff\1\172\1\uffff\2\172\1\77\1\uffff\3\172\1\57\1\172\1\uffff\1\172\1\uffff\11\172\3\uffff\1\76\1\172\2\uffff\1\uffff\1\175\1\uffff\1\172\3\75\3\172\1\uffff\2\uffff\2\uffff\2\uffff\3\uffff\2\172\1\uffff\1\172\2\uffff\2\172\1\uffff\2\172\4\uffff\1\172\1\uffff\1\172\1\uffff\1\172\1\uffff\1\172\5\uffff\3\172\1\uffff\5\172\1\uffff\12\172\6\uffff\3\172\2\uffff\1\uffff\14\uffff\1\172\2\uffff\1\45\4\uffff\3\172\1\uffff\2\172\2\uffff\1\172\1\uffff\1\172\1\uffff\6\172\1\uffff\13\172\3\uffff\4\172\1\uffff\6\172\1\uffff\3\172\1\uffff\5\172\1\uffff\3\172\2\uffff\17\172\1\uffff\2\172\1\uffff\3\172\1\uffff\1\172\1\uffff\3\172\1\uffff\6\172\1\uffff\5\172\1\uffff\13\172\2\uffff\1\172\2\uffff\6\172\4\uffff\3\172\1\uffff\2\172\2\uffff";
    static final String DFA26_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\3\uffff\1\7\5\uffff\1\16\1\uffff\1\20\11\uffff\1\33\1\34\1\35\2\uffff\1\47\1\50\2\uffff\1\55\7\uffff\1\102\2\uffff\1\111\1\112\2\uffff\1\116\1\117\1\1\2\uffff\1\10\1\uffff\1\111\1\3\2\uffff\1\101\2\uffff\1\66\1\6\1\7\1\11\1\uffff\1\77\1\uffff\1\100\1\uffff\1\76\1\uffff\1\114\1\115\1\14\1\15\1\16\3\uffff\1\20\5\uffff\1\74\12\uffff\1\33\1\34\1\35\1\44\1\107\1\57\3\uffff\1\47\1\50\1\uffff\1\51\1\106\1\54\1\52\1\55\1\110\1\56\1\73\1\67\1\72\1\70\1\71\1\uffff\1\102\1\103\1\uffff\1\105\1\112\1\113\1\116\3\uffff\1\45\2\uffff\1\12\1\13\1\uffff\1\36\1\uffff\1\43\6\uffff\1\41\13\uffff\1\53\1\75\1\104\4\uffff\1\42\6\uffff\1\62\3\uffff\1\60\5\uffff\1\46\3\uffff\1\37\1\4\17\uffff\1\61\2\uffff\1\5\3\uffff\1\40\1\uffff\1\23\3\uffff\1\63\6\uffff\1\17\5\uffff\1\26\13\uffff\1\27\1\30\1\uffff\1\32\1\65\6\uffff\1\31\1\2\1\64\1\21\3\uffff\1\22\2\uffff\1\24\1\25";
    static final String DFA26_specialS =
        "\1\6\37\uffff\1\3\12\uffff\1\0\1\1\2\uffff\1\2\1\4\75\uffff\1\5\u009c\uffff}>";
    static final String[] DFA26_transitionS = {
            "\11\62\2\61\2\62\1\61\22\62\1\61\1\46\1\57\1\36\1\52\1\54\1\37\1\60\1\32\1\33\1\15\1\7\1\17\1\34\1\3\1\13\12\56\1\31\1\1\1\45\1\6\1\44\1\62\1\43\1\11\1\47\1\12\1\50\1\10\25\50\1\53\2\62\1\14\1\55\1\62\1\25\1\51\1\27\1\23\1\2\1\5\2\51\1\16\2\51\1\22\1\30\1\51\1\21\1\24\3\51\1\4\1\35\1\26\1\20\3\51\1\40\1\42\1\41\uff82\62",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\13\67\1\65\13\67\1\64\2\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\16\67\1\73\2\67\1\72\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\75\15\67\1\76\13\67",
            "\1\77",
            "",
            "\1\104\1\uffff\12\105\7\uffff\32\103\4\106\1\103\1\106\32\105",
            "\1\104\1\uffff\12\105\7\uffff\3\103\1\107\26\103\4\106\1\103\1\106\32\105",
            "\1\104\1\uffff\12\105\7\uffff\4\103\1\111\25\103\4\106\1\103\1\106\32\105",
            "\1\112\4\uffff\1\113",
            "\32\70\4\uffff\1\70\1\uffff\32\70",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\5\67\1\120\6\67\1\117\1\121\14\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\7\67\1\124\6\67\1\123\13\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\67\1\125\30\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\126\7\67\1\127\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\131\11\67\1\132\13\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\133\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\2\67\1\136\14\67\1\135\4\67\1\134\5\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\137\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\140\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\142\11\67\1\141\13\67",
            "",
            "",
            "",
            "\1\147\20\uffff\1\146",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\15\67\1\153\3\67\1\151\1\152\7\67",
            "",
            "",
            "\173\160\1\156\uff84\160",
            "\1\161",
            "",
            "\32\164",
            "\1\166",
            "\1\170",
            "\1\172",
            "\1\104\1\uffff\12\105\7\uffff\2\103\1\173\27\103\4\106\1\103\1\106\32\105",
            "\1\104\1\uffff\12\105\7\uffff\32\103\4\106\1\103\1\106\32\105",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "",
            "\0\175",
            "\45\177\1\176\uffda\177",
            "",
            "",
            "\0\u0081",
            "\0\u0081",
            "",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\2\67\1\u0083\27\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u0084\7\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\24\67\1\u0085\5\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\13\67\1\u0087\16\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u0088\10\67",
            "",
            "",
            "",
            "",
            "\1\104\1\uffff\12\105\7\uffff\32\103\4\106\1\103\1\106\32\105",
            "",
            "\12\105\7\uffff\32\105\4\uffff\1\105\1\uffff\32\105",
            "",
            "\1\104\1\uffff\12\105\7\uffff\32\103\4\106\1\103\1\106\32\105",
            "",
            "\1\104\1\uffff\12\105\7\uffff\32\103\4\106\1\103\1\106\32\105",
            "",
            "",
            "",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\17\67\1\u008b\12\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\2\67\1\u008d\27\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u008f\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u0090\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u0091\7\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\67\1\u0092\30\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\67\1\u0093\30\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u0094\7\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u0096\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\23\67\1\u0097\6\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\17\67\1\u0098\12\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\23\67\1\u0099\6\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u009a\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u009b\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\3\67\1\u009c\26\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u009d\7\67",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\15\67\1\u009e\14\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u009f\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\12\67\1\u00a0\17\67",
            "",
            "",
            "\0\160",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\104\1\uffff\12\105\7\uffff\32\103\4\106\1\103\1\106\32\105",
            "",
            "",
            "\1\u00a3",
            "",
            "",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\13\67\1\u00a4\16\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00a5\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00a6\25\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u00a7\7\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\16\67\1\u00a9\13\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\13\67\1\u00aa\16\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\13\67\1\u00ab\16\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\13\67\1\u00ac\16\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00ad\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00ae\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\2\67\1\u00b0\27\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\14\67\1\u00b1\15\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\7\67\1\u00b2\22\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\16\67\1\u00b4\13\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u00b5\7\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\u00b6\31\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00b7\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u00b8\7\67",
            "\12\67\1\u00b9\6\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u00ba\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\15\67\1\u00bb\14\67",
            "",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\24\67\1\u00bc\5\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00bf\25\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u00c0\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\24\67\1\u00c1\5\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\3\67\1\u00c2\26\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00c3\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u00c4\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\13\67\1\u00c5\16\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u00c6\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00c7\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\16\67\1\u00c8\13\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u00c9\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00ca\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\23\67\1\u00cb\6\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\5\67\1\u00cc\24\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\u00cd\31\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\16\67\1\u00cf\13\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u00d0\7\67",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\23\67\1\u00d2\6\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u00d3\7\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\25\67\1\u00d4\4\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\25\67\1\u00d6\4\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00d8\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u00d9\7\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\21\67\1\u00da\10\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\16\67\1\u00dc\13\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00dd\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00de\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\6\67\1\u00df\23\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\26\67\1\u00e0\3\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00e1\21\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00e3\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00e4\21\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\u00e5\31\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\17\67\1\u00e6\12\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u00e7\7\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\15\67\1\u00e9\14\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\3\67\1\u00ea\26\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00eb\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00ec\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\15\67\1\u00ed\14\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\25\67\1\u00ee\4\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\25\67\1\u00ef\4\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00f0\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\1\67\1\u00f1\30\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\23\67\1\u00f2\6\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00f3\21\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\3\67\1\u00f6\26\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00f9\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u00fa\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\26\67\1\u00fb\3\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\13\67\1\u00fc\16\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\10\67\1\u00fd\21\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\16\67\1\u00fe\13\67",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\4\67\1\u0103\25\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\16\67\1\u0104\13\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\15\67\1\u0105\14\67",
            "",
            "",
            "",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\15\67\1\u0107\14\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\22\67\1\u0108\7\67",
            "",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "\12\67\7\uffff\32\70\4\uffff\1\67\1\uffff\32\67",
            "",
            ""
    };

    static final short[] DFA26_eot = DFA.unpackEncodedString(DFA26_eotS);
    static final short[] DFA26_eof = DFA.unpackEncodedString(DFA26_eofS);
    static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars(DFA26_minS);
    static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars(DFA26_maxS);
    static final short[] DFA26_accept = DFA.unpackEncodedString(DFA26_acceptS);
    static final short[] DFA26_special = DFA.unpackEncodedString(DFA26_specialS);
    static final short[][] DFA26_transition;

    static {
        int numStates = DFA26_transitionS.length;
        DFA26_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = DFA26_eot;
            this.eof = DFA26_eof;
            this.min = DFA26_min;
            this.max = DFA26_max;
            this.accept = DFA26_accept;
            this.special = DFA26_special;
            this.transition = DFA26_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_LOWERCASE_ID | RULE_ARGVALUE | RULE_EXPR | RULE_EMBEDDEDTEXT | RULE_REGEXP | RULE_OBSERVABLE | RULE_SEPARATOR | RULE_ANNOTATION_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_43 = input.LA(1);

                        s = -1;
                        if ( ((LA26_43>='\u0000' && LA26_43<='\uFFFF')) ) {s = 125;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_44 = input.LA(1);

                        s = -1;
                        if ( (LA26_44=='%') ) {s = 126;}

                        else if ( ((LA26_44>='\u0000' && LA26_44<='$')||(LA26_44>='&' && LA26_44<='\uFFFF')) ) {s = 127;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA26_47 = input.LA(1);

                        s = -1;
                        if ( ((LA26_47>='\u0000' && LA26_47<='\uFFFF')) ) {s = 129;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA26_32 = input.LA(1);

                        s = -1;
                        if ( (LA26_32=='{') ) {s = 110;}

                        else if ( ((LA26_32>='\u0000' && LA26_32<='z')||(LA26_32>='|' && LA26_32<='\uFFFF')) ) {s = 112;}

                        else s = 111;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA26_48 = input.LA(1);

                        s = -1;
                        if ( ((LA26_48>='\u0000' && LA26_48<='\uFFFF')) ) {s = 129;}

                        else s = 50;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA26_110 = input.LA(1);

                        s = -1;
                        if ( ((LA26_110>='\u0000' && LA26_110<='\uFFFF')) ) {s = 112;}

                        else s = 161;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA26_0 = input.LA(1);

                        s = -1;
                        if ( (LA26_0==';') ) {s = 1;}

                        else if ( (LA26_0=='e') ) {s = 2;}

                        else if ( (LA26_0=='.') ) {s = 3;}

                        else if ( (LA26_0=='t') ) {s = 4;}

                        else if ( (LA26_0=='f') ) {s = 5;}

                        else if ( (LA26_0=='=') ) {s = 6;}

                        else if ( (LA26_0=='+') ) {s = 7;}

                        else if ( (LA26_0=='E') ) {s = 8;}

                        else if ( (LA26_0=='A') ) {s = 9;}

                        else if ( (LA26_0=='C') ) {s = 10;}

                        else if ( (LA26_0=='/') ) {s = 11;}

                        else if ( (LA26_0=='^') ) {s = 12;}

                        else if ( (LA26_0=='*') ) {s = 13;}

                        else if ( (LA26_0=='i') ) {s = 14;}

                        else if ( (LA26_0==',') ) {s = 15;}

                        else if ( (LA26_0=='w') ) {s = 16;}

                        else if ( (LA26_0=='o') ) {s = 17;}

                        else if ( (LA26_0=='l') ) {s = 18;}

                        else if ( (LA26_0=='d') ) {s = 19;}

                        else if ( (LA26_0=='p') ) {s = 20;}

                        else if ( (LA26_0=='a') ) {s = 21;}

                        else if ( (LA26_0=='v') ) {s = 22;}

                        else if ( (LA26_0=='c') ) {s = 23;}

                        else if ( (LA26_0=='m') ) {s = 24;}

                        else if ( (LA26_0==':') ) {s = 25;}

                        else if ( (LA26_0=='(') ) {s = 26;}

                        else if ( (LA26_0==')') ) {s = 27;}

                        else if ( (LA26_0=='-') ) {s = 28;}

                        else if ( (LA26_0=='u') ) {s = 29;}

                        else if ( (LA26_0=='#') ) {s = 30;}

                        else if ( (LA26_0=='&') ) {s = 31;}

                        else if ( (LA26_0=='{') ) {s = 32;}

                        else if ( (LA26_0=='}') ) {s = 33;}

                        else if ( (LA26_0=='|') ) {s = 34;}

                        else if ( (LA26_0=='@') ) {s = 35;}

                        else if ( (LA26_0=='>') ) {s = 36;}

                        else if ( (LA26_0=='<') ) {s = 37;}

                        else if ( (LA26_0=='!') ) {s = 38;}

                        else if ( (LA26_0=='B') ) {s = 39;}

                        else if ( (LA26_0=='D'||(LA26_0>='F' && LA26_0<='Z')) ) {s = 40;}

                        else if ( (LA26_0=='b'||(LA26_0>='g' && LA26_0<='h')||(LA26_0>='j' && LA26_0<='k')||LA26_0=='n'||(LA26_0>='q' && LA26_0<='s')||(LA26_0>='x' && LA26_0<='z')) ) {s = 41;}

                        else if ( (LA26_0=='$') ) {s = 42;}

                        else if ( (LA26_0=='[') ) {s = 43;}

                        else if ( (LA26_0=='%') ) {s = 44;}

                        else if ( (LA26_0=='_') ) {s = 45;}

                        else if ( ((LA26_0>='0' && LA26_0<='9')) ) {s = 46;}

                        else if ( (LA26_0=='\"') ) {s = 47;}

                        else if ( (LA26_0=='\'') ) {s = 48;}

                        else if ( ((LA26_0>='\t' && LA26_0<='\n')||LA26_0=='\r'||LA26_0==' ') ) {s = 49;}

                        else if ( ((LA26_0>='\u0000' && LA26_0<='\b')||(LA26_0>='\u000B' && LA26_0<='\f')||(LA26_0>='\u000E' && LA26_0<='\u001F')||LA26_0=='?'||(LA26_0>='\\' && LA26_0<=']')||LA26_0=='`'||(LA26_0>='~' && LA26_0<='\uFFFF')) ) {s = 50;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 26, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}