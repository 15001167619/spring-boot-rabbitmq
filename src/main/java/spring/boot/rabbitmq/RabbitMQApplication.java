package spring.boot.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 武海升
 * @date 2018/8/21 9:56
 * @linkUrl http://localhost:15672/#/  guest/guest  [rabbitMQ后台管理]
 */
@SpringBootApplication
@Slf4j
public class RabbitMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class, args);
        log.info("Application  Spring Boot RabbitMQ start-up is success!");
    }

}
