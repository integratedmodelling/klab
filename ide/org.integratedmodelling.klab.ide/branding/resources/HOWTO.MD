### How to brand k.Modeler

Source for this documentation: http://andrius.velykis.lt/2012/10/creating-icons-for-eclipse-rcp-launcher/

## Linux
Linux launcher file does not actually have an icon. However, a icon file (XPM) can be provided for users to customise their shortcuts.
Eclipse bundles for Linux provide an XPM icon of size 256×256. Note that the file is included in the Linux product bundle as is, so the suggested name for it is icon.xpm.
To create an XPM icon file, I used GIMP
When creating the XPM file I used a 256×256 PNG file with transparency as my source. During export, GIMP asks to indicate Threshold Alpha value to handle the partial transparency in the icon. I found that the default value of 127 gives me the best result, but try what suits you.

## Mac OS X
Mac OS X launcher requires a single ICNS file that will be part of the \<Launcher\>.app folder. The file is standard Mac OS X icon file and supports multiple icons (e.g. PNG files with transparency) of the following sizes:

16×16, 32×32, 128×128, 256×256, 512×512.

Note that the icons should come in pairs (standard and high resolution, e.g. for Retina displays). The high resolution is double the pixels of a standard one.
The instructions to produce icons are available in Apple documentation. You need to put icons named icon_\<sizeinpoints\>x\<sizeinpoints\>\[@\<scale\>\].png, e.g. icon_16x16.png, icon_16x16@2x.png, etc, into an .iconset folder. Then run iconutil tool to produce the ICNS file for Eclipse. Refer to Apple documentation for details.
Alternatively, the ICNS file can be produced using the Icon Composer app (available with developer tools), but this method is no longer recommended by Apple.

## Windows
Windows launcher is produced by replacing icons in Eclipse executable with provided ones. So if the replacement fails, the executable will contain Eclipse icons.
To use with Tycho, you need to create a multi-icon ICO file. The .product file also provides fields to specify 7 separate BMP images to use as icons. Unfortunately, I could not manage to get it to work with Tycho 0.15.0 - it seemed to only pick the first one.
The ICO file supports all resolutions outlined in the .product file, in 32-bit (RGB / Alpha Channel) and 8-bit (256 colors, indexed) BMP icons:

32-bit: 16×16, 32×32, 48×48, 256×256.
8-bit: 16×16, 24×24 (for older Eclipse versions?), 32×32, 48×48

To convert from transparent PNG to BMP files, I used GIMP. For 32-bit files, just export to BMP file. For 8-bit files, first convert to 255 colours using Image > Mode > Indexed… and select Generate optimum palette with 255 colours. Then export to BMP file with a warning about losing the transparency.
After producing all BMP files, combine them in GIMP using different layers and export as ICO file. Alternatively, use icon software such as IcoFX (15 days fully-functional trial on Windows) to produce the multi-icon ICO.
For Eclipse, you need to make sure that the 256×256 icon is uncompressed, otherwise Tycho will not use it. When you have the ICO file (or if you are producing it with GIMP), re-save it with GIMP and deselect Compressed (PNG) for all layers during export.

To create the ico file you can use the imagemagick command:

`convert onvert k-lab-icon-16.png k-lab-icon-32.png k-lab-icon-48.png  k-lab-icon-64.png k-lab-icon-256.png k-lab-icon-16.gif k-lab-icon-24.gif k-lab-icon-32.gif k-lab-icon-48.gif -compress none k-lab.ico`

## Plugin configuration
If we need to change the file names, we must know that the icons configuration is present in 2 projects: `org.integratedmodelling.klab.ide` and `org.integratedmodelling.klab.ide.repository`
In `org.integratedmodelling.klab.ide` you must change the icon file names in the `plugin.xml`
In `org.integratedmodelling.klab.ide.repository` you must change the icon file names in `klab.product`

The information is redoundant, but for now we copy the icons file in both project. More investigation in the future