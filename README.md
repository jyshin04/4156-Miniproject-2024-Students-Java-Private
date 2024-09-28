# README for COMS4156 Miniproject
This is a repository of the miniproject in Columbia's COMS4156 Advanced Software engineering class. It keeps track of departments and courses at Columbia and allows to modify or update the information. 

## Building and running the app
mvn spring-boot:run -Dspring-boot.run.arguments="setup"

## Style checking and debugging

Used mvn for style checking: 
- mvn checkstyle:check
Used pmd for debugging:
- pmd check -f text -R rulesets/java/quickstart.xml -d src

## Test branch coverage: 
72%

## Features added
Two new features added - 1) retrieveCourses 2) enrollStudentInCourse

## Running the service
For a short time, you will be able to reach this service using the link: 
https://coms4156-individual-project.ue.r.appspot.com/

You can use software like Postman to experiment with the service. 
