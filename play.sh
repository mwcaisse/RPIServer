#!/bin/bash

# prevent screensaver from powering down display
setterm -blank off -powerdown off > /dev/tty0

# clear the display and turn off the flashing cursor
clear > /dev/tty0

setterm -cursor off > /dev/tty0

#You don't have to use -d it just makes it come up nicer on my old TV
omxplayer -o hdmi  "$1" > /dev/tty0

clear > /dev/tty0

# turn the cursor back on when done with omxplayer
setterm -cursor on > /dev/tty0
