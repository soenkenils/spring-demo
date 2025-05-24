# Getting Started

## Run the App

First, you need to start the database:

```shell
docker run -d \
  --name postgres-db \
  -e POSTGRES_PASSWORD=mypassword \
  -e POSTGRES_DB=mydb \
  -v postgres-data:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres:15
```

Now start the application:

```shell
./gradlew bootRun
```

## Test the App

Install HTTPie (if needed):

```shell
brew install httpie 
```

Now, test the application with:

```shell
http http://localhost:8080/greetings
```
