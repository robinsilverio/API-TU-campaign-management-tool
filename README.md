# Administration tool for managing campaigns and price-promotions for Webshop
 
## 1. Description
The purpose of this application is to function as an intermediate between the frontend of campaign management tool
and the database for managing campaigns and pricepromotions in the webshop.

## 2. Required software
It is required to have java installed, preferably of version 17. And for running mvn-commands, it is required to install
maven, preferably of version >= 3.

Link to download maven: https://maven.apache.org/download.cgi

## 3. Installation instructions

1. Clone the project by using the command
```git clone ssh://git@bitbucket.technischeunie.com:7999/cs/campaign-admin-backend.git```
2. Make sure that all maven dependencies are installed. Normally, maven re-imports the dependencies automatically
on startup of IDE
3. Make sure that you have installed the required software for running the application and commands (see section 2)
4. Run command ```mvn clean install``` for creating a brand new build plus package of the project (optional)
5. To run the application, simply go to the application main class called TuCMTApiApplication.java

## 4. Running tests:
For running tests, simply run the command ```mvn test``` or by accessing the testfiles that are stored in src/test
and from there run these manually. Also, the test suite class is called TuCMTApiApplicationTests.java

## 5. Testing API-requests
Work in progress...