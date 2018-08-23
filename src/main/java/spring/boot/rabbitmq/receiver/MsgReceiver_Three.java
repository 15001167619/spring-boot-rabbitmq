package spring.boot.rabbitmq.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import spring.boot.rabbitmq.config.RabbitConfig;

/**
 * @author 武海升
 * @date 2018/8/23 10:16
 * @desc 一个生产者，多个消费者
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_C)
@Slf4j
public class MsgReceiver_Three {

    @RabbitHandler
    public void process(String content) {
        log.info("处理器three接收处理队列C当中的消息： " + content);
    }

}
