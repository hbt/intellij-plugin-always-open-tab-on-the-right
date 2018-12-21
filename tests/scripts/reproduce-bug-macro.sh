#!/bin/bash
# script execute when pressing the mouse right click on keyboard button

cp /home/hassen/IdeaProjects/untitled1/workspace.xml /home/hassen/IdeaProjects/untitled1/.idea/workspace.xml

sleep 0.3

/home/hassen/config/scripts/private/bin/mouse-right.sh



# remove all tabs
xdotool keydown alt key t
xdotool keyup alt
sleep 0.5
xdotool key a

sleep 0.3

# open t2
xdotool keydown alt key o
xdotool keyup alt
sleep 0.3
xdotool key t
xdotool key 2
sleep 0.5
xdotool key Return


sleep 0.3

# open t1
xdotool key g
xdotool key g
xdotool key 0
xdotool key f
xdotool key 1
xdotool key g
xdotool key d

sleep 0.3

# back to t2
xdotool key ctrl+j

sleep 0.3

# open t3
xdotool key g
xdotool key g
xdotool key 0
xdotool key j
xdotool key f
xdotool key 3
xdotool key g
xdotool key d

sleep 0.3

# back to t2
xdotool key ctrl+j

sleep 0.3

# close t2
xdotool key t
xdotool key w

sleep 0.3



# reopen t2 -- bug is: it reopens on the left rather than the right
xdotool keydown alt key o
xdotool keyup alt
sleep 0.3
xdotool key t
xdotool key 2
sleep 0.5
xdotool key Return
