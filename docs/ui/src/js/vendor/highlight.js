/*
  Highlight.js 10.6.0 (f9ae495d)
  License: BSD-3-Clause
  Copyright (c) 2006-2021, Ivan Sagalaev
*/
var hljs = (function () {
    'use strict';

    function deepFreeze(obj) {
        if (obj instanceof Map) {
            obj.clear = obj.delete = obj.set = function () {
                throw new Error('map is read-only');
            };
        } else if (obj instanceof Set) {
            obj.add = obj.clear = obj.delete = function () {
                throw new Error('set is read-only');
            };
        }

        // Freeze self
        Object.freeze(obj);

        Object.getOwnPropertyNames(obj).forEach(function (name) {
            var prop = obj[name];

            // Freeze prop if it is an object
            if (typeof prop == 'object' && !Object.isFrozen(prop)) {
                deepFreeze(prop);
            }
        });

        return obj;
    }

    var deepFreezeEs6 = deepFreeze;
    var _default = deepFreeze;
    deepFreezeEs6.default = _default;

    class Response {
      
      constructor(mode) {
        // eslint-disable-next-line no-undefined
        if (mode.data === undefined) mode.data = {};

        this.data = mode.data;
      }

      ignoreMatch() {
        this.ignore = true;
      }
    }

    /**
     * @param {string} value
     * @returns {string}
     */
    function escapeHTML(value) {
      return value
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#x27;');
    }

    /**
     * performs a shallow merge of multiple objects into one
     *
     * @template T
     * @param {T} original
     * @param {Record<string,any>[]} objects
     * @returns {T} a single new object
     */
    function inherit(original, ...objects) {
      /** @type Record<string,any> */
      const result = Object.create(null);

      for (const key in original) {
        result[key] = original[key];
      }
      objects.forEach(function(obj) {
        for (const key in obj) {
          result[key] = obj[key];
        }
      });
      return /** @type {T} */ (result);
    }

    /**
     * @typedef {object} Renderer
     * @property {(text: string) => void} addText
     * @property {(node: Node) => void} openNode
     * @property {(node: Node) => void} closeNode
     * @property {() => string} value
     */

    /** @typedef {{kind?: string, sublanguage?: boolean}} Node */
    /** @typedef {{walk: (r: Renderer) => void}} Tree */
    /** */

    const SPAN_CLOSE = '</span>';

    /**
     * Determines if a node needs to be wrapped in <span>
     *
     * @param {Node} node */
    const emitsWrappingTags = (node) => {
      return !!node.kind;
    };

    /** @type {Renderer} */
    class HTMLRenderer {
      /**
       * Creates a new HTMLRenderer
       *
       * @param {Tree} parseTree - the parse tree (must support `walk` API)
       * @param {{classPrefix: string}} options
       */
      constructor(parseTree, options) {
        this.buffer = "";
        this.classPrefix = options.classPrefix;
        parseTree.walk(this);
      }

      /**
       * Adds texts to the output stream
       *
       * @param {string} text */
      addText(text) {
        this.buffer += escapeHTML(text);
      }

      /**
       * Adds a node open to the output stream (if needed)
       *
       * @param {Node} node */
      openNode(node) {
        if (!emitsWrappingTags(node)) return;

        let className = node.kind;
        if (!node.sublanguage) {
          className = `${this.classPrefix}${className}`;
        }
        this.span(className);
      }

      /**
       * Adds a node close to the output stream (if needed)
       *
       * @param {Node} node */
      closeNode(node) {
        if (!emitsWrappingTags(node)) return;

        this.buffer += SPAN_CLOSE;
      }

      /**
       * returns the accumulated buffer
      */
      value() {
        return this.buffer;
      }

      // helpers

      /**
       * Builds a span element
       *
       * @param {string} className */
      span(className) {
        this.buffer += `<span class="${className}">`;
      }
    }

    /** @typedef {{kind?: string, sublanguage?: boolean, children: Node[]} | string} Node */
    /** @typedef {{kind?: string, sublanguage?: boolean, children: Node[]} } DataNode */
    /**  */

    class TokenTree {
      constructor() {
        /** @type DataNode */
        this.rootNode = { children: [] };
        this.stack = [this.rootNode];
      }

      get top() {
        return this.stack[this.stack.length - 1];
      }

      get root() { return this.rootNode; }

      /** @param {Node} node */
      add(node) {
        this.top.children.push(node);
      }

      /** @param {string} kind */
      openNode(kind) {
        /** @type Node */
        const node = { kind, children: [] };
        this.add(node);
        this.stack.push(node);
      }

      closeNode() {
        if (this.stack.length > 1) {
          return this.stack.pop();
        }
        // eslint-disable-next-line no-undefined
        return undefined;
      }

      closeAllNodes() {
        while (this.closeNode());
      }

      toJSON() {
        return JSON.stringify(this.rootNode, null, 4);
      }

      /**
       * @typedef { import("./html_renderer").Renderer } Renderer
       * @param {Renderer} builder
       */
      walk(builder) {
        // this does not
        return this.constructor._walk(builder, this.rootNode);
        // this works
        // return TokenTree._walk(builder, this.rootNode);
      }

      /**
       * @param {Renderer} builder
       * @param {Node} node
       */
      static _walk(builder, node) {
        if (typeof node === "string") {
          builder.addText(node);
        } else if (node.children) {
          builder.openNode(node);
          node.children.forEach((child) => this._walk(builder, child));
          builder.closeNode(node);
        }
        return builder;
      }

      /**
       * @param {Node} node
       */
      static _collapse(node) {
        if (typeof node === "string") return;
        if (!node.children) return;

        if (node.children.every(el => typeof el === "string")) {
          // node.text = node.children.join("");
          // delete node.children;
          node.children = [node.children.join("")];
        } else {
          node.children.forEach((child) => {
            TokenTree._collapse(child);
          });
        }
      }
    }

    /**
      Currently this is all private API, but this is the minimal API necessary
      that an Emitter must implement to fully support the parser.

      Minimal interface:

      - addKeyword(text, kind)
      - addText(text)
      - addSublanguage(emitter, subLanguageName)
      - finalize()
      - openNode(kind)
      - closeNode()
      - closeAllNodes()
      - toHTML()

    */

    /**
     * @implements {Emitter}
     */
    class TokenTreeEmitter extends TokenTree {
      /**
       * @param {*} options
       */
      constructor(options) {
        super();
        this.options = options;
      }

      /**
       * @param {string} text
       * @param {string} kind
       */
      addKeyword(text, kind) {
        if (text === "") { return; }

        this.openNode(kind);
        this.addText(text);
        this.closeNode();
      }

      /**
       * @param {string} text
       */
      addText(text) {
        if (text === "") { return; }

        this.add(text);
      }

      /**
       * @param {Emitter & {root: DataNode}} emitter
       * @param {string} name
       */
      addSublanguage(emitter, name) {
        /** @type DataNode */
        const node = emitter.root;
        node.kind = name;
        node.sublanguage = true;
        this.add(node);
      }

      toHTML() {
        const renderer = new HTMLRenderer(this, this.options);
        return renderer.value();
      }

      finalize() {
        return true;
      }
    }

    /**
     * @param {string} value
     * @returns {RegExp}
     * */
    function escape(value) {
      return new RegExp(value.replace(/[-/\\^$*+?.()|[\]{}]/g, '\\$&'), 'm');
    }

    /**
     * @param {RegExp | string } re
     * @returns {string}
     */
    function source(re) {
      if (!re) return null;
      if (typeof re === "string") return re;

      return re.source;
    }

    /**
     * @param {...(RegExp | string) } args
     * @returns {string}
     */
    function concat(...args) {
      const joined = args.map((x) => source(x)).join("");
      return joined;
    }

    /**
     * Any of the passed expresssions may match
     *
     * Creates a huge this | this | that | that match
     * @param {(RegExp | string)[] } args
     * @returns {string}
     */
    function either(...args) {
      const joined = '(' + args.map((x) => source(x)).join("|") + ")";
      return joined;
    }

    /**
     * @param {RegExp} re
     * @returns {number}
     */
    function countMatchGroups(re) {
      return (new RegExp(re.toString() + '|')).exec('').length - 1;
    }

    /**
     * Does lexeme start with a regular expression match at the beginning
     * @param {RegExp} re
     * @param {string} lexeme
     */
    function startsWith(re, lexeme) {
      const match = re && re.exec(lexeme);
      return match && match.index === 0;
    }

    // BACKREF_RE matches an open parenthesis or backreference. To avoid
    // an incorrect parse, it additionally matches the following:
    // - [...] elements, where the meaning of parentheses and escapes change
    // - other escape sequences, so we do not misparse escape sequences as
    //   interesting elements
    // - non-matching or lookahead parentheses, which do not capture. These
    //   follow the '(' with a '?'.
    const BACKREF_RE = /\[(?:[^\\\]]|\\.)*\]|\(\??|\\([1-9][0-9]*)|\\./;

    // join logically computes regexps.join(separator), but fixes the
    // backreferences so they continue to match.
    // it also places each individual regular expression into it's own
    // match group, keeping track of the sequencing of those match groups
    // is currently an exercise for the caller. :-)
    /**
     * @param {(string | RegExp)[]} regexps
     * @param {string} separator
     * @returns {string}
     */
    function join(regexps, separator = "|") {
      let numCaptures = 0;

      return regexps.map((regex) => {
        numCaptures += 1;
        const offset = numCaptures;
        let re = source(regex);
        let out = '';

        while (re.length > 0) {
          const match = BACKREF_RE.exec(re);
          if (!match) {
            out += re;
            break;
          }
          out += re.substring(0, match.index);
          re = re.substring(match.index + match[0].length);
          if (match[0][0] === '\\' && match[1]) {
            // Adjust the backreference.
            out += '\\' + String(Number(match[1]) + offset);
          } else {
            out += match[0];
            if (match[0] === '(') {
              numCaptures++;
            }
          }
        }
        return out;
      }).map(re => `(${re})`).join(separator);
    }

    // Common regexps
    const MATCH_NOTHING_RE = /\b\B/;
    const IDENT_RE = '[a-zA-Z]\\w*';
    const UNDERSCORE_IDENT_RE = '[a-zA-Z_]\\w*';
    const NUMBER_RE = '\\b\\d+(\\.\\d+)?';
    const C_NUMBER_RE = '(-?)(\\b0[xX][a-fA-F0-9]+|(\\b\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?)'; // 0x..., 0..., decimal, float
    const BINARY_NUMBER_RE = '\\b(0b[01]+)'; // 0b...
    const RE_STARTERS_RE = '!|!=|!==|%|%=|&|&&|&=|\\*|\\*=|\\+|\\+=|,|-|-=|/=|/|:|;|<<|<<=|<=|<|===|==|=|>>>=|>>=|>=|>>>|>>|>|\\?|\\[|\\{|\\(|\\^|\\^=|\\||\\|=|\\|\\||~';

    /**
    * @param { Partial<Mode> & {binary?: string | RegExp} } opts
    */
    const SHEBANG = (opts = {}) => {
      const beginShebang = /^#![ ]*\//;
      if (opts.binary) {
        opts.begin = concat(
          beginShebang,
          /.*\b/,
          opts.binary,
          /\b.*/);
      }
      return inherit({
        className: 'meta',
        begin: beginShebang,
        end: /$/,
        relevance: 0,
        /** @type {ModeCallback} */
        "on:begin": (m, resp) => {
          if (m.index !== 0) resp.ignoreMatch();
        }
      }, opts);
    };

    // Common modes
    const BACKSLASH_ESCAPE = {
      begin: '\\\\[\\s\\S]', relevance: 0
    };
    const APOS_STRING_MODE = {
      className: 'string',
      begin: '\'',
      end: '\'',
      illegal: '\\n',
      contains: [BACKSLASH_ESCAPE]
    };
    const QUOTE_STRING_MODE = {
      className: 'string',
      begin: '"',
      end: '"',
      illegal: '\\n',
      contains: [BACKSLASH_ESCAPE]
    };
    const PHRASAL_WORDS_MODE = {
      begin: /\b(a|an|the|are|I'm|isn't|don't|doesn't|won't|but|just|should|pretty|simply|enough|gonna|going|wtf|so|such|will|you|your|they|like|more)\b/
    };
    /**
     * Creates a comment mode
     *
     * @param {string | RegExp} begin
     * @param {string | RegExp} end
     * @param {Mode | {}} [modeOptions]
     * @returns {Partial<Mode>}
     */
    const COMMENT = function(begin, end, modeOptions = {}) {
      const mode = inherit(
        {
          className: 'comment',
          begin,
          end,
          contains: []
        },
        modeOptions
      );
      mode.contains.push(PHRASAL_WORDS_MODE);
      mode.contains.push({
        className: 'doctag',
        begin: '(?:TODO|FIXME|NOTE|BUG|OPTIMIZE|HACK|XXX):',
        relevance: 0
      });
      return mode;
    };
    const C_LINE_COMMENT_MODE = COMMENT('//', '$');
    const C_BLOCK_COMMENT_MODE = COMMENT('/\\*', '\\*/');
    const HASH_COMMENT_MODE = COMMENT('#', '$');
    const NUMBER_MODE = {
      className: 'number',
      begin: NUMBER_RE,
      relevance: 0
    };
    const C_NUMBER_MODE = {
      className: 'number',
      begin: C_NUMBER_RE,
      relevance: 0
    };
    const BINARY_NUMBER_MODE = {
      className: 'number',
      begin: BINARY_NUMBER_RE,
      relevance: 0
    };
    const CSS_NUMBER_MODE = {
      className: 'number',
      begin: NUMBER_RE + '(' +
        '%|em|ex|ch|rem' +
        '|vw|vh|vmin|vmax' +
        '|cm|mm|in|pt|pc|px' +
        '|deg|grad|rad|turn' +
        '|s|ms' +
        '|Hz|kHz' +
        '|dpi|dpcm|dppx' +
        ')?',
      relevance: 0
    };
    const REGEXP_MODE = {
      // this outer rule makes sure we actually have a WHOLE regex and not simply
      // an expression such as:
      //
      //     3 / something
      //
      // (which will then blow up when regex's `illegal` sees the newline)
      begin: /(?=\/[^/\n]*\/)/,
      contains: [{
        className: 'regexp',
        begin: /\//,
        end: /\/[gimuy]*/,
        illegal: /\n/,
        contains: [
          BACKSLASH_ESCAPE,
          {
            begin: /\[/,
            end: /\]/,
            relevance: 0,
            contains: [BACKSLASH_ESCAPE]
          }
        ]
      }]
    };
    const TITLE_MODE = {
      className: 'title',
      begin: IDENT_RE,
      relevance: 0
    };
    const UNDERSCORE_TITLE_MODE = {
      className: 'title',
      begin: UNDERSCORE_IDENT_RE,
      relevance: 0
    };
    const METHOD_GUARD = {
      // excludes method names from keyword processing
      begin: '\\.\\s*' + UNDERSCORE_IDENT_RE,
      relevance: 0
    };

    /**
     * Adds end same as begin mechanics to a mode
     *
     * Your mode must include at least a single () match group as that first match
     * group is what is used for comparison
     * @param {Partial<Mode>} mode
     */
    const END_SAME_AS_BEGIN = function(mode) {
      return Object.assign(mode,
        {
          /** @type {ModeCallback} */
          'on:begin': (m, resp) => { resp.data._beginMatch = m[1]; },
          /** @type {ModeCallback} */
          'on:end': (m, resp) => { if (resp.data._beginMatch !== m[1]) resp.ignoreMatch(); }
        });
    };

    var MODES = /*#__PURE__*/Object.freeze({
        __proto__: null,
        MATCH_NOTHING_RE: MATCH_NOTHING_RE,
        IDENT_RE: IDENT_RE,
        UNDERSCORE_IDENT_RE: UNDERSCORE_IDENT_RE,
        NUMBER_RE: NUMBER_RE,
        C_NUMBER_RE: C_NUMBER_RE,
        BINARY_NUMBER_RE: BINARY_NUMBER_RE,
        RE_STARTERS_RE: RE_STARTERS_RE,
        SHEBANG: SHEBANG,
        BACKSLASH_ESCAPE: BACKSLASH_ESCAPE,
        APOS_STRING_MODE: APOS_STRING_MODE,
        QUOTE_STRING_MODE: QUOTE_STRING_MODE,
        PHRASAL_WORDS_MODE: PHRASAL_WORDS_MODE,
        COMMENT: COMMENT,
        C_LINE_COMMENT_MODE: C_LINE_COMMENT_MODE,
        C_BLOCK_COMMENT_MODE: C_BLOCK_COMMENT_MODE,
        HASH_COMMENT_MODE: HASH_COMMENT_MODE,
        NUMBER_MODE: NUMBER_MODE,
        C_NUMBER_MODE: C_NUMBER_MODE,
        BINARY_NUMBER_MODE: BINARY_NUMBER_MODE,
        CSS_NUMBER_MODE: CSS_NUMBER_MODE,
        REGEXP_MODE: REGEXP_MODE,
        TITLE_MODE: TITLE_MODE,
        UNDERSCORE_TITLE_MODE: UNDERSCORE_TITLE_MODE,
        METHOD_GUARD: METHOD_GUARD,
        END_SAME_AS_BEGIN: END_SAME_AS_BEGIN
    });

    // Grammar extensions / plugins
    // See: https://github.com/highlightjs/highlight.js/issues/2833

    // Grammar extensions allow "syntactic sugar" to be added to the grammar modes
    // without requiring any underlying changes to the compiler internals.

    // `compileMatch` being the perfect small example of now allowing a grammar
    // author to write `match` when they desire to match a single expression rather
    // than being forced to use `begin`.  The extension then just moves `match` into
    // `begin` when it runs.  Ie, no features have been added, but we've just made
    // the experience of writing (and reading grammars) a little bit nicer.

    // ------

    // TODO: We need negative look-behind support to do this properly
    /**
     * Skip a match if it has a preceding dot
     *
     * This is used for `beginKeywords` to prevent matching expressions such as
     * `bob.keyword.do()`. The mode compiler automatically wires this up as a
     * special _internal_ 'on:begin' callback for modes with `beginKeywords`
     * @param {RegExpMatchArray} match
     * @param {CallbackResponse} response
     */
    function skipIfhasPrecedingDot(match, response) {
      const before = match.input[match.index - 1];
      if (before === ".") {
        response.ignoreMatch();
      }
    }


    /**
     * `beginKeywords` syntactic sugar
     * @type {CompilerExt}
     */
    function beginKeywords(mode, parent) {
      if (!parent) return;
      if (!mode.beginKeywords) return;

      // for languages with keywords that include non-word characters checking for
      // a word boundary is not sufficient, so instead we check for a word boundary
      // or whitespace - this does no harm in any case since our keyword engine
      // doesn't allow spaces in keywords anyways and we still check for the boundary
      // first
      mode.begin = '\\b(' + mode.beginKeywords.split(' ').join('|') + ')(?!\\.)(?=\\b|\\s)';
      mode.__beforeBegin = skipIfhasPrecedingDot;
      mode.keywords = mode.keywords || mode.beginKeywords;
      delete mode.beginKeywords;

      // prevents double relevance, the keywords themselves provide
      // relevance, the mode doesn't need to double it
      // eslint-disable-next-line no-undefined
      if (mode.relevance === undefined) mode.relevance = 0;
    }

    /**
     * Allow `illegal` to contain an array of illegal values
     * @type {CompilerExt}
     */
    function compileIllegal(mode, _parent) {
      if (!Array.isArray(mode.illegal)) return;

      mode.illegal = either(...mode.illegal);
    }

    /**
     * `match` to match a single expression for readability
     * @type {CompilerExt}
     */
    function compileMatch(mode, _parent) {
      if (!mode.match) return;
      if (mode.begin || mode.end) throw new Error("begin & end are not supported with match");

      mode.begin = mode.match;
      delete mode.match;
    }

    /**
     * provides the default 1 relevance to all modes
     * @type {CompilerExt}
     */
    function compileRelevance(mode, _parent) {
      // eslint-disable-next-line no-undefined
      if (mode.relevance === undefined) mode.relevance = 1;
    }

    // keywords that should have no default relevance value
    const COMMON_KEYWORDS = [
      'of',
      'and',
      'for',
      'in',
      'not',
      'or',
      'if',
      'then',
      'parent', // common variable name
      'list', // common variable name
      'value' // common variable name
    ];

    const DEFAULT_KEYWORD_CLASSNAME = "keyword";

    /**
     * Given raw keywords from a language definition, compile them.
     *
     * @param {string | Record<string,string|string[]> | Array<string>} rawKeywords
     * @param {boolean} caseInsensitive
     */
    function compileKeywords(rawKeywords, caseInsensitive, className = DEFAULT_KEYWORD_CLASSNAME) {
      /** @type KeywordDict */
      const compiledKeywords = {};

      // input can be a string of keywords, an array of keywords, or a object with
      // named keys representing className (which can then point to a string or array)
      if (typeof rawKeywords === 'string') {
        compileList(className, rawKeywords.split(" "));
      } else if (Array.isArray(rawKeywords)) {
        compileList(className, rawKeywords);
      } else {
        Object.keys(rawKeywords).forEach(function(className) {
          // collapse all our objects back into the parent object
          Object.assign(
            compiledKeywords,
            compileKeywords(rawKeywords[className], caseInsensitive, className)
          );
        });
      }
      return compiledKeywords;

      // ---

      /**
       * Compiles an individual list of keywords
       *
       * Ex: "for if when while|5"
       *
       * @param {string} className
       * @param {Array<string>} keywordList
       */
      function compileList(className, keywordList) {
        if (caseInsensitive) {
          keywordList = keywordList.map(x => x.toLowerCase());
        }
        keywordList.forEach(function(keyword) {
          const pair = keyword.split('|');
          compiledKeywords[pair[0]] = [className, scoreForKeyword(pair[0], pair[1])];
        });
      }
    }

    /**
     * Returns the proper score for a given keyword
     *
     * Also takes into account comment keywords, which will be scored 0 UNLESS
     * another score has been manually assigned.
     * @param {string} keyword
     * @param {string} [providedScore]
     */
    function scoreForKeyword(keyword, providedScore) {
      // manual scores always win over common keywords
      // so you can force a score of 1 if you really insist
      if (providedScore) {
        return Number(providedScore);
      }

      return commonKeyword(keyword) ? 0 : 1;
    }

    /**
     * Determines if a given keyword is common or not
     *
     * @param {string} keyword */
    function commonKeyword(keyword) {
      return COMMON_KEYWORDS.includes(keyword.toLowerCase());
    }

    // compilation

    /**
     * Compiles a language definition result
     *
     * Given the raw result of a language definition (Language), compiles this so
     * that it is ready for highlighting code.
     * @param {Language} language
     * @param {{plugins: HLJSPlugin[]}} opts
     * @returns {CompiledLanguage}
     */
    function compileLanguage(language, { plugins }) {
      /**
       * Builds a regex with the case sensativility of the current language
       *
       * @param {RegExp | string} value
       * @param {boolean} [global]
       */
      function langRe(value, global) {
        return new RegExp(
          source(value),
          'm' + (language.case_insensitive ? 'i' : '') + (global ? 'g' : '')
        );
      }

      /**
        Stores multiple regular expressions and allows you to quickly search for
        them all in a string simultaneously - returning the first match.  It does
        this by creating a huge (a|b|c) regex - each individual item wrapped with ()
        and joined by `|` - using match groups to track position.  When a match is
        found checking which position in the array has content allows us to figure
        out which of the original regexes / match groups triggered the match.

        The match object itself (the result of `Regex.exec`) is returned but also
        enhanced by merging in any meta-data that was registered with the regex.
        This is how we keep track of which mode matched, and what type of rule
        (`illegal`, `begin`, end, etc).
      */
      class MultiRegex {
        constructor() {
          this.matchIndexes = {};
          // @ts-ignore
          this.regexes = [];
          this.matchAt = 1;
          this.position = 0;
        }

        // @ts-ignore
        addRule(re, opts) {
          opts.position = this.position++;
          // @ts-ignore
          this.matchIndexes[this.matchAt] = opts;
          this.regexes.push([opts, re]);
          this.matchAt += countMatchGroups(re) + 1;
        }

        compile() {
          if (this.regexes.length === 0) {
            // avoids the need to check length every time exec is called
            // @ts-ignore
            this.exec = () => null;
          }
          const terminators = this.regexes.map(el => el[1]);
          this.matcherRe = langRe(join(terminators), true);
          this.lastIndex = 0;
        }

        /** @param {string} s */
        exec(s) {
          this.matcherRe.lastIndex = this.lastIndex;
          const match = this.matcherRe.exec(s);
          if (!match) { return null; }

          // eslint-disable-next-line no-undefined
          const i = match.findIndex((el, i) => i > 0 && el !== undefined);
          // @ts-ignore
          const matchData = this.matchIndexes[i];
          // trim off any earlier non-relevant match groups (ie, the other regex
          // match groups that make up the multi-matcher)
          match.splice(0, i);

          return Object.assign(match, matchData);
        }
      }

      /*
        Created to solve the key deficiently with MultiRegex - there is no way to
        test for multiple matches at a single location.  Why would we need to do
        that?  In the future a more dynamic engine will allow certain matches to be
        ignored.  An example: if we matched say the 3rd regex in a large group but
        decided to ignore it - we'd need to started testing again at the 4th
        regex... but MultiRegex itself gives us no real way to do that.

        So what this class creates MultiRegexs on the fly for whatever search
        position they are needed.

        NOTE: These additional MultiRegex objects are created dynamically.  For most
        grammars most of the time we will never actually need anything more than the
        first MultiRegex - so this shouldn't have too much overhead.

        Say this is our search group, and we match regex3, but wish to ignore it.

          regex1 | regex2 | regex3 | regex4 | regex5    ' ie, startAt = 0

        What we need is a new MultiRegex that only includes the remaining
        possibilities:

          regex4 | regex5                               ' ie, startAt = 3

        This class wraps all that complexity up in a simple API... `startAt` decides
        where in the array of expressions to start doing the matching. It
        auto-increments, so if a match is found at position 2, then startAt will be
        set to 3.  If the end is reached startAt will return to 0.

        MOST of the time the parser will be setting startAt manually to 0.
      */
      class ResumableMultiRegex {
        constructor() {
          // @ts-ignore
          this.rules = [];
          // @ts-ignore
          this.multiRegexes = [];
          this.count = 0;

          this.lastIndex = 0;
          this.regexIndex = 0;
        }

        // @ts-ignore
        getMatcher(index) {
          if (this.multiRegexes[index]) return this.multiRegexes[index];

          const matcher = new MultiRegex();
          this.rules.slice(index).forEach(([re, opts]) => matcher.addRule(re, opts));
          matcher.compile();
          this.multiRegexes[index] = matcher;
          return matcher;
        }

        resumingScanAtSamePosition() {
          return this.regexIndex !== 0;
        }

        considerAll() {
          this.regexIndex = 0;
        }

        // @ts-ignore
        addRule(re, opts) {
          this.rules.push([re, opts]);
          if (opts.type === "begin") this.count++;
        }

        /** @param {string} s */
        exec(s) {
          const m = this.getMatcher(this.regexIndex);
          m.lastIndex = this.lastIndex;
          let result = m.exec(s);

          // The following is because we have no easy way to say "resume scanning at the
          // existing position but also skip the current rule ONLY". What happens is
          // all prior rules are also skipped which can result in matching the wrong
          // thing. Example of matching "booger":

          // our matcher is [string, "booger", number]
          //
          // ....booger....

          // if "booger" is ignored then we'd really need a regex to scan from the
          // SAME position for only: [string, number] but ignoring "booger" (if it
          // was the first match), a simple resume would scan ahead who knows how
          // far looking only for "number", ignoring potential string matches (or
          // future "booger" matches that might be valid.)

          // So what we do: We execute two matchers, one resuming at the same
          // position, but the second full matcher starting at the position after:

          //     /--- resume first regex match here (for [number])
          //     |/---- full match here for [string, "booger", number]
          //     vv
          // ....booger....

          // Which ever results in a match first is then used. So this 3-4 step
          // process essentially allows us to say "match at this position, excluding
          // a prior rule that was ignored".
          //
          // 1. Match "booger" first, ignore. Also proves that [string] does non match.
          // 2. Resume matching for [number]
          // 3. Match at index + 1 for [string, "booger", number]
          // 4. If #2 and #3 result in matches, which came first?
          if (this.resumingScanAtSamePosition()) {
            if (result && result.index === this.lastIndex) ; else { // use the second matcher result
              const m2 = this.getMatcher(0);
              m2.lastIndex = this.lastIndex + 1;
              result = m2.exec(s);
            }
          }

          if (result) {
            this.regexIndex += result.position + 1;
            if (this.regexIndex === this.count) {
              // wrap-around to considering all matches again
              this.considerAll();
            }
          }

          return result;
        }
      }

      /**
       * Given a mode, builds a huge ResumableMultiRegex that can be used to walk
       * the content and find matches.
       *
       * @param {CompiledMode} mode
       * @returns {ResumableMultiRegex}
       */
      function buildModeRegex(mode) {
        const mm = new ResumableMultiRegex();

        mode.contains.forEach(term => mm.addRule(term.begin, { rule: term, type: "begin" }));

        if (mode.terminatorEnd) {
          mm.addRule(mode.terminatorEnd, { type: "end" });
        }
        if (mode.illegal) {
          mm.addRule(mode.illegal, { type: "illegal" });
        }

        return mm;
      }

      /** skip vs abort vs ignore
       *
       * @skip   - The mode is still entered and exited normally (and contains rules apply),
       *           but all content is held and added to the parent buffer rather than being
       *           output when the mode ends.  Mostly used with `sublanguage` to build up
       *           a single large buffer than can be parsed by sublanguage.
       *
       *             - The mode begin ands ends normally.
       *             - Content matched is added to the parent mode buffer.
       *             - The parser cursor is moved forward normally.
       *
       * @abort  - A hack placeholder until we have ignore.  Aborts the mode (as if it
       *           never matched) but DOES NOT continue to match subsequent `contains`
       *           modes.  Abort is bad/suboptimal because it can result in modes
       *           farther down not getting applied because an earlier rule eats the
       *           content but then aborts.
       *
       *             - The mode does not begin.
       *             - Content matched by `begin` is added to the mode buffer.
       *             - The parser cursor is moved forward accordingly.
       *
       * @ignore - Ignores the mode (as if it never matched) and continues to match any
       *           subsequent `contains` modes.  Ignore isn't technically possible with
       *           the current parser implementation.
       *
       *             - The mode does not begin.
       *             - Content matched by `begin` is ignored.
       *             - The parser cursor is not moved forward.
       */

      /**
       * Compiles an individual mode
       *
       * This can raise an error if the mode contains certain detectable known logic
       * issues.
       * @param {Mode} mode
       * @param {CompiledMode | null} [parent]
       * @returns {CompiledMode | never}
       */
      function compileMode(mode, parent) {
        const cmode = /** @type CompiledMode */ (mode);
        if (mode.compiled) return cmode;

        [
          // do this early so compiler extensions generally don't have to worry about
          // the distinction between match/begin
          compileMatch
        ].forEach(ext => ext(mode, parent));

        language.compilerExtensions.forEach(ext => ext(mode, parent));

        // __beforeBegin is considered private API, internal use only
        mode.__beforeBegin = null;

        [
          beginKeywords,
          // do this later so compiler extensions that come earlier have access to the
          // raw array if they wanted to perhaps manipulate it, etc.
          compileIllegal,
          // default to 1 relevance if not specified
          compileRelevance
        ].forEach(ext => ext(mode, parent));

        mode.compiled = true;

        let keywordPattern = null;
        if (typeof mode.keywords === "object") {
          keywordPattern = mode.keywords.$pattern;
          delete mode.keywords.$pattern;
        }

        if (mode.keywords) {
          mode.keywords = compileKeywords(mode.keywords, language.case_insensitive);
        }

        // both are not allowed
        if (mode.lexemes && keywordPattern) {
          throw new Error("ERR: Prefer `keywords.$pattern` to `mode.lexemes`, BOTH are not allowed. (see mode reference) ");
        }

        // `mode.lexemes` was the old standard before we added and now recommend
        // using `keywords.$pattern` to pass the keyword pattern
        keywordPattern = keywordPattern || mode.lexemes || /\w+/;
        cmode.keywordPatternRe = langRe(keywordPattern, true);

        if (parent) {
          if (!mode.begin) mode.begin = /\B|\b/;
          cmode.beginRe = langRe(mode.begin);
          if (mode.endSameAsBegin) mode.end = mode.begin;
          if (!mode.end && !mode.endsWithParent) mode.end = /\B|\b/;
          if (mode.end) cmode.endRe = langRe(mode.end);
          cmode.terminatorEnd = source(mode.end) || '';
          if (mode.endsWithParent && parent.terminatorEnd) {
            cmode.terminatorEnd += (mode.end ? '|' : '') + parent.terminatorEnd;
          }
        }
        if (mode.illegal) cmode.illegalRe = langRe(/** @type {RegExp | string} */ (mode.illegal));
        if (!mode.contains) mode.contains = [];

        mode.contains = [].concat(...mode.contains.map(function(c) {
          return expandOrCloneMode(c === 'self' ? mode : c);
        }));
        mode.contains.forEach(function(c) { compileMode(/** @type Mode */ (c), cmode); });

        if (mode.starts) {
          compileMode(mode.starts, parent);
        }

        cmode.matcher = buildModeRegex(cmode);
        return cmode;
      }

      if (!language.compilerExtensions) language.compilerExtensions = [];

      // self is not valid at the top-level
      if (language.contains && language.contains.includes('self')) {
        throw new Error("ERR: contains `self` is not supported at the top-level of a language.  See documentation.");
      }

      // we need a null object, which inherit will guarantee
      language.classNameAliases = inherit(language.classNameAliases || {});

      return compileMode(/** @type Mode */ (language));
    }

    /**
     * Determines if a mode has a dependency on it's parent or not
     *
     * If a mode does have a parent dependency then often we need to clone it if
     * it's used in multiple places so that each copy points to the correct parent,
     * where-as modes without a parent can often safely be re-used at the bottom of
     * a mode chain.
     *
     * @param {Mode | null} mode
     * @returns {boolean} - is there a dependency on the parent?
     * */
    function dependencyOnParent(mode) {
      if (!mode) return false;

      return mode.endsWithParent || dependencyOnParent(mode.starts);
    }

    /**
     * Expands a mode or clones it if necessary
     *
     * This is necessary for modes with parental dependenceis (see notes on
     * `dependencyOnParent`) and for nodes that have `variants` - which must then be
     * exploded into their own individual modes at compile time.
     *
     * @param {Mode} mode
     * @returns {Mode | Mode[]}
     * */
    function expandOrCloneMode(mode) {
      if (mode.variants && !mode.cachedVariants) {
        mode.cachedVariants = mode.variants.map(function(variant) {
          return inherit(mode, { variants: null }, variant);
        });
      }

      // EXPAND
      // if we have variants then essentially "replace" the mode with the variants
      // this happens in compileMode, where this function is called from
      if (mode.cachedVariants) {
        return mode.cachedVariants;
      }

      // CLONE
      // if we have dependencies on parents then we need a unique
      // instance of ourselves, so we can be reused with many
      // different parents without issue
      if (dependencyOnParent(mode)) {
        return inherit(mode, { starts: mode.starts ? inherit(mode.starts) : null });
      }

      if (Object.isFrozen(mode)) {
        return inherit(mode);
      }

      // no special dependency issues, just return ourselves
      return mode;
    }

    var version = "10.6.0";

    // @ts-nocheck

    function hasValueOrEmptyAttribute(value) {
      return Boolean(value || value === "");
    }

    function BuildVuePlugin(hljs) {
      const Component = {
        props: ["language", "code", "autodetect"],
        data: function() {
          return {
            detectedLanguage: "",
            unknownLanguage: false
          };
        },
        computed: {
          className() {
            if (this.unknownLanguage) return "";

            return "hljs " + this.detectedLanguage;
          },
          highlighted() {
            // no idea what language to use, return raw code
            if (!this.autoDetect && !hljs.getLanguage(this.language)) {
              console.warn(`The language "${this.language}" you specified could not be found.`);
              this.unknownLanguage = true;
              return escapeHTML(this.code);
            }

            let result = {};
            if (this.autoDetect) {
              result = hljs.highlightAuto(this.code);
              this.detectedLanguage = result.language;
            } else {
              result = hljs.highlight(this.language, this.code, this.ignoreIllegals);
              this.detectedLanguage = this.language;
            }
            return result.value;
          },
          autoDetect() {
            return !this.language || hasValueOrEmptyAttribute(this.autodetect);
          },
          ignoreIllegals() {
            return true;
          }
        },
        // this avoids needing to use a whole Vue compilation pipeline just
        // to build Highlight.js
        render(createElement) {
          return createElement("pre", {}, [
            createElement("code", {
              class: this.className,
              domProps: { innerHTML: this.highlighted }
            })
          ]);
        }
        // template: `<pre><code :class="className" v-html="highlighted"></code></pre>`
      };

      const VuePlugin = {
        install(Vue) {
          Vue.component('highlightjs', Component);
        }
      };

      return { Component, VuePlugin };
    }

    /* plugin itself */

    /** @type {HLJSPlugin} */
    const mergeHTMLPlugin = {
      "after:highlightElement": ({ el, result, text }) => {
        const originalStream = nodeStream(el);
        if (!originalStream.length) return;

        const resultNode = document.createElement('div');
        resultNode.innerHTML = result.value;
        result.value = mergeStreams(originalStream, nodeStream(resultNode), text);
      }
    };

    /* Stream merging support functions */

    /**
     * @typedef Event
     * @property {'start'|'stop'} event
     * @property {number} offset
     * @property {Node} node
     */

    /**
     * @param {Node} node
     */
    function tag(node) {
      return node.nodeName.toLowerCase();
    }

    /**
     * @param {Node} node
     */
    function nodeStream(node) {
      /** @type Event[] */
      const result = [];
      (function _nodeStream(node, offset) {
        for (let child = node.firstChild; child; child = child.nextSibling) {
          if (child.nodeType === 3) {
            offset += child.nodeValue.length;
          } else if (child.nodeType === 1) {
            result.push({
              event: 'start',
              offset: offset,
              node: child
            });
            offset = _nodeStream(child, offset);
            // Prevent void elements from having an end tag that would actually
            // double them in the output. There are more void elements in HTML
            // but we list only those realistically expected in code display.
            if (!tag(child).match(/br|hr|img|input/)) {
              result.push({
                event: 'stop',
                offset: offset,
                node: child
              });
            }
          }
        }
        return offset;
      })(node, 0);
      return result;
    }

    /**
     * @param {any} original - the original stream
     * @param {any} highlighted - stream of the highlighted source
     * @param {string} value - the original source itself
     */
    function mergeStreams(original, highlighted, value) {
      let processed = 0;
      let result = '';
      const nodeStack = [];

      function selectStream() {
        if (!original.length || !highlighted.length) {
          return original.length ? original : highlighted;
        }
        if (original[0].offset !== highlighted[0].offset) {
          return (original[0].offset < highlighted[0].offset) ? original : highlighted;
        }

        /*
        To avoid starting the stream just before it should stop the order is
        ensured that original always starts first and closes last:

        if (event1 == 'start' && event2 == 'start')
          return original;
        if (event1 == 'start' && event2 == 'stop')
          return highlighted;
        if (event1 == 'stop' && event2 == 'start')
          return original;
        if (event1 == 'stop' && event2 == 'stop')
          return highlighted;

        ... which is collapsed to:
        */
        return highlighted[0].event === 'start' ? original : highlighted;
      }

      /**
       * @param {Node} node
       */
      function open(node) {
        /** @param {Attr} attr */
        function attributeString(attr) {
          return ' ' + attr.nodeName + '="' + escapeHTML(attr.value) + '"';
        }
        // @ts-ignore
        result += '<' + tag(node) + [].map.call(node.attributes, attributeString).join('') + '>';
      }

      /**
       * @param {Node} node
       */
      function close(node) {
        result += '</' + tag(node) + '>';
      }

      /**
       * @param {Event} event
       */
      function render(event) {
        (event.event === 'start' ? open : close)(event.node);
      }

      while (original.length || highlighted.length) {
        let stream = selectStream();
        result += escapeHTML(value.substring(processed, stream[0].offset));
        processed = stream[0].offset;
        if (stream === original) {
          /*
          On any opening or closing tag of the original markup we first close
          the entire highlighted node stack, then render the original tag along
          with all the following original tags at the same offset and then
          reopen all the tags on the highlighted stack.
          */
          nodeStack.reverse().forEach(close);
          do {
            render(stream.splice(0, 1)[0]);
            stream = selectStream();
          } while (stream === original && stream.length && stream[0].offset === processed);
          nodeStack.reverse().forEach(open);
        } else {
          if (stream[0].event === 'start') {
            nodeStack.push(stream[0].node);
          } else {
            nodeStack.pop();
          }
          render(stream.splice(0, 1)[0]);
        }
      }
      return result + escapeHTML(value.substr(processed));
    }

    /*

    For the reasoning behind this please see:
    https://github.com/highlightjs/highlight.js/issues/2880#issuecomment-747275419

    */

    /**
     * @param {string} message
     */
    const error = (message) => {
      console.error(message);
    };

    /**
     * @param {string} message
     * @param {any} args
     */
    const warn = (message, ...args) => {
      console.log(`WARN: ${message}`, ...args);
    };

    /**
     * @param {string} version
     * @param {string} message
     */
    const deprecated = (version, message) => {
      console.log(`Deprecated as of ${version}. ${message}`);
    };

    /*
    Syntax highlighting with language autodetection.
    https://highlightjs.org/
    */

    const escape$1 = escapeHTML;
    const inherit$1 = inherit;
    const NO_MATCH = Symbol("nomatch");

    /**
     * @param {any} hljs - object that is extended (legacy)
     * @returns {HLJSApi}
     */
    const HLJS = function(hljs) {
      // Global internal variables used within the highlight.js library.
      /** @type {Record<string, Language>} */
      const languages = Object.create(null);
      /** @type {Record<string, string>} */
      const aliases = Object.create(null);
      /** @type {HLJSPlugin[]} */
      const plugins = [];

      // safe/production mode - swallows more errors, tries to keep running
      // even if a single syntax or parse hits a fatal error
      let SAFE_MODE = true;
      const fixMarkupRe = /(^(<[^>]+>|\t|)+|\n)/gm;
      const LANGUAGE_NOT_FOUND = "Could not find the language '{}', did you forget to load/include a language module?";
      /** @type {Language} */
      const PLAINTEXT_LANGUAGE = { disableAutodetect: true, name: 'Plain text', contains: [] };

      // Global options used when within external APIs. This is modified when
      // calling the `hljs.configure` function.
      /** @type HLJSOptions */
      let options = {
        noHighlightRe: /^(no-?highlight)$/i,
        languageDetectRe: /\blang(?:uage)?-([\w-]+)\b/i,
        classPrefix: 'hljs-',
        tabReplace: null,
        useBR: false,
        languages: null,
        // beta configuration options, subject to change, welcome to discuss
        // https://github.com/highlightjs/highlight.js/issues/1086
        __emitter: TokenTreeEmitter
      };

      /* Utility functions */

      /**
       * Tests a language name to see if highlighting should be skipped
       * @param {string} languageName
       */
      function shouldNotHighlight(languageName) {
        return options.noHighlightRe.test(languageName);
      }

      /**
       * @param {HighlightedHTMLElement} block - the HTML element to determine language for
       */
      function blockLanguage(block) {
        let classes = block.className + ' ';

        classes += block.parentNode ? block.parentNode.className : '';

        // language-* takes precedence over non-prefixed class names.
        const match = options.languageDetectRe.exec(classes);
        if (match) {
          const language = getLanguage(match[1]);
          if (!language) {
            warn(LANGUAGE_NOT_FOUND.replace("{}", match[1]));
            warn("Falling back to no-highlight mode for this block.", block);
          }
          return language ? match[1] : 'no-highlight';
        }

        return classes
          .split(/\s+/)
          .find((_class) => shouldNotHighlight(_class) || getLanguage(_class));
      }

      /**
       * Core highlighting function.
       *
       * @param {string} languageName - the language to use for highlighting
       * @param {string} code - the code to highlight
       * @param {boolean} [ignoreIllegals] - whether to ignore illegal matches, default is to bail
       * @param {CompiledMode} [continuation] - current continuation mode, if any
       *
       * @returns {HighlightResult} Result - an object that represents the result
       * @property {string} language - the language name
       * @property {number} relevance - the relevance score
       * @property {string} value - the highlighted HTML code
       * @property {string} code - the original raw code
       * @property {CompiledMode} top - top of the current mode stack
       * @property {boolean} illegal - indicates whether any illegal matches were found
      */
      function highlight(languageName, code, ignoreIllegals, continuation) {
        /** @type {BeforeHighlightContext} */
        const context = {
          code,
          language: languageName
        };
        // the plugin can change the desired language or the code to be highlighted
        // just be changing the object it was passed
        fire("before:highlight", context);

        // a before plugin can usurp the result completely by providing it's own
        // in which case we don't even need to call highlight
        const result = context.result
          ? context.result
          : _highlight(context.language, context.code, ignoreIllegals, continuation);

        result.code = context.code;
        // the plugin can change anything in result to suite it
        fire("after:highlight", result);

        return result;
      }

      /**
       * private highlight that's used internally and does not fire callbacks
       *
       * @param {string} languageName - the language to use for highlighting
       * @param {string} code - the code to highlight
       * @param {boolean} [ignoreIllegals] - whether to ignore illegal matches, default is to bail
       * @param {CompiledMode} [continuation] - current continuation mode, if any
       * @returns {HighlightResult} - result of the highlight operation
      */
      function _highlight(languageName, code, ignoreIllegals, continuation) {
        const codeToHighlight = code;

        /**
         * Return keyword data if a match is a keyword
         * @param {CompiledMode} mode - current mode
         * @param {RegExpMatchArray} match - regexp match data
         * @returns {KeywordData | false}
         */
        function keywordData(mode, match) {
          const matchText = language.case_insensitive ? match[0].toLowerCase() : match[0];
          return Object.prototype.hasOwnProperty.call(mode.keywords, matchText) && mode.keywords[matchText];
        }

        function processKeywords() {
          if (!top.keywords) {
            emitter.addText(modeBuffer);
            return;
          }

          let lastIndex = 0;
          top.keywordPatternRe.lastIndex = 0;
          let match = top.keywordPatternRe.exec(modeBuffer);
          let buf = "";

          while (match) {
            buf += modeBuffer.substring(lastIndex, match.index);
            const data = keywordData(top, match);
            if (data) {
              const [kind, keywordRelevance] = data;
              emitter.addText(buf);
              buf = "";

              relevance += keywordRelevance;
              if (kind.startsWith("_")) {
                // _ implied for relevance only, do not highlight
                // by applying a class name
                buf += match[0];
              } else {
                const cssClass = language.classNameAliases[kind] || kind;
                emitter.addKeyword(match[0], cssClass);
              }
            } else {
              buf += match[0];
            }
            lastIndex = top.keywordPatternRe.lastIndex;
            match = top.keywordPatternRe.exec(modeBuffer);
          }
          buf += modeBuffer.substr(lastIndex);
          emitter.addText(buf);
        }

        function processSubLanguage() {
          if (modeBuffer === "") return;
          /** @type HighlightResult */
          let result = null;

          if (typeof top.subLanguage === 'string') {
            if (!languages[top.subLanguage]) {
              emitter.addText(modeBuffer);
              return;
            }
            result = _highlight(top.subLanguage, modeBuffer, true, continuations[top.subLanguage]);
            continuations[top.subLanguage] = /** @type {CompiledMode} */ (result.top);
          } else {
            result = highlightAuto(modeBuffer, top.subLanguage.length ? top.subLanguage : null);
          }

          // Counting embedded language score towards the host language may be disabled
          // with zeroing the containing mode relevance. Use case in point is Markdown that
          // allows XML everywhere and makes every XML snippet to have a much larger Markdown
          // score.
          if (top.relevance > 0) {
            relevance += result.relevance;
          }
          emitter.addSublanguage(result.emitter, result.language);
        }

        function processBuffer() {
          if (top.subLanguage != null) {
            processSubLanguage();
          } else {
            processKeywords();
          }
          modeBuffer = '';
        }

        /**
         * @param {Mode} mode - new mode to start
         */
        function startNewMode(mode) {
          if (mode.className) {
            emitter.openNode(language.classNameAliases[mode.className] || mode.className);
          }
          top = Object.create(mode, { parent: { value: top } });
          return top;
        }

        /**
         * @param {CompiledMode } mode - the mode to potentially end
         * @param {RegExpMatchArray} match - the latest match
         * @param {string} matchPlusRemainder - match plus remainder of content
         * @returns {CompiledMode | void} - the next mode, or if void continue on in current mode
         */
        function endOfMode(mode, match, matchPlusRemainder) {
          let matched = startsWith(mode.endRe, matchPlusRemainder);

          if (matched) {
            if (mode["on:end"]) {
              const resp = new Response(mode);
              mode["on:end"](match, resp);
              if (resp.ignore) matched = false;
            }

            if (matched) {
              while (mode.endsParent && mode.parent) {
                mode = mode.parent;
              }
              return mode;
            }
          }
          // even if on:end fires an `ignore` it's still possible
          // that we might trigger the end node because of a parent mode
          if (mode.endsWithParent) {
            return endOfMode(mode.parent, match, matchPlusRemainder);
          }
        }

        /**
         * Handle matching but then ignoring a sequence of text
         *
         * @param {string} lexeme - string containing full match text
         */
        function doIgnore(lexeme) {
          if (top.matcher.regexIndex === 0) {
            // no more regexs to potentially match here, so we move the cursor forward one
            // space
            modeBuffer += lexeme[0];
            return 1;
          } else {
            // no need to move the cursor, we still have additional regexes to try and
            // match at this very spot
            resumeScanAtSamePosition = true;
            return 0;
          }
        }

        /**
         * Handle the start of a new potential mode match
         *
         * @param {EnhancedMatch} match - the current match
         * @returns {number} how far to advance the parse cursor
         */
        function doBeginMatch(match) {
          const lexeme = match[0];
          const newMode = match.rule;

          const resp = new Response(newMode);
          // first internal before callbacks, then the public ones
          const beforeCallbacks = [newMode.__beforeBegin, newMode["on:begin"]];
          for (const cb of beforeCallbacks) {
            if (!cb) continue;
            cb(match, resp);
            if (resp.ignore) return doIgnore(lexeme);
          }

          if (newMode && newMode.endSameAsBegin) {
            newMode.endRe = escape(lexeme);
          }

          if (newMode.skip) {
            modeBuffer += lexeme;
          } else {
            if (newMode.excludeBegin) {
              modeBuffer += lexeme;
            }
            processBuffer();
            if (!newMode.returnBegin && !newMode.excludeBegin) {
              modeBuffer = lexeme;
            }
          }
          startNewMode(newMode);
          // if (mode["after:begin"]) {
          //   let resp = new Response(mode);
          //   mode["after:begin"](match, resp);
          // }
          return newMode.returnBegin ? 0 : lexeme.length;
        }

        /**
         * Handle the potential end of mode
         *
         * @param {RegExpMatchArray} match - the current match
         */
        function doEndMatch(match) {
          const lexeme = match[0];
          const matchPlusRemainder = codeToHighlight.substr(match.index);

          const endMode = endOfMode(top, match, matchPlusRemainder);
          if (!endMode) { return NO_MATCH; }

          const origin = top;
          if (origin.skip) {
            modeBuffer += lexeme;
          } else {
            if (!(origin.returnEnd || origin.excludeEnd)) {
              modeBuffer += lexeme;
            }
            processBuffer();
            if (origin.excludeEnd) {
              modeBuffer = lexeme;
            }
          }
          do {
            if (top.className) {
              emitter.closeNode();
            }
            if (!top.skip && !top.subLanguage) {
              relevance += top.relevance;
            }
            top = top.parent;
          } while (top !== endMode.parent);
          if (endMode.starts) {
            if (endMode.endSameAsBegin) {
              endMode.starts.endRe = endMode.endRe;
            }
            startNewMode(endMode.starts);
          }
          return origin.returnEnd ? 0 : lexeme.length;
        }

        function processContinuations() {
          const list = [];
          for (let current = top; current !== language; current = current.parent) {
            if (current.className) {
              list.unshift(current.className);
            }
          }
          list.forEach(item => emitter.openNode(item));
        }

        /** @type {{type?: MatchType, index?: number, rule?: Mode}}} */
        let lastMatch = {};

        /**
         *  Process an individual match
         *
         * @param {string} textBeforeMatch - text preceeding the match (since the last match)
         * @param {EnhancedMatch} [match] - the match itself
         */
        function processLexeme(textBeforeMatch, match) {
          const lexeme = match && match[0];

          // add non-matched text to the current mode buffer
          modeBuffer += textBeforeMatch;

          if (lexeme == null) {
            processBuffer();
            return 0;
          }

          // we've found a 0 width match and we're stuck, so we need to advance
          // this happens when we have badly behaved rules that have optional matchers to the degree that
          // sometimes they can end up matching nothing at all
          // Ref: https://github.com/highlightjs/highlight.js/issues/2140
          if (lastMatch.type === "begin" && match.type === "end" && lastMatch.index === match.index && lexeme === "") {
            // spit the "skipped" character that our regex choked on back into the output sequence
            modeBuffer += codeToHighlight.slice(match.index, match.index + 1);
            if (!SAFE_MODE) {
              /** @type {AnnotatedError} */
              const err = new Error('0 width match regex');
              err.languageName = languageName;
              err.badRule = lastMatch.rule;
              throw err;
            }
            return 1;
          }
          lastMatch = match;

          if (match.type === "begin") {
            return doBeginMatch(match);
          } else if (match.type === "illegal" && !ignoreIllegals) {
            // illegal match, we do not continue processing
            /** @type {AnnotatedError} */
            const err = new Error('Illegal lexeme "' + lexeme + '" for mode "' + (top.className || '<unnamed>') + '"');
            err.mode = top;
            throw err;
          } else if (match.type === "end") {
            const processed = doEndMatch(match);
            if (processed !== NO_MATCH) {
              return processed;
            }
          }

          // edge case for when illegal matches $ (end of line) which is technically
          // a 0 width match but not a begin/end match so it's not caught by the
          // first handler (when ignoreIllegals is true)
          if (match.type === "illegal" && lexeme === "") {
            // advance so we aren't stuck in an infinite loop
            return 1;
          }

          // infinite loops are BAD, this is a last ditch catch all. if we have a
          // decent number of iterations yet our index (cursor position in our
          // parsing) still 3x behind our index then something is very wrong
          // so we bail
          if (iterations > 100000 && iterations > match.index * 3) {
            const err = new Error('potential infinite loop, way more iterations than matches');
            throw err;
          }

          /*
          Why might be find ourselves here?  Only one occasion now.  An end match that was
          triggered but could not be completed.  When might this happen?  When an `endSameasBegin`
          rule sets the end rule to a specific match.  Since the overall mode termination rule that's
          being used to scan the text isn't recompiled that means that any match that LOOKS like
          the end (but is not, because it is not an exact match to the beginning) will
          end up here.  A definite end match, but when `doEndMatch` tries to "reapply"
          the end rule and fails to match, we wind up here, and just silently ignore the end.

          This causes no real harm other than stopping a few times too many.
          */

          modeBuffer += lexeme;
          return lexeme.length;
        }

        const language = getLanguage(languageName);
        if (!language) {
          error(LANGUAGE_NOT_FOUND.replace("{}", languageName));
          throw new Error('Unknown language: "' + languageName + '"');
        }

        const md = compileLanguage(language, { plugins });
        let result = '';
        /** @type {CompiledMode} */
        let top = continuation || md;
        /** @type Record<string,CompiledMode> */
        const continuations = {}; // keep continuations for sub-languages
        const emitter = new options.__emitter(options);
        processContinuations();
        let modeBuffer = '';
        let relevance = 0;
        let index = 0;
        let iterations = 0;
        let resumeScanAtSamePosition = false;

        try {
          top.matcher.considerAll();

          for (;;) {
            iterations++;
            if (resumeScanAtSamePosition) {
              // only regexes not matched previously will now be
              // considered for a potential match
              resumeScanAtSamePosition = false;
            } else {
              top.matcher.considerAll();
            }
            top.matcher.lastIndex = index;

            const match = top.matcher.exec(codeToHighlight);
            // console.log("match", match[0], match.rule && match.rule.begin)

            if (!match) break;

            const beforeMatch = codeToHighlight.substring(index, match.index);
            const processedCount = processLexeme(beforeMatch, match);
            index = match.index + processedCount;
          }
          processLexeme(codeToHighlight.substr(index));
          emitter.closeAllNodes();
          emitter.finalize();
          result = emitter.toHTML();

          return {
            // avoid possible breakage with v10 clients expecting
            // this to always be an integer
            relevance: Math.floor(relevance),
            value: result,
            language: languageName,
            illegal: false,
            emitter: emitter,
            top: top
          };
        } catch (err) {
          if (err.message && err.message.includes('Illegal')) {
            return {
              illegal: true,
              illegalBy: {
                msg: err.message,
                context: codeToHighlight.slice(index - 100, index + 100),
                mode: err.mode
              },
              sofar: result,
              relevance: 0,
              value: escape$1(codeToHighlight),
              emitter: emitter
            };
          } else if (SAFE_MODE) {
            return {
              illegal: false,
              relevance: 0,
              value: escape$1(codeToHighlight),
              emitter: emitter,
              language: languageName,
              top: top,
              errorRaised: err
            };
          } else {
            throw err;
          }
        }
      }

      /**
       * returns a valid highlight result, without actually doing any actual work,
       * auto highlight starts with this and it's possible for small snippets that
       * auto-detection may not find a better match
       * @param {string} code
       * @returns {HighlightResult}
       */
      function justTextHighlightResult(code) {
        const result = {
          relevance: 0,
          emitter: new options.__emitter(options),
          value: escape$1(code),
          illegal: false,
          top: PLAINTEXT_LANGUAGE
        };
        result.emitter.addText(code);
        return result;
      }

      /**
      Highlighting with language detection. Accepts a string with the code to
      highlight. Returns an object with the following properties:

      - language (detected language)
      - relevance (int)
      - value (an HTML string with highlighting markup)
      - second_best (object with the same structure for second-best heuristically
        detected language, may be absent)

        @param {string} code
        @param {Array<string>} [languageSubset]
        @returns {AutoHighlightResult}
      */
      function highlightAuto(code, languageSubset) {
        languageSubset = languageSubset || options.languages || Object.keys(languages);
        const plaintext = justTextHighlightResult(code);

        const results = languageSubset.filter(getLanguage).filter(autoDetection).map(name =>
          _highlight(name, code, false)
        );
        results.unshift(plaintext); // plaintext is always an option

        const sorted = results.sort((a, b) => {
          // sort base on relevance
          if (a.relevance !== b.relevance) return b.relevance - a.relevance;

          // always award the tie to the base language
          // ie if C++ and Arduino are tied, it's more likely to be C++
          if (a.language && b.language) {
            if (getLanguage(a.language).supersetOf === b.language) {
              return 1;
            } else if (getLanguage(b.language).supersetOf === a.language) {
              return -1;
            }
          }

          // otherwise say they are equal, which has the effect of sorting on
          // relevance while preserving the original ordering - which is how ties
          // have historically been settled, ie the language that comes first always
          // wins in the case of a tie
          return 0;
        });

        const [best, secondBest] = sorted;

        /** @type {AutoHighlightResult} */
        const result = best;
        result.second_best = secondBest;

        return result;
      }

      /**
      Post-processing of the highlighted markup:

      - replace TABs with something more useful
      - replace real line-breaks with '<br>' for non-pre containers

        @param {string} html
        @returns {string}
      */
      function fixMarkup(html) {
        if (!(options.tabReplace || options.useBR)) {
          return html;
        }

        return html.replace(fixMarkupRe, match => {
          if (match === '\n') {
            return options.useBR ? '<br>' : match;
          } else if (options.tabReplace) {
            return match.replace(/\t/g, options.tabReplace);
          }
          return match;
        });
      }

      /**
       * Builds new class name for block given the language name
       *
       * @param {HTMLElement} element
       * @param {string} [currentLang]
       * @param {string} [resultLang]
       */
      function updateClassName(element, currentLang, resultLang) {
        const language = currentLang ? aliases[currentLang] : resultLang;

        element.classList.add("hljs");
        if (language) element.classList.add(language);
      }

      /** @type {HLJSPlugin} */
      const brPlugin = {
        "before:highlightElement": ({ el }) => {
          if (options.useBR) {
            el.innerHTML = el.innerHTML.replace(/\n/g, '').replace(/<br[ /]*>/g, '\n');
          }
        },
        "after:highlightElement": ({ result }) => {
          if (options.useBR) {
            result.value = result.value.replace(/\n/g, "<br>");
          }
        }
      };

      const TAB_REPLACE_RE = /^(<[^>]+>|\t)+/gm;
      /** @type {HLJSPlugin} */
      const tabReplacePlugin = {
        "after:highlightElement": ({ result }) => {
          if (options.tabReplace) {
            result.value = result.value.replace(TAB_REPLACE_RE, (m) =>
              m.replace(/\t/g, options.tabReplace)
            );
          }
        }
      };

      /**
       * Applies highlighting to a DOM node containing code. Accepts a DOM node and
       * two optional parameters for fixMarkup.
       *
       * @param {HighlightedHTMLElement} element - the HTML element to highlight
      */
      function highlightElement(element) {
        /** @type HTMLElement */
        let node = null;
        const language = blockLanguage(element);

        if (shouldNotHighlight(language)) return;

        // support for v10 API
        fire("before:highlightElement",
          { el: element, language: language });

        node = element;
        const text = node.textContent;
        const result = language ? highlight(language, text, true) : highlightAuto(text);

        // support for v10 API
        fire("after:highlightElement", { el: element, result, text });

        element.innerHTML = result.value;
        updateClassName(element, language, result.language);
        element.result = {
          language: result.language,
          // TODO: remove with version 11.0
          re: result.relevance,
          relavance: result.relevance
        };
        if (result.second_best) {
          element.second_best = {
            language: result.second_best.language,
            // TODO: remove with version 11.0
            re: result.second_best.relevance,
            relavance: result.second_best.relevance
          };
        }
      }

      /**
       * Updates highlight.js global options with the passed options
       *
       * @param {Partial<HLJSOptions>} userOptions
       */
      function configure(userOptions) {
        if (userOptions.useBR) {
          deprecated("10.3.0", "'useBR' will be removed entirely in v11.0");
          deprecated("10.3.0", "Please see https://github.com/highlightjs/highlight.js/issues/2559");
        }
        options = inherit$1(options, userOptions);
      }

      /**
       * Highlights to all <pre><code> blocks on a page
       *
       * @type {Function & {called?: boolean}}
       */
      // TODO: remove v12, deprecated
      const initHighlighting = () => {
        if (initHighlighting.called) return;
        initHighlighting.called = true;

        deprecated("10.6.0", "initHighlighting() is deprecated.  Use highlightAll() instead.");

        const blocks = document.querySelectorAll('pre code');
        blocks.forEach(highlightElement);
      };

      // Higlights all when DOMContentLoaded fires
      // TODO: remove v12, deprecated
      function initHighlightingOnLoad() {
        deprecated("10.6.0", "initHighlightingOnLoad() is deprecated.  Use highlightAll() instead.");
        wantsHighlight = true;
      }

      let wantsHighlight = false;

      /**
       * auto-highlights all pre>code elements on the page
       */
      function highlightAll() {
        // if we are called too early in the loading process
        if (document.readyState === "loading") {
          wantsHighlight = true;
          return;
        }

        const blocks = document.querySelectorAll('pre code');
        blocks.forEach(highlightElement);
      }

      function boot() {
        // if a highlight was requested before DOM was loaded, do now
        if (wantsHighlight) highlightAll();
      }

      // make sure we are in the browser environment
      if (typeof window !== 'undefined' && window.addEventListener) {
        window.addEventListener('DOMContentLoaded', boot, false);
      }

      /**
       * Register a language grammar module
       *
       * @param {string} languageName
       * @param {LanguageFn} languageDefinition
       */
      function registerLanguage(languageName, languageDefinition) {
        let lang = null;
        try {
          lang = languageDefinition(hljs);
        } catch (error$1) {
          error("Language definition for '{}' could not be registered.".replace("{}", languageName));
          // hard or soft error
          if (!SAFE_MODE) { throw error$1; } else { error(error$1); }
          // languages that have serious errors are replaced with essentially a
          // "plaintext" stand-in so that the code blocks will still get normal
          // css classes applied to them - and one bad language won't break the
          // entire highlighter
          lang = PLAINTEXT_LANGUAGE;
        }
        // give it a temporary name if it doesn't have one in the meta-data
        if (!lang.name) lang.name = languageName;
        languages[languageName] = lang;
        lang.rawDefinition = languageDefinition.bind(null, hljs);

        if (lang.aliases) {
          registerAliases(lang.aliases, { languageName });
        }
      }

      /**
       * Remove a language grammar module
       *
       * @param {string} languageName
       */
      function unregisterLanguage(languageName) {
        delete languages[languageName];
        for (const alias of Object.keys(aliases)) {
          if (aliases[alias] === languageName) {
            delete aliases[alias];
          }
        }
      }

      /**
       * @returns {string[]} List of language internal names
       */
      function listLanguages() {
        return Object.keys(languages);
      }

      /**
        intended usage: When one language truly requires another

        Unlike `getLanguage`, this will throw when the requested language
        is not available.

        @param {string} name - name of the language to fetch/require
        @returns {Language | never}
      */
      function requireLanguage(name) {
        deprecated("10.4.0", "requireLanguage will be removed entirely in v11.");
        deprecated("10.4.0", "Please see https://github.com/highlightjs/highlight.js/pull/2844");

        const lang = getLanguage(name);
        if (lang) { return lang; }

        const err = new Error('The \'{}\' language is required, but not loaded.'.replace('{}', name));
        throw err;
      }

      /**
       * @param {string} name - name of the language to retrieve
       * @returns {Language | undefined}
       */
      function getLanguage(name) {
        name = (name || '').toLowerCase();
        return languages[name] || languages[aliases[name]];
      }

      /**
       *
       * @param {string|string[]} aliasList - single alias or list of aliases
       * @param {{languageName: string}} opts
       */
      function registerAliases(aliasList, { languageName }) {
        if (typeof aliasList === 'string') {
          aliasList = [aliasList];
        }
        aliasList.forEach(alias => { aliases[alias.toLowerCase()] = languageName; });
      }

      /**
       * Determines if a given language has auto-detection enabled
       * @param {string} name - name of the language
       */
      function autoDetection(name) {
        const lang = getLanguage(name);
        return lang && !lang.disableAutodetect;
      }

      /**
       * Upgrades the old highlightBlock plugins to the new
       * highlightElement API
       * @param {HLJSPlugin} plugin
       */
      function upgradePluginAPI(plugin) {
        // TODO: remove with v12
        if (plugin["before:highlightBlock"] && !plugin["before:highlightElement"]) {
          plugin["before:highlightElement"] = (data) => {
            plugin["before:highlightBlock"](
              Object.assign({ block: data.el }, data)
            );
          };
        }
        if (plugin["after:highlightBlock"] && !plugin["after:highlightElement"]) {
          plugin["after:highlightElement"] = (data) => {
            plugin["after:highlightBlock"](
              Object.assign({ block: data.el }, data)
            );
          };
        }
      }

      /**
       * @param {HLJSPlugin} plugin
       */
      function addPlugin(plugin) {
        upgradePluginAPI(plugin);
        plugins.push(plugin);
      }

      /**
       *
       * @param {PluginEvent} event
       * @param {any} args
       */
      function fire(event, args) {
        const cb = event;
        plugins.forEach(function(plugin) {
          if (plugin[cb]) {
            plugin[cb](args);
          }
        });
      }

      /**
      Note: fixMarkup is deprecated and will be removed entirely in v11

      @param {string} arg
      @returns {string}
      */
      function deprecateFixMarkup(arg) {
        deprecated("10.2.0", "fixMarkup will be removed entirely in v11.0");
        deprecated("10.2.0", "Please see https://github.com/highlightjs/highlight.js/issues/2534");

        return fixMarkup(arg);
      }

      /**
       *
       * @param {HighlightedHTMLElement} el
       */
      function deprecateHighlightBlock(el) {
        deprecated("10.7.0", "highlightBlock will be removed entirely in v12.0");
        deprecated("10.7.0", "Please use highlightElement now.");

        return highlightElement(el);
      }

      /* Interface definition */
      Object.assign(hljs, {
        highlight,
        highlightAuto,
        highlightAll,
        fixMarkup: deprecateFixMarkup,
        highlightElement,
        // TODO: Remove with v12 API
        highlightBlock: deprecateHighlightBlock,
        configure,
        initHighlighting,
        initHighlightingOnLoad,
        registerLanguage,
        unregisterLanguage,
        listLanguages,
        getLanguage,
        registerAliases,
        requireLanguage,
        autoDetection,
        inherit: inherit$1,
        addPlugin,
        // plugins for frameworks
        vuePlugin: BuildVuePlugin(hljs).VuePlugin
      });

      hljs.debugMode = function() { SAFE_MODE = false; };
      hljs.safeMode = function() { SAFE_MODE = true; };
      hljs.versionString = version;

      for (const key in MODES) {
        // @ts-ignore
        if (typeof MODES[key] === "object") {
          // @ts-ignore
          deepFreezeEs6(MODES[key]);
        }
      }

      // merge all the modes/regexs into our main object
      Object.assign(hljs, MODES);

      // built-in plugins, likely to be moved out of core in the future
      hljs.addPlugin(brPlugin); // slated to be removed in v11
      hljs.addPlugin(mergeHTMLPlugin);
      hljs.addPlugin(tabReplacePlugin);
      return hljs;
    };

    // export an "instance" of the highlighter
    var highlight = HLJS({});

    return highlight;

}());
if (typeof exports === 'object' && typeof module !== 'undefined') { module.exports = hljs; }

hljs.registerLanguage('java', function () {
  'use strict';

  // https://docs.oracle.com/javase/specs/jls/se15/html/jls-3.html#jls-3.10
  var decimalDigits = '[0-9](_*[0-9])*';
  var frac = `\\.(${decimalDigits})`;
  var hexDigits = '[0-9a-fA-F](_*[0-9a-fA-F])*';
  var NUMERIC = {
    className: 'number',
    variants: [
      // DecimalFloatingPointLiteral
      // including ExponentPart
      { begin: `(\\b(${decimalDigits})((${frac})|\\.)?|(${frac}))` +
        `[eE][+-]?(${decimalDigits})[fFdD]?\\b` },
      // excluding ExponentPart
      { begin: `\\b(${decimalDigits})((${frac})[fFdD]?\\b|\\.([fFdD]\\b)?)` },
      { begin: `(${frac})[fFdD]?\\b` },
      { begin: `\\b(${decimalDigits})[fFdD]\\b` },

      // HexadecimalFloatingPointLiteral
      { begin: `\\b0[xX]((${hexDigits})\\.?|(${hexDigits})?\\.(${hexDigits}))` +
        `[pP][+-]?(${decimalDigits})[fFdD]?\\b` },

      // DecimalIntegerLiteral
      { begin: '\\b(0|[1-9](_*[0-9])*)[lL]?\\b' },

      // HexIntegerLiteral
      { begin: `\\b0[xX](${hexDigits})[lL]?\\b` },

      // OctalIntegerLiteral
      { begin: '\\b0(_*[0-7])*[lL]?\\b' },

      // BinaryIntegerLiteral
      { begin: '\\b0[bB][01](_*[01])*[lL]?\\b' },
    ],
    relevance: 0
  };

  /*
  Language: Java
  Author: Vsevolod Solovyov <vsevolod.solovyov@gmail.com>
  Category: common, enterprise
  Website: https://www.java.com/
  */

  function java(hljs) {
    var JAVA_IDENT_RE = '[\u00C0-\u02B8a-zA-Z_$][\u00C0-\u02B8a-zA-Z_$0-9]*';
    var GENERIC_IDENT_RE = JAVA_IDENT_RE + '(<' + JAVA_IDENT_RE + '(\\s*,\\s*' + JAVA_IDENT_RE + ')*>)?';
    var KEYWORDS = 'false synchronized int abstract float private char boolean var static null if const ' +
      'for true while long strictfp finally protected import native final void ' +
      'enum else break transient catch instanceof byte super volatile case assert short ' +
      'package default double public try this switch continue throws protected public private ' +
      'module requires exports do';

    var ANNOTATION = {
      className: 'meta',
      begin: '@' + JAVA_IDENT_RE,
      contains: [
        {
          begin: /\(/,
          end: /\)/,
          contains: ["self"] // allow nested () inside our annotation
        },
      ]
    };
    const NUMBER = NUMERIC;

    return {
      name: 'Java',
      aliases: ['jsp'],
      keywords: KEYWORDS,
      illegal: /<\/|#/,
      contains: [
        hljs.COMMENT(
          '/\\*\\*',
          '\\*/',
          {
            relevance: 0,
            contains: [
              {
                // eat up @'s in emails to prevent them to be recognized as doctags
                begin: /\w+@/, relevance: 0
              },
              {
                className: 'doctag',
                begin: '@[A-Za-z]+'
              }
            ]
          }
        ),
        // relevance boost
        {
          begin: /import java\.[a-z]+\./,
          keywords: "import",
          relevance: 2
        },
        hljs.C_LINE_COMMENT_MODE,
        hljs.C_BLOCK_COMMENT_MODE,
        hljs.APOS_STRING_MODE,
        hljs.QUOTE_STRING_MODE,
        {
          className: 'class',
          beginKeywords: 'class interface enum', end: /[{;=]/, excludeEnd: true,
          // TODO: can this be removed somehow?
          // an extra boost because Java is more popular than other languages with
          // this same syntax feature (this is just to preserve our tests passing
          // for now)
          relevance: 1,
          keywords: 'class interface enum',
          illegal: /[:"\[\]]/,
          contains: [
            { beginKeywords: 'extends implements' },
            hljs.UNDERSCORE_TITLE_MODE
          ]
        },
        {
          // Expression keywords prevent 'keyword Name(...)' from being
          // recognized as a function definition
          beginKeywords: 'new throw return else',
          relevance: 0
        },
        {
          className: 'class',
          begin: 'record\\s+' + hljs.UNDERSCORE_IDENT_RE + '\\s*\\(',
          returnBegin: true,
          excludeEnd: true,
          end: /[{;=]/,
          keywords: KEYWORDS,
          contains: [
            { beginKeywords: "record" },
            {
              begin: hljs.UNDERSCORE_IDENT_RE + '\\s*\\(',
              returnBegin: true,
              relevance: 0,
              contains: [hljs.UNDERSCORE_TITLE_MODE]
            },
            {
              className: 'params',
              begin: /\(/, end: /\)/,
              keywords: KEYWORDS,
              relevance: 0,
              contains: [
                hljs.C_BLOCK_COMMENT_MODE
              ]
            },
            hljs.C_LINE_COMMENT_MODE,
            hljs.C_BLOCK_COMMENT_MODE
          ]
        },
        {
          className: 'function',
          begin: '(' + GENERIC_IDENT_RE + '\\s+)+' + hljs.UNDERSCORE_IDENT_RE + '\\s*\\(', returnBegin: true, end: /[{;=]/,
          excludeEnd: true,
          keywords: KEYWORDS,
          contains: [
            {
              begin: hljs.UNDERSCORE_IDENT_RE + '\\s*\\(', returnBegin: true,
              relevance: 0,
              contains: [hljs.UNDERSCORE_TITLE_MODE]
            },
            {
              className: 'params',
              begin: /\(/, end: /\)/,
              keywords: KEYWORDS,
              relevance: 0,
              contains: [
                ANNOTATION,
                hljs.APOS_STRING_MODE,
                hljs.QUOTE_STRING_MODE,
                NUMBER,
                hljs.C_BLOCK_COMMENT_MODE
              ]
            },
            hljs.C_LINE_COMMENT_MODE,
            hljs.C_BLOCK_COMMENT_MODE
          ]
        },
        NUMBER,
        ANNOTATION
      ]
    };
  }

  return java;

  return module.exports.definer || module.exports;

}());

hljs.registerLanguage('kim', function () {
  'use strict';

  // https://docs.oracle.com/javase/specs/jls/se15/html/jls-3.html#jls-3.10
  var decimalDigits = '[0-9](_*[0-9])*';
  var frac = `\\.(${decimalDigits})`;
  var hexDigits = '[0-9a-fA-F](_*[0-9a-fA-F])*';
  var NUMERIC = {
    className: 'number',
    variants: [
      // DecimalFloatingPointLiteral
      // including ExponentPart
      { begin: `(\\b(${decimalDigits})((${frac})|\\.)?|(${frac}))` +
        `[eE][+-]?(${decimalDigits})[fFdD]?\\b` },
      // excluding ExponentPart
      { begin: `\\b(${decimalDigits})((${frac})[fFdD]?\\b|\\.([fFdD]\\b)?)` },
      { begin: `(${frac})[fFdD]?\\b` },
      { begin: `\\b(${decimalDigits})[fFdD]\\b` },

      // HexadecimalFloatingPointLiteral
      { begin: `\\b0[xX]((${hexDigits})\\.?|(${hexDigits})?\\.(${hexDigits}))` +
        `[pP][+-]?(${decimalDigits})[fFdD]?\\b` },

      // DecimalIntegerLiteral
      { begin: '\\b(0|[1-9](_*[0-9])*)[lL]?\\b' },

      // HexIntegerLiteral
      { begin: `\\b0[xX](${hexDigits})[lL]?\\b` },

      // OctalIntegerLiteral
      { begin: '\\b0(_*[0-7])*[lL]?\\b' },

      // BinaryIntegerLiteral
      { begin: '\\b0[bB][01](_*[01])*[lL]?\\b' },
    ],
    relevance: 0
  };

  /*
  Support for k.IM. Lots to do. 
  Minimal list:
  	- highlight URNs (at least the normal patterns)
  */

  function kim(hljs) {
    var JAVA_IDENT_RE = '[\u00C0-\u02B8a-zA-Z_$][\u00C0-\u02B8a-zA-Z_$0-9]*';

    var KEYWORDS = 'extent metadata without defines disjoint exclusive energy using containing optional then version each quality as contains at applies domain object minus functional role ordering least uncertainty increases into unless scenario by temperature initialization where attribute relationship set charge learn column integrate describes transition viscosity decreases to implies thing ratio agent resolve project language do down observe assessment acceleration has inherent instantiation termination area finally count weight volume exposing reactive extends authority parameters changed private away distance mass for duration not related rate constituent exposes root averaged definition class over false length affects bond with money observing realm resistivity container named interactive during cooccurrent type required number identified children context model text classifies per presence if between inclusive deniable in linking probability nothing classified is aggregated priority resistance discretizes requires summed proportion occurrence unknown entropy identity angle event inherits within caused more change abstract marks confers equals date integer float percentage only from links observability deliberative otherwise all void imports level follows pressure plus most core outside true creates no rescaling covering purpose configuration discretized part according worldview times total targeting causing structural and of define magnitude row value causant on compresent lookup inverse move amount process monetary or subjective quantity match adjacent velocity exactly any contained boolean namespace consists uses';
    var QUALITIES = 'hydrology:PotentialEvapotranspiredWaterVolume ecology:VascularPlantBiomass soil.incubation:SoilDepth soil.incubation:Fertility ecology.incubation:NormalizedDifferenceMoistureIndex biology:Age hydrology.incubation:StreamWidth ecology:AlgalBiomass im:Sustainability ecology:HardwoodBiomass conservation:ConservationStatus hydrology:RunoffWaterVolume hydrology:FogInterceptionVolume hydrology.incubation:CropCoefficient geography:Elevation soil:SlopeStability ecology:Biomass geography:Heading hydrology:RunoffVelocity soil:SoilErodibility im:FunctionalConservationStatus earth:SolarRadiation agriculture:Yield soil.incubation:Roughness demography:SocialAcceptance geography:StreamGradient im:ConservationStatus hydrology:InfiltratedWaterVolume chemistry:Enthalpy ecology:SoftwoodBiomass soil.incubation:SoilFertility ecology:ForestFragmentation chemistry:ThermochemicalEnergy im:Impedance im:Risk im:Suitability soil:SlopeSteepnessAndLength es.aesthetics:AestheticValueEnjoyed hydrology:ContributingArea es.nca:Condition ecology.incubation:NormalizedBurnRatio hydrology:StreamOrder economics:EconomicProduct soil.incubation:PermanentWiltingPoint landcover:LandscapeHeterogeneity physical.incubation:Fragmentation chemistry:Ph economics:Income landcover:LandCoverType hydrology:Storativity hydrology:AvailableWaterCapacity economics:NationalProduct hydrology:SnowMeltVolume hydrology:Seepage ecology:BelowGroundBiomass ecology:LeafAreaIndex geography:LidarElevation hydrology:DrainageDensity hydrology:WaterVolume chemistry:Reactivity ecology:TerrestrialBiomass hydrology:CurveNumber earth:PrecipitationVolume chemistry:ExchangeCapacity soil.incubation:UltimateWiltingPoint soil.incubation:DroughtSeverityIndex ecology.incubation:FractionPhotosyntheticallyActiveRadiation soil:SupportPractice hydrology:ImperviousCover geography:SlopeLength earth:HailVolume soil:SaturatedHydraulicConductivity im:VisualConservationStatus hydrology.incubation:StreamFlow hydrology:EvaporatedWaterVolume ecology:PlantBiomass ecology:PrimaryProductivity im:StructuralConservationStatus es.nca:EcosystemType soil:CoverManagement hydrology.incubation:RefreezingWaterVolume chemistry:IonizationEnergy economics:DomesticProduct im:Change earth:SnowfallVolume hydrology:FlowDirection geography:Slope ecology.incubation:EcosystemType earth:AtmosphericTemperature soil:DroughtSeverity ecology:PrimaryProduction biology:Biomass ecology:NetPrimaryProduction ecology:LandscapeRichness earth.incubation:PhotosyntheticallyActiveRadiation hydrology:FloodWaterVolume chemistry:Purity ecology:WoodBiomass ecology:TreeCanopyHeight ecology:AboveGroundBiomass geography:Aspect im:Conductance hydrology:LocallyExchangedWaterVolume earth:RainfallVolume es.aesthetics:AestheticValue soil:RainfallRunoffErosivity geography:BathymetricDepth hydrology:EvapotranspiredWaterVolume geography:BathymetricSlope ecology:Fragmentation ecology:NormalizedDifferenceVegetationIndex hydrology.incubation:FieldCapacity ecology:RootBiomass ecology:AquaticBiomass ecology.incubation:EnhancedVegetationIndex ecology:CanopyCover hydrology:BaseFlowWaterVolume chemistry:BindingEnergy ecology:SpeciesRichness earth:TopographicHeterogeneity ecology.incubation:LeafAreaIndex physical:Fragmentation soil:SedimentSource ecology:NormalizedDifferenceWaterIndex';
    var SUBJECTS = 'infrastructure:Seawall earth:Footslope ecology.incubation:Seeds infrastructure:Campsite infrastructure:LimitedAccessRoad infrastructure:House infrastructure:CaravanSite earth:Dune infrastructure:MiningInfrastructure im:VariousThings infrastructure:SecondaryNationalPort earth:MountainPeak infrastructure:Cabin earth:Wetland earth:Shoulder demography:Household infrastructure:OilGasWell hydrology:PerennialFlow engineering:Car infrastructure:Ramp infrastructure:InterstateHighway infrastructure:PrimaryNationalPort demography:SocialIndividual infrastructure:BikePath infrastructure:Road infrastructure:TransmissionLine earth:Ocean infrastructure:ActiveOilGasWell infrastructure:PicnicArea infrastructure:Anchorage chemistry:Ion infrastructure:Motel infrastructure:Groin infrastructure:Path infrastructure:Ditch hydrology:SurfaceFlowFrequency infrastructure:Dam ecology.incubation:Branches infrastructure:PowerPlant infrastructure:Restaurant ecology:Community ecology:Population infrastructure:WindTurbine infrastructure:WeatherStation hydrology:RiverBasin hydrology:IntermittentFlow infrastructure:BoatLaunch earth:Spur ecology.incubation:Flower biology:Flower earth:Sea earth:Peak infrastructure:FerryRoute earth:Cliff infrastructure:Chalet earth:Location hydrology:Watershed demography:HumanPopulation infrastructure:SamplingSite infrastructure:CommunicationTower earth:GeoFormation ecology:Shrub infrastructure:Levee infrastructure:Mine hydrology:Floodplain infrastructure:Trail earth:Mountain infrastructure:Aqueduct infrastructure:Railway infrastructure:DetentionBasin im:MixedAnything infrastructure:SecondaryInternationalPort infrastructure:Village ecology.incubation:Foliage earth:River earth:Waterway earth:Hollow infrastructure:City earth:LandFormation chemistry:Cation infrastructure:StateHighway earth:Region biology:Leaf earth:Stream earth:Flat infrastructure:Hostel infrastructure:Hotel infrastructure:UrbanPark engineering:Vehicle earth:Slope biology:Insect hydrology:MountainfrontRechargeZone infrastructure:WastewaterTreatmentPlant infrastructure:Building earth:Lake infrastructure:AbandonedOilGasWell biology:Plant demography:HumanIndividual biology:Individual infrastructure:LocalRoad infrastructure:Port earth:Ridge hydrology:RechargeZone earth:Coastline ecology:Tree infrastructure:EnergyInfrastructure infrastructure:MajorRoad chemistry:Anion infrastructure:Town earth:WaterBody infrastructure:Bank earth.incubation:IceGlacier demography:SocialGroup infrastructure:Airfield infrastructure:PrimaryInternationalPort earth:Coast infrastructure:Trailhead infrastructure:HumanSettlement infrastructure:MinorRoad infrastructure:Jetty im:OtherThings ecology:Canopy infrastructure:HydropowerTurbine infrastructure:RecreationalArea infrastructure:CommunicationInfrastructure conservation:ProtectedArea agriculture:Cropfield earth:Site infrastructure:Church infrastructure:GuestHouse demography:Community infrastructure:NoOilGasWell infrastructure:Mosque earth:Valley earth.incubation:Glacier ecology.incubation:Root infrastructure:SolarArray earth:Pit infrastructure:PedestrianRoad earth:StreamJunction ecology.incubation:Stump policy:Institution infrastructure:Synagogue hydrology:EphemeralFlow earth:Hill infrastructure:WildernessHut infrastructure:Reservoir infrastructure:AlpineHut earth:Reach im:NotSomething infrastructure:Apartment ecology.incubation:Stem hydrology:StreamOutlet infrastructure:Bridge hydrology:InstreamRechargeZone earth:BreakFoothill earth.incubation:DebrisGlacier ecology.incubation:Bark infrastructure:Playground infrastructure:Highway earth:Escarpment infrastructure:WaterTreatmentPlant infrastructure:WorshipBuilding infrastructure:Accomodation';
    var PROCESSES = 'earth:Hailing physical.incubation:Recombination earth:Wind physical:Sublimation hydrology.incubation:Interception agriculture:Planting agriculture:Girdling agriculture:Tillage hydrology:Evapotranspiration chemistry.incubation:Immobilization physical.incubation:Vaporization physical.incubation:Condensation earth:Weathering physical.incubation:Melting earth:Erosion ecology.incubation:Respiration hydrology:WatershedFormation im:Movement hydrology:WaterExtraction physical.incubation:Freezing soil:SoilGenesis chemistry.incubation:Nitrification agriculture:Mariculture hydrology.incubation:CapillaryRise ecology:HabitatChoice infrastructure.incubation:NaturalResourceUse im:Displacement chemistry.incubation:Leaching agriculture:Sowing im:Growth ecology.incubation:PrimaryProduction ecology.incubation:Photosynthesis agriculture:Harvesting physical:Freezing ecology:Growth physical.incubation:Sublimation agriculture:Cultivation ecology.incubation:PhenologyActivity ecology:BiomassDynamics hydrology.incubation:SnowMelt agriculture:Staking hydrology.incubation:GlacierMelt earth:Rainfall hydrology:Runoff hydrology:SurfaceWaterFlow agriculture:Thinning im:Coupling agriculture:Pruning im:Homeostasis chemistry.incubation:Volatilization earth:Snowfall biology:Reproduction biology:Growth es.pollination:Pollination agriculture:Deblossoming es:FloodRegulation ecology:EcologicalProcess hydrology:Percolation physical:Recombination agriculture:Hoeing physical:Deposition ecology:EcologicalSuccession physical:Melting earth:Sedimentation hydrology:Infiltration agriculture:Hilling agriculture:Rolling agriculture:SeedBedPreparation physical.incubation:Deposition es:LandslideRegulation hydrology:AquiferRecharge biology.incubation:Digestion im:Decoupling physical:Ionization im:Dissipation physical.incubation:Ionization agriculture:Ridging im:Assessment es:ClimateRegulation earth:Weather earth:PlateTectonics behavior:Recreation physical:Vaporization chemistry.incubation:Mineralization agriculture:StubbleCleaning chemistry.incubation:Denitrification agriculture.incubation:Irrigation biology.incubation:Ingestion hydrology:WaterMovement policy:Jurisdiction agriculture:Aquaculture hydrology:WaterBalance hydrology:BaseFlow earth:Precipitation physical:Condensation agriculture:Mulching biology.incubation:Excretion es.aesthetics:VisualEnjoyment agriculture:Defruiting agriculture:Agriculture agriculture.incubation:Fertilization soil:Pedogenesis';
    var RELATIONSHIPS = 'ecology:Pollination ecology:EcologicalInteraction ecology:CarbonSequestration ecology:MaterialEnergyFlow behavior:HelpRequest demography:Kinship ecology:NutrientFlow es.aesthetics:VisuallyEnjoys ecology:Symbiosis ecology:Parasitism ecology:MatingSignaling ecology:Competition ecology:WaterFlow ecology:Commensalism ecology:TerritorialMarking demography:Brotherhood demography:Friendship behavior:TravelConnection hydrology:WaterFlow ecology:NitrogenFixation ecology:Evapotranspiration ecology:Mutualism ecology:Predation ecology:CarbonStorage ecology:NonMaterialEnergyFlow demography:SocialMediaConnection earth:StreamConnection';
    var PREDICATES = 'geography:Iran geography:Iraq geography:ClippertonIsland geology:UltramaphicRock physical:Radiant landcover:InlandWaterBody ecology.incubation:CoastalSaltmarsh geography:IsleOfMan es.nca:BorealCoolTemperatePalustrineWetland agriculture:Potato biology.nomenclature:PinusPinaster ecology:MacroalgalKelpHabitat earth:Geostationary chemistry:Mercury es.pollination:PollinationEcosystemBenefit ecology.incubation:EpisodicAridFloodplain infrastructure:Spaceport geography:BosniaAndHerzegovina im:Incoming geography:Peru behavior:Educational ecology:Vegetation geography:Micronesia soil:MediumGrain agriculture:Sunflower im:MeanAnything agriculture:Cherry chemistry:Samarium geography:SvalbardJanMayenIslands ecology:LateSuccession geography:Lao agriculture:WinterWheat earth:Polar geography:Latvia agriculture:SoyBean landcover:IndustrialCommercialUnits agriculture:Pig physical:SouthWest landcover.incubation:ShrublandShrubbyWoodland agriculture:Pasture chemistry:Lawrencium landcover:ClosedDeciduousConiferousForest geology:Pahoehoe agriculture:Lemon geography:CostaRica ecology.incubation:IntensiveLandUseSystem im:Likely im:Total landcover:Construction landcover:SeaAndOcean landcover:SclerophyllousVegetation geography:UnitedStates ecology:EarlySuccession agriculture:Cereal es.nca:SubtropicalWarmTemperateForestedWetland biology.nomenclature:PinusSylvestris chemistry:Organic ecology:Pollinating chemistry:Burned chemistry:Helium geography:SouthAmericanRegion agriculture:Pollinator chemistry:Palladium im:Maximum policy:Conservative im:Inappropriate landcover:ForestSeminatural es.water:WaterNonConsumingTransactor ses:Provided geography:Curacao im:Average chemistry:Holmium conservation:Pristine geography:Albania es.nca:WarmTemperateTropicalMarsh biology.nomenclature:Salmonidae landcover:SparseShrubCover geography:Bangladesh policy:Municipality earth:Snow ecology:Autotroph geography:Kiribati behavior:Interactive agriculture:Maize biology.nomenclature:Crustacean landcover:Saline biology.nomenclature:UlexEuropaeus geography:FrenchGuiana chemistry:Francium chemistry:Thorium landcover:NonVegetatedStillWaterBody agriculture:Wheat es.nca:CoastalEcosystem physical:Liquid chemistry:Yttrium biology.nomenclature:NonInsect geology:Pumice geography:Slovakia biology.nomenclature:Picea earth:Downstream geography:NorthernMarianaIslands geography:Brunei ecology:CoralReefHabitat es.nca:TerrestrialEcosystem landcover:PermanentlyIrrigatedArableLand chemistry:Silver earth:Upstream geography:Macedonia im:Differential im:Visible es.water:WaterConsumptionEcosystemBenefit im:ExtremelyHigh geology:Aphanite geography:InsularAsianRegion geography:Suriname policy:State demography:Adolescent im:Impervious geography:Lesotho agriculture:Rice ecology:PelagicHabitat chemistry:Cerium geography:Belize im:Compromised agriculture:Pear geography:Lithuania agriculture:Buffalo geography:MarshallIslands earth:Littoral geography:Chad geography:Hungary im:Continuous ecology:Fauna ecology.incubation:SeasonallyDryTemperateHeathShrubland agriculture:Duck geography:Austria geography:Tanzania geography:Canada geography:Comoros infrastructure:Airport agriculture:Almond ses:Provisioning policy:ReligiousPurpose physical:South physical:Chemical geography:SaoTomeAndPrincipe chemistry:Mendelevium chemistry:Roentgenium im:Long ecology.incubation:BorealTemperateMontaneForestWoodland geography:Kenya es.nca:PolarAlpineRockyOutcrop agriculture:Mango landcover:IntertidalFlat landcover:MarineWaterBody geography:Mayotte geography:FrenchPolynesia soil:SubSoil ecology:GraminoidVegetation demography:Child landcover:BroadleafForest geology:Tuff im:Structural ecology:Deciduous geography:Russia im:Normalized chemistry:Astatine ecology:Brackish geography:Malawi landcover.incubation:TemperateBorealForestWoodland chemistry:Rhenium geography:Italy im:Up ecology.incubation:YoungRockyPavementLavaflowScree im:Moderate biology.nomenclature:Odonata biology:Hermaphrodite biology:Insecta im:Right ecology.incubation:TropicalSubtropicalForest geography:DominicanRepublic policy:EducationalPurpose soil:MediumFineGrain geology:Basalt es.water:WaterMovementConnection ecology:BorealConiferousForest geography:Lebanon geography:Guadeloupe agriculture:Watermelon landcover:LowDensityUrban es.aesthetics:AestheticViewEcosystemBenefit es.nca:TropicalFloodedForestPeatForest biology.nomenclature:Robinia landcover:InactiveAgriculturalLand earth:Bedrock landcover:Dump behavior:VisualContemplation soil:AHorizon hydrology:Pervious landcover.incubation:Aquatic ecology:ShrubVegetation geology:MaficRock es.nca:CoolTemperateHeathland hydrology:WaterPermanence geography:Montserrat economics:Estimated landcover:Watercourse geography:HeardIslandAndMcDonaldIslands ecology:SubtidalSandyHabitat soil:DissolvedSoilMatter geography:Luxembourg chemistry:Xenon behavior:Christian ecology.incubation:TemperateBorealForestWoodland geography:Georgia policy:PrivatelyOwned physical:East im:Yearly geology:Rock geography:Zambia geography:Honduras earth:Pedosphere landcover:ArtificialSurface geography:Dominica earth:Crust soil:CoarseGrain es.aesthetics:AestheticEnjoyment biology.nomenclature:QuercusRobur ecology:Heterotroph earth:Wet soil:LightClay ecology.incubation:GroundCover im:ModeratelyLow es.nca:UrbanIndustrialEcosystem im:Active landcover:ClosedEvergreenConiferousForest agriculture:Pistachio im:Quaternary landcover:NonIrrigatedArableLand geology:Serpentinite geography:BritishIndianOceanTerritory geography:Moldova im:High physical:Gas im:Critical policy:MedicalPurpose biology:Female landcover:FruitAndBerryPlantation geography:CongoRepublic chemistry:Darmstadtium chemistry:Sulfur physical:NorthEast physical:Material earth:Cloudy im:Belief chemistry:Indium im:Fine hydrology:HydrologicallyCorrected geography:ChristmasIsland geography:India es.aesthetics:AestheticExperienceSeeking landcover:MixedForest geography:Singapore geography:Aruba ecology.incubation:Wetland im:VeryLow landcover:ComplexCultivationPatternedLand geography:AustralianRegion geography:Malaysia landcover:InlandSwamp geography:Algeria soil:Sand geography:Paraguay geography:Vatican es.water:WaterProvider agriculture:Cucumber agriculture:Pollinated infrastructure:Gravel landcover:LichenMoss landcover.incubation:TropicalSubtropicalForest agriculture:Eggplant physical:Negative chemistry:Chromium chemistry:Aluminum ecology:SubtropicalDesert geography:Cameroon es.pollination:AgriculturalUseConnection chemistry:Zinc geography:FalklandIsland hydrology:SoilGroupD hydrology:SoilGroupC chemistry:Neptunium hydrology:SoilGroupB hydrology:SoilGroupA ecology:TropicalDesert physical:Gravitational es.nca:TropicalSubtropicalLowlandRainforest im:ModeratelyHigh policy:FamilyPurpose chemistry:Curium geography:Seychelles chemistry:Erbium physical:North behavior:Economic geography:Syria es.water:WaterProvided chemistry:Nobelium es.nca:TropicalSubtropicalForest geography:Cuba im:Large es.nca:DesertSemidesert im:Potential chemistry:Titanium es.pollination:PollinatedYield ecology:Sclerophyllous im:Perceived landcover:EvergreenConiferousForest infrastructure:AirTrafficControlCenter agriculture:Fertilizer geography:Croatia biology.nomenclature:PinusHalepensis im:Economics es.nca:SeasonallyDryTemperateHeathShrubland im:ModeratelyDamaged geology:Dacite chemistry:Polonium earth:Subtropical im:Back ecology.incubation:ShrublandShrubbyWoodland geography:SouthKorea im:Small hydrology:Likelihood100Years biology.nomenclature:Gramineae chemistry:Thulium earth.incubation:Riparian behavior:Catholic earth:Lithosphere landcover:ConiferousForest geography:Greece ecology.incubation:Overstory im:ExtremelyLow ecology.incubation:UrbanEcosystem ecology:Aphyllous chemistry:Livermorium earth:Riverine landcover:NaturalVegetation chemistry:Iron soil:SandyClay es.pollination:PollinatorAbundance chemistry:Arsenic infrastructure:Dirt soil:FineGrain earth:Thermosphere policy:Managed agriculture:Carob policy:RegionalDistrict hydrology:Seasonal geography:ArcticRegion geography:Malta geography:Tokelau ses:Regulating landcover:AnnualCroplandAssociatedWithPermanent chemistry:Tennessine infrastructure.incubation:Timber geography:Angola chemistry:Californium chemistry:Scandium geography:AlandIslands geography:Andorra soil:HeavyClay geography:Guam chemistry:Nitrogen ecology:DroughtLimited geography:Pakistan im:Low im:Down landcover.incubation:Wetland chemistry:Dubnium im:Hypothetical im:Rare im:Common geography:Mauritius im:Difficult im:Monthly biology.nomenclature:Ohc im:Tall geography:Azerbaijan geography:GuineaBissau landcover:HighDensityUrban landcover:PulpPaperPlantation earth:Mantle im:Positive im:Possible ecology.incubation:IntertidalForestShrubland earth:Boreal chemistry:Bohrium ecology:TropicalRainforest geography:Guyana biology.nomenclature:Polypodiopsida soil:SiltyClayLoam geography:Mauritania landcover:InlandMarsh soil:VeryFineSand es.nca:IntensiveLandUseSystem ecology.incubation:TemperateWoodland agriculture:Sheep chemistry:Manganese biology.nomenclature:Betula geography:Brazil landcover:MineralExtraction geography:Greenland landcover:EvergreenShrubland landcover:Vineyard chemistry:Rhodium geography:Denmark geography:Fiji geology:Anorthosite chemistry:Chlorine es.nca:HyperaridDesert es.nca:ConditionIndicator landcover:Forest im:Geology soil.incubation:CHorizon es.pollination:PollinationDependent es.nca:PolarTundraDesert geography:Samoa demography:Rich soil:SandyClayLoam ecology.incubation:Aquatic geography:Namibia agriculture:SugarBeet geography:Nicaragua im:Far agriculture:OrganicFertilizer geography:Taiwan chemistry:Ruthenium infrastructure:Asphalt chemistry:Hafnium im:EnvironmentalSocialScience earth:Exosphere landcover:OpenEvergreenBroadleafForest policy:Progressive geography:Ukraine chemistry:WaterSoluble im:Weekly im:Passive landcover.incubation:SavannaGrassland biology.nomenclature:Hemiptera biology.nomenclature:Ephemeroptera agriculture:Rye chemistry:Platinum landcover:MoorAndHeathland behavior:Buddhist ecology:TemperateSteppe agriculture:Gourd geography:NorthKorea infrastructure:RailroadBridge earth:Brackish behavior.incubation:Fishing geography:Guatemala physical:NorthWest chemistry:Hassium agriculture:Oat geography:Swaziland soil:MediumSand geography:Mali im:Retained ecology:Forest earth:Marine earth:Tropical biology.nomenclature:Calluna geography:Oceanian earth:Terrestrial chemistry:Zirconium agriculture:Bean biology.nomenclature:Carex ecology.incubation:Midstory agriculture:Cotton chemistry:Promethium landcover:WaterBody infrastructure:Intercity geography:Nauru geology:Tephra geography:SolomonIslands im:Irregular geography:Tuvalu chemistry:Selenium geology:Kimberlite landcover:Estuary im:Artifacts biology.nomenclature:Trichoptera ecology.incubation:DesertSemidesert ecology.incubation:PolarAlpine geography:CaymanIslands geography:TrinidadAndTobago physical:Red chemistry:Terbium ecology:Saline ecology:TemperateMountainSystem agriculture:PalmOil chemistry:Oganesson infrastructure:Landfill geography:Australian es.pollination:AgriculturalProduction chemistry:Cobalt geography:Bonaire chemistry:Vanadium im:Moving geography:WesternSahara im:Biology chemistry:Molybdenum policy:PrivatelyManaged biology.nomenclature:FestucaRubra earth:Hail geography:Qatar landcover.incubation:IntensiveLandUseSystem landcover:SedgeMeadow im:Regular ecology:TemperateOceanicForest geography:SaintHelena geography:PalestinianTerritory im:Finite earth:Estuarine im:Net landcover:StillWaterBody geology:HypabyssalRock geography:WallisAndFutuna chemistry:Flerovium chemistry:Barium biology.nomenclature:Senecio policy:IndividuallyOwned im:Easy infrastructure:PassengerConveyance im:Removed es.aesthetics:AestheticallyDetrimental geography:CzechRepublic im:Functional im:Damaged landcover:PeatBog chemistry:Tungsten geology:MetamorphicRock ecology:SubtidalRockAndRockyReefsHabitat es.nca:SavannaGrassland ecology:SubtidalSandyMudHabitat geography:SpratlyIslands chemistry:Carbon landcover:OpenMixedForest infrastructure:CommuterTrain behavior:Cognitive landcover:ArableLand chemistry:Colloid im:Short es.nca:ShrublandShrubbyWoodland im:AbsoluteIndicator im:Art im:Current geography:SaintMartin landcover:DeciduousConiferousForest agriculture:Orange geography:Tunisia infrastructure:Paved geography:Reunion ecology.incubation:TropicalSubtropicalSavanna conservation:Protected im:Comparable geography:Zimbabwe demography:Infant im:Mean es.water:WaterEnergyEcosystemBenefit earth:Dry landcover:ClosedMixedForest ecology.incubation:Understory geography:SaintPierreAndMiquelon ecology.incubation:TemperateSubhumidGrassland geography:Turkmenistan behavior:Shintoist landcover:GlacierAndPerpetualSnow geography:Philippines soil:Erodible im:VeryHigh geography:NorfolkIsland geography:Kazakhstan chemistry:Lanthanum economics:Appraised chemistry:Krypton ecology.incubation:OtherDesertSemidesert ecology.incubation:BorealCoolTemperatePalustrineWetland landcover:HeterogeneousAgriculturalLand chemistry:Suspension chemistry:Phosphorus ecology:SubtidalLooseRockPebbleGravelHabitat chemistry:Ytterbium landcover:Pastureland ecology:Prey agriculture:BarbaryFig es.nca:Freshwater im:Horizontal behavior:Interesting behavior:Visual ecology.incubation:TropicalSubtropicalDryForestScrub chemistry:Einsteinium biology.nomenclature:Helictotrichon soil:Silt geography:Afghanistan biology.nomenclature:Pinus geography:UnitedArabEmirates es.nca:CoastalSaltmarshReedbed geography:SintMaarten agriculture:Fig agriculture.incubation:Fertilized ecology:Fresh chemistry:Thallium geography:CongoDemocraticRepublic es.carbon:SnowpackMaintenance geography:AtlanticRegion infrastructure:Transportation chemistry:Germanium geography:CentralAfricanRepublic chemistry:Gallium chemistry.incubation:Nitrate es.nca:Aquatic geography:Jamaica es.aesthetics:AestheticallyValuable hydrology:WaterloggedSoil physical:Sonic geography:Argentina soil:TopSoil geography:Rwanda im:Sociology es.nca:SeasonallyDryTropicalShrubland landcover.incubation:PolarAlpine landcover:OpenDeciduousConiferousForest geography:PitcairnIslands geography:Nigeria agriculture:Melon im:Previous behavior:Social biology.nomenclature:Phragmites chemistry:Technetium geography:Jersey chemistry:Fluorine im:Gross chemistry:Potassium landcover:ArtificialVegetatedArea geography:Gambia landcover:SeminaturalOpenSpace landcover:BareArea physical:Nuclear im:Ephemeral ecology:HerbaceousVegetation im:Absolute soil:Loam ecology:TemperateDesert es.nca:BrackishTidal chemistry:Neodymium soil:BHorizon agriculture:CashCrop earth:WaterFlowDirection geography:Bermuda geography:FaroeIslands chemistry:Basic landcover:AgriculturalVegetation ecology.incubation:Benthic soil.incubation:EHorizon es.nca:TemperateWoodland landcover:Airport soil:SiltyClay agriculture:Cattle im:Front behavior:Religious geography:Japan im:Coarse ecology:Polar im:Tertiary geography:Morocco im:Daily geography:Slovenia earth:Air chemistry:Cesium earth:Mesosphere geography:Australia soil:RHorizon landcover:Grassland behavior:BenefitCriterion agriculture:SugarCane geography:Niger landcover:MineDumpConstruction landcover:DeciduousBroadleafForest infrastructure:Concrete chemistry:Radium geography:Guinea es.pollination:AgriculturalExploitation soil:FineSand landcover:SparseTreeCover im:Pervious chemistry:Meitnerium geography:Thailand chemistry:Rubidium agriculture:Manure biology.nomenclature:Castanea geography:Benin im:SlightlyDamaged geology:IgneousRock geography:SouthAmerican landcover:AgroForestryLand geography:NorthAmericanRegion im:Destroyed ecology:SubtropicalHumidForest ecology.incubation:PolarAlpineTundra agriculture:WinterSquash landcover:EvergreenBroadleafForest landcover:ScrubHerbaceousVegetation chemistry:Plutonium soil:OHorizon behavior:CostCriterion geography:Kuwait geography:Tajikistan ecology.incubation:TemperateForest chemistry:Cadmium geography:BouvetIsland geography:HongKong agriculture:Cucurbitaceae geography:ElSalvador infrastructure:RailroadStation es.aesthetics:AestheticsEnjoyed es.aesthetics:AestheticEnjoymentBeneficiary agriculture:Apricot es.nca:TropicalSubtropicalMontaneRainforest im:Predicted geography:PapuaNewGuinea ses:Rival chemistry:Neon im:Engineering geography:PacificRegion ecology.incubation:CoolTemperateHeathland geography:Mozambique es.water:WaterConsumingBeneficiary geography:TurksAndCaicosIslands agriculture:Avocado earth:Geolocated geography:Bhutan landcover:CoastalLagoon chemistry:Osmium biology.nomenclature:Carpinus earth:SaltMarsh im:Realized hydrology:Sewage earth:Aquatic landcover:IndustrialCommercialTransport landcover:SparseVegetation geography:Antarctic landcover.incubation:DesertSemidesert biology.nomenclature:Fagus behavior:Indoor chemistry:Radon soil:SandyLoam geography:SaintVincentAndGrenadines ecology:BorealMountainSystem es.pollination:PollinatorThroughput geography:Ethiopia chemistry:Protactinium ecology:SeagrassHabitat im:Agriculture agriculture:Rapeseed demography:Toddler im:Hydrology landcover:RiceField geography:Libya ecology:BorealTundraWoodland geology:Peridotite ecology:EstuarineHabitat geography:Tonga geology:Lava im:Infinite geography:IndianRegion chemistry:Iridium chemistry:AlkalinityLevel landcover:Mangrove es.pollination:AgriculturalProductionDependent policy:Ward es.pollination:PollinationSupplyConnection behavior:Alternative demography:Poor behavior:Cultural chemistry:Inorganic biology.nomenclature:Ept chemistry:Lutetium geography:Barbados im:Primary geology:Andesite ecology.incubation:CoolDesertSemidesert chemistry:Lithium es.nca:TropicalSubtropicalSavanna landcover:InlandWetland ecology:SubtropicalMountainSystem geography:Ireland im:Partial agriculture:Plum geography:Botswana im:Outgoing ecology:TemperateContinentalForest chemistry:Oxygen geography:Bahamas im:Natural landcover:OilPalmPlantation geography:SierraLeone biology.nomenclature:Populus chemistry:Copernicium landcover:LandSurface agriculture:Fowl chemistry:Antimony im:Observed landcover:ClosedDeciduousBroadleafForest es.nca:SubterraneanEcosystem biology:Male hydrology:EventLikelihood infrastructure:FreightConveyance ecology.incubation:SavannaGrassland earth:AtmosphericBottomLayer agriculture:Alfalfa landcover:BeachDuneAndSand soil.incubation:RHorizon geography:Iceland soil.incubation:BHorizon chemistry:Alkaline chemistry:Magnesium biology.nomenclature:Mollusca policy:PubliclyOwned im:Appropriate im:Minimum biology.nomenclature:Coleoptera geography:NewZealand geography:Anguilla geography:Haiti geography:Maldives demography:Human geography:Poland ecology.incubation:WarmTemperateTropicalMarsh agriculture:Cocoa chemistry:Europium geography:EasternHemisphere soil.incubation:GroundWaterLayer infrastructure:Unpaved im:Policy geography:FrenchSouthernTerritories hydrology:Permanent policy:Moderate es.nca:AlpineGrasslandShrubland geography:Kyrgyzstan agriculture:Tangerine geography:Estonia ecology:TropicalMoistDeciduousForest geography:AfricanRegion landcover:OliveGrove chemistry:Hydrogen agriculture:Peanut geography:Uzbekistan landcover:ClosedSavanna physical:Plasma im:NoAttribute chemistry:Fermium soil:MediumCoarseGrain geography:AntarcticRegion es.nca:CoolDesertSemidesert ecology.incubation:TropicalSubtropicalLowlandRainforest geography:Serbia ecology:Needleleaved ecology.incubation:PolarAlpineCliffScreeOutcropLavaFlow soil:EHorizon geography:Djibouti es.aesthetics:Viewpoint im:Changed geography:Ghana geography:Liechtenstein im:Ecology soil:Soil geography:UnitedStatesVirginIslands im:Local landcover:BurnedLand geography:Montenegro chemistry:Argon biology.nomenclature:Eucalyptus geography:CocosIslands geography:EuropeanRegion biology:Reproductive geography:NewCaledonia landcover:DeciduousShrubland landcover:BareRock geography:CookIslands geography:Indonesia ecology:MidSuccession geography:Armenia geography:ParacelIslands geography:Colombia agriculture:Hazelnut geography:AsianRegion im:Hourly agriculture:Tomato geography:European ecology:SubtropicalSteppe chemistry:Niobium landcover:ClosedEvergreenBroadleafForest geography:EquatorialGuinea agriculture.incubation:Mares ecology.incubation:TropicalFloodedForestPeatForest geography:Ecuador landcover:Wetland agriculture:Honeybee im:Indicator agriculture.incubation:Irrigated biology.nomenclature:Insecta geography:Monaco chemistry:Beryllium chemistry.incubation:Phosphate agriculture:Bubalus demography:Immigrant geography:BurkinaFaso landcover:Shrubland ecology:NoSuccession geography:PuertoRico physical:Mechanical im:Pedology behavior:Sport chemistry:Boron landcover:SaltMarsh ecology:ForbVegetation physical:Electrical earth:Temperate geography:CapeVerde policy:County es.nca:Marine ecology.incubation:IceSheetGlacierPermanentSnowfield geography:Somalia chemistry:Seaborgium geography:NorthAmerican im:Nature policy:MetropolitanArea agriculture:Coffee agriculture:Cassava geography:Liberia biology.nomenclature:Fraxinus physical:SouthEast geology:Obsidian biology:Plants es.nca:RockyPavementLavaflowScree ecology.incubation:RadiationLimited behavior:Tourist biology.nomenclature:PinusRadiata geography:SouthAfrica es.nca:BorealTemperateMontaneForestWoodland chemistry:Gold infrastructure:Cesspool im:Geography hydrology:Likelihood500Years policy:CollectivelyManaged biology.nomenclature:Quercus geography:Macao physical:Energetic es.water:WaterDeliveryConnection physical:Potential landcover:TransitionalWoodlandScrub demography:Adult geography:Germany im:Animated chemistry:Nihonium landcover:DwarfShrubland chemistry:Dysprosium geography:WesternHemisphere es.nca:Cropland im:Pristine biology.nomenclature:Plecoptera ecology:MossVegetation im:Theoretical geography:SouthernHemisphere geography:Antarctica earth:Lacustrine geography:China geography:Vietnam policy:PoliticalPurpose chemistry:Berkelium geography:SaudiArabia landcover:OpenSavanna landcover:GreenUrbanArea es.nca:TemperateForest im:Reference demography:Foreigner geography:Togo es.nca:IntertidalForestShrubland im:Standardized geography:Gibraltar policy:Legal infrastructure:RailroadTrack geography:Grenada es.carbon:StormReduction agriculture:Goat ecology:TropicalDryForest es.pollination:PollinatorSupplier im:RelativeIndicator chemistry:Tantalum chemistry:Actinium im:Domain chemistry:Tin behavior:Relevant hydrology:Tidal policy:CollectivelyOwned im:Still geography:Spain im:Endogenous behavior:Experiential geography:Israel behavior:Muslim im:Whole geology:PillowLava behavior:Recreational behavior:Focal agriculture:Strawberry policy:IndividuallyControlled physical:Elastic geography:Niue geography:Bulgaria landcover:AgriculturalLandWithNaturalVegetation geography:Yemen geography:Chile physical:Positive behavior:Baptist biology.nomenclature:Cyprinidae earth:Rain chemistry:Uranium geography:SaintKittsAndNevis ecology:Broadleaved agriculture:Zucchini landcover:Urban agriculture:OilSeed agriculture:Syncerus behavior:Stakeholder im:Negative earth:Troposphere landcover:MediumDensityUrban demography:Autochtonous ecology:PoleSuccession earth:Stratosphere chemistry:Moscovium chemistry:Nickel behavior:ActivityContext es.aesthetics:AestheticEnjoymentLocation ecology:SubtidalMuddyHabitat soil:LoamySand ecology:TreeVegetation geography:SanMarino geography:Senegal im:Maximal geography:Turkey es.nca:PalustrineWetland agriculture:Chicken geography:BritishVirginIslands im:Secondary geography:Sudan soil:VeryCoarseSand geography:Cyprus es.nca:IceSheetGlacierPermanentSnowfield agriculture:Compost ecology.incubation:TropicalSubtropicalMontaneRainforest es.nca:TemperateSubhumidGrassland es.water:WaterConsumed ses:Demanded geography:Palau geography:TimorLeste geography:Asian ecology.incubation:NitrogenLimited biology.nomenclature:Pseudotsuga geography:Gabon im:Latter geography:Sweden chemistry:Tellurium geography:Bahrain im:Vertical geography:Mexico geography:SouthSudan chemistry:Americium landcover:OpenDeciduousBroadleafForest chemistry:Bismuth infrastructure:RailroadTunnel physical:West agriculture:Tobacco geography:Madagascar geography:Burundi es.water:WaterConsumingTransactor im:Added behavior:Jewish geography:Eritrea im:Near im:Exogenous agriculture:Sorghum geography:CoteDIvoire chemistry:Solution chemistry:Lead soil:ClayLoam soil:CoarseSand es.nca:OtherDesertSemidesert ecology:OldGrowth geography:Bolivia geography:UnitedStatesMinorOutlyingIslands biology.nomenclature:Abies geography:Romania ecology:LichenVegetation landcover:SportLeisureFacility es.taxonomy:Mangrove es.nca:EpisodicAridFloodplain ecology:Natural agriculture:Pumpkin ecology:TropicalMountainSystem agriculture:Turnip geography:African chemistry:Calcium geology:SedimentaryRock soil:SiltLoam policy:SocialWelfarePurpose ecology:Predator geography:Portugal es.carbon:SeaLevelStability agriculture:Tuber geography:Egypt biology.nomenclature:Alnus landcover:RoadRailNetwork chemistry:Sodium agriculture:InorganicFertilizer es.water:WaterProvision ecology.incubation:HyperaridDesert im:Successive demography:Elderly geology:Rhyolite im:Actual policy:EconomicPurpose im:Certain ecology:Evergreen es.nca:PolarAlpine im:Former es.aesthetics:AestheticValuable geography:Belarus chemistry:Gadolinium chemistry:Iodine chemistry:Silicon biology.nomenclature:UlexGallii im:Left landcover:OpenEvergreenConiferousForest agriculture:Peach geography:NetherlandsAntilles geography:Norway es.aesthetics:VisualConnection geography:Jordan geography:SaintBarthelemy es.nca:TemperateBorealForestWoodland chemistry:Neutral earth:Limnetic geography:Nepal geography:Uganda geography:Martinique geography:NorthernHemisphere geography:Venezuela geography:AmericanSamoa landcover:NonIrrigatedArableLandHerbaceous geography:Switzerland physical:Kinetic chemistry:Praseodymium agriculture:Meadow geography:AntiguaAndBarbuda physical:Green physical:Solid infrastructure:RoadSurface ecology.incubation:SubtropicalWarmTemperateForestedWetland geography:Oman geography:UnitedKingdom chemistry:Bromine agriculture:Citrus physical:Informational chemistry:Copper agriculture:LeafAndStem physical:Thermal ecology:TropicalShrubland ecology:SubtropicalDryForest es.nca:TropicalSubtropicalDryForestThicket biology.nomenclature:Larix im:Impossible geography:Panama chemistry:Strontium geography:France earth:Atmosphere agriculture:Barley geography:Myanmar im:Chemistry geography:Netherlands geography:Cambodia geography:Vanuatu ecology:MarineHabitat behavior:Outdoor geography:SouthGeorgiaAndSouthSandwichIslands geography:Uruguay im:Relevant geography:Mongolia soil:CHorizon agriculture:Apple geography:Guernsey agriculture:Legume im:Constant soil.incubation:Soil earth:Snowy geography:SriLanka landcover:VegetatedStillWaterBody geography:Finland im:Minimal infrastructure:RailroadYard landcover:Port chemistry:Rutherfordium es.nca:ConditionMetric ecology:ColdLimited es.water:WaterFlowed im:Relative geography:Belgium landcover:SparseHerbaceousCover policy:PubliclyManaged geography:SantaLucia ecology.incubation:SeasonallyDryTropicalShrubland landcover:MaritimeWetland ecology.incubation:Cropland landcover:PermanentCropland agriculture:Fallow';
    var EVENTS = 'earth:Fall biology:EmbryoStage earth.incubation:Snowfall ecology:Nesting behavior:Burglary earth:RainOnSnow biology:InfantStage im:March im:July im:September hydrology:Drought biology:FetusStage earth:Earthquake im:Millennium earth:Winter im:August biology:Life earth:Lightning earth:Summer im:Collapse earth.incubation:Rainfall im:May biology:Birth im:December earth:Flood im:Minute chemistry:Reaction im:October im:April hydrology.incubation:HighStreamFlowEvent im:Year biology:Adulthood im:June behavior:Coup earth:Storm chemistry:Fire biology:PreAdolescence im:Month earth:Spring im:November biology:Adolescence im:Second behavior:Election ecology:Flowering im:Hour im:Season ecology:GrowingSeason biology:ElderlyStage behavior:MassMurder im:January chemistry:Explosion im:February earth:Landslide behavior:Homicide im:Day biology:JuvenileStage behavior.incubation:Photograph im:Decade earth.incubation:Precipitation biology:Childhood im:Century earth.incubation:Hailing biology:Death hydrology:DayWithPrecipitation';
    var ABSTRACT_QUALITIES = 'im:QualityRanking im:Depth im:Width im:Resilience hydrology.incubation:Moisture im:Preference im:Length im:Capacity im:Quantity ecology:Diversity im:Balance im:Energy im:Priority im:Proportion hydrology:VerticalWaterFlowVelocity hydrology:GroundWaterVolume im:Amount im:Type im:Velocity im:Diversity im:Frequency im:Age im:MonetaryValue im:Concordance hydrology:WeatherMediatedDepositionWaterVolume im:Numerosity chemistry:ChemicalEnergy chemistry:Salinity hydrology:SurfaceWaterVolume im:Acceleration demography:IncomeInequality demography:Poverty hydrology:HorizontalWaterFlowVelocity im:Height im:Heterogeneity soil.incubation:WiltingPoint im:Temperature im:Mass im:Security im:Complexity economics:Wealth im:Duration im:Volume im:Direction im:Area im:Angle im:SpatialCoverage im:Density im:Weight';
    var ABSTRACT_SUBJECTS = 'infrastructure:TransportationInfrastructure infrastructure:AdministrativeTownship ecology:EcologicalGroup infrastructure:BuiltArtifact infrastructure:ManufacturedProduct ecology.incubation:SubterraneanPlantPart physical:AssertedBody biology:PlantPart ecology.incubation:PlantPart physical:Body infrastructure:GeolocatedInfrastructure policy:Government infrastructure:CommercialInfrastructure infrastructure:WaterRegulationInfrastructure im:Subject infrastructure:HousingInfrastructure ecology.incubation:AerialPlantPart physical:Entity physical:Group physical:DelimitedBody im:Agent infrastructure:MonitoringInfrastructure physical:Cavity im:Boundary hydrology:HydrologicallyRelevantRegion infrastructure:CoastalProtectionInfrastructure engineering:Machine physical:Observable';
    var ABSTRACT_PROCESSES = 'behavior:Assessment earth:MeteorologicalProcess hydrology:HydrologicalProcess hydrology:WaterOutput ecology.incubation:Resorption physical:PhaseTransition ecology.incubation:Decomposition physical:InformationTransport physical:EnergyTransport behavior:Activity earth:LandformingProcess ecology.incubation:Senescence behavior:Choice im:SystemTransformation agriculture:AgriculturalActivity chemistry.incubation:NitrogenCycle im:Transport im:TransitiveProcess im:IntransitiveProcess physical.incubation:PhaseTransition agriculture:Farming behavior:DecisionProcess ecology.incubation:Litterfall hydrology:WaterInput physical:MatterTransport im:Process im:SpatialIntransitiveProcess agriculture:AgriculturalProcess im:SpatialTransitiveProcess';
    var ABSTRACT_RELATIONSHIPS = 'im:InformationTransferConnection im:Relationship ecology:InterspecificInteraction im:EnergyTransferConnection ecology:DirectedInteraction demography:HumanBond im:MatterTransferConnection behavior:Interest behavior:IntentionalConnection ecology:AbioticInteraction behavior:Intention im:TransferConnection ecology:MaterialFlow ecology:InformationInteraction';
    var ABSTRACT_PREDICATES = 'im:DataReinterpretation ecology:Salinity biology.nomenclature:VegetationGenus geography:Country agriculture:Crop chemistry:Compound ecology.incubation:WoodProductivity policy:InstitutionalPurpose biology:Species ecology:LifeForm ecology:LeafType infrastructure:RailRightsofWay ecology:TrophicRole biology.nomenclature:VegetationFamily biology:Juvenile soil:ReferenceSoilGroup biology.incubation:Living im:Identity demography:AgeGroup im:Perviousness soil:SoilOrder biology:Taxonomy im:EuclidianRelativeOrientation2D im:Severity biology:Infraspecies earth:LatitudinalRegion biology.incubation:IngestedFood physical:Color policy:PoliticalViewpoint im:Knowledge im:Origin soil:SoilMatter physical:MagneticPlanetaryOrientation physical:PhysicalIdentity geography:RegionalIdentity behavior:Criterion es.nca:Ecosystem ses:Flow chemistry:ChemicalElement im:ObserverRelativeOrientation im:Coarseness es.nca:EcologicallyDependent landcover:Vegetation biology:Fry biology:Adult geography:LatitudinalHemisphere agriculture:Fodder im:Participation geology:ExtrusiveRock infrastructure:Aviation demography:Affluence earth:PhysicalEnvironment landcover.incubation:Ecosystem es.nca:ForestEcosystem im:Finiteness im:AccomplishmentRelated im:TemporalOrientation im:TemporalConsequentiality im:DynamicStateRelated im:Lineal chemistry:ChemicalSpecies im:FrequencyOfOccurrence im:Norms infrastructure:RailroadInfrastructure im:RelationalAttribute biology:TaxonomicIdentity im:Regularity im:RankingCriterion agriculture:Fruit ecology:EcoFloristicRegion behavior:Relevance chemistry:Mixture soil:Granularity im:EvidenceOfTruth biology:Sex im:Explanations im:DamageLevel ses:Sink es.nca:EcologicallyDependentGood behavior:FruitionMode infrastructure:Industry biology:Kingdom geography:GeographicalIdentity ses:ProductionRelated ses:UseFlow im:Unable biology.nomenclature:VegetationClass im:MeasurementRelated ecology:VegetationLimitingFactor earth:WeatherDominance agriculture:AgriculturallyUsefulInsect im:MovementRelated im:SpatialOrientation ses:Supply im:DataReduction biology.nomenclature:MacroInvertebrate geography:Hemisphere soil:SoilTexture geography:Continental earth:PrecipitationType im:Volumetric ses:Effector ecology:SuccessionalStage biology:Phylum economics:AppraisalRelated soil:SoilTaxon earth:Georeferenced im:Appropriateness ses:Provision policy:District biology.nomenclature:TreeSpecies ses:ValueMaintenance ses:RegulatingEcosystemBenefit im:UnboundedRankingCriterion ecology.incubation:Bocage es.water:WaterProvisionConnection im:VolumeLevel agriculture:AgriculturalDestination ecology:HeightClassifiedWoodyVegetation ecology.incubation:VegetationLimitingFactor landcover:LandCover hydrology.incubation:Intercepted earth:Freshwater im:Level policy:Ownership behavior:Faith agriculture:Livestock ses:ValueExchange biology:Cultivar im:Ability biology:Class im:ConnectionDirection biology.nomenclature:VegetationSpecies policy:GeoPoliticalSubdivision ses:BeneficialMechanism behavior:ActivityType ecology:LeafPhenology im:SystemDescription im:Outcome chemistry.incubation:Ice hydrology:HydrologicSoilGroup es.carbon:CarbonSequestrationBenefit ecology.incubation:Litter physical:Phase chemistry:ChemicalNature im:Culture im:Relativity im:Puntal earth:GeosphereStratum ecology.incubation:VegetationStratum ecology.incubation:Ecosystem im:IntendedPurpose geography:ContinentalRegion biology:Genus es.water:WaterService im:VerticalHeightLevel earth:AtmosphericLayer biology.nomenclature:TreeGenus ses:SocialUptake ses:FlowRole biology.nomenclature:FishFamily ses:Provider agriculture.incubation:BodyCondition geography:GeopoliticalIdentity im:Realm physical:Polarity biology:CommonsenseTaxonomy infrastructure:WasteDisposalPurpose ses:Transactor biology:Taxon im:Technology ecology:WoodyVegetation im:Areal im:Able demography:NationalOrigin im:RelativeDistance im:Attribute im:RankOrder ses:Demand policy:Management ses:ProvisioningEcosystemBenefit im:Closeness im:Orientation ses:Beneficiary im:OccurencePattern geography:LongitudinalHemisphere im:HorizontalLenghtLevel ecology:Habitat im:Throughput ses:ProvisionFlow ses:EcosystemBenefitFlow soil:SoilStratum biology:Nutrient ses:ValueExchanged ses:ValueTransferred es.nca:EcologicallyDependentCropType im:Wholeness infrastructure:RailroadConveyance es.nca:EcologicallyDependentService soil.incubation:SoilStratum ses:ValueProvided ses:ValueUsed im:Physical soil:Clay im:InterdisciplinarySciences im:HeightLevel ses:SocioEcologicalExchange biology:Order biology:Family im:Difficulty im:BoundedRankingCriterion';
    var ABSTRACT_EVENTS = 'im:PeriodExpiration earth:MeteorologicalEvent behavior:SociallyRelevantEvent behavior:CriminalEvent earth:Season earth:GeologicalEvent policy:Action im:SystemEvent behavior:Decision biology:LifeEvent im:Occurrence im:Event earth:GeolocatedEvent';
    var CONFIGURATIONS = "demography:SocialNetwork earth:EarthCover es.aesthetics:Aesthetics ecology:WaterRetentionStructure earth:Bathymetry earth:LandCover earth:StreamNetwork physical:Gravity es:GreenInfrastructure ecology:Biodiversity earth:Terrain ecology:Landscape soil:Horizon earth:Gravity ecology:VegetationStructure";
    var ABSTRACT_CONFIGURATIONS = "physical:FlowNetwork im:Configuration im:MeasuredConfiguration im:Network im:PerceivedConfiguration im:InferredConfiguration physical:PhysicalConfiguration physical:EnergyNetwork physical:InformationNetwork im:SpatialNetwork";

   var ANNOTATION = {
      className: 'meta',
      begin: '@' + JAVA_IDENT_RE,
      contains: [
        {
          begin: /\(/,
          end: /\)/,
          contains: ["self"] // allow nested () inside our annotation
        },
      ]
    };
    const NUMBER = NUMERIC;

    return {
      name: 'Kim',
      aliases: ['kim'],
      keywords: {
  		  // admits ns.subns:Concept notation
  		  $pattern: /\b[a-z\.]+(:[A-Z][A-z]+)?\b/,
  		  keyword: KEYWORDS,
            quality: QUALITIES,
  		  predicate: PREDICATES,
  		  subject: SUBJECTS,
  		  process: PROCESSES,
            event: EVENTS,
  		  relationship: RELATIONSHIPS,
            configuration: CONFIGURATIONS,
  		  aquality: ABSTRACT_QUALITIES,
  		  apredicate: ABSTRACT_PREDICATES,
  		  asubject: ABSTRACT_SUBJECTS,
  		  aprocess: ABSTRACT_PROCESSES,
  		  arelationship: ABSTRACT_RELATIONSHIPS,
  		  aevent: ABSTRACT_EVENTS,
            aconfiguration: ABSTRACT_CONFIGURATIONS
  	  },
      illegal: /<\/|#/,
      contains: [
        hljs.COMMENT(
          '/\\*\\*',
          '\\*/',
          {
            relevance: 0,
            contains: [
              {
                // eat up @'s in emails to prevent them to be recognized as doctags
                begin: /\w+@/, relevance: 0
              },
              {
                className: 'doctag',
                begin: '@[A-Za-z]+'
              }
            ]
          }
        ),
        // // relevance boost
        // {
        //   begin: /namespace/,
        //   keywords: "namespace",
        //   relevance: 2
        // },
        hljs.C_LINE_COMMENT_MODE,
        hljs.C_BLOCK_COMMENT_MODE,
        hljs.APOS_STRING_MODE,
        hljs.QUOTE_STRING_MODE,
        // {
        //   className: 'class',
        //   beginKeywords: 'class interface enum', end: /[{;=]/, excludeEnd: true,
        //   relevance: 1,
        //   keywords: 'class interface enum',
        //   illegal: /[:"\[\]]/,
        //   contains: [
        //     { beginKeywords: 'extends implements' },
        //     hljs.UNDERSCORE_TITLE_MODE
        //   ]
        // },
        // try to catch klab namespace
        {
          className: 'namespace',
          beginKeywords: 'namespace', 
          end: /[{;=]/, 
          excludeEnd: true,
          relevance: 1,
          keywords: 'namespace',
          illegal: /[:"\[\]]/,
          contains: [
            { beginKeywords: 'using' },
            hljs.UNDERSCORE_TITLE_MODE
          ]
        },
        {
          // Expression keywords prevent 'keyword Name(...)' from being
          // recognized as a function definition
          beginKeywords: 'new throw return else',
          relevance: 0
        },
        // {
        //   className: 'class',
        //   begin: 'record\\s+' + hljs.UNDERSCORE_IDENT_RE + '\\s*\\(',
        //   returnBegin: true,
        //   excludeEnd: true,
        //   end: /[{;=]/,
        //   keywords: KEYWORDS,
        //   contains: [
        //     { beginKeywords: "record" },
        //     {
        //       begin: hljs.UNDERSCORE_IDENT_RE + '\\s*\\(',
        //       returnBegin: true,
        //       relevance: 0,
        //       contains: [hljs.UNDERSCORE_TITLE_MODE]
        //     },
        //     {
        //       className: 'params',
        //       begin: /\(/, end: /\)/,
        //       keywords: KEYWORDS,
        //       relevance: 0,
        //       contains: [
        //         hljs.C_BLOCK_COMMENT_MODE
        //       ]
        //     },
        //     hljs.C_LINE_COMMENT_MODE,
        //     hljs.C_BLOCK_COMMENT_MODE
        //   ]
        // },
        // {
        //   className: 'function',
        //   begin: '(' + GENERIC_IDENT_RE + '\\s+)+' + hljs.UNDERSCORE_IDENT_RE + '\\s*\\(', returnBegin: true, end: /[{;=]/,
        //   excludeEnd: true,
        //   keywords: KEYWORDS,
        //   contains: [
        //     {
        //       begin: hljs.UNDERSCORE_IDENT_RE + '\\s*\\(', returnBegin: true,
        //       relevance: 0,
        //       contains: [hljs.UNDERSCORE_TITLE_MODE]
        //     },
        //     {
        //       className: 'params',
        //       begin: /\(/, end: /\)/,
        //       keywords: KEYWORDS,
        //       relevance: 0,
        //       contains: [
        //         ANNOTATION,
        //         hljs.APOS_STRING_MODE,
        //         hljs.QUOTE_STRING_MODE,
        //         NUMBER,
        //         hljs.C_BLOCK_COMMENT_MODE
        //       ]
        //     },
        //     hljs.C_LINE_COMMENT_MODE,
        //     hljs.C_BLOCK_COMMENT_MODE
        //   ]
        // },
        NUMBER,
        ANNOTATION
      ]
    };
  }

  return kim;

  return module.exports.definer || module.exports;

}());

hljs.registerLanguage('kactors', function () {
  'use strict';

  // https://docs.oracle.com/javase/specs/jls/se15/html/jls-3.html#jls-3.10
  var decimalDigits = '[0-9](_*[0-9])*';
  var frac = `\\.(${decimalDigits})`;
  var hexDigits = '[0-9a-fA-F](_*[0-9a-fA-F])*';
  var NUMERIC = {
    className: 'number',
    variants: [
      // DecimalFloatingPointLiteral
      // including ExponentPart
      { begin: `(\\b(${decimalDigits})((${frac})|\\.)?|(${frac}))` +
        `[eE][+-]?(${decimalDigits})[fFdD]?\\b` },
      // excluding ExponentPart
      { begin: `\\b(${decimalDigits})((${frac})[fFdD]?\\b|\\.([fFdD]\\b)?)` },
      { begin: `(${frac})[fFdD]?\\b` },
      { begin: `\\b(${decimalDigits})[fFdD]\\b` },

      // HexadecimalFloatingPointLiteral
      { begin: `\\b0[xX]((${hexDigits})\\.?|(${hexDigits})?\\.(${hexDigits}))` +
        `[pP][+-]?(${decimalDigits})[fFdD]?\\b` },

      // DecimalIntegerLiteral
      { begin: '\\b(0|[1-9](_*[0-9])*)[lL]?\\b' },

      // HexIntegerLiteral
      { begin: `\\b0[xX](${hexDigits})[lL]?\\b` },

      // OctalIntegerLiteral
      { begin: '\\b0(_*[0-7])*[lL]?\\b' },

      // BinaryIntegerLiteral
      { begin: '\\b0[bB][01](_*[01])*[lL]?\\b' },
    ],
    relevance: 0
  };

  /*
  * Support for k.Actors. Lots to do.
  */

  function kactors(hljs) {
    var JAVA_IDENT_RE = '[\u00C0-\u02B8a-zA-Z_$][\u00C0-\u02B8a-zA-Z_$0-9]*';

    var KEYWORDS = 'exception named during type required without identified permissions else action exclusive per presence if app inclusive in containing linking probability optional version script versionstring each as style summed minus proportion occurrence uncertainty unknown library web identity by where behavior set within caused change testcase component observable behaviour to ratio do while down empty assessment percentage trait logo modified create from observability new level author created count follows plus task true changed no import distance for description worldview locale total times not desktop causing public rate and of averaged magnitude value over monetary or mobile false adjacent with contained user';
    var QUALITIES = 'hydrology:PotentialEvapotranspiredWaterVolume ecology:VascularPlantBiomass soil.incubation:SoilDepth soil.incubation:Fertility ecology.incubation:NormalizedDifferenceMoistureIndex biology:Age hydrology.incubation:StreamWidth ecology:AlgalBiomass im:Sustainability ecology:HardwoodBiomass conservation:ConservationStatus hydrology:RunoffWaterVolume hydrology:FogInterceptionVolume hydrology.incubation:CropCoefficient geography:Elevation soil:SlopeStability ecology:Biomass geography:Heading hydrology:RunoffVelocity soil:SoilErodibility im:FunctionalConservationStatus earth:SolarRadiation agriculture:Yield soil.incubation:Roughness demography:SocialAcceptance geography:StreamGradient im:ConservationStatus hydrology:InfiltratedWaterVolume chemistry:Enthalpy ecology:SoftwoodBiomass soil.incubation:SoilFertility ecology:ForestFragmentation chemistry:ThermochemicalEnergy im:Impedance im:Risk im:Suitability soil:SlopeSteepnessAndLength es.aesthetics:AestheticValueEnjoyed hydrology:ContributingArea es.nca:Condition ecology.incubation:NormalizedBurnRatio hydrology:StreamOrder economics:EconomicProduct soil.incubation:PermanentWiltingPoint landcover:LandscapeHeterogeneity physical.incubation:Fragmentation chemistry:Ph economics:Income landcover:LandCoverType hydrology:Storativity hydrology:AvailableWaterCapacity economics:NationalProduct hydrology:SnowMeltVolume hydrology:Seepage ecology:BelowGroundBiomass ecology:LeafAreaIndex geography:LidarElevation hydrology:DrainageDensity hydrology:WaterVolume chemistry:Reactivity ecology:TerrestrialBiomass hydrology:CurveNumber earth:PrecipitationVolume chemistry:ExchangeCapacity soil.incubation:UltimateWiltingPoint soil.incubation:DroughtSeverityIndex ecology.incubation:FractionPhotosyntheticallyActiveRadiation soil:SupportPractice hydrology:ImperviousCover geography:SlopeLength earth:HailVolume soil:SaturatedHydraulicConductivity im:VisualConservationStatus hydrology.incubation:StreamFlow hydrology:EvaporatedWaterVolume ecology:PlantBiomass ecology:PrimaryProductivity im:StructuralConservationStatus es.nca:EcosystemType soil:CoverManagement hydrology.incubation:RefreezingWaterVolume chemistry:IonizationEnergy economics:DomesticProduct im:Change earth:SnowfallVolume hydrology:FlowDirection geography:Slope ecology.incubation:EcosystemType earth:AtmosphericTemperature soil:DroughtSeverity ecology:PrimaryProduction biology:Biomass ecology:NetPrimaryProduction ecology:LandscapeRichness earth.incubation:PhotosyntheticallyActiveRadiation hydrology:FloodWaterVolume chemistry:Purity ecology:WoodBiomass ecology:TreeCanopyHeight ecology:AboveGroundBiomass geography:Aspect im:Conductance hydrology:LocallyExchangedWaterVolume earth:RainfallVolume es.aesthetics:AestheticValue soil:RainfallRunoffErosivity geography:BathymetricDepth hydrology:EvapotranspiredWaterVolume geography:BathymetricSlope ecology:Fragmentation ecology:NormalizedDifferenceVegetationIndex hydrology.incubation:FieldCapacity ecology:RootBiomass ecology:AquaticBiomass ecology.incubation:EnhancedVegetationIndex ecology:CanopyCover hydrology:BaseFlowWaterVolume chemistry:BindingEnergy ecology:SpeciesRichness earth:TopographicHeterogeneity ecology.incubation:LeafAreaIndex physical:Fragmentation soil:SedimentSource ecology:NormalizedDifferenceWaterIndex';
    var SUBJECTS = 'infrastructure:Seawall earth:Footslope ecology.incubation:Seeds infrastructure:Campsite infrastructure:LimitedAccessRoad infrastructure:House infrastructure:CaravanSite earth:Dune infrastructure:MiningInfrastructure im:VariousThings infrastructure:SecondaryNationalPort earth:MountainPeak infrastructure:Cabin earth:Wetland earth:Shoulder demography:Household infrastructure:OilGasWell hydrology:PerennialFlow engineering:Car infrastructure:Ramp infrastructure:InterstateHighway infrastructure:PrimaryNationalPort demography:SocialIndividual infrastructure:BikePath infrastructure:Road infrastructure:TransmissionLine earth:Ocean infrastructure:ActiveOilGasWell infrastructure:PicnicArea infrastructure:Anchorage chemistry:Ion infrastructure:Motel infrastructure:Groin infrastructure:Path infrastructure:Ditch hydrology:SurfaceFlowFrequency infrastructure:Dam ecology.incubation:Branches infrastructure:PowerPlant infrastructure:Restaurant ecology:Community ecology:Population infrastructure:WindTurbine infrastructure:WeatherStation hydrology:RiverBasin hydrology:IntermittentFlow infrastructure:BoatLaunch earth:Spur ecology.incubation:Flower biology:Flower earth:Sea earth:Peak infrastructure:FerryRoute earth:Cliff infrastructure:Chalet earth:Location hydrology:Watershed demography:HumanPopulation infrastructure:SamplingSite infrastructure:CommunicationTower earth:GeoFormation ecology:Shrub infrastructure:Levee infrastructure:Mine hydrology:Floodplain infrastructure:Trail earth:Mountain infrastructure:Aqueduct infrastructure:Railway infrastructure:DetentionBasin im:MixedAnything infrastructure:SecondaryInternationalPort infrastructure:Village ecology.incubation:Foliage earth:River earth:Waterway earth:Hollow infrastructure:City earth:LandFormation chemistry:Cation infrastructure:StateHighway earth:Region biology:Leaf earth:Stream earth:Flat infrastructure:Hostel infrastructure:Hotel infrastructure:UrbanPark engineering:Vehicle earth:Slope biology:Insect hydrology:MountainfrontRechargeZone infrastructure:WastewaterTreatmentPlant infrastructure:Building earth:Lake infrastructure:AbandonedOilGasWell biology:Plant demography:HumanIndividual biology:Individual infrastructure:LocalRoad infrastructure:Port earth:Ridge hydrology:RechargeZone earth:Coastline ecology:Tree infrastructure:EnergyInfrastructure infrastructure:MajorRoad chemistry:Anion infrastructure:Town earth:WaterBody infrastructure:Bank earth.incubation:IceGlacier demography:SocialGroup infrastructure:Airfield infrastructure:PrimaryInternationalPort earth:Coast infrastructure:Trailhead infrastructure:HumanSettlement infrastructure:MinorRoad infrastructure:Jetty im:OtherThings ecology:Canopy infrastructure:HydropowerTurbine infrastructure:RecreationalArea infrastructure:CommunicationInfrastructure conservation:ProtectedArea agriculture:Cropfield earth:Site infrastructure:Church infrastructure:GuestHouse demography:Community infrastructure:NoOilGasWell infrastructure:Mosque earth:Valley earth.incubation:Glacier ecology.incubation:Root infrastructure:SolarArray earth:Pit infrastructure:PedestrianRoad earth:StreamJunction ecology.incubation:Stump policy:Institution infrastructure:Synagogue hydrology:EphemeralFlow earth:Hill infrastructure:WildernessHut infrastructure:Reservoir infrastructure:AlpineHut earth:Reach im:NotSomething infrastructure:Apartment ecology.incubation:Stem hydrology:StreamOutlet infrastructure:Bridge hydrology:InstreamRechargeZone earth:BreakFoothill earth.incubation:DebrisGlacier ecology.incubation:Bark infrastructure:Playground infrastructure:Highway earth:Escarpment infrastructure:WaterTreatmentPlant infrastructure:WorshipBuilding infrastructure:Accomodation';
    var PROCESSES = 'earth:Hailing physical.incubation:Recombination earth:Wind physical:Sublimation hydrology.incubation:Interception agriculture:Planting agriculture:Girdling agriculture:Tillage hydrology:Evapotranspiration chemistry.incubation:Immobilization physical.incubation:Vaporization physical.incubation:Condensation earth:Weathering physical.incubation:Melting earth:Erosion ecology.incubation:Respiration hydrology:WatershedFormation im:Movement hydrology:WaterExtraction physical.incubation:Freezing soil:SoilGenesis chemistry.incubation:Nitrification agriculture:Mariculture hydrology.incubation:CapillaryRise ecology:HabitatChoice infrastructure.incubation:NaturalResourceUse im:Displacement chemistry.incubation:Leaching agriculture:Sowing im:Growth ecology.incubation:PrimaryProduction ecology.incubation:Photosynthesis agriculture:Harvesting physical:Freezing ecology:Growth physical.incubation:Sublimation agriculture:Cultivation ecology.incubation:PhenologyActivity ecology:BiomassDynamics hydrology.incubation:SnowMelt agriculture:Staking hydrology.incubation:GlacierMelt earth:Rainfall hydrology:Runoff hydrology:SurfaceWaterFlow agriculture:Thinning im:Coupling agriculture:Pruning im:Homeostasis chemistry.incubation:Volatilization earth:Snowfall biology:Reproduction biology:Growth es.pollination:Pollination agriculture:Deblossoming es:FloodRegulation ecology:EcologicalProcess hydrology:Percolation physical:Recombination agriculture:Hoeing physical:Deposition ecology:EcologicalSuccession physical:Melting earth:Sedimentation hydrology:Infiltration agriculture:Hilling agriculture:Rolling agriculture:SeedBedPreparation physical.incubation:Deposition es:LandslideRegulation hydrology:AquiferRecharge biology.incubation:Digestion im:Decoupling physical:Ionization im:Dissipation physical.incubation:Ionization agriculture:Ridging im:Assessment es:ClimateRegulation earth:Weather earth:PlateTectonics behavior:Recreation physical:Vaporization chemistry.incubation:Mineralization agriculture:StubbleCleaning chemistry.incubation:Denitrification agriculture.incubation:Irrigation biology.incubation:Ingestion hydrology:WaterMovement policy:Jurisdiction agriculture:Aquaculture hydrology:WaterBalance hydrology:BaseFlow earth:Precipitation physical:Condensation agriculture:Mulching biology.incubation:Excretion es.aesthetics:VisualEnjoyment agriculture:Defruiting agriculture:Agriculture agriculture.incubation:Fertilization soil:Pedogenesis';
    var RELATIONSHIPS = 'ecology:Pollination ecology:EcologicalInteraction ecology:CarbonSequestration ecology:MaterialEnergyFlow behavior:HelpRequest demography:Kinship ecology:NutrientFlow es.aesthetics:VisuallyEnjoys ecology:Symbiosis ecology:Parasitism ecology:MatingSignaling ecology:Competition ecology:WaterFlow ecology:Commensalism ecology:TerritorialMarking demography:Brotherhood demography:Friendship behavior:TravelConnection hydrology:WaterFlow ecology:NitrogenFixation ecology:Evapotranspiration ecology:Mutualism ecology:Predation ecology:CarbonStorage ecology:NonMaterialEnergyFlow demography:SocialMediaConnection earth:StreamConnection';
    var PREDICATES = 'geography:Iran geography:Iraq geography:ClippertonIsland geology:UltramaphicRock physical:Radiant landcover:InlandWaterBody ecology.incubation:CoastalSaltmarsh geography:IsleOfMan es.nca:BorealCoolTemperatePalustrineWetland agriculture:Potato biology.nomenclature:PinusPinaster ecology:MacroalgalKelpHabitat earth:Geostationary chemistry:Mercury es.pollination:PollinationEcosystemBenefit ecology.incubation:EpisodicAridFloodplain infrastructure:Spaceport geography:BosniaAndHerzegovina im:Incoming geography:Peru behavior:Educational ecology:Vegetation geography:Micronesia soil:MediumGrain agriculture:Sunflower im:MeanAnything agriculture:Cherry chemistry:Samarium geography:SvalbardJanMayenIslands ecology:LateSuccession geography:Lao agriculture:WinterWheat earth:Polar geography:Latvia agriculture:SoyBean landcover:IndustrialCommercialUnits agriculture:Pig physical:SouthWest landcover.incubation:ShrublandShrubbyWoodland agriculture:Pasture chemistry:Lawrencium landcover:ClosedDeciduousConiferousForest geology:Pahoehoe agriculture:Lemon geography:CostaRica ecology.incubation:IntensiveLandUseSystem im:Likely im:Total landcover:Construction landcover:SeaAndOcean landcover:SclerophyllousVegetation geography:UnitedStates ecology:EarlySuccession agriculture:Cereal es.nca:SubtropicalWarmTemperateForestedWetland biology.nomenclature:PinusSylvestris chemistry:Organic ecology:Pollinating chemistry:Burned chemistry:Helium geography:SouthAmericanRegion agriculture:Pollinator chemistry:Palladium im:Maximum policy:Conservative im:Inappropriate landcover:ForestSeminatural es.water:WaterNonConsumingTransactor ses:Provided geography:Curacao im:Average chemistry:Holmium conservation:Pristine geography:Albania es.nca:WarmTemperateTropicalMarsh biology.nomenclature:Salmonidae landcover:SparseShrubCover geography:Bangladesh policy:Municipality earth:Snow ecology:Autotroph geography:Kiribati behavior:Interactive agriculture:Maize biology.nomenclature:Crustacean landcover:Saline biology.nomenclature:UlexEuropaeus geography:FrenchGuiana chemistry:Francium chemistry:Thorium landcover:NonVegetatedStillWaterBody agriculture:Wheat es.nca:CoastalEcosystem physical:Liquid chemistry:Yttrium biology.nomenclature:NonInsect geology:Pumice geography:Slovakia biology.nomenclature:Picea earth:Downstream geography:NorthernMarianaIslands geography:Brunei ecology:CoralReefHabitat es.nca:TerrestrialEcosystem landcover:PermanentlyIrrigatedArableLand chemistry:Silver earth:Upstream geography:Macedonia im:Differential im:Visible es.water:WaterConsumptionEcosystemBenefit im:ExtremelyHigh geology:Aphanite geography:InsularAsianRegion geography:Suriname policy:State demography:Adolescent im:Impervious geography:Lesotho agriculture:Rice ecology:PelagicHabitat chemistry:Cerium geography:Belize im:Compromised agriculture:Pear geography:Lithuania agriculture:Buffalo geography:MarshallIslands earth:Littoral geography:Chad geography:Hungary im:Continuous ecology:Fauna ecology.incubation:SeasonallyDryTemperateHeathShrubland agriculture:Duck geography:Austria geography:Tanzania geography:Canada geography:Comoros infrastructure:Airport agriculture:Almond ses:Provisioning policy:ReligiousPurpose physical:South physical:Chemical geography:SaoTomeAndPrincipe chemistry:Mendelevium chemistry:Roentgenium im:Long ecology.incubation:BorealTemperateMontaneForestWoodland geography:Kenya es.nca:PolarAlpineRockyOutcrop agriculture:Mango landcover:IntertidalFlat landcover:MarineWaterBody geography:Mayotte geography:FrenchPolynesia soil:SubSoil ecology:GraminoidVegetation demography:Child landcover:BroadleafForest geology:Tuff im:Structural ecology:Deciduous geography:Russia im:Normalized chemistry:Astatine ecology:Brackish geography:Malawi landcover.incubation:TemperateBorealForestWoodland chemistry:Rhenium geography:Italy im:Up ecology.incubation:YoungRockyPavementLavaflowScree im:Moderate biology.nomenclature:Odonata biology:Hermaphrodite biology:Insecta im:Right ecology.incubation:TropicalSubtropicalForest geography:DominicanRepublic policy:EducationalPurpose soil:MediumFineGrain geology:Basalt es.water:WaterMovementConnection ecology:BorealConiferousForest geography:Lebanon geography:Guadeloupe agriculture:Watermelon landcover:LowDensityUrban es.aesthetics:AestheticViewEcosystemBenefit es.nca:TropicalFloodedForestPeatForest biology.nomenclature:Robinia landcover:InactiveAgriculturalLand earth:Bedrock landcover:Dump behavior:VisualContemplation soil:AHorizon hydrology:Pervious landcover.incubation:Aquatic ecology:ShrubVegetation geology:MaficRock es.nca:CoolTemperateHeathland hydrology:WaterPermanence geography:Montserrat economics:Estimated landcover:Watercourse geography:HeardIslandAndMcDonaldIslands ecology:SubtidalSandyHabitat soil:DissolvedSoilMatter geography:Luxembourg chemistry:Xenon behavior:Christian ecology.incubation:TemperateBorealForestWoodland geography:Georgia policy:PrivatelyOwned physical:East im:Yearly geology:Rock geography:Zambia geography:Honduras earth:Pedosphere landcover:ArtificialSurface geography:Dominica earth:Crust soil:CoarseGrain es.aesthetics:AestheticEnjoyment biology.nomenclature:QuercusRobur ecology:Heterotroph earth:Wet soil:LightClay ecology.incubation:GroundCover im:ModeratelyLow es.nca:UrbanIndustrialEcosystem im:Active landcover:ClosedEvergreenConiferousForest agriculture:Pistachio im:Quaternary landcover:NonIrrigatedArableLand geology:Serpentinite geography:BritishIndianOceanTerritory geography:Moldova im:High physical:Gas im:Critical policy:MedicalPurpose biology:Female landcover:FruitAndBerryPlantation geography:CongoRepublic chemistry:Darmstadtium chemistry:Sulfur physical:NorthEast physical:Material earth:Cloudy im:Belief chemistry:Indium im:Fine hydrology:HydrologicallyCorrected geography:ChristmasIsland geography:India es.aesthetics:AestheticExperienceSeeking landcover:MixedForest geography:Singapore geography:Aruba ecology.incubation:Wetland im:VeryLow landcover:ComplexCultivationPatternedLand geography:AustralianRegion geography:Malaysia landcover:InlandSwamp geography:Algeria soil:Sand geography:Paraguay geography:Vatican es.water:WaterProvider agriculture:Cucumber agriculture:Pollinated infrastructure:Gravel landcover:LichenMoss landcover.incubation:TropicalSubtropicalForest agriculture:Eggplant physical:Negative chemistry:Chromium chemistry:Aluminum ecology:SubtropicalDesert geography:Cameroon es.pollination:AgriculturalUseConnection chemistry:Zinc geography:FalklandIsland hydrology:SoilGroupD hydrology:SoilGroupC chemistry:Neptunium hydrology:SoilGroupB hydrology:SoilGroupA ecology:TropicalDesert physical:Gravitational es.nca:TropicalSubtropicalLowlandRainforest im:ModeratelyHigh policy:FamilyPurpose chemistry:Curium geography:Seychelles chemistry:Erbium physical:North behavior:Economic geography:Syria es.water:WaterProvided chemistry:Nobelium es.nca:TropicalSubtropicalForest geography:Cuba im:Large es.nca:DesertSemidesert im:Potential chemistry:Titanium es.pollination:PollinatedYield ecology:Sclerophyllous im:Perceived landcover:EvergreenConiferousForest infrastructure:AirTrafficControlCenter agriculture:Fertilizer geography:Croatia biology.nomenclature:PinusHalepensis im:Economics es.nca:SeasonallyDryTemperateHeathShrubland im:ModeratelyDamaged geology:Dacite chemistry:Polonium earth:Subtropical im:Back ecology.incubation:ShrublandShrubbyWoodland geography:SouthKorea im:Small hydrology:Likelihood100Years biology.nomenclature:Gramineae chemistry:Thulium earth.incubation:Riparian behavior:Catholic earth:Lithosphere landcover:ConiferousForest geography:Greece ecology.incubation:Overstory im:ExtremelyLow ecology.incubation:UrbanEcosystem ecology:Aphyllous chemistry:Livermorium earth:Riverine landcover:NaturalVegetation chemistry:Iron soil:SandyClay es.pollination:PollinatorAbundance chemistry:Arsenic infrastructure:Dirt soil:FineGrain earth:Thermosphere policy:Managed agriculture:Carob policy:RegionalDistrict hydrology:Seasonal geography:ArcticRegion geography:Malta geography:Tokelau ses:Regulating landcover:AnnualCroplandAssociatedWithPermanent chemistry:Tennessine infrastructure.incubation:Timber geography:Angola chemistry:Californium chemistry:Scandium geography:AlandIslands geography:Andorra soil:HeavyClay geography:Guam chemistry:Nitrogen ecology:DroughtLimited geography:Pakistan im:Low im:Down landcover.incubation:Wetland chemistry:Dubnium im:Hypothetical im:Rare im:Common geography:Mauritius im:Difficult im:Monthly biology.nomenclature:Ohc im:Tall geography:Azerbaijan geography:GuineaBissau landcover:HighDensityUrban landcover:PulpPaperPlantation earth:Mantle im:Positive im:Possible ecology.incubation:IntertidalForestShrubland earth:Boreal chemistry:Bohrium ecology:TropicalRainforest geography:Guyana biology.nomenclature:Polypodiopsida soil:SiltyClayLoam geography:Mauritania landcover:InlandMarsh soil:VeryFineSand es.nca:IntensiveLandUseSystem ecology.incubation:TemperateWoodland agriculture:Sheep chemistry:Manganese biology.nomenclature:Betula geography:Brazil landcover:MineralExtraction geography:Greenland landcover:EvergreenShrubland landcover:Vineyard chemistry:Rhodium geography:Denmark geography:Fiji geology:Anorthosite chemistry:Chlorine es.nca:HyperaridDesert es.nca:ConditionIndicator landcover:Forest im:Geology soil.incubation:CHorizon es.pollination:PollinationDependent es.nca:PolarTundraDesert geography:Samoa demography:Rich soil:SandyClayLoam ecology.incubation:Aquatic geography:Namibia agriculture:SugarBeet geography:Nicaragua im:Far agriculture:OrganicFertilizer geography:Taiwan chemistry:Ruthenium infrastructure:Asphalt chemistry:Hafnium im:EnvironmentalSocialScience earth:Exosphere landcover:OpenEvergreenBroadleafForest policy:Progressive geography:Ukraine chemistry:WaterSoluble im:Weekly im:Passive landcover.incubation:SavannaGrassland biology.nomenclature:Hemiptera biology.nomenclature:Ephemeroptera agriculture:Rye chemistry:Platinum landcover:MoorAndHeathland behavior:Buddhist ecology:TemperateSteppe agriculture:Gourd geography:NorthKorea infrastructure:RailroadBridge earth:Brackish behavior.incubation:Fishing geography:Guatemala physical:NorthWest chemistry:Hassium agriculture:Oat geography:Swaziland soil:MediumSand geography:Mali im:Retained ecology:Forest earth:Marine earth:Tropical biology.nomenclature:Calluna geography:Oceanian earth:Terrestrial chemistry:Zirconium agriculture:Bean biology.nomenclature:Carex ecology.incubation:Midstory agriculture:Cotton chemistry:Promethium landcover:WaterBody infrastructure:Intercity geography:Nauru geology:Tephra geography:SolomonIslands im:Irregular geography:Tuvalu chemistry:Selenium geology:Kimberlite landcover:Estuary im:Artifacts biology.nomenclature:Trichoptera ecology.incubation:DesertSemidesert ecology.incubation:PolarAlpine geography:CaymanIslands geography:TrinidadAndTobago physical:Red chemistry:Terbium ecology:Saline ecology:TemperateMountainSystem agriculture:PalmOil chemistry:Oganesson infrastructure:Landfill geography:Australian es.pollination:AgriculturalProduction chemistry:Cobalt geography:Bonaire chemistry:Vanadium im:Moving geography:WesternSahara im:Biology chemistry:Molybdenum policy:PrivatelyManaged biology.nomenclature:FestucaRubra earth:Hail geography:Qatar landcover.incubation:IntensiveLandUseSystem landcover:SedgeMeadow im:Regular ecology:TemperateOceanicForest geography:SaintHelena geography:PalestinianTerritory im:Finite earth:Estuarine im:Net landcover:StillWaterBody geology:HypabyssalRock geography:WallisAndFutuna chemistry:Flerovium chemistry:Barium biology.nomenclature:Senecio policy:IndividuallyOwned im:Easy infrastructure:PassengerConveyance im:Removed es.aesthetics:AestheticallyDetrimental geography:CzechRepublic im:Functional im:Damaged landcover:PeatBog chemistry:Tungsten geology:MetamorphicRock ecology:SubtidalRockAndRockyReefsHabitat es.nca:SavannaGrassland ecology:SubtidalSandyMudHabitat geography:SpratlyIslands chemistry:Carbon landcover:OpenMixedForest infrastructure:CommuterTrain behavior:Cognitive landcover:ArableLand chemistry:Colloid im:Short es.nca:ShrublandShrubbyWoodland im:AbsoluteIndicator im:Art im:Current geography:SaintMartin landcover:DeciduousConiferousForest agriculture:Orange geography:Tunisia infrastructure:Paved geography:Reunion ecology.incubation:TropicalSubtropicalSavanna conservation:Protected im:Comparable geography:Zimbabwe demography:Infant im:Mean es.water:WaterEnergyEcosystemBenefit earth:Dry landcover:ClosedMixedForest ecology.incubation:Understory geography:SaintPierreAndMiquelon ecology.incubation:TemperateSubhumidGrassland geography:Turkmenistan behavior:Shintoist landcover:GlacierAndPerpetualSnow geography:Philippines soil:Erodible im:VeryHigh geography:NorfolkIsland geography:Kazakhstan chemistry:Lanthanum economics:Appraised chemistry:Krypton ecology.incubation:OtherDesertSemidesert ecology.incubation:BorealCoolTemperatePalustrineWetland landcover:HeterogeneousAgriculturalLand chemistry:Suspension chemistry:Phosphorus ecology:SubtidalLooseRockPebbleGravelHabitat chemistry:Ytterbium landcover:Pastureland ecology:Prey agriculture:BarbaryFig es.nca:Freshwater im:Horizontal behavior:Interesting behavior:Visual ecology.incubation:TropicalSubtropicalDryForestScrub chemistry:Einsteinium biology.nomenclature:Helictotrichon soil:Silt geography:Afghanistan biology.nomenclature:Pinus geography:UnitedArabEmirates es.nca:CoastalSaltmarshReedbed geography:SintMaarten agriculture:Fig agriculture.incubation:Fertilized ecology:Fresh chemistry:Thallium geography:CongoDemocraticRepublic es.carbon:SnowpackMaintenance geography:AtlanticRegion infrastructure:Transportation chemistry:Germanium geography:CentralAfricanRepublic chemistry:Gallium chemistry.incubation:Nitrate es.nca:Aquatic geography:Jamaica es.aesthetics:AestheticallyValuable hydrology:WaterloggedSoil physical:Sonic geography:Argentina soil:TopSoil geography:Rwanda im:Sociology es.nca:SeasonallyDryTropicalShrubland landcover.incubation:PolarAlpine landcover:OpenDeciduousConiferousForest geography:PitcairnIslands geography:Nigeria agriculture:Melon im:Previous behavior:Social biology.nomenclature:Phragmites chemistry:Technetium geography:Jersey chemistry:Fluorine im:Gross chemistry:Potassium landcover:ArtificialVegetatedArea geography:Gambia landcover:SeminaturalOpenSpace landcover:BareArea physical:Nuclear im:Ephemeral ecology:HerbaceousVegetation im:Absolute soil:Loam ecology:TemperateDesert es.nca:BrackishTidal chemistry:Neodymium soil:BHorizon agriculture:CashCrop earth:WaterFlowDirection geography:Bermuda geography:FaroeIslands chemistry:Basic landcover:AgriculturalVegetation ecology.incubation:Benthic soil.incubation:EHorizon es.nca:TemperateWoodland landcover:Airport soil:SiltyClay agriculture:Cattle im:Front behavior:Religious geography:Japan im:Coarse ecology:Polar im:Tertiary geography:Morocco im:Daily geography:Slovenia earth:Air chemistry:Cesium earth:Mesosphere geography:Australia soil:RHorizon landcover:Grassland behavior:BenefitCriterion agriculture:SugarCane geography:Niger landcover:MineDumpConstruction landcover:DeciduousBroadleafForest infrastructure:Concrete chemistry:Radium geography:Guinea es.pollination:AgriculturalExploitation soil:FineSand landcover:SparseTreeCover im:Pervious chemistry:Meitnerium geography:Thailand chemistry:Rubidium agriculture:Manure biology.nomenclature:Castanea geography:Benin im:SlightlyDamaged geology:IgneousRock geography:SouthAmerican landcover:AgroForestryLand geography:NorthAmericanRegion im:Destroyed ecology:SubtropicalHumidForest ecology.incubation:PolarAlpineTundra agriculture:WinterSquash landcover:EvergreenBroadleafForest landcover:ScrubHerbaceousVegetation chemistry:Plutonium soil:OHorizon behavior:CostCriterion geography:Kuwait geography:Tajikistan ecology.incubation:TemperateForest chemistry:Cadmium geography:BouvetIsland geography:HongKong agriculture:Cucurbitaceae geography:ElSalvador infrastructure:RailroadStation es.aesthetics:AestheticsEnjoyed es.aesthetics:AestheticEnjoymentBeneficiary agriculture:Apricot es.nca:TropicalSubtropicalMontaneRainforest im:Predicted geography:PapuaNewGuinea ses:Rival chemistry:Neon im:Engineering geography:PacificRegion ecology.incubation:CoolTemperateHeathland geography:Mozambique es.water:WaterConsumingBeneficiary geography:TurksAndCaicosIslands agriculture:Avocado earth:Geolocated geography:Bhutan landcover:CoastalLagoon chemistry:Osmium biology.nomenclature:Carpinus earth:SaltMarsh im:Realized hydrology:Sewage earth:Aquatic landcover:IndustrialCommercialTransport landcover:SparseVegetation geography:Antarctic landcover.incubation:DesertSemidesert biology.nomenclature:Fagus behavior:Indoor chemistry:Radon soil:SandyLoam geography:SaintVincentAndGrenadines ecology:BorealMountainSystem es.pollination:PollinatorThroughput geography:Ethiopia chemistry:Protactinium ecology:SeagrassHabitat im:Agriculture agriculture:Rapeseed demography:Toddler im:Hydrology landcover:RiceField geography:Libya ecology:BorealTundraWoodland geology:Peridotite ecology:EstuarineHabitat geography:Tonga geology:Lava im:Infinite geography:IndianRegion chemistry:Iridium chemistry:AlkalinityLevel landcover:Mangrove es.pollination:AgriculturalProductionDependent policy:Ward es.pollination:PollinationSupplyConnection behavior:Alternative demography:Poor behavior:Cultural chemistry:Inorganic biology.nomenclature:Ept chemistry:Lutetium geography:Barbados im:Primary geology:Andesite ecology.incubation:CoolDesertSemidesert chemistry:Lithium es.nca:TropicalSubtropicalSavanna landcover:InlandWetland ecology:SubtropicalMountainSystem geography:Ireland im:Partial agriculture:Plum geography:Botswana im:Outgoing ecology:TemperateContinentalForest chemistry:Oxygen geography:Bahamas im:Natural landcover:OilPalmPlantation geography:SierraLeone biology.nomenclature:Populus chemistry:Copernicium landcover:LandSurface agriculture:Fowl chemistry:Antimony im:Observed landcover:ClosedDeciduousBroadleafForest es.nca:SubterraneanEcosystem biology:Male hydrology:EventLikelihood infrastructure:FreightConveyance ecology.incubation:SavannaGrassland earth:AtmosphericBottomLayer agriculture:Alfalfa landcover:BeachDuneAndSand soil.incubation:RHorizon geography:Iceland soil.incubation:BHorizon chemistry:Alkaline chemistry:Magnesium biology.nomenclature:Mollusca policy:PubliclyOwned im:Appropriate im:Minimum biology.nomenclature:Coleoptera geography:NewZealand geography:Anguilla geography:Haiti geography:Maldives demography:Human geography:Poland ecology.incubation:WarmTemperateTropicalMarsh agriculture:Cocoa chemistry:Europium geography:EasternHemisphere soil.incubation:GroundWaterLayer infrastructure:Unpaved im:Policy geography:FrenchSouthernTerritories hydrology:Permanent policy:Moderate es.nca:AlpineGrasslandShrubland geography:Kyrgyzstan agriculture:Tangerine geography:Estonia ecology:TropicalMoistDeciduousForest geography:AfricanRegion landcover:OliveGrove chemistry:Hydrogen agriculture:Peanut geography:Uzbekistan landcover:ClosedSavanna physical:Plasma im:NoAttribute chemistry:Fermium soil:MediumCoarseGrain geography:AntarcticRegion es.nca:CoolDesertSemidesert ecology.incubation:TropicalSubtropicalLowlandRainforest geography:Serbia ecology:Needleleaved ecology.incubation:PolarAlpineCliffScreeOutcropLavaFlow soil:EHorizon geography:Djibouti es.aesthetics:Viewpoint im:Changed geography:Ghana geography:Liechtenstein im:Ecology soil:Soil geography:UnitedStatesVirginIslands im:Local landcover:BurnedLand geography:Montenegro chemistry:Argon biology.nomenclature:Eucalyptus geography:CocosIslands geography:EuropeanRegion biology:Reproductive geography:NewCaledonia landcover:DeciduousShrubland landcover:BareRock geography:CookIslands geography:Indonesia ecology:MidSuccession geography:Armenia geography:ParacelIslands geography:Colombia agriculture:Hazelnut geography:AsianRegion im:Hourly agriculture:Tomato geography:European ecology:SubtropicalSteppe chemistry:Niobium landcover:ClosedEvergreenBroadleafForest geography:EquatorialGuinea agriculture.incubation:Mares ecology.incubation:TropicalFloodedForestPeatForest geography:Ecuador landcover:Wetland agriculture:Honeybee im:Indicator agriculture.incubation:Irrigated biology.nomenclature:Insecta geography:Monaco chemistry:Beryllium chemistry.incubation:Phosphate agriculture:Bubalus demography:Immigrant geography:BurkinaFaso landcover:Shrubland ecology:NoSuccession geography:PuertoRico physical:Mechanical im:Pedology behavior:Sport chemistry:Boron landcover:SaltMarsh ecology:ForbVegetation physical:Electrical earth:Temperate geography:CapeVerde policy:County es.nca:Marine ecology.incubation:IceSheetGlacierPermanentSnowfield geography:Somalia chemistry:Seaborgium geography:NorthAmerican im:Nature policy:MetropolitanArea agriculture:Coffee agriculture:Cassava geography:Liberia biology.nomenclature:Fraxinus physical:SouthEast geology:Obsidian biology:Plants es.nca:RockyPavementLavaflowScree ecology.incubation:RadiationLimited behavior:Tourist biology.nomenclature:PinusRadiata geography:SouthAfrica es.nca:BorealTemperateMontaneForestWoodland chemistry:Gold infrastructure:Cesspool im:Geography hydrology:Likelihood500Years policy:CollectivelyManaged biology.nomenclature:Quercus geography:Macao physical:Energetic es.water:WaterDeliveryConnection physical:Potential landcover:TransitionalWoodlandScrub demography:Adult geography:Germany im:Animated chemistry:Nihonium landcover:DwarfShrubland chemistry:Dysprosium geography:WesternHemisphere es.nca:Cropland im:Pristine biology.nomenclature:Plecoptera ecology:MossVegetation im:Theoretical geography:SouthernHemisphere geography:Antarctica earth:Lacustrine geography:China geography:Vietnam policy:PoliticalPurpose chemistry:Berkelium geography:SaudiArabia landcover:OpenSavanna landcover:GreenUrbanArea es.nca:TemperateForest im:Reference demography:Foreigner geography:Togo es.nca:IntertidalForestShrubland im:Standardized geography:Gibraltar policy:Legal infrastructure:RailroadTrack geography:Grenada es.carbon:StormReduction agriculture:Goat ecology:TropicalDryForest es.pollination:PollinatorSupplier im:RelativeIndicator chemistry:Tantalum chemistry:Actinium im:Domain chemistry:Tin behavior:Relevant hydrology:Tidal policy:CollectivelyOwned im:Still geography:Spain im:Endogenous behavior:Experiential geography:Israel behavior:Muslim im:Whole geology:PillowLava behavior:Recreational behavior:Focal agriculture:Strawberry policy:IndividuallyControlled physical:Elastic geography:Niue geography:Bulgaria landcover:AgriculturalLandWithNaturalVegetation geography:Yemen geography:Chile physical:Positive behavior:Baptist biology.nomenclature:Cyprinidae earth:Rain chemistry:Uranium geography:SaintKittsAndNevis ecology:Broadleaved agriculture:Zucchini landcover:Urban agriculture:OilSeed agriculture:Syncerus behavior:Stakeholder im:Negative earth:Troposphere landcover:MediumDensityUrban demography:Autochtonous ecology:PoleSuccession earth:Stratosphere chemistry:Moscovium chemistry:Nickel behavior:ActivityContext es.aesthetics:AestheticEnjoymentLocation ecology:SubtidalMuddyHabitat soil:LoamySand ecology:TreeVegetation geography:SanMarino geography:Senegal im:Maximal geography:Turkey es.nca:PalustrineWetland agriculture:Chicken geography:BritishVirginIslands im:Secondary geography:Sudan soil:VeryCoarseSand geography:Cyprus es.nca:IceSheetGlacierPermanentSnowfield agriculture:Compost ecology.incubation:TropicalSubtropicalMontaneRainforest es.nca:TemperateSubhumidGrassland es.water:WaterConsumed ses:Demanded geography:Palau geography:TimorLeste geography:Asian ecology.incubation:NitrogenLimited biology.nomenclature:Pseudotsuga geography:Gabon im:Latter geography:Sweden chemistry:Tellurium geography:Bahrain im:Vertical geography:Mexico geography:SouthSudan chemistry:Americium landcover:OpenDeciduousBroadleafForest chemistry:Bismuth infrastructure:RailroadTunnel physical:West agriculture:Tobacco geography:Madagascar geography:Burundi es.water:WaterConsumingTransactor im:Added behavior:Jewish geography:Eritrea im:Near im:Exogenous agriculture:Sorghum geography:CoteDIvoire chemistry:Solution chemistry:Lead soil:ClayLoam soil:CoarseSand es.nca:OtherDesertSemidesert ecology:OldGrowth geography:Bolivia geography:UnitedStatesMinorOutlyingIslands biology.nomenclature:Abies geography:Romania ecology:LichenVegetation landcover:SportLeisureFacility es.taxonomy:Mangrove es.nca:EpisodicAridFloodplain ecology:Natural agriculture:Pumpkin ecology:TropicalMountainSystem agriculture:Turnip geography:African chemistry:Calcium geology:SedimentaryRock soil:SiltLoam policy:SocialWelfarePurpose ecology:Predator geography:Portugal es.carbon:SeaLevelStability agriculture:Tuber geography:Egypt biology.nomenclature:Alnus landcover:RoadRailNetwork chemistry:Sodium agriculture:InorganicFertilizer es.water:WaterProvision ecology.incubation:HyperaridDesert im:Successive demography:Elderly geology:Rhyolite im:Actual policy:EconomicPurpose im:Certain ecology:Evergreen es.nca:PolarAlpine im:Former es.aesthetics:AestheticValuable geography:Belarus chemistry:Gadolinium chemistry:Iodine chemistry:Silicon biology.nomenclature:UlexGallii im:Left landcover:OpenEvergreenConiferousForest agriculture:Peach geography:NetherlandsAntilles geography:Norway es.aesthetics:VisualConnection geography:Jordan geography:SaintBarthelemy es.nca:TemperateBorealForestWoodland chemistry:Neutral earth:Limnetic geography:Nepal geography:Uganda geography:Martinique geography:NorthernHemisphere geography:Venezuela geography:AmericanSamoa landcover:NonIrrigatedArableLandHerbaceous geography:Switzerland physical:Kinetic chemistry:Praseodymium agriculture:Meadow geography:AntiguaAndBarbuda physical:Green physical:Solid infrastructure:RoadSurface ecology.incubation:SubtropicalWarmTemperateForestedWetland geography:Oman geography:UnitedKingdom chemistry:Bromine agriculture:Citrus physical:Informational chemistry:Copper agriculture:LeafAndStem physical:Thermal ecology:TropicalShrubland ecology:SubtropicalDryForest es.nca:TropicalSubtropicalDryForestThicket biology.nomenclature:Larix im:Impossible geography:Panama chemistry:Strontium geography:France earth:Atmosphere agriculture:Barley geography:Myanmar im:Chemistry geography:Netherlands geography:Cambodia geography:Vanuatu ecology:MarineHabitat behavior:Outdoor geography:SouthGeorgiaAndSouthSandwichIslands geography:Uruguay im:Relevant geography:Mongolia soil:CHorizon agriculture:Apple geography:Guernsey agriculture:Legume im:Constant soil.incubation:Soil earth:Snowy geography:SriLanka landcover:VegetatedStillWaterBody geography:Finland im:Minimal infrastructure:RailroadYard landcover:Port chemistry:Rutherfordium es.nca:ConditionMetric ecology:ColdLimited es.water:WaterFlowed im:Relative geography:Belgium landcover:SparseHerbaceousCover policy:PubliclyManaged geography:SantaLucia ecology.incubation:SeasonallyDryTropicalShrubland landcover:MaritimeWetland ecology.incubation:Cropland landcover:PermanentCropland agriculture:Fallow';
    var EVENTS = 'earth:Fall biology:EmbryoStage earth.incubation:Snowfall ecology:Nesting behavior:Burglary earth:RainOnSnow biology:InfantStage im:March im:July im:September hydrology:Drought biology:FetusStage earth:Earthquake im:Millennium earth:Winter im:August biology:Life earth:Lightning earth:Summer im:Collapse earth.incubation:Rainfall im:May biology:Birth im:December earth:Flood im:Minute chemistry:Reaction im:October im:April hydrology.incubation:HighStreamFlowEvent im:Year biology:Adulthood im:June behavior:Coup earth:Storm chemistry:Fire biology:PreAdolescence im:Month earth:Spring im:November biology:Adolescence im:Second behavior:Election ecology:Flowering im:Hour im:Season ecology:GrowingSeason biology:ElderlyStage behavior:MassMurder im:January chemistry:Explosion im:February earth:Landslide behavior:Homicide im:Day biology:JuvenileStage behavior.incubation:Photograph im:Decade earth.incubation:Precipitation biology:Childhood im:Century earth.incubation:Hailing biology:Death hydrology:DayWithPrecipitation';
    var ABSTRACT_QUALITIES = 'im:QualityRanking im:Depth im:Width im:Resilience hydrology.incubation:Moisture im:Preference im:Length im:Capacity im:Quantity ecology:Diversity im:Balance im:Energy im:Priority im:Proportion hydrology:VerticalWaterFlowVelocity hydrology:GroundWaterVolume im:Amount im:Type im:Velocity im:Diversity im:Frequency im:Age im:MonetaryValue im:Concordance hydrology:WeatherMediatedDepositionWaterVolume im:Numerosity chemistry:ChemicalEnergy chemistry:Salinity hydrology:SurfaceWaterVolume im:Acceleration demography:IncomeInequality demography:Poverty hydrology:HorizontalWaterFlowVelocity im:Height im:Heterogeneity soil.incubation:WiltingPoint im:Temperature im:Mass im:Security im:Complexity economics:Wealth im:Duration im:Volume im:Direction im:Area im:Angle im:SpatialCoverage im:Density im:Weight';
    var ABSTRACT_SUBJECTS = 'infrastructure:TransportationInfrastructure infrastructure:AdministrativeTownship ecology:EcologicalGroup infrastructure:BuiltArtifact infrastructure:ManufacturedProduct ecology.incubation:SubterraneanPlantPart physical:AssertedBody biology:PlantPart ecology.incubation:PlantPart physical:Body infrastructure:GeolocatedInfrastructure policy:Government infrastructure:CommercialInfrastructure infrastructure:WaterRegulationInfrastructure im:Subject infrastructure:HousingInfrastructure ecology.incubation:AerialPlantPart physical:Entity physical:Group physical:DelimitedBody im:Agent infrastructure:MonitoringInfrastructure physical:Cavity im:Boundary hydrology:HydrologicallyRelevantRegion infrastructure:CoastalProtectionInfrastructure engineering:Machine physical:Observable';
    var ABSTRACT_PROCESSES = 'behavior:Assessment earth:MeteorologicalProcess hydrology:HydrologicalProcess hydrology:WaterOutput ecology.incubation:Resorption physical:PhaseTransition ecology.incubation:Decomposition physical:InformationTransport physical:EnergyTransport behavior:Activity earth:LandformingProcess ecology.incubation:Senescence behavior:Choice im:SystemTransformation agriculture:AgriculturalActivity chemistry.incubation:NitrogenCycle im:Transport im:TransitiveProcess im:IntransitiveProcess physical.incubation:PhaseTransition agriculture:Farming behavior:DecisionProcess ecology.incubation:Litterfall hydrology:WaterInput physical:MatterTransport im:Process im:SpatialIntransitiveProcess agriculture:AgriculturalProcess im:SpatialTransitiveProcess';
    var ABSTRACT_RELATIONSHIPS = 'im:InformationTransferConnection im:Relationship ecology:InterspecificInteraction im:EnergyTransferConnection ecology:DirectedInteraction demography:HumanBond im:MatterTransferConnection behavior:Interest behavior:IntentionalConnection ecology:AbioticInteraction behavior:Intention im:TransferConnection ecology:MaterialFlow ecology:InformationInteraction';
    var ABSTRACT_PREDICATES = 'im:DataReinterpretation ecology:Salinity biology.nomenclature:VegetationGenus geography:Country agriculture:Crop chemistry:Compound ecology.incubation:WoodProductivity policy:InstitutionalPurpose biology:Species ecology:LifeForm ecology:LeafType infrastructure:RailRightsofWay ecology:TrophicRole biology.nomenclature:VegetationFamily biology:Juvenile soil:ReferenceSoilGroup biology.incubation:Living im:Identity demography:AgeGroup im:Perviousness soil:SoilOrder biology:Taxonomy im:EuclidianRelativeOrientation2D im:Severity biology:Infraspecies earth:LatitudinalRegion biology.incubation:IngestedFood physical:Color policy:PoliticalViewpoint im:Knowledge im:Origin soil:SoilMatter physical:MagneticPlanetaryOrientation physical:PhysicalIdentity geography:RegionalIdentity behavior:Criterion es.nca:Ecosystem ses:Flow chemistry:ChemicalElement im:ObserverRelativeOrientation im:Coarseness es.nca:EcologicallyDependent landcover:Vegetation biology:Fry biology:Adult geography:LatitudinalHemisphere agriculture:Fodder im:Participation geology:ExtrusiveRock infrastructure:Aviation demography:Affluence earth:PhysicalEnvironment landcover.incubation:Ecosystem es.nca:ForestEcosystem im:Finiteness im:AccomplishmentRelated im:TemporalOrientation im:TemporalConsequentiality im:DynamicStateRelated im:Lineal chemistry:ChemicalSpecies im:FrequencyOfOccurrence im:Norms infrastructure:RailroadInfrastructure im:RelationalAttribute biology:TaxonomicIdentity im:Regularity im:RankingCriterion agriculture:Fruit ecology:EcoFloristicRegion behavior:Relevance chemistry:Mixture soil:Granularity im:EvidenceOfTruth biology:Sex im:Explanations im:DamageLevel ses:Sink es.nca:EcologicallyDependentGood behavior:FruitionMode infrastructure:Industry biology:Kingdom geography:GeographicalIdentity ses:ProductionRelated ses:UseFlow im:Unable biology.nomenclature:VegetationClass im:MeasurementRelated ecology:VegetationLimitingFactor earth:WeatherDominance agriculture:AgriculturallyUsefulInsect im:MovementRelated im:SpatialOrientation ses:Supply im:DataReduction biology.nomenclature:MacroInvertebrate geography:Hemisphere soil:SoilTexture geography:Continental earth:PrecipitationType im:Volumetric ses:Effector ecology:SuccessionalStage biology:Phylum economics:AppraisalRelated soil:SoilTaxon earth:Georeferenced im:Appropriateness ses:Provision policy:District biology.nomenclature:TreeSpecies ses:ValueMaintenance ses:RegulatingEcosystemBenefit im:UnboundedRankingCriterion ecology.incubation:Bocage es.water:WaterProvisionConnection im:VolumeLevel agriculture:AgriculturalDestination ecology:HeightClassifiedWoodyVegetation ecology.incubation:VegetationLimitingFactor landcover:LandCover hydrology.incubation:Intercepted earth:Freshwater im:Level policy:Ownership behavior:Faith agriculture:Livestock ses:ValueExchange biology:Cultivar im:Ability biology:Class im:ConnectionDirection biology.nomenclature:VegetationSpecies policy:GeoPoliticalSubdivision ses:BeneficialMechanism behavior:ActivityType ecology:LeafPhenology im:SystemDescription im:Outcome chemistry.incubation:Ice hydrology:HydrologicSoilGroup es.carbon:CarbonSequestrationBenefit ecology.incubation:Litter physical:Phase chemistry:ChemicalNature im:Culture im:Relativity im:Puntal earth:GeosphereStratum ecology.incubation:VegetationStratum ecology.incubation:Ecosystem im:IntendedPurpose geography:ContinentalRegion biology:Genus es.water:WaterService im:VerticalHeightLevel earth:AtmosphericLayer biology.nomenclature:TreeGenus ses:SocialUptake ses:FlowRole biology.nomenclature:FishFamily ses:Provider agriculture.incubation:BodyCondition geography:GeopoliticalIdentity im:Realm physical:Polarity biology:CommonsenseTaxonomy infrastructure:WasteDisposalPurpose ses:Transactor biology:Taxon im:Technology ecology:WoodyVegetation im:Areal im:Able demography:NationalOrigin im:RelativeDistance im:Attribute im:RankOrder ses:Demand policy:Management ses:ProvisioningEcosystemBenefit im:Closeness im:Orientation ses:Beneficiary im:OccurencePattern geography:LongitudinalHemisphere im:HorizontalLenghtLevel ecology:Habitat im:Throughput ses:ProvisionFlow ses:EcosystemBenefitFlow soil:SoilStratum biology:Nutrient ses:ValueExchanged ses:ValueTransferred es.nca:EcologicallyDependentCropType im:Wholeness infrastructure:RailroadConveyance es.nca:EcologicallyDependentService soil.incubation:SoilStratum ses:ValueProvided ses:ValueUsed im:Physical soil:Clay im:InterdisciplinarySciences im:HeightLevel ses:SocioEcologicalExchange biology:Order biology:Family im:Difficulty im:BoundedRankingCriterion';
    var ABSTRACT_EVENTS = 'im:PeriodExpiration earth:MeteorologicalEvent behavior:SociallyRelevantEvent behavior:CriminalEvent earth:Season earth:GeologicalEvent policy:Action im:SystemEvent behavior:Decision biology:LifeEvent im:Occurrence im:Event earth:GeolocatedEvent';
    var CONFIGURATIONS = "demography:SocialNetwork earth:EarthCover es.aesthetics:Aesthetics ecology:WaterRetentionStructure earth:Bathymetry earth:LandCover earth:StreamNetwork physical:Gravity es:GreenInfrastructure ecology:Biodiversity earth:Terrain ecology:Landscape soil:Horizon earth:Gravity ecology:VegetationStructure";
    var ABSTRACT_CONFIGURATIONS = "physical:FlowNetwork im:Configuration im:MeasuredConfiguration im:Network im:PerceivedConfiguration im:InferredConfiguration physical:PhysicalConfiguration physical:EnergyNetwork physical:InformationNetwork im:SpatialNetwork";
    var VIEW_VERBS = "image checkbutton tree combo label separator textinput button confirm alert radiobutton html text";
    var USER_VERBS = "__USER_VERBS__";
    var OBJECT_VERBS = "__USER_VERBS__";
    var STATE_VERBS = "__STATE_VERBS__";
    var SESSION_VERBS = "debug locate submit maybe roles scenarios error when pack context warning reset info";
    var EXPLORER_VERBS = "download hide setui show";
    var IMPORTED_VERBS = "__IMPORTED_VERBS__";

   var ANNOTATION = {
      className: 'meta',
      begin: '@' + JAVA_IDENT_RE,
      contains: [
        {
          begin: /\(/,
          end: /\)/,
          contains: ["self"] // allow nested () inside our annotation
        },
      ]
    };
    const NUMBER = NUMERIC;

    const TEXTBLOCK = function(begin, end, modeOptions = {}) {
      const mode = hljs.inherit(
        {
          className: 'textblock',
          begin,
          end,
          contains: []
        },
        modeOptions
      );
      return mode;
    };

    return {
      name: 'KActors',
      aliases: ['kactors'],
      keywords: {
  		  // admits ns.subns:Concept notation
  		  $pattern: /\b[a-z\.]+(:[A-Z][A-z]+)?\b/,
  		  keyword: KEYWORDS,
  		  viewverb: VIEW_VERBS,
  		  userverb: USER_VERBS,
  		  objectverb: OBJECT_VERBS,
  		  stateverb: STATE_VERBS,
  		  sessionverb: SESSION_VERBS,
  		  explorerverb: EXPLORER_VERBS,
  		  importedverb: IMPORTED_VERBS,
        	  quality: QUALITIES,
  		  predicate: PREDICATES,
  		  subject: SUBJECTS,
  		  process: PROCESSES,
            event: EVENTS,
  		  relationship: RELATIONSHIPS,
            configuration: CONFIGURATIONS,
  		  aquality: ABSTRACT_QUALITIES,
  		  apredicate: ABSTRACT_PREDICATES,
  		  asubject: ABSTRACT_SUBJECTS,
  		  aprocess: ABSTRACT_PROCESSES,
  		  arelationship: ABSTRACT_RELATIONSHIPS,
  		  aevent: ABSTRACT_EVENTS,
            aconfiguration: ABSTRACT_CONFIGURATIONS
  	  },
      illegal: /<\/|#/,
      contains: [
        hljs.COMMENT(
          '/\\*\\*',
          '\\*/',
          {
            relevance: 0,
            contains: [
              {
                // eat up @'s in emails to prevent them to be recognized as doctags
                begin: /\w+@/, relevance: 0
              },
              {
                className: 'doctag',
                begin: '@[A-Za-z]+'
              }
            ]
          }
        ),
        // // relevance boost
        // {
        //   begin: /namespace/,
        //   keywords: "namespace",
        //   relevance: 2
        // },
        hljs.C_LINE_COMMENT_MODE,
        hljs.C_BLOCK_COMMENT_MODE,
        hljs.APOS_STRING_MODE,
        hljs.QUOTE_STRING_MODE,
        TEXTBLOCK('%%%', '%%%'),
        // {
        //   className: 'class',
        //   beginKeywords: 'class interface enum', end: /[{;=]/, excludeEnd: true,
        //   relevance: 1,
        //   keywords: 'class interface enum',
        //   illegal: /[:"\[\]]/,
        //   contains: [
        //     { beginKeywords: 'extends implements' },
        //     hljs.UNDERSCORE_TITLE_MODE
        //   ]
        // },
        // try to catch klab namespace
        {
          className: 'namespace',
          beginKeywords: 'namespace', 
          end: /[{;=]/, 
          excludeEnd: true,
          relevance: 1,
          keywords: 'namespace',
          illegal: /[:"\[\]]/,
          contains: [
            { beginKeywords: 'using' },
            hljs.UNDERSCORE_TITLE_MODE
          ]
        },
        {
          // Expression keywords prevent 'keyword Name(...)' from being
          // recognized as a function definition
          beginKeywords: 'new throw return else',
          relevance: 0
        },
        // {
        //   className: 'class',
        //   begin: 'record\\s+' + hljs.UNDERSCORE_IDENT_RE + '\\s*\\(',
        //   returnBegin: true,
        //   excludeEnd: true,
        //   end: /[{;=]/,
        //   keywords: KEYWORDS,
        //   contains: [
        //     { beginKeywords: "record" },
        //     {
        //       begin: hljs.UNDERSCORE_IDENT_RE + '\\s*\\(',
        //       returnBegin: true,
        //       relevance: 0,
        //       contains: [hljs.UNDERSCORE_TITLE_MODE]
        //     },
        //     {
        //       className: 'params',
        //       begin: /\(/, end: /\)/,
        //       keywords: KEYWORDS,
        //       relevance: 0,
        //       contains: [
        //         hljs.C_BLOCK_COMMENT_MODE
        //       ]
        //     },
        //     hljs.C_LINE_COMMENT_MODE,
        //     hljs.C_BLOCK_COMMENT_MODE
        //   ]
        // },
        // {
        //   className: 'function',
        //   begin: '(' + GENERIC_IDENT_RE + '\\s+)+' + hljs.UNDERSCORE_IDENT_RE + '\\s*\\(', returnBegin: true, end: /[{;=]/,
        //   excludeEnd: true,
        //   keywords: KEYWORDS,
        //   contains: [
        //     {
        //       begin: hljs.UNDERSCORE_IDENT_RE + '\\s*\\(', returnBegin: true,
        //       relevance: 0,
        //       contains: [hljs.UNDERSCORE_TITLE_MODE]
        //     },
        //     {
        //       className: 'params',
        //       begin: /\(/, end: /\)/,
        //       keywords: KEYWORDS,
        //       relevance: 0,
        //       contains: [
        //         ANNOTATION,
        //         hljs.APOS_STRING_MODE,
        //         hljs.QUOTE_STRING_MODE,
        //         NUMBER,
        //         hljs.C_BLOCK_COMMENT_MODE
        //       ]
        //     },
        //     hljs.C_LINE_COMMENT_MODE,
        //     hljs.C_BLOCK_COMMENT_MODE
        //   ]
        // },
        NUMBER,
        ANNOTATION
      ]
    };
  }

  return kactors;

  return module.exports.definer || module.exports;

}());

hljs.registerLanguage('json', function () {
  'use strict';

  /*
  Language: JSON
  Description: JSON (JavaScript Object Notation) is a lightweight data-interchange format.
  Author: Ivan Sagalaev <maniac@softwaremaniacs.org>
  Website: http://www.json.org
  Category: common, protocols
  */

  function json(hljs) {
    const LITERALS = {
      literal: 'true false null'
    };
    const ALLOWED_COMMENTS = [
      hljs.C_LINE_COMMENT_MODE,
      hljs.C_BLOCK_COMMENT_MODE
    ];
    const TYPES = [
      hljs.QUOTE_STRING_MODE,
      hljs.C_NUMBER_MODE
    ];
    const VALUE_CONTAINER = {
      end: ',',
      endsWithParent: true,
      excludeEnd: true,
      contains: TYPES,
      keywords: LITERALS
    };
    const OBJECT = {
      begin: /\{/,
      end: /\}/,
      contains: [
        {
          className: 'attr',
          begin: /"/,
          end: /"/,
          contains: [hljs.BACKSLASH_ESCAPE],
          illegal: '\\n'
        },
        hljs.inherit(VALUE_CONTAINER, {
          begin: /:/
        })
      ].concat(ALLOWED_COMMENTS),
      illegal: '\\S'
    };
    const ARRAY = {
      begin: '\\[',
      end: '\\]',
      contains: [hljs.inherit(VALUE_CONTAINER)], // inherit is a workaround for a bug that makes shared modes with endsWithParent compile only the ending of one of the parents
      illegal: '\\S'
    };
    TYPES.push(OBJECT, ARRAY);
    ALLOWED_COMMENTS.forEach(function(rule) {
      TYPES.push(rule);
    });
    return {
      name: 'JSON',
      contains: TYPES,
      keywords: LITERALS,
      illegal: '\\S'
    };
  }

  return json;

  return module.exports.definer || module.exports;

}());
