# 📘 HTTP-Project-Networks-II

This project is part of the Networks II course. It involves developing a low-level HTTP server using sockets in Java. The server can handle basic HTTP requests and serve static files, including HTML, CSS, and JavaScript.

## Developed by

- [Juan Ariza Onecha](https://github.com/JuanArizaCoding)
- [Iván Royo Gutiérrez](https://github.com/ivanbisimbrero)
- [Germán Aguilar Bobadilla](https://github.com/Ger1y1)
- [Daniel Buxton Sierras](https://github.com/dbsDevelops)

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Directory Structure](#directory-structure)
- [Docker Configuration](#docker-configuration)
- [Gradle Configuration and Build](#gradle-configuration-and-build)
- [Trello Board](#trello-board)
- [Technical Report](#technical-report)
- [Teacher's Github](#teachers-github)

## Introduction

The goal of this project is to create a simple HTTP server that can handle GET requests and serve static files. It supports serving HTML files and related static resources like CSS and JavaScript files. The server can also handle directory requests and serve the `index.html` file if it exists.

## Features

-	Mandatory Features: Ensured all fundamental requirements were met.
-	Persistent Connection: Implemented to keep the connection alive between client and server.
-	Client GUI: Developed a graphical user interface for the client.
-	Multimedia Messages: Added support for multimedia message handling.
-	Static Resources: Added support for static resources such as Videogame Webpages.
-	Cookies Management: Implemented logic for managing cookies within the server.
-	Conditional GET: Improved efficiency with conditional GET requests.
-	Advanced CRUD: Enabled advanced Create, Read, Update, and Delete operations.
-	Folder System: Created a folder system for organizing HTML files.
-	Docker: Containerized the application using Docker.
-	Client CLI: Developed a command-line interface for the client.
-	TLS: Implemented Transport Layer Security for secure communications.
-	Logging: Implemented logging for tracking actions.
-	Javadocs: Implemented javadocs comment and generate field with gradle


## Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/dbsDevelops/HTTP-Project-Networks-II.git
   ```

2. Navigate to the project directory:

   ```sh
   cd HTTP-Project-Networks-II
   ```

3. Compile the project:

   ```sh
   ./gradlew build
   ```

4. Run the server:

   Use docker to run the server (previous build the image):

   ```sh
   docker run -p8080:80 -p4443:443 http-project-networks-ii
   ```
   And then the ClientCLI to make a request to the main page:

   ```sh
   java -jar app/build/libs/ClientCLI.jar "https://localhost:4443/"
   ```

## Usage

After starting the server, you can make HTTP GET requests to it. For example, if you have an `index.html` file in the `static_resources` directory, you can access it by navigating to `http://localhost:8080/` in your web browser.

### Example Requests

- Access the home page:
  ```
  GET / HTTP/1.1
  Host: localhost
  Accept: */*
  Accept-Language: en-US,en;q=0.5
  Connection: keep-alive
  User-Agent: DJGI/1.0.0
  Date: Tue, 19 May 2024 14:33:00 GMT
  ```

- Access a specific file:
  ```
  GET /sussyinvaders/index.html HTTP/1.1
  Host: localhost
  Accept: */*
  Accept-Language: en-US,en;q=0.5
  Connection: keep-alive
  User-Agent: DJGI/1.0.0
  Date: Tue, 19 May 2024 14:33:00 GMT
  ```

- Access a directory to list contents or serve `index.html`:
  ```
  GET /sussyinvaders/ HTTP/1.1
  Host: localhost
  Accept: */*
  Accept-Language: en-US,en;q=0.5
  Connection: keep-alive
  User-Agent: DJGI/1.0.0
  Date: Tue, 19 May 2024 14:33:00 GMT
  ```

## Directory Structure

```plaintext
├── Dockerfile
├── ExampleArchives
│   ├── HttpEchoServer.java
│   ├── MyHTTPClient.java
│   ├── Research.txt
│   ├── TestsHTTP.class
│   └── TestsHTTP.java
├── README.md
├── app
│   ├── build
│   │   ├── classes
│   │   │   └── java
│   │   │       ├── main
│   │   │       │   └── http
│   │   │       │       └── project
│   │   │       │           └── networks
│   │   │       │               └── ii
│   │   │       │                   ├── api
│   │   │       │                   │   └── teachers_api
│   │   │       │                   │       ├── APITeachers$1.class
│   │   │       │                   │       ├── APITeachers.class
│   │   │       │                   │       ├── Project.class
│   │   │       │                   │       ├── Teacher.class
│   │   │       │                   │       └── TeachersClass.class
│   │   │       │                   ├── client
│   │   │       │                   │   ├── CachedData.class
│   │   │       │                   │   ├── ClientApp.class
│   │   │       │                   │   ├── ClientCLI.class
│   │   │       │                   │   └── GreetClient.class
│   │   │       │                   ├── cookies
│   │   │       │                   │   └── Cookie.class
│   │   │       │                   ├── gui
│   │   │       │                   │   ├── ClientGui$1.class
│   │   │       │                   │   ├── ClientGui.class
│   │   │       │                   │   ├── buttons
│   │   │       │                   │   │   ├── AddHeadersButton$1.class
│   │   │       │                   │   │   ├── AddHeadersButton.class
│   │   │       │                   │   │   ├── SendRequestButton$1.class
│   │   │       │                   │   │   └── SendRequestButton.class
│   │   │       │                   │   ├── dialogs
│   │   │       │                   │   │   ├── HeadersDialog$1.class
│   │   │       │                   │   │   └── HeadersDialog.class
│   │   │       │                   │   ├── extra_headers
│   │   │       │                   │   │   └── HttpExtraHeaders.class
│   │   │       │                   │   ├── fields
│   │   │       │                   │   │   ├── BodyField.class
│   │   │       │                   │   │   ├── BodyTypeField.class
│   │   │       │                   │   │   ├── HostField.class
│   │   │       │                   │   │   ├── MethodComboBoxListener.class
│   │   │       │                   │   │   ├── MethodField.class
│   │   │       │                   │   │   └── PortField.class
│   │   │       │                   │   ├── panels
│   │   │       │                   │   │   ├── RequestAndResponseSplitPanel.class
│   │   │       │                   │   │   ├── RequestControlsPanel.class
│   │   │       │                   │   │   └── ResponsePanel.class
│   │   │       │                   │   └── utils
│   │   │       │                   │       └── GuiUtils.class
│   │   │       │                   ├── handle_requests
│   │   │       │                   │   ├── RequestCommand.class
│   │   │       │                   │   ├── RequestConditionalGet.class
│   │   │       │                   │   ├── RequestDELETE.class
│   │   │       │                   │   ├── RequestGET.class
│   │   │       │                   │   ├── RequestHEAD.class
│   │   │       │                   │   ├── RequestPOST.class
│   │   │       │                   │   └── RequestPUT.class
│   │   │       │                   ├── headers
│   │   │       │                   │   ├── Headers.class
│   │   │       │                   │   ├── RequestHeaders.class
│   │   │       │                   │   └── ResponseHeaders.class
│   │   │       │                   ├── logger
│   │   │       │                   │   └── Logger.class
│   │   │       │                   ├── requests
│   │   │       │                   │   └── Request.class
│   │   │       │                   ├── responses
│   │   │       │                   │   └── Response.class
│   │   │       │                   ├── server
│   │   │       │                   │   ├── GreetServer.class
│   │   │       │                   │   ├── ServerApp.class
│   │   │       │                   │   ├── ServerDockerApp.class
│   │   │       │                   │   ├── ServerStatusCodes.class
│   │   │       │                   │   └── ServerThread.class
│   │   │       │                   ├── tls
│   │   │       │                   │   ├── ClientHello.class
│   │   │       │                   │   ├── ServerHello.class
│   │   │       │                   │   └── TlsShared.class
│   │   │       │                   └── utils
│   │   │       │                       ├── HTTPUtils.class
│   │   │       │                       ├── HttpBody.class
│   │   │       │                       ├── HttpBodyType.class
│   │   │       │                       ├── HttpRequestHeaders.class
│   │   │       │                       └── Verbs.class
│   │   │       └── test
│   │   │           └── http
│   │   │               └── project
│   │   │                   └── networks
│   │   │                       └── ii
│   │   │                           ├── AppTest.class
│   │   │                           └── GreetClientTest.class
│   │   ├── distributions
│   │   │   ├── app.tar
│   │   │   └── app.zip
│   │   ├── generated
│   │   │   └── sources
│   │   │       ├── annotationProcessor
│   │   │       │   └── java
│   │   │       │       ├── main
│   │   │       │       └── test
│   │   │       └── headers
│   │   │           └── java
│   │   │               ├── main
│   │   │               └── test
│   │   ├── libs
│   │   │   ├── ClientCLI.jar
│   │   │   ├── ClientGUI.jar
│   │   │   ├── ServerDocker.jar
│   │   │   └── app.jar
│   │   ├── reports
│   │   │   └── tests
│   │   │       └── test
│   │   │           ├── classes
│   │   │           │   └── http.project.networks.ii.AppTest.html
│   │   │           ├── css
│   │   │           │   ├── base-style.css
│   │   │           │   └── style.css
│   │   │           ├── index.html
│   │   │           ├── js
│   │   │           │   └── report.js
│   │   │           └── packages
│   │   │               └── http.project.networks.ii.html
│   │   ├── resources
│   │   ├── scripts
│   │   │   ├── app
│   │   │   └── app.bat
│   │   ├── test-results
│   │   │   └── test
│   │   │       ├── TEST-http.project.networks.ii.AppTest.xml
│   │   │       └── binary
│   │   │           ├── output.bin
│   │   │           ├── output.bin.idx
│   │   │           └── results.bin
│   │   └── tmp
│   │       ├── compileJava
│   │       │   ├── compileTransaction
│   │       │   │   ├── annotation-output
│   │       │   │   ├── compile-output
│   │       │   │   │   └── http
│   │       │   │   │       └── project
│   │       │   │   │           └── networks
│   │       │   │   │               └── ii
│   │       │   │   │                   ├── api
│   │       │   │   │                   │   └── teachers_api
│   │       │   │   │                   ├── client
│   │       │   │   │                   ├── cookies
│   │       │   │   │                   ├── gui
│   │       │   │   │                   │   ├── buttons
│   │       │   │   │                   │   ├── dialogs
│   │       │   │   │                   │   ├── fields
│   │       │   │   │                   │   └── panels
│   │       │   │   │                   ├── handle_requests
│   │       │   │   │                   ├── headers
│   │       │   │   │                   ├── logger
│   │       │   │   │                   ├── requests
│   │       │   │   │                   ├── responses
│   │       │   │   │                   ├── server
│   │       │   │   │                   ├── tls
│   │       │   │   │                   └── utils
│   │       │   │   ├── header-output
│   │       │   │   └── stash-dir
│   │       │   │       ├── APITeachers$1.class.uniqueId29
│   │       │   │       ├── APITeachers.class.uniqueId3
│   │       │   │       ├── AddHeadersButton$1.class.uniqueId19
│   │       │   │       ├── AddHeadersButton.class.uniqueId10
│   │       │   │       ├── ClientApp.class.uniqueId5
│   │       │   │       ├── ClientCLI.class.uniqueId8
│   │       │   │       ├── ClientGui$1.class.uniqueId25
│   │       │   │       ├── ClientGui.class.uniqueId6
│   │       │   │       ├── GreetClient.class.uniqueId17
│   │       │   │       ├── GreetServer.class.uniqueId0
│   │       │   │       ├── HTTPUtils.class.uniqueId9
│   │       │   │       ├── HeadersDialog$1.class.uniqueId15
│   │       │   │       ├── HeadersDialog.class.uniqueId13
│   │       │   │       ├── Request.class.uniqueId24
│   │       │   │       ├── RequestAndResponseSplitPanel.class.uniqueId26
│   │       │   │       ├── RequestConditionalGet.class.uniqueId20
│   │       │   │       ├── RequestControlsPanel.class.uniqueId1
│   │       │   │       ├── RequestDELETE.class.uniqueId16
│   │       │   │       ├── RequestGET.class.uniqueId4
│   │       │   │       ├── RequestHEAD.class.uniqueId14
│   │       │   │       ├── RequestHeaders.class.uniqueId18
│   │       │   │       ├── RequestPOST.class.uniqueId11
│   │       │   │       ├── RequestPUT.class.uniqueId27
│   │       │   │       ├── Response.class.uniqueId7
│   │       │   │       ├── ResponsePanel.class.uniqueId2
│   │       │   │       ├── SendRequestButton$1.class.uniqueId21
│   │       │   │       ├── SendRequestButton.class.uniqueId28
│   │       │   │       ├── ServerApp.class.uniqueId23
│   │       │   │       ├── ServerDockerApp.class.uniqueId22
│   │       │   │       └── ServerThread.class.uniqueId12
│   │       │   └── previous-compilation-data.bin
│   │       ├── compileTestJava
│   │       │   ├── compileTransaction
│   │       │   │   ├── annotation-output
│   │       │   │   ├── compile-output
│   │       │   │   ├── header-output
│   │       │   │   └── stash-dir
│   │       │   └── previous-compilation-data.bin
│   │       ├── jar
│   │       │   └── MANIFEST.MF
│   │       ├── jarClientCLI
│   │       │   └── MANIFEST.MF
│   │       ├── jarClientGUI
│   │       │   └── MANIFEST.MF
│   │       ├── jarServerDocker
│   │       │   └── MANIFEST.MF
│   │       ├── javadoc
│   │       │   └── javadoc.options
│   │       └── test
│   ├── build.gradle
│   └── src
│       ├── main
│       │   └── java
│       │       └── http
│       │           └── project
│       │               └── networks
│       │                   └── ii
│       │                       ├── api
│       │                       │   └── teachers_api
│       │                       │       ├── APITeachers.java
│       │                       │       ├── Project.java
│       │                       │       ├── Teacher.java
│       │                       │       └── TeachersClass.java
│       │                       ├── client
│       │                       │   ├── CachedData.java
│       │                       │   ├── ClientApp.java
│       │                       │   ├── ClientCLI.java
│       │                       │   └── GreetClient.java
│       │                       ├── cookies
│       │                       │   └── Cookie.java
│       │                       ├── gui
│       │                       │   ├── ClientGui.java
│       │                       │   ├── buttons
│       │                       │   │   ├── AddHeadersButton.java
│       │                       │   │   └── SendRequestButton.java
│       │                       │   ├── dialogs
│       │                       │   │   └── HeadersDialog.java
│       │                       │   ├── extra_headers
│       │                       │   │   └── HttpExtraHeaders.java
│       │                       │   ├── fields
│       │                       │   │   ├── BodyField.java
│       │                       │   │   ├── BodyTypeField.java
│       │                       │   │   ├── HostField.java
│       │                       │   │   ├── MethodComboBoxListener.java
│       │                       │   │   ├── MethodField.java
│       │                       │   │   └── PortField.java
│       │                       │   ├── panels
│       │                       │   │   ├── RequestAndResponseSplitPanel.java
│       │                       │   │   ├── RequestControlsPanel.java
│       │                       │   │   └── ResponsePanel.java
│       │                       │   └── utils
│       │                       │       └── GuiUtils.java
│       │                       ├── handle_requests
│       │                       │   ├── RequestCommand.java
│       │                       │   ├── RequestConditionalGet.java
│       │                       │   ├── RequestDELETE.java
│       │                       │   ├── RequestGET.java
│       │                       │   ├── RequestHEAD.java
│       │                       │   ├── RequestPOST.java
│       │                       │   └── RequestPUT.java
│       │                       ├── headers
│       │                       │   ├── Headers.java
│       │                       │   ├── RequestHeaders.java
│       │                       │   └── ResponseHeaders.java
│       │                       ├── logger
│       │                       │   ├── Logger.java
│       │                       │   └── logs
│       │                       │       ├── server_log_2024-05-21_12_48_25.txt
│       │                       │       └── server_log_2024-05-21_12_49_45.txt
│       │                       ├── requests
│       │                       │   └── Request.java
│       │                       ├── responses
│       │                       │   └── Response.java
│       │                       ├── server
│       │                       │   ├── GreetServer.java
│       │                       │   ├── ServerApp.java
│       │                       │   ├── ServerDockerApp.java
│       │                       │   ├── ServerStatusCodes.java
│       │                       │   └── ServerThread.java
│       │                       ├── static_resources
│       │                       │   ├── Ravenger
│       │                       │   │   ├── BuildU
│       │                       │   │   │   ├── BuildWEB.data
│       │                       │   │   │   ├── BuildWEB.framework.js
│       │                       │   │   │   ├── BuildWEB.loader.js
│       │                       │   │   │   └── BuildWEB.wasm
│       │                       │   │   ├── TemplateData
│       │                       │   │   │   ├── MemoryProfiler.png
│       │                       │   │   │   ├── favicon.ico
│       │                       │   │   │   ├── fullscreen-button.png
│       │                       │   │   │   ├── progress-bar-empty-dark.png
│       │                       │   │   │   ├── progress-bar-empty-light.png
│       │                       │   │   │   ├── progress-bar-full-dark.png
│       │                       │   │   │   ├── progress-bar-full-light.png
│       │                       │   │   │   ├── style.css
│       │                       │   │   │   ├── unity-logo-dark.png
│       │                       │   │   │   ├── unity-logo-light.png
│       │                       │   │   │   ├── webgl-logo.png
│       │                       │   │   │   └── webmemd-icon.png
│       │                       │   │   └── index.html
│       │                       │   ├── baquero.jpeg
│       │                       │   ├── impostout
│       │                       │   │   ├── README.md
│       │                       │   │   ├── css
│       │                       │   │   │   ├── style.css
│       │                       │   │   │   └── styleHowTo.css
│       │                       │   │   ├── fonts
│       │                       │   │   │   └── amongus_vector.ttf
│       │                       │   │   ├── howtoplay.html
│       │                       │   │   ├── images
│       │                       │   │   │   ├── Admin.png
│       │                       │   │   │   ├── AdminCam.png
│       │                       │   │   │   ├── Electrics.png
│       │                       │   │   │   ├── ElectricsCam.png
│       │                       │   │   │   ├── MedBay.png
│       │                       │   │   │   ├── MedBayCam.png
│       │                       │   │   │   ├── Shields.png
│       │                       │   │   │   ├── ShieldsCam.png
│       │                       │   │   │   ├── bgPattern.png
│       │                       │   │   │   ├── black
│       │                       │   │   │   │   ├── BlackAnim.gif
│       │                       │   │   │   │   ├── Body.png
│       │                       │   │   │   │   ├── CuerpoMuerto.png
│       │                       │   │   │   │   ├── Death.png
│       │                       │   │   │   │   └── step
│       │                       │   │   │   │       ├── step00.png
│       │                       │   │   │   │       ├── step01.png
│       │                       │   │   │   │       ├── step02.png
│       │                       │   │   │   │       ├── step03.png
│       │                       │   │   │   │       ├── step04.png
│       │                       │   │   │   │       ├── step05.png
│       │                       │   │   │   │       ├── step06.png
│       │                       │   │   │   │       ├── step07.png
│       │                       │   │   │   │       ├── step08.png
│       │                       │   │   │   │       ├── step09.png
│       │                       │   │   │   │       ├── step10.png
│       │                       │   │   │   │       ├── step11.png
│       │                       │   │   │   │       ├── step12.png
│       │                       │   │   │   │       ├── step13.png
│       │                       │   │   │   │       ├── step14.png
│       │                       │   │   │   │       ├── step15.png
│       │                       │   │   │   │       ├── step16.png
│       │                       │   │   │   │       ├── step17.png
│       │                       │   │   │   │       ├── step18.png
│       │                       │   │   │   │       ├── step19.png
│       │                       │   │   │   │       ├── step20.png
│       │                       │   │   │   │       ├── step21.png
│       │                       │   │   │   │       ├── step22.png
│       │                       │   │   │   │       └── step23.png
│       │                       │   │   │   ├── icons
│       │                       │   │   │   │   ├── Announce.png
│       │                       │   │   │   │   ├── Lock.png
│       │                       │   │   │   │   ├── PowerUPx2.png
│       │                       │   │   │   │   ├── Report.png
│       │                       │   │   │   │   └── mouseCursor.ico
│       │                       │   │   │   ├── interface
│       │                       │   │   │   │   ├── Button.png
│       │                       │   │   │   │   ├── ButtonPressed.png
│       │                       │   │   │   │   ├── Camara.png
│       │                       │   │   │   │   ├── CameraSupport.png
│       │                       │   │   │   │   ├── Counter.png
│       │                       │   │   │   │   ├── Erase.png
│       │                       │   │   │   │   ├── Exclamation.gif
│       │                       │   │   │   │   ├── Exclamation.png
│       │                       │   │   │   │   ├── HowToPlay.png
│       │                       │   │   │   │   ├── Needle.png
│       │                       │   │   │   │   ├── Save.png
│       │                       │   │   │   │   ├── phalax1.png
│       │                       │   │   │   │   ├── phalax2.png
│       │                       │   │   │   │   └── phalax3.png
│       │                       │   │   │   ├── miniCustom
│       │                       │   │   │   │   ├── 343GuiltySpark.png
│       │                       │   │   │   │   ├── Archeologist.png
│       │                       │   │   │   │   ├── ClankPet.png
│       │                       │   │   │   │   ├── Cube.png
│       │                       │   │   │   │   ├── Demon.png
│       │                       │   │   │   │   ├── Gladiator.png
│       │                       │   │   │   │   ├── Hat.png
│       │                       │   │   │   │   ├── Robot.png
│       │                       │   │   │   │   ├── Suit.png
│       │                       │   │   │   │   ├── noneCostume.png
│       │                       │   │   │   │   ├── noneHat.png
│       │                       │   │   │   │   └── nonePet.png
│       │                       │   │   │   ├── red
│       │                       │   │   │   │   ├── Body.png
│       │                       │   │   │   │   ├── CuerpoMuerto.png
│       │                       │   │   │   │   ├── Death.png
│       │                       │   │   │   │   └── step
│       │                       │   │   │   │       ├── step00.png
│       │                       │   │   │   │       ├── step01.png
│       │                       │   │   │   │       ├── step02.png
│       │                       │   │   │   │       ├── step03.png
│       │                       │   │   │   │       ├── step04.png
│       │                       │   │   │   │       ├── step05.png
│       │                       │   │   │   │       ├── step06.png
│       │                       │   │   │   │       ├── step07.png
│       │                       │   │   │   │       ├── step08.png
│       │                       │   │   │   │       ├── step09.png
│       │                       │   │   │   │       ├── step10.png
│       │                       │   │   │   │       ├── step11.png
│       │                       │   │   │   │       ├── step12.png
│       │                       │   │   │   │       ├── step13.png
│       │                       │   │   │   │       ├── step14.png
│       │                       │   │   │   │       ├── step15.png
│       │                       │   │   │   │       ├── step16.png
│       │                       │   │   │   │       ├── step17.png
│       │                       │   │   │   │       ├── step18.png
│       │                       │   │   │   │       ├── step19.png
│       │                       │   │   │   │       ├── step20.png
│       │                       │   │   │   │       ├── step21.png
│       │                       │   │   │   │       ├── step22.png
│       │                       │   │   │   │       └── step23.png
│       │                       │   │   │   ├── vent
│       │                       │   │   │   │   ├── ventBLACK.png
│       │                       │   │   │   │   ├── ventClosed.png
│       │                       │   │   │   │   ├── ventOpen.png
│       │                       │   │   │   │   ├── ventRED.png
│       │                       │   │   │   │   └── ventWHITE.png
│       │                       │   │   │   └── white
│       │                       │   │   │       ├── Body.png
│       │                       │   │   │       ├── CuerpoMuerto.png
│       │                       │   │   │       ├── Death.png
│       │                       │   │   │       └── step
│       │                       │   │   │           ├── step00.png
│       │                       │   │   │           ├── step01.png
│       │                       │   │   │           ├── step02.png
│       │                       │   │   │           ├── step03.png
│       │                       │   │   │           ├── step04.png
│       │                       │   │   │           ├── step05.png
│       │                       │   │   │           ├── step06.png
│       │                       │   │   │           ├── step07.png
│       │                       │   │   │           ├── step08.png
│       │                       │   │   │           ├── step09.png
│       │                       │   │   │           ├── step10.png
│       │                       │   │   │           ├── step11.png
│       │                       │   │   │           ├── step12.png
│       │                       │   │   │           ├── step13.png
│       │                       │   │   │           ├── step14.png
│       │                       │   │   │           ├── step15.png
│       │                       │   │   │           ├── step16.png
│       │                       │   │   │           ├── step17.png
│       │                       │   │   │           ├── step18.png
│       │                       │   │   │           ├── step19.png
│       │                       │   │   │           ├── step20.png
│       │                       │   │   │           ├── step21.png
│       │                       │   │   │           ├── step22.png
│       │                       │   │   │           └── step23.png
│       │                       │   │   ├── index.html
│       │                       │   │   ├── js
│       │                       │   │   │   ├── Buttons.js
│       │                       │   │   │   ├── CustomSus.js
│       │                       │   │   │   ├── HUD.js
│       │                       │   │   │   ├── TextValues.js
│       │                       │   │   │   ├── autoclicker.js
│       │                       │   │   │   ├── barometer.js
│       │                       │   │   │   ├── bg_room.js
│       │                       │   │   │   ├── collisions.js
│       │                       │   │   │   ├── cookies.js
│       │                       │   │   │   ├── crewMini.js
│       │                       │   │   │   ├── game.js
│       │                       │   │   │   ├── imposBLACK.js
│       │                       │   │   │   ├── imposRED.js
│       │                       │   │   │   ├── imposWHITE.js
│       │                       │   │   │   ├── impostor.js
│       │                       │   │   │   ├── paralax.js
│       │                       │   │   │   ├── powerup.js
│       │                       │   │   │   ├── sound.js
│       │                       │   │   │   └── vent.js
│       │                       │   │   └── sound
│       │                       │   │       ├── NormalTask.wav
│       │                       │   │       └── killed.mp3
│       │                       │   ├── index.html
│       │                       │   ├── javadocs
│       │                       │   │   ├── allclasses-index.html
│       │                       │   │   ├── allpackages-index.html
│       │                       │   │   ├── constant-values.html
│       │                       │   │   ├── element-list
│       │                       │   │   ├── help-doc.html
│       │                       │   │   ├── http
│       │                       │   │   │   └── project
│       │                       │   │   │       └── networks
│       │                       │   │   │           └── ii
│       │                       │   │   │               ├── api
│       │                       │   │   │               │   └── teachers_api
│       │                       │   │   │               │       ├── APITeachers.html
│       │                       │   │   │               │       ├── Project.html
│       │                       │   │   │               │       ├── Teacher.html
│       │                       │   │   │               │       ├── TeachersClass.html
│       │                       │   │   │               │       ├── package-summary.html
│       │                       │   │   │               │       └── package-tree.html
│       │                       │   │   │               ├── client
│       │                       │   │   │               │   ├── CachedData.html
│       │                       │   │   │               │   ├── ClientApp.html
│       │                       │   │   │               │   ├── ClientCLI.html
│       │                       │   │   │               │   ├── GreetClient.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               ├── cookies
│       │                       │   │   │               │   ├── Cookie.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               ├── gui
│       │                       │   │   │               │   ├── ClientGui.html
│       │                       │   │   │               │   ├── buttons
│       │                       │   │   │               │   │   ├── AddHeadersButton.html
│       │                       │   │   │               │   │   ├── SendRequestButton.html
│       │                       │   │   │               │   │   ├── package-summary.html
│       │                       │   │   │               │   │   └── package-tree.html
│       │                       │   │   │               │   ├── dialogs
│       │                       │   │   │               │   │   ├── HeadersDialog.html
│       │                       │   │   │               │   │   ├── package-summary.html
│       │                       │   │   │               │   │   └── package-tree.html
│       │                       │   │   │               │   ├── extra_headers
│       │                       │   │   │               │   │   ├── HttpExtraHeaders.html
│       │                       │   │   │               │   │   ├── package-summary.html
│       │                       │   │   │               │   │   └── package-tree.html
│       │                       │   │   │               │   ├── fields
│       │                       │   │   │               │   │   ├── BodyField.html
│       │                       │   │   │               │   │   ├── BodyTypeField.html
│       │                       │   │   │               │   │   ├── HostField.html
│       │                       │   │   │               │   │   ├── MethodComboBoxListener.html
│       │                       │   │   │               │   │   ├── MethodField.html
│       │                       │   │   │               │   │   ├── PortField.html
│       │                       │   │   │               │   │   ├── package-summary.html
│       │                       │   │   │               │   │   └── package-tree.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   ├── package-tree.html
│       │                       │   │   │               │   ├── panels
│       │                       │   │   │               │   │   ├── RequestAndResponseSplitPanel.html
│       │                       │   │   │               │   │   ├── RequestControlsPanel.html
│       │                       │   │   │               │   │   ├── ResponsePanel.html
│       │                       │   │   │               │   │   ├── package-summary.html
│       │                       │   │   │               │   │   └── package-tree.html
│       │                       │   │   │               │   └── utils
│       │                       │   │   │               │       ├── GuiUtils.html
│       │                       │   │   │               │       ├── package-summary.html
│       │                       │   │   │               │       └── package-tree.html
│       │                       │   │   │               ├── handle_requests
│       │                       │   │   │               │   ├── RequestCommand.html
│       │                       │   │   │               │   ├── RequestConditionalGet.html
│       │                       │   │   │               │   ├── RequestDELETE.html
│       │                       │   │   │               │   ├── RequestGET.html
│       │                       │   │   │               │   ├── RequestHEAD.html
│       │                       │   │   │               │   ├── RequestPOST.html
│       │                       │   │   │               │   ├── RequestPUT.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               ├── headers
│       │                       │   │   │               │   ├── Headers.html
│       │                       │   │   │               │   ├── RequestHeaders.html
│       │                       │   │   │               │   ├── ResponseHeaders.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               ├── logger
│       │                       │   │   │               │   ├── Logger.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               ├── requests
│       │                       │   │   │               │   ├── Request.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               ├── responses
│       │                       │   │   │               │   ├── Response.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               ├── server
│       │                       │   │   │               │   ├── GreetServer.html
│       │                       │   │   │               │   ├── ServerApp.html
│       │                       │   │   │               │   ├── ServerDockerApp.html
│       │                       │   │   │               │   ├── ServerStatusCodes.html
│       │                       │   │   │               │   ├── ServerThread.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               ├── tls
│       │                       │   │   │               │   ├── ClientHello.html
│       │                       │   │   │               │   ├── ServerHello.html
│       │                       │   │   │               │   ├── TlsShared.html
│       │                       │   │   │               │   ├── package-summary.html
│       │                       │   │   │               │   └── package-tree.html
│       │                       │   │   │               └── utils
│       │                       │   │   │                   ├── HTTPUtils.html
│       │                       │   │   │                   ├── HttpBody.html
│       │                       │   │   │                   ├── HttpBodyType.html
│       │                       │   │   │                   ├── HttpRequestHeaders.html
│       │                       │   │   │                   ├── Verbs.html
│       │                       │   │   │                   ├── package-summary.html
│       │                       │   │   │                   └── package-tree.html
│       │                       │   │   ├── index-all.html
│       │                       │   │   ├── index.html
│       │                       │   │   ├── jquery-ui.overrides.css
│       │                       │   │   ├── legal
│       │                       │   │   │   ├── COPYRIGHT
│       │                       │   │   │   ├── LICENSE
│       │                       │   │   │   ├── jquery.md
│       │                       │   │   │   └── jqueryUI.md
│       │                       │   │   ├── member-search-index.js
│       │                       │   │   ├── module-search-index.js
│       │                       │   │   ├── overview-summary.html
│       │                       │   │   ├── overview-tree.html
│       │                       │   │   ├── package-search-index.js
│       │                       │   │   ├── resources
│       │                       │   │   │   ├── glass.png
│       │                       │   │   │   └── x.png
│       │                       │   │   ├── script-dir
│       │                       │   │   │   ├── jquery-3.6.1.min.js
│       │                       │   │   │   ├── jquery-ui.min.css
│       │                       │   │   │   └── jquery-ui.min.js
│       │                       │   │   ├── script.js
│       │                       │   │   ├── search.js
│       │                       │   │   ├── serialized-form.html
│       │                       │   │   ├── stylesheet.css
│       │                       │   │   ├── tag-search-index.js
│       │                       │   │   └── type-search-index.js
│       │                       │   ├── juan.jpg
│       │                       │   ├── nineties_web
│       │                       │   │   ├── index2.html
│       │                       │   │   ├── prueba
│       │                       │   │   │   ├── index.html
│       │                       │   │   │   └── style.css
│       │                       │   │   └── style.css
│       │                       │   ├── style.css
│       │                       │   ├── sussyinvaders
│       │                       │   │   ├── README.md
│       │                       │   │   ├── css
│       │                       │   │   │   └── style.css
│       │                       │   │   ├── images
│       │                       │   │   │   ├── AmoSaucer.png
│       │                       │   │   │   ├── BottomEnemy0.png
│       │                       │   │   │   ├── BottomEnemy1.png
│       │                       │   │   │   ├── CompleteSpritesheet.png
│       │                       │   │   │   ├── Explosion.png
│       │                       │   │   │   ├── MidEnemy0.png
│       │                       │   │   │   ├── MidEnemy1.png
│       │                       │   │   │   ├── Player.png
│       │                       │   │   │   ├── PlayerDead.png
│       │                       │   │   │   ├── Saucer.png
│       │                       │   │   │   ├── Sussy.ico
│       │                       │   │   │   ├── TopEnemy0.png
│       │                       │   │   │   ├── TopEnemy1.png
│       │                       │   │   │   └── background.png
│       │                       │   │   ├── index.html
│       │                       │   │   ├── js
│       │                       │   │   │   ├── aliens.js
│       │                       │   │   │   ├── bullets.js
│       │                       │   │   │   ├── collision.js
│       │                       │   │   │   ├── covers.js
│       │                       │   │   │   ├── game.js
│       │                       │   │   │   ├── matrix.js
│       │                       │   │   │   ├── player.js
│       │                       │   │   │   ├── saucerAlien.js
│       │                       │   │   │   └── sound.js
│       │                       │   │   └── sounds
│       │                       │   │       ├── amongUsImpostor.mp3
│       │                       │   │       ├── fastinvader1.wav
│       │                       │   │       ├── fastinvader2.wav
│       │                       │   │       ├── fastinvader3.wav
│       │                       │   │       ├── fastinvader4.wav
│       │                       │   │       ├── invaderkilled.wav
│       │                       │   │       ├── shoot.wav
│       │                       │   │       └── ufo_lowpitch.wav
│       │                       │   └── teachers.json
│       │                       ├── tls
│       │                       │   ├── ClientHello.java
│       │                       │   ├── ServerHello.java
│       │                       │   ├── TlsShared.java
│       │                       │   ├── certif.crt
│       │                       │   ├── private_key.der
│       │                       │   └── private_key.key
│       │                       └── utils
│       │                           ├── HTTPUtils.java
│       │                           ├── HttpBody.java
│       │                           ├── HttpBodyType.java
│       │                           ├── HttpRequestHeaders.java
│       │                           └── Verbs.java
│       └── test
│           └── java
│               └── http
│                   └── project
│                       └── networks
│                           └── ii
│                               ├── AppTest.java
│                               └── GreetClientTest.java
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## Docker Configuration

The `Dockerfile` contains a set of instructions to build a Docker image, it's at the main root directory level of the project.

1. **Build the Docker Image**:
   
   Once you've created the `Dockerfile`, you can build the Docker image by running the following command in the root of your project:

   ```sh
   docker build -t http-project-networks-ii .
   ```

   This will create a Docker image tagged as `http-project-networks-ii`.

2. **Run the Docker Container**:
   
   After building the image, you can run a container based on that image with the following command:

   ```sh
   docker run -p8080:80 -p4443:443 http-project-networks-ii
   ```

   This will run your application in a Docker container and map port 8080 of the container to port 8080 of your host machine.

## Gradle Configuration and Build

Gradle is a build automation tool that you can use to compile and build your Java project. Here's an example of how to configure and build your project with Gradle:

1. **Build the Project with Gradle**:
   
   Run the following command to build your project:

   ```sh
   ./gradlew build
   ```

   This will compile your project and generate a JAR file in the `build/libs` directory.

2. **Generate Javadocs with Gradle**:
   
   Run the following command to generate the Javadocs:

   ```sh
   ./gradlew javadoc
   ```

## Complete Steps

1. **Build the Docker image**:
   
   ```sh
   docker build -t http-project-networks-ii .
   ```

2. **Run the Docker container**:
   
   ```sh
   docker run -p8080:80 -p4443:443 http-project-networks-ii
   ```

By following these steps, you can configure, build, and run your Java project using Gradle and Docker. Be sure to customize the `build.gradle` and `Dockerfile` according to the specific needs of your project.

## Trello Board
https://trello.com/b/RvijUP3N/http

## Technical Report
Decisions made, challenges overcome, group task distribution, and work methodology.
[Technical Report](Http_Project_Memory_DanielJuanGermanIvan.pdf)

## Teacher's Github
https://github.com/pitazzo/usj-http-project
