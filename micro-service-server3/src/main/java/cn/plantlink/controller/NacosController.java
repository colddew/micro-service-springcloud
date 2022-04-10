package cn.plantlink.controller;

import cn.plantlink.config.DynamicProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
public class NacosController {

    @Autowired
    private DynamicProperties dynamicProperties;

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) throws Exception {
        return "Hello Nacos Discovery " + string + ", IP address is " + InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * modify config
     * curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=server3.properties&group=DEFAULT_GROUP&content=service.local.config=2"
     *
     * get config
     * curl http://127.0.0.1:8013/config/get
     */
    @RequestMapping("/config/get")
    public String get() {
        return dynamicProperties.getServerLocalConfig();
    }
}