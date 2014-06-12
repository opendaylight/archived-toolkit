SDNHub atf
============================

DIRECTORY ORGANIZATION
======================

- commons/parent: contains the parent pom.xml for all projects.
- sdnhub/atf: contains the project
- sdnhub/tutorial_l2_forwarding: contains the forwarding tutorial
- distribution/opendaylight: will build a working controller distribution
  based on the controller + all sdn modules

HOW TO ADD NEW MODULE
============

1. extract new_module.zip at sdnhub/
2. edit commons/parent/pom.xml
3. edit sdnhub/[new project name]/pom.xml
4. edit distribution/opendaylight/pom.xml
5. edit pom.xml

remember: 
	<groupId>org.sdnhub</groupId>	is the group of our projects
	
<artifactId> is the name of the project that we are building.

also at the distribution/opendaylight/pom.xml we also incluse our projects as dependency to allow the compiler to load them.

HOW TO BUILD
============

In order to build it's required to have JDK 1.7+ and Maven 3+

to configure eclipse:
	mvn eclipse:eclipse -DdownloadSources=true

a build going it's needed to:
	cd commons/parent
	mvn clean install

or if you want to avoid SNAPSHOT checking:
	cd commons/parent
	mvn clean install -nsu

and if you want to skip all tests and updates
	cd commons/parent
	mvn clean install -DskipTests -DskipIT -nsu

HOW TO RUN
============

Upon successful completion of a build

	cd distribution\opendaylight\target\distribution-0.5.0-SNAPSHOT-osgipackage\opendaylight
	./run.sh -debug

Wait for the osgi console to startup and then point a browser at 

	http:localhost:8080/




