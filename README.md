# INF112 Robo Rally
##  How to build and run the game
   To build this game you need java, maven and git installed
   ####  With commandline
    *   git clone https://github.com/inf112-v20/staring-horse
    *   cd staring-horse && mvn clean verify assembly:single
    *   cd target && java -jar roborally-Staring-horse-Final-jar-with-dependencies.jar

   #### Alternatives
    *   Download the source code
    *   Import a projcet in an IDE from the pom.xml file. (We used IntelliJ IDEA)
    *   Run mvn clean verify assembly:single
    *   Locate the target folder
    *   Run roborally-Staring-horse-jar-with-dependencies.jar
    *   Or run Main.java from the IDE.
    
##  Tests
   #### Manual tests
   [Wiki for manual tests](https://github.com/inf112-v20/staring-horse/wiki/Manual-Testing---RoboRally,-Staring-Horse-version) 
   #### Unit tests
    *   Unit tests are run when building the game with maven.
    *   If you want to run them again in an IDE run test/java


Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

[![Build Status](https://travis-ci.com/inf112-v20/staring-horse.svg?branch=master)](https://travis-ci.com/inf112-v20/staring-horse)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a90f767e283a4cf7b88e8bb3c344fded)](https://www.codacy.com/gh/inf112-v20/staring-horse?utm_source=github.com&utm_medium=referral&utm_content=inf112-v20/staring-horse&utm_campaign=Badge_Grade)

[Credit for Menu-UI to Raymond "Raeleus" Buckley](https://github.com/czyzby/gdx-skins/tree/master/star-soldier)
