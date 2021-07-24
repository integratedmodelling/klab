set -x

HIGHLIGHTDIR=`pwd`/build/
cp ../../src/main/asciidoc/org/integratedmodelling/documentation/technote/technote.adoc .
asciidoctor -a highlightjsdir=$HIGHLIGHTDIR --base-dir . technote.adoc

