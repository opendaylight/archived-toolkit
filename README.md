# OpenDaylight Toolkit

App Web Development with NodeJS
-------------------------------

1) web/src/main/java/org/opendaylight/toolkit/web/CorsFilter.java and replace *your-ip* (e.g. http://localhost:8000)

2) mvn clean install project's root directory

3) Go to simple/ and issue mvn clean install

4) Run the controller main/target/main-osgipackage/opendaylight/run.sh

5) In a new window, go to node/ and run the node server *node server.js*

>Note: you may need to install missing modules *npm install module_name*

6) Go to http://your-ip:8000 in your browser and start developing from simple/

>Disclaimer: you may point node to any app you wish to develop on top of, not just simple, but that will have to be done manually for now

>Note: ensure bower components are installed for web/, refer to section below


Quick HowTo
-----------

1) Go to <code>main/archetypes/archetype-app-simple</code> and run <code>mvn install</code> to install the simple archetype

2) Go to the project root directory and generate an app using the simple archetypes you just installed <code>mvn archetype:generate -DarchetypeCatalog=local</code>

>**groupId:** *org.bar.foo* <code>&lt;or any package name&gt;</code>

>**artifactId:** *simple* <code>&lt;or any artifact name&gt;</code>

>**version:** <code>&lt;press enter, default&gt;</code>

>**package:** <code>&lt;press enter, default&gt;</code>

>**REST-Resource-Name** *simple* <code>&lt;or any resource name&gt;</code>

3) *[optional]* Install the necessary web bower components, under directories <code>web/src/main/resources/js</code> and <code>web/src/main/resources/css</code> issue <code>bower install</code>

>Note: you may need to install bower on your system using <code>npm -g install bower</code>

>Note: [tip for setting up Node.js and npm on Mac](http://shapeshed.com/setting-up-nodejs-and-npm-on-mac-osx/)

4) **[important]** Install the base controller, within project root directory (where common, main, web are located) issue <code>mvn install</code>
>Note: This may take a while if you're on a new system 

5) Then go into one of the apps you just generated and issue <code>mvn install</code> to install that bundle

6) Then start the controller under <code>main/target/main-osgipackage/opendaylight</code> and <code>./run.sh -console</code>

7) *[optional]* If you installed the bower components, you can access the toolkit web UI at <code>http://localhost:8080</code>


Troubleshooting
---------------

Please visit the wiki for more information

https://wiki.opendaylight.org/view/OpenDaylight_Toolkit:Main
