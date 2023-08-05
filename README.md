# HomeAmenitiesBookingModule_Backend
1) Project Name: HomeAmenitiesBookingModule
2) Prerequisites:
    a) springTool suite 4: to run the application
        * after importing project from github update the project and then run using "spring boot application"
    b) java-version: 17
    c) postman API
3) Installation:
    a) Import the project into Spring Tool Suite (STS).
    b)Resolve dependencies using Maven.
4) Endpoints and APIs:
    a) http://localhost:8080/api/bookings/facilities
    b) http://localhost:8080/api/bookings
5) Postman Usage:
    a) for facilities Rest-API:
        {
          "name": "Clubhouse"
        }

    b) for booking Rest-API:
        {
          "facility": {
          "name": "Clubhouse"
        },
            "date": "2023-08-04",
            "startTime": "10:00",
            "endTime": "16:00"
        }

6) Dependencies:
        a) spring-boot-starter-data-jpa
        b) spring-boot-starter-web
        c) spring-boot-devtools
        d) spring-boot-starter-validation
        e) com.h2database

note: Postman -> set the method to "POST"
                 paste the Endpoints provided above.
                 select "Body" then "raw" then format as "JSON"