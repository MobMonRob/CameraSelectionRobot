# CameraSelectionRobot

CameraSelectionRobot is a program that allows to automatically enable and disable cameras on the Vicon Tracker GUI.

## Necessary external resources and configurations
### WinAppDriver Server
This is a program that must be downloaded, in order for CameraSelection to work. The CameraSelection program starts it and stops it automatically, so you just have to worry of having this installed on the Server Machine with the Vicon Tracker.

#### Download
1. You can download it from: https://github.com/Microsoft/WinAppDriver/releases
2. Scroll down to WinAppDriver v1.2.1 and look on the bottom for the Assets section
3. There you must download the WindowsApplicationDriver_1.2.1.msi
4. Click on the download it file to open the installation Wizard
5. Just follow the Wizard instructions, do not change any default configuration or the CameraSelection program will not work.

#### Access in your Computer
Once you have installed the WinAppDriver, you can make sure it is properly installed with the following steps (remember not to change the installation wiyard settings):
1. Go to C:\Program Files (x86)
2. Then look for a folder named Windows Application Driver
3. Inside that folder click on the executable WinAppDriver
4. It should open a terminal with the message: "Windows Application Driver listening for requests at: http://127.0.0.1:4723/"

After this you can close that terminal, it is only for testing. In summary, the location of the executable is: C:\Program Files (x86)\Windows Application Driver 
> You can also find it in video, at minute 8:30 of:
>  https://www.youtube.com/watch?v=jVjg2WOO6-8&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=2
### Developer Mode
This is a characteristic you must enable on the Windows System of the Server Machine with the Vicon Tracker.

#### Steps
1. On your Windows Search bar type: "Developer setting" and hit enter.
2. Enable the developer mode.

## Sockets Configuration

### Environment Configuration
Server has to have firewall off or allow incoming messages
Client has to be configured to the  Vicon Network (out of the code)

### Code Configuration
The current code already has the proper settings, but in case you want to modify something, make sure that:
- When creating the socket, the code in the client computer points to the address of the server computer. In this case 192.168.10.1. For example:
```java
String remoteMachineAddress = "192.168.10.1";  // Dr. Oliver´s laptop in the lab ip address
String completeAddress = "tcp://" + remoteMachineAddress + ":5555";
```
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


## Tried but discarded tools
SikuliX
JeroMQ
Pure Selenium


## Encountered Problems
Hardware too slow
Image recognition too slow and unreliable
The main class is not found
Dependencies not loaded
Java version not compatible 
Firewall popping up wit .jar

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


