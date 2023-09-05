# CameraSelectionRobot

CameraSelectionRobot is a program that allows to automatically enable and disable cameras on the Vicon Tracker GUI.

## Necessary external resources and configurations
WinAppDriver Server
Developer Mode

# Sockets Configuration
Server has to have firewall off or allow incoming messages
Client has to be configured to the  Vicon Network (out of the code)
Use * for ip address in the server
Both computers use port 5555


## How to run

# Server
Run Server.jar
Wait 30 seconds
Do not touch it

### Client
Run client.jar
After 30 seconds and server must be on 

## Used tools
JeroMQ
WinAppDriver
Java Robot

## Dependencies Setup
WinAppDriver dependencies
JeroMQ depedencies


## Recommended Tools for Developers
UI Recorder
Inspect

# Possible problems
The names of the cameras change
Lack of exception Handling
Vicon Tracker GUI not on full screen or another GUI in front
Resolution of the screen chages and the oofsets does not match
Someone uses the mouse at the same time
Not able to connect with the WinAppDriver Server
Connection lost between the client machine and the servef machine
Port 5555 is already used
The Vicon Tracker gets unplugged

## Tried but discarded tools
SikuliX
JeroMQ
Pure Selenium


