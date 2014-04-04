# OpenDaylight Toolkit

[![Build Status](https://travis-ci.org/opendaylight-toolkit/opendaylight-toolkit.svg?branch=master)](https://travis-ci.org/opendaylight-toolkit/opendaylight-toolkit)

![alt text](http://media.playdota.com/items/121/icon.jpg "Mekansm")

Quick HowTo
-----------

1) Go to <code>main/archetypes/archetype-app-simple</code> and run <code>mvn install</code> to install the simple archetype

2) Go to the project root directory and generate an app using the simple archetypes you just installed <code>mvn archetype:generate -DarchetypeCatalog=local</code>

>**groupId:** *org.bar.foo* <code>&lt;or any package name&gt;</code>

>**artifactId:** *simple* <code>&lt;or any artifact name&gt;</code>

>**version:** <code>&lt;press enter, default&gt;</code>

>**package:** <code>&lt;press enter, default&gt;</code>

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
