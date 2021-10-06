package cn.plantlink

import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MicroServiceApplication

private val logger = LoggerFactory.getLogger(MicroServiceApplication::class.java)

fun main(args: Array<String>) {
    runApplication<MicroServiceApplication>(*args) {
        setBannerMode(Banner.Mode.CONSOLE)
        logger.info("micro-service-server2 is running...")
    }
}