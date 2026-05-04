# Jobnest

![Status](https://img.shields.io/badge/status-concluído-brightgreen?style=for-the-badge)

## 🚀 Visão Geral

Jobnest é uma aplicação backend para gerenciamento de vagas de emprego, desenvolvida para atender tanto recrutadores que
anunciam oportunidades quanto candidatos que buscam emprego, contemplando funcionalidades compartilhadas entre os perfis e
funcionalidades exclusivas para cada um.

> ⚠️ Este projeto foi desenvolvido para fins educacionais, com foco na exploração e
aplicação de tópicos previamente estudados.

### Principais tópicos abordados:

Arquitetura Monolítica, Arquitetura Limpa, SOLID, Comunicação Assíncrona combinada com padrões Inbox/Outbox,
Integração com Serviço de Armazenamento em Nuvem, Autenticação utilizando JWT, Transações de Banco de Dados, Idempotência,
Rate Limiting e Testes Unitários.

## ⚙️ Tecnologias Utilizadas

![Java](https://img.shields.io/badge/Java-21-000?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=ED8B00)
![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/-Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_data_jpa-6DB33F?style=for-the-badge&logo=hibernate&logoColor=white)
![MAVEN](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![POSTGRESQL](https://img.shields.io/badge/-postgresql-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![RABBITMQ](https://img.shields.io/badge/-rabbitmq-%23FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![REDIS](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![CLOUDINARY](https://img.shields.io/badge/Cloudinary-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white)
![DOCKER](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## 🔧 Instalação

### 1. Clonar o repositório

```
git clone https://github.com/MiguelSperle/jobnest.git
```

### 2. Acessar a pasta

```
cd jobnest
```

### 3. Instalar as dependências

```
mvn clean install
```

### 4. Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto e configure as variáveis de ambiente necessárias:

```env
DB_HOST=
DB_NAME=
DB_USERNAME=
DB_PASSWORD=

RABBITMQ_HOST=
RABBITMQ_USERNAME=
RABBITMQ_PASSWORD=

REDIS_HOST=

MAIL_USERNAME=
MAIL_PASSWORD=

CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=
```

### 5. Subir os serviços com Docker Compose

```
docker-compose up -d
```

A aplicação estará disponível em `http://localhost:8080`

## 🧪 Testes

Para rodar os testes unitários:

```
mvn test
```

## 👨‍💻 Autor

Desenvolvido por **Miguel Sperle**