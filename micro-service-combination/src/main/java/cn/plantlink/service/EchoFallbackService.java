package cn.plantlink.service;

import org.springframework.stereotype.Component;

/**
 * @Author colddew
 * @Date 2022-02-17
 */
@Component
public class EchoFallbackService implements EchoService {

    @Override
    public String echo(String string) {
        return "fallback";
    }
}
