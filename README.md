# automated-automation-demo
The purpose of this project is to illustrate the concept of Automated Automation of REST APIs. The project refers the Swagger document of the Petstore API - http://petstore.swagger.io/ and creates Java files on the fly that assist in the Automation of the API.

## Tools Used:-

1. JavaFX - Creation of Front End
2. Rest-Assured - For creation of classes that call the API
3. TestNG - Testing Framework

## Directory structure of project:
  * src
    * main -> java -> api.user.login: contain the java file which create api request, execute the request and validate the api response.
    * main -> java -> controller : contain the Controller class which contains definitions for all action mentioned inside .fxml file.
    * main -> java -> view : contain the java file to start the application
    * main -> javapoet : contains the java file which are javapoet scripts to write the .java file.
    * main -> resources : contain the .fxml file used by JavaFX 
  * src -> test -> java : Testscript to test the api.
  * pom.xml - Maven's POM file used for build operations
  
## How to Run ?
main() method of the view.Main class starts the application
