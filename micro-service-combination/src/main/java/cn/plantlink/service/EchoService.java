package cn.plantlink.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "server3", fallback = EchoFallbackService.class)
public interface EchoService {

    @GetMapping(value = "/echo/{string}")
    String echo(@PathVariable("string") String string);
}