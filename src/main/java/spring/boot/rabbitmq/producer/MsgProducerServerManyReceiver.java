package spring.boot.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.boot.rabbitmq.config.RabbitConfig;

import java.util.UUID;

/**
 * @author 武海升
 * @date 2018/8/23 10:01
 * @desc 消息的生产者(一对多)
 */
@Component
@Slf4j
public class MsgProducerServerManyReceiver implements RabbitTemplate.ConfirmCallback {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MsgProducerServerManyReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }


    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B, RabbitConfig.ROUTINGKEY_B, content, correlationId);
    }



    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.info(" 回调id:" + correlationData);
        if (b) {
            log.info("消息成功消费");
        } else {
            log.info("消息消费失败:" + s);
        }

    }
}
