# publisher

How to setup publisher service on local.

This is spring boot project.
Tech stack used : spring boot, java 17

Clone the repository in your local system and open it in your preferred IDE.
clone link : **https://github.com/rahulsaw/publisher.git**

We are using amazon corretto jdk 17 in this service, make sure your system should have jdk 17. Steps to download jdk 17 via sdkman.

a.)Install sdkman **https://sdkman.io/install**

b.)After installation, install jdk 17 by using command : **sdk install java 17.0.2.8.1-amzn**

c.)After installation of jdk, set the publisher service java version to 17 : **sdk use java 17.0.2.8.1-amzn**

This is a maven based project so first install all the dependencies by using command : **mvn clean install -DskipTests**

We are using embedded tomcat so no need to link external tomcat, just run it, and your service is up.

Note: Before push transaction to other service please change the server port and listener domain url in application.properties.

You can check the health of service by using following end point :

**{domainUrl}:{port}/publisher/healthcheck**

