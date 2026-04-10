<h1>Jobnest 💻</h1>

<p>
    <img src="https://img.shields.io/badge/-java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Badge"/>
    <img src="https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="SpringBoot Badge"/>
    <img src="https://img.shields.io/badge/-Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security Badge" />
    <img src="https://img.shields.io/badge/Spring_data_jpa-6DB33F?style=for-the-badge&logo=hibernate&logoColor=white" alt="Spring Data Jpa Badge" />
    <img src="https://img.shields.io/badge/-postgresql-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres Badge"/>
    <img src="https://img.shields.io/badge/-rabbitmq-%23FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="RabbitMQ Badge"/>
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker Badge"/>
    <img src="https://img.shields.io/badge/junit-%23E33332?style=for-the-badge&logo=junit5&logoColor=white" alt="Junit Badge"/>
    <img src="https://img.shields.io/badge/Mockito-25A162?style=for-the-badge" alt="Mockito Badge" />
</p>

<p>
To summarize, JobNest is a back-end application designed to efficiently manage and support job vacancy searches. It provides a complete set of functionalities that a system of this kind typically offers, 
both for those who publish job vacancies and for those who seek to apply for them.
</p>

<p>
It's important to emphasize that this is a study project designed to explore and implement complex concepts, such as: Clean Architecture, SOLID Principles, Messaging System combined with the implementation of the Inbox and Outbox pattern, Database Transactions, Unit Testing and Idempotency.
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

| Variable | Description
|----------|------------
| `DB_HOST` | To specify the Database server address
| `DB_NAME` | To specify the Database name
| `DB_USERNAME` | To authenticate with the Database
| `DB_PASSWORD` | To authenticate with the Database
| `RABBITMQ_HOST` | To specify the RabbitMQ server address
| `RABBITMQ_USERNAME` | To authenticate with RabbitMQ
| `RABBITMQ_PASSWORD` | To authenticate with RabbitMQ
| `REDIS_HOST` | To specify the Redis server address
| `MAIL_USERNAME` | To authenticate with the SMTP service
| `MAIL_PASSWORD` |To authenticate with the SMTP service
| `CLOUDINARY_CLOUD_NAME` | To identify your account on Cloudinary
| `CLOUDINARY_API_KEY` | To authenticate with Cloudinary
| `CLOUDINARY_API_SECRET` | To authenticate with Cloudinary
| `JWT_SECRET` | To sign and verify JSON Web Tokens

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