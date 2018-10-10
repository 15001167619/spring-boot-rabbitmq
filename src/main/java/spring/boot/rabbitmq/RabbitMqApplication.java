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
public class RabbitMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApplication.class, args);
        log.info("Application  Spring Boot RabbitMq start-up is success!");
    }

}
