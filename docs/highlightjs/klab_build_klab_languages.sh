# build only the languages used in the klab documentation
# 
# At the moment these are:
# java
# kim
#
# TODO add kactors


node tools/build.js -n java kim

# then rename and move the files necessary to compile asciidoctor 
# documents within the customized Antora UI

cp build/highlight.js ../ui/src/js/vendor/highlight.bundle.js
cp build/styles/klab.min.css ../ui/src/css/klab.css
#mv build/styles/github.css build/styles/github.min.css
#cp klab.min.css build/styles/
