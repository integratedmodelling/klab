set -x


HIGHLIGHTDIR=`pwd`/build/
asciidoctor -a highlightjsdir=$HIGHLIGHTDIR technote.adoc

