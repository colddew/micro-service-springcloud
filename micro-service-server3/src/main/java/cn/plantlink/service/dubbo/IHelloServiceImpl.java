package cn.plantlink.service.dubbo;

import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DubboService(group = "springcloud")
public class IHelloServiceImpl implements IHelloService {

    private static final Logger logger = LoggerFactory.getLogger(IHelloServiceImpl.class);

    public String sayHello(String name) {
        return "Hello, " + name;
    }
}