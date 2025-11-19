@Echo Off
chcp 1251>nul
Set JAVA_HOME="%ProgramFiles%\Java\jdk-21"
Set JRE_HOME=%JAVA_HOME%
Set PATH=%JRE_HOME%\bin;%PATH%

Echo ************
Echo Просмотр таблиц БД в консоли
Echo ************
Echo.

cd /D "%~dp0"
java -cp "./dbcli.jar;ext_libs/*;ext_libs/ini4j-0.5.4/*" main.startIt %*
