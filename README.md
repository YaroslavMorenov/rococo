# ![](readme/favicon.ico) Rococo
**Дипломная работа по курсу QA.GURU Advanced**
<hr>

## ![](readme/edit-info.png) О проекте
+ Rococo - web-приложение для сохранения художников, их картин и музеев.

### Технологии, использованные в Rococo:
- [Spring Authorization Server](https://spring.io/projects/spring-authorization-server)
- [Spring OAuth 2.0 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [Spring data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Web](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#spring-web)
- [Spring actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Postgres](https://www.postgresql.org/about/)
- [Apache Kafka](https://developer.confluent.io/quickstart/kafka-docker/)
- [Docker](https://www.docker.com/resources/what-container/)
- [Svelte](https://svelte.dev/)
- [Jakarta Bean Validation](https://beanvalidation.org/)
- [JUnit 5 (Extensions, Resolvers, etc)](https://junit.org/junit5/docs/current/user-guide/)
- [Allure](https://docs.qameta.io/allure/)
- [Selenide](https://selenide.org/)
- [Selenoid & Selenoid-UI](https://aerokube.com/selenoid/latest/)
- [Allure-docker-service](https://github.com/fescobar/allure-docker-service)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Gradle 7.6](https://docs.gradle.org/7.6/release-notes.html)

### Микросервисы rococo:
+ [rococo-auth]() - *Сервис авторизации*
+ [rococo-gateway]() - *Api-шлюз*
+ [rococo-userdata]() - *Сервис для работы с профилями пользователей*
+ [rococo-artist]() - *Сервис для работы с художниками*
+ [rococo-country]() - *Сервис для работы с геоданными*
+ [rococo-museum]() - *Сервис для работы с музеями*
+ [rococo-painting]() - *Сервис для работы с картинами*

<hr>

###  Минимальные предусловия для работы с проектом Rococo:
#### 1. Установить docker (Если не установлен)

[Установка на Windows](https://docs.docker.com/desktop/install/windows-install/)

[Установка на Mac](https://docs.docker.com/desktop/install/mac-install/) (Для ARM и Intel разные пакеты)

[Установка на Linux](https://docs.docker.com/desktop/install/linux-install/)

После установки и запуска docker daemon необходимо убедиться в работе команд docker, например `docker -v`

#### 2. Спуллить контейнер postgres:15.1, zookeeper и kafka версии 7.3.2

```posh
docker pull postgres:15.1
docker pull confluentinc/cp-zookeeper:7.3.2
docker pull confluentinc/cp-kafka:7.3.2
```

После `pull` вы увидите спуленный image командой `docker images`

```posh           
REPOSITORY                 TAG              IMAGE ID       CREATED         SIZE
postgres                   15.1             9f3ec01f884d   10 days ago     379MB
confluentinc/cp-kafka      7.3.2            db97697f6e28   12 months ago   457MB
confluentinc/cp-zookeeper  7.3.2            6fe5551964f5   7 years ago     451MB

```

#### 3. Создать volume для сохранения данных из БД в docker на вашем компьютере

```posh
docker volume create rococo
```

#### 4. Запустить БД, zookeeper и kafka 4-мя последовательными командами:

Для *nix:
```posh
docker run --name rococo-db -p 5432:5432 -e POSTGRES_PASSWORD=secret -v rococo:/var/lib/postgresql/data -d postgres:15.1

docker run --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 -d confluentinc/cp-zookeeper:7.3.2

docker run --name=kafka -e KAFKA_BROKER_ID=1 \
-e KAFKA_ZOOKEEPER_CONNECT=$(docker inspect zookeeper --format='{{ .NetworkSettings.IPAddress }}'):2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
-e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
-e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 \
-e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 \
-p 9092:9092 -d confluentinc/cp-kafka:7.3.2
```
Для Windows:
```posh
docker run --name rococo-db -p 5432:5432 -e POSTGRES_PASSWORD=secret -v rococo:/var/lib/postgresql/data -d postgres:15.1

docker run --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 -d confluentinc/cp-zookeeper:7.3.2

docker run --name=kafka -e KAFKA_BROKER_ID=1 -e KAFKA_ZOOKEEPER_CONNECT=$(docker inspect zookeeper --format='{{ .NetworkSettings.IPAddress }}'):2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 -e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 -p 9092:9092 -d confluentinc/cp-kafka:7.3.2
```

#### 6. Установить одну из программ для визуальной работы с Postgres.
Например, PgAdmin 4 или DBeaver.
#### 7.Подключиться к БД postgres (host: localhost, port: 5432, user: postgres, pass: secret, database name: postgres) из PgAdmin и создать пустые БД микросервисов

```sql
create
    database "rococo-auth" with owner postgres;
create
    database "rococo-userdata" with owner postgres;
create
    database "rococo-artist" with owner postgres;
create
    database "rococo-country" with owner postgres;
create
    database "rococo-museum" with owner postgres;
create
    database "rococo-painting" with owner postgres;
```

#### 8. Установить Java версии 17 или новее.
#### 9. Установить пакетый менеджер для сборки front-end npm

[Инструкция](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm).
Рекомендованная версия Node.js - 18.13.0 (LTS)
<hr>


## ![](readme/editor.png) Запуск Rococo локальное в IDE:
#### 1. Запусти фронт Rococo, для этого перейти в соответствующий каталог

```posh
cd rococo-client
```
---
Обнови зависимости и запускай фронт:

```posh
npm i
npm run dev
```

Фронт стартанет в твоем браузере на порту 3000: http://127.0.0.1:3000/

#### 2. Прописать run конфигурацию для всех сервисов rococo-* - Active profiles local

Для этого зайти в меню Run -> Edit Configurations -> выбрать main класс -> указать Active profiles: local
[Инструкция](https://stackoverflow.com/questions/39738901/how-do-i-activate-a-spring-boot-profile-when-running-from-intellij).

#### 3 Запустить сервис rococo-auth c помощью gradle или командой Run в IDE:
- Запустить сервис auth

```posh
cd rococo-auth
gradle bootRun --args='--spring.profiles.active=local'
```

Или просто перейдя к main-классу приложения RococoAuthApplication выбрать run в IDEA (предварительно удостовериться что
выполнен предыдущий пункт)

#### 4 Запустить в любой последовательности другие сервисы: rococo-gateway, rococo-userdata, rococo-geo, rococo-artist, rococo-museum, rococo-painting


## ![](readme/docker.png) Запуск Rococo в докере:

#### 1. Создать бесплатную учетную запись на https://hub.docker.com/ (если отсутствует)
#### 2. Создать в настройках своей учетной записи access_token

[Инструкция](https://docs.docker.com/docker-hub/access-tokens/).

#### 3. Выполнить docker login с созданным access_token (в инструкции это описано)
#### 4. Прописать в etc/hosts элиас для Docker-имени
```
127.0.0.1       client.rococo.dc
127.0.0.1       auth.rococo.dc
127.0.0.1       gateway.rococo.dc
```

#### 5. Перейти в корневой каталог проекта
```posh
cd rococo
```

#### 6. Запустить все сервисы
```posh
bash docker-compose-dev.sh
```
Дождаться старта всех контейнеров rococo-*

Rococo при запуске в докере доступен по адресу http://client.rococo.dc

## ![](readme/testing.png) Запуск e-2-e тестов в докере:
#### 1. Перейти в корневой каталог проекта

```posh
cd rococo
```

#### 2. Запустить все сервисы и тесты:

```posh
bash docker-compose-e2e.sh
```

#### 3. [Selenoid UI](http://localhost:9090/)

#### 4. [Allure](http://localhost:5050/allure-docker-service/projects/rococo-e2e-tests/reports/latest/index.html)
