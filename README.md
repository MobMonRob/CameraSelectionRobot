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
> 
>  https://www.youtube.com/watch?v=jVjg2WOO6-8&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=2

### Developer Mode
This is a characteristic you must enable on the Windows System of the Server Machine with the Vicon Tracker.

#### Steps
1. On your Windows Search bar type: "Developer setting" and hit enter.
2. Enable the developer mode.

## Sockets Configuration

### Environment Configuration
- The server machine has to have firewall off or allow incoming messages. The computer of the lab normally has the firewall off.
- The client machine has to be configured to the Vicon Network.
1. Plug in the Ethernet cable into the client machine
2. Go to Control Panel
3. Go to Networks and Internet
4. Click on the first option, Networks and shared resources
5. On the Ethernet section, click on the network. That will open a new window.
6. On the new window click on Properties. That will open another Window.
7. On the new window, leave everything disabled but Internet Protocol Version 4 (TCP/IP v4). That is the only one that should be enabled.
8. Then click on Internet Protocol Version 4 (TCP/IP v4). That will open another window.
9. On that window click on use the following address.
10. Put as IP address 192.168.10.x, where x is any number not used yet in the network, 193 for example. For mask put 255.255.255.0.
11. Then click Ok on all the windows to close them and save the changes.

   To make sure everything is working correctly, you can type the following command on the terminal of the client.
   ```
   ping 192.168.10.1
   ```

### Code Configuration
The current code already has the proper settings, but in case you want to modify something, make sure that:
- When creating the socket, the code in the client computer points to the address of the server computer. In this case 192.168.10.1. For example:
  ```Java
  String remoteMachineAddress = "192.168.10.1";  // Dr. Oliver´s laptop in the lab ip address
  String completeAddress = "tcp://" + remoteMachineAddress + ":5555";
  ```
- Use * for ip address when creating the sockets in the server machine. This is to ensure that it will listen to incoming petitions. For example:
  ```Java
  server.bind("tcp://*:5555");
  ```
- Make sure that both computers use the same port. Currently they use the port 5555.

## How to run

### Server
Run Server.jar
Wait 30 seconds
Do not touch it

### Client
Run client.jar
After 30 seconds and server must be on 

## Used tools (Libraries and Dependencies)

### JeroMQ
This is a dependency for the socktes. It allows client-server model. **Important:** It forces to send a request and get a response. For example, without a previous request the server cannot send a message to the client. 
> More information at:
> https://github.com/zeromq/jeromq/tree/master

#### Important Methods
```Java
try(ZContext context = new ZContext())
ZMQ.Socket socket = context.createSocket(SocketType.REQ);
socket.connect(otherMachineAddress);

socket.send(Data, 0);
socket.recv();
```
* You may need more methods, those are just the most common ones.

#### Dependency Setup
**Maven**
```xml
    <dependency>
      <groupId>org.zeromq</groupId>
      <artifactId>jeromq</artifactId>
      <version>0.5.3</version>
    </dependency>
```

### WinAppDriver
This is a dependency for GUI automation. Windows version of Selenium. Works with the IDs of accesibility. 
> More information at:
> 
> https://youtube.com/playlist?list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&si=4cprlRQje5FBwzh9

#### Important Methods
```Java
// Creation
Desktop desktop = Desktop.getDesktop();
desktop.open(new File("file path"));
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("app", "Root");
// URL is deprecated but must be used because it is the one supported by WindowsDriver
winDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);

// find methods
winDriver.findElementByName("something");
winDriver.findElementByXPath("something");

//
findBySomething().click();
```

#### Dependency Setup
**Maven**

2 dependencies are necessary

*Selenium*
```xml
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>3.141.59</version>
    </dependency>
```

*Appium*
```xml
    <dependency>
      <groupId>io.appium</groupId>
      <artifactId>java-client</artifactId>
      <version>7.4.1</version>
    </dependency>
```

> More information at minute 2:00 of:
>
> https://www.youtube.com/watch?v=165imUZ0098&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=3

### Java Robot
This library is to move the mouse with code. It is a JDK library.

#### Important Methods
```Java
robot.mouseMove(x, y);
robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
robot.mouseWheel(java);
```

> More information in:
>
> https://docs.oracle.com/javase/8/docs/api/java/awt/Robot.html


## Recommended Tools for Developers
To use the WinAppDriver methods, you need to know the automation ID, name, XPath or another identifier of the GUI elements you want to interact with. These are recommended tools for that.

### Inspect
This tool gives you the name, automation ID, group and other details of the element under the mouse in that specific moment.

#### Download
You can donwload from: https://developer.microsoft.com/en-us/windows/downloads/windows-sdk/
1. Click on *Download Installer*
2. Once downloaded, execute the Wizard
3. In the Wizard. For the step of *Select the features you want to download*, disable the options:
   - Windows Performance Toolkit
   - Debugging Tools for Windows
   - .NET Framework 4.8 Software Development Kit
   - Windows App Certification Kit
   - Windows IP Over USB
   - MSI Tools
     
   -- For all the other steps just click next with all the default settings until the installation is finished.

> More information of the download in minute 6:45 of:
>
> https://www.youtube.com/watch?v=IlkPsJiGUYA&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=2

#### Use
1. Open the inspect.exe. It should be in: "C:\Program Files (x86)\Windows Kits\10\bin\version\x64. (Where version is the latest you find in the folder, something like 10.0.19041.0)

### UI Recorder
This tool allows you to record your actions in a period of time (started and paused by you). It generates the XPath for each element the mouse interacted with during tht period.
#### Download
This tool can be downloaded from: https://github.com/Microsoft/WinAppDriver/releases
1. Go to **WinAppDriver UI Recorder v1.1**
2. Go to Assets and download WinAppDriverUIRecorder.zip
3. Go to the zip in your computer and extract it
4. Open the Wizard and just click next to install it on your computer.
> More information about the download in minute 12:05 of:
>
> https://www.youtube.com/watch?v=IlkPsJiGUYA&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=2

#### Use
1. Go to folder of installation you selected in the wizard
2. Open UI Recorder executable
3. Once the program opens, click on Record and do all the actions you need to record
4. When you have done all of the actions you had to record, click on Pause in the UI Recorder
5. Over the button clear, you will find a display menu. Click on that the see all the actions you recorded
6. Select the desired action. On the top pane you can see the XPath and copy it to clipboard
> More information at:
>
> https://www.youtube.com/watch?v=86n3f3CN2h0&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=8

## Tried but discarded tools

### SikuliX

### OpenCV

### Pure Selenium


## Encountered Problems

### Hardware too slow

### Image recognition too slow and unreliable

### The main class is not found

### Dependencies not loaded

### Java version not compatible 

### Firewall popping up wit .jar

## Possible problems

### The names of the cameras change

### Lack of exception Handling

### Vicon Tracker GUI not on full screen or another GUI in front

### Resolution of the screen chages and the oofsets does not match

### Someone uses the mouse at the same time

### Not able to connect with the WinAppDriver Server

### Connection lost between the client machine and the servef machine

### Port 5555 is already used

### The Vicon Tracker gets unplugged


