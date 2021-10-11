package cn.plantlink.controller;

import cn.plantlink.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NacosController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EchoService echoService;

    @RequestMapping(value = "/restEcho/{string}", method = RequestMethod.GET)
    public String restEcho(@PathVariable String string) {
        return restTemplate.getForObject("http://server3/echo/" + string, String.class);
    }

    @RequestMapping(value = "/feignEcho/{string}", method = RequestMethod.GET)
    public String feignEcho(@PathVariable String string) {
        return echoService.echo(string);
    }
}