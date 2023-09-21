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
This section explains how to run both programs in both computers, the server computer and the client computer. The necessary jars are added in this repository. Those are CameraSelectionAPP.jar and CameraSelectionAPI.jar.

### Server Machine in the lab
This uses CameraSelectionAPI.jar.

1. **Run CameraSelectionAPI.jar:** Open the terminal and type the following command.

   ```
   java -jar locationCameraSelectionAPI.jar
   ```
   -- You can drag and drop the file into the the terminal to get the location
2. **Wait 30 seconds:** Just with the previous step. The program is launched. But it is recommended to wait 30 seconds before sending request to make sure the Vicon Tracker had time to load.
3. **Do not touch it:** Once the software is running, do not alter it. Avoid using the mouse or the keyboard meanwhile it is running to avoid interferences. If you need to interact with the software, do it from the client.

### Client Remote machine
This is the program you will use as user. This uses CameraSelectionAPP.jar.

1. **Run CameraSelectionAPI.jar:** Open the terminal and type the following command.

   ```
   java -jar locationCameraSelectionAPP.jar
   ```
   -- You can drag and drop the file into the the terminal to get the location
2. **Wait 30 seconds:** After the previous step, it should open the main menu in the terminal. It is recommended to send the first request 30 seconds after the server program has started.
- **Enable/Disable a camera:** Send the number of camera you want to interact with (1 to 13). Then send 0 to disable that camera or 1 to enable it. After this, it will show you a success mesaage and take you again to the main menu.
- **Check the status of all the cameras:** Send 14. Notice that this process will take a while. After it is completed, it will print a list with all the cameras and its status. Then it will take you back to the main menu.
- **Close the program**: Send 0. It will print a closing message and finish the execution.

During all the execution of the program, please read carefully all the instructions given in the terminal. 
  

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
This section is about tools that were used during the development of the project, but were replaced because they did not give satisfactory results or its implementation produced errors.

### SikuliX
SikuliX is a tool of image recognition. It was tried to detect elements on the screen, to be able to move the mouse to the correct position. It was discarded because it is extremely slow. Normally it took over 40 seconds just to locare a single element. And since it compares images, it is prone to errors like confusing 1 with 11.

### OpenCV
OpenCV is another tool of computer vision that was suggested to replace SikuliX. It is supposed to be much more capable and faster than SikuliX. However, this tool was not used because for Java it comes as a library. This project is built with Maven, so it uses dependencies, not libraries. Since OpenCV was a library and not a dependecy the integration to the project generated many conflicts. Besides, it is also image recognition, so it is still prone to confuse elements.

### Pure Selenium
Selenium is a verz popular automation tool that works with IDs. However, Selenium works for web browser to automate websites, not desktop apps like Vicon Tracker. The sholution was to use WinApp Driver, which is the desktop version of Selenium.


## Encountered Problems
This section is about the problems that occured during the development of the project, and more important, how those were solved. In case a similar problem appears in the future. 

### Performance of the lab laptop
This is not a problem of the program, but of the environment, but still has to be taken into account. When the Vicon Tracker is running, the computer gets very slow because that program consumes a lot of resources. It not only affects this program. As long as the Vicon Tracker is running, the whole system gets slow. For example, even with this CameraSelection software not running, apps like NetBeans and Edge got extremely slow when the Vicon Tracker was also running. So, this problem is out of the scope of this project. The only recommendation is to have patience.

### Image recognition too slow and unreliable
The first approach tried to find elements on the screen was image recognition. It was applied with SikuliX. But it was discarded because it was very slow as stated in the previous sections. Also it is not very accurate. The solution was to switch to another approach. Now the program works with automation IDs, names and XPaths, which is faster and more precise.

### The main class is not found
When generating the .jar files in NetBeans with Clean and Build, none of the generated .jar files (the one of the server or the one of the client) could not find a main class. To solve that, in NetBeans go to the project and right click on it. Go to Properties->Run and there select the main class.
You also have to add the following plugin the the pom file.
```xml
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>packageMainClass.MainClassName</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
- Remember there to specify also the name of the main class including its package.

### Dependencies not loaded
When generating the .jar files in NetBeans with Clean and Build, none of the generated .jar files (the one of the server or the one of the client) could not find a main class. Even clicking on Build with Dependencies was not useful. To solve this, only use in the pom file the exact same plugin of the previous point.

### Java version not compatible 
The computer of the lab runs with Java Runtime version 61.0. However, that version is already a bit old. So a .jar generated with a newer version of Java will not work in the computer of the lab. So, make sure that the .jar that ruins on the Server machine in the lab is built in the same coputer or a computer with Java version 61.0 or a bit older. With build. I do not only mean to click Build with dependenies in the computer of the lab. You have to create a complete new project in the computer of the lab, copy paste the code and Build with dependencies. If you bring the project from anotjer computer, it will keep the version of the other computer, even though you are building in the computer of the lab.

### Firewall Pop up window
When this program starts, everything is alright. But as soon as the server machine receives the fist request, it awakes the Firewall. The firewall sets a pop up window. This freezes the whole computer until the pop up is closed. To solve that, the fisrt request also activates a method to click on Accept on that pop up window.

## Possible problems
This section describes problems that may appear during the execution of the program but have not been solved yet in the code. Since they have not been solved yet, a solution has to be implemented eventually. Also a possible solution is giving. This is just proposed and is not implemented yet. Since it is only a sugestion, feel free to follow another approach if you consider it better.

### The names of the cameras change
This software looks for a specific name or automation ID. For example, "#1 5 (Vero v2.2)", "#2 6 (Vero v2.2)" Just changing 1 single character is enough to produce an error. You should be careful to not change the names of the cameras in the Vicon Tracker Software in the server computer.

#### Possible solution
Add a try-catch to handle when the cameras namer are not found. The catch clause can handle when the names are not found instead of just crashing. It can show I message like: "Cameras names not found. Cameras should have the following names: "#1 5 (Vero v2.2)", "#2 6 (Vero v2.2)", ..., "#13 12 (Vero v2.2)". So the human user knows the cameras names are producing conflict and be able to set the Vicon Tracker to the correct names.

### Lack of exception Handling
In general, there is almost no try-catch clauses at all. So, whenever anything happens that does not follow the planned path, it may produce an error that crashes the program, instead of smoothly handling the execution. For example, if the WinAppController class crashes, the Tracker controller has no way to know it and it will only crash too. Or if the server crashes, the client does not receive any message so it will just eternally try to unsuccessfully connect with the server *(which is not running anymore).

#### Possible solution
In several points of the code, add the necessary try-catch exceptions to recover from the error. Or at least, inform the user about the occured problem and continue with the execution instead of crashing.

### Vicon Tracker GUI not on full screen or another GUI in front
In order for the program to work properly, the Vicon Tracke GUI has to be completely visible and with the same resolution always. If this is not met, some elements that need to be modified, may not be visible.

#### Possible solution
Add a script to close all the applications in the computer except this program, the WinApp Driver and the Vicon Tracker (necessary programs for this tasks). To avoid any interference from another "innecessary" program. 

### Resolution of the screen chages and the offsets does not match
In order for the program to work properly, the Vicon Tracke GUI has to have the same resolution always. If this is not met, some elements that need to be modified, may not be visible or the mouse offsets may not work properly.

#### Possible solution
A script to click on the maximize button of the Vicon Tracker GUI can be added to meet the resolution criteria, full-screen.

### Someone uses the mouse at the same time
This program moves the mouse, but it does not block this resource to other services. This means the human user or another program can try to use the mouse at the same time and lead to conflicts.

#### Possible solution
Block the use of the mouse during the execution. Another more gentle and recommendable approach is to send a message that the mouse is being share to ask the human user to stop using it or close other programs. And pause the esecution of this program until the mouse is not being shared anymore.


### Not able to connect with the WinAppDriver Server
Sometimes, the WinApp driver server fails to receive the request. This is extremely unlikely to happens but the chances are not 0. If this happens, the program can not work because it lacks all the automation sevices.

#### Possible solution
Find a message in the WinApp Driver of unsuccessful connection. Add a script to stop the current WinApp Driver instance and create a coonect to a new one, whenever that error message appears.

### Connection lost between the client machine and the server machine
If the connection is lost the program cannot continue because the human user cannot send instructions to the server machine in the lab. This may happen because the cable is loose, or the server crashed.

#### Possible solution
For every request message the client sends, it expects a response from the server. A timeout can be added. If after a given amount of seconds the client does not receive a response, the client print to the human user that the connection with the server was lost, to check factors like the cable connection or if the server is still running.

### Port 5555 is already used
The port 5555 is the default port in this code. If it is already used by anothe application in one or both of the machines, it will lead to conflicts.

#### Possible solution
Whenever a port conflict is detected (either in the server or client machines), add a script to set another random port in both machines and try again until both machines have no problems.

### The Vicon Tracker cable gets unplugged
If the Vicon Tracker cable is not plugged into the hardware the Vicon Tracker Software cannot do anything and therefore this code neither. It is quite obvious that before starting a experiment, it has to be ensured that the hardware is ready to use. But still, it is good to have this case into account.

#### Possible solution
In the Tracker class, add a script to ensure the calbe is plugged in. Before starting to receive requests from the client, check in the Vicon Software GUI if the element with the name "Connected" is there. If not, send a message to the human user to go to plug in the cable and do not start the menu until the cable is pluggged in.

