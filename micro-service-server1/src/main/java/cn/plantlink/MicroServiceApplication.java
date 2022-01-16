package cn.plantlink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author colddew
 * @Date 2021-10-06
 */
@SpringBootApplication
public class MicroServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(MicroServiceApplication.class);

    public static void main(String[] args) throws Exception {

        SpringApplication application = new SpringApplication(MicroServiceApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        ConfigurableApplicationContext context = application.run(args);

        logger.info("load bean quantity: {}", context.getBeanDefinitionCount());

        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            logger.info("load bean name : {}", beanName);
        }

        logger.info("micro-service-server1 is running...");
    }
}
