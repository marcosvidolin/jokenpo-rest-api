# Jokenpo REST API

A ready to play Jokenpo Game API.

## Requirements

- JDK 8+

## How to Run

```shell script
./gradlew bootRun
```

## Test

```shell script
./gradlew test
```

## Swagger

Access the API documentation at:

> http://localhost:9000/swagger-ui.html

## How to Play

- Create all players
- Create a new Game and associate all players using the `username`
- Add moves to each player (you can consult all available moves at GET /moves)
- Run the game and get the winner

## Rules

<p align="center"> 
  <img src="./github/rules.png">
</p>