# UPDATING THE VERSION NUMBER

- mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=x.y.z-SNAPSHOT
- (optional) change the versions in those pom.xml not listed as current modules (those won't have a modification tag)
- change the CURRENT string in Version.java
- change the k.Modeler splash image:
	- template in different formats are stored in `org.integratedmodelling.klab.ide/branding`
	- format available are `psd` (Photoshop), `xcf` (Gimp), `svg` (Vectorial, the version is text, simpler to modify)
	- change number and export file as `.png` and`.bmp` in `org.integratedmodelling.klab.ide` project root
	- branding is managed in `org.integratedmodelling.klab.ide.repository/klab.product`
 