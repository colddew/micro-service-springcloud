# build package of different environment
```
# mvn clean package -Dmaven.test.skip=true -Pdev
mvn clean package -Dmaven.test.skip=true -Pk8s
```

# build docker image
```
# docker build --build-arg JAR_FILE=target/*.jar -t micro-service-server3 .
docker build -t micro-service-server3 .
```

# run docker container
```
# docker run -p 8013:8013 micro-service-server3
docker run -p 8013:8013 -e "SPRING_PROFILES_ACTIVE=k8s" micro-service-server3
```