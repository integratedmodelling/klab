@echo off

set JAVA=%userprofile%\.klab\jre16\bin\java
set KLAB_RELEASE=develop
set KLAB_VERSION=0.11.0-SNAPSHOT
set CLI_PATH=%userprofile%\.klab\releases\%KLAB_RELEASE%\cli
set CLI_PRODUCT=%CLI_PATH%\cli-%KLAB_VERSION%.jar

IF NOT "%1"=="" (
    IF "%1"=="-dev" (
	    echo TODO ADD DEBUG OPTION
	    echo TODO FIND CODE IN GIT AND REDIRECT CLI_PATH
        SHIFT
    )
)

%JAVA% -cp "%CLI_PATH%\lib\*;%CLI_PRODUCT%" -Xmx4096M org.integratedmodelling.klab.k.Main - "$@"
@echo on