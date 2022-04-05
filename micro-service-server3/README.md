# make docker image
```
# docker build --build-arg JAR_FILE=target/*.jar -t cn.plantlink/micro-service-server3 .
docker build -t cn.plantlink/micro-service-server3 .
```

# start docker container
```
# docker run -p 9999:9999 cn.plantlink/micro-service-server3
docker run -p 8013:8013 -e "SPRING_PROFILES_ACTIVE=k8s" --network=micro-service-springcloud_microservice cn.plantlink/micro-service-server3
```