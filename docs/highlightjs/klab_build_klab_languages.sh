# build only the languages used in the klab documentation
# 
# At the moment these are:
# java
# kim
#
# TODO add kactors


node tools/build.js -n java kim kactors json

# then rename and move the files necessary to compile asciidoctor 
# documents within the customized Antora UI

cp build/highlight.js ../ui/src/js/vendor/highlight.js
cp templates/klab.css ../ui/src/css/klab.css

