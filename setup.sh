#!/bin/bash

#PLAY_VERSION=1.3.4
PLAY_VERSION=1.5.3
wget -N https://downloads.typesafe.com/play/$PLAY_VERSION/play-$PLAY_VERSION.zip
unzip -u play-$PLAY_VERSION.zip
export PATH="./play-$PLAY_VERSION:$PATH"
