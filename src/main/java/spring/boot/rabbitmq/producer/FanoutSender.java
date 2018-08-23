package spring.boot.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.boot.rabbitmq.config.RabbitConfig;

/**
 * @author 武海升
 * @date 2018/8/23 10:42
 * @desc 广播
 */
@Component
@Slf4j
public class FanoutSender{

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public FanoutSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAll(String context) {
        log.info("Sender：======>"+context);
        this.rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE,"", context);
    }

}
