define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "E|abstract|acceleration|according|adjacent|affects|agent|aggregated|amount|and|angle|any|applies|area|as|assess|assessment|at|attribute|authority|away|between|bidirectional|boolean|by|causant|caused|causing|change|charge|children|class|classification|classified|classifies|compresent|confers|configuration|consists|constituent|contained|container|containing|contains|context|core|count|covering|creates|date|decreases|defines|definition|deliberative|deniable|describes|discretization|discretized|discretizes|disjoint|distance|do|documentation|domain|double|down|duration|e|each|energy|entropy|equals|event|exactly|exclusive|exposes|exposing|extends|extent|false|float|follows|for|from|functional|has|identified|identity|if|implies|imports|in|inclusive|increases|inherent|inheriting|inherits|initialization|instantiation|integer|integrate|interactive|into|inverse|is|learn|least|length|line|links|lookup|marks|mass|metadata|model|money|most|move|named|namespace|no|not|nothing|number|observability|observe|observing|occurrence|of|on|only|optional|or|ordering|otherwise|outside|over|part|per|point|polygon|presence|pressure|priority|private|probability|process|proportion|purpose|quality|quantity|ratio|reactive|realm|related|relationship|required|requires|resistance|resistivity|resolution|resolve|role|root|scenario|set|structural|subjective|table|temperature|termination|text|thing|to|transition|true|type|uncertainty|unidirectional|unknown|unless|uses|using|value|velocity|version|viscosity|void|volume|weight|with|within|worldview";
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
	Mode.prototype.$id = "xtext/kim";
	Mode.prototype.getCompletions = function(state, session, pos, prefix) {
		return [];
	}
	
	return {
		Mode: Mode
	};
});
