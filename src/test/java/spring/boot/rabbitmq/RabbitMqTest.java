package spring.boot.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.boot.rabbitmq.producer.FanoutSender;
import spring.boot.rabbitmq.producer.MsgProducer;
import spring.boot.rabbitmq.producer.MsgProducerServerManyReceiver;

/**
 * @author 武海升
 * @date 2018/8/21 10:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RabbitMqTest {

    @Autowired
    private MsgProducer msgProducer;

    @Autowired
    private MsgProducerServerManyReceiver msgProducerServerManyReceiver;

    @Autowired
    private FanoutSender fanoutSender;

    /**
     * 一个生产者一个消费者
     */
    @Test
    public void testSendMsgOneToOne() {
        log.info("*********一个生产者一个消费者**********");
        msgProducer.sendMsg("我生产了一个小脑虎");
    }
    /**
     * 一个生产者多个消费者
     */
    @Test
    public void testSendMsgOneToMany() {
        log.info("*********一个生产者多个消费者**********");
        for (int i = 0; i < 10; i++) {
            msgProducerServerManyReceiver.sendMsg("我生产了一个小脑虎"+i);
        }

    }
    /**
     * 广播
     */
    @Test
    public void testSendAll() {
        log.info("*********广播**********");
        String context = "大家好！我叫小脑虎....";
        fanoutSender.sendAll(context);

    }

}
