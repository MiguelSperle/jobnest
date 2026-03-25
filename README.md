<h1>JobNest 💻</h1>

<p>
    <img src="https://img.shields.io/badge/-java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Badge"/>
    <img src="https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="SpringBoot Badge"/>
    <img src="https://img.shields.io/badge/-Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security Badge" />
    <img src="https://img.shields.io/badge/Spring_data_jpa-6DB33F?style=for-the-badge&logo=hibernate&logoColor=white" alt="Spring Data Jpa Badge" />
    <img src="https://img.shields.io/badge/-postgresql-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres Badge"/>
    <img src="https://img.shields.io/badge/-rabbitmq-%23FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="RabbitMQ Badge"/>
    <img src="https://img.shields.io/badge/-resilience4j-000000?style=for-the-badge" alt="Resilience4j Badge" />
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker Badge"/>
    <img src="https://img.shields.io/badge/junit-%23E33332?style=for-the-badge&logo=junit5&logoColor=white" alt="Junit Badge"/>
    <img src="https://img.shields.io/badge/Mockito-25A162?style=for-the-badge" alt="Mockito Badge" />
</p>

<p>
To summarize, JobNest is a back-end application designed to efficiently manage and support job vacancy searches. It provides a complete set of functionalities that a system of this kind typically offers, 
both for those who publish job vacancies and for those who seek to apply for them.
</p>

<p>
It's important to emphasize that this is a study project, designed to explore and implement complex concepts, such as: Clean Architecture, SOLID Principles, Messaging using the Inbox and Outbox pattern, Database Transactions, Unit Testing, Rate Limiting and Idempotency.
</p>

<p>
This is just a small text to clarify the purpose of the project.
</p>

<h2>🚀 Getting started</h2>

<h3>💻 Prerequisites</h3>

- [JDK 21](https://www.oracle.com/br/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://docs.docker.com/)

<h3>🛸 Cloning</h3>

```
git clone https://github.com/MiguelSperle/jobnest.git
```

📂 Access at folder

```
cd jobnest
```

📡 Install dependencies

```
mvn clean install
```

<h3>⌨️ Command to create and start containers in Docker Compose</h3>

```
docker-compose up -d
```

<h3>🔑 System environment variables</h3>

```
spring:
  config:
    import: optional:file:.env[.properties]
  application:
    name: jobnest
  datasource:
    url: jdbc:postgresql://${DB_HOST}:5432/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: none
  flyway:
    schemas: public
    default-schema: public
  rabbitmq:
    host: ${RABBITMQ_HOST}
    port: 5672
    username: ${RABBITMQ_USERNAME}
    password: ${RABBITMQ_PASSWORD}
    listener:
      simple:
        acknowledge-mode: auto
        retry:
          enabled: true
          max-attempts: 4
          initial-interval: 3000
          multiplier: 2
          max-interval: 12000
  data:
    redis:
      host: ${REDIS_HOST}
      port: 6379
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
  cloudinary:
    cloud-name: ${CLOUDINARY_CLOUD_NAME}
    api-key: ${CLOUDINARY_API_KEY}
    api-secret: ${CLOUDINARY_API_SECRET}

security:
  token:
    secret: ${JWT_SECRET}
 
resilience4j:
  ratelimiter:
    instances:
      defaultConfiguration:
        limitForPeriod: 5
        limitRefreshPeriod: 15s
        timeoutDuration: 0

amqp:
  queues:
    user-code-created:
      exchange-properties:
        name: user.code.events
        type: direct
      routing-key: user.code.created
      queue: user.code.created.queue
      dead-letter-queue-properties:
        queue: user.code.created.queue.dlq
        exchange: user.code.events.dlq
        routing-key: user.code.created.dlq
    subscription-created:
      exchange-properties:
        name: subscription.events
        type: direct
      routing-key: subscription.created
      queue: subscription.created.queue
      dead-letter-queue-properties:
        queue: subscription.created.queue.dlq
        exchange: subscription.events.dlq
        routing-key: subscription.created.dlq
```

<h3>👨🏻‍💻 Contributors</h3>

<table>
  <tr>
    <td>
      <a href="https://github.com/MiguelSperle">
        <img src="https://avatars.githubusercontent.com/u/102910354?v=4" width="100px;" alt="Miguel Sperle Profile Picture"/><br>
      </a>
    </td>
  </tr>
</table>