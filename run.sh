#!/bin/bash
mvn -q clean compile exec:java -Dexec.mainClass=com.healthdiet.Main
