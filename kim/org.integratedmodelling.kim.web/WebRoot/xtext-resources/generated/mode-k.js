define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "AD|BC|CE|E|abstract|acceleration|according|adjacent|affects|agent|aggregated|all|amount|and|angle|any|applies|area|as|at|attribute|authority|averaged|away|between|bond|boolean|by|causant|caused|causing|change|changed|charge|children|class|classified|classifies|column|compresent|confers|configuration|contained|container|containing|contains|context|cooccurrent|core|count|covering|creates|date|decreases|define|defines|definition|deliberative|deniable|describes|discretized|discretizes|disjoint|distance|do|domain|down|duration|during|e|each|emerges|energy|entropy|equals|event|exactly|exclusive|extends|extent|false|finally|float|follows|for|from|functional|has|identified|identity|if|implies|imports|in|inclusive|increases|inherent|inherits|initialization|instantiation|integer|integrate|interactive|into|is|l|language|learn|least|length|level|linking|links|lookup|magnitude|marks|mass|match|metadata|minus|model|monetary|money|more|most|move|named|namespace|no|not|nothing|number|object|observe|observing|occurrence|of|on|only|optional|or|ordering|otherwise|outside|over|parameters|per|percentage|plus|presence|pressure|priority|private|probability|process|project|proportion|purpose|quality|quantity|rate|ratio|reactive|realm|related|relationship|required|requires|rescaling|resistance|resistivity|resolve|role|root|row|scenario|set|structural|subjective|summed|targeting|temperature|termination|text|then|thing|times|to|total|transition|true|type|uncertainty|unknown|unless|uses|using|value|velocity|version|viscosity|void|volume|weight|where|with|within|without|worldview";
		this.$rules = {
			"start": [
				{token: "comment", regex: "\\/\\/.*$"},
				{token: "comment", regex: "\\/\\*", next : "comment"},
				{token: "string", regex: '["](?:(?:\\\\.)|(?:[^"\\\\]))*?["]'},
				{token: "string", regex: "['](?:(?:\\\\.)|(?:[^'\\\\]))*?[']"},
				{token: "constant.numeric", regex: "[+-]?\\d+(?:(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)?\\b"},
				{token: "lparen", regex: "[({]"},
				{token: "rparen", regex: "[)}]"},
				{token: "keyword", regex: "\\b(?:" + keywords + ")\\b"}
			],
			"comment": [
				{token: "comment", regex: ".*?\\*\\/", next : "start"},
				{token: "comment", regex: ".+"}
			]
		};
	};
	oop.inherits(HighlightRules, mTextHighlightRules.TextHighlightRules);
	
	var Mode = function() {
		this.HighlightRules = HighlightRules;
	};
	oop.inherits(Mode, mText.Mode);
	Mode.prototype.$id = "xtext/k";
	Mode.prototype.getCompletions = function(state, session, pos, prefix) {
		return [];
	}
	
	return {
		Mode: Mode
	};
});
