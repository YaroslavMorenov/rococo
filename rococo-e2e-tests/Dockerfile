ARG DOCKER
FROM $DOCKER
ENV JAVA_OPTS=""

WORKDIR /rococo
COPY ./gradle ./gradle
COPY ./gradlew ./
COPY ./rococo-e2e-tests ./rococo-e2e-tests
COPY ./build.gradle ./
COPY ./settings.gradle ./

CMD chmod +x ./rococo-e2e-tests/send_results.sh; ./gradlew test -Dtest.env=docker -Drepository=jpa; bash ./rococo-e2e-tests/send_results.sh
