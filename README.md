# Real Time Leaderboard System

## Task 1 Description
Create a real-time leaderboard system for a multiplayer online game. The system should be able to handle the following operations efficiently:

* Deliverables.
1) Add a new player's score.
2) Update an existing player's score.
3) Retrieve the top 'N' players.
4) Retrieve a player’s rank in the leaderboard


## Software Specifications
    * java 1.8
    * Maven
    * Springboot V2.5
    * MYSQL Database
    * RabbitMQ
    * Redis 7.2.0

## Assumptions
    1) Players and scores are diffent entities hence they exist in a one-to-one relationship
    2) Players data are rarely retrieved hence we can easily work on score data without actually putting strain on the db fetching player data

## Important Links
For api documentation I am using swagger-Open Document Api which can be accessed from below Link

Swagger : http://localhost:8080/swagger-ui/index.html

Some of the API links are described below:

    some of the links are:
## Player

1.```PUT /score/player/{id}/score **```-Update New Player score
```json        
    {
      "score"
        {
          "score": "integer"
        }
    }
        
```

2.```POST /player``` -This is to register new player
```json        
    {
        {
          "first_name": "string",
          "last_name": "string",
          "gender": "MALE/FEMALE",
          "email": "string",
          "phone_number": "string",
          "score":0
        }
    }

3.```GET /score/players/{id}/rank``` -This is to fetch the rank of a player**

4.```GET /score/leaderboard/top/{players}?page=0&pageSize=20``` -This is to get certain top players

## Storage
**Task 1**
1. For Storage I am using MYSQL-DB for persistence

## Build
Download or clone the project from the link below:

Task
https://github.com/ikatanyi/Order-API.git

mvn clean build

## Run
    mvn spring-boot:run
*Run packaged jar*
```
https://github.com/ikatanyi/config-repo.git
## Licence

    pepeta
