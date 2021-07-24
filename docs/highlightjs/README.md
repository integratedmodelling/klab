This package contains the original distribution of https://github.com/highlightjs[highlight.js] 
plus templates and support files to add the klab and kactors language highlighters. 
The final destination is a highlight.js file to be included in ../ui/src/js/vendor and 
a matching klab.css style sheet.

The grammars are generated from templates that must pass through the engine to add all
concepts and keywords for both languages. For this reason **the engine must be on** when the script is invoked.

After building the necessary support with `npm install` and starting the engine, call the `build_all.sh` script
to create the grammars in src/languages and the highlight.js in build/. If you need more languages than
java, json, kim and kactors add them in the script at the node.js call.

After everything has worked, the ../ui package will contain the updated highlight support and you can proceed
to recreate the UI bundle (`cd ../ui; gulp bundle`) and the site (`cd ../site; antora antora-playbook.yml').

