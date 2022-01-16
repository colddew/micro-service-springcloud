package cn.plantlink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author colddew
 * @Date 2021-10-10
 */
@SpringBootApplication
public class GatewayApplication {

    private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) throws Exception {

        SpringApplication application = new SpringApplication(GatewayApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);

        logger.info("gateway is running...");
    }
}
