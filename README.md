<h1>Flashcards API</h1>
Flashcards is the online version of traditional flashcards. The aim of that web app is teaching basic frontend skills.
A user can choose card with a quiz question -between CSS3, HTML5 or JavaScript on different difficulty levels.
The technology stack is: Spring,JPA, Maven, PostgreSQL. 
There is gamification to make that more enjoyable. User can gain various achievements and stats.
It`s being developed. Current language version is polish.

<h1>API Documentation</h1>
Thanks to Swagger API  all endpoints are documented. Just run app on http://localhost:8080/swagger-ui.html

<h1>Sendgrid API</h1>
This web app uses Sengrid Web API to reset password. A link is sent on an email. Insert your api key in application.properties file
which you get on Sendgrid website.

<h1>Basic token auth</h1>
During logging jwt token is generated.

<h1>Database</h1>
The sql file with is located in the resources folder. You need to create and restore the database with that file.
There is 714 questions.

<h1>Run</h1>
In command line insert java -jar + ptath to do flashcards\flashcards\target\flashcards-0.0.1-SNAPSHOT.jar
