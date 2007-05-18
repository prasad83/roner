#!/bin/sh

export JAVA_HOME=/home/java

${JAVA_HOME}/bin/java -classpath ../dist/roner.jar web20.roner.console.RoundedCorner $*