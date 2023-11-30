#!/bin/bash
source ./docker.properties
export PROFILE=docker

echo '### Java version ###'
java --version

docker_arch=""
front="./rococo-client/";
front_image="$IMAGE_PREFIX/${FRONT_IMAGE_NAME}:latest";

ARCH="$docker_arch" FRONT_IMAGE="$front_image" PREFIX="${IMAGE_PREFIX}" PROFILE="${PROFILE}" docker-compose -f docker-compose.test.yml down

docker_containers="$(docker ps -a -q)"
docker_images="$(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'rococo')"

if [ ! -z "$docker_containers" ]; then
  echo "### Stop containers: $docker_containers ###"
  docker stop $(docker ps -a -q)
  docker rm $(docker ps -a -q)
fi
if [ ! -z "$docker_images" ]; then
  echo "### Remove images: $docker_images ###"
  docker rmi $(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'rococo')
fi

ARCH=$(uname -m)

bash ./gradlew -Pskipjaxb jibDockerBuild -x :rococo-e2e-tests:test

if [ "$ARCH" = "arm64" ] || [ "$ARCH" = "aarch64" ]; then
  docker_arch="linux/arm64"
  docker build --build-arg DOCKER=arm64v8/eclipse-temurin:19-jdk -t "${IMAGE_PREFIX}/${TEST_IMAGE_NAME}:latest" -f ./rococo-e2e-tests/Dockerfile .
else
  docker_arch="linux/amd64"
  docker build --build-arg DOCKER=eclipse-temurin:19-jdk -t "${IMAGE_PREFIX}/${TEST_IMAGE_NAME}:latest" -f ./rococo-e2e-tests/Dockerfile .
fi

cd "$front" || exit
echo "### Build frontend image ###"
docker build -t ${IMAGE_PREFIX}/rococo-client:${FRONT_VERSION} -t ${IMAGE_PREFIX}/rococo-client:latest .
cd ../ || exit
docker pull selenoid/vnc_chrome:110.0
docker images
ARCH="$docker_arch" FRONT_IMAGE="$front_image" PREFIX="${IMAGE_PREFIX}" PROFILE="${PROFILE}" docker-compose -f docker-compose.test.yml up -d
docker ps -a
