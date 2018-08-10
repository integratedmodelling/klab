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
    public static final int RULE_ML_COMMENT=14;
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
    public static final int RULE_EXPR=11;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int RULE_UPPERCASE_PATH=13;
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
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int RULE_LOWERCASE_DASHID=7;
    public static final int RULE_CAMELCASE_ID=12;
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
    public static final int RULE_SL_COMMENT=15;
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
    public static final int RULE_WS=16;
    public static final int RULE_ANY_OTHER=17;
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

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
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
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
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
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
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
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
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
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
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
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
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
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
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
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
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
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
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
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
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
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
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
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
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
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
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
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
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
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
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
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
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
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
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
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
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
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
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
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
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
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
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
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
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
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
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
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
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
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
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
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
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
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
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
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
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
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
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
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
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
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
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
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
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
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
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
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
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
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
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
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
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
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
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
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
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
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
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
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
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
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
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
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
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
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
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
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
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
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
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
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
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
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
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
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
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
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
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
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
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
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
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
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
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
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
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
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
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
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
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
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
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
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:67:7: ( 'definitions' )
            // InternalKdl.g:67:9: 'definitions'
            {
            match("definitions"); 


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
            // InternalKdl.g:68:7: ( 'true' )
            // InternalKdl.g:68:9: 'true'
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
            // InternalKdl.g:69:7: ( 'false' )
            // InternalKdl.g:69:9: 'false'
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
            // InternalKdl.g:70:7: ( 'inclusive' )
            // InternalKdl.g:70:9: 'inclusive'
            {
            match("inclusive"); 


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
            // InternalKdl.g:71:7: ( 'exclusive' )
            // InternalKdl.g:71:9: 'exclusive'
            {
            match("exclusive"); 


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
            // InternalKdl.g:72:7: ( 'in' )
            // InternalKdl.g:72:9: 'in'
            {
            match("in"); 


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
            // InternalKdl.g:73:7: ( '(' )
            // InternalKdl.g:73:9: '('
            {
            match('('); 

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
            // InternalKdl.g:74:7: ( ')' )
            // InternalKdl.g:74:9: ')'
            {
            match(')'); 

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
            // InternalKdl.g:75:7: ( 'unknown' )
            // InternalKdl.g:75:9: 'unknown'
            {
            match("unknown"); 


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
            // InternalKdl.g:76:7: ( 'urn:klab:' )
            // InternalKdl.g:76:9: 'urn:klab:'
            {
            match("urn:klab:"); 


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
            // InternalKdl.g:77:7: ( ':' )
            // InternalKdl.g:77:9: ':'
            {
            match(':'); 

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
            // InternalKdl.g:78:7: ( '#' )
            // InternalKdl.g:78:9: '#'
            {
            match('#'); 

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
            // InternalKdl.g:79:7: ( '{{' )
            // InternalKdl.g:79:9: '{{'
            {
            match("{{"); 


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
            // InternalKdl.g:80:7: ( '}}' )
            // InternalKdl.g:80:9: '}}'
            {
            match("}}"); 


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
            // InternalKdl.g:81:7: ( '|' )
            // InternalKdl.g:81:9: '|'
            {
            match('|'); 

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
            // InternalKdl.g:82:7: ( '/' )
            // InternalKdl.g:82:9: '/'
            {
            match('/'); 

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
            // InternalKdl.g:83:7: ( '.' )
            // InternalKdl.g:83:9: '.'
            {
            match('.'); 

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
            // InternalKdl.g:84:7: ( '=?' )
            // InternalKdl.g:84:9: '=?'
            {
            match("=?"); 


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
            // InternalKdl.g:85:7: ( '=' )
            // InternalKdl.g:85:9: '='
            {
            match('='); 

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
            // InternalKdl.g:86:7: ( '>>' )
            // InternalKdl.g:86:9: '>>'
            {
            match(">>"); 


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
            // InternalKdl.g:87:7: ( '>' )
            // InternalKdl.g:87:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:88:7: ( '<' )
            // InternalKdl.g:88:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:89:7: ( '!=' )
            // InternalKdl.g:89:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "T__97"
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:90:7: ( '<=' )
            // InternalKdl.g:90:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__97"

    // $ANTLR start "T__98"
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:91:7: ( '>=' )
            // InternalKdl.g:91:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__98"

    // $ANTLR start "T__99"
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:92:7: ( '-' )
            // InternalKdl.g:92:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__99"

    // $ANTLR start "T__100"
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:93:8: ( 'e' )
            // InternalKdl.g:93:10: 'e'
            {
            match('e'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__100"

    // $ANTLR start "T__101"
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:94:8: ( 'E' )
            // InternalKdl.g:94:10: 'E'
            {
            match('E'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__101"

    // $ANTLR start "T__102"
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:95:8: ( '^' )
            // InternalKdl.g:95:10: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__102"

    // $ANTLR start "RULE_EXPR"
    public final void mRULE_EXPR() throws RecognitionException {
        try {
            int _type = RULE_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:5201:11: ( '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']' )
            // InternalKdl.g:5201:13: '[' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )* ']'
            {
            match('['); 
            // InternalKdl.g:5201:17: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' ) | ~ ( ( '\\\\' | ']' ) ) )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='\\') ) {
                    alt1=1;
                }
                else if ( ((LA1_0>='\u0000' && LA1_0<='[')||(LA1_0>='^' && LA1_0<='\uFFFF')) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalKdl.g:5201:18: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | ']' | '\\\\' )
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
            	    // InternalKdl.g:5201:58: ~ ( ( '\\\\' | ']' ) )
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
            	    break loop1;
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

    // $ANTLR start "RULE_LOWERCASE_ID"
    public final void mRULE_LOWERCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:5203:19: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )* )
            // InternalKdl.g:5203:21: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )*
            {
            matchRange('a','z'); 
            // InternalKdl.g:5203:30: ( 'a' .. 'z' | '0' .. '9' | '_' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
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
    // $ANTLR end "RULE_LOWERCASE_ID"

    // $ANTLR start "RULE_LOWERCASE_DASHID"
    public final void mRULE_LOWERCASE_DASHID() throws RecognitionException {
        try {
            int _type = RULE_LOWERCASE_DASHID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:5205:23: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '-' )* )
            // InternalKdl.g:5205:25: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '-' )*
            {
            matchRange('a','z'); 
            // InternalKdl.g:5205:34: ( 'a' .. 'z' | '0' .. '9' | '-' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='-'||(LA3_0>='0' && LA3_0<='9')||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
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
    // $ANTLR end "RULE_LOWERCASE_DASHID"

    // $ANTLR start "RULE_SHAPE"
    public final void mRULE_SHAPE() throws RecognitionException {
        try {
            int _type = RULE_SHAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:5207:12: ( ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ ) )
            // InternalKdl.g:5207:14: ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ )
            {
            // InternalKdl.g:5207:14: ( '#' | ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+ )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='#') ) {
                int LA6_1 = input.LA(2);

                if ( ((LA6_1>='A' && LA6_1<='z')) ) {
                    alt6=2;
                }
                else {
                    alt6=1;}
            }
            else if ( ((LA6_0>='A' && LA6_0<='z')) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalKdl.g:5207:15: '#'
                    {
                    match('#'); 

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5207:19: ( '#' )? ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+
                    {
                    // InternalKdl.g:5207:19: ( '#' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='#') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // InternalKdl.g:5207:19: '#'
                            {
                            match('#'); 

                            }
                            break;

                    }

                    // InternalKdl.g:5207:24: ( 'A' .. 'z' ( '.' | '0' .. '3' ) )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='A' && LA5_0<='z')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalKdl.g:5207:25: 'A' .. 'z' ( '.' | '0' .. '3' )
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
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
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
            // InternalKdl.g:5209:19: ( 'A' .. 'Z' ( 'A' .. 'Z' )* )
            // InternalKdl.g:5209:21: 'A' .. 'Z' ( 'A' .. 'Z' )*
            {
            matchRange('A','Z'); 
            // InternalKdl.g:5209:30: ( 'A' .. 'Z' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='A' && LA7_0<='Z')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKdl.g:5209:31: 'A' .. 'Z'
            	    {
            	    matchRange('A','Z'); 

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
    // $ANTLR end "RULE_UPPERCASE_ID"

    // $ANTLR start "RULE_UPPERCASE_PATH"
    public final void mRULE_UPPERCASE_PATH() throws RecognitionException {
        try {
            int _type = RULE_UPPERCASE_PATH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:5211:21: ( RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )* )
            // InternalKdl.g:5211:23: RULE_UPPERCASE_ID ( '.' RULE_UPPERCASE_ID )*
            {
            mRULE_UPPERCASE_ID(); 
            // InternalKdl.g:5211:41: ( '.' RULE_UPPERCASE_ID )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='.') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalKdl.g:5211:42: '.' RULE_UPPERCASE_ID
            	    {
            	    match('.'); 
            	    mRULE_UPPERCASE_ID(); 

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
    // $ANTLR end "RULE_UPPERCASE_PATH"

    // $ANTLR start "RULE_CAMELCASE_ID"
    public final void mRULE_CAMELCASE_ID() throws RecognitionException {
        try {
            int _type = RULE_CAMELCASE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:5213:19: ( 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )* )
            // InternalKdl.g:5213:21: 'A' .. 'Z' ( 'A' .. 'z' | '0' .. '9' )*
            {
            matchRange('A','Z'); 
            // InternalKdl.g:5213:30: ( 'A' .. 'z' | '0' .. '9' )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='0' && LA9_0<='9')||(LA9_0>='A' && LA9_0<='z')) ) {
                    alt9=1;
                }


                switch (alt9) {
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
            	    break loop9;
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
            // InternalKdl.g:5215:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalKdl.g:5215:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalKdl.g:5215:11: ( '^' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='^') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalKdl.g:5215:11: '^'
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

            // InternalKdl.g:5215:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')||(LA11_0>='A' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')) ) {
                    alt11=1;
                }


                switch (alt11) {
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
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKdl.g:5217:10: ( ( '0' .. '9' )+ )
            // InternalKdl.g:5217:12: ( '0' .. '9' )+
            {
            // InternalKdl.g:5217:12: ( '0' .. '9' )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalKdl.g:5217:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
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
            // InternalKdl.g:5219:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalKdl.g:5219:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalKdl.g:5219:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\"') ) {
                alt15=1;
            }
            else if ( (LA15_0=='\'') ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalKdl.g:5219:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalKdl.g:5219:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop13:
                    do {
                        int alt13=3;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0=='\\') ) {
                            alt13=1;
                        }
                        else if ( ((LA13_0>='\u0000' && LA13_0<='!')||(LA13_0>='#' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFF')) ) {
                            alt13=2;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // InternalKdl.g:5219:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKdl.g:5219:28: ~ ( ( '\\\\' | '\"' ) )
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
                    	    break loop13;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalKdl.g:5219:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalKdl.g:5219:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop14:
                    do {
                        int alt14=3;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0=='\\') ) {
                            alt14=1;
                        }
                        else if ( ((LA14_0>='\u0000' && LA14_0<='&')||(LA14_0>='(' && LA14_0<='[')||(LA14_0>=']' && LA14_0<='\uFFFF')) ) {
                            alt14=2;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // InternalKdl.g:5219:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalKdl.g:5219:61: ~ ( ( '\\\\' | '\\'' ) )
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
                    	    break loop14;
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
            // InternalKdl.g:5221:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalKdl.g:5221:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalKdl.g:5221:24: ( options {greedy=false; } : . )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='*') ) {
                    int LA16_1 = input.LA(2);

                    if ( (LA16_1=='/') ) {
                        alt16=2;
                    }
                    else if ( ((LA16_1>='\u0000' && LA16_1<='.')||(LA16_1>='0' && LA16_1<='\uFFFF')) ) {
                        alt16=1;
                    }


                }
                else if ( ((LA16_0>='\u0000' && LA16_0<=')')||(LA16_0>='+' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalKdl.g:5221:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop16;
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
            // InternalKdl.g:5223:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKdl.g:5223:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalKdl.g:5223:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='\u0000' && LA17_0<='\t')||(LA17_0>='\u000B' && LA17_0<='\f')||(LA17_0>='\u000E' && LA17_0<='\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalKdl.g:5223:24: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop17;
                }
            } while (true);

            // InternalKdl.g:5223:40: ( ( '\\r' )? '\\n' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='\n'||LA19_0=='\r') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalKdl.g:5223:41: ( '\\r' )? '\\n'
                    {
                    // InternalKdl.g:5223:41: ( '\\r' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='\r') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKdl.g:5223:41: '\\r'
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
            // InternalKdl.g:5225:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKdl.g:5225:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKdl.g:5225:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='\t' && LA20_0<='\n')||LA20_0=='\r'||LA20_0==' ') ) {
                    alt20=1;
                }


                switch (alt20) {
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
            	    if ( cnt20 >= 1 ) break loop20;
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
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
            // InternalKdl.g:5227:16: ( . )
            // InternalKdl.g:5227:18: .
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
        // InternalKdl.g:1:8: ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | RULE_EXPR | RULE_LOWERCASE_ID | RULE_LOWERCASE_DASHID | RULE_SHAPE | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt21=99;
        alt21 = dfa21.predict(input);
        switch (alt21) {
            case 1 :
                // InternalKdl.g:1:10: T__18
                {
                mT__18(); 

                }
                break;
            case 2 :
                // InternalKdl.g:1:16: T__19
                {
                mT__19(); 

                }
                break;
            case 3 :
                // InternalKdl.g:1:22: T__20
                {
                mT__20(); 

                }
                break;
            case 4 :
                // InternalKdl.g:1:28: T__21
                {
                mT__21(); 

                }
                break;
            case 5 :
                // InternalKdl.g:1:34: T__22
                {
                mT__22(); 

                }
                break;
            case 6 :
                // InternalKdl.g:1:40: T__23
                {
                mT__23(); 

                }
                break;
            case 7 :
                // InternalKdl.g:1:46: T__24
                {
                mT__24(); 

                }
                break;
            case 8 :
                // InternalKdl.g:1:52: T__25
                {
                mT__25(); 

                }
                break;
            case 9 :
                // InternalKdl.g:1:58: T__26
                {
                mT__26(); 

                }
                break;
            case 10 :
                // InternalKdl.g:1:64: T__27
                {
                mT__27(); 

                }
                break;
            case 11 :
                // InternalKdl.g:1:70: T__28
                {
                mT__28(); 

                }
                break;
            case 12 :
                // InternalKdl.g:1:76: T__29
                {
                mT__29(); 

                }
                break;
            case 13 :
                // InternalKdl.g:1:82: T__30
                {
                mT__30(); 

                }
                break;
            case 14 :
                // InternalKdl.g:1:88: T__31
                {
                mT__31(); 

                }
                break;
            case 15 :
                // InternalKdl.g:1:94: T__32
                {
                mT__32(); 

                }
                break;
            case 16 :
                // InternalKdl.g:1:100: T__33
                {
                mT__33(); 

                }
                break;
            case 17 :
                // InternalKdl.g:1:106: T__34
                {
                mT__34(); 

                }
                break;
            case 18 :
                // InternalKdl.g:1:112: T__35
                {
                mT__35(); 

                }
                break;
            case 19 :
                // InternalKdl.g:1:118: T__36
                {
                mT__36(); 

                }
                break;
            case 20 :
                // InternalKdl.g:1:124: T__37
                {
                mT__37(); 

                }
                break;
            case 21 :
                // InternalKdl.g:1:130: T__38
                {
                mT__38(); 

                }
                break;
            case 22 :
                // InternalKdl.g:1:136: T__39
                {
                mT__39(); 

                }
                break;
            case 23 :
                // InternalKdl.g:1:142: T__40
                {
                mT__40(); 

                }
                break;
            case 24 :
                // InternalKdl.g:1:148: T__41
                {
                mT__41(); 

                }
                break;
            case 25 :
                // InternalKdl.g:1:154: T__42
                {
                mT__42(); 

                }
                break;
            case 26 :
                // InternalKdl.g:1:160: T__43
                {
                mT__43(); 

                }
                break;
            case 27 :
                // InternalKdl.g:1:166: T__44
                {
                mT__44(); 

                }
                break;
            case 28 :
                // InternalKdl.g:1:172: T__45
                {
                mT__45(); 

                }
                break;
            case 29 :
                // InternalKdl.g:1:178: T__46
                {
                mT__46(); 

                }
                break;
            case 30 :
                // InternalKdl.g:1:184: T__47
                {
                mT__47(); 

                }
                break;
            case 31 :
                // InternalKdl.g:1:190: T__48
                {
                mT__48(); 

                }
                break;
            case 32 :
                // InternalKdl.g:1:196: T__49
                {
                mT__49(); 

                }
                break;
            case 33 :
                // InternalKdl.g:1:202: T__50
                {
                mT__50(); 

                }
                break;
            case 34 :
                // InternalKdl.g:1:208: T__51
                {
                mT__51(); 

                }
                break;
            case 35 :
                // InternalKdl.g:1:214: T__52
                {
                mT__52(); 

                }
                break;
            case 36 :
                // InternalKdl.g:1:220: T__53
                {
                mT__53(); 

                }
                break;
            case 37 :
                // InternalKdl.g:1:226: T__54
                {
                mT__54(); 

                }
                break;
            case 38 :
                // InternalKdl.g:1:232: T__55
                {
                mT__55(); 

                }
                break;
            case 39 :
                // InternalKdl.g:1:238: T__56
                {
                mT__56(); 

                }
                break;
            case 40 :
                // InternalKdl.g:1:244: T__57
                {
                mT__57(); 

                }
                break;
            case 41 :
                // InternalKdl.g:1:250: T__58
                {
                mT__58(); 

                }
                break;
            case 42 :
                // InternalKdl.g:1:256: T__59
                {
                mT__59(); 

                }
                break;
            case 43 :
                // InternalKdl.g:1:262: T__60
                {
                mT__60(); 

                }
                break;
            case 44 :
                // InternalKdl.g:1:268: T__61
                {
                mT__61(); 

                }
                break;
            case 45 :
                // InternalKdl.g:1:274: T__62
                {
                mT__62(); 

                }
                break;
            case 46 :
                // InternalKdl.g:1:280: T__63
                {
                mT__63(); 

                }
                break;
            case 47 :
                // InternalKdl.g:1:286: T__64
                {
                mT__64(); 

                }
                break;
            case 48 :
                // InternalKdl.g:1:292: T__65
                {
                mT__65(); 

                }
                break;
            case 49 :
                // InternalKdl.g:1:298: T__66
                {
                mT__66(); 

                }
                break;
            case 50 :
                // InternalKdl.g:1:304: T__67
                {
                mT__67(); 

                }
                break;
            case 51 :
                // InternalKdl.g:1:310: T__68
                {
                mT__68(); 

                }
                break;
            case 52 :
                // InternalKdl.g:1:316: T__69
                {
                mT__69(); 

                }
                break;
            case 53 :
                // InternalKdl.g:1:322: T__70
                {
                mT__70(); 

                }
                break;
            case 54 :
                // InternalKdl.g:1:328: T__71
                {
                mT__71(); 

                }
                break;
            case 55 :
                // InternalKdl.g:1:334: T__72
                {
                mT__72(); 

                }
                break;
            case 56 :
                // InternalKdl.g:1:340: T__73
                {
                mT__73(); 

                }
                break;
            case 57 :
                // InternalKdl.g:1:346: T__74
                {
                mT__74(); 

                }
                break;
            case 58 :
                // InternalKdl.g:1:352: T__75
                {
                mT__75(); 

                }
                break;
            case 59 :
                // InternalKdl.g:1:358: T__76
                {
                mT__76(); 

                }
                break;
            case 60 :
                // InternalKdl.g:1:364: T__77
                {
                mT__77(); 

                }
                break;
            case 61 :
                // InternalKdl.g:1:370: T__78
                {
                mT__78(); 

                }
                break;
            case 62 :
                // InternalKdl.g:1:376: T__79
                {
                mT__79(); 

                }
                break;
            case 63 :
                // InternalKdl.g:1:382: T__80
                {
                mT__80(); 

                }
                break;
            case 64 :
                // InternalKdl.g:1:388: T__81
                {
                mT__81(); 

                }
                break;
            case 65 :
                // InternalKdl.g:1:394: T__82
                {
                mT__82(); 

                }
                break;
            case 66 :
                // InternalKdl.g:1:400: T__83
                {
                mT__83(); 

                }
                break;
            case 67 :
                // InternalKdl.g:1:406: T__84
                {
                mT__84(); 

                }
                break;
            case 68 :
                // InternalKdl.g:1:412: T__85
                {
                mT__85(); 

                }
                break;
            case 69 :
                // InternalKdl.g:1:418: T__86
                {
                mT__86(); 

                }
                break;
            case 70 :
                // InternalKdl.g:1:424: T__87
                {
                mT__87(); 

                }
                break;
            case 71 :
                // InternalKdl.g:1:430: T__88
                {
                mT__88(); 

                }
                break;
            case 72 :
                // InternalKdl.g:1:436: T__89
                {
                mT__89(); 

                }
                break;
            case 73 :
                // InternalKdl.g:1:442: T__90
                {
                mT__90(); 

                }
                break;
            case 74 :
                // InternalKdl.g:1:448: T__91
                {
                mT__91(); 

                }
                break;
            case 75 :
                // InternalKdl.g:1:454: T__92
                {
                mT__92(); 

                }
                break;
            case 76 :
                // InternalKdl.g:1:460: T__93
                {
                mT__93(); 

                }
                break;
            case 77 :
                // InternalKdl.g:1:466: T__94
                {
                mT__94(); 

                }
                break;
            case 78 :
                // InternalKdl.g:1:472: T__95
                {
                mT__95(); 

                }
                break;
            case 79 :
                // InternalKdl.g:1:478: T__96
                {
                mT__96(); 

                }
                break;
            case 80 :
                // InternalKdl.g:1:484: T__97
                {
                mT__97(); 

                }
                break;
            case 81 :
                // InternalKdl.g:1:490: T__98
                {
                mT__98(); 

                }
                break;
            case 82 :
                // InternalKdl.g:1:496: T__99
                {
                mT__99(); 

                }
                break;
            case 83 :
                // InternalKdl.g:1:502: T__100
                {
                mT__100(); 

                }
                break;
            case 84 :
                // InternalKdl.g:1:509: T__101
                {
                mT__101(); 

                }
                break;
            case 85 :
                // InternalKdl.g:1:516: T__102
                {
                mT__102(); 

                }
                break;
            case 86 :
                // InternalKdl.g:1:523: RULE_EXPR
                {
                mRULE_EXPR(); 

                }
                break;
            case 87 :
                // InternalKdl.g:1:533: RULE_LOWERCASE_ID
                {
                mRULE_LOWERCASE_ID(); 

                }
                break;
            case 88 :
                // InternalKdl.g:1:551: RULE_LOWERCASE_DASHID
                {
                mRULE_LOWERCASE_DASHID(); 

                }
                break;
            case 89 :
                // InternalKdl.g:1:573: RULE_SHAPE
                {
                mRULE_SHAPE(); 

                }
                break;
            case 90 :
                // InternalKdl.g:1:584: RULE_UPPERCASE_ID
                {
                mRULE_UPPERCASE_ID(); 

                }
                break;
            case 91 :
                // InternalKdl.g:1:602: RULE_UPPERCASE_PATH
                {
                mRULE_UPPERCASE_PATH(); 

                }
                break;
            case 92 :
                // InternalKdl.g:1:622: RULE_CAMELCASE_ID
                {
                mRULE_CAMELCASE_ID(); 

                }
                break;
            case 93 :
                // InternalKdl.g:1:640: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 94 :
                // InternalKdl.g:1:648: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 95 :
                // InternalKdl.g:1:657: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 96 :
                // InternalKdl.g:1:669: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 97 :
                // InternalKdl.g:1:685: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 98 :
                // InternalKdl.g:1:701: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 99 :
                // InternalKdl.g:1:709: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA21_eotS =
        "\1\uffff\1\60\1\uffff\1\100\3\107\1\uffff\2\107\1\125\1\127\13\107\1\uffff\1\107\3\uffff\1\156\1\uffff\1\162\1\uffff\1\165\1\170\1\172\1\60\1\uffff\1\176\1\u0083\1\60\1\107\1\u0086\1\103\1\60\1\uffff\2\60\14\uffff\3\107\1\uffff\1\107\2\uffff\1\107\1\uffff\3\107\1\uffff\1\107\1\u009c\5\107\1\uffff\4\107\4\uffff\1\u00a6\4\107\1\u00ac\13\107\1\uffff\1\107\22\uffff\1\76\1\uffff\1\u0086\1\76\1\u0082\2\uffff\1\76\2\uffff\1\76\6\uffff\6\107\1\103\7\107\1\uffff\7\107\1\u00da\1\107\1\uffff\5\107\1\uffff\16\107\1\u00bc\1\uffff\2\u0082\1\u0085\6\uffff\3\107\1\u00f5\2\107\1\u00f7\15\107\1\uffff\4\107\1\u0109\1\107\1\u010b\1\u010c\1\107\1\u010e\6\107\1\uffff\5\107\1\76\3\107\1\uffff\1\107\1\uffff\3\107\1\u0121\11\107\1\u012b\3\107\1\uffff\1\107\2\uffff\1\u0131\1\uffff\2\107\1\u0134\1\107\1\u0136\1\107\1\u0138\4\107\1\u013d\1\u013e\2\107\1\u0141\1\107\1\u0143\1\uffff\5\107\1\u0149\3\107\1\uffff\1\107\1\u014e\2\107\1\u0151\1\uffff\2\107\1\uffff\1\107\1\uffff\1\107\1\uffff\4\107\2\uffff\2\107\1\uffff\1\107\1\uffff\2\107\1\u015f\1\u0160\1\107\1\uffff\2\107\1\u0164\1\107\1\uffff\1\u0166\1\107\1\uffff\1\u0168\2\107\1\u016b\1\u016c\1\u016e\3\107\1\u0172\2\107\1\u0175\2\uffff\1\u0176\2\107\1\uffff\1\107\1\uffff\1\107\1\uffff\1\107\1\u017c\2\uffff\1\u017d\1\uffff\2\107\1\u0180\1\uffff\1\u0181\1\u0182\2\uffff\1\u0183\1\u0184\3\107\2\uffff\2\107\5\uffff\1\u018a\4\107\1\uffff\1\107\1\u0190\3\107\1\uffff\4\107\1\u0198\1\u0199\1\u019a\3\uffff";
    static final String DFA21_eofS =
        "\u019b\uffff";
    static final String DFA21_minS =
        "\1\0\1\141\1\uffff\4\55\1\uffff\2\55\1\173\1\175\13\55\1\uffff\1\55\3\uffff\1\101\1\uffff\1\52\1\uffff\1\77\3\75\1\uffff\2\56\1\0\1\55\3\56\1\uffff\2\0\3\uffff\1\141\6\uffff\1\157\1\uffff\3\55\1\uffff\1\55\2\uffff\1\60\1\uffff\3\55\1\uffff\7\55\1\uffff\4\55\4\uffff\21\55\1\uffff\1\55\22\uffff\1\101\1\uffff\1\56\2\60\2\uffff\1\0\2\uffff\1\60\3\uffff\1\154\1\uffff\1\156\5\55\2\56\7\55\1\uffff\11\55\1\uffff\5\55\1\uffff\16\55\1\56\1\uffff\4\56\1\0\4\uffff\4\55\1\60\17\55\1\uffff\20\55\1\uffff\5\55\1\60\3\55\1\uffff\1\55\1\uffff\21\55\1\uffff\1\55\2\uffff\1\55\1\uffff\22\55\1\uffff\11\55\1\uffff\5\55\1\uffff\2\55\1\uffff\1\55\1\uffff\1\55\1\uffff\4\55\2\uffff\2\55\1\uffff\1\55\1\uffff\5\55\1\uffff\4\55\1\uffff\2\55\1\uffff\15\55\2\uffff\3\55\1\uffff\1\55\1\uffff\1\55\1\uffff\2\55\2\uffff\1\55\1\uffff\3\55\1\uffff\2\55\2\uffff\5\55\2\uffff\2\55\5\uffff\5\55\1\uffff\5\55\1\uffff\7\55\3\uffff";
    static final String DFA21_maxS =
        "\1\uffff\1\167\1\uffff\4\172\1\uffff\2\172\1\173\1\175\13\172\1\uffff\1\172\3\uffff\1\172\1\uffff\1\57\1\uffff\1\77\1\76\2\75\1\uffff\2\172\1\uffff\2\172\2\63\1\uffff\2\uffff\3\uffff\1\145\6\uffff\1\157\1\uffff\3\172\1\uffff\1\172\2\uffff\1\172\1\uffff\3\172\1\uffff\7\172\1\uffff\4\172\4\uffff\21\172\1\uffff\1\172\22\uffff\1\132\1\uffff\3\172\2\uffff\1\uffff\2\uffff\1\172\3\uffff\1\162\1\uffff\1\166\6\172\1\63\7\172\1\uffff\11\172\1\uffff\5\172\1\uffff\16\172\1\63\1\uffff\1\172\2\63\1\165\1\uffff\4\uffff\24\172\1\uffff\20\172\1\uffff\11\172\1\uffff\1\172\1\uffff\21\172\1\uffff\1\172\2\uffff\1\172\1\uffff\22\172\1\uffff\11\172\1\uffff\5\172\1\uffff\2\172\1\uffff\1\172\1\uffff\1\172\1\uffff\4\172\2\uffff\2\172\1\uffff\1\172\1\uffff\5\172\1\uffff\4\172\1\uffff\2\172\1\uffff\15\172\2\uffff\3\172\1\uffff\1\172\1\uffff\1\172\1\uffff\2\172\2\uffff\1\172\1\uffff\3\172\1\uffff\2\172\2\uffff\5\172\2\uffff\2\172\5\uffff\5\172\1\uffff\5\172\1\uffff\7\172\3\uffff";
    static final String DFA21_acceptS =
        "\2\uffff\1\14\4\uffff\1\22\17\uffff\1\52\1\uffff\1\77\1\100\1\103\1\uffff\1\107\1\uffff\1\111\4\uffff\1\122\7\uffff\1\136\2\uffff\1\142\1\143\1\1\1\uffff\1\4\1\6\1\7\1\10\1\11\1\12\1\uffff\1\14\3\uffff\1\131\1\uffff\1\123\1\130\1\uffff\1\135\3\uffff\1\127\7\uffff\1\22\4\uffff\1\105\1\25\1\106\1\26\21\uffff\1\52\1\uffff\1\77\1\100\1\103\1\104\1\107\1\140\1\141\1\110\1\111\1\112\1\113\1\114\1\121\1\115\1\120\1\116\1\117\1\122\1\uffff\1\124\3\uffff\1\134\1\125\1\uffff\1\126\1\132\1\uffff\1\136\1\137\1\142\1\uffff\1\5\17\uffff\1\76\11\uffff\1\27\5\uffff\1\44\17\uffff\1\133\5\uffff\1\2\1\3\1\13\1\15\24\uffff\1\24\20\uffff\1\102\11\uffff\1\35\1\uffff\1\30\21\uffff\1\33\1\uffff\1\72\1\34\1\uffff\1\64\22\uffff\1\36\11\uffff\1\73\5\uffff\1\55\2\uffff\1\43\1\uffff\1\46\1\uffff\1\50\4\uffff\1\16\1\57\2\uffff\1\53\1\uffff\1\20\5\uffff\1\66\4\uffff\1\31\2\uffff\1\37\15\uffff\1\41\1\42\3\uffff\1\54\1\uffff\1\32\1\uffff\1\40\2\uffff\1\101\1\51\1\uffff\1\56\3\uffff\1\17\2\uffff\1\21\1\47\5\uffff\1\45\1\67\2\uffff\1\75\1\70\1\74\1\23\1\65\5\uffff\1\62\5\uffff\1\71\7\uffff\1\60\1\61\1\63";
    static final String DFA21_specialS =
        "\1\4\46\uffff\1\2\5\uffff\1\1\1\3\125\uffff\1\5\74\uffff\1\0\u00d9\uffff}>";
    static final String[] DFA21_transitionS = {
            "\11\60\2\57\2\60\1\57\22\60\1\57\1\43\1\55\1\34\3\60\1\56\1\31\1\32\1\27\1\7\1\2\1\44\1\37\1\36\12\54\1\33\1\60\1\42\1\40\1\41\1\60\1\1\4\51\1\45\25\51\1\47\2\53\1\46\1\52\1\53\1\14\1\16\1\26\1\22\1\3\1\11\1\24\1\50\1\5\2\50\1\20\1\6\1\15\1\4\1\10\1\50\1\23\1\30\1\17\1\25\1\21\4\50\1\12\1\35\1\13\uff82\60",
            "\1\63\1\uffff\1\71\1\61\1\67\1\uffff\1\66\3\uffff\1\64\2\uffff\1\70\7\uffff\1\62\1\65",
            "",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\74\11\77\1\73\2\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\77\1\106\15\77\1\104\5\77\1\105\4\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\110\1\111\14\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\114\3\77\1\115\3\77\1\113\5\77\1\116\5\77\1\112\5\77",
            "",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\120\20\77\1\121\10\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\123\15\77\1\122\13\77",
            "\1\124",
            "\1\126",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\131\4\77\1\130\7\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\132\5\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\133\13\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\134\11\77\1\135\2\77\1\136\10\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\137\21\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\140\15\77\1\141\13\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\142\25\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\143\31\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\144\25\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\145\3\77\1\146\10\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\147\2\77\1\150\13\77",
            "",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\17\77\1\152\12\77",
            "",
            "",
            "",
            "\72\76",
            "",
            "\1\160\4\uffff\1\161",
            "",
            "\1\164",
            "\1\167\1\166",
            "\1\171",
            "\1\173",
            "",
            "\1\175\1\uffff\4\u0080\6\u0081\7\uffff\32\177\4\u0082\1\u0081\1\u0082\32\u0081",
            "\1\76\1\uffff\4\76\15\uffff\32\103\4\uffff\1\103\1\uffff\32\103",
            "\56\u0085\1\u0084\1\u0085\4\u0084\uffcc\u0085",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\175\1\uffff\4\u0080\6\u0081\7\uffff\32\177\4\u0082\1\u0081\1\u0082\32\u0081",
            "\1\76\1\uffff\4\u0087",
            "\1\76\1\uffff\4\76",
            "",
            "\0\u0089",
            "\0\u0089",
            "",
            "",
            "",
            "\1\u008b\3\uffff\1\u008c",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u008d",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\2\77\1\u0090\14\77\1\u008e\3\77\1\u008f\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u0091\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\u0094\4\76\1\u0093\1\76\32\u0092",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "",
            "\12\102\7\uffff\32\103\4\uffff\1\102\1\uffff\32\102",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0095\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0096\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\11\77\1\u0097\10\77\1\u0098\7\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\17\77\1\u0099\12\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\2\77\1\u009b\14\77\1\u009a\12\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u009d\16\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u009e\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\27\77\1\u009f\2\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u00a0\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\3\77\1\u00a1\26\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u00a2\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u00a3\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u00a4\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u00a5\16\77",
            "",
            "",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u00a7\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u00a8\15\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u00a9\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u00ab\12\77\1\u00aa\2\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u00ad\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u00ae\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u00af\16\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u00b0\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\5\77\1\u00b1\24\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u00b2\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u00b3\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u00b4\1\77\1\u00b5\17\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u00b6\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u00b7\31\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u00b8\1\u00b9\14\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u00ba\31\77",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\32\u00bb",
            "",
            "\1\u00bc\1\uffff\12\u0081\7\uffff\32\177\4\u0082\1\u0081\1\u0082\32\u0081",
            "\12\u0081\7\uffff\32\u00bd\4\u00be\1\u00bd\1\u00be\32\u00bd",
            "\12\u0081\7\uffff\32\u0081\4\uffff\1\u0081\1\uffff\32\u0081",
            "",
            "",
            "\101\u0085\33\u00c1\1\u00c0\1\u00bf\35\u00c1\uff85\u0085",
            "",
            "",
            "\12\103\7\uffff\32\u0094\4\uffff\1\u0094\1\uffff\32\u0094",
            "",
            "",
            "",
            "\1\u00c3\5\uffff\1\u00c2",
            "",
            "\1\u00c5\7\uffff\1\u00c4",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u00c6\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u00c7\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u00c8\16\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u00c9\15\77",
            "\1\101\1\76\1\uffff\4\75\6\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\76\1\uffff\4\u00ca\6\102\7\uffff\32\103\4\uffff\1\102\1\uffff\32\102",
            "\1\76\1\uffff\4\u0087",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u00cb\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u00cc\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u00cd\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u00ce\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u00cf\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u00d0\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u00d1\16\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u00d2\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u00d3\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u00d4\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u00d5\31\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u00d6\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u00d7\22\77\1\u00d8\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\2\77\1\u00d9\27\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u00db\7\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u00dc\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\77\1\u00dd\30\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u00de\16\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u00df\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\17\77\1\u00e0\12\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u00e1\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u00e2\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u00e3\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\3\77\1\u00e4\26\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u00e5\7\77\1\u00e6\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\6\77\1\u00e7\23\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u00e8\15\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u00e9\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u00ea\14\77",
            "\1\101\2\uffff\12\77\1\u00eb\6\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u00ec\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\17\77\1\u00ed\12\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\2\77\1\u00ee\20\77\1\u00ef\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u00f0\6\77",
            "\1\175\1\uffff\4\76",
            "",
            "\1\76\1\uffff\4\u0080\6\u0081\7\uffff\32\u0081\4\uffff\1\u0081\1\uffff\32\u0081",
            "\1\76\1\uffff\4\u00f1",
            "\1\76\1\uffff\4\76",
            "\1\76\1\uffff\4\76\50\uffff\2\u0085\4\uffff\1\u0085\3\uffff\1\u0085\7\uffff\1\u0085\3\uffff\1\u0085\1\uffff\2\u0085",
            "\56\u0085\1\u0084\1\u0085\4\u0084\uffcc\u0085",
            "",
            "",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u00f2\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u00f3\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u00f4\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\12\102\7\uffff\32\u0094\4\76\1\u0093\1\76\32\u0093",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u00f6\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\2\77\1\u00f8\27\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u00f9\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u00fa\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u00fb\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u00fc\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u00fd\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u00fe\15\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u00ff\15\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\3\77\1\u0100\26\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u0101\16\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u0102\15\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u0103\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0104\25\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0105\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0106\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0107\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0108\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u010a\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u010d\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u010f\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u0110\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0111\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0112\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u0113\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u0114\13\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u0115\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u0116\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0117\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0118\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u0119\21\77",
            "\12\u0082\7\uffff\72\u00be",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u011a\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u011b\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u011c\7\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u011d\14\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u011e\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\25\77\1\u011f\4\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0120\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u0122\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\17\77\1\u0123\12\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u0124\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u0125\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u0126\31\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u0127\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0128\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0129\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u012a\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u012c\31\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u012d\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u012e\31\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u012f\10\77",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u0130\7\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u0132\16\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u0133\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0135\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\26\77\1\u0137\3\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0139\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\17\77\1\u013a\12\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\27\77\1\u013b\2\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u013c\31\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u013f\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u0140\31\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0142\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u0144\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u0145\16\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u0146\15\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\14\77\1\u0147\15\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0148\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u014a\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u014b\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u014c\7\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u014d\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u014f\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u0150\31\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0152\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0153\6\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u0154\10\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u0155\14\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0156\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0157\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0158\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u0159\16\77",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\25\77\1\u015a\4\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u015b\16\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u015c\10\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\25\77\1\u015d\4\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u015e\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u0161\31\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0162\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u0163\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u0165\21\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u0167\16\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u0169\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\30\77\1\u016a\1\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u016d\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\24\77\1\u016f\5\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0170\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0171\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u0173\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0174\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u0177\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u0178\14\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u0179\13\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u017a\25\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\16\77\1\u017b\13\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\1\u017e\31\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\27\77\1\u017f\2\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u0185\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\27\77\1\u0186\2\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u0187\14\77",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\13\77\1\u0188\16\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0189\6\77",
            "",
            "",
            "",
            "",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u018b\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\22\77\1\u018c\7\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\10\77\1\u018d\21\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u018e\25\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u018f\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\31\77\1\u0191",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u0192\14\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\15\77\1\u0193\14\77",
            "",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\4\77\1\u0194\25\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0195\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\23\77\1\u0196\6\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\21\77\1\u0197\10\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "\1\101\2\uffff\12\77\7\uffff\32\103\4\uffff\1\102\1\uffff\32\77",
            "",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | RULE_EXPR | RULE_LOWERCASE_ID | RULE_LOWERCASE_DASHID | RULE_SHAPE | RULE_UPPERCASE_ID | RULE_UPPERCASE_PATH | RULE_CAMELCASE_ID | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_193 = input.LA(1);

                        s = -1;
                        if ( (LA21_193=='.'||(LA21_193>='0' && LA21_193<='3')) ) {s = 132;}

                        else if ( ((LA21_193>='\u0000' && LA21_193<='-')||LA21_193=='/'||(LA21_193>='4' && LA21_193<='\uFFFF')) ) {s = 133;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA21_45 = input.LA(1);

                        s = -1;
                        if ( ((LA21_45>='\u0000' && LA21_45<='\uFFFF')) ) {s = 137;}

                        else s = 48;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA21_39 = input.LA(1);

                        s = -1;
                        if ( (LA21_39=='.'||(LA21_39>='0' && LA21_39<='3')) ) {s = 132;}

                        else if ( ((LA21_39>='\u0000' && LA21_39<='-')||LA21_39=='/'||(LA21_39>='4' && LA21_39<='\uFFFF')) ) {s = 133;}

                        else s = 48;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA21_46 = input.LA(1);

                        s = -1;
                        if ( ((LA21_46>='\u0000' && LA21_46<='\uFFFF')) ) {s = 137;}

                        else s = 48;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA21_0 = input.LA(1);

                        s = -1;
                        if ( (LA21_0=='@') ) {s = 1;}

                        else if ( (LA21_0==',') ) {s = 2;}

                        else if ( (LA21_0=='e') ) {s = 3;}

                        else if ( (LA21_0=='o') ) {s = 4;}

                        else if ( (LA21_0=='i') ) {s = 5;}

                        else if ( (LA21_0=='m') ) {s = 6;}

                        else if ( (LA21_0=='+') ) {s = 7;}

                        else if ( (LA21_0=='p') ) {s = 8;}

                        else if ( (LA21_0=='f') ) {s = 9;}

                        else if ( (LA21_0=='{') ) {s = 10;}

                        else if ( (LA21_0=='}') ) {s = 11;}

                        else if ( (LA21_0=='a') ) {s = 12;}

                        else if ( (LA21_0=='n') ) {s = 13;}

                        else if ( (LA21_0=='b') ) {s = 14;}

                        else if ( (LA21_0=='t') ) {s = 15;}

                        else if ( (LA21_0=='l') ) {s = 16;}

                        else if ( (LA21_0=='v') ) {s = 17;}

                        else if ( (LA21_0=='d') ) {s = 18;}

                        else if ( (LA21_0=='r') ) {s = 19;}

                        else if ( (LA21_0=='g') ) {s = 20;}

                        else if ( (LA21_0=='u') ) {s = 21;}

                        else if ( (LA21_0=='c') ) {s = 22;}

                        else if ( (LA21_0=='*') ) {s = 23;}

                        else if ( (LA21_0=='s') ) {s = 24;}

                        else if ( (LA21_0=='(') ) {s = 25;}

                        else if ( (LA21_0==')') ) {s = 26;}

                        else if ( (LA21_0==':') ) {s = 27;}

                        else if ( (LA21_0=='#') ) {s = 28;}

                        else if ( (LA21_0=='|') ) {s = 29;}

                        else if ( (LA21_0=='/') ) {s = 30;}

                        else if ( (LA21_0=='.') ) {s = 31;}

                        else if ( (LA21_0=='=') ) {s = 32;}

                        else if ( (LA21_0=='>') ) {s = 33;}

                        else if ( (LA21_0=='<') ) {s = 34;}

                        else if ( (LA21_0=='!') ) {s = 35;}

                        else if ( (LA21_0=='-') ) {s = 36;}

                        else if ( (LA21_0=='E') ) {s = 37;}

                        else if ( (LA21_0=='^') ) {s = 38;}

                        else if ( (LA21_0=='[') ) {s = 39;}

                        else if ( (LA21_0=='h'||(LA21_0>='j' && LA21_0<='k')||LA21_0=='q'||(LA21_0>='w' && LA21_0<='z')) ) {s = 40;}

                        else if ( ((LA21_0>='A' && LA21_0<='D')||(LA21_0>='F' && LA21_0<='Z')) ) {s = 41;}

                        else if ( (LA21_0=='_') ) {s = 42;}

                        else if ( ((LA21_0>='\\' && LA21_0<=']')||LA21_0=='`') ) {s = 43;}

                        else if ( ((LA21_0>='0' && LA21_0<='9')) ) {s = 44;}

                        else if ( (LA21_0=='\"') ) {s = 45;}

                        else if ( (LA21_0=='\'') ) {s = 46;}

                        else if ( ((LA21_0>='\t' && LA21_0<='\n')||LA21_0=='\r'||LA21_0==' ') ) {s = 47;}

                        else if ( ((LA21_0>='\u0000' && LA21_0<='\b')||(LA21_0>='\u000B' && LA21_0<='\f')||(LA21_0>='\u000E' && LA21_0<='\u001F')||(LA21_0>='$' && LA21_0<='&')||LA21_0==';'||LA21_0=='?'||(LA21_0>='~' && LA21_0<='\uFFFF')) ) {s = 48;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA21_132 = input.LA(1);

                        s = -1;
                        if ( (LA21_132==']') ) {s = 191;}

                        else if ( (LA21_132=='\\') ) {s = 192;}

                        else if ( ((LA21_132>='A' && LA21_132<='[')||(LA21_132>='^' && LA21_132<='z')) ) {s = 193;}

                        else if ( ((LA21_132>='\u0000' && LA21_132<='@')||(LA21_132>='{' && LA21_132<='\uFFFF')) ) {s = 133;}

                        else s = 62;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 21, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}