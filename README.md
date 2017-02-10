This is a demonstration project on Spring development. It involves:

MySQL as a database with Spring JDBC support
Eclipse Mars with Maven plugin
Tomcat 8 as a container

moviesDBBasic.sql was used to generate the MySql database used in this project.

This project can run on Tomcat 8 from Eclipse or on Tomcat 8 standalone from a .war file. It requires Java 8 Runtime Environment.

This is the first version of this project. More stuff is coming soon with a version that uses Hibernate with Spring support.

For a full tutorial about this project please visit my personal site:

http://www.dominique-ubersfeld.com/JAVADEV/SpringDevelopment.html

As a reference book I mainly used Java for Web Applications by Nicholas S. Williams

Note: this project was run on my home computer. To run it on your system you have to edit some files to customize them to your actual file system. They are:

production/java/com/dub/site/actors/ActorController.java:		String path = "/home/dominique/Pictures/tmp/" + fileName; 

production/resources/log4j2.xml:        &lt;RollingFile name="DUbFileAppender" fileName="/home/dominique/logs/support.log"
				                     filePattern="/home/dominique/logs/support-%d{MM-dd-yyyy}-%i.log"&gt;

where the folder /home/dominique should be replaced by a folder that matches your own file system.
