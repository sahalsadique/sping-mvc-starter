# SIMPLE INTEREST CALCULATIONS

Calculate and store your simple interest calculations.

## Setup

### Prerequisites

Java  
Maven  
Eclipse  
Tomcat  
Mysql  

### Database schema

```
create database testdb;
use testdb;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) default null
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime ,
  `updated_at` datetime ,
  PRIMARY KEY (`id`)
);

CREATE TABLE `simple_interests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `principal` float DEFAULT NULL,
  `rate` float DEFAULT NULL,
  `time` float DEFAULT NULL,	
  `interest` float DEFAULT NULL,	
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```


Open application.properties and add your database credentials.

```
spring.datasource.url=jdbc:mysql://localhost:3306/testdb
spring.datasource.username=test
spring.datasource.password=test
```

### Installing

Import the project in eclipse and build the project by running the following command.

```
mvn clean install
```
### Deployment

Deploy the war file generated in the previous build process and deploy directly in a Tomcat server.
https://stackoverflow.com/questions/5109112/how-to-deploy-a-war-file-in-tomcat-7

or

If you are using eclipse. Select "run as" - "run on server". 
Setup Apache Tomcat server, if it's not setup already.


## Test

On your browser: visit http://localhost:8080/spring-demo/login

- Registration  
  
Select "create an account". Enter name, email and password and click submit.

- Login to home page  
  
Enter the registered email and password and login into home page.  
Enter the Principal amount, rate of interest and tenure and click on calculate.  
The page will display all the previous calculations.  

