### Assumptions

- Newly Registered drone state will be `IDEL`.
- Drone can't deliver medications to the multiple location in a one single trip.
- Medications can be loaded in to the drone until it's in `IDLE` OR `LOADING` states.
- Medication image saved in the Aws s3(or Azure blob storage) and assume that imageUrl saved in the Database.

### prerequisite

- Java 17 or higher version
- Docker

### How to Run

* First you need to navigate to the project directory
* Then you need to run the MySQL docker container using command in below,

```
docker-compose -f docker/docker-compose.yml up
```

* After database is up and running, you can start the spring boot application using command in below,

```
./mvnw spring-boot:run
```

* You can run Unit test using command in below,

```
./mvnw clean test
```