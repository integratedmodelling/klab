@echo off

set JAVA=%userprofile%\.klab\jre16\bin\java
set KLAB_RELEASE=develop
set KLAB_VERSION=0.11.0-SNAPSHOT
set CLI_PATH=%userprofile%\.klab\releases\%KLAB_RELEASE%\cli
set CLI_PRODUCT=%CLI_PATH%\cli-%KLAB_VERSION%.jar
set JAVA_OPTS=-Xmx4096M

IF NOT "%1"=="" (
    IF "%1"=="-dev" (
    	set GIT_DIR %userprofile%\git
    	IF EXIST "%GIT_DIR%\klab\cli\target\NUL" (
    		echo "Using distribution from local source tree in %GIT_DIR%"
			set JAVA_OPTS="%JAVA_OPTS% -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000"
			set CLI_PATH=%GIT_DIR%\klab\cli\target
		)
        SHIFT
    )
)

%JAVA% -cp "%CLI_PATH%\lib\*;%CLI_PRODUCT%" %JAVA_OPTS% org.integratedmodelling.klab.k.Main %1
@echo on