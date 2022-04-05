# BSB API service
BSB api service is microservice application provides restful end points to customers
It is using Auth0 for authentication and authorization(Not implemented yet)
API service provides BSB listing, searching BSB data using BSB number functionalities
This application will send user notifications to messaging queue and notification service will consume those messages and send notifications

### Technologies used
Springboot
JDK 11
Postgres
ActiveMQ
Auth0
Redis

### Assumptions
Things are not dockerized
File processing module is not part of this project and part of the bsb-file-processing-service
But keeping that module here to test the API functionality.

