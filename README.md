# Kafka example in Kotlin
Simplify the use of Kafka for simple projects. This project can be used to make simple operations in Kafka, like: send and listener messages.
Contains simple configs to Kafka Producer, Consumer and DLT topic.

## Technologies
<img height="80" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/kotlin/kotlin-original-wordmark.svg" title="kotlin" width="80" alt="Kotlin"/>
<img height="80" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/spring/spring-original-wordmark.svg" title="spring" width="80" alt="Spring"/>
<img height="80" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/apachekafka/apachekafka-original-wordmark.svg" title="kafka" width="80" alt="Kafka"/>
<img height="80" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/docker/docker-original-wordmark.svg" title="docker" width="80" alt="Docker"/>

## Dependencies
***Docker version 20.10.14***

***docker-compose version 1.26.0***

## docker-compose.yaml content
1. Zookeeper - Service with Zookeeper
2. Apache Kafka - Service with Apache Kafka
3. Kafkdrop - UI for Kafka, can use on port 19000
4. backend App - Kafka example in Kotlin

## Build
This projects uses Maven to build. To build, just run the command `docker-compose up`

## Testing
```http
  POST /producer/customer
  
  {
    "name":"Peter",
    "age":20,
    "message":"Test message"
  }
```

| Parameter | Type     | Description                    |
|:----------|:---------|:-------------------------------|
| `name`    | `string` | **Required**. Customer name    |
| `age`     | `string` | **Required**. Customer Age     |
| `message` | `string` | **Required**. Customer message |

### Post via curl
```shell
curl --location --request POST 'localhost:8080/producer/customer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"Peter",
    "age":20,
    "message":"Test message"
}'
```

### Authors
 * [Danilo Yassuhiko Miyamura](https://www.linkedin.com/in/danilomiyamura)
