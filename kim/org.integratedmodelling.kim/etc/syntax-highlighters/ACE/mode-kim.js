define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "abstract|acceleration|according|additional|affects|agent|aggregated|all|and|angle|annotation|applies|area|as|assess|assessment|at|attribute|authority|away|between|bidirectional|boolean|by|change|charge|children|class|classify|concept|confers|configuration|constituent|contains|context|contextualize|count|covering|creates|date|define|defines|definition|deliberative|deniable|describes|discretize|discretized|disjoint|distance|do|documentation|domain|double|down|duration|each|energy|entropy|equals|event|every|exactly|exclusive|exports|exposes|exposing|extends|extent|false|float|for|from|functional|has|identified|identity|if|implies|in|inclusive|inheriting|inherits|initialization|instantiation|integer|integrate|interactive|interpret|into|inverse|is|learn|least|length|line|links|lookup|mass|measure|metadata|model|money|most|move|named|namespace|no|not|nothing|objective|observe|observing|occurrence|of|on|only|optional|or|ordering|otherwise|outside|over|parent|part|per|percentage|point|polygon|presence|pressure|priority|private|probability|process|proportion|quality|quantity|range|rank|ratio|reactive|realm|related|relationship|required|requires|resistance|resistivity|resolution|resolve|role|root|scenario|set|source|structural|subjective|table|target|temperature|termination|text|thing|to|true|type|uncertainty|unidirectional|unknown|unless|uses|using|value|velocity|version|viscosity|void|volume|weight|when|where|with|within|worldview";
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
