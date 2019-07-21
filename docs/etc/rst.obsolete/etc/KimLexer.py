from pygments.lexer import RegexLexer, words, include
from pygments.token import *

__all__ = ['KimLexer']

class KimLexer(RegexLexer):
    
    name = 'Kim'
    aliases = ['kim']
    filenames = ['*.kim']

    tokens = {
        'root': [
            ('"', String, 'string'),
            (r'/\*', Comment.Multiline, 'comment'),
            (r'//.*?$', Comment.Singleline),
            (words(('abstract', 'acceleration', 'according', 'affects', 'agent', 'aggregated', 'and', 'angle', 'annotation', 
                'applies', 'area', 'as', 'assess', 'authority', 'at', 'attribute', 'away', 'between', 'bidirectional', 'boolean', 'by', 
                'change', 'charge', 'children', 'class', 'classify', 'concept', 'confers', 'configuration', 'contains', 
                'contextualize', 'core', 'count', 'covering', 'creates', 'date', 'define', 'definition', 'deliberative', 'deniable', 
                'describes', 'discretize', 'discretized', 'disjoint', 'distance', 'do', 'domain', 'double', 'down', 'duration',
                'each', 'electric-potential', 'energy', 'entropy', 'equals', 'event', 'every', 'exactly', 'exclusive', 'exports', 
                'exposes', 'extends', 'extent', 'false', 'float', 'for', 'from', 'functional', 'has', 'identified', 'identity',
                'if', 'in', 'inclusive', 'inheriting', 'inherits', 'integer', 'integrate', 'interpret', 'into', 'inverse', 'is',
                'learn', 'least', 'length', 'line', 'links', 'lookup', 'mass', 'measure', 'metadata', 'model', 'money', 'most', 
                'move', 'named', 'namespace', 'no', 'not', 'nothing', 'objective', 'observe', 'observing', 'occurrence', 'of', 
                'on', 'only', 'optional', 'or', 'ordering', 'organized', 'otherwise', 'outside', 'over', 'parent', 'per', 
                'percentage', 'presence', 'pressure', 'priority', 'private', 'probability', 'process', 'proportion', 'quality', 
                'quantity', 'range', 'rank', 'ratio', 'reactive', 'realm', 'relationship', 'required', 'requires', 'resistance', 
                'resistivity', 'resolve', 'role', 'root', 'scenario', 'set', 'social', 'source', 'structural', 'subjective', 
                'table', 'target', 'temperature', 'text', 'thing', 'to', 'train', 'true', 'type', 'uncertainty', 'unidirectional', 
                'unknown', 'unless', 'uses', 'using', 'value', 'velocity', 'version', 'viscosity', 'void', 'volume', 'weight', 
                'where', 'with'), suffix=r'\b'), Keyword),
            (r'[a-z]+:([A-z]|-|_)+', Name.Class),
            (r'[a-z]+', Name.Variable),
            (r'[A-z]+', Name.Class),
            (r'(\{|\}|\(|\)|\;|\,)', Punctuation),
            (r'[0-9]([0-9]|\.)*', Number),
#            (r'\b[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\-\.:=@;$_!*\'%/?#]*[a-z0-9+=@$/]', Literal),
            (r'\s+', Text)
        ],
        'string': [
            ('[^"]+', String),
            ('"', String, '#pop'),
        ],
        'comment': [
            (r'[^*/]', Comment.Multiline),
            (r'/\*', Comment.Multiline, '#push'),
            (r'\*/', Comment.Multiline, '#pop'),
            (r'[*/]', Comment.Multiline)
        ]
    }