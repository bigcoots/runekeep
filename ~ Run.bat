@echo off
title Runekeep
screen -A -m -d -S runekeep java -Xmx815m -classpath bin:lib/*: com.rs.Launcher true true false
pause