title Client Controls

@echo off
SET JAVA_HOME=C:\Program Files (x86)\Java\jre6

if exist "%JAVA_HOME%" goto java_home_exists
set JAVA_RUN=java
goto run

:java_home_exists
set JAVA_RUN="%JAVA_HOME%/bin/java"

:run
SET JAVA_OPTS=-Xms128m -Xmx512m

%JAVA_RUN% %JAVA_OPTS% -jar  -Dlog4j.configuration=file:"./etc/log4j.properties"  Controls-1.0-SNAPSHOT.jar CLIENT   