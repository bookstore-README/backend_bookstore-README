cd ..
./gradlew clean build
docker image rm -f $(docker image ls -aq)

dockerUrl=didadu/readme:v1.0.0
docker buildx install
docker buildx create --use
docker buildx build --platform linux/amd64,linux/arm64 -t $dockerUrl -f docker/Dockerfile --push .
docker buildx rm
docker image rm moby/buildkit:buildx-stable-1

#docker image build -t readme:v1.0.0 -f docker/Dockerfile .
#docker image tag readme:v1.0.0 didadu/readme:v1.0.0
#docker push didadu/readme:v1.0.0