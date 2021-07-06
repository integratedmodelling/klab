# Generating documentation

Packages: 

* Asciidoc sources are in src/main/asciidoc. Those used to build the Antora site are in 
  org.integratedmodelling.documentation.components. Everything else is legacy or in 
  development, eventually to be moved into Antora components. Antora's file organization 
  is complex. The technical note is in components/technote/modules/ROOT/pages/index.adoc.
* The ui/ directory contains the UI package sources that Antora needs to build the site. 
  When Antora runs, it uses a zip bundle built from within ui/ by calling `gulp bundle`.
* The site/ directory contains the Antora playbook which refers to code in src/ and 
  to the bundle built from ui/. It builds a site within site/build/site. The last ui-bundle.zip 
  is committed and available.
* The highlightjs/ directory contains the (basic) port of highlightjs that supports 
  kim. Scripts are provided to build the final highlight.js in build/; the minimal running 
  test.html can be used to check that the highlighting works. Only highlight.js and 
  kim.min.css are needed, plus a small script to apply the highlighting in each page.
  
## Prerequisites
0. Install node.js/npm to manage everything.
1. Install Antora as per https://docs.antora.org/antora/latest/install/install-antora. 
   Using v.3.0.0alpha (the stable 2.3 version is probably OK).
2. Install gulp + gulp-cli (using npm or yarn) to manage the UI bundle.

## Building the site

1. If changes to the ui package are made, cd into ui/ and run `gulp bundle`.
2. Cd into site/ and run `antora antora-playbook.yml`. 

The site is in site/build/site.

# Problems

The ui/ package inserts highlight.js in the vendor scripts and adds kim.min.css to the 
final assembled site.css. All these are in ui/src/partials, including the final script 
to apply the highlighting.

The use of `gulp-uglify` in gulp.d/build.js had to be switched to gulp-uglify-es to 
correctly handle highlight.js, and a much more permissive lint configuration was required 
to handle it. Eventually I also commented out the pipe(uglify()) calls so that the code 
remains readable (with no change functionally).
