# BSB API service
BSB api service is microservice application provides restful end points to customers
It is using Auth0 for authentication and authorization(Not implemented yet)
API service provides BSB listing, searching BSB data using BSB number functionalities
This application will send user notifications to messaging queue and notification service will consume those messages and send notifications

### Technologies used
1. Springboot
2. JDK 11
3. Postgres
4. ActiveMQ
5. Auth0
6. Redis

### Assumptions
Things are not dockerized
File processing module  is not actually part of this project. But duplicating that module here to test the API service

