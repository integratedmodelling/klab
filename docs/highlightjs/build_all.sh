# start engine before running!
echo Hope you have started the engine. If not, do not commit because I am not checking, and 
echo the kim.js, kactors.js grammars in src/languages will be overwritten with garbage.
echo ---------------------------------------------------------------------------------------------------------
echo Generating kim and kactors grammars in src/languages based on templates...
cat templates/kim_template.js | curl --data-binary @- http://127.0.0.1:8283/modeler/kim/template --header "Content-Type: text/plain" > src/languages/kim.js
cat templates/kactors_template.js | curl --data-binary @- http://127.0.0.1:8283/modeler/kactors/template --header "Content-Type: text/plain" > src/languages/kactors.js

echo ---------------------------------------------------------------------------------------------------------
echo Building the highlight.js overall file with java, kim, kactors and json...
node tools/build.js -n java kim kactors json

echo ---------------------------------------------------------------------------------------------------------
echo Moving the highlight script and the style sheet into the ui package...
cp build/highlight.js ../ui/src/js/vendor/highlight.js
cp templates/klab.css ../ui/src/css/klab.css

echo ---------------------------------------------------------------------------------------------------------
echo Done. Now rebuild the bundle in ../ui and regenerate the site in ../site.

