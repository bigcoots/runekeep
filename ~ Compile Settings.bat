@echo off
@title Compiler
@echo Compiling
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/Launcher.java
@echo Finished Compiling
pause