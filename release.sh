#!/bin/bash
set -ev

./gradlew clean
./gradlew assemble
./gradlew bintrayUpload -PdryRun=true

./gradlew :common:bintrayUpload -PdryRun=false
./gradlew :networkquality:bintrayUpload -PdryRun=false
./gradlew :logs:bintrayUpload -PdryRun=false
./gradlew :preferences:bintrayUpload -PdryRun=false
./gradlew :layer:bintrayUpload -PdryRun=false
