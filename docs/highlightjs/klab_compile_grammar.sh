# start engine before running
# at this point it seems safe to add > src/languages/kim.js at the end
cat templates/kim_template.js | curl --data-binary @- http://127.0.0.1:8283/modeler/kim/template --header "Content-Type: text/plain" 