package org.integratedmodelling.kdl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalKdlLexer extends Lexer {
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
    public static final int RULE_ID=10;
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
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__90=90;
    public static final int RULE_SHAPE=9;
    public static final int T__19=19;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int RULE_LOWERCASE_DASHID=7;
    public static final int RULE_CAMELCASE_ID=11;
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
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=15;
    public static final int RULE_ANY_OTHER=16;
    public static final int RULE_LOWERCASE_ID=5;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;

    // delegates
    // delegators

    public InternalKdlLexer() {;} 
    public InternalKdlLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalKdlLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalKdl.g"; }

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:11:7: ( '@dataflow' )
            // InternalKdl.g:11:9: '@dataflow'
            {
            match("@dataflow"); 


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
            // InternalKdl.g:12:7: ( '@var' )
            // InternalKdl.g:12:9: '@var'
            {
            match("@var"); 


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
            // InternalKdl.g:13:7: ( '@val' )
            // InternalKdl.g:13:9: '@val'
            {
            match("@val"); 


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
            // InternalKdl.g:14:7: ( '@author' )
            // InternalKdl.g:14:9: '@author'
            {
            match("@author"); 


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
            // InternalKdl.g:15:7: ( '@version' )
            // InternalKdl.g:15:9: '@version'
            {
            match("@version"); 


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
            // InternalKdl.g:16:7: ( '@klab' )
            // InternalKdl.g:16:9: '@klab'
            {
            match("@klab"); 


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
            // InternalKdl.g:17:7: ( '@worldview' )
            // InternalKdl.g:17:9: '@worldview'
            {
            match("@worldview"); 


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
            // InternalKdl.g:18:7: ( '@geometry' )
            // InternalKdl.g:18:9: '@geometry'
            {
            match("@geometry"); 


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
            // InternalKdl.g:19:7: ( '@endpoint' )
            // InternalKdl.g:19:9: '@endpoint'
            {
            match("@endpoint"); 


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
            // InternalKdl.g:20:7: ( '@namespace' )
            // InternalKdl.g:20:9: '@namespace'
            {
            match("@namespace"); 


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
            // InternalKdl.g:21:7: ( '@coverage' )
            // InternalKdl.g:21:9: '@coverage'
            {
            match("@coverage"); 


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
            // InternalKdl.g:22:7: ( ',' )
            // InternalKdl.g:22:9: ','
            {
            match(','); 

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
            // InternalKdl.g:23:7: ( '@context' )
            // InternalKdl.g:23:9: '@context'
            {
            match("@context"); 


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
            // InternalKdl.g:24:7: ( 'export' )
            // InternalKdl.g:24:9: 'export'
            {
            match("export"); 


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
            // InternalKdl.g:25:7: ( 'optional' )
            // InternalKdl.g:25:9: 'optional'
            {
            match("optional"); 


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
            // InternalKdl.g:26:7: ( 'import' )
            // InternalKdl.g:26:9: 'import'
            {
            match("import"); 


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
            // InternalKdl.g:27:7: ( 'multiple' )
            // InternalKdl.g:27:9: 'multiple'
            {
            match("multiple"); 


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
            // InternalKdl.g:28:7: ( '+' )
            // InternalKdl.g:28:9: '+'
            {
            match('+'); 

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
            // InternalKdl.g:29:7: ( 'parameter' )
            // InternalKdl.g:29:9: 'parameter'
            {
            match("parameter"); 


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
            // InternalKdl.g:30:7: ( 'for' )
            // InternalKdl.g:30:9: 'for'
            {
            match("for"); 


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
            // InternalKdl.g:31:7: ( '{' )
            // InternalKdl.g:31:9: '{'
            {
            match('{'); 

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
            // InternalKdl.g:32:7: ( '}' )
            // InternalKdl.g:32:9: '}'
            {
            match('}'); 

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
            // InternalKdl.g:33:7: ( 'as' )
            // InternalKdl.g:33:9: 'as'
            {
            match("as"); 


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
            // InternalKdl.g:34:7: ( 'over' )
            // InternalKdl.g:34:9: 'over'
            {
            match("over"); 


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
            // InternalKdl.g:35:7: ( 'number' )
            // InternalKdl.g:35:9: 'number'
            {
            match("number"); 


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
            // InternalKdl.g:36:7: ( 'boolean' )
            // InternalKdl.g:36:9: 'boolean'
            {
            match("boolean"); 


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
            // InternalKdl.g:37:7: ( 'text' )
            // InternalKdl.g:37:9: 'text'
            {
            match("text"); 


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
            // InternalKdl.g:38:7: ( 'list' )
            // InternalKdl.g:38:9: 'list'
            {
            match("list"); 


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
            // InternalKdl.g:39:7: ( 'enum' )
            // InternalKdl.g:39:9: 'enum'
            {
            match("enum"); 


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
            // InternalKdl.g:40:7: ( 'input' )
            // InternalKdl.g:40:9: 'input'
            {
            match("input"); 


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
            // InternalKdl.g:41:7: ( 'values' )
            // InternalKdl.g:41:9: 'values'
            {
            match("values"); 


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
            // InternalKdl.g:42:7: ( 'default' )
            // InternalKdl.g:42:9: 'default'
            {
            match("default"); 


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
            // InternalKdl.g:43:7: ( 'minimum' )
            // InternalKdl.g:43:9: 'minimum'
            {
            match("minimum"); 


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
            // InternalKdl.g:44:7: ( 'maximum' )
            // InternalKdl.g:44:9: 'maximum'
            {
            match("maximum"); 


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
            // InternalKdl.g:45:7: ( 'range' )
            // InternalKdl.g:45:9: 'range'
            {
            match("range"); 


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
            // InternalKdl.g:46:7: ( 'to' )
            // InternalKdl.g:46:9: 'to'
            {
            match("to"); 


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
            // InternalKdl.g:47:7: ( 'geometry' )
            // InternalKdl.g:47:9: 'geometry'
            {
            match("geometry"); 


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
            // InternalKdl.g:48:7: ( 'units' )
            // InternalKdl.g:48:9: 'units'
            {
            match("units"); 


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
            // InternalKdl.g:49:7: ( 'metadata' )
            // InternalKdl.g:49:9: 'metadata'
            {
            match("metadata"); 


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
            // InternalKdl.g:50:7: ( 'class' )
            // InternalKdl.g:50:9: 'class'
            {
            match("class"); 


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
            // InternalKdl.g:51:7: ( 'compute' )
            // InternalKdl.g:51:9: 'compute'
            {
            match("compute"); 


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
            // InternalKdl.g:52:7: ( '*' )
            // InternalKdl.g:52:9: '*'
            {
            match('*'); 

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
            // InternalKdl.g:53:7: ( 'object' )
            // InternalKdl.g:53:9: 'object'
            {
            match("object"); 


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
            // InternalKdl.g:54:7: ( 'process' )
            // InternalKdl.g:54:9: 'process'
            {
            match("process"); 


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
            // InternalKdl.g:55:7: ( 'value' )
            // InternalKdl.g:55:9: 'value'
            {
            match("value"); 


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
            // InternalKdl.g:56:7: ( 'concept' )
            // InternalKdl.g:56:9: 'concept'
            {
            match("concept"); 


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
            // InternalKdl.g:57:7: ( 'extent' )
            // InternalKdl.g:57:9: 'extent'
            {
            match("extent"); 


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
            // InternalKdl.g:58:7: ( 'spatialextent' )
            // InternalKdl.g:58:9: 'spatialextent'
            {
            match("spatialextent"); 


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
            // InternalKdl.g:59:7: ( 'temporalextent' )
            // InternalKdl.g:59:9: 'temporalextent'
            {
            match("temporalextent"); 


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
            // InternalKdl.g:60:7: ( 'annotation' )
            // InternalKdl.g:60:9: 'annotation'
            {
            match("annotation"); 


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
            // InternalKdl.g:61:7: ( 'contextualizer' )
            // InternalKdl.g:61:9: 'contextualizer'
            {
            match("contextualizer"); 


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
            // InternalKdl.g:62:7: ( 'void' )
            // InternalKdl.g:62:9: 'void'
            {
            match("void"); 


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
            // InternalKdl.g:63:7: ( 'partition' )
            // InternalKdl.g:63:9: 'partition'
            {
            match("partition"); 


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
            // InternalKdl.g:64:7: ( 'models' )
            // InternalKdl.g:64:9: 'models'
            {
            match("models"); 


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
            // InternalKdl.g:65:7: ( 'concepts' )
            // InternalKdl.g:65:9: 'concepts'
            {
            match("concepts"); 


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
            // InternalKdl.g:66:7: ( 'observers' )
            // InternalKdl.g:66:9: 'observers'
            {
            match("observers"); 


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
            // InternalKdl.g:67:7: ( '(' )
            // InternalKdl.g:67:9: '('
            {
            match('('); 

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
            // InternalKdl.g:68:7: ( ')' )
            // InternalKdl.g:68:9: ')'
            {
            match(')'); 

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
            // InternalKdl.g:69:7: ( 'true' )
            // InternalKdl.g:69:9: 'true'
            {
            match("true"); 


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
            // InternalKdl.g:70:7: ( 'false' )
            // InternalKdl.g:70:9: 'false'
            {
            match("false"); 


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
            // InternalKdl.g:71:7: ( 'urn:klab:' )
            // InternalKdl.g:71:9: 'urn:klab:'
            {
            match("urn:klab:"); 


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
            // InternalKdl.g:72:7: ( ':' )
            // InternalKdl.g:72:9: ':'
            {
            match(':'); 

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
            // InternalKdl.g:73:7: ( '#' )
            // InternalKdl.g:73:9: '#'
            {
            match('#'); 

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
            // InternalKdl.g:74:7: ( '/' )
            // InternalKdl.g:74:9: '/'
            {
            match('/'); 

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
            // InternalKdl.g:75:7: ( '.' )
            // InternalKdl.g:75:9: '.'
            {
            match('.'); 

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
            // InternalKdl.g:76:7: ( '=?' )
            // InternalKdl.g:76:9: '=?'
            {
            match("=?"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:77:7: ( '=' )
            // InternalKdl.g:77:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:78:7: ( '>>' )
            // InternalKdl.g:78:9: '>>'
            {
            match(">>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:79:7: ( '>' )
            // InternalKdl.g:79:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:80:7: ( '<' )
            // InternalKdl.g:80:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:81:7: ( '!=' )
            // InternalKdl.g:81:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:82:7: ( '<=' )
            // InternalKdl.g:82:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:83:7: ( '>=' )
            // InternalKdl.g:83:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:84:7: ( '@' )
            // InternalKdl.g:84:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:85:7: ( '-' )
            // InternalKdl.g:85:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:86:7: ( 'e' )
            // InternalKdl.g:86:9: 'e'
            {
            match('e'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:87:7: ( 'E' )
            // InternalKdl.g:87:9: 'E'
            {
            match('E'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:88:7: ( '^' )
            // InternalKdl.g:88:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "RULE_LOWERCASE_ID"
    public final void mRULE_LOWERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:4296:19: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )* )
            // InternalKdl.g:4296:21: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )*
            {
            matchRange('a','z'); 
            // InternalKdl.g:4296:30: ( 'a' .. 'z' | '0' .. '9' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalKdl.g:
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

    // $ANTLR start "RULE_LOWERCASE_DASHID"
    public final void mRULE_LOWERCASE_DASHID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_DASHID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:4298:23: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '-' )* )
            // InternalKdl.g:4298:25: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '-' )*
            {
            matchRange('a','z'); 
            // InternalKdl.g:4298:34: ( 'a' .. 'z' | '0' .. '9' | '-' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='-'||(LA2_0>='0' && LA2_0<='9')||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKdl.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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
    // $ANTLR end "RULE_LOWERCASE_DASHID"

    // $ANTLR start "RULE_SHAPE"
    public final void mRULE_SHAPE() throws RecognitionException {
        try {
            int _type = RULE_SHAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:4300:12: ( ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ ) )
            // InternalKdl.g:4300:14: ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ )
            {
            // InternalKdl.g:4300:14: ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='#') ) {
                int LA5_1 = input.LA(2);

                if ( ((LA5_1>='A' && LA5_1<='z')) ) {
                    alt5=2;
                }
                else {
                    alt5=1;}
            }
            else if ( ((LA5_0>='A' && LA5_0<='z')) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalKdl.g:4300:15: '#'
                    {
                    match('#'); 

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4300:19: ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+
                    {
                    // InternalKdl.g:4300:19: ( '#' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='#') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalKdl.g:4300:19: '#'
                            {
                            match('#'); 

                            }
                            break;

                    }

                    // InternalKdl.g:4300:24: ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='A' && LA4_0<='z')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // InternalKdl.g:4300:25: 'A' .. 'z' ( '.' | '0' .. '3' )
                    	    {
                    	    matchRange('A','z'); 
                    	    if ( input.LA(1)=='.'||(input.LA(1)>='0' && input.LA(1)<='3') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);


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
    // $ANTLR end "RULE_SHAPE"

    // $ANTLR start "RULE_UPPERCASE_ID"
    public final void mRULE_UPPERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_UPPERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:4302:19: ( 'A' .. 'Z' ( 'A' .. 'Z' )* )
            // InternalKdl.g:4302:21: 'A' .. 'Z' ( 'A' .. 'Z' )*
            {
            matchRange('A','Z'); 
            // InternalKdl.g:4302:30: ( 'A' .. 'Z' )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='A' && LA6_0<='Z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalKdl.g:4302:31: 'A' .. 'Z'
            	    {
            	    matchRange('A','Z'); 

            	    }
            	    break;

            	default :
            	    break loop6;
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
            // InternalKdl.g:4304:21: ( RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )* )
            // InternalKdl.g:4304:23: RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )*
            {
            mRULE_UPPERCASE_ID(); 
            // InternalKdl.g:4304:41: ( '.' RULE_UPPERCASE_ID )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='.') ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKdl.g:4304:42: '.' RULE_UPPERCASE_ID
            	    {
            	    match('.'); 
            	    mRULE_UPPERCASE_ID(); 

            	    }
            	    break;

            	default :
            	    break loop7;
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
            // InternalKdl.g:4306:19: ( 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )* )
            // InternalKdl.g:4306:21: 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )*
            {
            matchRange('A','Z'); 
            // InternalKdl.g:4306:30: ( 'A' .. 'z' | '0' .. '9' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')||(LA8_0>='A' && LA8_0<='z')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalKdl.g:
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
            	    break loop8;
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

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:4308:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalKdl.g:4308:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalKdl.g:4308:11: ( '^' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='^') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalKdl.g:4308:11: '^'
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

            // InternalKdl.g:4308:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalKdl.g:
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
            	    break loop10;
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
            // InternalKdl.g:4310:10: ( ( '0' .. '9' )+ )
            // InternalKdl.g:4310:12: ( '0' .. '9' )+
            {
            // InternalKdl.g:4310:12: ( '0' .. '9' )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalKdl.g:4310:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
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
            // InternalKdl.g:4312:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalKdl.g:4312:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalKdl.g:4312:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='\"') ) {
                alt14=1;
            }
            else if ( (LA14_0=='\'') ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalKdl.g:4312:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalKdl.g:4312:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop12:
                    do {
                        int alt12=3;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0=='\\') ) {
                            alt12=1;
                        }
                        else if ( ((LA12_0>='\u0000' && LA12_0<='!')||(LA12_0>='#' && LA12_0<='[')||(LA12_0>=']' && LA12_0<='\uFFFF')) ) {
                            alt12=2;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // InternalKdl.g:4312:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKdl.g:4312:28: ~ ( ( '\\\\' | '\"' ) )
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
                    	    break loop12;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalKdl.g:4312:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalKdl.g:4312:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop13:
                    do {
                        int alt13=3;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0=='\\') ) {
                            alt13=1;
                        }
                        else if ( ((LA13_0>='\u0000' && LA13_0<='&')||(LA13_0>='(' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFF')) ) {
                            alt13=2;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // InternalKdl.g:4312:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKdl.g:4312:61: ~ ( ( '\\\\' | '\\'' ) )
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
                    	    break loop13;
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
            // InternalKdl.g:4314:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalKdl.g:4314:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalKdl.g:4314:24: ( options {greedy=false; } : . )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0=='*') ) {
                    int LA15_1 = input.LA(2);

                    if ( (LA15_1=='/') ) {
                        alt15=2;
                    }
                    else if ( ((LA15_1>='\u0000' && LA15_1<='.')||(LA15_1>='0' && LA15_1<='\uFFFF')) ) {
                        alt15=1;
                    }


                }
                else if ( ((LA15_0>='\u0000' && LA15_0<=')')||(LA15_0>='+' && LA15_0<='\uFFFF')) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalKdl.g:4314:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop15;
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
            // InternalKdl.g:4316:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKdl.g:4316:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalKdl.g:4316:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='\u0000' && LA16_0<='\t')||(LA16_0>='\u000B' && LA16_0<='\f')||(LA16_0>='\u000E' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalKdl.g:4316:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop16;
                }
            } while (true);

            // InternalKdl.g:4316:40: ( ( '\\r' )? '\\n' )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='\n'||LA18_0=='\r') ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalKdl.g:4316:41: ( '\\r' )? '\\n'
                    {
                    // InternalKdl.g:4316:41: ( '\\r' )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0=='\r') ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalKdl.g:4316:41: '\\r'
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
            // InternalKdl.g:4318:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKdl.g:4318:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKdl.g:4318:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>='\t' && LA19_0<='\n')||LA19_0=='\r'||LA19_0==' ') ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalKdl.g:
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
            	    if ( cnt19 >= 1 ) break loop19;
                        EarlyExitException eee =
                            new EarlyExitException(19, input);
                        throw eee;
                }
                cnt19++;
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
            // InternalKdl.g:4320:16: ( . )
            // InternalKdl.g:4320:18: .
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
        // InternalKdl.g:1:8: ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | RULE_LOWERCASE_ID | RULE_LOWERCASE_DASHID | RULE_SHAPE | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt20=91;
        alt20 = dfa20.predict(input);
        switch (alt20) {
            case 1 :
                // InternalKdl.g:1:10: T__17
                {
                mT__17(); 

                }
                break;
            case 2 :
                // InternalKdl.g:1:16: T__18
                {
                mT__18(); 

                }
                break;
            case 3 :
                // InternalKdl.g:1:22: T__19
                {
                mT__19(); 

                }
                break;
            case 4 :
                // InternalKdl.g:1:28: T__20
                {
                mT__20(); 

                }
                break;
            case 5 :
                // InternalKdl.g:1:34: T__21
                {
                mT__21(); 

                }
                break;
            case 6 :
                // InternalKdl.g:1:40: T__22
                {
                mT__22(); 

                }
                break;
            case 7 :
                // InternalKdl.g:1:46: T__23
                {
                mT__23(); 

                }
                break;
            case 8 :
                // InternalKdl.g:1:52: T__24
                {
                mT__24(); 

                }
                break;
            case 9 :
                // InternalKdl.g:1:58: T__25
                {
                mT__25(); 

                }
                break;
            case 10 :
                // InternalKdl.g:1:64: T__26
                {
                mT__26(); 

                }
                break;
            case 11 :
                // InternalKdl.g:1:70: T__27
                {
                mT__27(); 

                }
                break;
            case 12 :
                // InternalKdl.g:1:76: T__28
                {
                mT__28(); 

                }
                break;
            case 13 :
                // InternalKdl.g:1:82: T__29
                {
                mT__29(); 

                }
                break;
            case 14 :
                // InternalKdl.g:1:88: T__30
                {
                mT__30(); 

                }
                break;
            case 15 :
                // InternalKdl.g:1:94: T__31
                {
                mT__31(); 

                }
                break;
            case 16 :
                // InternalKdl.g:1:100: T__32
                {
                mT__32(); 

                }
                break;
            case 17 :
                // InternalKdl.g:1:106: T__33
                {
                mT__33(); 

                }
                break;
            case 18 :
                // InternalKdl.g:1:112: T__34
                {
                mT__34(); 

                }
                break;
            case 19 :
                // InternalKdl.g:1:118: T__35
                {
                mT__35(); 

                }
                break;
            case 20 :
                // InternalKdl.g:1:124: T__36
                {
                mT__36(); 

                }
                break;
            case 21 :
                // InternalKdl.g:1:130: T__37
                {
                mT__37(); 

                }
                break;
            case 22 :
                // InternalKdl.g:1:136: T__38
                {
                mT__38(); 

                }
                break;
            case 23 :
                // InternalKdl.g:1:142: T__39
                {
                mT__39(); 

                }
                break;
            case 24 :
                // InternalKdl.g:1:148: T__40
                {
                mT__40(); 

                }
                break;
            case 25 :
                // InternalKdl.g:1:154: T__41
                {
                mT__41(); 

                }
                break;
            case 26 :
                // InternalKdl.g:1:160: T__42
                {
                mT__42(); 

                }
                break;
            case 27 :
                // InternalKdl.g:1:166: T__43
                {
                mT__43(); 

                }
                break;
            case 28 :
                // InternalKdl.g:1:172: T__44
                {
                mT__44(); 

                }
                break;
            case 29 :
                // InternalKdl.g:1:178: T__45
                {
                mT__45(); 

                }
                break;
            case 30 :
                // InternalKdl.g:1:184: T__46
                {
                mT__46(); 

                }
                break;
            case 31 :
                // InternalKdl.g:1:190: T__47
                {
                mT__47(); 

                }
                break;
            case 32 :
                // InternalKdl.g:1:196: T__48
                {
                mT__48(); 

                }
                break;
            case 33 :
                // InternalKdl.g:1:202: T__49
                {
                mT__49(); 

                }
                break;
            case 34 :
                // InternalKdl.g:1:208: T__50
                {
                mT__50(); 

                }
                break;
            case 35 :
                // InternalKdl.g:1:214: T__51
                {
                mT__51(); 

                }
                break;
            case 36 :
                // InternalKdl.g:1:220: T__52
                {
                mT__52(); 

                }
                break;
            case 37 :
                // InternalKdl.g:1:226: T__53
                {
                mT__53(); 

                }
                break;
            case 38 :
                // InternalKdl.g:1:232: T__54
                {
                mT__54(); 

                }
                break;
            case 39 :
                // InternalKdl.g:1:238: T__55
                {
                mT__55(); 

                }
                break;
            case 40 :
                // InternalKdl.g:1:244: T__56
                {
                mT__56(); 

                }
                break;
            case 41 :
                // InternalKdl.g:1:250: T__57
                {
                mT__57(); 

                }
                break;
            case 42 :
                // InternalKdl.g:1:256: T__58
                {
                mT__58(); 

                }
                break;
            case 43 :
                // InternalKdl.g:1:262: T__59
                {
                mT__59(); 

                }
                break;
            case 44 :
                // InternalKdl.g:1:268: T__60
                {
                mT__60(); 

                }
                break;
            case 45 :
                // InternalKdl.g:1:274: T__61
                {
                mT__61(); 

                }
                break;
            case 46 :
                // InternalKdl.g:1:280: T__62
                {
                mT__62(); 

                }
                break;
            case 47 :
                // InternalKdl.g:1:286: T__63
                {
                mT__63(); 

                }
                break;
            case 48 :
                // InternalKdl.g:1:292: T__64
                {
                mT__64(); 

                }
                break;
            case 49 :
                // InternalKdl.g:1:298: T__65
                {
                mT__65(); 

                }
                break;
            case 50 :
                // InternalKdl.g:1:304: T__66
                {
                mT__66(); 

                }
                break;
            case 51 :
                // InternalKdl.g:1:310: T__67
                {
                mT__67(); 

                }
                break;
            case 52 :
                // InternalKdl.g:1:316: T__68
                {
                mT__68(); 

                }
                break;
            case 53 :
                // InternalKdl.g:1:322: T__69
                {
                mT__69(); 

                }
                break;
            case 54 :
                // InternalKdl.g:1:328: T__70
                {
                mT__70(); 

                }
                break;
            case 55 :
                // InternalKdl.g:1:334: T__71
                {
                mT__71(); 

                }
                break;
            case 56 :
                // InternalKdl.g:1:340: T__72
                {
                mT__72(); 

                }
                break;
            case 57 :
                // InternalKdl.g:1:346: T__73
                {
                mT__73(); 

                }
                break;
            case 58 :
                // InternalKdl.g:1:352: T__74
                {
                mT__74(); 

                }
                break;
            case 59 :
                // InternalKdl.g:1:358: T__75
                {
                mT__75(); 

                }
                break;
            case 60 :
                // InternalKdl.g:1:364: T__76
                {
                mT__76(); 

                }
                break;
            case 61 :
                // InternalKdl.g:1:370: T__77
                {
                mT__77(); 

                }
                break;
            case 62 :
                // InternalKdl.g:1:376: T__78
                {
                mT__78(); 

                }
                break;
            case 63 :
                // InternalKdl.g:1:382: T__79
                {
                mT__79(); 

                }
                break;
            case 64 :
                // InternalKdl.g:1:388: T__80
                {
                mT__80(); 

                }
                break;
            case 65 :
                // InternalKdl.g:1:394: T__81
                {
                mT__81(); 

                }
                break;
            case 66 :
                // InternalKdl.g:1:400: T__82
                {
                mT__82(); 

                }
                break;
            case 67 :
                // InternalKdl.g:1:406: T__83
                {
                mT__83(); 

                }
                break;
            case 68 :
                // InternalKdl.g:1:412: T__84
                {
                mT__84(); 

                }
                break;
            case 69 :
                // InternalKdl.g:1:418: T__85
                {
                mT__85(); 

                }
                break;
            case 70 :
                // InternalKdl.g:1:424: T__86
                {
                mT__86(); 

                }
                break;
            case 71 :
                // InternalKdl.g:1:430: T__87
                {
                mT__87(); 

                }
                break;
            case 72 :
                // InternalKdl.g:1:436: T__88
                {
                mT__88(); 

                }
                break;
            case 73 :
                // InternalKdl.g:1:442: T__89
                {
                mT__89(); 

                }
                break;
            case 74 :
                // InternalKdl.g:1:448: T__90
                {
                mT__90(); 

                }
                break;
            case 75 :
                // InternalKdl.g:1:454: T__91
                {
                mT__91(); 

                }
                break;
            case 76 :
                // InternalKdl.g:1:460: T__92
                {
                mT__92(); 

                }
                break;
            case 77 :
                // InternalKdl.g:1:466: T__93
                {
                mT__93(); 

                }
                break;
            case 78 :
                // InternalKdl.g:1:472: T__94
                {
                mT__94(); 

                }
                break;
            case 79 :
                // InternalKdl.g:1:478: RULE_LOWERCASE_ID
                {
                mRULE_LOWERCASE_ID(); 

                }
                break;
            case 80 :
                // InternalKdl.g:1:496: RULE_LOWERCASE_DASHID
                {
                mRULE_LOWERCASE_DASHID(); 

                }
                break;
            case 81 :
                // InternalKdl.g:1:518: RULE_SHAPE
                {
                mRULE_SHAPE(); 

                }
                break;
            case 82 :
                // InternalKdl.g:1:529: RULE_UPPERCASE_ID
                {
                mRULE_UPPERCASE_ID(); 

                }
                break;
            case 83 :
                // InternalKdl.g:1:547: RULE_UPPERCASE_PATH
                {
                mRULE_UPPERCASE_PATH(); 

                }
                break;
            case 84 :
                // InternalKdl.g:1:567: RULE_CAMELCASE_ID
                {
                mRULE_CAMELCASE_ID(); 

                }
                break;
            case 85 :
                // InternalKdl.g:1:585: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 86 :
                // InternalKdl.g:1:593: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 87 :
                // InternalKdl.g:1:602: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 88 :
                // InternalKdl.g:1:614: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 89 :
                // InternalKdl.g:1:630: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 90 :
                // InternalKdl.g:1:646: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 91 :
                // InternalKdl.g:1:654: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA20 dfa20 = new DFA20(this);
    static final String DFA20_eotS =
        "\1\uffff\1\70\1\uffff\1\75\3\106\1\uffff\2\106\2\uffff\13\106\1\uffff\1\106\3\uffff\1\153\1\156\1\uffff\1\161\1\164\1\166\1\56\1\uffff\1\173\1\177\1\106\1\u0080\1\100\1\56\1\uffff\2\56\15\uffff\3\106\2\uffff\1\106\1\uffff\1\106\1\uffff\3\106\1\uffff\7\106\1\uffff\4\106\2\uffff\1\u009d\4\106\1\u00a3\13\106\1\uffff\1\106\21\uffff\1\76\1\u0080\1\uffff\1\76\1\176\3\uffff\1\76\6\uffff\5\106\1\100\15\106\1\u00cb\1\106\1\uffff\5\106\1\uffff\15\106\1\u00b2\1\uffff\2\176\4\uffff\2\106\1\u00e3\2\106\1\u00e5\14\106\1\uffff\4\106\1\u00f6\1\106\1\u00f8\1\u00f9\1\106\1\u00fb\4\106\1\uffff\5\106\1\76\2\106\1\uffff\1\106\1\uffff\3\106\1\u010b\10\106\1\u0114\3\106\1\uffff\1\106\2\uffff\1\u011a\1\uffff\1\106\1\u011c\1\106\1\u011e\1\u011f\4\106\1\u0124\1\u0125\1\106\1\u0127\1\106\1\u0129\1\uffff\4\106\1\u012e\3\106\1\uffff\1\106\1\u0133\2\106\1\u0136\1\uffff\1\106\1\uffff\1\106\2\uffff\4\106\2\uffff\1\106\1\uffff\1\106\1\uffff\1\106\1\u0140\1\u0141\1\106\1\uffff\2\106\1\u0145\1\106\1\uffff\1\u0147\1\106\1\uffff\1\u0149\1\106\1\u014b\1\u014d\2\106\1\u0150\1\106\1\u0152\2\uffff\1\u0153\2\106\1\uffff\1\106\1\uffff\1\106\1\uffff\1\u0158\1\uffff\1\u0159\1\uffff\2\106\1\uffff\1\u015c\2\uffff\1\u015d\1\u015e\2\106\2\uffff\2\106\3\uffff\1\u0163\3\106\1\uffff\10\106\1\u016f\1\u0170\1\u0171\3\uffff";
    static final String DFA20_eofS =
        "\u0172\uffff";
    static final String DFA20_minS =
        "\1\0\1\141\1\uffff\4\55\1\uffff\2\55\2\uffff\13\55\1\uffff\1\55\3\uffff\1\101\1\52\1\uffff\1\77\3\75\1\uffff\2\56\1\55\3\56\1\uffff\2\0\3\uffff\1\141\6\uffff\1\157\2\uffff\3\55\2\uffff\1\55\1\uffff\1\60\1\uffff\3\55\1\uffff\7\55\1\uffff\4\55\2\uffff\21\55\1\uffff\1\55\21\uffff\1\101\1\56\1\uffff\2\60\3\uffff\1\60\3\uffff\1\154\1\uffff\1\156\4\55\2\56\17\55\1\uffff\5\55\1\uffff\15\55\1\56\1\uffff\2\56\4\uffff\3\55\1\60\16\55\1\uffff\16\55\1\uffff\5\55\1\60\2\55\1\uffff\1\55\1\uffff\20\55\1\uffff\1\55\2\uffff\1\55\1\uffff\17\55\1\uffff\10\55\1\uffff\5\55\1\uffff\1\55\1\uffff\1\55\2\uffff\4\55\2\uffff\1\55\1\uffff\1\55\1\uffff\4\55\1\uffff\4\55\1\uffff\2\55\1\uffff\11\55\2\uffff\3\55\1\uffff\1\55\1\uffff\1\55\1\uffff\1\55\1\uffff\1\55\1\uffff\2\55\1\uffff\1\55\2\uffff\4\55\2\uffff\2\55\3\uffff\4\55\1\uffff\13\55\3\uffff";
    static final String DFA20_maxS =
        "\1\uffff\1\167\1\uffff\4\172\1\uffff\2\172\2\uffff\13\172\1\uffff\1\172\3\uffff\1\172\1\57\1\uffff\1\77\1\76\2\75\1\uffff\4\172\2\63\1\uffff\2\uffff\3\uffff\1\145\6\uffff\1\157\2\uffff\3\172\2\uffff\1\172\1\uffff\1\172\1\uffff\3\172\1\uffff\7\172\1\uffff\4\172\2\uffff\21\172\1\uffff\1\172\21\uffff\1\132\1\172\1\uffff\2\172\3\uffff\1\172\3\uffff\1\162\1\uffff\1\166\5\172\1\63\17\172\1\uffff\5\172\1\uffff\15\172\1\63\1\uffff\1\172\1\63\4\uffff\22\172\1\uffff\16\172\1\uffff\10\172\1\uffff\1\172\1\uffff\20\172\1\uffff\1\172\2\uffff\1\172\1\uffff\17\172\1\uffff\10\172\1\uffff\5\172\1\uffff\1\172\1\uffff\1\172\2\uffff\4\172\2\uffff\1\172\1\uffff\1\172\1\uffff\4\172\1\uffff\4\172\1\uffff\2\172\1\uffff\11\172\2\uffff\3\172\1\uffff\1\172\1\uffff\1\172\1\uffff\1\172\1\uffff\1\172\1\uffff\2\172\1\uffff\1\172\2\uffff\4\172\2\uffff\2\172\3\uffff\4\172\1\uffff\13\172\3\uffff";
    static final String DFA20_acceptS =
        "\2\uffff\1\14\4\uffff\1\22\2\uffff\1\25\1\26\13\uffff\1\52\1\uffff\1\71\1\72\1\76\2\uffff\1\101\4\uffff\1\113\6\uffff\1\126\2\uffff\1\132\1\133\1\1\1\uffff\1\4\1\6\1\7\1\10\1\11\1\12\1\uffff\1\112\1\14\3\uffff\1\114\1\121\1\uffff\1\125\1\uffff\1\120\3\uffff\1\117\7\uffff\1\22\4\uffff\1\25\1\26\21\uffff\1\52\1\uffff\1\71\1\72\1\76\1\77\1\130\1\131\1\100\1\101\1\102\1\103\1\104\1\111\1\105\1\110\1\106\1\107\1\113\2\uffff\1\115\2\uffff\1\124\1\116\1\122\1\uffff\1\126\1\127\1\132\1\uffff\1\5\26\uffff\1\27\5\uffff\1\44\16\uffff\1\123\2\uffff\1\2\1\3\1\13\1\15\22\uffff\1\24\16\uffff\1\75\10\uffff\1\35\1\uffff\1\30\20\uffff\1\33\1\uffff\1\73\1\34\1\uffff\1\64\17\uffff\1\36\10\uffff\1\74\5\uffff\1\55\1\uffff\1\43\1\uffff\1\46\1\50\4\uffff\1\16\1\57\1\uffff\1\53\1\uffff\1\20\4\uffff\1\66\4\uffff\1\31\2\uffff\1\37\11\uffff\1\41\1\42\3\uffff\1\54\1\uffff\1\32\1\uffff\1\40\1\uffff\1\51\1\uffff\1\56\2\uffff\1\17\1\uffff\1\21\1\47\4\uffff\1\45\1\67\2\uffff\1\70\1\23\1\65\4\uffff\1\62\13\uffff\1\60\1\61\1\63";
    static final String DFA20_specialS =
        "\1\1\52\uffff\1\0\1\2\u0145\uffff}>";
    static final String[] DFA20_transitionS = {
            "\11\56\2\55\2\56\1\55\22\56\1\55\1\42\1\53\1\34\3\56\1\54\1\31\1\32\1\27\1\7\1\2\1\43\1\36\1\35\12\52\1\33\1\56\1\41\1\37\1\40\1\56\1\1\4\47\1\44\25\47\3\51\1\45\1\50\1\51\1\14\1\16\1\26\1\22\1\3\1\11\1\24\1\46\1\5\2\46\1\20\1\6\1\15\1\4\1\10\1\46\1\23\1\30\1\17\1\25\1\21\4\46\1\12\1\56\1\13\uff82\56",
            "\1\61\1\uffff\1\67\1\57\1\65\1\uffff\1\64\3\uffff\1\62\2\uffff\1\66\7\uffff\1\60\1\63",
            "",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\73\11\77\1\72\2\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\77\1\105\15\77\1\103\5\77\1\104\4\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\107\1\110\14\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\113\3\77\1\114\3\77\1\112\5\77\1\115\5\77\1\111\5\77",
            "",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\117\20\77\1\120\10\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\122\15\77\1\121\13\77",
            "",
            "",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\126\4\77\1\125\7\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\127\5\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\130\13\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\131\11\77\1\132\2\77\1\133\10\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\134\21\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\135\15\77\1\136\13\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\137\25\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\140\31\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\141\25\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\142\3\77\1\143\10\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\144\2\77\1\145\13\77",
            "",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\17\77\1\147\12\77",
            "",
            "",
            "",
            "\72\76",
            "\1\154\4\uffff\1\155",
            "",
            "\1\160",
            "\1\163\1\162",
            "\1\165",
            "\1\167",
            "",
            "\1\171\1\uffff\4\174\6\175\7\uffff\32\172\4\176\1\175\1\176\32\175",
            "\1\76\1\uffff\4\76\15\uffff\32\100\4\uffff\1\100\1\uffff\32\100",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\171\1\uffff\4\174\6\175\7\uffff\32\172\4\176\1\175\1\176\32\175",
            "\1\76\1\uffff\4\u0081",
            "\1\76\1\uffff\4\76",
            "",
            "\0\u0083",
            "\0\u0083",
            "",
            "",
            "",
            "\1\u0085\3\uffff\1\u0086",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0087",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\17\77\1\u0088\3\77\1\u0089\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u008a\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\u008d\4\76\1\u008c\1\76\32\u008b",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "",
            "\12\101\7\uffff\32\100\4\uffff\1\101\1\uffff\32\101",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u008e\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u008f\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\11\77\1\u0090\10\77\1\u0091\7\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\17\77\1\u0092\12\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\17\77\1\u0093\12\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u0094\16\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u0095\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\27\77\1\u0096\2\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0097\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\3\77\1\u0098\26\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u0099\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u009a\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u009b\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u009c\16\77",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u009e\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u009f\15\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u00a0\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u00a2\12\77\1\u00a1\2\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u00a4\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u00a5\7\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u00a6\16\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u00a7\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\5\77\1\u00a8\24\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u00a9\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u00aa\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u00ab\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u00ac\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u00ad\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u00ae\1\u00af\14\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u00b0\31\77",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\32\u00b1",
            "\1\u00b2\1\uffff\12\175\7\uffff\32\172\4\176\1\175\1\176\32\175",
            "",
            "\12\175\7\uffff\32\u00b3\4\u00b4\1\u00b3\1\u00b4\32\u00b3",
            "\12\175\7\uffff\32\175\4\uffff\1\175\1\uffff\32\175",
            "",
            "",
            "",
            "\12\100\7\uffff\32\u008d\4\uffff\1\u008d\1\uffff\32\u008d",
            "",
            "",
            "",
            "\1\u00b6\5\uffff\1\u00b5",
            "",
            "\1\u00b8\7\uffff\1\u00b7",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u00b9\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00ba\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u00bb\15\77",
            "\1\102\1\76\1\uffff\4\74\6\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\76\1\uffff\4\u00bc\6\101\7\uffff\32\100\4\uffff\1\101\1\uffff\32\101",
            "\1\76\1\uffff\4\u0081",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u00bd\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u00be\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00bf\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00c0\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u00c1\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u00c2\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u00c3\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u00c4\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u00c5\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u00c6\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00c7\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u00c8\22\77\1\u00c9\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\2\77\1\u00ca\27\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u00cc\7\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u00cd\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\77\1\u00ce\30\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u00cf\16\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u00d0\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\17\77\1\u00d1\12\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00d2\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u00d3\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u00d4\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\3\77\1\u00d5\26\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u00d6\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\6\77\1\u00d7\23\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u00d8\15\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u00d9\6\77",
            "\1\102\2\uffff\12\77\1\u00da\6\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u00db\7\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\17\77\1\u00dc\12\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\2\77\1\u00dd\20\77\1\u00de\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u00df\6\77",
            "\1\171\1\uffff\4\76",
            "",
            "\1\76\1\uffff\4\174\6\175\7\uffff\32\175\4\uffff\1\175\1\uffff\32\175",
            "\1\76\1\uffff\4\u00e0",
            "",
            "",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u00e1\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u00e2\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\12\101\7\uffff\32\u008d\4\76\1\u008c\1\76\32\u008c",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u00e4\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\2\77\1\u00e6\27\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u00e7\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u00e8\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u00e9\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u00ea\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u00eb\15\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u00ec\15\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\3\77\1\u00ed\26\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u00ee\16\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u00ef\15\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u00f0\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00f1\25\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00f2\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u00f3\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00f4\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00f5\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u00f7\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00fa\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u00fc\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00fd\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u00fe\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u00ff\7\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u0100\7\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u0101\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0102\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0103\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u0104\21\77",
            "\12\176\7\uffff\72\u00b4",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0105\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0106\6\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u0107\14\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0108\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\25\77\1\u0109\4\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u010a\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\17\77\1\u010c\12\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u010d\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u010e\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u010f\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u0110\7\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0111\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0112\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u0113\7\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u0115\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u0116\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u0117\31\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u0118\10\77",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u0119\7\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u011b\16\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u011d\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0120\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\17\77\1\u0121\12\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\27\77\1\u0122\2\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u0123\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u0126\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0128\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u012a\16\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u012b\15\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\14\77\1\u012c\15\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u012d\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u012f\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u0130\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u0131\7\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0132\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u0134\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u0135\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0137\6\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u0138\10\77",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0139\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u013a\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u013b\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u013c\16\77",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u013d\16\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u013e\10\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u013f\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u0142\31\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0143\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u0144\13\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u0146\21\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u0148\16\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\30\77\1\u014a\1\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u014c\7\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\24\77\1\u014e\5\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u014f\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\22\77\1\u0151\7\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u0154\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u0155\14\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\16\77\1\u0156\13\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0157\25\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\1\u015a\31\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\27\77\1\u015b\2\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u015f\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\27\77\1\u0160\2\77",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\13\77\1\u0161\16\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0162\6\77",
            "",
            "",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u0164\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\10\77\1\u0165\21\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0166\25\77",
            "",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u0167\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\31\77\1\u0168",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u0169\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\15\77\1\u016a\14\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\4\77\1\u016b\25\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u016c\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\23\77\1\u016d\6\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\21\77\1\u016e\10\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "\1\102\2\uffff\12\77\7\uffff\32\100\4\uffff\1\101\1\uffff\32\77",
            "",
            "",
            ""
    };

    static final short[] DFA20_eot = DFA.unpackEncodedString(DFA20_eotS);
    static final short[] DFA20_eof = DFA.unpackEncodedString(DFA20_eofS);
    static final char[] DFA20_min = DFA.unpackEncodedStringToUnsignedChars(DFA20_minS);
    static final char[] DFA20_max = DFA.unpackEncodedStringToUnsignedChars(DFA20_maxS);
    static final short[] DFA20_accept = DFA.unpackEncodedString(DFA20_acceptS);
    static final short[] DFA20_special = DFA.unpackEncodedString(DFA20_specialS);
    static final short[][] DFA20_transition;

    static {
        int numStates = DFA20_transitionS.length;
        DFA20_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA20_transition[i] = DFA.unpackEncodedString(DFA20_transitionS[i]);
        }
    }

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = DFA20_eot;
            this.eof = DFA20_eof;
            this.min = DFA20_min;
            this.max = DFA20_max;
            this.accept = DFA20_accept;
            this.special = DFA20_special;
            this.transition = DFA20_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | RULE_LOWERCASE_ID | RULE_LOWERCASE_DASHID | RULE_SHAPE | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA20_43 = input.LA(1);

                        s = -1;
                        if ( ((LA20_43>='\u0000' && LA20_43<='\uFFFF')) ) {s = 131;}

                        else s = 46;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA20_0 = input.LA(1);

                        s = -1;
                        if ( (LA20_0=='@') ) {s = 1;}

                        else if ( (LA20_0==',') ) {s = 2;}

                        else if ( (LA20_0=='e') ) {s = 3;}

                        else if ( (LA20_0=='o') ) {s = 4;}

                        else if ( (LA20_0=='i') ) {s = 5;}

                        else if ( (LA20_0=='m') ) {s = 6;}

                        else if ( (LA20_0=='+') ) {s = 7;}

                        else if ( (LA20_0=='p') ) {s = 8;}

                        else if ( (LA20_0=='f') ) {s = 9;}

                        else if ( (LA20_0=='{') ) {s = 10;}

                        else if ( (LA20_0=='}') ) {s = 11;}

                        else if ( (LA20_0=='a') ) {s = 12;}

                        else if ( (LA20_0=='n') ) {s = 13;}

                        else if ( (LA20_0=='b') ) {s = 14;}

                        else if ( (LA20_0=='t') ) {s = 15;}

                        else if ( (LA20_0=='l') ) {s = 16;}

                        else if ( (LA20_0=='v') ) {s = 17;}

                        else if ( (LA20_0=='d') ) {s = 18;}

                        else if ( (LA20_0=='r') ) {s = 19;}

                        else if ( (LA20_0=='g') ) {s = 20;}

                        else if ( (LA20_0=='u') ) {s = 21;}

                        else if ( (LA20_0=='c') ) {s = 22;}

                        else if ( (LA20_0=='*') ) {s = 23;}

                        else if ( (LA20_0=='s') ) {s = 24;}

                        else if ( (LA20_0=='(') ) {s = 25;}

                        else if ( (LA20_0==')') ) {s = 26;}

                        else if ( (LA20_0==':') ) {s = 27;}

                        else if ( (LA20_0=='#') ) {s = 28;}

                        else if ( (LA20_0=='/') ) {s = 29;}

                        else if ( (LA20_0=='.') ) {s = 30;}

                        else if ( (LA20_0=='=') ) {s = 31;}

                        else if ( (LA20_0=='>') ) {s = 32;}

                        else if ( (LA20_0=='<') ) {s = 33;}

                        else if ( (LA20_0=='!') ) {s = 34;}

                        else if ( (LA20_0=='-') ) {s = 35;}

                        else if ( (LA20_0=='E') ) {s = 36;}

                        else if ( (LA20_0=='^') ) {s = 37;}

                        else if ( (LA20_0=='h'||(LA20_0>='j' && LA20_0<='k')||LA20_0=='q'||(LA20_0>='w' && LA20_0<='z')) ) {s = 38;}

                        else if ( ((LA20_0>='A' && LA20_0<='D')||(LA20_0>='F' && LA20_0<='Z')) ) {s = 39;}

                        else if ( (LA20_0=='_') ) {s = 40;}

                        else if ( ((LA20_0>='[' && LA20_0<=']')||LA20_0=='`') ) {s = 41;}

                        else if ( ((LA20_0>='0' && LA20_0<='9')) ) {s = 42;}

                        else if ( (LA20_0=='\"') ) {s = 43;}

                        else if ( (LA20_0=='\'') ) {s = 44;}

                        else if ( ((LA20_0>='\t' && LA20_0<='\n')||LA20_0=='\r'||LA20_0==' ') ) {s = 45;}

                        else if ( ((LA20_0>='\u0000' && LA20_0<='\b')||(LA20_0>='\u000B' && LA20_0<='\f')||(LA20_0>='\u000E' && LA20_0<='\u001F')||(LA20_0>='$' && LA20_0<='&')||LA20_0==';'||LA20_0=='?'||LA20_0=='|'||(LA20_0>='~' && LA20_0<='\uFFFF')) ) {s = 46;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA20_44 = input.LA(1);

                        s = -1;
                        if ( ((LA20_44>='\u0000' && LA20_44<='\uFFFF')) ) {s = 131;}

                        else s = 46;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 20, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}