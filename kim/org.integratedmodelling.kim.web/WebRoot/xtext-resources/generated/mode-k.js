define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "E|abstract|acceleration|according|adjacent|affects|agent|aggregated|amount|and|angle|any|applies|area|as|assessment|at|attribute|authority|away|between|bond|boolean|by|causant|caused|causing|change|charge|children|class|classified|classifies|compresent|confers|configuration|consists|constituent|contained|container|containing|contains|context|cooccurrent|core|count|covering|creates|date|decreases|define|defines|definition|deliberative|deniable|describes|discretized|discretizes|disjoint|distance|do|domain|down|duration|during|e|each|energy|entropy|equals|event|exactly|exclusive|exposes|exposing|extends|extent|false|finally|float|follows|for|from|functional|has|identified|identity|if|implies|imports|in|inclusive|increases|inherent|inheriting|inherits|initialization|instantiation|integer|integrate|interactive|into|inverse|is|l|learn|least|length|linking|links|lookup|magnitude|marks|mass|metadata|minus|model|monetary|money|most|move|named|namespace|no|not|nothing|number|object|observability|observe|observing|occurrence|of|on|only|optional|or|ordering|otherwise|outside|over|parameters|part|per|percentage|plus|presence|pressure|priority|private|probability|process|project|proportion|purpose|quality|quantity|ratio|reactive|realm|related|relationship|required|requires|rescaling|resistance|resistivity|resolution|resolve|role|root|scenario|set|structural|subjective|targeting|temperature|termination|text|then|thing|times|to|transition|true|type|uncertainty|unknown|unless|uses|using|value|velocity|version|viscosity|void|volume|weight|where|with|within|without|worldview";
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
