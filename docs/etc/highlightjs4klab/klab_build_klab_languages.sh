

# build only the languages used in the klab documentation
# 
# At the moment these are:
# java
# kim
#
# later to be added kactors


node tools/build.js -n java kim

# then rename and move the files necessary to compile asciidoctor 
# documents using this custom installation

cp build/highlight.js build/highlight.min.js
mv build/demo/styles build/
#mv build/styles/github.css build/styles/github.min.css
cp klab.min.css build/styles/
