# UPDATING THE VERSION NUMBER

. mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=x.y.z-SNAPSHOT
. change klab.version in the main pom.xml and the versions in those pom.xml not listed as current modules (those won't have a modification tag)
. change the CURRENT string in Version.java
. change the k.Modeler splash page
 