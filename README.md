# xchangedemo
Small project which exposes an API for retrieving EUR exchange rates from the ECB website in JSON format

The application is using Spring Boot. Run the com.pbalazs.fxdemo.Application class to start up the webserver. 
Make sure that the 8080 port is free or specify a free port as a JVM arg, ex: --server.port=8090

Usage example: http://localhost:8080/rate/CHF/2017-04-09
You can replace the currency and/or the date. The date format is yyyy-MM-dd, like in the example above.
