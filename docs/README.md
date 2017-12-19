Before running make to generate documentation:

# Ensure you have python and make installed (on windows use chocolatey for make, but better to install phython from the official distribution, as the path does not need to be fixed then);

# Ensure that sphinx 1.6+ is installed 
	> pip install sphinx

# Install the ReadTheDocs theme (see https://github.com/rtfd/sphinx_rtd_theme) using
	> pip install sphinx_rtd_theme

# Install the Sphinx http domains for REST documentation (see https://pythonhosted.org/sphinxcontrib-httpdomain) using
    >  pip install sphinxcontrib.httpdomain
    >  pip install sphinxcontrib.httpexample

# Install the Sphinx Java domain for Java documentation (see http://bronto-javasphinx.readthedocs.io/en/latest/#) using
    >  pip install install javasphinx

Then you can run sphinx as usual, e.g.:

	> make html 
	
The etc/ directory contains a k.IM syntax lexer for Pygments which is automatically installed in the conf.py configuration file.

