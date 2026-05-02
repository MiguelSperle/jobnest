<h1>Jobnest 💻</h1>

<p>
    <img src="https://img.shields.io/badge/-java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Badge"/>
    <img src="https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="SpringBoot Badge"/>
    <img src="https://img.shields.io/badge/-Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security Badge" />
    <img src="https://img.shields.io/badge/Spring_data_jpa-6DB33F?style=for-the-badge&logo=hibernate&logoColor=white" alt="Spring Data Jpa Badge" />
    <img src="https://img.shields.io/badge/-postgresql-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres Badge"/>
    <img src="https://img.shields.io/badge/-rabbitmq-%23FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="RabbitMQ Badge"/>
    <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white" alt="Redis Badge" />
    <img src="https://img.shields.io/badge/Cloudinary-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white" alt="Cloudinary Badge" />
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker Badge"/>
    <img src="https://img.shields.io/badge/junit-%23E33332?logo=junit5&logoColor=white&style=for-the-badge" alt="JUnit Badge" />
    <img src="https://img.shields.io/badge/Mockito-1F3D1F?style=for-the-badge" alt="Mockito Badge" />
</p>

<p>
In summary, Jobnest is a backend application designed to efficiently manage and provide job vacancies. 
It offers a comprehensive set of features for both recruiters posting job openings and job seekers applying for them.
</p>

<p>
It is important to emphasize that this project was developed for educational purposes, 
focusing on the exploration and implementation of previously studied topics.
</p>

<p>
This project implements the following topics: Clean Architecture, SOLID, Messaging System combined with the implementation of the Inbox and Outbox patterns, 
Integration with Cloud Storage Service, Authentication using JWT, Database Transactions, Idempotency, Rate Limiting, and Unit Testing.
</p>

<p>
This is just a brief description to clarify the purpose of the project.
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

<h3>👨🏻‍💻 Contributors</h3>

<table>
  <tr>
    <td>
      <a href="https://github.com/MiguelSperle">
        <img src="https://avatars.githubusercontent.com/u/102910354?v=4" width="100px;" alt="Profile photo of Miguel Sperle"/><br>
      </a>
    </td>
  </tr>
</table>