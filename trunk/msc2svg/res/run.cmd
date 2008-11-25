@echo off
set JAVA_HOME=
set APPLICATION_HONE=%CD%
set OLD_CLASSPATH=%CLASSPATH%

set CLASSPATH=%APPLICATION_HONE%\lib\batik-svggen.jar
set CLASSPATH=%CLASSPATH%;%APPLICATION_HONE%\lib\batik-xml.jar
set CLASSPATH=%CLASSPATH%;%APPLICATION_HONE%\lib\batik-awt-util.jar
set CLASSPATH=%CLASSPATH%;%APPLICATION_HONE%\lib\batik-dom.jar
set CLASSPATH=%CLASSPATH%;%APPLICATION_HONE%\lib\batik-ext.jar
set CLASSPATH=%CLASSPATH%;%APPLICATION_HONE%\lib\batik-util.jar
set CLASSPATH=%CLASSPATH%;%APPLICATION_HONE%\lib\javacsv.jar
set CLASSPATH=%CLASSPATH%;%APPLICATION_HONE%\lib\xalan.jar
set CLASSPATH=%CLASSPATH%;%APPLICATION_HONE%\lib\msc2svg-@@@.jar
java  com.google.code.msc2svg.Application --calsspath=%CLASSPATH%   
set CLASSPATH=%OLD_CLASSPATH%
