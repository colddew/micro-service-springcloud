# brief
microservice based on springcloud alibaba

# tech stack
- [x] Kotlin
- [x] Nacos
- [x] Elasticsearch
- [x] Canal
- [ ] Javassist
- [x] Sentinel

# startup script
```
java -Dserver.port=7777 -Dcsp.sentinel.dashboard.server=127.0.0.1:7777 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
sentinel/sentinel
```

