package spring.boot.rabbitmq.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import spring.boot.rabbitmq.config.RabbitConfig;

/**
 * @author 武海升
 * @date 2018/8/23 10:10
 * @desc 一个生产者，一个消费者
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
@Slf4j
public class MsgReceiver {

    @RabbitHandler
    public void process(String content) {
        log.info("接收处理队列A当中的消息： " + content);
    }

}
