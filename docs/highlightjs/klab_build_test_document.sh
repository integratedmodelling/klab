set -x


HIGHLIGHTDIR=`pwd`/build/
asciidoctor -a highlightjsdir=$HIGHLIGHTDIR test_kim_language.adoc
