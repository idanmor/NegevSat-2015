@ECHO OFF

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto END

"%JAVA_HOME%\bin\java.exe" -jar proxy.jar -com COM1 -inip 10.0.0.7 -inport 9999 -outip 10.0.0.7 -outport 8888
goto END


:noJavaHome
@echo please set up JAVA_HOME environment variable - java should be version 8 and 32bit 
goto END

:END

