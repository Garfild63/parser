@echo off
set /p name=
"C:\Program Files\Java\jdk-15.0.2\bin\javac" Parser.java
java Parser %name%
pause
