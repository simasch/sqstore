# Example Application using most of the Java EE technologies used in a commong web application

You should use WildFly 11 to run the application: http://wildfly.org/

The example uses JMS (Java Messaging Service) you must configure to queue and start WildFly with the full profile.

## Configure the JMS Queue

Add the queue in <JBOSS_HOME>/standalone/configuration/standlong-full.xml
 
     <subsystem xmlns="urn:jboss:domain:messaging-activemq:2.0">
     ...
         <jms-queue name="CustomerQueue" entries="java:/jms/queue/CustomerQueue"/>
     ...
     </subsystem>

## Starting WildFly Full Profile
Either start with: 

    standalone.(sh|bat) -c standalone-full.xml

or pass a system property:

    -Djboss.server.default.config=standalone-full.xml
    
