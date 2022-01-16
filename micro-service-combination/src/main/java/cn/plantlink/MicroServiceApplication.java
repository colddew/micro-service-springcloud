package cn.plantlink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author colddew
 * @Date 2021-10-06
 */
@EnableFeignClients
@SpringBootApplication
public class MicroServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(MicroServiceApplication.class);

    public static void main(String[] args) throws Exception {

        SpringApplication application = new SpringApplication(MicroServiceApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);

        logger.info("micro-service-combination is running...");
    }
}
