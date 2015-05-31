@echo off
@title Compiler
@echo Compiling
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/player/content/clans/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/worldlist/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/npc/combat/impl/ThunderCombat.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/player/Equipment.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/Launcher.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/player/dialogues/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/player/actions/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/npc/dungeonnering/Blink.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/minigames/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/player/content/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/net/decoders/handlers/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/game/player/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/tools/*.java
"C:\Program Files (x86)\Java\jdk1.8.0_45\bin\javac.exe" -d bin -cp data/libs/*; -sourcepath src src/com/rs/utils/*.java
@echo Finished Compiling
pause